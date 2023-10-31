package com.example.tasklist.domain.task;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Task {
    @Id
    private Long id;
    private String title;
    private String description;
    private Status status;
    private LocalDateTime expirationDate;



}
