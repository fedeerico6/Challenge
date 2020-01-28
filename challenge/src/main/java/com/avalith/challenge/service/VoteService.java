package com.avalith.challenge.service;

import com.avalith.challenge.domain.Employee;
import com.avalith.challenge.domain.Vote;
import com.avalith.challenge.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class VoteService {
    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private EmployeeService employeeService;

    /**
     * SAVE A VOTE
     * @param vote = VOTE TO SAVE
     * @param fromEmployeeId = EMPLOYEE VOTING
     * @param voteEmployeeId = VOTED EMPLOYEE
     * @return SAVED VOTE
     */
    public Optional<Vote> save(Vote vote, Integer fromEmployeeId, Integer voteEmployeeId) {
        vote.setFromEmployee(employeeService.findById(fromEmployeeId).orElse(new Employee()));
        vote.setVoteEmployee(employeeService.findById(voteEmployeeId).orElse(new Employee()));
        return Optional.of(voteRepository.save(vote));
    }

    /**
     * FIND A VOTE BY ID
     * @param id = ID TO SEARCH VOTE
     * @return FOUND VOTE
     */
    public Optional<Vote> findById(Integer id) {
        return voteRepository.findById(id);
    }

    /***
     * SEARCH ALL VOTES
     * @return ALL VOTES
     */
    public List<Vote> findAll() {
        return voteRepository.findAll();
    }

    /**
     * CHECK THAT THE TWO EMPLOYEES ARE NOT OF THE SAME AREA
     * @param employeeId = EMPLOYEE VOTING
     * @param voteEmployeeId = VOTED EMPLOYEE
     * @return TRUE OR FALSE
     */
    public boolean checkSameArea(Integer employeeId, Integer voteEmployeeId) {
        Employee employee = employeeService.findById(employeeId).orElse(new Employee());
        Employee voteEmployee = employeeService.findById(voteEmployeeId).orElse(new Employee());
        for(Vote vote: voteRepository.findAll()){
            if(employee.getId() == vote.getFromEmployee().getId()) {
                if (voteEmployee.getArea().getId() == vote.getVoteEmployee().getArea().getId()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * MOST VOTED EMPLOYEE, SEARCH BETWEEN THE RANGE OF MONTHS
     * @param month1 = FIRST MONTH TO SEARCH
     * @param month2 = SECOND MONTH TO SEARCH
     * @return MOST VOTED EMPLOYEE
     */
    public Optional<Employee> mostVotedEmployee(Integer month1, Integer month2){
        Optional<Employee> bestEmployee = Optional.ofNullable(null);
        List<Object[]> array = voteRepository.mostVotedEmployee(month1, month2);
        for(Object[] objects : array) {
            bestEmployee = employeeService.findById((Integer) objects[0]);
        }
        return bestEmployee;
    }

    public long countEmployees() {
        return voteRepository.count();
    }

    /**
     *  LOOK FOR WHICH IS THE MOST VOTED EMPLOYEE IN A SPECIFIC AREA
     * @param area = AREA TO SEARCH
     * @return MOST VOTED EMPLOYEE BY AREA
     */
    public Optional<Employee> mostVoteArea(String area) {
        Optional<Employee> bestEmployee = Optional.ofNullable(null);
        List<Object[]> array = voteRepository.mostVotedArea(area);
        for(Object[] objects : array) {
            bestEmployee = employeeService.findById((Integer) objects[0]);
        }
        return bestEmployee;
    }

}
