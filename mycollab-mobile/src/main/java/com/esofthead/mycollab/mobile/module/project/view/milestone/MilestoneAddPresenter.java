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
package com.esofthead.mycollab.mobile.module.project.view.milestone;

import org.apache.log4j.Logger;

import com.esofthead.mycollab.common.GenericLinkUtils;
import com.esofthead.mycollab.eventmanager.EventBusFactory;
import com.esofthead.mycollab.mobile.module.project.CurrentProjectVariables;
import com.esofthead.mycollab.mobile.module.project.events.MilestoneEvent;
import com.esofthead.mycollab.mobile.ui.AbstractMobilePresenter;
import com.esofthead.mycollab.module.project.ProjectRolePermissionCollections;
import com.esofthead.mycollab.module.project.domain.Milestone;
import com.esofthead.mycollab.module.project.domain.SimpleMilestone;
import com.esofthead.mycollab.module.project.i18n.MilestoneI18nEnum;
import com.esofthead.mycollab.module.project.service.MilestoneService;
import com.esofthead.mycollab.spring.ApplicationContextUtil;
import com.esofthead.mycollab.vaadin.AppContext;
import com.esofthead.mycollab.vaadin.events.EditFormHandler;
import com.esofthead.mycollab.vaadin.mvp.HistoryViewManager;
import com.esofthead.mycollab.vaadin.mvp.NullViewState;
import com.esofthead.mycollab.vaadin.mvp.ScreenData;
import com.esofthead.mycollab.vaadin.mvp.ViewState;
import com.esofthead.mycollab.vaadin.ui.NotificationUtil;
import com.vaadin.ui.ComponentContainer;

/**
 * 
 * @author MyCollab Ltd.
 * @since 4.5.2
 */
public class MilestoneAddPresenter extends
		AbstractMobilePresenter<MilestoneAddView> {

	private static final long serialVersionUID = 1L;

	Logger log = Logger.getLogger(MilestoneAddPresenter.class);

	public MilestoneAddPresenter() {
		super(MilestoneAddView.class);
	}

	@Override
	protected void postInitView() {
		view.getEditFormHandlers().addFormHandler(
				new EditFormHandler<SimpleMilestone>() {
					private static final long serialVersionUID = 1L;

					@Override
					public void onSave(final SimpleMilestone milestone) {
						saveMilestone(milestone);
						ViewState viewState = HistoryViewManager.back();
						if (viewState instanceof NullViewState) {
							EventBusFactory.getInstance().post(
									new MilestoneEvent.GotoList(this, null));
						}
					}

					@Override
					public void onCancel() {
						ViewState viewState = HistoryViewManager.back();
						if (viewState instanceof NullViewState) {
							EventBusFactory.getInstance().post(
									new MilestoneEvent.GotoList(this, null));
						}
					}

					@Override
					public void onSaveAndNew(final SimpleMilestone milestone) {
						saveMilestone(milestone);
						EventBusFactory.getInstance().post(
								new MilestoneEvent.GotoAdd(this, null));
					}
				});
	}

	@Override
	protected void onGo(ComponentContainer container, ScreenData<?> data) {
		if (CurrentProjectVariables
				.canWrite(ProjectRolePermissionCollections.MILESTONES)) {

			SimpleMilestone milestone = (SimpleMilestone) data.getParams();
			view.editItem(milestone);
			super.onGo(container, data);

			if (milestone.getId() == null) {
				log.info("URLFragment: "
						+ GenericLinkUtils
								.encodeParam(new Object[] { CurrentProjectVariables
										.getProjectId() }));
				AppContext
						.addFragment(
								"project/milestone/add/"
										+ GenericLinkUtils
												.encodeParam(new Object[] { CurrentProjectVariables
														.getProjectId() }),
								AppContext
										.getMessage(MilestoneI18nEnum.FORM_NEW_TITLE));
			} else {
				AppContext.addFragment(
						"project/milestone/edit/"
								+ GenericLinkUtils.encodeParam(new Object[] {
										CurrentProjectVariables.getProjectId(),
										milestone.getId() }),
						milestone.getName());
			}
		} else {
			NotificationUtil.showMessagePermissionAlert();
		}
	}

	private void saveMilestone(Milestone milestone) {
		MilestoneService milestoneService = ApplicationContextUtil
				.getSpringBean(MilestoneService.class);
		milestone.setProjectid(CurrentProjectVariables.getProjectId());
		milestone.setSaccountid(AppContext.getAccountId());

		if (milestone.getId() == null) {
			milestone.setCreateduser(AppContext.getUsername());
			milestoneService.saveWithSession(milestone,
					AppContext.getUsername());
		} else {
			milestoneService.updateWithSession(milestone,
					AppContext.getUsername());
		}

	}

}
