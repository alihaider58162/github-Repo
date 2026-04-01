package com.example.JenkinsCICD.Service;

import com.example.JenkinsCICD.Entity.Car;
import com.example.JenkinsCICD.Repository.CarRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {

    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public Car saveCar(Car car) {
        return carRepository.save(car);
    }

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public Car getCarById(Long id) {
        return carRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Car not found"));
    }

    public Car updateCar(Long id, Car updatedCar) {
        Car car = getCarById(id);
        car.setName(updatedCar.getName());
        car.setBrand(updatedCar.getBrand());
        car.setPrice(updatedCar.getPrice());
        return carRepository.save(car);
    }

    public void deleteCar(Long id) {
        carRepository.deleteById(id);
    }
}