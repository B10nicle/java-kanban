package kanban.managers.taskManagers;

import kanban.tasks.Subtask;
import kanban.tasks.Task;
import kanban.tasks.Epic;

import java.util.List;

/**
 * @author Oleg Khilko
 */

public interface TasksManager {

    // получение мапы всех тасков
    List<Task> getTasks();

    // получение мапы всех эпиков
    List<Epic> getEpics();

    // получение мапы всех сабтасков
    List<Subtask> getSubtasks();

    // создание таска
    Task createTask(Task task);

    // создание сабтаска
    Subtask createSubtask(Subtask subtask);

    // создание эпика
    Epic createEpic(Epic epic);

    // удаление эпика
    void removeEpic(int epicID);

    // удаление сабтаска
    void removeSubtask(int id);

    // удаление таска
    void removeTask(int id);

    // удаление всех тасков, эпиков и сабтасков
    void removeAllTasksEpicsSubtasks();

    // печать списка всех тасков
    void printAllTasks();

    // печать списка всех сабтасков
    void printAllSubtasks();

    // печать списка всех эпиков
    void printAllEpics();

    // обновление таска
    void update(Task task);

    // обновление сабтаска
    void update(Subtask subtask);

    // обновление эпика
    void update(Epic epic);

    // запрос таска
    Task getTask(int id);

    // запрос сабтаска
    Subtask getSubtask(int id);

    // запрос эпика
    Epic getEpic(int id);

    // получение истории
    List<Task> getHistory();

    // печать приоритетного списка
    void printPrioritizedTasks();

}