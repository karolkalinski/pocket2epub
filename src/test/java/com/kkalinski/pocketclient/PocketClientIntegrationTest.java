package com.kkalinski.pocketclient;

import static org.junit.Assert.assertNotNull;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

public class PocketClientIntegrationTest {

    private final PocketClient pocketClient = new PocketClient();

    @Test
    @Ignore
    public void testAuthroize() throws UnsupportedEncodingException {

        // when
        pocketClient.authorize();

        // then

    }

    @Test
    public void testRetriveDocumentsList() throws UnsupportedEncodingException {

        // when
        final List<String> retriveDocumentsList = pocketClient.retriveDocumentsList();

        // then
        assertNotNull(retriveDocumentsList);
    }

}
