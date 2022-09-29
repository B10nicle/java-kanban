package kanban.managers.taskManagers;

import kanban.tasks.Epic;
import kanban.tasks.Subtask;
import kanban.tasks.Task;

import java.util.Collection;
import java.util.List;

/**
 * @author Oleg Khilko
 */

public interface TasksManager {

    // получение мапы всех тасков
    Collection<Task> getTasks();

    // получение мапы всех эпиков
    Collection<Epic> getEpics();

    // получение мапы всех сабтасков
    Collection<Subtask> getSubtasks();

    // создание таска
    Task createTask(Task task);

    // создание сабтаска
    Subtask createSubtask(Subtask subtask);

    // создание эпика
    Epic createEpic(Epic epic);

    // удаление эпика
    void removeEpic(Integer epicID);

    // удаление сабтаска
    void removeSubtask(Integer id);

    // удаление таска
    void removeTask(Integer id);

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
    Task getTask(Integer id);

    // запрос сабтаска
    Subtask getSubtask(Integer id);

    // запрос эпика
    Epic getEpic(Integer id);

    // получение истории
    List<Task> getHistory();

}