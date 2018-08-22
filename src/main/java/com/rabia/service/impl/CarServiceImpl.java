package com.rabia.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rabia.dao.CarDao;
import com.rabia.model.Car;
import com.rabia.service.CarService;

@Transactional
@Service("carService")
public class CarServiceImpl implements CarService{

	@Autowired
	private CarDao carDao;
	
	@Override
	public List<Car> findAll() {
		return carDao.findAll();
	}

	@Override
	public List<Car> search(String keyword) {
		return carDao.search(keyword);
	}

	@Override
	public void deleteCar(Car selected) {
		carDao.deleteCar(selected);
	}

	@Override
	public Integer save(Car car) {
		Integer result = null;
		if (car.getId() == null) {
			result = carDao.save(car);
		} 
		return result;
	}

	public CarDao getCarDao() {
		return carDao;
	}

	public void setCarDao(CarDao carDao) {
		this.carDao = carDao;
	}

	@Override
	public List<Car> search(String keyword, int offset, int limit) {
		return carDao.search(keyword, offset, limit);
	}

	@Override
	public void updateCar(Car car) {
		carDao.update(car);
	}

	@Override
	public Integer countCarByKeyword(String keyword) {
		return carDao.countCarByKeyword(keyword);
	}

}
