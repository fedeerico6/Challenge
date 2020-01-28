package com.avalith.challenge.controller;

import com.avalith.challenge.domain.Vote;
import com.avalith.challenge.service.EmployeeService;
import com.avalith.challenge.service.VoteService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("vote")
@Api(value = "Vote Management System")
public class VoteController {
    @Autowired
    private VoteService voteService;

    @Autowired
    private EmployeeService employeeService;

    @ApiOperation(value = "return the list of votes", response = List.class)
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("findAll")
    public List<Vote> findALl() {
        return voteService.findAll();
    }

    @ApiOperation(value = "look for an vote by id", response = Vote.class)
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("findById/{id}")
    public ResponseEntity<Vote> findById(@ApiParam(value = "vote id", required = true) @PathVariable Integer id) {
        return ResponseEntity.of(voteService.findById(id));
    }

    @ApiOperation(value = "Keep a vote", response = Vote.class)
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @PostMapping("save/{fromEmployeeId}/{voteEmployeeId}")
    public ResponseEntity<Vote> save(@ApiParam(required = true) @RequestBody Vote vote,
                                     @ApiParam(required = true) @PathVariable(value = "fromEmployeeId")Integer fromEmployeeId,
                                     @ApiParam(required = true) @PathVariable(value = "voteEmployeeId")Integer voteEmployeeId) {
        return ResponseEntity.of(voteService.save(vote, fromEmployeeId, voteEmployeeId));
    }

    @ApiOperation(value = "Take a vote", response = Vote.class)
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @PostMapping("ballot/{fromEmployeeId}/{voteEmployeeId}")
    public ResponseEntity<?> vote(@ApiParam(required = true) @RequestBody Vote vote,
                                  @ApiParam(required = true) @PathVariable(value = "fromEmployeeId")Integer fromEmployeeId,
                                  @ApiParam(required = true) @PathVariable(value = "voteEmployeeId")Integer voteEmployeeId) {
        if (fromEmployeeId == voteEmployeeId) {
            return ResponseEntity.badRequest()
                    .body("No se puede votar a uno mismo");
        }

        if (vote.getCommentary().length() > 175) {
            return ResponseEntity.badRequest()
                    .body("El comentario no puede exceder los 175 caracteres");
        }
        if (employeeService.checkEmployeeSameArea(fromEmployeeId, voteEmployeeId)) {
            return ResponseEntity.badRequest()
                    .body("Nose puede votar a un empleado de la misma area");
        }

        if (!voteService.checkSameArea(fromEmployeeId, voteEmployeeId)) {
            return ResponseEntity.badRequest()
                    .body("Ya realizo un voto dirigido a esa misma area");
        }

        return ResponseEntity.of(voteService.save(vote, fromEmployeeId, voteEmployeeId));

    }

    @ApiOperation(value = "Most voted employee", response = Vote.class)
    @Secured("ROLE_ADMIN")
    @GetMapping("mostVoted/{month1}/{month2}")
    public ResponseEntity<?> mostVotedEmployee(@ApiParam(required = true) @PathVariable(value = "month1") Integer month1,
                                               @ApiParam(required = true) @PathVariable(value = "month2") Integer month2) {
        return ResponseEntity.of(voteService.mostVotedEmployee(month1,month2));
    }

    @ApiOperation(value = "Count the number of registered employees", response = long.class)
    @Secured("ROLE_ADMIN")
    @GetMapping("count")
    public long countEmployees() {
        return voteService.countEmployees();
    }

    @ApiOperation(value = "Returns the most voted employee of a certain area", response = Vote.class)
    @Secured("ROLE_ADMIN")
    @GetMapping("mostArea/{area}")
    public ResponseEntity<?> mostVotedArea(@ApiParam(required = true) @PathVariable String area) {
        return ResponseEntity.of(voteService.mostVoteArea(area));
    }
}
