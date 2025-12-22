package com.skill.taskassigner.service;

import com.skill.taskassigner.entity.Employee;
import com.skill.taskassigner.entity.Task;
import com.skill.taskassigner.repository.EmployeeRepository;
import com.skill.taskassigner.repository.TaskRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepo;
    private final TaskRepository taskRepo;

    @Transactional
    public Employee addEmployee(Employee emp) {

        // HARD RESET (same as your controller)
        emp.setId(null);
        emp.setWorkload(0);

        Employee savedEmployee = employeeRepo.save(emp);

        String skills = savedEmployee.getSkills().toLowerCase();

        // Find all UNASSIGNED tasks
        List<Task> unassignedTasks = taskRepo.findByStatus("UNASSIGNED");

        for (Task task : unassignedTasks) {
            if (skills.contains(task.getRequiredSkill().toLowerCase())) {

                task.setAssignedEmployee(savedEmployee);
                task.setStatus("ASSIGNED");

                savedEmployee.setWorkload(savedEmployee.getWorkload() + 1);
                taskRepo.save(task);
            }
        }

        // Persist updated workload
        return employeeRepo.save(savedEmployee);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepo.findAll();
    }

    public void deleteEmployee(Long employeeId) {

        Employee emp = employeeRepo.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        if (taskRepo.existsByAssignedEmployee(emp)) {
            throw new RuntimeException("Cannot delete employee with assigned tasks");
        }

        employeeRepo.delete(emp);
    }

}
