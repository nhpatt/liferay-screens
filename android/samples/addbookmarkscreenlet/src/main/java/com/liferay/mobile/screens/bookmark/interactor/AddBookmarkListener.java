package com.liferay.mobile.screens.bookmark.interactor;

import com.liferay.mobile.screens.base.interactor.AuthFailed;

/**
 * @author Javier Gamarra
 */
public interface AddBookmarkListener extends AuthFailed {
	void onAddBookmarkFailure(Exception exception);

	void onAddBookmarkSuccess();
}
