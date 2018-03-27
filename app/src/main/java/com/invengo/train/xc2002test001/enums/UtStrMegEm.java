package com.invengo.train.xc2002test001.enums;

/**
 * Created by ziniulian on 2017/10/17.
 */

public class UtStrMegEm {
	public static String meg(String s, String[] args) {
		for (int i = 0; i < args.length; i ++) {
			s = s.replaceAll(("<" + i + '>'), args[i]);
		}
		return s;
	}
}
