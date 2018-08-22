package com.rabia.rest;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.rabia.model.Car;
import com.rabia.service.CarService;

@Component
@Path("/car")
public class CarRestful {
	
	@Autowired
	private CarService carService;
	
	@GET
	@Path("/findAll")
	@Produces(MediaType.APPLICATION_JSON)
	public String findAll () {
		String result = "";
		List<Car> cars = carService.findAll();
		
		if (!cars.isEmpty())
			result = new Gson().toJson(cars);
		
		return  result;
	}
	
	@GET
	@Path("/find/{keyword}")
	@Produces(MediaType.APPLICATION_JSON)
	public String findAll (@PathParam("keyword") String keyword) {
		String result = "";
		List<Car> cars = carService.search(keyword);
		
		if (!cars.isEmpty())
			result = new Gson().toJson(cars);
		
		return  result;
	}
	
	@POST
	@Path("/add")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addCar(String carJson) {
		Response response = Response.status(Status.BAD_REQUEST).build();
		Gson gson = new Gson();
		Car car = gson.fromJson(carJson, Car.class);
		int affected = carService.save(car);
		if (affected > 0) {
			response = Response.status(Status.ACCEPTED).build();
		} 
		
		return response;
	}
	
	@POST
	@Path("/add/form")
	public Response addCarForm(@FormParam("model") String model,
			@FormParam("make") String make, @FormParam("description") String description, @FormParam("price") Integer price) throws IOException {
		Response response = Response.status(Status.BAD_REQUEST).build();
		Car car = new Car();
		car.setModel(model);
		car.setMake(make);
		car.setDescription(description);
		car.setPrice(price);
		int affected = carService.save(car);
		if (affected > 0) {
			response = Response.status(Status.ACCEPTED).build();
		} 
		
		return response;
	}
}
