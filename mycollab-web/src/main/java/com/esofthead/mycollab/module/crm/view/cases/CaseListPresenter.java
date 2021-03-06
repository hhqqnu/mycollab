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
package com.esofthead.mycollab.module.crm.view.cases;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.esofthead.mycollab.common.i18n.GenericI18Enum;
import com.esofthead.mycollab.common.i18n.WebExceptionI18nEnum;
import com.esofthead.mycollab.core.persistence.service.ISearchableService;
import com.esofthead.mycollab.module.crm.domain.CaseWithBLOBs;
import com.esofthead.mycollab.module.crm.domain.SimpleCase;
import com.esofthead.mycollab.module.crm.domain.criteria.CaseSearchCriteria;
import com.esofthead.mycollab.module.crm.i18n.CaseI18nEnum;
import com.esofthead.mycollab.module.crm.i18n.CrmCommonI18nEnum;
import com.esofthead.mycollab.module.crm.service.CaseService;
import com.esofthead.mycollab.module.crm.view.CrmGenericListPresenter;
import com.esofthead.mycollab.module.crm.view.CrmToolbar;
import com.esofthead.mycollab.security.RolePermissionCollections;
import com.esofthead.mycollab.spring.ApplicationContextUtil;
import com.esofthead.mycollab.vaadin.AppContext;
import com.esofthead.mycollab.vaadin.desktop.ui.DefaultMassEditActionHandler;
import com.esofthead.mycollab.vaadin.events.MassItemActionHandler;
import com.esofthead.mycollab.vaadin.mvp.MassUpdateCommand;
import com.esofthead.mycollab.vaadin.mvp.ScreenData;
import com.esofthead.mycollab.vaadin.mvp.ViewManager;
import com.esofthead.mycollab.vaadin.ui.MailFormWindow;
import com.esofthead.mycollab.vaadin.ui.NotificationUtil;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.UI;

/**
 * 
 * @author MyCollab Ltd.
 * @since 1.0
 * 
 */
public class CaseListPresenter extends
		CrmGenericListPresenter<CaseListView, CaseSearchCriteria, SimpleCase>
		implements MassUpdateCommand<CaseWithBLOBs> {

	private static final long serialVersionUID = 1L;
	private CaseService caseService;

	public CaseListPresenter() {
		super(CaseListView.class, CaseListNoItemView.class);
	}

	@Override
	protected void postInitView() {
		super.postInitView();

		caseService = ApplicationContextUtil.getSpringBean(CaseService.class);

		view.getPopupActionHandlers().addMassItemActionHandler(
				new DefaultMassEditActionHandler(this) {

					@Override
					protected void onSelectExtra(String id) {
						if (MassItemActionHandler.MAIL_ACTION.equals(id)) {
							if (isSelectAll) {
								NotificationUtil.showWarningNotification(AppContext
										.getMessage(WebExceptionI18nEnum.NOT_SUPPORT_SENDING_EMAIL_TO_ALL_USERS));
							} else {
								List<String> lstMail = new ArrayList<String>();
								List<SimpleCase> tableData = view
										.getPagedBeanTable()
										.getCurrentDataList();
								for (SimpleCase item : tableData) {
									if (item.isSelected()) {
										lstMail.add(item.getEmail());
									}
								}
								UI.getCurrent().addWindow(
										new MailFormWindow(lstMail));
							}

						} else if (MassItemActionHandler.MASS_UPDATE_ACTION
								.equals(id)) {
							MassUpdateCaseWindow massUpdateWindow = new MassUpdateCaseWindow(
									AppContext
											.getMessage(
													GenericI18Enum.WINDOW_MASS_UPDATE_TITLE,
													"Case"),
									CaseListPresenter.this);
							UI.getCurrent().addWindow(massUpdateWindow);
						}

					}

					@Override
					protected String getReportTitle() {
						return AppContext
								.getMessage(CaseI18nEnum.VIEW_LIST_TITLE);
					}

					@Override
					protected Class<SimpleCase> getReportModelClassType() {
						return SimpleCase.class;
					}
				});
	}

	@Override
	protected void onGo(ComponentContainer container, ScreenData<?> data) {
		if (AppContext.canRead(RolePermissionCollections.CRM_CASE)) {
			CrmToolbar crmToolbar = ViewManager
					.getCacheComponent(CrmToolbar.class);
			crmToolbar.gotoItem(AppContext
					.getMessage(CrmCommonI18nEnum.TOOLBAR_CASES_HEADER));

			searchCriteria = (CaseSearchCriteria) data.getParams();
			int totalCount = caseService.getTotalCount(searchCriteria);
			if (totalCount > 0) {
				this.displayListView(container, data);
				doSearch(searchCriteria);
			} else {
				this.displayNoExistItems(container, data);
			}

			AppContext.addFragment("crm/cases/list",
					AppContext.getMessage(CaseI18nEnum.VIEW_LIST_TITLE));
		} else {
			NotificationUtil.showMessagePermissionAlert();
		}
	}

	@Override
	public void doSearch(CaseSearchCriteria searchCriteria) {
		this.searchCriteria = searchCriteria;
		view.getPagedBeanTable().setSearchCriteria(searchCriteria);
		checkWhetherEnableTableActionControl();
	}

	@Override
	protected void deleteSelectedItems() {
		if (!isSelectAll) {
			Collection<SimpleCase> currentDataList = view.getPagedBeanTable()
					.getCurrentDataList();
			List<Integer> keyList = new ArrayList<Integer>();
			for (SimpleCase item : currentDataList) {
				if (item.isSelected()) {
					keyList.add(item.getId());
				}
			}

			if (keyList.size() > 0) {
				caseService.massRemoveWithSession(keyList,
						AppContext.getUsername(), AppContext.getAccountId());
				doSearch(searchCriteria);
				checkWhetherEnableTableActionControl();
			}
		} else {
			caseService.removeByCriteria(searchCriteria,
					AppContext.getAccountId());
			doSearch(searchCriteria);
		}

	}

	@Override
	public void massUpdate(CaseWithBLOBs value) {
		if (!isSelectAll) {
			Collection<SimpleCase> currentDataList = view.getPagedBeanTable()
					.getCurrentDataList();
			List<Integer> keyList = new ArrayList<Integer>();
			for (SimpleCase item : currentDataList) {
				if (item.isSelected()) {
					keyList.add(item.getId());
				}
			}
			if (keyList.size() > 0) {
				caseService.massUpdateWithSession(value, keyList,
						AppContext.getAccountId());
				doSearch(searchCriteria);
			}
		} else {
			caseService.updateBySearchCriteria(value, searchCriteria);
			doSearch(searchCriteria);
		}
	}

	@Override
	public ISearchableService<CaseSearchCriteria> getSearchService() {
		return ApplicationContextUtil.getSpringBean(CaseService.class);
	}
}
