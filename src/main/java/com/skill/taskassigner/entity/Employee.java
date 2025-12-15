package com.skill.taskassigner.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String skills;   // Example: "JAVA,SPRING,SQL"
    private Integer workload; // Number of active tasks
}
