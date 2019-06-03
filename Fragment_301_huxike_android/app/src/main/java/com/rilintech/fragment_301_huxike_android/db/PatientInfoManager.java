package com.rilintech.fragment_301_huxike_android.db;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.rilintech.fragment_301_huxike_android.bean.PatientInfo;


@SuppressWarnings("unused")
public class PatientInfoManager {

    public static final String DB_NAME = "sci";
    public static final String TABLE_NAME = "patient_infos";
    public static final int DB_VERSION = 2;
    private static final String TAG = "patient_infos";
    public static final String KEY_ID = "_id";
    public static final String KEY_NAME = "name";
    public static final int NAME_COLUMN = 1;
    private Context mContext = null;
    private String table = "patient_infos";
    public static final String DATABASE_CREATE = "create table if not exists patient_infos (_id Integer primary key autoincrement,"
            + " name varchar(255), " +
            "bed_no varchar(255), " +
            "age varchar(255), " +
            "sex varchar(255), " +
            "identity_card varchar(255), " +
            "height varchar(255), " +
            "weight varchar(255), " +
            "phone varchar(255), " +
            "zip_code varchar(255), " +
            "address varchar(255), " +
            "company varchar(255), " +
            "hospital varchar(255), " +
            "describe varchar(255) " +
            ") ";
    private SQLiteDatabase mSQLiteDatabase = null;
    private DataBaseManagementHelper mDatabaseHelper = null;

    private static class DataBaseManagementHelper extends SQLiteOpenHelper {

        DataBaseManagementHelper(Context context) {

            super(context, DB_NAME, null, DB_VERSION);
             onCreate(getWritableDatabase());
        }

        DataBaseManagementHelper(Context context, String bZ) {

            super(context, DB_NAME, null, DB_VERSION);
            onCreate(getWritableDatabase());
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.i(TAG, "db.getVersion()=" + db.getVersion());
            //db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME + ";");
            db.execSQL(DATABASE_CREATE);
            Log.i(TAG, "db.execSQL(DB_CREATE)");
            Log.e(TAG, DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.i(TAG, "DataBaseManagementHelper onUpgrade");
            onCreate(db);
        }
    }

    public PatientInfoManager(Context context) {
        mContext = context;
        Log.i(TAG, "UserDataManager construction!");

    }

    public void openDataBase() throws SQLException {

        mDatabaseHelper = new DataBaseManagementHelper(mContext);
        mSQLiteDatabase = mDatabaseHelper.getWritableDatabase();
    }

    public void openDataBase(String bZ) throws SQLException {

        mDatabaseHelper = new DataBaseManagementHelper(mContext, bZ);

        mSQLiteDatabase = mDatabaseHelper.getWritableDatabase();
    }

    public void closeDataBase() throws SQLException {

        mDatabaseHelper.close();
    }


    //查询返回患者
    public PatientInfo patientInfo() {
        Cursor c = queryPatientInfoCursor();
        System.out.println("+++++++++++++++" + c.getCount());
        PatientInfo patientInfo = null;
        while (c.moveToNext()) {
            patientInfo = new PatientInfo();
            patientInfo.setName(c.getString(c.getColumnIndex("name")));
            patientInfo.setAge((c.getString(c.getColumnIndex("age"))));
            patientInfo.setBedNO(c.getString(c.getColumnIndex("bed_no")));
            patientInfo.setSex(c.getString(c.getColumnIndex("sex")));
            patientInfo.setIdentity_card(c.getString(c.getColumnIndex("identity_card")));
            patientInfo.setHeight(c.getString(c.getColumnIndex("height")));
            patientInfo.setWeight(c.getString(c.getColumnIndex("weight")));
            patientInfo.setPhone(c.getString(c.getColumnIndex("phone")));
            patientInfo.setZip_code(c.getString(c.getColumnIndex("zip_code")));
            patientInfo.setAddress(c.getString(c.getColumnIndex("address")));
            patientInfo.setCompany(c.getString(c.getColumnIndex("company")));
            patientInfo.setHospital(c.getString(c.getColumnIndex("hospital")));
            patientInfo.setDescribe(c.getString(c.getColumnIndex("describe")));

        }
        c.close();
        return patientInfo;
    }

    public Cursor queryPatientInfoCursor() {
        return mSQLiteDatabase.rawQuery("select * from patient_infos", null);
    }

    //插入患者信息
    public void addPatientInfo(PatientInfo patientInfo) {
        mSQLiteDatabase.beginTransaction();
        try {
            //删除
            //mSQLiteDatabase.delete(TABLE_NAME, null, null);
            mSQLiteDatabase.execSQL("delete from patient_infos");
            //插入
            ContentValues cvs = new ContentValues();
            cvs.put("name", patientInfo.getName());
            cvs.put("age", patientInfo.getAge());
            cvs.put("bed_no", patientInfo.getBedNO());
            cvs.put("sex", patientInfo.getSex());
            cvs.put("identity_card", patientInfo.getIdentity_card());
            cvs.put("height", patientInfo.getHeight());
            cvs.put("weight", patientInfo.getWeight());
            cvs.put("phone", patientInfo.getPhone());
            cvs.put("zip_code", patientInfo.getZip_code());
            cvs.put("address", patientInfo.getAddress());
            cvs.put("company", patientInfo.getCompany());
            cvs.put("hospital", patientInfo.getHospital());
            cvs.put("describe", patientInfo.getDescribe());


            mSQLiteDatabase.insert(TABLE_NAME, null, cvs);

            mSQLiteDatabase.setTransactionSuccessful();
        } catch (Exception e) {

        } finally {
            mSQLiteDatabase.endTransaction();
        }
    }
    //更新
    public int updateItem(String key,String value){

        ContentValues contentValues = new ContentValues();
        contentValues.put(key, value);

        //String whereClause = "_id=?";
        //String[]whereArgs = {"1"};

        return  mSQLiteDatabase.update(TABLE_NAME, contentValues,null,null);
    }


}
