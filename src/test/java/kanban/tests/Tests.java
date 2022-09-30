package kanban.tests;

import kanban.managers.Managers;
import kanban.managers.taskManagers.TasksManager;
import kanban.tasks.Epic;
import kanban.tasks.Subtask;
import kanban.tasks.Task;
import org.junit.jupiter.api.Test;

import java.time.Instant;

/**
 * @author Oleg Khilko
 */

public class Tests {
    TasksManager manager = Managers.getDefault();

    @Test
    void print() {
        var task1 = manager.createTask(
                new Task("Task1",
                        "Task1_description",
                        Instant.now(),
                        120L));

        Epic epic1 = manager.createEpic(
                new Epic("Epic1",
                        "Epic1_description"));

        var subtask1 = manager.createSubtask(
                new Subtask("Subtask1",
                        "Subtask1_description",
                        Instant.now(),
                        460L,
                        epic1.getId()));

        var subtask2 = manager.createSubtask(
                new Subtask("Subtask2",
                        "Subtask2_description",
                        Instant.now(),
                        3130L,
                        epic1.getId()));

        System.out.println(task1);
        System.out.println(epic1);
        System.out.println(subtask1);
        System.out.println(subtask2);
    }

}
