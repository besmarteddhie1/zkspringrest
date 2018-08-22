package com.rabia.ui.controller;

import java.util.List;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.event.SerializableEventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.event.PagingEvent;

import com.rabia.model.Car;
import com.rabia.service.CarService;

@VariableResolver(DelegatingVariableResolver.class)
public class CarController extends SelectorComposer<Window> {

	private static final long serialVersionUID = 1L;
	
	@WireVariable(rewireOnActivate = true)
	private CarService carService;
	@SuppressWarnings("unused")
	@Wire
	private Window windowSearchCar;
	@Wire
	private Textbox keywordBox;
	@Wire
	private Grid carGrid;
	@Wire
	private Textbox textboxModel;
	@Wire
	private Textbox textboxMake;
	@Wire
	private Textbox textboxPrice;
	@Wire
	private Textbox textboxDescription;
	@Wire
	private Paging carPaging;
	
	private ListModelList<Car> carModelList = new ListModelList<Car>();
	private Car car;

	protected int CURRENT_PAGE_NUMBER = 0;
	private static int PAGE_SIZE = 5;
	private static int DEFAULT_PAGE_NUMBER = 0;
	
	public void doAfterCompose(Window window) throws Exception {
		super.doAfterCompose(window);
		carGrid.setModel(carModelList);
		initGridPaging();
		cleanDescription();
		refreshGridData(DEFAULT_PAGE_NUMBER);
	}

	private void initGridPaging() {
		carPaging.setPageSize(PAGE_SIZE);
		carPaging.setDetailed(true);
		SerializableEventListener<Event> pagingEventListener = new SerializableEventListener<Event>() {
			private static final long serialVersionUID = 9202931699920227444L;

			@Override
			public void onEvent(Event event) throws Exception {
				if (event instanceof PagingEvent) {
					PagingEvent pe = (PagingEvent) event;
					CURRENT_PAGE_NUMBER  = pe.getActivePage();
					refreshGridData(CURRENT_PAGE_NUMBER);
				}
			}
		};
		carPaging.addEventListener("onPaging", pagingEventListener);
	}

	protected void refreshGridData(int pageNumber) {
		String keyword = "";
		if (keywordBox.getValue() != null){
			keyword = keywordBox.getValue().toLowerCase();
		}
		 	
		List<Car> cars = carService.search(keyword, pageNumber*PAGE_SIZE, PAGE_SIZE);
		carModelList.clear();
		carModelList.addAll(cars);
		int totalCars = carService.countCarByKeyword(keyword);

		carPaging.setTotalSize((int) totalCars);
		carPaging.setActivePage(pageNumber);
	}

	@Listen("onClick = #searchButton")
	public void search() {
		refreshGridData(DEFAULT_PAGE_NUMBER);
	}

	@Listen("onClick = #clearButton")
	public void clear() {
		keywordBox.setValue(null);
		refreshGridData(DEFAULT_PAGE_NUMBER);
	}

	@Listen("onButtonViewClick=#windowSearchCar")
	public void showDetail(ForwardEvent event) {
		car = (Car) event.getData();
		bindingObject();
	}
	
	@Listen("onButtonDeleteClick=#windowSearchCar")
	public void deleteCar(ForwardEvent event) {
		final Car selected = (Car) event.getData();
		carService.deleteCar(selected);
		refreshGridData(DEFAULT_PAGE_NUMBER);
		cleanDescription();
	}
	
	
	@Listen("onClick = #saveButton")
	public void save() {
		boolean updateMode = true;
		if (car == null) {
			car = new Car();
			car.setId(null);
			updateMode = false;
		} 
		car.setModel(textboxModel.getValue());
		car.setMake(textboxMake.getValue());
		car.setPrice(Integer.valueOf(textboxPrice.getValue()));
		car.setDescription(textboxDescription.getValue());
		
		if (updateMode) {
			carService.updateCar(car);
		} else {
			carService.save(car);
		}
		refreshGridData(DEFAULT_PAGE_NUMBER);
		cleanDescription();
	}
	
	public void cleanDescription() {
		textboxModel.setValue(null);
		textboxMake.setValue(null);
		textboxPrice.setValue(null);
		textboxDescription.setValue(null);
		car = null;
	}
	
	public void bindingObject() {
		textboxModel.setValue(car.getModel());
		textboxMake.setValue(car.getMake());
		textboxPrice.setValue(car.getPrice().toString());
		textboxDescription.setValue(car.getDescription());
	}
}
