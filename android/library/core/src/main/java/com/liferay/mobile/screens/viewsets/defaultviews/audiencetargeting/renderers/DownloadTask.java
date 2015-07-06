package com.liferay.mobile.screens.viewsets.defaultviews.audiencetargeting.renderers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;

import com.liferay.mobile.android.util.download.DownloadProgressCallback;
import com.liferay.mobile.android.util.download.DownloadUtil;
import com.liferay.mobile.screens.context.LiferayScreensContext;
import com.liferay.mobile.screens.context.LiferayServerContext;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.util.LiferayLogger;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;

/**
 * @author Javier Gamarra
 */
public class DownloadTask implements Runnable {

	public DownloadTask(final Context context, final Object object) {
		_context = new WeakReference<>(context);
		_object = object;
	}

	@Override
	public void run() {
		try {
			JSONObject jsonObject = (JSONObject) _object;

			final Activity activity = LiferayScreensContext.getActivityFromContext(_context.get());

			//TODO show progress in callback
			final DownloadProgressCallback callback = null;

			File file = new File(Environment.getExternalStorageDirectory() + "/screens/");
			OutputStream os = new FileOutputStream(file);

			String url = constructUrl(jsonObject, LiferayServerContext.getServer());

			DownloadUtil.download(SessionContext.createSessionFromCurrentSession(), url, os,
				callback);

			Intent intent = new Intent();
			intent.setAction(Intent.ACTION_VIEW);
			intent.setDataAndType(Uri.fromFile(file), jsonObject.getString("mimeType"));

			activity.startActivity(intent);
		}
		catch (Exception e) {
			LiferayLogger.e("Could not download the audience targeting file", e);
		}
	}

	@NonNull
	private String constructUrl(JSONObject result, String server) throws JSONException {
		Integer groupId = result.getInt("groupId");
		Integer folderId = result.getInt("folderId");
		String name = result.getString("name");
		String uuid = result.getString("uuid");

		return server + "/documents/" + groupId + "/" + folderId + "/" + name + "/" + uuid;
	}

	private WeakReference<Context> _context;
	private Object _object;

}
