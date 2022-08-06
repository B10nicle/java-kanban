package kanban.manager;

import kanban.manager.historymanager.HistoryManager;
import kanban.manager.historymanager.InMemoryHistoryManager;
import kanban.manager.taskmanager.InMemoryTaskManager;
import kanban.manager.taskmanager.TaskManager;

public class Managers {
    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
