package org.orienteer.graph.component.command;

import com.orientechnologies.orient.core.id.ORID;
import com.orientechnologies.orient.core.metadata.schema.OClass;
import com.orientechnologies.orient.core.record.impl.ODocument;
import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory;
import com.tinkerpop.blueprints.impls.orient.OrientVertex;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.orienteer.core.component.BootstrapType;
import org.orienteer.core.component.command.AbstractDeleteCommand;
import org.orienteer.core.component.command.Command;
import org.orienteer.core.component.property.DisplayMode;
import org.orienteer.core.component.table.OrienteerDataTable;
import org.orienteer.core.web.ODocumentPage;
import org.orienteer.graph.module.GraphModule;

import ru.ydn.wicket.wicketorientdb.model.OClassModel;
import ru.ydn.wicket.wicketorientdb.security.ISecuredComponent;
import ru.ydn.wicket.wicketorientdb.security.OSecurityHelper;
import ru.ydn.wicket.wicketorientdb.security.OrientPermission;
import ru.ydn.wicket.wicketorientdb.security.RequiredOrientResource;

import java.util.List;

/**
 * {@link Command} to unlink vertex
 */
public class UnlinkVertexCommand extends AbstractDeleteCommand<ODocument> implements ISecuredComponent
{
	private static final long serialVersionUID = 1L;
	private IModel<OClass> classModel;
    private IModel<ODocument> documentModel;

    public UnlinkVertexCommand(OrienteerDataTable<ODocument, ?> table, IModel<ODocument> documentModel)
    {
        super(new ResourceModel("command.unlink"), table);
        setBootstrapType(BootstrapType.WARNING);
        this.documentModel = documentModel;
        this.classModel = new OClassModel(GraphModule.EDGE_CLASS_NAME);
    }
	
	@Override
	protected void performMultiAction(AjaxRequestTarget target, List<ODocument> objects) {
		super.performMultiAction(target, objects);
        OrientGraph tx = new OrientGraphFactory(getDatabase().getURL()).getTx();
        tx.commit();
        for (ODocument doc : objects) {
            ORID id = doc.getIdentity();
            OrientVertex vertex = tx.getVertex(id);

            removeEdges(tx, vertex);
        }
        tx.begin();
        setResponsePage(new ODocumentPage(documentModel.getObject()).setModeObject(DisplayMode.VIEW));
	}

    private void removeEdges(OrientGraph tx, OrientVertex vertex) {
        OrientVertex destination = tx.getVertex(documentModel.getObject().getIdentity());
        Iterable<Edge> edges = vertex.getEdges(destination, Direction.BOTH);
        for(Edge edge : edges) {
            tx.removeEdge(edge);
        }
    }

    @Override
	public RequiredOrientResource[] getRequiredResources() {
		return OSecurityHelper.requireOClass(classModel.getObject(), OrientPermission.DELETE);
	}
    
    @Override
    public void detachModels() {
    	super.detachModels();
    	classModel.detach();
    	documentModel.detach();
    }

}
