package kanban.managers.historyManagers.inMemoryHistoryManager;

import kanban.managers.historyManagers.HistoryManager;
import kanban.tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Oleg Khilko
 */

public class InMemoryHistoryManager implements HistoryManager {

    private final CustomLinkedList historyManager = new CustomLinkedList();
    private final Map<Integer, Node<Task>> history = new HashMap<>();

    // добавление таска
    @Override
    public void add(Task task) {
        Node<Task> node = historyManager.linkLast(task);

        if (history.containsKey(task.getId()))
            historyManager.removeNode(history.get(task.getId()));

        history.put(task.getId(), node);
    }

    // удаление по id
    @Override
    public void remove(int id) {
        historyManager.removeNode(history.get(id));
        history.remove(id);
    }

    // полная очистка истории
    @Override
    public void clear() {
        history.clear();
        historyManager.clear();
    }

    // получение истории
    @Override
    public List<Task> getHistory() {
        return historyManager.getTasks();
    }

    // преобразование истории в строку
    public static String historyToString(HistoryManager manager) {

        StringBuilder sb = new StringBuilder();

        for (Task task : manager.getHistory())
            sb.append(task.getId()).append(",");

        return sb.toString();
    }

    // преобразование истории из строки
    public static List<Integer> historyFromString(String value) {

        List<Integer> history = new ArrayList<>();

        for (var line : value.split(","))
            history.add(Integer.parseInt(line));

        return history;

    }

}