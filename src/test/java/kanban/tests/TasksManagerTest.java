package kanban.tests;

import kanban.managers.historyManagers.inMemoryHistoryManager.InMemoryHistoryManager;
import kanban.managers.taskManagers.exceptions.IntersectionException;
import kanban.managers.historyManagers.HistoryManager;
import kanban.managers.taskManagers.TasksManager;
import kanban.tasks.enums.TaskState;
import org.junit.jupiter.api.Test;
import kanban.utils.Formatter;
import kanban.tasks.Subtask;
import kanban.tasks.Epic;
import kanban.tasks.Task;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Oleg Khilko
 */

public abstract class TasksManagerTest {

    private final List<Task> emptyList = new ArrayList<>();
    protected TasksManager manager;

    protected Task newTask() {

        return new Task("Task1", "Task1",
                Instant.EPOCH, 0);

    }

    protected Epic newEpic() {

        return new Epic("Epic1", "Epic1");

    }

    protected Subtask newSubtask(Epic epic) {

        return new Subtask("Subtask1", "Subtask1",
                Instant.EPOCH, 0, epic.getId());

    }

    @Test
    public void createNewTaskTest() {

        var task = manager.createTask(newTask());
        var tasks = manager.getTasks();

        assertEquals(List.of(task), tasks);

    }

    @Test
    public void createNewEpicTest() {

        var epic = manager.createEpic(newEpic());
        var epics = manager.getEpics();

        assertEquals(List.of(epic), epics);

    }

    @Test
    public void createNewSubtaskTest() {

        var epic = manager.createEpic(newEpic());
        var subtask = manager.createSubtask(newSubtask(epic));
        var subtasks = manager.getSubtasks();

        assertEquals(List.of(subtask), subtasks);

    }

    @Test
    public void updateTaskStateTest() {

        var task = manager.createTask(newTask());
        task.setTaskState(TaskState.IN_PROGRESS);
        manager.update(task);
        var updatedTaskState = manager.getTask(task.getId()).getTaskState();

        assertEquals(TaskState.IN_PROGRESS, updatedTaskState);

    }

    @Test
    public void updateSubtaskStateDoneTest() {

        var epic = manager.createEpic(newEpic());
        var subtask = manager.createSubtask(newSubtask(epic));
        subtask.setTaskState(TaskState.DONE);
        manager.update(subtask);
        var updatedEpicState = manager.getEpic(epic.getId()).getTaskState();
        var updatedSubtaskState = manager.getSubtask(subtask.getId()).getTaskState();

        assertEquals(TaskState.DONE, updatedEpicState);
        assertEquals(TaskState.DONE, updatedSubtaskState);

    }

    @Test
    public void noSubtaskRemoveIfIncorrectIDTest() {

        var epic = manager.createEpic(newEpic());
        var subtask = manager.createSubtask(newSubtask(epic));

        manager.removeSubtask(42);

        assertEquals(List.of(subtask), manager.getSubtasks());

    }

    @Test
    public void updateEpicStateToInProgressTest() {

        var epic = manager.createEpic(newEpic());
        var subtask1 = manager.createSubtask(newSubtask(epic));
        var subtask2 = manager.createSubtask(newSubtask(epic));
        subtask1.setTaskState(TaskState.IN_PROGRESS);
        manager.update(subtask1);
        subtask2.setTaskState(TaskState.DONE);
        manager.update(subtask2);
        var updatedEpicState = manager.getEpic(epic.getId()).getTaskState();

        assertEquals(TaskState.IN_PROGRESS, updatedEpicState);

    }

    @Test
    public void removeAllTasksEpicsSubtasksTest() {

        var task = manager.createTask(newTask());
        var epic = manager.createEpic(newEpic());
        var subtask = manager.createSubtask(newSubtask(epic));
        manager.removeAllTasksEpicsSubtasks();

        assertEquals(emptyList, manager.getTasks());
        assertEquals(emptyList, manager.getEpics());
        assertEquals(emptyList, manager.getSubtasks());

    }

    @Test
    public void removeTaskTest() {

        var task = manager.createTask(newTask());
        manager.removeTask(task.getId());

        assertEquals(emptyList, manager.getTasks());

    }

    @Test
    public void updateSubtaskStateInProgressTest() {

        var epic = manager.createEpic(newEpic());
        var subtask = manager.createSubtask(newSubtask(epic));
        subtask.setTaskState(TaskState.IN_PROGRESS);
        manager.update(subtask);
        var updatedEpicState = manager.getEpic(epic.getId()).getTaskState();
        var updatedSubtaskState = manager.getSubtask(subtask.getId()).getTaskState();

        assertEquals(TaskState.IN_PROGRESS, updatedEpicState);
        assertEquals(TaskState.IN_PROGRESS, updatedSubtaskState);

    }

    @Test
    public void noTaskRemoveIfIncorrectIDTest() {

        var task = manager.createTask(newTask());
        manager.removeTask(42);

        assertEquals(List.of(task), manager.getTasks());

    }

    @Test
    public void removeEpicTest() {

        var epic = manager.createEpic(newEpic());
        var subtask = manager.createSubtask(newSubtask(epic));
        manager.removeEpic(epic.getId());

        assertEquals(emptyList, manager.getEpics());

    }

