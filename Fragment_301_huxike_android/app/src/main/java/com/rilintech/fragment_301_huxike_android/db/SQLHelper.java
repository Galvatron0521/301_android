package com.rilintech.fragment_301_huxike_android.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLHelper extends SQLiteOpenHelper {
	public static final String DB_NAME = "database.db";// 数据库名称
	public static final int VERSION = 1;
	
	public static final String TABLE_CHANNEL = "channel";//数据表 channel
	public static final String TABLE_SCORES = "scores";//数据表 scores

	public static final String ID = "id";//
	public static final String NAME = "name";
	public static final String IMG="img";
	public static final String SCORE_1="score_1";
	public static final String SCORE_2="score_2";
	public static final String SCORE_3="score_3";
	public static final String SCORE_4="score_4";
	public static final String SCORE_5="score_5";
	public static final String ORDERID = "orderId";
	public static final String SELECTED = "selected";
	private Context context;
	public SQLHelper(Context context) {
		super(context, DB_NAME, null, VERSION);
		this.context = context;
	}

	public Context getContext(){
		return context;
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		String sqlChannel = "create table if not exists "+TABLE_CHANNEL +
				"(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
				ID + " INTEGER , " +
				NAME + " TEXT , " +
				IMG + " INTEGER , " +
				ORDERID + " INTEGER , " +
				SELECTED + " SELECTED)";
		String sqlScore="create table if not exists "+TABLE_SCORES +
				"(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
				ID + " INTEGER , " +
				SCORE_1 + " INTEGER , " +
				SCORE_2 + " INTEGER , " +
				SCORE_3 + " INTEGER , " +
				SCORE_4 + " INTEGER , " +
				SCORE_5 + " INTEGER)";
		db.execSQL(sqlChannel);
		db.execSQL(sqlScore);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		onCreate(db);
	}

}
