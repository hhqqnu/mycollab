/**
 * This file is part of mycollab-web.
 *
 * mycollab-web is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * mycollab-web is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with mycollab-web.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.esofthead.mycollab.module.user.accountsettings.profile.view;

import com.esofthead.mycollab.common.i18n.GenericI18Enum;
import com.esofthead.mycollab.eventmanager.EventBusFactory;
import com.esofthead.mycollab.module.user.accountsettings.localization.UserI18nEnum;
import com.esofthead.mycollab.module.user.accountsettings.view.events.ProfileEvent;
import com.esofthead.mycollab.module.user.domain.User;
import com.esofthead.mycollab.module.user.service.UserService;
import com.esofthead.mycollab.spring.ApplicationContextUtil;
import com.esofthead.mycollab.vaadin.AppContext;
import com.esofthead.mycollab.vaadin.ui.CountryComboBox;
import com.esofthead.mycollab.vaadin.ui.GridFormLayoutHelper;
import com.esofthead.mycollab.vaadin.ui.UIConstants;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 * 
 * @author MyCollab Ltd.
 * @since 1.0
 * 
 */
@SuppressWarnings("serial")
class AdvancedInfoChangeWindow extends Window {

	private TextField txtWebsite;
	private TextField txtCompany;
	private CountryComboBox cboCountry;

	private final User user;

	AdvancedInfoChangeWindow(final User user) {
		this.user = user;
		this.setWidth("450px");
		this.setResizable(false);
		this.setModal(true);
		this.initUI();
		this.center();
		this.setCaption(AppContext
				.getMessage(UserI18nEnum.WINDOW_CHANGE_ADVANCED_INFO_TITLE));
	}

	private void initUI() {
		final VerticalLayout mainLayout = new VerticalLayout();
		mainLayout.setWidth("100%");
		mainLayout.setMargin(new MarginInfo(false, false, true, false));
		mainLayout.setSpacing(true);

		final GridFormLayoutHelper passInfo = new GridFormLayoutHelper(1, 4,
				"100%", "150px", Alignment.TOP_LEFT);

		this.txtWebsite = (TextField) passInfo.addComponent(new TextField(),
				AppContext.getMessage(UserI18nEnum.FORM_WEBSITE), 0, 0);
		this.txtCompany = (TextField) passInfo.addComponent(new TextField(),
				AppContext.getMessage(UserI18nEnum.FORM_COMPANY), 0, 1);
		this.cboCountry = (CountryComboBox) passInfo.addComponent(
				new CountryComboBox(),
				AppContext.getMessage(UserI18nEnum.FORM_COUNTRY), 0, 2);

		this.txtWebsite.setValue(this.user.getWebsite() == null ? ""
				: this.user.getWebsite());
		this.txtCompany.setValue(this.user.getCompany() == null ? ""
				: this.user.getCompany());
		this.cboCountry.setValue(this.user.getCountry() == null ? ""
				: this.user.getCountry());

		passInfo.getLayout().setMargin(false);
		passInfo.getLayout().setWidth("100%");
		passInfo.getLayout().addStyleName("colored-gridlayout");
		mainLayout.addComponent(passInfo.getLayout());
		mainLayout.setComponentAlignment(passInfo.getLayout(),
				Alignment.TOP_LEFT);

		final HorizontalLayout hlayoutControls = new HorizontalLayout();
		hlayoutControls.setSpacing(true);
		hlayoutControls.setMargin(new MarginInfo(false, true, false, true));
		final Button cancelBtn = new Button(
				AppContext.getMessage(GenericI18Enum.BUTTON_CANCEL),
				new Button.ClickListener() {
					private static final long serialVersionUID = 1L;

					@Override
					public void buttonClick(final ClickEvent event) {
						AdvancedInfoChangeWindow.this.close();
					}
				});

		cancelBtn.setStyleName(UIConstants.THEME_GRAY_LINK);
		hlayoutControls.addComponent(cancelBtn);
		hlayoutControls.setComponentAlignment(cancelBtn,
				Alignment.MIDDLE_CENTER);

		final Button saveBtn = new Button(
				AppContext.getMessage(GenericI18Enum.BUTTON_SAVE),
				new Button.ClickListener() {
					private static final long serialVersionUID = 1L;

					@Override
					public void buttonClick(final ClickEvent event) {
						changeInfo();
					}
				});
		saveBtn.setStyleName(UIConstants.THEME_GREEN_LINK);
		hlayoutControls.addComponent(saveBtn);
		hlayoutControls.setComponentAlignment(saveBtn, Alignment.MIDDLE_CENTER);

		mainLayout.addComponent(hlayoutControls);
		mainLayout.setComponentAlignment(hlayoutControls,
				Alignment.MIDDLE_RIGHT);

		this.setModal(true);
		this.setContent(mainLayout);
	}

	private void changeInfo() {
		this.user.setWebsite(this.txtWebsite.getValue());
		this.user.setCompany(this.txtCompany.getValue());
		this.user.setCountry((String) this.cboCountry.getValue());

		final UserService userService = ApplicationContextUtil
				.getSpringBean(UserService.class);
		userService.updateWithSession(this.user, AppContext.getUsername());

		EventBusFactory.getInstance().post(
				new ProfileEvent.GotoProfileView(AdvancedInfoChangeWindow.this,
						null));
		AdvancedInfoChangeWindow.this.close();

		Page.getCurrent().getJavaScript().execute("window.location.reload();");
	}
}
