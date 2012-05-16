package com.abnote.util.oe;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.util.Log;

import com.abnote.net.util.HttpRequestHandler;
import com.abnote.util.oe.beans.BaseResponseBean;
import com.abnote.util.oe.beans.LoginResponseBean;
import com.abnote.util.oe.beans.RedeemBean;
import com.abnote.util.oe.beans.RedeemBeanContainer;
import com.abnote.util.oe.beans.SaleResponseBean;
import com.google.gson.Gson;

public class OneEmpowerProcessor {
	private static final String TAG = "OneEmpowerProcessor";
	private static final String URL = "http://27.109.106.190:8601/merchant/json";
	
	private final static String CARD_NO="cardNo";
	private final static String SHOW_EXP_DATE="showExpDate";
	private final static String ACCESS_TOKEN="accessToken";
	private final static String GROSS_AMOUNT="grossAmt";
	private final static String REDEEM="redeem";
	private final static String ITEMLIST="itemList";
	private final static String LOAD="load";
	private final static String VOID_REF_NO="refNo";
	private final static String CARD_STATUS="cardStatus";
	private final static String CURRENT_PAGE="pageNo";
	private final static String PAGE_LIMIT="totalRecordPerPage";
	private final static String HOLD_BALANCE="holdBalance";
	private final static String UNHOLD_BALANCE="unholdBalance";
	private final static String BURN="burn";
	private final static String TXN_DATE="date";
	private final static String TXN_TIME="time";
	private final static String TIME_ZONE="timezone";
	private final static String VERSION_NO="versionNo";
	private final static String OPERATOR_ID="operatorID";
	private final static String OPERATOR_PIN="pin";
	private final static String LAST_REF_NO="lastRefNo";
	
	private static final String SERVICE_LOGIN = "merchantlogin";
	private static final String SERVICE_LOGOUT = "merchantlogout";
	private static final String SERVICE_TRANSACTION = "merchantsaletransaction";
	private static final String VERSION = "0000";
	private HttpRequestHandler mHttpRequestHandler;
	
	public OneEmpowerProcessor() {
		mHttpRequestHandler = HttpRequestHandler.getInstance();
	}
	
	public BaseResponseBean processLogoutRequest(String lastRefNo, String accessToken){
		ArrayList<NameValuePair> params= new ArrayList<NameValuePair>();		
		return processBaseTransactionRequest(params, SERVICE_LOGOUT, lastRefNo, accessToken);
	}
	
	public BaseResponseBean processLoginRequest(String operator, String pin) {
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair(OPERATOR_ID, operator));
		params.add(new BasicNameValuePair(OPERATOR_PIN, pin));
		
		return processBaseRequest(params, SERVICE_LOGIN);
	}
	
	public BaseResponseBean processSonicLoginRequest() {
		return processLoginRequest("912856", "123456");
	}
	
	public BaseResponseBean processSavewayLoginRequest() {
		return processLoginRequest("912855", "123456");
	}
	
	public BaseResponseBean processSaleRequest(String lastRefNo, String grossAmount, String card, ArrayList<RedeemBean> rb, String accessToken) {
		ArrayList<NameValuePair> params= new ArrayList<NameValuePair>();
		RedeemBeanContainer rbc=new RedeemBeanContainer();
		rbc.setRedeem(rb);
		params.add(new BasicNameValuePair(REDEEM, encode(rbc)));
		params.add(new BasicNameValuePair(GROSS_AMOUNT, grossAmount));
		params.add(new BasicNameValuePair(CARD_NO, card));
		params.add(new BasicNameValuePair(UNHOLD_BALANCE, "N"));

		return processBaseTransactionRequest(params, SERVICE_TRANSACTION, lastRefNo, accessToken);
	}
	
	public BaseResponseBean processBaseTransactionRequest(ArrayList<NameValuePair> params, String service, String lastRefNo, String accessToken) {
		params.add(new BasicNameValuePair(ACCESS_TOKEN, accessToken));
		params.add(new BasicNameValuePair(LAST_REF_NO,lastRefNo));
		return processBaseRequest(params,service);
	}
	
	private BaseResponseBean processResponse(String json, String service) {
		JSONObject j;
		try {
			j = new JSONObject(json);			
			JSONObject meta=j.optJSONObject("meta");
			JSONObject response=j.optJSONObject("response");
			if(meta!=null){
				String responseCode=meta.optString("responseCode","999");
				String responseMsg=meta.optString("responseMsg", "Unknown error");			
				if(!"000".equals(responseCode)){
					return new BaseResponseBean(responseCode,responseMsg);
				}
				response.put("responseCode", responseCode);
				response.put("responseMsg", responseMsg);
			}
			else{
				response.put("responseCode", "999");
				response.put("responseMsg", "Unknown error");
				response.put(VERSION_NO, VERSION);
			}
			if (service.equals(SERVICE_LOGIN)) {
				LoginResponseBean r = new LoginResponseBean();
				return (BaseResponseBean)r.map(response);
			} else if (service.equals(SERVICE_TRANSACTION)) {
				SaleResponseBean r = new SaleResponseBean();
				return (BaseResponseBean)r.map(response);
			} 
		} catch (Exception e) {
			if (e.getMessage() != null) {
				Log.e(TAG, e.getMessage());
			} else {
				Log.d(TAG, "Exception with null message from JSONObject");
			}
		}
		return null;
	}
	
	public BaseResponseBean processBaseRequest(ArrayList<NameValuePair> params, String service) {
		Date now = new Date();
		String date = new SimpleDateFormat("yyyyMMdd").format(now);
		String time = new SimpleDateFormat("HHmmss").format(now);
		String tz = Calendar.getInstance().getTimeZone().getID();
		
		params.add(new BasicNameValuePair(TXN_DATE, date));
		params.add(new BasicNameValuePair(TXN_TIME, time));
		params.add(new BasicNameValuePair(TIME_ZONE, tz));
		params.add(new BasicNameValuePair(VERSION_NO, VERSION));
		
		String json = mHttpRequestHandler.sendRequest(params, URL, service);
		
		return processResponse(json, service);
	}
	
	private static String encode(Object o){
        String s=null;
        try{       	        	
        	s=new Gson().toJson(o);
        	System.out.println(s);
        }
        catch(Exception e){
        	e.printStackTrace();
        }
        return s;
     }
}
