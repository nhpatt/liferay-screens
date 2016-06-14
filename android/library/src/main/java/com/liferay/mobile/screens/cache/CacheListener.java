package com.liferay.mobile.screens.cache;

import com.liferay.mobile.screens.base.interactor.AuthFailed;
import com.liferay.mobile.screens.webcontentdisplay.interactor.WebContentDisplayEvent;

/**
 * @author Javier Gamarra
 */
public interface CacheListener extends AuthFailed {

	void loadingFromCache(boolean success);

	void retrievingOnline(boolean triedInCache, Exception e);

	void storingToCache(Object object);
}
