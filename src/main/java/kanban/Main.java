package kanban;

import kanban.managers.taskManagers.fileBackedTasksManager.FileBackedTasksManager;
import kanban.servers.HttpTaskServer;

import java.io.IOException;
import java.nio.file.Path;

/**
 * @author Oleg Khilko
 */

public class Main {

    public static void main(String[] args) throws IOException {

        var manager = FileBackedTasksManager.load(Path.of("src/main/resources/results.csv"));

        new HttpTaskServer(manager);

    }

}