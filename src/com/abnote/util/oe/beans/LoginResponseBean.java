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

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

public class LoginResponseBean extends BaseResponseBean {
	
	private static final long serialVersionUID = -5609999558106364937L;
	private String totalUnreadMsg;
	private String totalMsg;
	private String accessRightsID;
	private String accessRights;
	private String accessToken;	
	private ArrayList<LoadValuePool> loadPool;
	private String merchantName;
	private boolean isOffLineMode;
	private boolean isTabCard;
	private String currency;
	private HashMap<String,Integer> blockMap;
	private boolean printMerchantCopy;
	private boolean hideBalance;
	private boolean showAwardInCatalogReceipt;
	/**
	 * @return the totalUnreadMsg
	 */
	public String getTotalUnreadMsg() {
		return totalUnreadMsg;
	}
	/**
	 * @param totalUnreadMsg the totalUnreadMsg to set
	 */
	public void setTotalUnreadMsg(String totalUnreadMsg) {
		this.totalUnreadMsg = totalUnreadMsg;
	}
	/**
	 * @return the totalMsg
	 */
	public String getTotalMsg() {
		return totalMsg;
	}
	/**
	 * @param totalMsg the totalMsg to set
	 */
	public void setTotalMsg(String totalMsg) {
		this.totalMsg = totalMsg;
	}
	/**
	 * @return the accessRightsID
	 */
	public String getAccessRightsID() {
		return accessRightsID;
	}
	/**
	 * @param accessRightsID the accessRightsID to set
	 */
	public void setAccessRightsID(String accessRightsID) {
		this.accessRightsID = accessRightsID;
	}
	/**
	 * @return the accessRights
	 */
	public String getAccessRights() {
		return accessRights;
	}
	/**
	 * @param accessRights the accessRights to set
	 */
	public void setAccessRights(String accessRights) {
		this.accessRights = accessRights;
	}
	/**
	 * @return the accessToken
	 */
	public String getAccessToken() {
		return accessToken;
	}
	/**
	 * @param accessToken the accessToken to set
	 */
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	/**
	 * @return the loadPool
	 */
	public ArrayList<LoadValuePool> getLoadPool() {
//		ArrayList<LoadValuePool> x=new ArrayList<LoadValuePool>();
//		LoadValuePool a=new LoadValuePool();
//		a.setLoadPoolID("PTS");
//		ArrayList<LoadAmountValueBean> b=new ArrayList<LoadAmountValueBean>();
//		LoadAmountValueBean c=new LoadAmountValueBean();
//		c.setLoadAmountID("1");
//		c.setLoadAmountValue("-9");
//		LoadAmountValueBean d=new LoadAmountValueBean();
//		d.setLoadAmountID("2");
//		d.setLoadAmountValue("10");
//		b.add(c);
//		b.add(d);
//		a.setLoadAmount(b);
//		x.add(a);
//		return x;
		return loadPool;
	}
	/**
	 * @param loadPool the loadPool to set
	 */
	public void setLoadPool(ArrayList<LoadValuePool> loadPool) {
		this.loadPool = loadPool;
	}
	/**
	 * @return the merchantName
	 */
	public String getMerchantName() {
		return merchantName;
	}
	/**
	 * @param merchantName the merchantName to set
	 */
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	/**
	 * @return the isOffLineMode
	 */
	public boolean isOffLineMode() {
		return isOffLineMode;
	}
	/**
	 * @param isOffLineMode the isOffLineMode to set
	 */
	public void setOffLineMode(boolean isOffLineMode) {
		this.isOffLineMode = isOffLineMode;
	}
	/**
	 * @return the isTabCard
	 */
	public boolean isTabCard() {
		return isTabCard;
	}
	/**
	 * @param isTabCard the isTabCard to set
	 */
	public void setTabCard(boolean isTabCard) {
		this.isTabCard = isTabCard;
	}
	/**
	 * @return the currency
	 */
	public String getCurrency() {
		return currency;
	}
	/**
	 * @param currency the currency to set
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	/**
	 * @return the blockMap
	 */
	public HashMap<String, Integer> getBlockMap() {
		return blockMap;
	}
	/**
	 * @param blockMap the blockMap to set
	 */
	public void setBlockMap(HashMap<String, Integer> blockMap) {
		this.blockMap = blockMap;
	}
	/**
	 * @return the printMerchantCopy
	 */
	public boolean isPrintMerchantCopy() {
		return printMerchantCopy;
	}
	/**
	 * @param printMerchantCopy the printMerchantCopy to set
	 */
	public void setPrintMerchantCopy(boolean printMerchantCopy) {
		this.printMerchantCopy = printMerchantCopy;
	}
	public boolean isHideBalance() {
		return hideBalance;
	}
	public void setHideBalance(boolean hideBalance) {
		this.hideBalance = hideBalance;
	}
	public boolean isShowAwardInCatalogReceipt() {
		return showAwardInCatalogReceipt;
	}
	public void setShowAwardInCatalogReceipt(boolean showAwardInCatalogReceipt) {
		this.showAwardInCatalogReceipt = showAwardInCatalogReceipt;
	}
	public LoginResponseBean map(JSONObject json){
		LoginResponseBean bean=new LoginResponseBean();
		bean.setAccessRights(json.optString("accessRights",""));
		bean.setAccessToken(json.optString("accessToken", ""));
		bean.setMerchantName(json.optString("merchantName", ""));
		bean.setMID(json.optString("MID",""));
		bean.setOperatorID(json.optString("operatorID",""));
		bean.setResponseCode(json.optString("responseCode", ""));
		bean.setSubscriberID(json.optString("subscriberID", ""));
		bean.setTID(json.optString("TID", ""));
		bean.setTotalMsg(json.optString("totalMsg", "0"));
		bean.setCurrency(json.optString("currency",""));
		bean.setTotalUnreadMsg(json.optString("totalUnreadMsg","0"));
		bean.setResponseMsg(json.optString("responseMsg","None"));
	    bean.setResponseCode(json.optString("responseCode","999"));
		ArrayList<LoadValuePool> loadPool=new ArrayList<LoadValuePool>();
		JSONArray ja=json.optJSONArray("loadPool");
		if(ja!=null&&ja.length()>0){
			for(int i=0;i<ja.length();i++){
				LoadValuePool lvp=new LoadValuePool().map(ja.optJSONObject(i));
				if(lvp!=null){
					loadPool.add(lvp);
				}
			}
			if(loadPool!=null&&loadPool.size()>0){
				bean.setLoadPool(loadPool);
			}
		}	
		JSONArray jb=json.optJSONArray("pool");
		HashMap<String,Integer> bMap=new HashMap<String,Integer>();
		if(jb!=null&&jb.length()>0){			
			for(int i=0;i<jb.length();i++){
				JSONObject j=jb.optJSONObject(i);
				if(j!=null){
					String poolID=j.optString("poolID");
					if(poolID!=null){
						bMap.put(poolID, j.optInt("blockPoint"));
					}
				}
			}						
		}	
		bean.setBlockMap(bMap);
		return bean;		
	}
}
