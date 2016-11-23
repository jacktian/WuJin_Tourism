package wujin.tourism.android.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class DatabaseHelper extends SQLiteOpenHelper {
	private static int m_nversion = 1;
	private static String m_strdbname = "wujin.tourism.android";

	public DatabaseHelper(Context context, CursorFactory factory, int version) {
		super(context, m_strdbname, factory, version);
	}

	public DatabaseHelper(Context context, int version) {
		this(context, null, version);
	}

	public DatabaseHelper(Context context) {
		this(context, m_nversion);
	}

	public void onCreate(SQLiteDatabase Database) {
		Database.execSQL(
				"create table wzinfrotable(id varchar(40),type varchar(40),address varchar(400),description varchar(400),imageUrl  varchar(60),phone  varchar(60),title  varchar(60))");
		Database.execSQL(
				"create table yxinfrotable(id varchar(15),imageurl varchar(100),imagesmallurl varchar(100),count varchar(10))");
		Database.execSQL("create table imageurltable(imageurl varchar(10))");
	}

	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
	}
}
