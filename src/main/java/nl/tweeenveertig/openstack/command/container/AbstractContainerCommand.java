package nl.tweeenveertig.openstack.command.container;

import nl.tweeenveertig.openstack.command.core.AbstractSecureCommand;
import nl.tweeenveertig.openstack.command.identity.access.Access;
import nl.tweeenveertig.openstack.client.Container;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpRequestBase;

public abstract class AbstractContainerCommand<M extends HttpRequestBase, N extends Object> extends AbstractSecureCommand<M, N> {

    public AbstractContainerCommand(HttpClient httpClient, Access access, Container container) {
        super(httpClient, access.getInternalURL() + "/" + container.getName(), access.getToken());
    }
}