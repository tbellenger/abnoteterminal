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

public class LoadAmountValueBean implements Serializable{
	private static final long serialVersionUID = 7787247515460254031L;
	private String loadAmountID;
	private String loadAmountValue;
	/**
	 * @return the amountID
	 */
	public String getLoadAmountID() {
		return loadAmountID;
	}
	/**
	 * @param amountID the amountID to set
	 */
	public void setLoadAmountID(String amountID) {
		this.loadAmountID = amountID;
	}
	/**
	 * @return the amountValue
	 */
	public String getLoadAmountValue() {
		return loadAmountValue;
	}
	/**
	 * @param amountValue the amountValue to set
	 */
	public void setLoadAmountValue(String amountValue) {
		this.loadAmountValue = amountValue;
	}
	public LoadAmountValueBean map(JSONObject json){
		LoadAmountValueBean bean =null;
		if (json!=null) {
			bean = new LoadAmountValueBean();
			bean.setLoadAmountID(json.optString("loadAmountID", ""));
			bean.setLoadAmountValue(json.optString("loadAmountValue", ""));
		}
		return bean;
	}
}
