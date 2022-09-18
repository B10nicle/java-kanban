package kanban.managers;

import kanban.managers.historyManagers.HistoryManager;
import kanban.managers.historyManagers.inMemoryHistoryManager.InMemoryHistoryManager;
import kanban.managers.taskManagers.inMemoryTaskManager.InMemoryTaskManager;
import kanban.managers.taskManagers.TaskManager;

/**
 * @author Oleg Khilko
 */

public class Managers {

    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

}