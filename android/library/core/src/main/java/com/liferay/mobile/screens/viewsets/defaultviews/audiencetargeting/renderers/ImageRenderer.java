package com.liferay.mobile.screens.viewsets.defaultviews.audiencetargeting.renderers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;

import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.audiencetargeting.interactor.AudienceTargetingResult;
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

	public RequestCreator createRequest(String server, Context context, JSONObject result, Integer targetWidth)
		throws JSONException {

		String url = constructUrl(result, server);
		return createPicassoRequest(context, targetWidth, url);
	}

	@Override
	protected View fillView(final View view, AudienceTargetingResult result, final Object object) throws JSONException {
		JSONObject jsonObject = (JSONObject) object;
		RequestCreator request = createRequest(LiferayServerContext.getServer(), LiferayScreensContext.getContext(), jsonObject, null);

		request.into((ImageView) view.findViewById(R.id.audience_image));
		return view;
	}

	@Override
	protected int getLayout() {
		return R.layout.audience_targeting_image_view_default;
	}

	@NonNull
	private String constructUrl(JSONObject result, String server) throws JSONException {
		Integer groupId = result.getInt("groupId");
		Integer folderId = result.getInt("folderId");
		String name = result.getString("name");
		String uuid = result.getString("uuid");

		return server + "/documents/" + groupId + "/" + folderId + "/" + name + "/" + uuid;
	}

	private RequestCreator createPicassoRequest(Context context, Integer targetWidth, String url) {
		RequestCreator load = Picasso.with(context).load(url);
		if (targetWidth != null) {
			return load.resize(targetWidth, targetWidth);
		}
		return load;
	}

}
