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

import org.json.JSONObject;

public class SalePoolBean extends BalancePoolBean{
	private static final long serialVersionUID = 6551538592234093814L;	
	private String openBal;
	private String openBalValue;
	private String awardQty;
	private String awardQtyValue;
	private String redeemQty;
	private String redeemQtyValue;
	private String balance;
	private String balanceValue;

	/**
	 * @return the openBal
	 */
	public String getOpenBal() {
		return openBal;
	}
	/**
	 * @param openBal the openBal to set
	 */
	public void setOpenBal(String openBal) {
		this.openBal = openBal;
	}
	/**
	 * @return the openBalValue
	 */
	public String getOpenBalValue() {
		return openBalValue;
	}
	/**
	 * @param openBalValue the openBalValue to set
	 */
	public void setOpenBalValue(String openBalValue) {
		this.openBalValue = openBalValue;
	}
	/**
	 * @return the awardQty
	 */
	public String getAwardQty() {
		return awardQty;
	}
	/**
	 * @param awardQty the awardQty to set
	 */
	public void setAwardQty(String awardQty) {
		this.awardQty = awardQty;
	}
	/**
	 * @return the awardQtyValue
	 */
	public String getAwardQtyValue() {
		return awardQtyValue;
	}
	/**
	 * @param awardQtyValue the awardQtyValue to set
	 */
	public void setAwardQtyValue(String awardQtyValue) {
		this.awardQtyValue = awardQtyValue;
	}
	/**
	 * @return the redeemQty
	 */
	public String getRedeemQty() {
		return redeemQty;
	}
	/**
	 * @param redeemQty the redeemQty to set
	 */
	public void setRedeemQty(String redeemQty) {
		this.redeemQty = redeemQty;
	}
	/**
	 * @return the redeemQtyValue
	 */
	public String getRedeemQtyValue() {
		return redeemQtyValue;
	}
	/**
	 * @param redeemQtyValue the redeemQtyValue to set
	 */
	public void setRedeemQtyValue(String redeemQtyValue) {
		this.redeemQtyValue = redeemQtyValue;
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
	 * @return the balanceValue
	 */
	public String getBalanceValue() {
		return balanceValue;
	}
	/**
	 * @param balanceValue the balanceValue to set
	 */
	public void setBalanceValue(String balanceValue) {
		this.balanceValue = balanceValue;
	}
	public SalePoolBean map(JSONObject json){
		SalePoolBean bean=null;
		if(json!=null){
			bean=new SalePoolBean();
			bean.setAwardQty(json.optString("awardQty", ""));
			bean.setAwardQtyValue(json.optString("awardQtyValue", ""));
			bean.setBalance(json.optString("balance", ""));
			bean.setDesc(json.optString("desc", ""));
			bean.setOpenBal(json.optString("openBal", ""));
			bean.setRedeemQty(json.optString("redeemQty", ""));
			bean.setRedeemQtyValue(json.optString("redeemQtyValue", ""));
			bean.setBalanceValue(json.optString("balanceValue",""));
			bean.setPoolType(json.optString("poolType",""));
		}
		return bean;
	}
}
