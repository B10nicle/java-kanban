package kanban.tests;

import kanban.managers.taskManagers.TasksManager;
import kanban.tasks.Epic;
import kanban.tasks.Subtask;
import kanban.tasks.Task;

import java.time.Duration;
import java.time.Instant;

/**
 * @author Oleg Khilko
 */

public class TaskManagerTest {

    TasksManager manager;

    Task createTask() {
        return new Task("Task1", "Task1_description",
                Instant.now(), 0);
    }

    Epic createEpic() {
        return new Epic("Epic1", "Epic1_description");
    }

    Subtask createSubtask(Epic epic) {
        return new Subtask("Subtask1", "Subtask1_description",
                Instant.now(), 0, epic.getId());
    }
}