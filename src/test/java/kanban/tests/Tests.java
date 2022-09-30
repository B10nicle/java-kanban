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

        Task task1 = manager.createTask(
                new Task("Task1", "Task1_description",
                        Instant.now(), 200));

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

    }

}