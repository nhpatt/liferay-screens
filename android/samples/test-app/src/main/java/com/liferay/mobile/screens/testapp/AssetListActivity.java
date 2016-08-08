/*
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

package com.liferay.mobile.screens.testapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.liferay.mobile.screens.assetlist.AssetEntry;
import com.liferay.mobile.screens.assetlist.AssetListScreenlet;
import com.liferay.mobile.screens.base.list.BaseListListener;
import com.liferay.mobile.screens.base.list.BaseListScreenlet;
import com.liferay.mobile.screens.viewsets.defaultviews.DefaultAnimation;
import java.util.List;

/**
 * @author Javier Gamarra
 */
public class AssetListActivity extends ThemeActivity implements BaseListListener<AssetEntry> {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.asset_list);

		_screenlet = (AssetListScreenlet) findViewById(R.id.asset_list_screenlet);

		_screenlet.setClassNameId(getIntent().getLongExtra("classNameId", 20116));
		_screenlet.setListener(this);
	}

	@Override
	protected void onResume() {
		super.onResume();

		_screenlet.loadPage(0);
	}

	@Override
	public void onListPageFailed(BaseListScreenlet source, int startRow, int endRow, Exception e) {
		error("Page request failed", e);
	}

	@Override
	public void onListPageReceived(BaseListScreenlet source, int startRow, int endRow, List<AssetEntry> entries,
		int rowCount) {
		info("Row " + startRow + " received!");
	}

	@Override
	public void onListItemSelected(AssetEntry element, View view) {
		Intent intent = getIntentWithTheme(AssetDisplayActivity.class);
		intent.putExtra("entryId", Long.valueOf((String) element.getValues().get("entryId")));
		DefaultAnimation.startActivityWithAnimation(this, intent);
	}

	@Override
	public void loadingFromCache(boolean success) {

	}

	@Override
	public void retrievingOnline(boolean triedInCache, Exception e) {

	}

	@Override
	public void storingToCache(Object object) {

	}

	private AssetListScreenlet _screenlet;
}
