package com.liferay.mobile.screens.audiencetargeting;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Javier Gamarra
 */
public class UserContext {

	private Long userId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(final Long userId) {
		this.userId = userId;
	}

	public JSONObject toJSON() throws JSONException {
		JSONObject jsonObject = new JSONObject();

		jsonObject.put("userId", userId);

		return jsonObject;
	}

}
