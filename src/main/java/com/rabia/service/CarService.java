package com.rabia.service;

import java.util.List;

import com.rabia.model.Car;

public interface CarService {
	
	List<Car> findAll ();   
    List<Car> search (String keyword);
    List<Car> search (String keyword, int offset, int limit);
	void deleteCar (Car car);
	Integer save (Car car);
	void updateCar (Car car);
	Integer countCarByKeyword (String keyword);
}
