package kanban.manager.historymanager;

import kanban.task.Task;

import java.util.List;

public interface HistoryManager {
    void add(Task task);
    List<Task> getHistory();
}
