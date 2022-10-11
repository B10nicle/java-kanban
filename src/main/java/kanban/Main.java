package kanban;

import kanban.managers.taskManagers.fileBackedTasksManager.FileBackedTasksManager;
import kanban.managers.taskManagers.TasksManager;
import kanban.servers.HttpTaskServer;
import kanban.tasks.enums.TaskType;
import kanban.managers.Managers;
import kanban.servers.KVServer;
import kanban.tasks.Subtask;
import kanban.tasks.Task;
import kanban.tasks.Epic;

import java.io.IOException;
import java.nio.file.Path;
import java.time.Instant;

/**
 * @author Oleg Khilko
 */

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {

        var fileBackedManager = FileBackedTasksManager.load(Path.of("src/main/resources/results.csv"));
        new HttpTaskServer(fileBackedManager);

        new KVServer().start();
        TasksManager defaultManager = Managers.getDefaultManager();

        var task1 = defaultManager.createTask(
                new Task("Task1", "Task1D", Instant.ofEpochSecond(600_000), 1000));

        var epic1 = defaultManager.createEpic(
                new Epic("Epic1", "Epic1D", TaskType.EPIC));

        var subtask1 = defaultManager.createSubtask(
                new Subtask("Subtask1", "Subtask1D", Instant.EPOCH, 500, epic1.getId()));

        var subtask2 = defaultManager.createSubtask(
                new Subtask("Subtask2", "Subtask2D", Instant.ofEpochSecond(500_000),500, epic1.getId()));

        defaultManager.getTask(task1.getId());
        defaultManager.getEpic(epic1.getId());
        defaultManager.getSubtask(subtask1.getId());
        defaultManager.getSubtask(subtask2.getId());
        defaultManager.getHistory();

/*      HttpTasksManager client = new HttpTasksManager();
        client.read();
        client.getPrioritizedTasks();*/

    }

}