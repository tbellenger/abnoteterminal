package com.abnote.terminal;

import java.util.concurrent.atomic.AtomicBoolean;

import com.abnote.nfc.activity.BaseNfcActivity;
import com.abnote.util.oe.beans.BaseResponseBean;
import com.abnote.util.oe.beans.LoginResponseBean;
import com.abnote.util.oe.beans.SaleResponseBean;

import android.content.Intent;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class NfcActivity extends BaseNfcActivity {
	private static final String TAG = "NfcActivity";
	private static final String STATE_AMT = "com.abnote.termial.NfcActivity.AMT";
	private static final String STATE_LOGIN = "com.abnote.termial.NfcActivity.LOGIN";
	private static final String STATE_INFO = "com.abnote.termial.NfcActivity.INFO";
	private static final String STATE_INFO_AMT = "com.abnote.termial.NfcActivity.INFO_AMT";
	public static final int REQ_READ_CARD = 1;
	public static final int RSP_OK = 0;
	public static final int RSP_ERROR = 1;
	public static final int RSP_CANCEL = 2;
	public static final int STATUS_READING_CARD = 1;
	public static final int STATUS_ONLINE = 2;
	private Button mCancel;
	private TextView mInfo, mInfoAmt;
	private AtomicBoolean mIsCancelClicked = new AtomicBoolean();
	private String mAmt;
	private LoginResponseBean mLogin;
	private PerformTransactionTask mRunningTask;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate");
		//Remove title bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.nfc);
		
		if (getIntent().getExtras() != null) {
			Log.d(TAG, "with extras");
			mAmt = getIntent().getExtras().getString("amt");
			mLogin = (LoginResponseBean) getIntent().getExtras().get("loginbean");
		}
		
		if( (mRunningTask = (PerformTransactionTask)getLastNonConfigurationInstance()) != null) {
			Log.d(TAG, "re-attach to previous task"); 
			mRunningTask.attach(this);
		} else {
			Log.d(TAG, "new task");
			mRunningTask = new PerformTransactionTask(this, mAmt, mLogin.getAccessToken());
		}

		mInfo = (TextView) findViewById(R.id.tvinfo);
		mInfo.setText(getString(R.string.txt_tap_card));
		mInfoAmt = (TextView) findViewById(R.id.tvamt);
		mInfoAmt.setText(TerminalActivity.formatCurrency(mAmt));
		mCancel = (Button) findViewById(R.id.bcancel);
		mCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View paramView) {
			if (mRunningTask != null && mRunningTask.getStatus().compareTo(AsyncTask.Status.RUNNING) == 0) {
					mIsCancelClicked.set(true);
				} else {
		            setResult(RSP_CANCEL);
		            finish();
				}
				
			}
		});
		
		if (savedInstanceState != null) {
			Log.d(TAG, "update from saved instance");
			mAmt = savedInstanceState.getString(STATE_AMT);
			mLogin = (LoginResponseBean) savedInstanceState.getSerializable(STATE_LOGIN);
			mInfo.setText(savedInstanceState.getString(STATE_INFO));
			mInfoAmt.setText(savedInstanceState.getCharSequence(STATE_INFO_AMT));
		}
		
		setResult(RSP_CANCEL);
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString(STATE_AMT, mAmt);
		outState.putSerializable(STATE_LOGIN, mLogin);
		outState.putString(STATE_INFO, mInfo.getText().toString());
		outState.putCharSequence(STATE_INFO_AMT, mInfoAmt.getText());
	}
	
	@Override
	public Object onRetainNonConfigurationInstance() {
		Log.d(TAG, "config change. detach from task");
		mRunningTask.detach();
		return mRunningTask;
	}
	
	@Override
	public void onPause() {
		super.onPause();
		Log.d(TAG, "onPause");
	}
	
	@Override
	public void onStop() {
		super.onStop();
		Log.d(TAG, "onStop");
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d(TAG, "onDestroy");
	}
	
	@Override
	public void onResume() {
		super.onResume();
		Log.d(TAG, "onResume");
	}
	
	public void onNfcDiscovered(Tag tag) {
		// Make processing a transaction one shot for this activity
		if (mRunningTask.getStatus().equals(AsyncTask.Status.PENDING)) {
			Log.d(TAG, "task pending so execute");
			mRunningTask.execute(tag);
		} else {
			Log.d(TAG, "no task pending");
		}
	}
	
	public void onProgressUpdate(Integer status) {
		switch (status) {
			case STATUS_READING_CARD: mInfo.setText(getString(R.string.txt_reading_card)); break;
			case STATUS_ONLINE: mInfo.setText(getString(R.string.txt_online_processing)); break;
		}
	}
	
	public void onCancelled() {
		setResult(RSP_CANCEL);
        finish();
	}
	
	public void onTaskFinished(BaseResponseBean result) {
		if (result != null) {
			if (result.getResponseCode().equals("000")) {
				Intent in = new Intent();
	            in.putExtra("transbean", (SaleResponseBean)result);
	            setResult(RSP_OK,in);
	            finish();
			} else {
				Intent in = new Intent();
				setResult(RSP_ERROR);
				in.putExtra("resultbean", result);
				finish();
			}
		} else {
			setResult(RSP_ERROR);
            finish();
		}
	}
	
	
}
