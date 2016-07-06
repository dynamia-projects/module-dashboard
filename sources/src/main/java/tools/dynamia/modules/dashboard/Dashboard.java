/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools.dynamia.modules.dashboard;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.zkoss.zul.Div;

import tools.dynamia.actions.Action;
import tools.dynamia.actions.ActionEvent;
import tools.dynamia.actions.ActionEventBuilder;
import tools.dynamia.viewers.View;
import tools.dynamia.viewers.ViewDescriptor;
import tools.dynamia.zk.actions.ActionToolbar;

/**
 *
 * @author mario
 */
public class Dashboard extends Div implements View<List<DashboardWidgetWindow>> {

	private ViewDescriptor viewDescriptor;
	private View parentView;
	private List<DashboardWidgetWindow> value = new ArrayList<>();
	private ActionToolbar actionToolbar;

	public Dashboard() {
		setSclass("dashboard");
		actionToolbar = new ActionToolbar(new ActionEventBuilder() {

			@Override
			public ActionEvent buildActionEvent(Object source, Map<String, Object> params) {
				return new ActionEvent(Dashboard.this, Dashboard.this);
			}
		});
		appendChild(actionToolbar);
	}

	public void initWidgets() {

		for (DashboardWidgetWindow window : value) {
			DashboardContext context = new DashboardContext(this, window.getField());
			window.getWidget().init(context);
			window.initView(context);
		}
	}

	@Override
	public ViewDescriptor getViewDescriptor() {
		return viewDescriptor;
	}

	@Override
	public void setViewDescriptor(ViewDescriptor viewDescriptor) {
		this.viewDescriptor = viewDescriptor;
	}

	@Override
	public View getParentView() {
		return parentView;
	}

	@Override
	public void setParentView(View parentView) {
		this.parentView = parentView;
	}

	@Override
	public List<DashboardWidgetWindow> getValue() {
		return value;
	}

	@Override
	public void setValue(List<DashboardWidgetWindow> value) {
		this.value = value;
	}

	public DashboardWidgetWindow getWidgetWindow(String name) {
		Optional<DashboardWidgetWindow> windows = value.stream().filter(w -> w.getField().getName().equals(name))
				.findFirst();
		if (windows.isPresent()) {
			return windows.get();
		} else {
			return null;
		}
	}

	public ActionToolbar getActionToolbar() {
		return actionToolbar;
	}

	public void addAction(Action action) {
		actionToolbar.addAction(action);

	}

}
