/*
import kanban.Main;
import kanban.managers.Managers;
import kanban.managers.taskManagers.TasksManager;
import kanban.tasks.Epic;
import kanban.tasks.Subtask;
import kanban.tasks.Task;
import kanban.tasks.enums.TaskState;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

*/
/**
 * @author Oleg Khilko
 *//*


public class InMemoryTasksManagerTest extends Assertions {

    @Test
    public void testMain() throws IOException {
        System.out.println("main");
        String[] args = null;
        final InputStream original = System.in;
        final FileInputStream fips = new FileInputStream("results.csv");
        System.setIn(fips);
        Main.main(args);
        System.setIn(original);
    }

    TasksManager manager = Managers.getDefault();

    Task task1 = manager.createTask(
            new Task("Task1", "Task1_description"));
    Task task2 = manager.createTask(
            new Task("Task2", "Task2_description"));
    Task task3 = manager.createTask(
            new Task("Task3", "Task3_description"));

    Epic epic1 = manager.createEpic(
            new Epic("Epic1", "Epic1_description"));
    Epic epic2 = manager.createEpic(
            new Epic("Epic2", "Epic2_description"));
    Epic epic3 = manager.createEpic(
            new Epic("Epic3", "Epic3_description"));

    Subtask subtask1 = manager.createSubtask(
            new Subtask("Subtask1", "Subtask1_description", epic1.getId()));
    Subtask subtask2 = manager.createSubtask(
            new Subtask("Subtask2", "Subtask2_description", epic1.getId()));
    Subtask subtask3 = manager.createSubtask(
            new Subtask("Subtask3", "Subtask3_description", epic1.getId()));

    @Test // Распечатать все таски
    public void printAllTasks() {

        manager.printAllTasks();
        assertEquals(3, manager.getTasks().size());

    }

    @Test // Распечатать все эпики
    public void printAllEpics() {

        manager.printAllEpics();
        assertEquals(3, manager.getEpics().size());

    }

    @Test // Распечатать все сабтаски
    public void printAllSubtasks() {

        manager.printAllSubtasks();
        assertEquals(3, manager.getSubtasks().size());

    }

    @Test // Проверить getHistory()
    public void getHistory() {

        manager.getTask(task1.getId());
        manager.getTask(task2.getId());
        manager.getTask(task3.getId());
        manager.getSubtask(subtask1.getId());
        manager.getSubtask(subtask2.getId());
        manager.getSubtask(subtask3.getId());
        manager.getEpic(epic1.getId());
        manager.getEpic(epic2.getId());
        manager.getEpic(epic3.getId());

        assertEquals(9, manager.getHistory().size());

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
        manager.update(subtask1);
        assertEquals(TaskState.IN_PROGRESS, subtask1.getTaskState());

        subtask3.setTaskState(TaskState.DONE);
        manager.update(subtask3);
        assertEquals(TaskState.DONE, subtask3.getTaskState());

        subtask1.setTaskState(TaskState.DONE);
        manager.update(subtask1);
        assertEquals(TaskState.DONE, subtask1.getTaskState());

        subtask2.setTaskState(TaskState.DONE);
        manager.update(subtask2);
        assertEquals(TaskState.DONE, subtask2.getTaskState());

    }

    @Test // проверить удаление таска
    public void removeTask() {

        assertEquals(3, manager.getTasks().size());
        manager.getTask(task1.getId());

        manager.removeTask(task1.getId());

        assertEquals(2, manager.getTasks().size());

    }

    @Test // проверить удаление эпика
    public void removeEpic() {

        assertEquals(3, manager.getEpics().size());
        manager.getEpic(epic1.getId());
        manager.getSubtask(subtask1.getId());
        manager.getSubtask(subtask2.getId());
        manager.getSubtask(subtask3.getId());

        manager.removeEpic(epic1.getId());

        assertEquals(2, manager.getEpics().size());

    }

    @Test // проверить удаление всех тасков, эпиков и сабтасков
    public void removeAllTasksEpicsSubtasks() {

        manager.getTask(task1.getId());
        manager.getTask(task2.getId());
        manager.getTask(task3.getId());
        manager.getSubtask(subtask1.getId());
        manager.getSubtask(subtask2.getId());
        manager.getSubtask(subtask3.getId());
        manager.getEpic(epic1.getId());
        manager.getEpic(epic2.getId());
        manager.getEpic(epic3.getId());

        assertEquals(9, manager.getHistory().size());

        manager.removeAllTasksEpicsSubtasks();

        assertEquals(0, manager.getHistory().size());

    }

}*/
