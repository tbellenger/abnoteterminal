package com.abnote.terminal;

import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Random;

import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.util.Log;

import com.abnote.nfc.EmvCard;
import com.abnote.nfc.Terminal;
import com.abnote.nfc.Card.StatusWordException;
import com.abnote.nfc.util.HexString;
import com.abnote.nfc.util.TlvException;
import com.abnote.util.oe.OneEmpowerProcessor;
import com.abnote.util.oe.beans.BaseResponseBean;
import com.abnote.util.oe.beans.LoginResponseBean;
import com.abnote.util.oe.beans.RedeemBean;

public class PerformTransactionTask extends AsyncTask<Tag, Integer, BaseResponseBean> {
	private static final String TAG = "PerformTransactionTask";
	//private MediaPlayer mMediaPlayer;
	private String mAmt;
	private String mAccessToken;
	private NfcActivity linkedActivity = null;
	
	public PerformTransactionTask(NfcActivity linkedActivity, String amt, String accessToken) {
		//Uri alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		//mMediaPlayer = MediaPlayer.create(linkedActivity, alert);
		mAmt = amt;
		mAccessToken = accessToken;
		Log.d(TAG, "Amt: " + mAmt + " AccessToken: " + mAccessToken);
		attach(linkedActivity);
	}
	
	public void attach(NfcActivity linkedActivity) {
		this.linkedActivity = linkedActivity;
		
		Log.d(TAG, "Activity link complete");
	}
	
	public void detach() {
		this.linkedActivity = null;
	}
	
	private String padAmount(String amt) {
		String unpadded = amt.replace(".", "");
		// pad amount to correct length
		while (unpadded.length() < 12) {
			unpadded = "0" + unpadded;
		}
		return unpadded;
	}
	
	@Override
	protected BaseResponseBean doInBackground(Tag... tags) {
		Terminal term = null;
		String pan = "";
		try {
			// get amt of transaction
			String amt = padAmount(mAmt);
			Log.d(TAG, "Amt " + amt);
			
			Random rand = new Random();
			byte[] randBuff = new byte[4];
			rand.nextBytes(randBuff);
			Date today = new Date();
			Format f = new SimpleDateFormat("yyMMdd");
			Hashtable<String, String> terminalSettings = new Hashtable<String, String>();
			terminalSettings.put("9F66", "D6400000");			// Visa Terminal Transaction Qualifiers
			terminalSettings.put("9F02", amt);					// Amt Authorised
			terminalSettings.put("9F37", HexString.hexify(randBuff));	// Terminal Random
			terminalSettings.put("5F2A", "0036");				// Country Code AU
			terminalSettings.put("9A", f.format(today));		// Transaction Date YYMMDD
			
			
			term = new Terminal(new EmvCard(tags[0]), terminalSettings);
		
			term.connect();
			publishProgress(NfcActivity.STATUS_READING_CARD);
			term.selectPpse();
			term.selectPaymentApp();
			term.getProcessingOptions();
			term.getMSD();
			pan = term.getPAN();
			
			//mMediaPlayer.setLooping(false);
	        //mMediaPlayer.start();
			
			if (!pan.isEmpty()) {
				publishProgress(NfcActivity.STATUS_ONLINE);
				OneEmpowerProcessor oem = new OneEmpowerProcessor();
				ArrayList<RedeemBean> rb = new ArrayList<RedeemBean>();
				BaseResponseBean r = oem.processSaleRequest("", mAmt, pan, rb, mAccessToken);
				return r;
			}
		} catch (StatusWordException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TlvException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				term.disconnect();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	@Override
	protected void onProgressUpdate(Integer...status) {
		linkedActivity.onProgressUpdate(status[0]);
	}
	
	@Override
	protected void onPostExecute(BaseResponseBean result) {
		linkedActivity.onTaskFinished(result);
	}
	
	@Override
	protected void onCancelled() {
        linkedActivity.onCancelled();
	}
	
	@Override
	public void finalize() {
		try {
			super.finalize();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//mMediaPlayer.release();
	}
	
}
