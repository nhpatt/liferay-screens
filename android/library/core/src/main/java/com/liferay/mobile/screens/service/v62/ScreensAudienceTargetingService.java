/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.mobile.screens.service.v62;

import com.liferay.mobile.android.service.BaseService;
import com.liferay.mobile.android.service.JSONObjectWrapper;
import com.liferay.mobile.android.service.Session;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Bruno Farache
 */
public class ScreensAudienceTargetingService extends BaseService {

	public ScreensAudienceTargetingService(Session session) {
		super(session);
	}

	public JSONObject addScreensMobile(long tacticId, String appId, String placeholderId, long assetEntryId, JSONObject customContentMap, JSONObjectWrapper serviceContext) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("tacticId", tacticId);
			_params.put("appId", checkNull(appId));
			_params.put("placeholderId", checkNull(placeholderId));
			_params.put("assetEntryId", assetEntryId);
			_params.put("customContentMap", checkNull(customContentMap));
			mangleWrapper(_params, "serviceContext", "com.liferay.portal.service.ServiceContext", serviceContext);

			_command.put("/channel-screens-mobile.screensmobile/add-screens-mobile", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		JSONArray _result = session.invoke(_command);

		if (_result == null) {
			return null;
		}

		return _result.getJSONObject(0);
	}

	public JSONArray getContent(String appId, long groupId, JSONObject userContext, JSONObjectWrapper serviceContext) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("appId", checkNull(appId));
			_params.put("groupId", groupId);
			_params.put("userContext", checkNull(userContext));
			mangleWrapper(_params, "serviceContext", "com.liferay.portal.service.ServiceContext", serviceContext);

			_command.put("/channel-screens-mobile.screensmobile/get-content", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		JSONArray _result = session.invoke(_command);

		if (_result == null) {
			return null;
		}

		return _result.getJSONArray(0);
	}

	public JSONArray getContent(String appId, long groupId, String placeholderId, JSONObject userContext, JSONObjectWrapper serviceContext) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("appId", checkNull(appId));
			_params.put("groupId", groupId);
			_params.put("placeholderId", checkNull(placeholderId));
			_params.put("userContext", checkNull(userContext));
			mangleWrapper(_params, "serviceContext", "com.liferay.portal.service.ServiceContext", serviceContext);

			_command.put("/channel-screens-mobile.screensmobile/get-content", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		JSONArray _result = session.invoke(_command);

		if (_result == null) {
			return null;
		}

		return _result.getJSONArray(0);
	}

	public JSONArray getContent(String appId, long groupId, JSONArray placeholderIds, JSONObject userContext, JSONObjectWrapper serviceContext) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("appId", checkNull(appId));
			_params.put("groupId", groupId);
			_params.put("placeholderIds", checkNull(placeholderIds));
			_params.put("userContext", checkNull(userContext));
			mangleWrapper(_params, "serviceContext", "com.liferay.portal.service.ServiceContext", serviceContext);

			_command.put("/channel-screens-mobile.screensmobile/get-content", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		JSONArray _result = session.invoke(_command);

		if (_result == null) {
			return null;
		}

		return _result.getJSONArray(0);
	}

	public JSONObject getScreensMobile(long screensMobileId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("screensMobileId", screensMobileId);

			_command.put("/channel-screens-mobile.screensmobile/get-screens-mobile", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		JSONArray _result = session.invoke(_command);

		if (_result == null) {
			return null;
		}

		return _result.getJSONObject(0);
	}

	public JSONObject updateScreensMobile(long screensMobileId, String appId, String placeholderId, long assetEntryId, JSONObject customContentMap, JSONObjectWrapper serviceContext) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("screensMobileId", screensMobileId);
			_params.put("appId", checkNull(appId));
			_params.put("placeholderId", checkNull(placeholderId));
			_params.put("assetEntryId", assetEntryId);
			_params.put("customContentMap", checkNull(customContentMap));
			mangleWrapper(_params, "serviceContext", "com.liferay.portal.service.ServiceContext", serviceContext);

			_command.put("/channel-screens-mobile.screensmobile/update-screens-mobile", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		JSONArray _result = session.invoke(_command);

		if (_result == null) {
			return null;
		}

		return _result.getJSONObject(0);
	}

}