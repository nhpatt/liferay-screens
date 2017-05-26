package com.liferay.mobile.screens.demoform.interactors;

import com.liferay.mobile.screens.base.list.interactor.BaseListEvent;
import com.liferay.mobile.screens.base.list.interactor.Query;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.ddl.form.interactor.DDLFormEvent;
import com.liferay.mobile.screens.ddl.list.interactor.DDLListInteractor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.json.JSONException;

public class FilterByUserNameColumnInteractor extends DDLListInteractor {

	@Override
	public BaseListEvent<DDLFormEvent> execute(Query query, Object... args) throws Exception {
		BaseListEvent event = super.execute(query, args);
		if (event != null) {
			event.setEntries(filter(event.getEntries()));
			event.setRowCount(event.getEntries().size());
		}
		return event;
	}

	private List<DDLFormEvent> filter(List<DDLFormEvent> events) throws JSONException {
		List<DDLFormEvent> filtered = new ArrayList<>();
		for (DDLFormEvent event : events) {
			Map<String, Object> modelValues = event.getModel().getModelValues();

			if (modelValues.containsKey("screename") && ((String) modelValues.get("screename")).contains(
				SessionContext.getCurrentUser().getScreenName())) {
				filtered.add(event);
			}
		}
		return filtered;
	}
}