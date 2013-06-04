/**
 * 
 */
package com.half.keyboard.utils;

/**
 * @author Thinkpad
 *
 */
public class Log {
	static final boolean debuging = true;

	public static void v(String tag, String msg) {
		if (debuging) {
			if (msg.length() > 1000) {
				for (int i = 0; i < msg.length() / 1000; i++) {
					int start = i * 1000;
					int end = (i + 1) * 1000 - 1;
					if (end > msg.length() - 1) {
						end = msg.length() - 1;
					}
					String subMsg = msg.substring(start, end);
					android.util.Log.v(tag, subMsg);
				}
			} else {
				android.util.Log.v(tag, msg);
			}
		}
	}

	public static void e(String tag, String msg) {
		if (debuging) {
			if (msg.length() > 1000) {
				for (int i = 0; i < msg.length() / 1000; i++) {
					int start = i * 1000;
					int end = (i + 1) * 1000 - 1;
					if (end > msg.length() - 1) {
						end = msg.length() - 1;
					}
					String subMsg = msg.substring(start, end);
					android.util.Log.e(tag, subMsg);
				}
			} else {
				android.util.Log.e(tag, msg);
			}
		}
	}

	public static void d(String tag, String msg) {
		if (debuging) {
			if (msg.length() > 1000) {
				for (int i = 0; i < msg.length() / 1000; i++) {
					int start = i * 1000;
					int end = (i + 1) * 1000 - 1;
					if (end > msg.length() - 1) {
						end = msg.length() - 1;
					}
					String subMsg = msg.substring(start, end);
					android.util.Log.d(tag, subMsg);
				}
			} else {
				android.util.Log.d(tag, msg);
			}
		}
	}

	public static void e(String tag, String msg, Throwable e) {
		if (debuging) {
			android.util.Log.e(tag, msg, e);
		}
	}
}
