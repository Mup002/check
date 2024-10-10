package com.example.demo.repository;

import com.example.demo.entity.ShiftDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShiftDetailRepository extends JpaRepository<ShiftDetail,Long> {
}
