package com.ENSA.Devoir2.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ENSA.Devoir2.model.daoImpl.OrderDaoImpl;
import com.ENSA.Devoir2.service.JsonFileCreator;
import com.ENSA.Devoir2.service.Order;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


public class AutomateStoring extends Thread {
	public String getFileContent(String path) throws IOException {
		StringBuilder content = new StringBuilder();
		BufferedReader InputReader = new BufferedReader(new FileReader(path));
		String line;
		int linesNumber = 0;
		while((line = InputReader.readLine()) != null) {
			content.append(line);
			content.append(System.lineSeparator());
			linesNumber++;
		}
		if(linesNumber == 0) {
	    	List<Order> Data = new ArrayList<>();
	        ObjectMapper objectMapper = new ObjectMapper();
	        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
	        objectMapper.writeValue(new File(path), Data);
			return "[ ]";
		} 
		return content.toString();
	}
	public List<Order> getOrderList(String path) throws IOException {
		Gson gson = new Gson();
		AutomateStoring AS = new AutomateStoring();
		String FileContent = AS.getFileContent(path);
		List<Order> ordersList = gson.fromJson(FileContent, new TypeToken<List<Order>>() {}.getType());
		return ordersList;
	}
	public void run() {
		AutomateStoring AS = new AutomateStoring();
		JsonFileCreator jfc = new JsonFileCreator();
		String path = "src/main/java/com/ENSA/Devoir2/data/input.json";
		OrderDaoImpl od = new OrderDaoImpl();
		while(true) {
			try {
				List<Order> orderList = AS.getOrderList(path);
				int orderListSize = orderList.size();
				if(orderListSize==0) {
					System.out.println("The \"input.json\" file is empty. Run the JsonFileCreator class or fill it manually. Changes will be checked in 1 hour.");
				}
				for (int i=0; i<orderListSize; i++) {
					od.checkOrderAction(orderList.get(0));
					orderList.remove(0);
			    	jfc.filesCreator("src/main/java/com/ENSA/Devoir2/data/", "input.json", orderList);
				}
				} catch(IOException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			try {
				Thread.sleep(60000*60); //60s(1min)*60
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
