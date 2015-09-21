package com.liferay.mobile.screens.push;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.WakefulBroadcastReceiver;

import com.liferay.mobile.screens.util.LiferayLogger;

/**
 * @author Javier Gamarra
 */
public abstract class AbstractPushReceiver<P extends AbstractPushService> extends WakefulBroadcastReceiver {

	public void onReceive(Context context, Intent intent) {
		LiferayLogger.e(intent.toString());
		ComponentName component = new ComponentName(context.getPackageName(), getPushServiceClass().getName());
		intent.setComponent(component);
		startWakefulService(context, intent);
	}

	@NonNull
	protected abstract Class<P> getPushServiceClass();
}
