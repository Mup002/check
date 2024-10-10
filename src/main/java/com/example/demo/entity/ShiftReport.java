package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "shift_reports")
public class ShiftReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long countWorkDay;

    private Long leaveAllowed;

    private Long leaveNotAllowed;

    private Double countWorkTime;

    private LocalDateTime created_at;

    @Column(updatable = false)
    private LocalDateTime updated_at;

    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "shift_id",referencedColumnName = "shift_id")
    private Shift shift;

    @PrePersist
    public void onCreated(){
        this.created_at = LocalDateTime.now();
    }

}
