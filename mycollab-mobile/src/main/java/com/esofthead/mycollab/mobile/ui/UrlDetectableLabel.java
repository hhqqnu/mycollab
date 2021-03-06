/**
 * This file is part of mycollab-mobile.
 *
 * mycollab-mobile is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * mycollab-mobile is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with mycollab-mobile.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.esofthead.mycollab.mobile.ui;

import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;

/**
 * @author MyCollab Ltd.
 *
 * @since 4.5.0
 */
public class UrlDetectableLabel extends Label {
	private static final long serialVersionUID = -8041297963487354112L;

	public UrlDetectableLabel(String value) {
		super(formatExtraLink(value), ContentMode.HTML);
	}

	private static String formatExtraLink(String value) {
		if (value == null || "".equals(value)) {
			return "&nbsp;";
		}
		return value
				.replaceAll(
						"(?:https?|ftps?)://[\\w/%.-][/\\??\\w=?\\w?/%.-]?[/\\?&\\w=?\\w?/%.-]*",
						"<a href=\"$0\" target=\"_system\">$0</a>");
	}

}
