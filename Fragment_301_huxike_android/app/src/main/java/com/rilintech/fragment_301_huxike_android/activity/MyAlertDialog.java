package com.rilintech.fragment_301_huxike_android.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.rilintech.fragment_301_huxike_android.R;


public class MyAlertDialog extends Activity implements OnClickListener {
	private TextView mTextView;
	private Button mButton;
	@SuppressLint("ResourceAsColor")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alert_dialog);
		mTextView = (TextView) findViewById(R.id.title);
		mButton = (Button) findViewById(R.id.btn_ok);
		mButton.setOnClickListener(this);
		// 提示内容
		String msg = getIntent().getStringExtra("msg");
		// 提示标题
		String title = getIntent().getStringExtra("title");

		if (msg != null)
			((TextView) findViewById(R.id.alert_message)).setText(msg);
		if (title != null)
			mTextView.setText(title);
			mTextView.setTextColor(getResources().getColor(R.color.theme_background));
	}
	@Override
	public void onClick(View arg0) {
		//startActivity(new Intent(this, MainActivity.class));
		finish();
	}
}
