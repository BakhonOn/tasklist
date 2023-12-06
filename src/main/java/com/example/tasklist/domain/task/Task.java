package com.example.tasklist.domain.task;

import com.example.tasklist.domain.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    @Id
    private Long id;
    private String title;
    private String description;
    private Status status;
    private LocalDateTime expirationDate;
    @ManyToOne
    @JoinColumn(name = "user_id") // Это указывает столбец внешнего ключа в таблице Task
    private User user;


}
