package com.invengo.train.rfid.xc2002;

import com.invengo.train.rfid.tag.BaseTag;
import com.invengo.train.rfid.tag.ParseTag;
import com.invengo.train.rfid.tag.TagUn;

/**
 * 位标解析测试
 * Created by ziniulian on 2018/3/20.
 */

public class RdTest001 extends Rd {
	protected BaseTag parse(byte[] bs) {
		TagUn ut = new TagUn();
		ut.setCod(ParseTag.parse(bs));
		return ut;
	}
}
