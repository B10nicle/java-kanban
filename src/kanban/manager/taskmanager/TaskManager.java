package kanban.manager.taskmanager;

import kanban.task.Epic;
import kanban.task.Subtask;
import kanban.task.Task;

import java.util.List;

public interface TaskManager {
    //создание таска
    Task createTask(Task task);

    //создание сабтаска
    Subtask createSubtask(Subtask subtask);

    //создание эпика
    Epic createEpic(Epic epic);

    //удаление эпика
    void deleteEpic(int epicID);

    //удаление сабтаска
    void deleteSubtask(int id);

    //удаление таска
    void deleteTask(int id);

    //удаление всех тасков, эпиков и сабтасков
    void deleteAllTasksEpicsSubtasks();

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
    Task getTask(int id);

    //запрос сабтаска
    Subtask getSubtask(int id);

    //запрос эпика
    Epic getEpic(int id);

    //получение истории
    List<Task> getHistory();
}
