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
package com.esofthead.mycollab.mobile.module.crm.view.account;

import com.esofthead.mycollab.core.arguments.NumberSearchField;
import com.esofthead.mycollab.core.arguments.SearchField;
import com.esofthead.mycollab.eventmanager.EventBusFactory;
import com.esofthead.mycollab.mobile.module.crm.ui.AbstractRelatedListView;
import com.esofthead.mycollab.mobile.module.crm.view.lead.LeadListDisplay;
import com.esofthead.mycollab.mobile.shell.events.ShellEvent;
import com.esofthead.mycollab.module.crm.domain.Account;
import com.esofthead.mycollab.module.crm.domain.SimpleLead;
import com.esofthead.mycollab.module.crm.domain.criteria.LeadSearchCriteria;
import com.esofthead.mycollab.module.crm.i18n.LeadI18nEnum;
import com.esofthead.mycollab.vaadin.AppContext;
import com.esofthead.vaadin.navigationbarquickmenu.NavigationBarQuickMenu;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;

/**
 * 
 * @author MyCollab Ltd.
 * @since 4.0
 * 
 */
public class AccountRelatedLeadView extends
		AbstractRelatedListView<SimpleLead, LeadSearchCriteria> {
	private static final long serialVersionUID = -6563776375301107391L;
	private Account account;

	public AccountRelatedLeadView() {
		initUI();
	}

	private void initUI() {
		this.setCaption(AppContext
				.getMessage(LeadI18nEnum.M_TITLE_RELATED_LEADS));
		this.itemList = new LeadListDisplay();
		this.setContent(itemList);
	}

	public void displayLeads(final Account account) {
		this.account = account;
		loadLeads();
	}

	private void loadLeads() {
		final LeadSearchCriteria criteria = new LeadSearchCriteria();
		criteria.setSaccountid(new NumberSearchField(SearchField.AND,
				AppContext.getAccountId()));
		criteria.setAccountId(new NumberSearchField(SearchField.AND, account
				.getId()));
		setSearchCriteria(criteria);
	}

	@Override
	public void refresh() {
		loadLeads();
	}

	@Override
	protected Component createRightComponent() {
		final NavigationBarQuickMenu addLead = new NavigationBarQuickMenu();
		addLead.setStyleName("add-btn");

		VerticalLayout addButtons = new VerticalLayout();
		addButtons.setSpacing(true);
		addButtons.setWidth("100%");
		addButtons.setMargin(true);
		addButtons.addStyleName("edit-btn-layout");

		Button newLead = new Button(
				AppContext.getMessage(LeadI18nEnum.VIEW_NEW_TITLE));
		newLead.addClickListener(new Button.ClickListener() {

			private static final long serialVersionUID = 8228954365650824438L;

			@Override
			public void buttonClick(Button.ClickEvent arg0) {
				fireNewRelatedItem("");
			}
		});
		addButtons.addComponent(newLead);

		Button selectLead = new Button(
				AppContext.getMessage(LeadI18nEnum.M_TITLE_SELECT_LEADS));
		selectLead.addClickListener(new Button.ClickListener() {

			private static final long serialVersionUID = 9076596614526838523L;

			@Override
			public void buttonClick(Button.ClickEvent event) {
				AccountLeadSelectionView leadSelectionView = new AccountLeadSelectionView(
						AccountRelatedLeadView.this);
				final LeadSearchCriteria criteria = new LeadSearchCriteria();
				criteria.setSaccountid(new NumberSearchField(AppContext
						.getAccountId()));
				leadSelectionView.setSearchCriteria(criteria);
				EventBusFactory.getInstance().post(
						new ShellEvent.PushView(AccountRelatedLeadView.this,
								leadSelectionView));

			}
		});
		addButtons.addComponent(selectLead);

		addLead.setContent(addButtons);

		return addLead;
	}

}
