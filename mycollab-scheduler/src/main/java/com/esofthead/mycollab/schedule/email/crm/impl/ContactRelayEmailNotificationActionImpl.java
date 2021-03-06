/**
 * This file is part of mycollab-scheduler.
 *
 * mycollab-scheduler is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * mycollab-scheduler is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with mycollab-scheduler.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.esofthead.mycollab.schedule.email.crm.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.esofthead.mycollab.common.MonitorTypeConstants;
import com.esofthead.mycollab.common.domain.SimpleRelayEmailNotification;
import com.esofthead.mycollab.common.i18n.GenericI18Enum;
import com.esofthead.mycollab.configuration.StorageManager;
import com.esofthead.mycollab.core.utils.StringUtils;
import com.esofthead.mycollab.module.crm.CrmLinkGenerator;
import com.esofthead.mycollab.module.crm.CrmResources;
import com.esofthead.mycollab.module.crm.CrmTypeConstants;
import com.esofthead.mycollab.module.crm.domain.SimpleAccount;
import com.esofthead.mycollab.module.crm.domain.SimpleContact;
import com.esofthead.mycollab.module.crm.i18n.ContactI18nEnum;
import com.esofthead.mycollab.module.crm.service.AccountService;
import com.esofthead.mycollab.module.crm.service.ContactService;
import com.esofthead.mycollab.module.mail.MailUtils;
import com.esofthead.mycollab.module.user.AccountLinkGenerator;
import com.esofthead.mycollab.module.user.domain.SimpleUser;
import com.esofthead.mycollab.module.user.service.UserService;
import com.esofthead.mycollab.schedule.email.ItemFieldMapper;
import com.esofthead.mycollab.schedule.email.MailContext;
import com.esofthead.mycollab.schedule.email.crm.ContactRelayEmailNotificationAction;
import com.esofthead.mycollab.schedule.email.format.DateFieldFormat;
import com.esofthead.mycollab.schedule.email.format.EmailLinkFieldFormat;
import com.esofthead.mycollab.schedule.email.format.FieldFormat;
import com.esofthead.mycollab.schedule.email.format.html.TagBuilder;
import com.esofthead.mycollab.spring.ApplicationContextUtil;
import com.hp.gagawa.java.elements.A;
import com.hp.gagawa.java.elements.Img;
import com.hp.gagawa.java.elements.Span;

/**
 * 
 * @author MyCollab Ltd.
 * @since 1.0
 * 
 */
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ContactRelayEmailNotificationActionImpl extends
		CrmDefaultSendingRelayEmailAction<SimpleContact> implements
		ContactRelayEmailNotificationAction {

	private static Logger log = LoggerFactory
			.getLogger(ContactRelayEmailNotificationActionImpl.class);

	@Autowired
	private ContactService contactService;

	private static final ContactFieldNameMapper mapper = new ContactFieldNameMapper();

	@Override
	protected void buildExtraTemplateVariables(
			MailContext<SimpleContact> context) {
		String summary = bean.getContactName();
		String summaryLink = CrmLinkGenerator.generateContactPreviewFullLink(
				siteUrl, bean.getId());

		SimpleRelayEmailNotification emailNotification = context
				.getEmailNotification();

		String avatarId = "";

		SimpleUser user = userService.findUserByUserNameInAccount(
				emailNotification.getChangeby(), context.getSaccountid());

		if (user != null) {
			avatarId = user.getAvatarid();
		}
		Img userAvatar = new Img("", StorageManager.getAvatarLink(avatarId, 16));
		userAvatar.setWidth("16");
		userAvatar.setHeight("16");
		userAvatar.setStyle("display: inline-block; vertical-align: top;");

		String makeChangeUser = userAvatar.toString()
				+ emailNotification.getChangeByUserFullName();

		if (MonitorTypeConstants.CREATE_ACTION.equals(emailNotification
				.getAction())) {
			contentGenerator.putVariable("actionHeading", context.getMessage(
					ContactI18nEnum.MAIL_CREATE_ITEM_HEADING, makeChangeUser));
		} else if (MonitorTypeConstants.UPDATE_ACTION.equals(emailNotification
				.getAction())) {
			contentGenerator.putVariable("actionHeading", context.getMessage(
					ContactI18nEnum.MAIL_UPDATE_ITEM_HEADING, makeChangeUser));
		} else if (MonitorTypeConstants.ADD_COMMENT_ACTION
				.equals(emailNotification.getAction())) {
			contentGenerator.putVariable("actionHeading", context.getMessage(
					ContactI18nEnum.MAIL_COMMENT_ITEM_HEADING, makeChangeUser));
		}

		contentGenerator.putVariable("summary", summary);
		contentGenerator.putVariable("summaryLink", summaryLink);
	}

	@Override
	protected Enum<?> getCreateSubjectKey() {
		return ContactI18nEnum.MAIL_CREATE_ITEM_SUBJECT;
	}

	@Override
	protected Enum<?> getUpdateSubjectKey() {
		return ContactI18nEnum.MAIL_UPDATE_ITEM_SUBJECT;
	}

	@Override
	protected Enum<?> getCommentSubjectKey() {
		return ContactI18nEnum.MAIL_COMMENT_ITEM_SUBJECT;
	}

	@Override
	protected String getItemName() {
		return StringUtils.trim(bean.getContactName(), 100);
	}

	@Override
	protected ItemFieldMapper getItemFieldMapper() {
		return mapper;
	}

	@Override
	protected SimpleContact getBeanInContext(MailContext<SimpleContact> context) {
		return contactService.findById(Integer.parseInt(context.getTypeid()),
				context.getSaccountid());
	}

	public static class ContactFieldNameMapper extends ItemFieldMapper {

		ContactFieldNameMapper() {
			put("firstname", ContactI18nEnum.FORM_FIRSTNAME);
			put("officephone", ContactI18nEnum.FORM_OFFICE_PHONE);

			put("lastname", ContactI18nEnum.FORM_LASTNAME);
			put("mobile", ContactI18nEnum.FORM_MOBILE);

			put("accountid", new AccountFieldFormat("accountid",
					ContactI18nEnum.FORM_ACCOUNTS));
			put("homephone", ContactI18nEnum.FORM_HOME_PHONE);

			put("title", ContactI18nEnum.FORM_TITLE);
			put("otherphone", ContactI18nEnum.FORM_OTHER_PHONE);

			put("department", ContactI18nEnum.FORM_DEPARTMENT);
			put("fax", ContactI18nEnum.FORM_FAX);

			put("email", new EmailLinkFieldFormat("email",
					ContactI18nEnum.FORM_EMAIL));
			put("birthday", new DateFieldFormat("birthday",
					ContactI18nEnum.FORM_BIRTHDAY));

			put("assistant", ContactI18nEnum.FORM_ASSISTANT);
			put("iscallable", ContactI18nEnum.FORM_IS_CALLABLE);

			put("assistantphone", ContactI18nEnum.FORM_ASSISTANT_PHONE);
			put("assignuser", new AssigneeFieldFormat("assignuser",
					GenericI18Enum.FORM_ASSIGNEE));

			put("leadsource", ContactI18nEnum.FORM_LEAD_SOURCE, true);

			put("primaddress", ContactI18nEnum.FORM_PRIMARY_ADDRESS);
			put("otheraddress", ContactI18nEnum.FORM_OTHER_ADDRESS);

			put("primcity", ContactI18nEnum.FORM_PRIMARY_CITY);
			put("othercity", ContactI18nEnum.FORM_OTHER_CITY);

			put("primstate", ContactI18nEnum.FORM_PRIMARY_STATE);
			put("otherstate", ContactI18nEnum.FORM_OTHER_STATE);

			put("primpostalcode", ContactI18nEnum.FORM_PRIMARY_POSTAL_CODE);
			put("otherpostalcode", ContactI18nEnum.FORM_OTHER_POSTAL_CODE);

			put("primcountry", ContactI18nEnum.FORM_PRIMARY_COUNTRY);
			put("othercountry", ContactI18nEnum.FORM_OTHER_COUNTRY);

			put("description", GenericI18Enum.FORM_DESCRIPTION, true);

		}
	}

	public static class AssigneeFieldFormat extends FieldFormat {

		public AssigneeFieldFormat(String fieldName, Enum<?> displayName) {
			super(fieldName, displayName);
		}

		@Override
		public String formatField(MailContext<?> context) {
			SimpleContact contact = (SimpleContact) context.getWrappedBean();
			if (contact.getAssignuser() != null) {
				String userAvatarLink = MailUtils.getAvatarLink(
						contact.getAssignUserAvatarId(), 16);

				Img img = TagBuilder.newImg("avatar", userAvatarLink);

				String userLink = AccountLinkGenerator
						.generatePreviewFullUserLink(
								MailUtils.getSiteUrl(contact.getSaccountid()),
								contact.getAssignuser());
				A link = TagBuilder.newA(userLink,
						contact.getAssignUserFullName());
				return TagBuilder.newLink(img, link).write();
			} else {
				return new Span().write();
			}
		}

		@Override
		public String formatField(MailContext<?> context, String value) {
			if (value == null || "".equals(value)) {
				return new Span().write();
			}

			UserService userService = ApplicationContextUtil
					.getSpringBean(UserService.class);
			SimpleUser user = userService.findUserByUserNameInAccount(value,
					context.getUser().getAccountId());
			if (user != null) {
				String userAvatarLink = MailUtils.getAvatarLink(
						user.getAvatarid(), 16);
				String userLink = AccountLinkGenerator
						.generatePreviewFullUserLink(
								MailUtils.getSiteUrl(user.getAccountId()),
								user.getUsername());
				Img img = TagBuilder.newImg("avatar", userAvatarLink);
				A link = TagBuilder.newA(userLink, user.getDisplayName());
				return TagBuilder.newLink(img, link).write();
			}
			return value;
		}
	}

	public static class AccountFieldFormat extends FieldFormat {

		public AccountFieldFormat(String fieldName, Enum<?> displayName) {
			super(fieldName, displayName);
		}

		@Override
		public String formatField(MailContext<?> context) {
			SimpleContact contact = (SimpleContact) context.getWrappedBean();
			if (contact.getAccountid() != null) {
				String accountIconLink = CrmResources
						.getResourceLink(CrmTypeConstants.ACCOUNT);
				Img img = TagBuilder.newImg("icon", accountIconLink);

				String accountLink = CrmLinkGenerator
						.generateAccountPreviewFullLink(context.getSiteUrl(),
								contact.getAccountid());
				A link = TagBuilder.newA(accountLink, contact.getAccountName());
				return TagBuilder.newLink(img, link).write();
			} else {
				return new Span().write();
			}
		}

		@Override
		public String formatField(MailContext<?> context, String value) {
			if (value == null || "".equals(value)) {
				return new Span().write();
			}

			try {
				int accountId = Integer.parseInt(value);
				AccountService accountService = ApplicationContextUtil
						.getSpringBean(AccountService.class);
				SimpleAccount account = accountService.findById(accountId,
						context.getUser().getAccountId());
				if (account != null) {
					String accountIconLink = CrmResources
							.getResourceLink(CrmTypeConstants.ACCOUNT);
					Img img = TagBuilder.newImg("icon", accountIconLink);
					String accountLink = CrmLinkGenerator
							.generateAccountPreviewFullLink(
									context.getSiteUrl(), account.getId());
					A link = TagBuilder.newA(accountLink,
							account.getAccountname());
					return TagBuilder.newLink(img, link).write();
				}
			} catch (Exception e) {
				log.error("Error", e);
			}

			return value;
		}
	}
}
