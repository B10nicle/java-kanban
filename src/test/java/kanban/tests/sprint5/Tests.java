package kanban.tests.sprint5;

import kanban.managers.Managers;
import kanban.managers.taskManagers.TasksManager;
import kanban.tasks.Epic;
import kanban.tasks.Subtask;
import kanban.tasks.Task;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Oleg Khilko
 */

public class Tests extends Assert {

    TasksManager tasksManager = Managers.getDefault();

    Task task1 = tasksManager.createTask(
            new Task("Task1", "Task1_description"));
    Task task2 = tasksManager.createTask(
            new Task("Task2", "Task2_description"));
    Task task3 = tasksManager.createTask(
            new Task("Task3", "Task3_description"));

    Epic epic1 = tasksManager.createEpic(
            new Epic("Epic1", "Epic1_description"));
    Epic epic2 = tasksManager.createEpic(
            new Epic("Epic2", "Epic2_description"));
    Epic epic3 = tasksManager.createEpic(
            new Epic("Epic3", "Epic3_description"));

    Subtask subtask1 = tasksManager.createSubtask(
            new Subtask("Subtask1", "Subtask1_description", epic1.getId()));
    Subtask subtask2 = tasksManager.createSubtask(
            new Subtask("Subtask2", "Subtask2_description", epic1.getId()));
    Subtask subtask3 = tasksManager.createSubtask(
            new Subtask("Subtask3", "Subtask3_description", epic1.getId()));

    @Test // Запросите созданные задачи несколько раз в разном порядке
          // После каждого запроса выведите историю и убедитесь, что в ней нет повторов
    public void noDuplicatesInHistory() {

        assertEquals(0, tasksManager.getHistory().size());

        tasksManager.getEpic(epic1.getId());
        tasksManager.getHistory();
        tasksManager.getEpic(epic2.getId());
        tasksManager.getHistory();
        tasksManager.getSubtask(subtask1.getId());
        tasksManager.getHistory();
        tasksManager.getEpic(epic3.getId());
        tasksManager.getHistory();
        tasksManager.getSubtask(subtask2.getId());
        tasksManager.getHistory();
        tasksManager.getTask(task3.getId());
        tasksManager.getHistory();
        tasksManager.getEpic(epic2.getId());
        tasksManager.getHistory();
        tasksManager.getSubtask(subtask1.getId());
        tasksManager.getHistory();
        tasksManager.getTask(task2.getId());
        tasksManager.getHistory();
        tasksManager.getSubtask(subtask3.getId());
        tasksManager.getHistory();
        tasksManager.getEpic(epic1.getId());
        tasksManager.getHistory();
        tasksManager.getTask(task1.getId());
        tasksManager.getHistory();

        assertEquals(9, tasksManager.getHistory().size());

    }

    @Test // Удалите задачу, которая есть в истории, и проверьте, что при печати она не будет выводиться
    public void deleteTaskInHistory() {

        tasksManager.getEpic(epic1.getId());
        tasksManager.getEpic(epic2.getId());
        tasksManager.getEpic(epic3.getId());
        tasksManager.getSubtask(subtask1.getId());
        tasksManager.getSubtask(subtask2.getId());
        tasksManager.getSubtask(subtask3.getId());
        tasksManager.getTask(task1.getId());
        tasksManager.getTask(task2.getId());
        tasksManager.getTask(task3.getId());

        assertEquals(9, tasksManager.getHistory().size());
        tasksManager.removeSubtask(subtask1.getId());
        assertEquals(8, tasksManager.getHistory().size());

    }

    @Test // Удалите эпик с тремя подзадачами и убедитесь, что из истории удалился как сам эпик, так и все его подзадачи
    public void deleteEpicWithAllSubtasksFromTheHistory() {

        tasksManager.getEpic(epic1.getId());
        tasksManager.getEpic(epic2.getId());
        tasksManager.getEpic(epic3.getId());
        tasksManager.getSubtask(subtask1.getId());
        tasksManager.getSubtask(subtask2.getId());
        tasksManager.getSubtask(subtask3.getId());
        tasksManager.getTask(task1.getId());
        tasksManager.getTask(task2.getId());
        tasksManager.getTask(task3.getId());

        assertEquals(9, tasksManager.getHistory().size());
        tasksManager.removeEpic(epic1.getId());
        assertEquals(5, tasksManager.getHistory().size());

    }

}