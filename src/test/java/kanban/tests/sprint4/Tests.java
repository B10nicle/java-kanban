package kanban.tests.sprint4;

import kanban.managers.Managers;
import kanban.managers.taskManagers.TaskManager;
import kanban.tasks.Epic;
import kanban.tasks.Subtask;
import kanban.tasks.Task;
import kanban.tasks.enums.TaskState;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Oleg Khilko
 */

public class Tests extends Assert {

    TaskManager taskManager = Managers.getDefault();

    Task task1 = taskManager.createTask(
            new Task("Task1", "Task1_description"));
    Task task2 = taskManager.createTask(
            new Task("Task2", "Task2_description"));
    Task task3 = taskManager.createTask(
            new Task("Task3", "Task3_description"));

    Epic epic1 = taskManager.createEpic(
            new Epic("Epic1", "Epic1_description"));
    Epic epic2 = taskManager.createEpic(
            new Epic("Epic2", "Epic2_description"));
    Epic epic3 = taskManager.createEpic(
            new Epic("Epic3", "Epic3_description"));

    Subtask subtask1 = taskManager.createSubtask(
            new Subtask("Subtask1", "Subtask1_description", epic1.getId()));
    Subtask subtask2 = taskManager.createSubtask(
            new Subtask("Subtask2", "Subtask2_description", epic1.getId()));
    Subtask subtask3 = taskManager.createSubtask(
            new Subtask("Subtask3", "Subtask3_description", epic1.getId()));

    @Test // Распечатать все таски
    public void printAllTasks() {

        taskManager.printAllTasks();
        assertEquals(3, taskManager.getTasks().size());

    }

    @Test // Распечатать все эпики
    public void printAllEpics() {

        taskManager.printAllEpics();
        assertEquals(3, taskManager.getEpics().size());

    }

    @Test // Распечатать все сабтаски
    public void printAllSubtasks() {

        taskManager.printAllSubtasks();
        assertEquals(3, taskManager.getSubtasks().size());

    }

    @Test // Проверить getHistory()
    public void getHistory() {

        taskManager.getTask(task1.getId());
        taskManager.getTask(task2.getId());
        taskManager.getTask(task3.getId());
        taskManager.getSubtask(subtask1.getId());
        taskManager.getSubtask(subtask2.getId());
        taskManager.getSubtask(subtask3.getId());
        taskManager.getEpic(epic1.getId());
        taskManager.getEpic(epic2.getId());
        taskManager.getEpic(epic3.getId());

        assertEquals(9, taskManager.getHistory().size());

    }

    @Test // проверить изменение статусов
    public void setTaskState() {

        task1.setTaskState(TaskState.NEW);
        assertEquals(TaskState.NEW, task1.getTaskState());

        task2.setTaskState(TaskState.NEW);
        assertEquals(TaskState.NEW, task2.getTaskState());

        epic1.setTaskState(TaskState.NEW);
        assertEquals(TaskState.NEW, epic1.getTaskState());

        epic2.setTaskState(TaskState.NEW);
        assertEquals(TaskState.NEW, epic2.getTaskState());

        subtask1.setTaskState(TaskState.NEW);
        assertEquals(TaskState.NEW, subtask1.getTaskState());

        subtask2.setTaskState(TaskState.NEW);
        assertEquals(TaskState.NEW, subtask2.getTaskState());

        subtask3.setTaskState(TaskState.NEW);
        assertEquals(TaskState.NEW, subtask3.getTaskState());

        subtask1.setTaskState(TaskState.IN_PROGRESS);
        taskManager.updateTask(subtask1);
        assertEquals(TaskState.IN_PROGRESS, subtask1.getTaskState());

        subtask3.setTaskState(TaskState.DONE);
        taskManager.updateTask(subtask3);
        assertEquals(TaskState.DONE, subtask3.getTaskState());

        subtask1.setTaskState(TaskState.DONE);
        taskManager.updateSubtask(subtask1);
        assertEquals(TaskState.DONE, subtask1.getTaskState());

        subtask2.setTaskState(TaskState.DONE);
        taskManager.updateSubtask(subtask2);
        assertEquals(TaskState.DONE, subtask2.getTaskState());

    }

    @Test // проверить удаление таска
    public void removeTask() {

        assertEquals(3, taskManager.getTasks().size());
        taskManager.getTask(task1.getId());

        taskManager.removeTask(task1.getId());

        assertEquals(2, taskManager.getTasks().size());

    }

    @Test // проверить удаление эпика
    public void removeEpic() {

        assertEquals(3, taskManager.getEpics().size());
        taskManager.getEpic(epic1.getId());
        taskManager.getSubtask(subtask1.getId());
        taskManager.getSubtask(subtask2.getId());
        taskManager.getSubtask(subtask3.getId());

        taskManager.removeEpic(epic1.getId());

        assertEquals(2, taskManager.getEpics().size());

    }

    @Test // проверить удаление всех тасков, эпиков и сабтасков
    public void removeAllTasksEpicsSubtasks() {

        taskManager.getTask(task1.getId());
        taskManager.getTask(task2.getId());
        taskManager.getTask(task3.getId());
        taskManager.getSubtask(subtask1.getId());
        taskManager.getSubtask(subtask2.getId());
        taskManager.getSubtask(subtask3.getId());
        taskManager.getEpic(epic1.getId());
        taskManager.getEpic(epic2.getId());
        taskManager.getEpic(epic3.getId());

        assertEquals(9, taskManager.getHistory().size());

        taskManager.removeAllTasksEpicsSubtasks();

        assertEquals(0, taskManager.getHistory().size());

    }

}