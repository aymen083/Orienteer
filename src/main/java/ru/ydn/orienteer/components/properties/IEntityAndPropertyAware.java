package ru.ydn.orienteer.components.properties;

import org.apache.wicket.model.IModel;

@Deprecated
public interface IEntityAndPropertyAware<E, P, V>
{
	public IModel<E> getEntityModel();
	
	public IModel<P> getPropertyModel();
	
	public IModel<V> getValueModel();
}
