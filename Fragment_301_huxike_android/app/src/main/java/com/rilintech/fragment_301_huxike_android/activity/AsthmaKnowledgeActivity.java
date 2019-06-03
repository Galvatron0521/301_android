package com.rilintech.fragment_301_huxike_android.activity;

import android.os.Bundle;
import android.view.View;

import com.rilintech.fragment_301_huxike_android.R;
import com.umeng.analytics.MobclickAgent;


public class AsthmaKnowledgeActivity  extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.asthmaknowledge);
		
	}
	public void onClick(View view){

		this.finish();
	}

	@Override
	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}
	@Override
	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}
}
