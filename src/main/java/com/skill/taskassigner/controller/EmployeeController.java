package com.skill.taskassigner.controller;

import com.skill.taskassigner.entity.Employee;
import com.skill.taskassigner.security.AdminAuth;
import com.skill.taskassigner.service.EmployeeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Employee APIs", description = "Manage employees and skills")
@RestController
@RequestMapping("/api/employees")
@CrossOrigin
public class EmployeeController {

    private final EmployeeService employeeService;
    private final AdminAuth adminAuth;

    public EmployeeController(EmployeeService employeeService, AdminAuth adminAuth) {
        this.employeeService = employeeService;
        this.adminAuth = adminAuth;
    }

    // ✅ PUBLIC (READ-ONLY)
    @GetMapping
    public List<Employee> all() {
        return employeeService.getAllEmployees();
    }

    // 🔒 ADMIN ONLY (CREATE)
    @PostMapping
    public Employee add(
            @RequestHeader(value = "X-ADMIN-KEY", required = false) String adminKey,
            @RequestBody Employee emp) {

        adminAuth.checkAdmin(adminKey);
        return employeeService.addEmployee(emp);
    }

    // 🔒 ADMIN ONLY (DELETE)
    @DeleteMapping("/{employeeId}")
    public String deleteEmployee(
            @RequestHeader(value = "X-ADMIN-KEY", required = false) String adminKey,
            @PathVariable Long employeeId) {

        adminAuth.checkAdmin(adminKey);
        employeeService.deleteEmployee(employeeId);
        return "Employee deleted successfully";
    }
}
