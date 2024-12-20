package com.ENSA.Devoir2.service;
import com.ENSA.Devoir2.controller.AutomateStoring;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JsonFileCreator {
	
	public void filesCreator(String directoryPath, String fileName, List<Order> Data){
		// Directory for the files
//        String directoryPath = "src/main/java/com/ENSA/Devoir2/data/";
        File directory = new File(directoryPath);
        // Create the directory if it doesn't exist
        if (!directory.exists()) {
            directory.mkdirs();
        }
        // Write the JSON files
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // For pretty printing

        try {
            // Write input.json
            objectMapper.writeValue(new File(directoryPath + fileName), Data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	public void Reset() {
		JsonFileCreator jfc = new JsonFileCreator();
    	String directoryPath = "src/main/java/com/ENSA/Devoir2/data/";
    	List<Order> Data = new ArrayList<>();
        jfc.filesCreator(directoryPath, "input.json", Data);
    	jfc.filesCreator(directoryPath, "output.json", Data);
        jfc.filesCreator(directoryPath, "error.json", Data);
	}
	public void AddToInputFile(List<Order> orders) throws IOException {
		AutomateStoring AS = new AutomateStoring();
		JsonFileCreator jfc = new JsonFileCreator();
    	List<Order> inputData = AS.getOrderList("src/main/java/com/ENSA/Devoir2/data/input.json");
    	for(Order order:orders) {
    		inputData.add(order);
    	}
        jfc.filesCreator("src/main/java/com/ENSA/Devoir2/data/", "input.json", inputData);
	}
    public static void main(String[] args) {
    	List<Order> inputData = new ArrayList<>();
        long currentTimeMillis = System.currentTimeMillis();
        Date currentDate = new Date(currentTimeMillis);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = formatter.format(currentDate);
    	inputData.add(new Order(1, formattedDate, 100.0, 1));
        inputData.add(new Order(2, formattedDate, 200.0, 2));
        inputData.add(new Order(3, formattedDate, 150.0, 3));
        inputData.add(new Order(4, formattedDate, 900.0, 4));
        inputData.add(new Order(5, formattedDate, 500.0, 5));
        inputData.add(new Order(6, formattedDate, 600.0, 6));
        inputData.add(new Order(7, formattedDate, 100.0, 7));
        inputData.add(new Order(8, formattedDate, 200.0, 8));
        inputData.add(new Order(9, formattedDate, 300.0, 9));
        inputData.add(new Order(10, formattedDate, 800.0, 10));
        JsonFileCreator jfc = new JsonFileCreator();
        try {
//        	jfc.Reset();
			jfc.AddToInputFile(inputData);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
