package com.invengo.train.xc2002test001.dao;

import android.content.Context;
import android.content.ContextWrapper;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.invengo.train.xc2002test001.Ma;

import java.io.File;

/**
 * 数据库位置
 * Created by ziniulian on 2017/10/18.
 */

public class SdDb extends ContextWrapper {
	public SdDb(Context base) {
		super(base);
	}

	@Override
	public File getDatabasePath(String name) {
		String p = Ma.sdDir + "DB/" + name;
		if (!p.endsWith(".db")) {
			p += ".db";
		}

		File dbfile = new File(Environment.getExternalStorageDirectory(), p);
		if (!dbfile.getParentFile().exists()) {
			dbfile.getParentFile().mkdirs();
		}

		return dbfile;
	}

	@Override
	public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory) {
		return SQLiteDatabase.openOrCreateDatabase(getDatabasePath(name), null);
	}

	@Override
	public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory, DatabaseErrorHandler errorHandler) {
		return openOrCreateDatabase(name, mode, factory);
	}
}
