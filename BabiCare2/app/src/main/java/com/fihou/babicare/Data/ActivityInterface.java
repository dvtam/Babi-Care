package com.fihou.babicare.Data;

import android.app.Activity;



import java.util.Vector;

public interface ActivityInterface {
	public Activity getActivity();
	public void alertFinish(Vector<PrimitiveMessage> messages);
	public void setFinish();
	public void setupUserInfo();
	
	public void readTimeOut();
	public void updateGameMoneyView();
}
