package com.fihou.babicare.Data;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class MiDialog {

	public static void alert(String title, String message, Context context) {
		try {
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			builder.setTitle(title);
			builder.setMessage(message);
			builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
				}
			});
			builder.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void alert(int titleResId, String messageResId, Context context) {
		try {
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			builder.setTitle(titleResId);
			builder.setMessage(messageResId);
			builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
				}
			});
			builder.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
