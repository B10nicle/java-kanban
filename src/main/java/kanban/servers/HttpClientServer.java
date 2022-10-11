package kanban.servers;

import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest;
import java.io.IOException;
import java.net.URI;

/**
 * @author Oleg Khilko
 */

public class HttpClientServer {

    public static void main(String[] args) throws IOException, InterruptedException {

        var uri = URI.create("http://localhost:8080/tasks");

        var request = HttpRequest.newBuilder()
                .GET()
                .uri(uri)
                .version(java.net.http.HttpClient.Version.HTTP_1_1)
                .header("Accept", "text/html")
                .build();

        HttpClient client = HttpClient.newHttpClient();

        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();

        HttpResponse<String> response = client.send(request, handler);

        System.out.println("Код ответа: " + response.statusCode());
        System.out.println("Тело ответа: " + response.body());

    }

}
