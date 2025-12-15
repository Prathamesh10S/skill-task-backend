package com.skill.taskassigner.dto;

import lombok.Data;

@Data
public class TaskDTO {
    private Long id;
    private String title;
    private String requiredSkill;
    private String status;
    private EmployeeDTO assignedEmployee;
}
