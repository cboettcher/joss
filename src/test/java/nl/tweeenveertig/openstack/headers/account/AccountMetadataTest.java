package nl.tweeenveertig.openstack.headers.account;

import nl.tweeenveertig.openstack.headers.AbstractHeaderTest;
import org.junit.Test;

public class AccountMetadataTest extends AbstractHeaderTest {

    @Test
    public void addHeader() {
        testHeader(new AccountMetadata("name", "value"));
    }
}
