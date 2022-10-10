package kanban.managers;

import kanban.managers.historyManagers.inMemoryHistoryManager.InMemoryHistoryManager;
import kanban.managers.taskManagers.fileBackedTasksManager.FileBackedTasksManager;
import kanban.managers.taskManagers.inMemoryTasksManager.InMemoryTasksManager;
import kanban.managers.historyManagers.HistoryManager;
import kanban.managers.taskManagers.TasksManager;

import java.nio.file.Path;

/**
 * @author Oleg Khilko
 */

public class Managers {

    public static TasksManager getDefaultManager() {

        return new InMemoryTasksManager();

    }

    public static HistoryManager getDefaultHistoryManager() {

        return new InMemoryHistoryManager();

    }

    public static FileBackedTasksManager getDefaultFileBackedManager(Path filePath) {

        return new FileBackedTasksManager(filePath);

    }

}