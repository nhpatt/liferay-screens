/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * <p/>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p/>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.mobile.screens.ddl.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.liferay.mobile.screens.asset.AssetEntry;
import com.liferay.mobile.screens.util.JSONUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Jose Manuel Navarro
 */
public class Record extends AssetEntry implements WithDDM, Parcelable {

	public static final Parcelable.ClassLoaderCreator<Record> CREATOR = new ClassLoaderCreator<Record>() {

		@Override
		public Record createFromParcel(Parcel parcel, ClassLoader classLoader) {
			return new Record(parcel, classLoader);
		}

		public Record createFromParcel(Parcel in) {
			throw new AssertionError();
		}

		public Record[] newArray(int size) {
			return new Record[size];
		}
	};
	public static final String MODEL_VALUES = "modelValues";
	public static final String MODEL_ATTRIBUTES = "modelAttributes";
	private DDMStructure ddmStructure;
	private Long creatorUserId;
	private Long structureId;
	private Long recordSetId;
	private Long recordId;

	public Record() {
		super();
	}

	public Record(Map<String, Object> valuesAndAttributes, Locale locale) {
		super(valuesAndAttributes);

		ddmStructure = new DDMStructure(locale);
		parseServerValues();
	}

	public Record(Locale locale) {
		this(new HashMap<String, Object>(), locale);
	}

	private Record(Parcel in, ClassLoader loader) {
		super(in, loader);
		ddmStructure = in.readParcelable(DDMStructure.class.getClassLoader());
		creatorUserId = (Long) in.readValue(Long.class.getClassLoader());
		structureId = (Long) in.readValue(Long.class.getClassLoader());
		recordSetId = (Long) in.readValue(Long.class.getClassLoader());
		recordId = (Long) in.readValue(Long.class.getClassLoader());
	}

	public void refresh() {
		for (Field f : getDDMStructure().getFields()) {
			Object fieldValue = getServerValue(f.getName());
			if (fieldValue != null) {
				f.setCurrentValue(f.convertFromString(fieldValue.toString()));
			}
		}
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel destination, int flags) {
		super.writeToParcel(destination, flags);
		destination.writeParcelable(ddmStructure, flags);
		destination.writeValue(creatorUserId);
		destination.writeValue(structureId);
		destination.writeValue(recordSetId);
		destination.writeValue(recordId);
	}

	public long getRecordSetId() {
		return recordSetId;
	}

	public void setRecordSetId(long recordSetId) {
		this.recordSetId = recordSetId;
	}

	public long getRecordId() {
		return recordId;
	}

	public void setRecordId(long recordId) {
		this.recordId = recordId;
	}

	public long getStructureId() {
		return structureId;
	}

	public void setStructureId(long structureId) {
		this.structureId = structureId;
	}

	public long getCreatorUserId() {
		return creatorUserId;
	}

	public void setCreatorUserId(long value) {
		creatorUserId = value;
	}

	public Map<String, String> getData() {
		Map<String, String> values = new HashMap<>(getFields().size());

		for (Field f : getFields()) {
			String fieldValue = f.toData();

			//FIXME - LPS-49460
			// Server rejects the request if the value is empty string.
			// This way we workaround the problem but a field can't be
			// emptied when you're editing an existing row.
			if (fieldValue != null && !fieldValue.isEmpty()) {
				values.put(f.getName(), fieldValue);
			}
		}

		return values;
	}

	public void setValues(Map<String, Object> values) {
		for (Field f : getFields()) {
			Object fieldValue = values.get(f.getName());
			if (fieldValue != null) {
				f.setCurrentValue(f.convertFromString(fieldValue.toString()));
			}
		}
	}

	public boolean isRecordStructurePresent() {
		return (getFields().size() > 0);
	}

	/**
	 * renamed from getValue()
	 *
	 * @param field key of the field
	 * @return server value of that field
	 */
	public Object getServerValue(String field) {
		return getModelValues() == null ? null : getModelValues().get(field);
	}

	/**
	 * renamed from getAttributes()
	 *
	 * @param field key of the field
	 * @return server attribute of that field
	 */
	public Object getServerAttribute(String field) {
		return getModelAttributes() == null ? null : getModelAttributes().get(field);
	}

	public Map<String, Object> getValuesAndAttributes() {
		return values;
	}

	public void setValuesAndAttributes(Map<String, Object> valuesAndAttributes) {
		values = valuesAndAttributes;
		parseServerValues();
	}

	public Map<String, Object> getModelValues() {
		return (HashMap<String, Object>) values.get(MODEL_VALUES);
	}

	public Map<String, Object> getModelAttributes() {
		return (Map<String, Object>) values.get(MODEL_ATTRIBUTES);
	}

	@Override
	public DDMStructure getDDMStructure() {
		return ddmStructure;
	}

	public List<Field> getFields() {
		return ddmStructure.getFields();
	}

	public int getFieldCount() {
		return ddmStructure.getFieldCount();
	}

	public Field getField(int i) {
		return ddmStructure.getField(i);
	}

	public Locale getLocale() {
		return ddmStructure.getLocale();
	}

	public Field getFieldByName(String name) {
		return ddmStructure.getFieldByName(name);
	}

	public void parseDDMStructure(JSONObject jsonObject) throws JSONException {
		ddmStructure.parse(jsonObject);
	}

	private void parseServerValues() {
		//TODO refactor
		Long recordId = JSONUtil.castToLong(getServerAttribute("recordId"));
		if (recordId != null) {
			this.recordId = recordId;
		}
		Long recordSetId = JSONUtil.castToLong(getServerAttribute("recordSetId"));
		if (recordSetId != null) {
			this.recordSetId = recordSetId;
		}
		Long userId = JSONUtil.castToLong(getServerAttribute("userId"));
		if (userId != null) {
			creatorUserId = userId;
		}
	}
}