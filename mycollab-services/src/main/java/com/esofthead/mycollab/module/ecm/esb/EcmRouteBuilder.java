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
package com.esofthead.mycollab.module.ecm.esb;

import org.apache.camel.ExchangePattern;
import org.apache.camel.spring.SpringRouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.esofthead.mycollab.spring.ApplicationContextUtil;

/**
 * 
 * @author MyCollab Ltd.
 * @since 1.0
 * 
 */
@Component
public class EcmRouteBuilder extends SpringRouteBuilder {
	private static Logger log = LoggerFactory.getLogger(EcmRouteBuilder.class);

	@Override
	public void configure() throws Exception {
		log.debug("Configure content save route");
		from(EcmEndPoints.SAVE_CONTENT_ENDPOINT).setExchangePattern(
				ExchangePattern.InOnly).to("seda:saveContent.queue");
		from("seda:saveContent.queue")
				.threads()
				.bean(ApplicationContextUtil
						.getSpringBean(SaveContentCommand.class),
						"saveContent(com.esofthead.mycollab.module.ecm.domain.Content, String, int)");

		log.debug("Configure contents deleted route");
		from(EcmEndPoints.DELETE_RESOURCES_ENDPOINT).setExchangePattern(
				ExchangePattern.InOnly).to("seda:deleteResources.queue");
		from("seda:deleteResources.queue")
				.threads()
				.bean(ApplicationContextUtil
						.getSpringBean(DeleteResourcesCommand.class),
						"removeResource(String, String, int)");

	}

}
