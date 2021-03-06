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

package com.esofthead.mycollab.module.project.view.user;

import static com.esofthead.mycollab.html.DivLessFormatter.EMPTY_SPACE;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.time.DateUtils;

import com.esofthead.mycollab.common.ActivityStreamConstants;
import com.esofthead.mycollab.common.ModuleNameConstants;
import com.esofthead.mycollab.common.domain.criteria.ActivityStreamSearchCriteria;
import com.esofthead.mycollab.configuration.StorageManager;
import com.esofthead.mycollab.core.MyCollabException;
import com.esofthead.mycollab.core.arguments.NumberSearchField;
import com.esofthead.mycollab.core.arguments.SearchField;
import com.esofthead.mycollab.core.arguments.SetSearchField;
import com.esofthead.mycollab.html.DivLessFormatter;
import com.esofthead.mycollab.module.project.ProjectLinkBuilder;
import com.esofthead.mycollab.module.project.ProjectResources;
import com.esofthead.mycollab.module.project.ProjectTypeConstants;
import com.esofthead.mycollab.module.project.domain.ProjectActivityStream;
import com.esofthead.mycollab.module.project.i18n.ProjectCommonI18nEnum;
import com.esofthead.mycollab.module.project.ui.components.ProjectAuditLogStreamGenerator;
import com.esofthead.mycollab.module.project.view.ProjectLocalizationTypeMap;
import com.esofthead.mycollab.vaadin.AppContext;
import com.esofthead.mycollab.vaadin.ui.MyCollabResource;
import com.hp.gagawa.java.elements.A;
import com.hp.gagawa.java.elements.Div;
import com.hp.gagawa.java.elements.Img;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;

/**
 * 
 * @author MyCollab Ltd.
 * @since 1.0
 */
public class ActivityStreamComponent extends CssLayout {

	private static final long serialVersionUID = 1L;

	private final ProjectActivityStreamPagedList2 activityStreamList;

	public ActivityStreamComponent() {
		this.setStyleName("project-activity-list");
		this.activityStreamList = new ProjectActivityStreamPagedList2();
	}

	public void showFeeds(final List<Integer> prjKeys) {
		this.removeAllComponents();
		this.addComponent(this.activityStreamList);

		final ActivityStreamSearchCriteria searchCriteria = new ActivityStreamSearchCriteria();
		searchCriteria.setModuleSet(new SetSearchField<String>(SearchField.AND,
				new String[] { ModuleNameConstants.PRJ }));
		searchCriteria.setExtraTypeIds(new SetSearchField<Integer>(prjKeys
				.toArray(new Integer[0])));
		searchCriteria.setSaccountid(new NumberSearchField(AppContext
				.getAccountId()));
		this.activityStreamList.setSearchCriteria(searchCriteria);

	}

