package com.liferay.mobile.screens.audiencetargeting;

import android.content.Context;
import android.content.res.TypedArray;
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

		if (_listener != null) {
			_listener.onSuccess(event);
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

	public Integer getGroupId() {
		return _groupId;
	}

	public void setGroupId(final Integer groupId) {
		_groupId = groupId;
	}

	@Override
	protected View createScreenletView(Context context, AttributeSet attributes) {
		TypedArray typedArray = context.getTheme().obtainStyledAttributes(attributes, R.styleable.AudienceTargetingScreenlet, 0, 0);

		int layoutId = typedArray.getResourceId(R.styleable.AudienceTargetingScreenlet_layoutId, 0);

		_appName = typedArray.getString(R.styleable.AudienceTargetingScreenlet_screenletApp);
		if (_appName == null) {
			_appName = getResources().getString(R.string.app_name);
		}

		_groupId = typedArray.getInt(R.styleable.AudienceTargetingScreenlet_groupId, (int) LiferayServerContext.getGroupId());
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
			}
			catch (Exception e) {
				onFailure(e);
			}
		}
		else if (REQUEST_CONTENT.equals(userActionName)) {

			try {
				UserContext userContext = new UserContext();
				userContext.setUserId(SessionContext.getLoggedUser().getId());

				AudienceTargetingRequestContentInteractor loadScreenletsInteractor = (AudienceTargetingRequestContentInteractor) interactor;
				loadScreenletsInteractor.getContent((AudienceTargetingResult) args[0]);
			}
			catch (Exception e) {
				onFailure(e);
			}
		}
	}

	private static final String LOAD_SCREENLETS = "LOAD_SCREENLETS";
	private static final String REQUEST_CONTENT = "REQUEST_CONTENT";

	private AudienceTargetingListener _listener;
	private String _appName;
	private String _placeholder;
	private Integer _groupId;

}
