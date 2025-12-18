package com.skill.taskassigner.controller;

import com.skill.taskassigner.entity.Task;
import com.skill.taskassigner.repository.TaskRepository;
import com.skill.taskassigner.security.AdminAuth;
import com.skill.taskassigner.service.TaskAssignmentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Task APIs")
@RestController
@RequestMapping("/api/tasks")
@CrossOrigin
public class TaskController {

    private final TaskAssignmentService service;
    private final TaskRepository taskRepo;
    private final AdminAuth adminAuth;

    public TaskController(
            TaskAssignmentService service,
            TaskRepository taskRepo,
            AdminAuth adminAuth
    ) {
        this.service = service;
        this.taskRepo = taskRepo;
        this.adminAuth = adminAuth;
    }

    // 🔒 ADMIN ONLY (CREATE TASK)
    @PostMapping
    public Task createTask(
            @RequestHeader(value = "X-ADMIN-KEY", required = false) String adminKey,
            @RequestBody Task task
    ) {
        adminAuth.checkAdmin(adminKey);
        return service.assignTask(task);
    }

    // 🔒 ADMIN ONLY (UPDATE STATUS)
    @PutMapping("/{taskId}/status")
    public Task updateStatus(
            @RequestHeader(value = "X-ADMIN-KEY", required = false) String adminKey,
            @PathVariable Long taskId,
            @RequestParam String status
    ) {
        adminAuth.checkAdmin(adminKey);
        return service.updateTaskStatus(taskId, status);
    }

    // ✅ PUBLIC (READ-ONLY)
    @GetMapping
    public List<Task> getAllTasks() {
        return taskRepo.findAll();
    }
}
