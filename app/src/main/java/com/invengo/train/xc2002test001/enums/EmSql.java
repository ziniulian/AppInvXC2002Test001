package com.invengo.train.xc2002test001.enums;

/**
 * 本地 SQL 语句
 * Created by ziniulian on 2017/10/17.
 */

public enum EmSql {
	// 建表
	CrtTab("create table tbMotor(" +
			"mid integer primary key autoincrement, " +
			"readtime text, " +
			"tagcode text, " +
			"repairclass text, " +
			"updated integer)"),

	// 插入一笔记录
	AddOne("insert into tbMotor(readtime, tagcode, repairclass, updated) values('<0>', '<1>', '<2>', 0)"),

	// 删除一笔记录
	DelOne("delete from tbMotor where mid in (<0>)"),

	// 全部删除
	DelAll("delete from tbMotor"),

	// 删除多余数据
	DelOut("delete from tbMotor where mid in (select mid from tbMotor order by readtime asc limit <0>)"),

	// 修改修程
	SetXiu("update tbMotor set repairclass = '<1>' where mid = <0>"),

	// 获取记录总数
	GetCount("select count(*) from tbMotor"),
//	GetCount("select count(*) from tbMotor where tagcode like '[<0>]%'"),
//	GetCountH("select count(*) from tbMotor where tagcode like 'T%' or tagcode like 'Q%' or tagcode like '!%'"),
//	GetCountJ("select count(*) from tbMotor where tagcode like 'J%' or tagcode like 'D%'"),
//	GetCountK("select count(*) from tbMotor where tagcode like 'K%'"),

	// 分页查询
	Get("select * from tbMotor order by readtime desc limit <0>, <1>"),
//	Get("select * from tbMotor where tagcode like '[<2>]%' limit <0>, <1>"),
//	GetH("select count(*) from tbMotor where tagcode like 'T%' or tagcode like 'Q%' or tagcode like '!%' limit <0>, <1>"),
//	GetJ("select count(*) from tbMotor where tagcode like 'J%' or tagcode like 'D%' limit <0>, <1>"),
//	GetK("select count(*) from tbMotor where tagcode like 'K%' limit <0>, <1>"),

	// 数据库名
	DbNam("motor");

	private final String sql;
	EmSql(String s) {
		sql = s;
	}
	@Override
	public String toString() {
		return sql;
	}
}
