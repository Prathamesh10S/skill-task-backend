package com.skill.taskassigner.repository;

import com.skill.taskassigner.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByStatus(String status);

    List<Task> findByAssignedEmployeeId(Long employeeId);

    List<Task> findByStatusAndAssignedEmployeeId(String status, Long employeeId);
}

