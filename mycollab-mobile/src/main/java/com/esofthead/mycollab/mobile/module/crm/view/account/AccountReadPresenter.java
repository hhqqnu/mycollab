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
package com.esofthead.mycollab.mobile.module.crm.view.account;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;

import com.esofthead.mycollab.common.i18n.GenericI18Enum;
import com.esofthead.mycollab.core.arguments.NumberSearchField;
import com.esofthead.mycollab.eventmanager.EventBusFactory;
import com.esofthead.mycollab.mobile.module.crm.events.AccountEvent;
import com.esofthead.mycollab.mobile.module.crm.events.ActivityEvent;
import com.esofthead.mycollab.mobile.module.crm.events.CaseEvent;
import com.esofthead.mycollab.mobile.module.crm.events.ContactEvent;
import com.esofthead.mycollab.mobile.module.crm.events.LeadEvent;
import com.esofthead.mycollab.mobile.module.crm.events.OpportunityEvent;
import com.esofthead.mycollab.mobile.shell.events.ShellEvent;
import com.esofthead.mycollab.mobile.ui.AbstractMobilePresenter;
import com.esofthead.mycollab.mobile.ui.ConfirmDialog;
import com.esofthead.mycollab.module.crm.CrmLinkGenerator;
import com.esofthead.mycollab.module.crm.CrmTypeConstants;
import com.esofthead.mycollab.module.crm.domain.Account;
import com.esofthead.mycollab.module.crm.domain.AccountLead;
import com.esofthead.mycollab.module.crm.domain.SimpleAccount;
import com.esofthead.mycollab.module.crm.domain.SimpleActivity;
import com.esofthead.mycollab.module.crm.domain.SimpleCall;
import com.esofthead.mycollab.module.crm.domain.SimpleCase;
import com.esofthead.mycollab.module.crm.domain.SimpleContact;
import com.esofthead.mycollab.module.crm.domain.SimpleLead;
import com.esofthead.mycollab.module.crm.domain.SimpleMeeting;
import com.esofthead.mycollab.module.crm.domain.SimpleOpportunity;
import com.esofthead.mycollab.module.crm.domain.SimpleTask;
import com.esofthead.mycollab.module.crm.domain.criteria.AccountSearchCriteria;
import com.esofthead.mycollab.module.crm.service.AccountService;
import com.esofthead.mycollab.module.crm.service.ContactService;
import com.esofthead.mycollab.security.RolePermissionCollections;
import com.esofthead.mycollab.spring.ApplicationContextUtil;
import com.esofthead.mycollab.vaadin.AppContext;
import com.esofthead.mycollab.vaadin.events.DefaultPreviewFormHandler;
import com.esofthead.mycollab.vaadin.mvp.ScreenData;
import com.esofthead.mycollab.vaadin.ui.NotificationUtil;
import com.esofthead.mycollab.vaadin.ui.RelatedListHandler;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.UI;

/**
 * 
 * @author MyCollab Ltd.
 * @since 1.0
 * 
 */
