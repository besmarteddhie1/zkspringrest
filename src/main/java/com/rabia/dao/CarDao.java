package com.rabia.dao;

import java.util.List;

import com.rabia.model.Car;

public interface CarDao {

    public List<Car> findAll();
   
    public List<Car> search(String keyword);

	public void deleteCar(Car selected);

	public Integer save(Car car);
	
	public void update(Car car);

	public List<Car> search(String keyword, int offset, int limit);
	
	public Integer countCarByKeyword(String keyword);
}


