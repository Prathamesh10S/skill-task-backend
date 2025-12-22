package com.skill.taskassigner.service;

import com.skill.taskassigner.entity.Employee;
import com.skill.taskassigner.entity.Task;
import com.skill.taskassigner.repository.EmployeeRepository;
import com.skill.taskassigner.repository.TaskRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class TaskAssignmentService {

    private final TaskRepository taskRepo;
    private final EmployeeRepository employeeRepo;

    public TaskAssignmentService(TaskRepository taskRepo, EmployeeRepository employeeRepo) {
        this.taskRepo = taskRepo;
        this.employeeRepo = employeeRepo;
    }

    @Transactional
    public Task assignTask(Task task) {

        String requiredSkill = task.getRequiredSkill().toLowerCase();

        List<Employee> matchingEmployees =
                employeeRepo.findAll().stream()
                        .filter(e -> e.getSkills().toLowerCase().contains(requiredSkill))
                        .toList();

        if (!matchingEmployees.isEmpty()) {
            Employee selected = matchingEmployees.stream()
                    .min(Comparator.comparingInt(Employee::getWorkload))
                    .get();

            task.setAssignedEmployee(selected);
            task.setStatus("ASSIGNED");

            selected.setWorkload(selected.getWorkload() + 1);
            employeeRepo.save(selected);
        } else {
            // ✅ FIX: allow task creation without assignment
            task.setAssignedEmployee(null);
            task.setStatus("UNASSIGNED");
        }

        return taskRepo.save(task);
    }

    @Transactional
    public Task updateTaskStatus(Long taskId, String newStatus) {

        Task task = taskRepo.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        if ("COMPLETED".equals(task.getStatus())) {
            throw new RuntimeException("Task already completed");
        }

        if ("COMPLETED".equals(newStatus)) {
            Employee emp = task.getAssignedEmployee();

            if (emp != null) {
                emp.setWorkload(Math.max(0, emp.getWorkload() - 1));
                employeeRepo.save(emp);
            }

            // 🔥 CRITICAL FIX
            task.setAssignedEmployee(null);
        }

        task.setStatus(newStatus);
        return taskRepo.save(task);
    }

    @Transactional
    public void deleteTask(Long taskId) {

        Task task = taskRepo.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        if ("ASSIGNED".equals(task.getStatus()) || "IN_PROGRESS".equals(task.getStatus())) {
            throw new RuntimeException("Cannot delete active task");
        }

        taskRepo.delete(task);
    }

}
