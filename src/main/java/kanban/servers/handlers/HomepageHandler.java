package kanban.servers.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.io.IOException;
import java.util.Random;
import java.io.File;

/**
 * @author Oleg Khilko
 */

public class HomepageHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        System.out.println("Началась обработка стартовой страницы");

        httpExchange.getResponseHeaders().set("Content-Type", "image/gif");

        ArrayList<File> homepages = new ArrayList<>();

        homepages.add(new File("src/main/java/kanban/servers/handlers/images/homepage1.png"));
        homepages.add(new File("src/main/java/kanban/servers/handlers/images/homepage2.png"));
        homepages.add(new File("src/main/java/kanban/servers/handlers/images/homepage3.png"));
        homepages.add(new File("src/main/java/kanban/servers/handlers/images/homepage4.png"));
        homepages.add(new File("src/main/java/kanban/servers/handlers/images/homepage5.png"));

        var randomNumber = new Random().nextInt(5);

        var fileInputStream = new FileInputStream(homepages.get(randomNumber));

        var response = fileInputStream.readAllBytes();

        httpExchange.sendResponseHeaders(200, 0);

        try (OutputStream os = httpExchange.getResponseBody()) {
            os.write(response);
        }

        fileInputStream.close();

    }

}