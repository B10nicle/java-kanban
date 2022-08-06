package kanban.manager.historymanager;

import kanban.task.Task;

import java.util.List;

public interface HistoryManager {
    //добавление таска
    void add(Task task);

    //получение истории
    List<Task> getHistory();
}