	static class ProjectActivityStreamPagedList2 extends
			ProjectActivityStreamPagedList {
		private static final long serialVersionUID = 1L;

		@Override
		public void doSearch() {
			this.totalCount = this.projectActivityStreamService
					.getTotalActivityStream(this.searchRequest
							.getSearchCriteria());
			this.totalPage = (this.totalCount - 1)
					/ this.searchRequest.getNumberOfItems() + 1;
			if (this.searchRequest.getCurrentPage() > this.totalPage) {
				this.searchRequest.setCurrentPage(this.totalPage);
			}

			if (totalPage > 1) {
				if (this.controlBarWrapper != null) {
					this.removeComponent(this.controlBarWrapper);
				}
				this.addComponent(this.createPageControls());
			} else {
				if (getComponentCount() == 2) {
					removeComponent(getComponent(1));
				}
			}

			final List<ProjectActivityStream> currentListData = this.projectActivityStreamService
					.getProjectActivityStreams(this.searchRequest);
			this.listContainer.removeAllComponents();

			Date currentDate = new GregorianCalendar(2100, 1, 1).getTime();

			CssLayout currentFeedBlock = new CssLayout();

			try {
				for (final ProjectActivityStream activityStream : currentListData) {
					final Date itemCreatedDate = activityStream
							.getCreatedtime();
					if (!DateUtils.isSameDay(currentDate, itemCreatedDate)) {
						currentFeedBlock = new CssLayout();
						currentFeedBlock.setStyleName("feed-block");
						feedBlocksPut(currentDate, itemCreatedDate,
								currentFeedBlock);
						currentDate = itemCreatedDate;
					}

					String content = "";

					// --------------Item hidden div tooltip----------------
					String uid = UUID.randomUUID().toString();
					String type = AppContext
							.getMessage(ProjectLocalizationTypeMap
									.getType(activityStream.getType()));
					String assigneeValue = buildAssigneeValue(activityStream,
							uid);
					String itemLink = buildItemValue(activityStream, uid);
					String projectLink = buildProjectValue(activityStream, uid);

					if (ActivityStreamConstants.ACTION_CREATE
							.equals(activityStream.getAction())) {
						if (ProjectTypeConstants.PROJECT.equals(activityStream
								.getType())) {
							content = AppContext
									.getMessage(
											ProjectCommonI18nEnum.FEED_USER_ACTIVITY_CREATE_ACTION_TITLE,
											assigneeValue, type, projectLink);
						} else {
							content = AppContext
									.getMessage(
											ProjectCommonI18nEnum.FEED_PROJECT_USER_ACTIVITY_CREATE_ACTION_TITLE,
											assigneeValue, type, itemLink,
											projectLink);
						}

					} else if (ActivityStreamConstants.ACTION_UPDATE
							.equals(activityStream.getAction())) {
						if (ProjectTypeConstants.PROJECT.equals(activityStream
								.getType())) {
							content = AppContext
									.getMessage(
											ProjectCommonI18nEnum.FEED_USER_ACTIVITY_UPDATE_ACTION_TITLE,
											assigneeValue, type, projectLink);
						} else {
							content = AppContext
									.getMessage(
											ProjectCommonI18nEnum.FEED_PROJECT_USER_ACTIVITY_UPDATE_ACTION_TITLE,
											assigneeValue, type, itemLink,
											projectLink);
						}
						if (activityStream.getAssoAuditLog() != null) {
							content += ProjectAuditLogStreamGenerator
									.generatorDetailChangeOfActivity(activityStream);
						}
					}

					final Label actionLbl = new Label(content, ContentMode.HTML);
					final CssLayout streamWrapper = new CssLayout();
					streamWrapper.setWidth("100%");
					streamWrapper.addStyleName("stream-wrapper");
					streamWrapper.addComponent(actionLbl);
					currentFeedBlock.addComponent(streamWrapper);
				}
			} catch (final Exception e) {
				throw new MyCollabException(e);
			}
		}

		private String buildAssigneeValue(ProjectActivityStream activityStream,
				String uid) {
			DivLessFormatter div = new DivLessFormatter();
			Img userAvatar = new Img("", StorageManager.getAvatarLink(
					activityStream.getCreatedUserAvatarId(), 16));
			A userLink = new A();
			userLink.setId("projectusertagA" + uid);
			userLink.setHref(ProjectLinkBuilder.generateProjectMemberFullLink(
					activityStream.getExtratypeid(),
					activityStream.getCreateduser()));

			String arg3 = "'" + uid + "'";
			String arg4 = "'" + activityStream.getCreateduser() + "'";
			String arg5 = "'" + AppContext.getSiteUrl() + "tooltip/'";
			String arg6 = "'" + AppContext.getSiteUrl() + "'";
			String arg7 = AppContext.getSession().getTimezone();
			String arg8 = "'" + activityStream.getSaccountid() + "'";
			String arg9 = "'" + AppContext.getUserLocale().toString() + "'";

			String mouseOverFunc = String.format(
					"return projectuseroverIt(%s,%s,%s,%s,%s,%s,%s);", arg3,
					arg4, arg5, arg6, arg7, arg8, arg9);
			userLink.setAttribute("onmouseover", mouseOverFunc);

			String arg10 = activityStream.getCreatedUserFullName();
			userLink.appendText(arg10);

			Div div1 = new Div();
			div1.setId("projectusermystickyTooltip" + uid);
			div1.setAttribute("class", "stickytooltip");

			Div div11 = new Div();
			div11.setAttribute("style", "padding:5px");
			div1.appendChild(div11);

			Div div12 = new Div();
			div12.setId("projectusertooltip" + uid);
			div12.setAttribute("class", "atip");
			div12.setAttribute("style", "width:400px");
			div11.appendChild(div12);

			Div div13 = new Div();
			div13.setId("projectuserserverdata" + uid);
			div12.appendChild(div13);

			div.appendChild(userAvatar, EMPTY_SPACE, userLink, EMPTY_SPACE,
					div1);
			return div.write();
		}

		private String buildItemValue(ProjectActivityStream activityStream,
				String uid) {
			DivLessFormatter div = new DivLessFormatter();
			Img itemImg = new Img("",
					ProjectResources.getResourceLink(activityStream.getType()));
			A itemLink = new A();
			itemLink.setId("projectOverViewtagA" + uid);

			if (ProjectTypeConstants.TASK.equals(activityStream.getType())
					|| ProjectTypeConstants.BUG
							.equals(activityStream.getType())) {
				itemLink.setHref(ProjectLinkBuilder.generateProjectItemLink(
						activityStream.getProjectShortName(),
						activityStream.getExtratypeid(),
						activityStream.getType(), activityStream.getItemKey()));
			} else {
				itemLink.setHref(ProjectLinkBuilder.generateProjectItemLink(
						activityStream.getProjectShortName(),
						activityStream.getExtratypeid(),
						activityStream.getType(), activityStream.getTypeid()));
			}

			String arg17 = "'" + uid + "'";
			String arg18 = "'" + activityStream.getType() + "'";
			String arg19 = "'" + activityStream.getTypeid() + "'";
			String arg20 = "'" + AppContext.getSiteUrl() + "tooltip/'";
			String arg21 = "'" + activityStream.getSaccountid() + "'";
			String arg22 = "'" + AppContext.getSiteUrl() + "'";
			String arg23 = AppContext.getSession().getTimezone();
			String arg24 = "'" + AppContext.getUserLocale().toString() + "'";

			String mouseOverFunc = String.format(
					"return projectOverViewOverIt(%s,%s,%s,%s,%s,%s,%s,%s);",
					arg17, arg18, arg19, arg20, arg21, arg22, arg23, arg24);
			itemLink.setAttribute("onmouseover", mouseOverFunc);
			itemLink.appendText(activityStream.getNamefield());

			div.appendChild(itemImg, EMPTY_SPACE, itemLink);
			return div.write();
		}

		private String buildProjectValue(ProjectActivityStream activityStream,
				String uid) {
			DivLessFormatter div = new DivLessFormatter();
			Img prjImg = new Img("",
					MyCollabResource
							.newResourceLink("icons/16/project/project.png"));
			A prjLink = new A(
					ProjectLinkBuilder.generateProjectFullLink(activityStream
							.getProjectId()));
			prjLink.appendText(activityStream.getProjectName());

			Div div1 = new Div();
			div1.setId("projectOverViewmystickyTooltip" + uid);
			div1.setAttribute("class", "stickytooltip");

			Div div12 = new Div();
			div12.setAttribute("style", "padding:5px");
			div1.appendChild(div12);

			Div div13 = new Div();
			div13.setId("projectOverViewtooltip" + uid);
			div13.setAttribute("class", "atip");
			div13.setAttribute("style", "width:500px");
			div12.appendChild(div13);

			Div div14 = new Div();
			div14.setId("projectOverViewserverdata" + uid);
			div13.appendChild(div14);

			div.appendChild(prjImg, EMPTY_SPACE, prjLink, EMPTY_SPACE, div1);

			return div.write();
		}

		@Override
		protected int queryTotalCount() {
			return 0;
		}

		@Override
		protected List<ProjectActivityStream> queryCurrentData() {
			return null;
		}
	}
}
