package com.liferay.mobile.screens.audiencetargeting;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.audiencetargeting.interactor.AudienceTargetingInteractor;
import com.liferay.mobile.screens.audiencetargeting.interactor.AudienceTargetingResult;
import com.liferay.mobile.screens.audiencetargeting.interactor.loadscreenlets.AudienceTargetingLoadScreenletsInteractor;
import com.liferay.mobile.screens.audiencetargeting.interactor.loadscreenlets.AudienceTargetingLoadScreenletsInteractorImpl;
import com.liferay.mobile.screens.audiencetargeting.interactor.loadscreenlets.AudienceTargetingScreenletsLoadedEvent;
import com.liferay.mobile.screens.audiencetargeting.interactor.requestcontent.AudienceTargetingContentRequestedEvent;
import com.liferay.mobile.screens.audiencetargeting.interactor.requestcontent.AudienceTargetingRequestContentInteractor;
import com.liferay.mobile.screens.audiencetargeting.interactor.requestcontent.AudienceTargetingRequestContentInteractorImpl;
import com.liferay.mobile.screens.audiencetargeting.view.AudienceTargetingViewModel;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.context.LiferayServerContext;
import com.liferay.mobile.screens.context.SessionContext;

import java.util.List;

/**
 * @author Javier Gamarra
 */
