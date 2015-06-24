package com.liferay.mobile.screens.viewsets.defaultviews.audiencetargeting.renderers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;

import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.context.LiferayScreensContext;
import com.liferay.mobile.screens.context.LiferayServerContext;
import com.liferay.mobile.screens.viewsets.defaultviews.audiencetargeting.AudienceTargetingRenderer;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Javier Gamarra
 */
public class ImageRenderer extends AudienceTargetingRenderer {

	@Override
	protected View fillView(final View view, final JSONObject jsonObject) throws JSONException {
		RequestCreator request = createRequest(LiferayServerContext.getServer(), LiferayScreensContext.getContext(), jsonObject, 300);

		request.into((ImageView) view.findViewById(R.id.audience_image));
		return view;
	}

	@Override
	protected int getLayout() {
		return R.layout.audience_targeting_image_view_default;
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
