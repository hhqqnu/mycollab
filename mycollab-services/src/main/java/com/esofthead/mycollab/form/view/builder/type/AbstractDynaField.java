/**
 * This file is part of mycollab-services.
 *
 * mycollab-services is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * mycollab-services is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with mycollab-services.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.esofthead.mycollab.form.view.builder.type;

import com.esofthead.mycollab.core.utils.JsonDeSerializer.Exclude;

/**
 * 
 * @author MyCollab Ltd.
 * @since 1.0
 *
 */
public class AbstractDynaField implements Comparable<AbstractDynaField> {
	public static final String TEXT_FIELD_1 = "text1";
	public static final String TEXT_FIELD_2 = "text2";
	public static final String TEXT_FIELD_3 = "text3";
	public static final String TEXT_FIELD_4 = "text4";
	public static final String TEXT_FIELD_5 = "text5";

	public static final String[] TEXT_FIELD_ARR = { TEXT_FIELD_1, TEXT_FIELD_2,
			TEXT_FIELD_3, TEXT_FIELD_4, TEXT_FIELD_5 };

	public static final String TEXTAREA_FIELD_1 = "textarea1";
	public static final String TEXTAREA_FIELD_2 = "textarea2";
	public static final String TEXTAREA_FIELD_3 = "textarea3";
	public static final String TEXTAREA_FIELD_4 = "textarea4";
	public static final String TEXTAREA_FIELD_5 = "textarea5";

	@Exclude
	private int fieldIndex;

	@Exclude
	private String fieldName;

	@Exclude
	private String displayName;

	@Exclude
	private boolean isMandatory = false;

	@Exclude
	private boolean isRequired = false;

	@Exclude
	private boolean isCustom = false;

	@Exclude
	private DynaSection ownSection;

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public int getFieldIndex() {
		return fieldIndex;
	}

	public void setFieldIndex(int fieldIndex) {
		this.fieldIndex = fieldIndex;
	}

	public boolean isRequired() {
		return isRequired;
	}

	public void setRequired(boolean isRequired) {
		this.isRequired = isRequired;
	}

	public DynaSection getOwnSection() {
		return ownSection;
	}

	public void setOwnSection(DynaSection ownSection) {
		this.ownSection = ownSection;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public boolean isCustom() {
		return isCustom;
	}

	public void setCustom(boolean isCustom) {
		this.isCustom = isCustom;
	}

	public boolean isMandatory() {
		return isMandatory;
	}

	public void setMandatory(boolean isMandatory) {
		this.isMandatory = isMandatory;
	}

	@Override
	public int compareTo(AbstractDynaField paramT) {
		return (this.fieldIndex - paramT.fieldIndex);
	}
}
