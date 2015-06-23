package com.liferay.mobile.screens.viewsets.defaultviews.audiencetargeting.renderers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.liferay.mobile.screens.context.LiferayScreensContext;
import com.liferay.mobile.screens.context.LiferayServerContext;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Javier Gamarra
 */
public class ImageRenderer {

	public void render(ImageView imageView, JSONObject jsonObject) throws JSONException {
		RequestCreator request = createRequest(LiferayServerContext.getServer(), LiferayScreensContext.getContext(), jsonObject, 300);
		request.into(imageView);
	}

	public RequestCreator createRequest(String server, Context context, JSONObject result, int targetWidth)
		throws JSONException {

		String url = constructUrl(result, server);
		return createPicassoRequest(context, targetWidth, url);
	}

	@NonNull
	private String constructUrl(JSONObject result, String server) throws JSONException {
		Integer groupId = result.getInt("groupId");
		Integer folderId = result.getInt("folderId");
		String name = result.getString("name");
		String uuid = result.getString("uuid");

		return server + "/documents/" + groupId + "/" + folderId + "/" + name + "/" + uuid;
	}

	private RequestCreator createPicassoRequest(Context context, int targetWidth, String url) {
		return Picasso.with(context).load(url).resize(targetWidth, targetWidth);
	}

}
