package org.orienteer.core.method.definitions;

import java.util.HashSet;
import org.orienteer.core.method.IMethod;
import org.orienteer.core.method.IMethodDefinition;
import org.orienteer.core.method.MethodStorage;

/**
 * 
 * Storage for {@link IMethodDefinition}'s loaded from source
 *
 */
public class SourceMethodDefinitionStorage extends AbstractMethodDefinitionStorage{

	public SourceMethodDefinitionStorage(MethodStorage storage) {
		super(storage);
	}

	@Override
	public void reload() {
		definitions = new HashSet<IMethodDefinition>();
		for (Class<? extends IMethod> class1 : methodStorage.getMethodClasses()) {
			if (SourceMethodDefinition.isSupportedClass(class1)){
				try {
					definitions.add(new SourceMethodDefinition(class1));
				} catch (InstantiationException | IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
