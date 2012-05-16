/*
 * Copyright @ 2011 OneEmpower Pte Ltd. All rights reserved.
 * 
 * Revision History:
 * =======================================================================================
 * Date          Version   Author            Description
 * =======================================================================================
 * 8 SEP 2011   	1.0      Wei Zheng    	  1 Initial creation
 * ---------------------------------------------------------------------------------------
 */
package com.abnote.util.oe.beans;

import java.io.Serializable;

import org.json.JSONObject;

public class BalancePoolBean implements Serializable{
	
	private static final long serialVersionUID = -2858808532071069521L;
	private String poolId;
	private String desc;
	private String balance;
	private String balanceValue;
	private String unitValue;
	private String expDate;
	private String poolType;
	/**
	 * @return the poolID
	 */
	public String getPoolId() {
		return poolId;
	}
	/**
	 * @param poolID the poolID to set
	 */
	public void setPoolId(String poolId) {
		this.poolId = poolId;
	}
	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}
	/**
	 * @param desc the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}
	/**
	 * @return the balance
	 */
	public String getBalance() {
		return balance;
	}
	/**
	 * @param balance the balance to set
	 */
	public void setBalance(String balance) {
		this.balance = balance;
	}
	/**
	 * @return the amount
	 */
	public String getBalanceValue() {
		return balanceValue;
	}
	/**
	 * @param amount the amount to set
	 */
	public void setBalanceValue(String amount) {
		this.balanceValue = amount;
	}
	/**
	 * @return the unitAmt
	 */
	public String getUnitValue() {
		return unitValue;
	}
	/**
	 * @param unitAmt the unitAmt to set
	 */
	public void setUnitValue(String unitAmt) {
		this.unitValue = unitAmt;
	}
	/**
	 * @return the expDate
	 */
	public String getExpDate() {
		return expDate;
	}
	/**
	 * @param expDate the expDate to set
	 */
	public void setExpDate(String expDate) {
		this.expDate = expDate;
	}
	/**
	 * @return the poolType
	 */
	public String getPoolType() {
		return poolType;
	}
	/**
	 * @param poolType the poolType to set
	 */
	public void setPoolType(String poolType) {
		this.poolType = poolType;
	}
	public BalancePoolBean map(JSONObject json){
		BalancePoolBean bean=null;
		if(json!=null){
			bean=new BalancePoolBean();
			bean.setBalance(json.optString("balance",""));
			bean.setBalanceValue(json.optString("balanceValue", ""));
			bean.setDesc(json.optString("desc",""));
			bean.setExpDate(json.optString("expDate", ""));
			bean.setPoolId(json.optString("poolId", ""));
			bean.setPoolType(json.optString("poolType", ""));
			bean.setUnitValue(json.optString("unitValue", ""));
		}
		return bean;
	}
}
