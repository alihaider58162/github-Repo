package com.example.JenkinsCICD;

import com.example.JenkinsCICD.Entity.Car;
import com.example.JenkinsCICD.Repository.CarRepository;
import com.example.JenkinsCICD.Service.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarServiceTest {

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarService carService;

    private Car car;

    @BeforeEach
    void setUp() {
        car = new Car();
        //    car.setId(1L);
        car.setName("Mehran");
        car.setBrand("Suzuki");
        car.setPrice(20000.0);
    }

    @Test
    void testSaveCar() {
        // Arrange
        when(carRepository.save(any(Car.class))).thenReturn(car);

        // Act
        Car savedCar = carService.saveCar(car);

        // Assert
        assertNotNull(savedCar);
        assertEquals("Mehran", savedCar.getName());
        assertEquals("Suzuki", savedCar.getBrand());
        assertEquals(20000.0, savedCar.getPrice());
        verify(carRepository, times(1)).save(car);
    }

    @Test
    void testGetAllCars() {
        // Arrange
        List<Car> cars = Arrays.asList(car);
        when(carRepository.findAll()).thenReturn(cars);

        // Act
        List<Car> result = carService.getAllCars();

        // Assert
        assertEquals(1, result.size());
        assertEquals("Mehran", result.get(0).getName());
        verify(carRepository, times(1)).findAll();
    }

    @Test
    void testGetCarById_Success() {
        // Arrange
        Car mockCar = new Car();
        mockCar.setId(1L);
        mockCar.setName("Civic");
        mockCar.setBrand("Honda");
        mockCar.setPrice(20000.0);

        when(carRepository.findById(1L)).thenReturn(Optional.of(mockCar));

        // Act
        Car foundCar = carService.getCarById(1L);

        // Assert
        assertNotNull(foundCar);
        assertEquals(null, foundCar.getId());
        assertEquals("Civic", foundCar.getName());
        assertEquals("Honda", foundCar.getBrand());
        assertEquals(20000.0, foundCar.getPrice());

        verify(carRepository, times(1)).findById(1L);
    }

    @Test
    void testGetCarById_NotFound() {
        // Arrange
        when(carRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            carService.getCarById(99L);
        });
        assertEquals("Car not found", exception.getMessage());
        verify(carRepository, times(1)).findById(99L);
    }

    @Test
    void testUpdateCar() {
        // Arrange
        Car updatedDetails = new Car();
        updatedDetails.setName("Cultus");
        updatedDetails.setBrand("Suzuki");
        updatedDetails.setPrice(35000.0);

        Car updatedCar = new Car();
        //  updatedCar.setId(1L);
        updatedCar.setName("Cultus");
        updatedCar.setBrand("Suzuki");
        updatedCar.setPrice(35000.0);

        when(carRepository.findById(1L)).thenReturn(Optional.of(car));
        when(carRepository.save(any(Car.class))).thenReturn(updatedCar);

        // Act
        Car result = carService.updateCar(1L, updatedDetails);

        // Assert
        assertEquals("Cultus", result.getName());
        assertEquals(35000.0, result.getPrice());
        verify(carRepository, times(1)).findById(1L);
        verify(carRepository, times(1)).save(any(Car.class));
    }

    @Test
    void testDeleteCar() {
        // Arrange
        doNothing().when(carRepository).deleteById(1L);

        // Act
        carService.deleteCar(1L);

        // Assert
        verify(carRepository, times(1)).deleteById(1L);
    }
}