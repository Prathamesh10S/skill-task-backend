package com.skill.taskassigner.controller;

import com.skill.taskassigner.entity.Employee;
import com.skill.taskassigner.service.EmployeeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Employee APIs", description = "Manage employees and skills")
@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
@CrossOrigin
public class EmployeeController {

    private final EmployeeService service;

    @PostMapping
    public Employee add(@RequestBody Employee emp) {
        return service.addEmployee(emp);
    }

    @GetMapping
    public List<Employee> all() {
        return service.getAllEmployees();
    }

    @DeleteMapping("/{employeeId}")
    public String deleteEmployee(@PathVariable Long employeeId) {
        service.deleteEmployee(employeeId);
        return "Employee deleted successfully";
    }
}
