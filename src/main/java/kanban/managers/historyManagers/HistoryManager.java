package kanban.managers.historyManagers;

import kanban.tasks.Task;

import java.util.List;

/**
 * @author Oleg Khilko
 */

public interface HistoryManager {

    //добавление таска
    void add(Task task);

    //удаление по id
    void remove(int id);

    //полная очистка истории
    void clear();

    //получение истории
    List<Task> getHistory();

}