package kanban.tests;

import kanban.managers.Managers;
import kanban.managers.taskManagers.TasksManager;
import kanban.tasks.Epic;
import kanban.tasks.Subtask;
import kanban.tasks.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;

public class HistoryTest extends Assertions {

    TasksManager manager = Managers.getDefault();

    Task task1 = manager.createTask(
            new Task("Task1", "Task1_description",
                    Instant.now(), 60));

    Task task2 = manager.createTask(
            new Task("Task2", "Task2_description",
                    Instant.now(), 70));

    Task task3 = manager.createTask(
            new Task("Task3", "Task3_description",
                    Instant.now(), 80));

    Epic epic1 = manager.createEpic(
            new Epic("Epic1", "Epic1_description"));

    Epic epic2 = manager.createEpic(
            new Epic("Epic2", "Epic2_description"));

    Epic epic3 = manager.createEpic(
            new Epic("Epic3", "Epic3_description"));

    Subtask subtask1 = manager.createSubtask(
            new Subtask("Subtask1", "Subtask1_description",
                    Instant.now(), 90, epic1.getId()));

    Subtask subtask2 = manager.createSubtask(
            new Subtask("Subtask2", "Subtask2_description",
                    Instant.now(), 100, epic1.getId()));

    Subtask subtask3 = manager.createSubtask(
            new Subtask("Subtask3", "Subtask3_description",
                    Instant.now(), 110, epic1.getId()));

    @Test // Запросите созданные задачи несколько раз в разном порядке
    // После каждого запроса выведите историю и убедитесь, что в ней нет повторов
    public void noDuplicatesInHistory() {

        assertEquals(0, manager.getHistory().size());

        manager.getEpic(epic1.getId());
        manager.getHistory();
        manager.getEpic(epic2.getId());
        manager.getHistory();
        manager.getSubtask(subtask1.getId());
        manager.getHistory();
        manager.getEpic(epic3.getId());
        manager.getHistory();
        manager.getSubtask(subtask2.getId());
        manager.getHistory();
        manager.getTask(task3.getId());
        manager.getHistory();
        manager.getEpic(epic2.getId());
        manager.getHistory();
        manager.getSubtask(subtask1.getId());
        manager.getHistory();
        manager.getTask(task2.getId());
        manager.getHistory();
        manager.getSubtask(subtask3.getId());
        manager.getHistory();
        manager.getEpic(epic1.getId());
        manager.getHistory();
        manager.getTask(task1.getId());
        manager.getHistory();

        assertEquals(9, manager.getHistory().size());

    }

    @Test // Удалите задачу, которая есть в истории, и проверьте, что при печати она не будет выводиться
    public void deleteTaskInHistory() {

        manager.getEpic(epic1.getId());
        manager.getEpic(epic2.getId());
        manager.getEpic(epic3.getId());
        manager.getSubtask(subtask1.getId());
        manager.getSubtask(subtask2.getId());
        manager.getSubtask(subtask3.getId());
        manager.getTask(task1.getId());
        manager.getTask(task2.getId());
        manager.getTask(task3.getId());

        assertEquals(9, manager.getHistory().size());
        manager.removeTask(task1.getId());
        assertEquals(8, manager.getHistory().size());

    }

    @Test // Удалите эпик с тремя подзадачами и убедитесь, что из истории удалился как сам эпик, так и все его подзадачи
    public void deleteEpicWithAllSubtasksFromTheHistory() {

        manager.getEpic(epic1.getId());
        manager.getEpic(epic2.getId());
        manager.getEpic(epic3.getId());
        manager.getSubtask(subtask1.getId());
        manager.getSubtask(subtask2.getId());
        manager.getSubtask(subtask3.getId());
        manager.getTask(task1.getId());
        manager.getTask(task2.getId());
        manager.getTask(task3.getId());

        assertEquals(9, manager.getHistory().size());
        manager.removeEpic(epic1.getId());
        assertEquals(5, manager.getHistory().size());

    }

}
