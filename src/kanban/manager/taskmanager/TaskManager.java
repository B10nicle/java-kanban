package kanban.manager.taskmanager;

import kanban.task.Epic;
import kanban.task.Subtask;
import kanban.task.Task;

public interface TaskManager {
    //создание таска
    Task createTask(Task task);

    //создание сабтаска
    Subtask createSubTask(Subtask subtask);

    //создание эпика
    Epic createEpic(Epic epic);

    //добавление сабтаска в эпик
    Epic addSubtaskToEpic(Epic epic, Subtask subtask);

    //удаление сабтаска из эпика
    void deleteSubtaskFromEpic(Epic epic, Subtask subtask);

    //удаление эпика из маппы эпиков если список сабтасков пуст
    void deleteSubtaskFromEpic(Epic epic);

    //удаление сабтаска
    void deleteSubtask(Subtask subtask);

    //удаление таска
    void deleteTask(Task task);

    //удаление всех тасков
    void deleteAllTasks();

    //удаление всех сабтасков
    void deleteAllSubtasks();

    //удаление всех эпиков
    void deleteAllEpics();

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
}
