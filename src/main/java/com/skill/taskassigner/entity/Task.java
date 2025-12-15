package com.skill.taskassigner.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String requiredSkill; // Example: "JAVA"

    private String status; // NEW, ASSIGNED, COMPLETED

    @ManyToOne
    private Employee assignedEmployee;
}
