package com.skill.taskassigner.controller;

import com.skill.taskassigner.entity.Task;
import com.skill.taskassigner.repository.TaskRepository;
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

    public TaskController(TaskAssignmentService service, TaskRepository taskRepo) {
        this.service = service;
        this.taskRepo = taskRepo;
    }

    @PostMapping
    public Task createTask(@RequestBody Task task) {
        return service.assignTask(task);
    }

    @PutMapping("/{taskId}/status")
    public Task updateStatus(
            @PathVariable Long taskId,
            @RequestParam String status) {
        return service.updateTaskStatus(taskId, status);
    }

    @GetMapping
    public List<Task> getAllTasks() {
        return taskRepo.findAll();
    }
}
