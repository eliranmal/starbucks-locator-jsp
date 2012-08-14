package com.starbucks.locator.model.dataaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import managers.BankBalanceManager;
import systemCore.BankException;
import valueObjects.BalanceAction;

public class LocationsDBManager implements LocationsManager {

	private String getBalancePS;
	private String addBalancePS;

	public LocationsDBManager() {
		getBalancePS = "SELECT * FROM bank_balance WHERE balance_id = ?";
		addBalancePS = "INSERT INTO bank_balance (action, amount, [date], cust_id) VALUES"
			+ " (?,?,?,?)";
	}

	public Locations getBankBalance(int balanceId, Connection con) throws StarbucksLocatorException {
		PreparedStatement ps;
		ResultSet rs;
		Locations b = null;
		try {
			ps = con.prepareStatement(getBalancePS);
			ps.setInt(1, balanceId);
			rs = ps.executeQuery();
			if (rs.next()) {
				b = new Locations(rs.getInt("balance_id"), rs.getString("action"), rs
					.getDouble("amount"), rs.getDate("date"), rs.getInt("cust_id"));
			}
		} catch (SQLException e) {
		}
		if (b == null) {
			throw new StarbucksLocatorException("Balance record with ID '" + balanceId + "' not found.");
		} else {
			return b;
		}
	}

	public void addBankBalance(Locations b, Connection con) {
		PreparedStatement ps;
		try {
			ps = con.prepareStatement(addBalancePS);
			ps.setString(1, b.getAction());
			ps.setDouble(2, b.getAmount());
			ps.setDate(3, b.getDate());
			ps.setInt(4, b.getCustId());
			ps.executeUpdate();
		} catch (SQLException e) {
		}
	}
}
