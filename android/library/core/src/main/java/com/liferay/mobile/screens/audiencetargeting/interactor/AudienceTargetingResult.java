package com.liferay.mobile.screens.audiencetargeting.interactor;

import com.liferay.mobile.screens.util.LiferayLogger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Javier Gamarra
 */
public class AudienceTargetingResult implements Comparable<AudienceTargetingResult> {

	public AudienceTargetingResult(final JSONObject object) {
		try {
			_campaignId = object.getInt("campaignId");
			_className = object.getString("className");
			_classPK = object.getInt("classPK");
			_customContent = object.getString("customContent");
			_placeholderId = object.getString("placeholderId");
			_placeholderKey = object.getString("placeholderKey");
			_priority = object.getInt("priority");
			_object = object;

			JSONArray userSegmentIds = (JSONArray) object.get("userSegmentIds");

			for (int i = 0; i < userSegmentIds.length(); i++) {
				_userSegmentIds.add(userSegmentIds.getLong(i));
			}
		}
		catch (JSONException e) {
			LiferayLogger.e("Error parsing an audience targeting result");
		}
	}

	public boolean isCustomContent() {
		return "".equals(_className);
	}

	public Integer getCampaignId() {
		return _campaignId;
	}

	public void setCampaignId(final Integer campaignId) {
		_campaignId = campaignId;
	}

	public String getClassName() {
		return _className;
	}

	public void setClassName(final String className) {
		_className = className;
	}

	public Integer getClassPK() {
		return _classPK;
	}

	public void setClassPK(final Integer classPK) {
		_classPK = classPK;
	}

	public String getCustomContent() {
		return _customContent;
	}

	public void setCustomContent(final String customContent) {
		_customContent = customContent;
	}

	public String getPlaceholderId() {
		return _placeholderId;
	}

	public void setPlaceholderId(final String placeholderId) {
		_placeholderId = placeholderId;
	}

	public Integer getPriority() {
		return _priority;
	}

	public void setPriority(final Integer priority) {
		_priority = priority;
	}

	public List<Long> getUserSegmentIds() {
		return _userSegmentIds;
	}

	public void setUserSegmentIds(final List<Long> userSegmentIds) {
		_userSegmentIds = userSegmentIds;
	}

	public JSONObject getObject() {
		return _object;
	}

	@Override
	public int compareTo(final AudienceTargetingResult another) {
		if (_priority == null) {
			return -1;
		}
		else if (another == null || another.getPriority() == null) {
			return 1;
		}
		else {
			return _priority.compareTo(another.getPriority());
		}
	}

	public String getPlaceholderKey() {
		return _placeholderKey;
	}

	private Integer _campaignId;
	private String _className;
	private Integer _classPK;
	private String _customContent;
	private String _placeholderId;
	private Integer _priority;
	private List<Long> _userSegmentIds = new ArrayList<>();
	private JSONObject _object;
	private String _placeholderKey;
}
