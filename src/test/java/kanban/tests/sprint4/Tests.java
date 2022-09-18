package kanban.tests.sprint4;

import kanban.managers.Managers;
import kanban.managers.taskManagers.TasksManager;
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

    TasksManager tasksManager = Managers.getDefault();

    Task task1 = tasksManager.createTask(
            new Task("Task1", "Task1_description"));
    Task task2 = tasksManager.createTask(
            new Task("Task2", "Task2_description"));
    Task task3 = tasksManager.createTask(
            new Task("Task3", "Task3_description"));

    Epic epic1 = tasksManager.createEpic(
            new Epic("Epic1", "Epic1_description"));
    Epic epic2 = tasksManager.createEpic(
            new Epic("Epic2", "Epic2_description"));
    Epic epic3 = tasksManager.createEpic(
            new Epic("Epic3", "Epic3_description"));

    Subtask subtask1 = tasksManager.createSubtask(
            new Subtask("Subtask1", "Subtask1_description", epic1.getId()));
    Subtask subtask2 = tasksManager.createSubtask(
            new Subtask("Subtask2", "Subtask2_description", epic1.getId()));
    Subtask subtask3 = tasksManager.createSubtask(
            new Subtask("Subtask3", "Subtask3_description", epic1.getId()));

    @Test // Распечатать все таски
    public void printAllTasks() {

        tasksManager.printAllTasks();
        assertEquals(3, tasksManager.getTasks().size());

    }

    @Test // Распечатать все эпики
    public void printAllEpics() {

        tasksManager.printAllEpics();
        assertEquals(3, tasksManager.getEpics().size());

    }

    @Test // Распечатать все сабтаски
    public void printAllSubtasks() {

        tasksManager.printAllSubtasks();
        assertEquals(3, tasksManager.getSubtasks().size());

    }

    @Test // Проверить getHistory()
    public void getHistory() {

        tasksManager.getTask(task1.getId());
        tasksManager.getTask(task2.getId());
        tasksManager.getTask(task3.getId());
        tasksManager.getSubtask(subtask1.getId());
        tasksManager.getSubtask(subtask2.getId());
        tasksManager.getSubtask(subtask3.getId());
        tasksManager.getEpic(epic1.getId());
        tasksManager.getEpic(epic2.getId());
        tasksManager.getEpic(epic3.getId());

        assertEquals(9, tasksManager.getHistory().size());

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
        tasksManager.updateTask(subtask1);
        assertEquals(TaskState.IN_PROGRESS, subtask1.getTaskState());

        subtask3.setTaskState(TaskState.DONE);
        tasksManager.updateTask(subtask3);
        assertEquals(TaskState.DONE, subtask3.getTaskState());

        subtask1.setTaskState(TaskState.DONE);
        tasksManager.updateSubtask(subtask1);
        assertEquals(TaskState.DONE, subtask1.getTaskState());

        subtask2.setTaskState(TaskState.DONE);
        tasksManager.updateSubtask(subtask2);
        assertEquals(TaskState.DONE, subtask2.getTaskState());

    }

    @Test // проверить удаление таска
    public void removeTask() {

        assertEquals(3, tasksManager.getTasks().size());
        tasksManager.getTask(task1.getId());

        tasksManager.removeTask(task1.getId());

        assertEquals(2, tasksManager.getTasks().size());

    }

    @Test // проверить удаление эпика
    public void removeEpic() {

        assertEquals(3, tasksManager.getEpics().size());
        tasksManager.getEpic(epic1.getId());
        tasksManager.getSubtask(subtask1.getId());
        tasksManager.getSubtask(subtask2.getId());
        tasksManager.getSubtask(subtask3.getId());

        tasksManager.removeEpic(epic1.getId());

        assertEquals(2, tasksManager.getEpics().size());

    }

    @Test // проверить удаление всех тасков, эпиков и сабтасков
    public void removeAllTasksEpicsSubtasks() {

        tasksManager.getTask(task1.getId());
        tasksManager.getTask(task2.getId());
        tasksManager.getTask(task3.getId());
        tasksManager.getSubtask(subtask1.getId());
        tasksManager.getSubtask(subtask2.getId());
        tasksManager.getSubtask(subtask3.getId());
        tasksManager.getEpic(epic1.getId());
        tasksManager.getEpic(epic2.getId());
        tasksManager.getEpic(epic3.getId());

        assertEquals(9, tasksManager.getHistory().size());

        tasksManager.removeAllTasksEpicsSubtasks();

        assertEquals(0, tasksManager.getHistory().size());

    }

}