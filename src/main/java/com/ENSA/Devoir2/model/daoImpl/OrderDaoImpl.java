package com.ENSA.Devoir2.model.daoImpl;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.ENSA.Devoir2.controller.AutomateStoring;
import com.ENSA.Devoir2.model.DB;
import com.ENSA.Devoir2.model.dao.OrderDao;
import com.ENSA.Devoir2.service.Order;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class OrderDaoImpl implements OrderDao{
	private Connection connection;
	public boolean addOrder(Order order) throws SQLException {
		DB db = new DB();
		db.conn("devoir2", "root", "");
		connection = db.getConnection();
		String sqlInsert =  "INSERT INTO order_(date, amount, customer_id) VALUES (?,?,?)";
		PreparedStatement pst = null;
		pst = connection.prepareStatement(sqlInsert);
		pst.setString(1, order.getDate());
		pst.setDouble(2, order.getAmount());
		pst.setInt(3, order.getCustomer_id());
		boolean state = pst.execute();
		if(state == true) {
			System.out.println("the order failed to be stored in the DB, try another time later");
		} else {
			System.out.println("the order added successfuly to the database");
		}
		return ! state;
	}
	public boolean checkOrder(Order order) throws SQLException {
		DB db = new DB();
		db.conn("devoir2", "root", "");
		connection = db.getConnection();
		String sqlQuery =  "SELECT * FROM customer WHERE id=?";
		PreparedStatement pst = null;
		pst = connection.prepareStatement(sqlQuery);
		pst.setInt(1, order.getCustomer_id());
		ResultSet resultSet = pst.executeQuery();
		int findCounter = 0;
		while(resultSet.next()) {
			findCounter++;
			break;
		}
		if(findCounter==1) {
//			System.out.println("found");
			return true;
		} else if(findCounter==0){
//			System.out.println("Not found");
			return false;
		} else {
			System.out.println("Error");
			return false;
		}
		
	}
	public void checkOrderAction(Order order) throws SQLException {
		DB db = new DB();
		db.conn("devoir2", "root", "");
		connection = db.getConnection();
		AutomateStoring AS = new AutomateStoring();
		boolean checkOrderResult = checkOrder(order);
		String directoryPath = "src/main/java/com/ENSA/Devoir2/data/";
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		if(checkOrderResult == true) {
			System.out.print("The customer who made this order exist in the DB: ");
			try {
		        boolean addState = addOrder(order);
		        if(addState == true) {
		        	List<Order> dataSucc = AS.getOrderList(directoryPath + "output.json");
		        	dataSucc.add(order);
		        	objectMapper.writeValue(new File(directoryPath + "output.json"), dataSucc);
		        } else {
		        	List<Order> dataFail = AS.getOrderList(directoryPath + "error.json");
		        	dataFail.add(order);
					objectMapper.writeValue(new File(directoryPath + "error.json"), dataFail);
		        }
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if(checkOrderResult == false) {
			System.out.println("The customer who made this order doesn't exist in the DB: the order added to the \"error.json\" file");
			try {
				List<Order> Data = AS.getOrderList(directoryPath + "error.json");
		        Data.add(order);
				objectMapper.writeValue(new File(directoryPath + "error.json"), Data);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			System.out.println("Error");
		}
		
	}
}