    @Test
    public void calculateStartAndEndTimeOfEpicTest() {

        var epic = manager.createEpic(newEpic());
        var subtask1 = manager.createSubtask(newSubtask(epic));
        var subtask2 = manager.createSubtask(newSubtask(epic));

        assertEquals(subtask1.getStartTime(), epic.getStartTime());
        assertEquals(subtask2.getEndTime(), epic.getEndTime());

    }

    @Test
    public void noEpicRemoveIfIncorrectIDTest() {

        var epic = manager.createEpic(newEpic());
        var subtask = manager.createSubtask(newSubtask(epic));
        manager.removeEpic(42);

        assertEquals(List.of(epic), manager.getEpics());

    }

    @Test
    public void tasksToStringTest() {

        var epic = manager.createEpic(newEpic());
        var subtask = manager.createSubtask(newSubtask(epic));

        assertEquals(epic + "\n" + subtask + "\n", Formatter.tasksToString(manager));

    }

    @Test
    public void tasksFromStringTest() {

        var realTask = new Task(1, "Task1", TaskState.NEW,
                "Task1", Instant.EPOCH, 30);

        var taskFromString = Formatter.tasksFromString(
                "1,TASK,Task1,NEW,Task1,1970-01-01T00:00:00Z,30,1970-01-01T00:00:00Z");

        assertEquals(realTask, taskFromString);

    }

    @Test
    public void throwIllegalArgumentExceptionTest() {

        assertThrows(IllegalArgumentException.class, () -> Formatter.tasksFromString(
                "1,MASK,Task1,NEW,Task1,1970-01-01T00:00:00Z,30,1970-01-01T00:00:00Z"));

    }

    @Test
    public void returnEmptyHistoryTest() {

        assertEquals(emptyList, manager.getHistory());

    }

    @Test
    public void returnHistoryWithTasksTest() {

        var task = manager.createTask(newTask());
        var epic = manager.createEpic(newEpic());
        var subtask = manager.createSubtask(newSubtask(epic));
        manager.getSubtask(subtask.getId());
        manager.getEpic(epic.getId());
        manager.getTask(task.getId());

        assertEquals(List.of(subtask, epic, task), manager.getHistory());

    }

    @Test
    public void throwIntersectionExceptionTest() {

        assertThrows(IntersectionException.class, () -> {

            manager.createTask(new Task(
                    "Task1", "Task1",
                    Instant.ofEpochMilli(42), 42));

            manager.createTask(new Task(
                    "Task2", "Task2",
                    Instant.ofEpochMilli(43), 43));

        });

    }

    @Test
    public void printAllTasksTest() {

        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        var task = manager.createTask(newTask());
        System.setOut(new PrintStream(outContent));
        manager.printAllTasks();
        assertEquals("№1 1,TASK,Task1,NEW,Task1,1970-01-01T00:00:00Z,0,1970-01-01T00:00:00Z\n",
                outContent.toString());
        System.setOut(System.out);

    }

    @Test
    public void printAllSubtasksTest() {

        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        var epic = manager.createEpic(newEpic());
        var subtask = manager.createSubtask(newSubtask(epic));
        System.setOut(new PrintStream(outContent));
        manager.printAllSubtasks();
        assertEquals("№2 2,SUBTASK,Subtask1,NEW,Subtask1,1970-01-01T00:00:00Z,0,1970-01-01T00:00:00Z,1\n",
                outContent.toString());
        System.setOut(System.out);

    }

    @Test
    public void printAllEpicsTest() {

        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        var epic = manager.createEpic(newEpic());
        System.setOut(new PrintStream(outContent));
        manager.printAllEpics();
        assertEquals("№1 1,EPIC,Epic1,NEW,Epic1,1970-01-01T00:00:00Z,0,1970-01-01T00:00:00Z\n",
                outContent.toString());
        System.setOut(System.out);

    }

    @Test
    public void printPrioritizedTasksTest() {

        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        var task = manager.createTask(newTask());
        System.setOut(new PrintStream(outContent));
        manager.printPrioritizedTasks();
        assertEquals("СПИСОК ПРИОРИТЕТНЫХ ЗАДАЧ: \n" +
                        "1,TASK,Task1,NEW,Task1,1970-01-01T00:00:00Z,0,1970-01-01T00:00:00Z\n",
                outContent.toString());
        System.setOut(System.out);

    }

    @Test
    public void historyToStringTest() {

        HistoryManager manager = new InMemoryHistoryManager();

        var task1 = new Task(1, "Task1", TaskState.NEW,
                "Task1", Instant.EPOCH, 0);
        var task2 = new Task(2, "Task2", TaskState.NEW,
                "Task2", Instant.EPOCH, 0);
        var task3 = new Task(3, "Task3", TaskState.NEW,
                "Task3", Instant.EPOCH, 0);

        manager.add(task1);
        manager.add(task2);
        manager.add(task3);

        assertEquals(task1.getId() + ","
                        + task2.getId() + ","
                        + task3.getId() + ",",
                Formatter.historyToString(manager));
    }

    @Test
    public void historyFromStringTest() {

        assertEquals(List.of(1, 2), Formatter.historyFromString("1,2"));

    }

}