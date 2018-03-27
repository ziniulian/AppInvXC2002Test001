package com.invengo.train.rfid;

import com.invengo.train.rfid.tag.BaseTag;

/**
 * Created by ziniulian on 2017/10/16.
 */

public interface InfCallBack {
	public void onReadTag (BaseTag tag);
	public void cb (EmCb e, String[] args);
}
