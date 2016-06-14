package com.liferay.mobile;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.liferay.mobile.screens.testapp.LoginActivity;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class LoginFailedReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		Intent loginIntent = new Intent(context, LoginActivity.class);
		loginIntent.setFlags(FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(loginIntent);
	}
}
