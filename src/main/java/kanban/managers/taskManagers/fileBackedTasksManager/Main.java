package kanban.managers.taskManagers.fileBackedTasksManager;

import kanban.tasks.Epic;
import kanban.tasks.Subtask;
import kanban.tasks.Task;

import java.io.File;

/**
 * @author Oleg Khilko
 */

public class Main {

    public static void main(String[] args) {

        var fileManager = new FileBackedTasksManager();

        var task1 = fileManager.createTask(
                new Task("Task1", "Task1_description"));
        var task2 = fileManager.createTask(
                new Task("Task2", "Task2_description"));
        var task3 = fileManager.createTask(
                new Task("Task3", "Task3_description"));

        var epic1 = fileManager.createEpic(
                new Epic("Epic1", "Epic1_description"));
        var epic2 = fileManager.createEpic(
                new Epic("Epic2", "Epic2_description"));
        var epic3 = fileManager.createEpic(
                new Epic("Epic3", "Epic3_description"));

        var subtask1 = fileManager.createSubtask(
                new Subtask("Subtask1", "Subtask1_description", epic1.getId()));
        var subtask2 = fileManager.createSubtask(
                new Subtask("Subtask2", "Subtask2_description", epic1.getId()));
        var subtask3 = fileManager.createSubtask(
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


    }

}