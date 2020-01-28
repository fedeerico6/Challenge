package com.avalith.challenge.service;

import com.avalith.challenge.domain.Area;
import com.avalith.challenge.domain.Employee;
import com.avalith.challenge.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private AreaService areaService;

    /**
     * SAVE A EMPLOYEE
     * @param employee = EMPLOYEE TO SAVE
     * @param areaId = AREA TO WHICH THE EMPLOYEE BELONGS
     * @return EMPLOYEE TO SAVE
     */
    public Optional<Employee> save(Employee employee, Integer areaId) {
        Area area = areaService.findById(areaId).orElse(new Area());
        employee.setArea(area);
        return Optional.of(employeeRepository.save(employee));
    }

    /**
     * FIND A EMPLOYEE BY ID
     * @param id = ID TO SEARCH EMPLOYEE
     * @return FOUND EMPLOYEE
     */
    public Optional<Employee> findById(Integer id) {
        return employeeRepository.findById(id);
    }

    /**
     * SEARCH ALL EMPLOYEES
     * @return ALL EMPLOYEES
     */
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    /**
     * CHECK IF TWO EMPLOYEES ARE FROM THE SAME AREA
     * @param fromEmployeeId = FIRST EMPLOYEE
     * @param voteEmployeeId = SECOND EMPLOYEE
     * @return RETURN TRUE IF THEY ARE FROM THE SAME AREA IF NOT RETURN FALSE
     */
    public boolean checkEmployeeSameArea(Integer fromEmployeeId, Integer voteEmployeeId) {
        Employee fromEmployee = employeeRepository.findById(fromEmployeeId).orElse(new Employee());
        Employee voteEmployee = employeeRepository.findById(voteEmployeeId).orElse(new Employee());
        return fromEmployee.getArea().getId().equals(voteEmployee.getArea().getId());
    }
}
