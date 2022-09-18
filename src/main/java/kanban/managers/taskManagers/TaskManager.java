package kanban.managers.taskManagers;

import kanban.tasks.Epic;
import kanban.tasks.Subtask;
import kanban.tasks.Task;

import java.util.List;
import java.util.Map;

/**
 * @author Oleg Khilko
 */

public interface TaskManager {

    //получение мапы всех тасков
    Map<Integer, Task> getTasks();

    //получение мапы всех эпиков
    Map<Integer, Epic> getEpics();

    //получение мапы всех сабтасков
    Map<Integer, Subtask> getSubtasks();

    //создание таска
    Task createTask(Task task);

    //создание сабтаска
    Subtask createSubtask(Subtask subtask);

    //создание эпика
    Epic createEpic(Epic epic);

    //удаление эпика
    void removeEpic(Integer epicID);

    //удаление сабтаска
    void removeSubtask(Integer id);

    //удаление таска
    void removeTask(Integer id);

    //удаление всех тасков, эпиков и сабтасков
    void removeAllTasksEpicsSubtasks();

    //печать списка всех тасков
    void printAllTasks();

    //печать списка всех сабтасков
    void printAllSubtasks();

    //печать списка всех эпиков
    void printAllEpics();

    //обновление таска
    void updateTask(Task task);

    //обновление сабтаска
    void updateSubtask(Subtask subtask);

    //обновление эпика
    void updateEpic(Epic epic);

    //запрос таска
    Task getTask(Integer id);

    //запрос сабтаска
    Subtask getSubtask(Integer id);

    //запрос эпика
    Epic getEpic(Integer id);

    //получение истории
    List<Task> getHistory();

}