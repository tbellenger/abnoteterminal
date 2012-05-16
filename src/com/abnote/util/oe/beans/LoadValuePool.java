package com.abnote.util.oe.beans;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class LoadValuePool implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -814710515733923460L;
	private String loadPoolID;
	private ArrayList<LoadAmountValueBean> loadAmount;
	/**
	 * @return the loadPoolID
	 */
	public String getLoadPoolID() {
		return loadPoolID;
	}
	/**
	 * @param loadPoolID the loadPoolID to set
	 */
	public void setLoadPoolID(String loadPoolID) {
		this.loadPoolID = loadPoolID;
	}
	/**
	 * @return the loadAmount
	 */
	public ArrayList<LoadAmountValueBean> getLoadAmount() {
		return loadAmount;
	}
	/**
	 * @param loadAmount the loadAmount to set
	 */
	public void setLoadAmount(ArrayList<LoadAmountValueBean> loadAmount) {
		this.loadAmount = loadAmount;
	}
	public LoadValuePool map(JSONObject json){		
		LoadValuePool lvp=null;
		if (json!=null) {
			lvp = new LoadValuePool();
			lvp.setLoadPoolID(json.optString("loadPoolID", ""));
			if (!json.isNull("loadAmount")) {
				ArrayList<LoadAmountValueBean> lavb = new ArrayList<LoadAmountValueBean>();
				JSONArray ja = json.optJSONArray("loadAmount");
				if (ja!=null&&ja.length()>0) {
					for (int i = 0; i < ja.length(); i++) {
						LoadAmountValueBean l = new LoadAmountValueBean().map(ja.optJSONObject(i));
						if(l!=null){
							lavb.add(l);
						}
					}
				}
				if(lavb!=null&&lavb.size()>0){
					lvp.setLoadAmount(lavb);
				}
			}
		}
		return lvp;		
	}
}
