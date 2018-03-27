package com.invengo.train.xc2002test001.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.invengo.train.xc2002test001.enums.EmSql;

import static com.invengo.train.rfid.tag.BaseTag.parse;
import static com.invengo.train.xc2002test001.enums.UtStrMegEm.meg;

/**
 * 本地数据库
 * Created by ziniulian on 2017/10/17.
 */

public class DbLocal extends SQLiteOpenHelper {
	private int max = 999;

	public DbLocal(Context c) {
		super(new SdDb(c), EmSql.DbNam.toString(), null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(EmSql.CrtTab.toString());
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	private String getStr (Cursor c, int i) {
		String s = null;
		s = c.getString(i);
		if (s != null) {
			return '\"' + s + '\"';
		} else {
			return null;
		}
	}

	// 执行SQL语句
	private void exe (String s, String[] args) {
		SQLiteDatabase db = this.getWritableDatabase();
		if (args == null) {
			db.execSQL(s);
		} else {
			db.execSQL(meg(s, args));
		}
		db.close();
	}

	// 保存数据
	public void sav (String[] arg) {
		exe(EmSql.AddOne.toString(), arg);
		long n = this.count();
		if (n > this.max) {
			n -= this.max;
			exe(EmSql.DelOut.toString(), new String[] {n + ""});
		}
	}

	// 删除数据
	public void del (String[] arg) {
		if (arg == null) {
			exe(EmSql.DelAll.toString(), null);
		} else {
			exe(EmSql.DelOne.toString(), arg);
		}
	}

	// 修改数据
	public void set (String[] arg) {
		exe(EmSql.SetXiu.toString(), arg);
	}

	// 查询数据
	public String get (String p, String len) {
		StringBuilder r = new StringBuilder();
		r.append('[');

		SQLiteDatabase db = this.getReadableDatabase();
//		Cursor c = db.rawQuery(meg(EmSql.Get.toString(), new String[] {p, len, getAble()}), null);
		Cursor c = db.rawQuery(meg(EmSql.Get.toString(), new String[] {p, len}), null);
		while (c.moveToNext()) {
			r.append("{\"id\":");
			r.append(c.getLong(0));
			r.append(",\"tim\":\"");
			r.append(c.getString(1));
			r.append("\",\"xiu\":\"");
			r.append(c.getString(3));
			r.append("\",\"tag\":");
			r.append(parse(c.getString(2)).toJson());
			r.append(",\"enb\":");
			r.append(c.getLong(4));
			r.append("},");
		}
		if (r.length() > 1) {
			r.deleteCharAt(r.length() - 1);
		}

 		r.append(']');
		return r.toString();
	}

	// 获取总数
	public long count () {
		long r = 0;
		SQLiteDatabase db = this.getReadableDatabase();
//		Cursor c = db.rawQuery(meg(EmSql.GetCount.toString(), new String[] {getAble()}), null);
		Cursor c = db.rawQuery(EmSql.GetCount.toString(), null);
		if (c.moveToNext()) {
			r = c.getLong(0);
		}
		return r;
	}

}
