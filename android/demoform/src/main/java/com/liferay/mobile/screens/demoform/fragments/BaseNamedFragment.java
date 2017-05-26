package com.liferay.mobile.screens.demoform.fragments;

import android.support.v4.app.Fragment;

public abstract class BaseNamedFragment extends Fragment {

	public abstract String getName();

	public static final int FRAGMENT_ID = -1;

	@Override
	public void onResume() {
		super.onResume();

		getActivity().setTitle(getName());
	}
}
