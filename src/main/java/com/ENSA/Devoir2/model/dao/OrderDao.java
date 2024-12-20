package com.ENSA.Devoir2.model.dao;

import java.sql.SQLException;

import com.ENSA.Devoir2.service.Order;

public interface OrderDao {
	public boolean addOrder(Order order) throws SQLException;
	public boolean checkOrder(Order order) throws SQLException;
	public void checkOrderAction(Order order) throws SQLException;
}
