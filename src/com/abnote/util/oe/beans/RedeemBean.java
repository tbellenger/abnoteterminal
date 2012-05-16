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

public class RedeemBean implements Serializable{

	private static final long serialVersionUID = -5485075716157483951L;
	private String redeemPoolID;
	private String redeemQty;
	public RedeemBean(){
		
	}
	public RedeemBean(String id,String string){
		this.redeemPoolID=id;
		this.redeemQty=string;
	}
	/**
	 * @return the redeemPoolID
	 */
	public String getRedeemPoolID() {
		return redeemPoolID;
	}
	/**
	 * @param redeemPoolID the redeemPoolID to set
	 */
	public void setRedeemPoolID(String redeemPoolID) {
		this.redeemPoolID = redeemPoolID;
	}
	/**
	 * @return the redeemQTY
	 */
	public String getRedeemQty() {
		return redeemQty;
	}
	/**
	 * @param redeemQTY the redeemQTY to set
	 */
	public void setRedeemQty(String redeemQTY) {
		this.redeemQty= redeemQTY;
		
	}

}
