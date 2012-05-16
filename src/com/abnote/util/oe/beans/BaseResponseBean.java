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

/**
 * Base Bean for other response bean
 *
 */
public class BaseResponseBean implements Serializable{

	private static final long serialVersionUID = -2735198141479196612L;	
	private String responseCode;
	private String responseMsg;
	private String versionNo;
	private String date;
	private String time;
	private String timezone;
	private String operatorID;
	private String subscriberID;
	private String MID;
	private String TID;
	public BaseResponseBean(){
		
	}
	public BaseResponseBean(String code){
		this.responseCode=code;
	}
	public BaseResponseBean(String code,String message){
		this.responseCode=code;
		this.responseMsg=message;
	}
	/**
	 * @return the responseCode
	 */
	public String getResponseCode() {
		return responseCode;
	}
	/**
	 * @param responseCode the responseCode to set
	 */
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	/**
	 * @return the responseMsg
	 */
	public String getResponseMsg() {
		return responseMsg;
	}
	/**
	 * @param responseMsg the responseMsg to set
	 */
	public void setResponseMsg(String responseMsg) {
		this.responseMsg = responseMsg;
	}
	/**
	 * @return the versionNo
	 */
	public String getVersionNo() {
		return versionNo;
	}
	/**
	 * @param versionNo the versionNo to set
	 */
	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}
	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}
	/**
	 * @return the time
	 */
	public String getTime() {
		return time;
	}
	/**
	 * @param time the time to set
	 */
	public void setTime(String time) {
		this.time = time;
	}
	/**
	 * @return the timezone
	 */
	public String getTimezone() {
		return timezone;
	}
	/**
	 * @param timezone the timezone to set
	 */
	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}
	/**
	 * @return the operatorID
	 */
	public String getOperatorID() {
		return operatorID;
	}
	/**
	 * @param operatorID the operatorID to set
	 */
	public void setOperatorID(String operatorID) {
		this.operatorID = operatorID;
	}
	/**
	 * @return the subscriberID
	 */
	public String getSubscriberID() {
		return subscriberID;
	}
	/**
	 * @param subscriberID the subscriberID to set
	 */
	public void setSubscriberID(String subscriberID) {
		this.subscriberID = subscriberID;
	}
	/**
	 * @return the mID
	 */
	public String getMID() {
		return MID;
	}
	/**
	 * @param mID the mID to set
	 */
	public void setMID(String mID) {
		MID = mID;
	}
	/**
	 * @return the tID
	 */
	public String getTID() {
		return TID;
	}
	/**
	 * @param tID the tID to set
	 */
	public void setTID(String tID) {
		TID = tID;
	}
	public BaseResponseBean map(JSONObject json){
		BaseResponseBean bean=null;
		if(json!=null){
			bean=new BaseResponseBean();
			bean.setResponseCode(json.optString("responseCode", "999"));
			bean.setResponseMsg(json.optString("responseMsg", "None"));
		}
		return bean;
	}
}
