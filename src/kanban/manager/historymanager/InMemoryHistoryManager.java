package kanban.manager.historymanager;

import kanban.task.Task;

import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    HistoryManager historyManager;

    //добавление таска
    @Override
    public void add(Task task) {
        historyManager.add(task);
    }

    //получение истории
    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }
}