public class AudienceTargetingScreenlet
		extends BaseScreenlet<AudienceTargetingViewModel, AudienceTargetingInteractor>
		implements AudienceTargetingListener {

	public AudienceTargetingScreenlet(Context context) {
		super(context);
	}

	public AudienceTargetingScreenlet(Context context, AttributeSet attributes) {
		super(context, attributes);
	}

	public AudienceTargetingScreenlet(Context context, AttributeSet attributes, int defaultStyle) {
		super(context, attributes, defaultStyle);
	}

	public void onSuccess(AudienceTargetingScreenletsLoadedEvent event) {
		List<AudienceTargetingResult> results = event.getResults();
		AudienceTargetingManager.storeAudienceResults(results);

		if (_listener != null) {
			_listener.onSuccess(event);
		}

		if (_loadContentAfterLoad) {
			_loadContentAfterLoad = false;
			if (!results.isEmpty()) {
				loadContent(results.get(0));
			}
		}
	}

	@Override
	public void onSuccess(final AudienceTargetingContentRequestedEvent event) {
		getViewModel().showAudienceContent(event);

		if (_listener != null) {
			_listener.onSuccess(event);
		}

		_loadContentAfterLoad = false;
	}

	@Override
	protected void onScreenletAttached() {
		if (_autoLoad) {
			loadAndLoadContent();
		}
	}

	public void onFailure(Exception e) {
		getViewModel().showFailedOperation(null, e);

		if (_listener != null) {
			_listener.onFailure(e);
		}
	}

	public void load() {
		performUserAction(LOAD_SCREENLETS);
	}

	public void load(final String placeholder) {
		performUserAction(LOAD_SCREENLETS, placeholder);
	}

	public void loadContent(final AudienceTargetingResult audienceTargetingResult) {
		performUserAction(REQUEST_CONTENT, audienceTargetingResult);
	}

	private void loadAndLoadContent() {
		_loadContentAfterLoad = true;
		performUserAction(LOAD_SCREENLETS, _placeholder);
	}

	public boolean isAutoLoad() {
		return _autoLoad;
	}

	public void setAutoLoad(boolean value) {
		_autoLoad = value;
	}

	public AudienceTargetingListener getListener() {
		return _listener;
	}

	public void setListener(AudienceTargetingListener listener) {
		_listener = listener;
	}

	public String getAppName() {
		return _appName;
	}

	public void setAppName(final String appName) {
		_appName = appName;
	}

	public String getPlaceholder() {
		return _placeholder;
	}

	public void setPlaceholder(final String placeholder) {
		_placeholder = placeholder;
	}

	public Long getGroupId() {
		return _groupId;
	}

	public void setGroupId(final Long groupId) {
		_groupId = groupId;
	}

	@Override
	protected View createScreenletView(Context context, AttributeSet attributes) {
		TypedArray typedArray = context.getTheme().obtainStyledAttributes(attributes, R.styleable.AudienceTargetingScreenlet, 0, 0);

		int layoutId = typedArray.getResourceId(R.styleable.AudienceTargetingScreenlet_layoutId, getDefaultLayoutId());

		_autoLoad = typedArray.getBoolean(R.styleable.AudienceTargetingScreenlet_autoLoad, false);
		_appName = typedArray.getString(R.styleable.AudienceTargetingScreenlet_screenletApp);
		if (_appName == null) {
			_appName = getResources().getString(R.string.app_name);
		}

		_groupId = (long) typedArray.getInt(R.styleable.AudienceTargetingScreenlet_groupId, (int) LiferayServerContext.getGroupId());
		_placeholder = typedArray.getString(R.styleable.AudienceTargetingScreenlet_placeholder);

		View view = LayoutInflater.from(context).inflate(layoutId, null);

		typedArray.recycle();

		return view;
	}

	@Override
	protected AudienceTargetingInteractor createInteractor(String actionName) {
		if (LOAD_SCREENLETS.equals(actionName)) {
			return new AudienceTargetingLoadScreenletsInteractorImpl(getScreenletId());
		}
		else if (REQUEST_CONTENT.equals(actionName)) {
			return new AudienceTargetingRequestContentInteractorImpl(getScreenletId());
		}
		return null;
	}

	@Override
	protected void onUserAction(final String userActionName, final AudienceTargetingInteractor interactor, final Object[] args) {

		if (LOAD_SCREENLETS.equals(userActionName)) {
			try {
				UserContext userContext = new UserContext();
				userContext.setUserId(SessionContext.getLoggedUser().getId());

				AudienceTargetingLoadScreenletsInteractor loadScreenletsInteractor = (AudienceTargetingLoadScreenletsInteractor) interactor;

				if ((args != null && args.length > 0) || _placeholder != null) {
					String placeholder = (args != null && args.length > 0) ? (String) args[0] : _placeholder;
					loadScreenletsInteractor.getScreenlets(_appName, _groupId, placeholder, userContext);
				}
				else {
					loadScreenletsInteractor.getScreenlets(_appName, _groupId, userContext);
				}
			} catch (Exception e) {
				onFailure(e);
			}
		}
		else if (REQUEST_CONTENT.equals(userActionName)) {

			try {
				UserContext userContext = new UserContext();
				userContext.setUserId(SessionContext.getLoggedUser().getId());

				AudienceTargetingRequestContentInteractor loadScreenletsInteractor = (AudienceTargetingRequestContentInteractor) interactor;
				loadScreenletsInteractor.getContent((AudienceTargetingResult) args[0]);
			} catch (Exception e) {
				onFailure(e);
			}
		}
	}

	@Override
	protected Parcelable onSaveInstanceState() {
		Parcelable superState = super.onSaveInstanceState();

		Bundle state = new Bundle();
		state.putParcelable(_STATE_SUPER, superState);

		state.putBoolean(_STATE_LOAD_CONTENT_AFTER_LOAD, _loadContentAfterLoad);
		state.putBoolean(_STATE_AUTO_LOAD, _autoLoad);
		state.putLong(_STATE_GROUP_ID, _groupId);
		state.putString(_STATE_APP_NAME, _appName);
		state.putString(_STATE_PLACEHOLDER, _placeholder);

		return state;
	}

	@Override
	protected void onRestoreInstanceState(Parcelable inState) {

		Bundle state = (Bundle) inState;

		_autoLoad = state.getBoolean(_STATE_AUTO_LOAD);
		_appName = state.getString(_STATE_APP_NAME);
		_groupId = state.getLong(_STATE_GROUP_ID);
		_loadContentAfterLoad = state.getBoolean(_STATE_LOAD_CONTENT_AFTER_LOAD);
		_placeholder = state.getString(_STATE_PLACEHOLDER);

		Parcelable superState = state.getParcelable(_STATE_SUPER);

		super.onRestoreInstanceState(superState);
	}

	private static final String LOAD_SCREENLETS = "LOAD_SCREENLETS";
	private static final String REQUEST_CONTENT = "REQUEST_CONTENT";

	private static final String _STATE_SUPER = "STATE_SUPER";
	private static final String _STATE_LOAD_CONTENT_AFTER_LOAD = "STATE_LOAD_CONTENT_AFTER_LOAD";
	private static final String _STATE_AUTO_LOAD = "STATE_AUTO_LOAD";
	private static final String _STATE_GROUP_ID = "STATE_GROUP_ID";
	private static final String _STATE_APP_NAME = "STATE_APP_NAME";
	private static final String _STATE_PLACEHOLDER = "STATE_PLACEHOLDER";

	private AudienceTargetingListener _listener;
	private boolean _loadContentAfterLoad = true;
	private boolean _autoLoad;
	private String _appName;
	private String _placeholder;
	private Long _groupId;

}
