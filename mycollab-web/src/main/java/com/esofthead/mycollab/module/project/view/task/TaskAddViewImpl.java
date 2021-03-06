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

package com.esofthead.mycollab.module.project.view.task;

import com.esofthead.mycollab.module.file.AttachmentType;
import com.esofthead.mycollab.module.file.AttachmentUtils;
import com.esofthead.mycollab.module.project.CurrentProjectVariables;
import com.esofthead.mycollab.module.project.domain.Task;
import com.esofthead.mycollab.module.project.i18n.TaskI18nEnum;
import com.esofthead.mycollab.module.project.ui.components.AbstractEditItemComp;
import com.esofthead.mycollab.module.project.ui.components.ProjectTaskListComboBox;
import com.esofthead.mycollab.module.project.ui.components.TaskPercentageCompleteComboBox;
import com.esofthead.mycollab.module.project.view.settings.component.ProjectMemberSelectionField;
import com.esofthead.mycollab.vaadin.AppContext;
import com.esofthead.mycollab.vaadin.events.HasEditFormHandlers;
import com.esofthead.mycollab.vaadin.mvp.ViewComponent;
import com.esofthead.mycollab.vaadin.mvp.ViewScope;
import com.esofthead.mycollab.vaadin.ui.AbstractBeanFieldGroupEditFieldFactory;
import com.esofthead.mycollab.vaadin.ui.AdvancedEditBeanForm;
import com.esofthead.mycollab.vaadin.ui.DefaultFormViewFieldFactory.AttachmentUploadField;
import com.esofthead.mycollab.vaadin.ui.DefaultFormViewFieldFactory.FormAttachmentUploadField;
import com.esofthead.mycollab.vaadin.ui.EditFormControlsGenerator;
import com.esofthead.mycollab.vaadin.ui.GenericBeanForm;
import com.esofthead.mycollab.vaadin.ui.IFormLayoutFactory;
import com.esofthead.mycollab.vaadin.ui.MyCollabResource;
import com.vaadin.server.Resource;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.Field;
import com.vaadin.ui.Layout;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.TextField;

/**
 * 
 * @author MyCollab Ltd.
 * @since 1.0
 */
@ViewComponent(scope=ViewScope.PROTOTYPE)
public class TaskAddViewImpl extends AbstractEditItemComp<Task> implements
		TaskAddView {

	private static final long serialVersionUID = 1L;
	private FormAttachmentUploadField attachmentUploadField;

	@Override
	public AttachmentUploadField getAttachUploadField() {
		return this.attachmentUploadField;
	}

	@Override
	public HasEditFormHandlers<Task> getEditFormHandlers() {
		return this.editForm;
	}

	@Override
	protected String initFormHeader() {
		return (beanItem.getId() == null) ? AppContext
				.getMessage(TaskI18nEnum.FORM_NEW_TASK_TITLE) : AppContext
				.getMessage(TaskI18nEnum.FORM_EDIT_TASK_TITLE);
	}

	@Override
	protected String initFormTitle() {
		return (beanItem.getId() == null) ? null : beanItem.getTaskname();
	}

	@Override
	protected Resource initFormIconResource() {
		return MyCollabResource.newResource("icons/24/project/task.png");
	}

	@Override
	protected ComponentContainer createButtonControls() {

		final Layout controlButtons = (new EditFormControlsGenerator<Task>(
				editForm)).createButtonControls(true, true, true);
		controlButtons.setSizeUndefined();

		return controlButtons;
	}

	@Override
	protected AdvancedEditBeanForm<Task> initPreviewForm() {
		return new AdvancedEditBeanForm<Task>();
	}

	@Override
	protected IFormLayoutFactory initFormLayoutFactory() {
		return new TaskFormLayoutFactory();
	}

	@Override
	protected AbstractBeanFieldGroupEditFieldFactory<Task> initBeanFormFieldFactory() {
		return new EditFormFieldFactory(editForm);
	}

	private class EditFormFieldFactory extends
			AbstractBeanFieldGroupEditFieldFactory<Task> {
		private static final long serialVersionUID = 1L;

		public EditFormFieldFactory(GenericBeanForm<Task> form) {
			super(form);
		}

		@Override
		protected Field<?> onCreateField(final Object propertyId) {
			if (propertyId.equals("assignuser")) {
				return new ProjectMemberSelectionField();
			} else if (propertyId.equals("tasklistid")) {
				return new ProjectTaskListComboBox();
			} else if (propertyId.equals("notes")) {
				final RichTextArea richTextArea = new RichTextArea();
				richTextArea.setNullRepresentation("");
				return richTextArea;
			} else if ("name".equals(propertyId)) {
				final TextField tf = new TextField();
				tf.setNullRepresentation("");
				tf.setRequired(true);
				tf.setRequiredError("Please enter a Name");
				return tf;
			} else if ("percentagecomplete".equals(propertyId)) {
				return new TaskPercentageCompleteComboBox();
			} else if ("priority".equals(propertyId)) {
				return new TaskPriorityComboBox();
			} else if (propertyId.equals("id")) {
				TaskAddViewImpl.this.attachmentUploadField = new FormAttachmentUploadField();
				if (beanItem.getId() != null) {
					String attachmentPath = AttachmentUtils
							.getProjectEntityAttachmentPath(
									AppContext.getAccountId(),
									CurrentProjectVariables.getProjectId(),
									AttachmentType.PROJECT_TASK_TYPE,
									beanItem.getId());
					TaskAddViewImpl.this.attachmentUploadField
							.getAttachments(attachmentPath);
				}
				return TaskAddViewImpl.this.attachmentUploadField;
			}
			return null;
		}
	}
}
