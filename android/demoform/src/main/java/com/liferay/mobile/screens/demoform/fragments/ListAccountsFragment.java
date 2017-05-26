package com.liferay.mobile.screens.demoform.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.liferay.mobile.screens.base.interactor.CustomInteractorListener;
import com.liferay.mobile.screens.base.interactor.Interactor;
import com.liferay.mobile.screens.base.list.BaseListListener;
import com.liferay.mobile.screens.ddl.list.DDLListScreenlet;
import com.liferay.mobile.screens.ddl.model.Record;
import com.liferay.mobile.screens.demoform.R;
import com.liferay.mobile.screens.demoform.activities.MainActivity;
import com.liferay.mobile.screens.demoform.interactors.FilterByUserNameColumnInteractor;
import java.util.List;

public class ListAccountsFragment extends BaseNamedFragment
	implements BaseListListener<Record>, CustomInteractorListener {

	public static final int FRAGMENT_ID = 0;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_list_accounts, container, false);

		DDLListScreenlet ddlListScreenlet = (DDLListScreenlet) view.findViewById(R.id.ddl_form_screenlet);
		ddlListScreenlet.setListener(this);
		ddlListScreenlet.setUserId(0);
		ddlListScreenlet.setCustomInteractorListener(this);

		return view;
	}

	@Override
	public String getName() {
		return "Accounts";
	}

	@Override
	public void error(Exception e, String userAction) {

	}

	@Override
	public void onListPageFailed(int startRow, Exception e) {

	}

	@Override
	public void onListPageReceived(int startRow, int endRow, List<Record> entries, int rowCount) {

	}

	@Override
	public void onListItemSelected(Record record, View view) {
		((MainActivity) getActivity()).accountClicked(record);
	}

	@Override
	public Interactor createInteractor(String actionName) {
		return new FilterByUserNameColumnInteractor();
	}

	public static ListAccountsFragment newInstance() {
		Bundle args = new Bundle();
		ListAccountsFragment fragment = new ListAccountsFragment();
		fragment.setArguments(args);
		return fragment;
	}
}