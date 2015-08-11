package com.liferay.mobile.screens.audiencetargeting;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Javier Gamarra
 */
public class UserContext {

	public Long getUserId() {
		return _userId;
	}

	public void setUserId(final Long userId) {
		_userId = userId;
	}

	public JSONObject toJSON() throws JSONException {
		JSONObject jsonObject = new JSONObject();

		jsonObject.put("userId", _userId);

		return jsonObject;
	}

	private Long _userId;

}
