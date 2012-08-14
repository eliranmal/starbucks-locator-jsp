package com.starbucks.locator.model.dto;

import java.sql.Date;
import systemCore.MBank;
import utility.ActionTypes;

public class Locations {

	private int balanceId;
	private String action;
	private double amount;
	private Date date;
	private int custId;
	private char amountPrefix;

	public Locations(String action, double amount, int custId) {
		this.action = action;
		this.amount = amount;
		this.date = new Date(System.currentTimeMillis());
		this.custId = custId;
		this.amountPrefix = ActionTypes.getPrefixByActionType(action);
		MBank.getBank().updateBankAmount(amount, amountPrefix);
	}

	public Locations(int balanceId, String action, double amount, Date date, int custId) {
		this.balanceId = balanceId;
		this.action = action;
		this.amount = amount;
		this.date = date;
		this.custId = custId;
		this.amountPrefix = ActionTypes.getPrefixByActionType(action);
	}

	public int getBalanceId() {
		return balanceId;
	}

	public String getAction() {
		return action;
	}

	public double getAmount() {
		return amount;
	}

	public Date getDate() {
		return date;
	}

	public int getCustId() {
		return custId;
	}

	public void setBalanceId(int balanceId) {
		this.balanceId = balanceId;
	}

	public char getAmountPrefix() {
		return amountPrefix;
	}

	public String toString() {
		return "BalanceAction [action=" + action + ", amount=" + amount + ", balanceId="
			+ balanceId + ", custId=" + custId + ", date=" + date + ", amountPrefix="
			+ amountPrefix + "]";
	}
}
