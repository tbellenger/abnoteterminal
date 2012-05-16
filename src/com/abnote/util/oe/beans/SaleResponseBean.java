
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

import org.json.JSONArray;
import org.json.JSONObject;

public class SaleResponseBean extends BaseResponseBean {

	private static final long serialVersionUID = 813722224669730292L;
	private ArrayList<String> marketing;
	private ArrayList<SalePoolBean> pool;
	private String cardNo;
	private String grossAmt;
	private String netAmt;
	private String refNo;
	/**
	 * @return the marketing
	 */
	public ArrayList<String> getMarketing() {
		return marketing;
	}
	/**
	 * @param marketing the marketing to set
	 */
	public void setMarketing(ArrayList<String> marketing) {
		this.marketing = marketing;
	}
	/**
	 * @return the pool
	 */
	public ArrayList<SalePoolBean> getPool() {
		return pool;
	}
	/**
	 * @param pool the pool to set
	 */
	public void setPool(ArrayList<SalePoolBean> pool) {
		this.pool = pool;
	}
	/**
	 * @return the cardNo
	 */
	public String getCardNo() {
		return cardNo;
	}
	/**
	 * @param cardNo the cardNo to set
	 */
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	/**
	 * @return the grossAmt
	 */
	public String getGrossAmt() {
		return grossAmt;
	}
	/**
	 * @param grossAmt the grossAmt to set
	 */
	public void setGrossAmt(String grossAmt) {
		this.grossAmt = grossAmt;
	}
	/**
	 * @return the netAmt
	 */
	public String getNetAmt() {
		return netAmt;
	}
	/**
	 * @param netAmt the netAmt to set
	 */
	public void setNetAmt(String netAmt) {
		this.netAmt = netAmt;
	}
	/**
	 * @return the refNo
	 */
	public String getRefNo() {
		return refNo;
	}
	/**
	 * @param refNo the refNo to set
	 */
	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}
	public SaleResponseBean map(JSONObject json){
		SaleResponseBean bean=null;
		if(json!=null){
			bean=new SaleResponseBean();
			bean.setCardNo(json.optString("cardNo",""));
			bean.setDate(json.optString("date", ""));
			bean.setGrossAmt(json.optString("grossAmt", ""));
			bean.setNetAmt(json.optString("netAmt",""));
			bean.setRefNo(json.optString("refNo", ""));
			bean.setResponseMsg(json.optString("responseMsg","None"));
			bean.setResponseCode(json.optString("responseCode", "999"));
			bean.setTime(json.optString("time", ""));
			JSONArray poolRows=json.optJSONArray("pool");
			ArrayList<SalePoolBean> salePool=null;
			if(poolRows!=null&&poolRows.length()>0){
				salePool=new ArrayList<SalePoolBean>();
				for(int i=0;i<poolRows.length();i++){
					if(!poolRows.isNull(i)){
						SalePoolBean sBean=new SalePoolBean().map(poolRows.optJSONObject(i));
						if(sBean!=null)
							salePool.add(sBean);
					}
				}
			}
			bean.setPool(salePool);
			JSONArray ja=json.optJSONArray("marketing");
			ArrayList<String> market=null;
			if(ja!=null&&ja.length()>0){
				market=new ArrayList<String>();
				for(int i=0;i<ja.length();i++){
					if(!ja.isNull(i)){
						JSONObject o=ja.optJSONObject(i);
						if(o!=null){
							String s=o.optString("message", "");
							if(s != null)
								market.add(s);
						}
					}
				}
			}
			bean.setMarketing(market);
		}
		return bean;
	}
}
