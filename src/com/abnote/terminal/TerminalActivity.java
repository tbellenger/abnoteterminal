package com.abnote.terminal;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import com.abnote.util.oe.OneEmpowerProcessor;
import com.abnote.util.oe.beans.BaseResponseBean;
import com.abnote.util.oe.beans.LoginResponseBean;
import com.abnote.util.oe.beans.SaleResponseBean;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class TerminalActivity extends Activity {
	private static final String TAG = "TerminalActivity";
	private static final String STATE_AMT = "com.abnote.termial.TerminalActivity.AMT";
	private static final String STATE_LOGIN = "com.abnote.termial.TerminalActivity.LOGIN";
	private static final int SONIC = 0;
	private static final int SAVE = 1;
	private Button mOne, mTwo, mThree, mFour, mFive, mSix, mSeven, mEight, mNine, mZero, mZeroes, mClear, mPay;
	private double mCurrAmt = 0;
	private TextView mAmt;
	private TextView mMerchant;
	private LoginResponseBean mLogin;
	private Handler mHandler;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		//Remove title bar
		if (android.os.Build.VERSION.SDK_INT <= android.os.Build.VERSION_CODES.GINGERBREAD_MR1){
			this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		}

		setContentView(R.layout.term);
		
		mHandler = new Handler();

		mOne = (Button) findViewById(R.id.b1);
		mTwo = (Button) findViewById(R.id.b2);
		mThree = (Button) findViewById(R.id.b3);
		mFour = (Button) findViewById(R.id.b4);
		mFive = (Button) findViewById(R.id.b5);
		mSix = (Button) findViewById(R.id.b6);
		mSeven = (Button) findViewById(R.id.b7);
		mEight = (Button) findViewById(R.id.b8);
		mNine = (Button) findViewById(R.id.b9);
		mZero = (Button) findViewById(R.id.b0);
		mZeroes = (Button) findViewById(R.id.b00);
		mClear = (Button) findViewById(R.id.bclear);
		mPay = (Button) findViewById(R.id.bpay);
		mAmt = (TextView) findViewById(R.id.tvamt);
		mMerchant = (TextView) findViewById(R.id.tvmerchant);

		mOne.setOnClickListener(mButtonListener);
		mTwo.setOnClickListener(mButtonListener);
		mThree.setOnClickListener(mButtonListener);
		mFour.setOnClickListener(mButtonListener);
		mFive.setOnClickListener(mButtonListener);
		mSix.setOnClickListener(mButtonListener);
		mSeven.setOnClickListener(mButtonListener);
		mEight.setOnClickListener(mButtonListener);
		mNine.setOnClickListener(mButtonListener);
		mZero.setOnClickListener(mButtonListener);
		mZeroes.setOnClickListener(mButtonListener);
		mClear.setOnClickListener(mButtonListener);
		mPay.setOnClickListener(mButtonListener);
		
		if (savedInstanceState != null) {
			mCurrAmt = savedInstanceState.getDouble(STATE_AMT);
			mLogin = (LoginResponseBean) savedInstanceState.getSerializable(STATE_LOGIN);
			if (mLogin != null) {
				mMerchant.setText(getString(R.string.txt_login) + " " + mLogin.getMerchantName());
			}
		}
		
		mAmt.setText(formatCurrency(mCurrAmt));
	}

	private OnClickListener mButtonListener = new OnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.b1: appendDigit(.01); break;
			case R.id.b2: appendDigit(.02); break;
			case R.id.b3: appendDigit(.03); break;
			case R.id.b4: appendDigit(.04); break;
			case R.id.b5: appendDigit(.05); break;
			case R.id.b6: appendDigit(.06); break;
			case R.id.b7: appendDigit(.07); break;
			case R.id.b8: appendDigit(.08); break;
			case R.id.b9: appendDigit(.09); break;
			case R.id.b0: appendDigit(.0); break;
			case R.id.b00: appendDigit(.0); appendDigit(.0); break;
			case R.id.bclear: clearAmt(); break;
			case R.id.bpay: pay(); break;
			default: clearAmt();
			}
		}
	};
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_menu, menu);
        if (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.GINGERBREAD_MR1){
			MenuItem settings = menu.findItem(R.id.menu_settings);
			MenuItem saveway = menu.findItem(R.id.menu_saveway);
			MenuItem sonic = menu.findItem(R.id.menu_sonic);
			settings.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
			saveway.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
			sonic.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		} 
        return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch(item.getItemId()) {
    	case R.id.menu_saveway: login(SAVE); break;
    	case R.id.menu_sonic: login(SONIC); break;
    	default: return super.onOptionsItemSelected(item);
    	}
    	return true;
    }
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putDouble(STATE_AMT, mCurrAmt);
		outState.putSerializable(STATE_LOGIN, mLogin);
	}
	
	private void login(final int merchant) {
		
		final ProgressDialog progress = ProgressDialog.show(TerminalActivity.this, "Merchant Login","Logging in, please wait...", true);

		new Thread(new Runnable() {

			@Override
			public void run() {
				OneEmpowerProcessor oem = new OneEmpowerProcessor();
				if (mLogin != null) {
					oem.processLogoutRequest("", mLogin.getAccessToken());
					mHandler.post(new Runnable() {

						@Override
						public void run() {
							mMerchant.setText(R.string.txt_logout);
						}
					});
				}
				final BaseResponseBean r;
				if (merchant == SONIC) {
					r = oem.processSonicLoginRequest();
				} else {
					r = oem.processSavewayLoginRequest();
				}
				if (r != null && r.getResponseCode().equals("000")) {
					mLogin = (LoginResponseBean) r;
					mHandler.post(new Runnable() {
						@Override
						public void run() {
							progress.dismiss();
							mMerchant.setText(getString(R.string.txt_login) + " " + mLogin.getMerchantName());
							Toast.makeText(getApplicationContext(), mLogin.getResponseMsg(), Toast.LENGTH_LONG).show();
						}
					});
				} else {
					mHandler.post(new Runnable() {
						@Override
						public void run() {
							progress.dismiss();
							Toast.makeText(getApplicationContext(), "Error logging in. Check your network settings", Toast.LENGTH_LONG).show();
						}
					});
				}
				
			}
		}).start();
	}
	
	private void clearAmt() {
		mCurrAmt = 0;
		mAmt.setText(formatCurrency(mCurrAmt));
	}
	
	private void pay() {
		if (mLogin != null) {
			if (mCurrAmt == 0) {
				Toast.makeText(this, "Enter amount", Toast.LENGTH_LONG).show();
				return;
			}
			Intent i = new Intent(getApplicationContext(), NfcActivity.class);
			i.putExtra("amt", new DecimalFormat("#.##").format(mCurrAmt));
			i.putExtra("loginbean", mLogin);
			startActivityForResult(i, NfcActivity.REQ_READ_CARD);
		} else {
			Toast.makeText(this, "Login first", Toast.LENGTH_LONG).show();
		}
	}
	
	@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    	super.onActivityResult(requestCode, resultCode, data);
    	
    	if (resultCode == NfcActivity.RSP_OK) {
    		clearAmt();
    		SaleResponseBean sale = (SaleResponseBean) data.getExtras().get("transbean");
    		Toast.makeText(this, sale.getResponseMsg(), Toast.LENGTH_LONG).show();
    		ReceiptDialog rd = new ReceiptDialog(this);
    		rd.setSaleData(sale);
    		rd.show();
    	} else if (resultCode == NfcActivity.RSP_ERROR) {
    		Toast.makeText(this, "NFC Error", Toast.LENGTH_LONG).show();
    	} else if (resultCode == NfcActivity.RSP_CANCEL) {
    		Toast.makeText(this, "Payment cancelled", Toast.LENGTH_LONG).show();
    	}
    }
	
	private void appendDigit(double digit) {
		if (digit == 0 && mCurrAmt == 0) {
			// don't add 0 or 00 to an empty amount
		} else {
			mCurrAmt = (mCurrAmt * 10) + digit;
			mAmt.setText(formatCurrency(mCurrAmt));
		}
	}
	
	public static String formatCurrency(double amt) {
		NumberFormat formatter = NumberFormat.getCurrencyInstance();
		return formatter.format(amt);
	}
	
	public static String formatCurrency(String amt) {
		return formatCurrency(Double.parseDouble(amt));
	}
}
