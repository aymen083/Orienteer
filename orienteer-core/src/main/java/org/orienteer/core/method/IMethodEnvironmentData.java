package org.orienteer.core.method;

import org.apache.wicket.model.IModel;
import org.orienteer.core.widget.AbstractWidget;

/**
 * 
 * Interface for setting method environment data.
 * We recommend using {@link MethodBaseData} instead your implementation of this interface.  
 *
 */
public interface IMethodEnvironmentData {
	/**
	 * {@link IModel} for current displayed object
	 * @return
	 */
	public IModel<?> getDisplayObjectModel();
	/**
	 * Current displayed widget
	 * @return
	 */
	public AbstractWidget<?> getCurrentWidget();
	/**
	 * Current displayed widget type
	 * @return
	 */
	public String getCurrentWidgetType();
	/**
	 * Current place
	 * 
	 * @return
	 */
	public MethodPlace getPlace();
}
