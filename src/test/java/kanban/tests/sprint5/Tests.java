package kanban.tests.sprint5;

import kanban.managers.Managers;
import kanban.managers.taskManagers.TaskManager;
import kanban.tasks.Epic;
import kanban.tasks.Subtask;
import kanban.tasks.Task;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Oleg Khilko
 */

public class Tests extends Assert {

    TaskManager taskManager = Managers.getDefault();

    Task task1 = taskManager.createTask(
            new Task("Task1", "Task1_description"));
    Task task2 = taskManager.createTask(
            new Task("Task2", "Task2_description"));
    Task task3 = taskManager.createTask(
            new Task("Task3", "Task3_description"));

    Epic epic1 = taskManager.createEpic(
            new Epic("Epic1", "Epic1_description"));
    Epic epic2 = taskManager.createEpic(
            new Epic("Epic2", "Epic2_description"));
    Epic epic3 = taskManager.createEpic(
            new Epic("Epic3", "Epic3_description"));

    Subtask subtask1 = taskManager.createSubtask(
            new Subtask("Subtask1", "Subtask1_description", epic1.getId()));
    Subtask subtask2 = taskManager.createSubtask(
            new Subtask("Subtask2", "Subtask2_description", epic1.getId()));
    Subtask subtask3 = taskManager.createSubtask(
            new Subtask("Subtask3", "Subtask3_description", epic1.getId()));

    @Test // Запросите созданные задачи несколько раз в разном порядке
          // После каждого запроса выведите историю и убедитесь, что в ней нет повторов
    public void noDuplicatesInHistory() {

        assertEquals(0, taskManager.getHistory().size());

        taskManager.getEpic(epic1.getId());
        taskManager.getHistory();
        taskManager.getEpic(epic2.getId());
        taskManager.getHistory();
        taskManager.getSubtask(subtask1.getId());
        taskManager.getHistory();
        taskManager.getEpic(epic3.getId());
        taskManager.getHistory();
        taskManager.getSubtask(subtask2.getId());
        taskManager.getHistory();
        taskManager.getTask(task3.getId());
        taskManager.getHistory();
        taskManager.getEpic(epic2.getId());
        taskManager.getHistory();
        taskManager.getSubtask(subtask1.getId());
        taskManager.getHistory();
        taskManager.getTask(task2.getId());
        taskManager.getHistory();
        taskManager.getSubtask(subtask3.getId());
        taskManager.getHistory();
        taskManager.getEpic(epic1.getId());
        taskManager.getHistory();
        taskManager.getTask(task1.getId());
        taskManager.getHistory();

        assertEquals(9, taskManager.getHistory().size());

    }

    @Test // Удалите задачу, которая есть в истории, и проверьте, что при печати она не будет выводиться
    public void deleteTaskInHistory() {

        taskManager.getEpic(epic1.getId());
        taskManager.getEpic(epic2.getId());
        taskManager.getEpic(epic3.getId());
        taskManager.getSubtask(subtask1.getId());
        taskManager.getSubtask(subtask2.getId());
        taskManager.getSubtask(subtask3.getId());
        taskManager.getTask(task1.getId());
        taskManager.getTask(task2.getId());
        taskManager.getTask(task3.getId());

        assertEquals(9, taskManager.getHistory().size());
        taskManager.removeSubtask(subtask1.getId());
        assertEquals(8, taskManager.getHistory().size());

    }

    @Test // Удалите эпик с тремя подзадачами и убедитесь, что из истории удалился как сам эпик, так и все его подзадачи
    public void deleteEpicWithAllSubtasksFromTheHistory() {

        taskManager.getEpic(epic1.getId());
        taskManager.getEpic(epic2.getId());
        taskManager.getEpic(epic3.getId());
        taskManager.getSubtask(subtask1.getId());
        taskManager.getSubtask(subtask2.getId());
        taskManager.getSubtask(subtask3.getId());
        taskManager.getTask(task1.getId());
        taskManager.getTask(task2.getId());
        taskManager.getTask(task3.getId());

        assertEquals(9, taskManager.getHistory().size());
        taskManager.removeEpic(epic1.getId());
        assertEquals(5, taskManager.getHistory().size());

    }

}