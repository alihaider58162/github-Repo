package com.example.JenkinsCICD.Repository;

import com.example.JenkinsCICD.Entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {
}