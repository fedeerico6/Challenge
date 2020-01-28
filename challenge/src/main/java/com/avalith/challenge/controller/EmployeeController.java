package com.avalith.challenge.controller;

import com.avalith.challenge.domain.Employee;
import com.avalith.challenge.service.EmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("employee")
@Api(value = "Employee Management System")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @ApiOperation(value = "", response = List.class)
    @GetMapping("findAll")
    public List<Employee> findAll() {
        return employeeService.findAll();
    }

    @ApiOperation(value = "", response = Employee.class)
    @GetMapping("findById/{id}")
    public ResponseEntity<Employee> findById(@ApiParam(required = true) @PathVariable Integer id) {
        return ResponseEntity.of(employeeService.findById(id));
    }

    @ApiOperation(value = "", response = Employee.class)
    @PostMapping("save/{areaId}")
    public ResponseEntity<Employee> save(@ApiParam(required = true) @PathVariable(value = "areaId")Integer areaId,
                                         @ApiParam(required = true) @RequestBody Employee employee) {
        return ResponseEntity.of(employeeService.save(employee, areaId));
    }
}
