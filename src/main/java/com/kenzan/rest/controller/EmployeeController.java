package com.kenzan.rest.controller;

import com.kenzan.rest.model.Employee;
import com.kenzan.rest.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    private static final String producesData = "application/json;charset=UTF-8";
    private static final String consumesData = "application/json;charset=UTF-8";

    @GetMapping(value = "/employees/{employeeId}", produces = {producesData})
    public ResponseEntity<Object> retrieveEmployee(@PathVariable String employeeId){
        Employee employee = employeeService.retrieveEmployeeById(employeeId);
        return ResponseEntity.ok(employee);
    }

    @GetMapping(value = "/employees", produces = {producesData})
    public ResponseEntity<Object> retrieveAllEmployees(){
        List<Employee> employees = employeeService.retrieveAllEmployees();
        return ResponseEntity.ok(employees);
    }

    @PostMapping(value = "/employees", consumes = {consumesData})
    public ResponseEntity<Object> createEmployee(@Valid @RequestBody Employee employee){
        Employee createdEmployee = employeeService.createEmployee(employee);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().build().toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/employees/{employeeId}", consumes = {consumesData}, produces = {producesData})
    public ResponseEntity<Object> updateEmployee(@PathVariable String employeeId, @Valid @RequestBody Employee employee){
       employeeService.updateEmployee(employeeId, employee);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/employees/{employeeId}", produces = {producesData})
    public ResponseEntity<Object> deleteEmployee(@PathVariable String employeeId, @RequestHeader(value = "Authorization",
            required = false) String authorization){
        employeeService.deleteEmployee(employeeId, authorization);
        return ResponseEntity.noContent().build();
    }
}
