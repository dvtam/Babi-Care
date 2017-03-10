package com.fihou.babicare.Data;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONUtils {
	public static String getString(JSONObject data, String key, String defValue) {
		if (data.has(key)) {
			try {
				return data.getString(key);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return defValue;
	}

	public static int getInt(JSONObject data, String key, int defValue) {
		if (data.has(key)) {
			try {
				return data.getInt(key);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return defValue;
	}

	public static float getFloat(JSONObject data, String key, float defValue) {
		if (data.has(key)) {
			try {
				return (float) data.getDouble(key);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return defValue;
	}

	public static boolean getBoolean(JSONObject data, String key, boolean defValue) {
		if (data.has(key)) {
			try {
				return data.getBoolean(key);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return defValue;
	}
}
