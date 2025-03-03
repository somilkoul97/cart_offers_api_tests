package com.zomato.mock;

import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import static org.mockserver.model.JsonBody.json;

public class MockServerSetup {
	
    private static ClientAndServer mockServer;

    public static void startMockServer() {
        mockServer = ClientAndServer.startClientAndServer(1080);
        
        // Mock response for user segment lookup
        mockServer.when(
                HttpRequest.request()
                        .withMethod("GET")
                        .withPath("/api/v1/user_segment")
                        .withQueryStringParameter("user_id", "1")
        ).respond(
                HttpResponse.response()
                        .withStatusCode(200)
                        .withBody(json("{\"segment\": \"p1\"}"))
        );
    }

    public static void stopMockServer() {
        if (mockServer != null) {
            mockServer.stop();
        }
    }
}
