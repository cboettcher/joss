package nl.tweeenveertig.openstack.client.impl;

import nl.tweeenveertig.openstack.client.Account;
import nl.tweeenveertig.openstack.client.Container;
import nl.tweeenveertig.openstack.client.StoredObject;
import nl.tweeenveertig.openstack.client.core.AbstractContainer;
import nl.tweeenveertig.openstack.command.container.*;
import nl.tweeenveertig.openstack.command.identity.access.Access;
import nl.tweeenveertig.openstack.headers.Header;
import nl.tweeenveertig.openstack.headers.object.ObjectManifest;
import nl.tweeenveertig.openstack.model.UploadInstructions;
import org.apache.http.client.HttpClient;

import java.util.ArrayList;
import java.util.Collection;

public class ContainerImpl extends AbstractContainer {

    public ContainerImpl(Account account, String name) {
        super(account, name);
    }

    public void makePublic() {
        new ContainerRightsCommand(getAccount(), getClient(), getAccess(), this, true).call();
    }

    public void makePrivate() {
        new ContainerRightsCommand(getAccount(), getClient(), getAccess(), this, false).call();
    }

    public Collection<StoredObject> listObjects() {
        Collection<String> objectNames = new ListObjectsCommand(getAccount(), getClient(), getAccess(), this).call();
        Collection<StoredObject> objects = new ArrayList<StoredObject>();
        for (String objectName : objectNames) {
            objects.add(getObject(objectName));
        }
        return objects;
    }

    public void create() {
        new CreateContainerCommand(getAccount(), getClient(), getAccess(), this).call();
    }

    public void delete() {
        new DeleteContainerCommand(getAccount(), getClient(), getAccess(), this).call();
    }

    public StoredObject getObject(String objectName) {
        return new StoredObjectImpl(this, objectName);
    }

    protected HttpClient getClient() {
        return ((AccountImpl)getAccount()).getClient();
    }

    protected Access getAccess() {
        return ((AccountImpl)getAccount()).getAccess();
    }

    // TODO - abstract this one to AbstractContainer? Gives same benefits to Impl and Mock
    protected void uploadSegmentedObjects(UploadInstructions uploadInstructions) {

        // 1. Ask upload instructions to return the segments

        // 2. Upload every individual segment
//        for () {
//
//        }

        // 3. Upload the manifest file
        UploadInstructions manifest = new UploadInstructions(new byte[] {})
                .setObjectManifest(new ObjectManifest(getName()));
        getObject(getName()).uploadObject(manifest);
    }

    @Override
    protected void saveMetadata() {
        new ContainerMetadataCommand(getAccount(), getClient(), getAccess(), this, info.getMetadata()).call();
    }

    protected void getInfo() {
        this.info = new ContainerInformationCommand(getAccount(), getClient(), getAccess(), this).call();
        this.setInfoRetrieved();
    }

}
