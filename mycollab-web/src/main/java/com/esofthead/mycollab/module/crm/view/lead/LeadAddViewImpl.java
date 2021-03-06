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
package com.esofthead.mycollab.module.crm.view.lead;

import com.esofthead.mycollab.form.view.DynaFormLayout;
import com.esofthead.mycollab.module.crm.CrmTypeConstants;
import com.esofthead.mycollab.module.crm.domain.SimpleLead;
import com.esofthead.mycollab.module.crm.ui.components.AbstractEditItemComp;
import com.esofthead.mycollab.vaadin.mvp.ViewComponent;
import com.esofthead.mycollab.vaadin.ui.AbstractBeanFieldGroupEditFieldFactory;
import com.esofthead.mycollab.vaadin.ui.AdvancedEditBeanForm;
import com.esofthead.mycollab.vaadin.ui.EditFormControlsGenerator;
import com.esofthead.mycollab.vaadin.ui.IFormLayoutFactory;
import com.esofthead.mycollab.vaadin.ui.MyCollabResource;
import com.vaadin.server.Resource;
import com.vaadin.ui.ComponentContainer;

/**
 * 
 * @author MyCollab Ltd.
 * @since 2.0
 * 
 */
@ViewComponent
public class LeadAddViewImpl extends AbstractEditItemComp<SimpleLead> implements
		LeadAddView {
	private static final long serialVersionUID = 1L;

	@Override
	protected String initFormTitle() {
		return (beanItem.getId() == null) ? "New Lead" : beanItem
				.getLeadName();
	}

	@Override
	protected Resource initFormIconResource() {
		return MyCollabResource.newResource("icons/22/crm/lead.png");
	}

	@Override
	protected ComponentContainer createButtonControls() {
		return new EditFormControlsGenerator<SimpleLead>(editForm)
				.createButtonControls();
	}

	@Override
	protected AdvancedEditBeanForm<SimpleLead> initPreviewForm() {
		return new AdvancedEditBeanForm<SimpleLead>();
	}

	@Override
	protected IFormLayoutFactory initFormLayoutFactory() {
		return new DynaFormLayout(CrmTypeConstants.LEAD,
				LeadDefaultDynaFormLayoutFactory.getForm());
	}

	@Override
	protected AbstractBeanFieldGroupEditFieldFactory<SimpleLead> initBeanFormFieldFactory() {
		return new LeadEditFormFieldFactory<SimpleLead>(editForm);
	}
}
