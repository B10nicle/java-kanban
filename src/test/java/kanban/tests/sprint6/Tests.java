package kanban.tests.sprint6;

import kanban.managers.Managers;
import kanban.managers.taskManagers.fileBackedTasksManager.FileBackedTasksManager;
import kanban.tasks.Epic;
import kanban.tasks.Subtask;
import kanban.tasks.Task;
import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author Oleg Khilko
 */

public class Tests extends Assert {

    Path path = Path.of("results.csv");

    @Test
    public void createAndLoadTasks() {

        FileBackedTasksManager fileManagerBeforeLoading = Managers.getFileBackedTasksManager();

        Task task1 = fileManagerBeforeLoading.createTask(
                new Task("Task1", "Task1_description"));
        Task task2 = fileManagerBeforeLoading.createTask(
                new Task("Task2", "Task2_description"));
        Task task3 = fileManagerBeforeLoading.createTask(
                new Task("Task3", "Task3_description"));

        Epic epic1 = fileManagerBeforeLoading.createEpic(
                new Epic("Epic1", "Epic1_description"));
        Epic epic2 = fileManagerBeforeLoading.createEpic(
                new Epic("Epic2", "Epic2_description"));
        Epic epic3 = fileManagerBeforeLoading.createEpic(
                new Epic("Epic3", "Epic3_description"));

        Subtask subtask1 = fileManagerBeforeLoading.createSubtask(
                new Subtask("Subtask1", "Subtask1_description", epic1.getId()));
        Subtask subtask2 = fileManagerBeforeLoading.createSubtask(
                new Subtask("Subtask2", "Subtask2_description", epic1.getId()));
        Subtask subtask3 = fileManagerBeforeLoading.createSubtask(
                new Subtask("Subtask3", "Subtask3_description", epic1.getId()));

        fileManagerBeforeLoading.getTask(task1.getId());
        fileManagerBeforeLoading.getTask(task2.getId());
        fileManagerBeforeLoading.getTask(task3.getId());
        fileManagerBeforeLoading.getEpic(epic1.getId());
        fileManagerBeforeLoading.getEpic(epic2.getId());
        fileManagerBeforeLoading.getEpic(epic3.getId());
        fileManagerBeforeLoading.getSubtask(subtask1.getId());
        fileManagerBeforeLoading.getSubtask(subtask2.getId());
        fileManagerBeforeLoading.getSubtask(subtask3.getId());

        var fileManagerAfterLoading = FileBackedTasksManager.load(Path.of("results.csv"));

        // проверяю все таски
        assertEquals(fileManagerBeforeLoading.getTask(1), fileManagerAfterLoading.getTask(1));
        assertEquals(fileManagerBeforeLoading.getTask(2), fileManagerAfterLoading.getTask(2));
        assertEquals(fileManagerBeforeLoading.getTask(3), fileManagerAfterLoading.getTask(3));

        // проверяю все эпики
        assertEquals(fileManagerBeforeLoading.getEpic(4), fileManagerAfterLoading.getEpic(4));
        assertEquals(fileManagerBeforeLoading.getEpic(5), fileManagerAfterLoading.getEpic(5));
        assertEquals(fileManagerBeforeLoading.getEpic(6), fileManagerAfterLoading.getEpic(6));

        // проверяю все сабтаски
        assertEquals(fileManagerBeforeLoading.getSubtask(7), fileManagerAfterLoading.getSubtask(7));
        assertEquals(fileManagerBeforeLoading.getSubtask(8), fileManagerAfterLoading.getSubtask(8));
        assertEquals(fileManagerBeforeLoading.getSubtask(9), fileManagerAfterLoading.getSubtask(9));

    }

    @Test
    public void fileExists() {
        assertTrue(Files.exists(path));
    }

    @Test
    public void fileHasContent() throws IOException {

        BufferedReader br = new BufferedReader(new FileReader(String.valueOf(path)));
        int amountOfLines = 0;

        while (br.ready()) {
            br.readLine();
            amountOfLines++;
        }

        assertEquals(12, amountOfLines);
    }

}