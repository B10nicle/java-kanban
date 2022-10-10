package kanban.servers;

import kanban.servers.handlers.HomepageHandler;
import com.sun.net.httpserver.HttpServer;
import kanban.managers.Managers;

import java.net.InetSocketAddress;
import java.io.IOException;
import java.nio.file.Path;

/**
 * @author Oleg Khilko
 */

public class HttpTaskServer extends KVTaskClient {

    private static final int PORT = 8080;

    public HttpTaskServer() throws IOException {

        var filePath = Path.of("src/main/resources/results.csv");
        var manager = Managers.getDefaultFileBackedManager(filePath);

        var httpServer = HttpServer.create();

        httpServer.bind(new InetSocketAddress(PORT), 0);
        httpServer.createContext("/", new HomepageHandler());

        httpServer.start();

        System.out.println("HTTP-сервер запущен на " + PORT + " порту!");
    }
}
