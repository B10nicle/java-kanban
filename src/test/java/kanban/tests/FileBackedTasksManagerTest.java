package kanban.tests;

import kanban.managers.Managers;
import kanban.managers.taskManagers.fileBackedTasksManager.FileBackedTasksManager;
import kanban.managers.taskManagers.fileBackedTasksManager.exceptions.ManagerSaveException;
import kanban.managers.taskManagers.fileBackedTasksManager.exceptions.NotSupportedTypeException;
import kanban.tasks.Epic;
import kanban.tasks.Subtask;
import kanban.tasks.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;

public class FileBackedTasksManagerTest extends Assertions {

    Path path = Path.of("results.csv");

    @Test
    public void createAndLoadTasks() {

        FileBackedTasksManager fileManagerBeforeLoading = Managers.getFileBackedTasksManager();

        Task task1 = fileManagerBeforeLoading.createTask(
                new Task("Task1",
                        "Task1_description",
                        Instant.now(), 30));

        Task task2 = fileManagerBeforeLoading.createTask(
                new Task("Task2",
                        "Task2_description",
                        Instant.now(), 40));

        Task task3 = fileManagerBeforeLoading.createTask(
                new Task("Task3",
                        "Task3_description",
                        Instant.now(), 50));

        Epic epic1 = fileManagerBeforeLoading.createEpic(
                new Epic("Epic1", "Epic1_description"));

        Epic epic2 = fileManagerBeforeLoading.createEpic(
                new Epic("Epic2", "Epic2_description"));

        Epic epic3 = fileManagerBeforeLoading.createEpic(
                new Epic("Epic3", "Epic3_description"));

        Subtask subtask1 = fileManagerBeforeLoading.createSubtask(
                new Subtask("Subtask1", "Subtask1_description",
                        Instant.now(), 110, epic1.getId()));

        Subtask subtask2 = fileManagerBeforeLoading.createSubtask(
                new Subtask("Subtask2", "Subtask2_description",
                        Instant.now(), 180, epic1.getId()));

        Subtask subtask3 = fileManagerBeforeLoading.createSubtask(
                new Subtask("Subtask3", "Subtask3_description",
                        Instant.now(), 210, epic1.getId()));

        fileManagerBeforeLoading.getTask(task1.getId());
        fileManagerBeforeLoading.getTask(task2.getId());
        fileManagerBeforeLoading.getTask(task3.getId());
        fileManagerBeforeLoading.getEpic(epic1.getId());
        fileManagerBeforeLoading.getEpic(epic2.getId());
        fileManagerBeforeLoading.getEpic(epic3.getId());
        fileManagerBeforeLoading.getSubtask(subtask1.getId());
        fileManagerBeforeLoading.getSubtask(subtask2.getId());
        fileManagerBeforeLoading.getSubtask(subtask3.getId());

        FileBackedTasksManager fileManagerAfterLoading = FileBackedTasksManager.load(Path.of("results.csv"));

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

    @Test
    public void shouldThrowManagerSaveException() {

        ManagerSaveException exception = assertThrows(
                ManagerSaveException.class,
                () -> {
                    throw new ManagerSaveException("Ошибка записи в файл");
                });

        assertEquals("Ошибка записи в файл", exception.getMessage());
    }

    @Test
    public void shouldNotSupportedTypeException() {

        NotSupportedTypeException exception = assertThrows(
                NotSupportedTypeException.class,
                () -> {
                    throw new NotSupportedTypeException("Данный формат таска не поддерживается");
                });

        assertEquals("Данный формат таска не поддерживается", exception.getMessage());
    }

}