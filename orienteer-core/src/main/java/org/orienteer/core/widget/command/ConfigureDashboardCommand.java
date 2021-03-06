package org.orienteer.core.widget.command;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;
import org.orienteer.core.component.BootstrapSize;
import org.orienteer.core.component.BootstrapType;
import org.orienteer.core.component.FAIconType;
import org.orienteer.core.component.command.AjaxCommand;
import org.orienteer.core.component.property.DisplayMode;
import org.orienteer.core.widget.DashboardPanel;

import com.orientechnologies.orient.core.record.impl.ODocument;

/**
 * Command to enable modification of dashboard
 */
public class ConfigureDashboardCommand extends AjaxCommand<ODocument> {
	
	public ConfigureDashboardCommand(String id, IModel<ODocument> dashboardDocumentModel) {
		super(id, "command.configure.widget", dashboardDocumentModel);
		setIcon(FAIconType.gear);
		setBootstrapType(BootstrapType.DEFAULT);
		setBootstrapSize(BootstrapSize.EXTRA_SMALL);
		setChangingDisplayMode(true);
	}

	@Override
	public void onClick(AjaxRequestTarget target) {
		DashboardPanel<?> dashboard = findParent(DashboardPanel.class);
		dashboard.getModeModel().setObject(DisplayMode.EDIT);
		target.add(dashboard);
	}

}
