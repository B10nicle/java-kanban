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

        FileBackedTasksManager fileManager = Managers.getFileBackedTasksManager();

        Task task1 = fileManager.createTask(
                new Task("Task1", "Task1_description"));
        Task task2 = fileManager.createTask(
                new Task("Task2", "Task2_description"));
        Task task3 = fileManager.createTask(
                new Task("Task3", "Task3_description"));

        Epic epic1 = fileManager.createEpic(
                new Epic("Epic1", "Epic1_description"));
        Epic epic2 = fileManager.createEpic(
                new Epic("Epic2", "Epic2_description"));
        Epic epic3 = fileManager.createEpic(
                new Epic("Epic3", "Epic3_description"));

        Subtask subtask1 = fileManager.createSubtask(
                new Subtask("Subtask1", "Subtask1_description", epic1.getId()));
        Subtask subtask2 = fileManager.createSubtask(
                new Subtask("Subtask2", "Subtask2_description", epic1.getId()));
        Subtask subtask3 = fileManager.createSubtask(
                new Subtask("Subtask3", "Subtask3_description", epic1.getId()));

        fileManager.getTask(task1.getId());
        fileManager.getTask(task2.getId());
        fileManager.getTask(task3.getId());
        fileManager.getEpic(epic1.getId());
        fileManager.getEpic(epic2.getId());
        fileManager.getEpic(epic3.getId());
        fileManager.getSubtask(subtask1.getId());
        fileManager.getSubtask(subtask2.getId());
        fileManager.getSubtask(subtask3.getId());

        FileBackedTasksManager.load(Path.of("results.csv"));

    }

    @Test
    public void fileIsExist() {
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
    public void fileContentIsRight() throws IOException {

        BufferedReader br = new BufferedReader(new FileReader(String.valueOf(path)));
        StringBuilder sb = new StringBuilder();

        while (br.ready()) {
            sb.append(br.readLine()).append("\n");
        }

        String expectedResult = "id,type,name,status,description,epic\n" +
                "1,TASK,Task1,NEW,Task1_description\n" +
                "2,TASK,Task2,NEW,Task2_description\n" +
                "3,TASK,Task3,NEW,Task3_description\n" +
                "4,EPIC,Epic1,NEW,Epic1_description\n" +
                "5,EPIC,Epic2,NEW,Epic2_description\n" +
                "6,EPIC,Epic3,NEW,Epic3_description\n" +
                "7,SUBTASK,Subtask1,NEW,Subtask1_description,4\n" +
                "8,SUBTASK,Subtask2,NEW,Subtask2_description,4\n" +
                "9,SUBTASK,Subtask3,NEW,Subtask3_description,4" +
                "\n\n" + "1,2,3,4,5,6,7,8,9," + "\n";

        assertEquals(expectedResult, sb.toString());

    }

}