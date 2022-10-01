package kanban.managers;

import kanban.managers.historyManagers.inMemoryHistoryManager.InMemoryHistoryManager;
import kanban.managers.taskManagers.fileBackedTasksManager.FileBackedTasksManager;
import kanban.managers.taskManagers.inMemoryTasksManager.InMemoryTasksManager;
import kanban.managers.historyManagers.HistoryManager;
import kanban.managers.taskManagers.TasksManager;

/**
 * @author Oleg Khilko
 */

public class Managers {

    public static TasksManager getDefault() {

        return new InMemoryTasksManager();

    }

    public static HistoryManager getDefaultHistory() {

        return new InMemoryHistoryManager();

    }

    public static FileBackedTasksManager getFileBackedTasksManager() {

        return new FileBackedTasksManager();

    }

}