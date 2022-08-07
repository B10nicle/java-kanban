package kanban.manager.historymanager;

import kanban.task.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private final List<Task> historyManager;

    public InMemoryHistoryManager() {
        this.historyManager = new ArrayList<>();
    }

    //добавление таска
    @Override
    public void add(Task task) {
        historyManager.add(task);
        if (historyManager.size() > 10) {
            historyManager.remove(0);
        }
    }

    //получение истории
    @Override
    public List<Task> getHistory() {
        return historyManager;
    }
}
