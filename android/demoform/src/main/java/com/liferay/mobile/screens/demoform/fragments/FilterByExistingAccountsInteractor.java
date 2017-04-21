package com.liferay.mobile.screens.demoform.fragments;

import com.liferay.mobile.screens.base.list.interactor.BaseListEvent;
import com.liferay.mobile.screens.base.list.interactor.Query;
import com.liferay.mobile.screens.context.LiferayScreensContext;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.ddl.form.connector.ScreensDDLRecordConnector;
import com.liferay.mobile.screens.ddl.form.interactor.DDLFormEvent;
import com.liferay.mobile.screens.ddl.list.interactor.DDLListInteractor;
import com.liferay.mobile.screens.demoform.R;
import com.liferay.mobile.screens.util.ServiceProvider;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class FilterByExistingAccountsInteractor extends DDLListInteractor {

	@Override
	public BaseListEvent<DDLFormEvent> execute(Query query, Object... args) throws Exception {
		BaseListEvent<DDLFormEvent> event = super.execute(query, args);

		if (event != null) {

			ScreensDDLRecordConnector ddlRecordService =
				ServiceProvider.getInstance().getScreensDDLRecordConnector(getSession());
			String localeString = locale.toString();

			String accountId = LiferayScreensContext.getContext().getResources().getString(R.string.account_id);

			JSONArray accounts = ddlRecordService.getDdlRecords(Long.valueOf(accountId), localeString, 0, 1000, null);

			List<String> filtered = new ArrayList<>();

			for (int i = 0; i < accounts.length(); i++) {
				JSONObject jsonObject = accounts.getJSONObject(i);
				JSONObject modelValues = jsonObject.getJSONObject("modelValues");

				if (modelValues.has("screename") && (modelValues.getString("screename")).contains(
					SessionContext.getCurrentUser().getScreenName())) {
					String type = modelValues.getString("type");
					if (type != null) {
						filtered.add(type.replace("[\"", "").replace("\"]", ""));
					}
				}
			}

			List<DDLFormEvent> filteredEvents = new ArrayList<>();

			for (DDLFormEvent formEntry : event.getEntries()) {
				String name = (String) formEntry.getModel().getServerValue("Name");
				if (!filtered.contains(name)) {
					filteredEvents.add(formEntry);
				}
			}

			event.setEntries(filteredEvents);
			event.setRowCount(filteredEvents.size());
		}
		return event;
	}
}
