/**
 * This file is part of mycollab-esb.
 *
 * mycollab-esb is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * mycollab-esb is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with mycollab-esb.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.esofthead.mycollab.module.billing.esb.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.esofthead.mycollab.common.domain.CustomerFeedbackWithBLOBs;
import com.esofthead.mycollab.module.billing.esb.AccountDeletedCommand;
import com.esofthead.mycollab.module.ecm.service.ResourceService;
import com.esofthead.mycollab.module.page.service.PageService;

/**
 * 
 * @author MyCollab Ltd.
 * @since 1.0
 * 
 */
@Component
public class AccountDeletedCommandImpl implements AccountDeletedCommand {

	private static Logger log = LoggerFactory
			.getLogger(AccountDeletedCommandImpl.class);

	@Autowired
	private ResourceService resourceService;

	@Autowired
	private PageService wikiService;

	@Override
	public void accountDeleted(int accountid, CustomerFeedbackWithBLOBs feedback) {
		deleteAccountFiles(accountid);
	}

	private void deleteAccountFiles(int accountId) {
		log.debug("Delete account files of account {}", accountId);

		String rootPath = accountId + "";
		resourceService.removeResource(rootPath, "", accountId);

		wikiService.removeResource(rootPath);
	}

}
