package com.example.JenkinsCICD.Controller;
import com.example.JenkinsCICD.Entity.Car;
import com.example.JenkinsCICD.Service.CarService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/cars")
public class CarController {

    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @PostMapping
    public Car createCar(@RequestBody Car car) {
        return carService.saveCar(car);
    }

    @GetMapping
    public List<Car> getAllCars() {
        return carService.getAllCars();
    }

    @GetMapping("/{id}")
    public Car getCarById(@PathVariable Long id) {
        return carService.getCarById(id);
    }

    @PutMapping("/{id}")
    public Car updateCar(@PathVariable Long id, @RequestBody Car car) {
        return carService.updateCar(id, car);
    }

    @DeleteMapping("/{id}")
    public String deleteCar(@PathVariable Long id) {
        carService.deleteCar(id);
        return "Car deleted successfully";
    }
}