package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "shifts")
public class Shift {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shift_id")
    private Long id;

    @Column(nullable = false)
    private String shiftName;

    @Column(nullable = false)
    private LocalDateTime checkIn;

    @Column(nullable = false)
    private LocalDateTime checkOut;

    @Column(nullable = false)
    private String dayOfWeek;

    @Column(nullable = false)
    private String shiftCode;

    private boolean isAvailable;

    @Column(updatable = false)
    private LocalDateTime created_at;

    @PrePersist
    public void onCreate(){
        this.created_at = LocalDateTime.now();
    }
}