public class AccountReadPresenter extends
		AbstractMobilePresenter<AccountReadView> {

	private static final long serialVersionUID = 1L;

	public AccountReadPresenter() {
		super(AccountReadView.class);
	}

	@Override
	protected void postInitView() {
		view.getPreviewFormHandlers().addFormHandler(
				new DefaultPreviewFormHandler<SimpleAccount>() {
					@Override
					public void onEdit(SimpleAccount data) {
						EventBusFactory.getInstance().post(
								new AccountEvent.GotoEdit(this, data));
					}

					@Override
					public void onDelete(final SimpleAccount data) {

						ConfirmDialog.show(
								UI.getCurrent(),
								AppContext
										.getMessage(GenericI18Enum.DIALOG_DELETE_SINGLE_ITEM_MESSAGE),
								AppContext
										.getMessage(GenericI18Enum.BUTTON_YES),
								AppContext
										.getMessage(GenericI18Enum.BUTTON_NO),
								new ConfirmDialog.CloseListener() {
									private static final long serialVersionUID = 1L;

									@Override
									public void onClose(ConfirmDialog dialog) {
										if (dialog.isConfirmed()) {
											AccountService accountService = ApplicationContextUtil
													.getSpringBean(AccountService.class);
											accountService.removeWithSession(
													data.getId(),
													AppContext.getUsername(),
													AppContext.getAccountId());
											EventBusFactory.getInstance().post(
													new AccountEvent.GotoList(
															this, null));
										}
									}
								});

					}

					@Override
					public void onClone(SimpleAccount data) {
						Account cloneData = (Account) data.copy();
						cloneData.setId(null);
						EventBusFactory.getInstance().post(
								new AccountEvent.GotoEdit(this, cloneData));
					}

					@Override
					public void onCancel() {
						EventBusFactory.getInstance().post(
								new AccountEvent.GotoList(this, null));
					}

					@Override
					public void gotoNext(SimpleAccount data) {
						AccountService accountService = ApplicationContextUtil
								.getSpringBean(AccountService.class);
						AccountSearchCriteria criteria = new AccountSearchCriteria();
						criteria.setSaccountid(new NumberSearchField(AppContext
								.getAccountId()));
						criteria.setId(new NumberSearchField(data.getId(),
								NumberSearchField.GREATER));
						Integer nextId = accountService
								.getNextItemKey(criteria);
						if (nextId != null) {
							EventBusFactory.getInstance().post(
									new AccountEvent.GotoRead(this, nextId));
						} else {
							NotificationUtil.showGotoLastRecordNotification();
						}

					}

					@Override
					public void gotoPrevious(SimpleAccount data) {
						AccountService accountService = ApplicationContextUtil
								.getSpringBean(AccountService.class);
						AccountSearchCriteria criteria = new AccountSearchCriteria();
						criteria.setSaccountid(new NumberSearchField(AppContext
								.getAccountId()));
						criteria.setId(new NumberSearchField(data.getId(),
								NumberSearchField.LESSTHAN));
						Integer nextId = accountService
								.getPreviousItemKey(criteria);
						if (nextId != null) {
							EventBusFactory.getInstance().post(
									new AccountEvent.GotoRead(this, nextId));
						} else {
							NotificationUtil.showGotoFirstRecordNotification();
						}
					}
				});

		view.getRelatedCaseHandlers().addRelatedListHandler(
				new RelatedListHandler<SimpleCase>() {

					@Override
					public void selectAssociateItems(Set<SimpleCase> items) {
						// TODO Auto-generated method stub

					}

					@Override
					public void createNewRelatedItem(String itemId) {
						SimpleCase cases = new SimpleCase();
						cases.setAccountid(view.getItem().getId());
						EventBusFactory.getInstance().post(
								new CaseEvent.GotoEdit(this, cases));
					}
				});
		view.getRelatedContactHandlers().addRelatedListHandler(
				new RelatedListHandler<SimpleContact>() {

					@Override
					public void createNewRelatedItem(String itemId) {
						SimpleContact contact = new SimpleContact();
						contact.setAccountid(view.getItem().getId());
						EventBusFactory.getInstance().post(
								new ContactEvent.GotoEdit(this, contact));
					}

					@Override
					public void selectAssociateItems(Set<SimpleContact> items) {
						ContactService contactService = ApplicationContextUtil
								.getSpringBean(ContactService.class);
						SimpleAccount account = view.getItem();
						for (SimpleContact contact : items) {
							contact.setAccountid(account.getId());
							contactService.updateWithSession(contact,
									AppContext.getUsername());
						}

						EventBusFactory.getInstance().post(
								new ShellEvent.NavigateBack(this, null));
					}

				});
		view.getRelatedLeadHandlers().addRelatedListHandler(
				new RelatedListHandler<SimpleLead>() {

					@Override
					public void selectAssociateItems(Set<SimpleLead> items) {
						SimpleAccount account = view.getItem();
						List<AccountLead> associateLeads = new ArrayList<AccountLead>();
						for (SimpleLead contact : items) {
							AccountLead assoLead = new AccountLead();
							assoLead.setAccountid(account.getId());
							assoLead.setLeadid(contact.getId());
							assoLead.setCreatetime(new GregorianCalendar()
									.getTime());
							associateLeads.add(assoLead);
						}

						AccountService accountService = ApplicationContextUtil
								.getSpringBean(AccountService.class);
						accountService.saveAccountLeadRelationship(
								associateLeads, AppContext.getAccountId());

						EventBusFactory.getInstance().post(
								new ShellEvent.NavigateBack(this, null));
					}

					@Override
					public void createNewRelatedItem(String itemId) {
						SimpleLead lead = new SimpleLead();
						lead.setAccountname(view.getItem().getAccountname());
						EventBusFactory.getInstance().post(
								new LeadEvent.GotoEdit(this, lead));
					}
				});
		view.getRelatedOpportunityHandlers().addRelatedListHandler(
				new RelatedListHandler<SimpleOpportunity>() {

					@Override
					public void selectAssociateItems(
							Set<SimpleOpportunity> items) {
						// TODO Auto-generated method stub

					}

					@Override
					public void createNewRelatedItem(String itemId) {
						SimpleOpportunity opportunity = new SimpleOpportunity();
						opportunity.setAccountid(view.getItem().getId());
						EventBusFactory.getInstance()
								.post(new OpportunityEvent.GotoEdit(this,
										opportunity));
					}
				});
		view.getRelatedActivityHandlers().addRelatedListHandler(
				new RelatedListHandler<SimpleActivity>() {

					@Override
					public void selectAssociateItems(Set<SimpleActivity> items) {

					}

					@Override
					public void createNewRelatedItem(String itemId) {
						if (itemId.equals(CrmTypeConstants.TASK)) {
							final SimpleTask task = new SimpleTask();
							task.setType(CrmTypeConstants.ACCOUNT);
							task.setTypeid(view.getItem().getId());
							EventBusFactory.getInstance().post(
									new ActivityEvent.TaskEdit(
											AccountReadPresenter.this, task));
						} else if (itemId.equals(CrmTypeConstants.MEETING)) {
							final SimpleMeeting meeting = new SimpleMeeting();
							meeting.setType(CrmTypeConstants.ACCOUNT);
							meeting.setTypeid(view.getItem().getId());
							EventBusFactory
									.getInstance()
									.post(new ActivityEvent.MeetingEdit(
											AccountReadPresenter.this, meeting));
						} else if (itemId.equals(CrmTypeConstants.CALL)) {
							final SimpleCall call = new SimpleCall();
							call.setType(CrmTypeConstants.ACCOUNT);
							call.setTypeid(view.getItem().getId());
							EventBusFactory.getInstance().post(
									new ActivityEvent.CallEdit(
											AccountReadPresenter.this, call));
						}
					}
				});

	}

	@Override
	protected void onGo(ComponentContainer container, ScreenData<?> data) {

		if (AppContext.canRead(RolePermissionCollections.CRM_ACCOUNT)) {
			if (data.getParams() instanceof Integer) {
				AccountService accountService = ApplicationContextUtil
						.getSpringBean(AccountService.class);
				SimpleAccount account = accountService.findById(
						(Integer) data.getParams(), AppContext.getAccountId());
				if (account != null) {
					super.onGo(container, data);
					view.previewItem(account);
					AppContext.addFragment(CrmLinkGenerator
							.generateAccountPreviewLink(account.getId()),
							AppContext.getMessage(
									GenericI18Enum.BROWSER_PREVIEW_ITEM_TITLE,
									"Account", account.getAccountname()));
				} else {
					NotificationUtil.showRecordNotExistNotification();
					return;
				}
			}
		} else {
			NotificationUtil.showMessagePermissionAlert();
		}
	}
}
