package kanban.manager.historymanager;

import kanban.task.Task;

import java.util.LinkedList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private final List<Task> historyManager = new LinkedList<>();

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
