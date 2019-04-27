import org.junit.Test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpClientTest {

    private final String url = "https://www.google.com/";
    private final String s_json = "{\"firstName\":\"Matteo\",\"lastName\":\"Pipitone\",\"year\":\"1993\"}";

    @Test
    public void get() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();

        HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.statusCode());
        System.out.println(response.headers());
        System.out.println(response.body());
        assertTrue("Status code shoul be OK", response.statusCode() == 200);
    }

    //change url for right test
    @Test
    public void post() {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(s_json))
                .build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::statusCode)
                .thenAccept(statusCode -> assertEquals("Status code shoul be Created", 201, (int) statusCode));
    }
}