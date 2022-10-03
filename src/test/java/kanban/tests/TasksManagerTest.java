package kanban.tests;

import kanban.managers.historyManagers.inMemoryHistoryManager.InMemoryHistoryManager;
import kanban.managers.taskManagers.exceptions.IntersectionException;
import kanban.managers.historyManagers.HistoryManager;
import kanban.managers.taskManagers.TasksManager;
import kanban.managers.taskManagers.inMemoryTasksManager.InMemoryTasksManager;
import kanban.tasks.enums.TaskState;
import kanban.tasks.enums.TaskType;
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
    public void hashCodeTaskTest() {

        var task1 = manager.createTask(newTask());

        assertEquals(List.of(task1).hashCode(), manager.getTasks().hashCode());

    }

    @Test
    public void hashCodeSubtaskTest() {

        var epic1 = manager.createEpic(newEpic());
        var subtask1 = manager.createSubtask(newSubtask(epic1));

        assertEquals(List.of(subtask1).hashCode(), manager.getSubtasks().hashCode());

    }

    @Test
    public void hashCodeEpicTest() {

        var epic1 = manager.createEpic(newEpic());

        assertEquals(List.of(epic1).hashCode(), manager.getEpics().hashCode());

    }

    @Test
    public void getEpicIDTest() {

        var epic1 = manager.createEpic(newEpic());
        var subtask1 = manager.createSubtask(newSubtask(epic1));

        assertEquals(epic1.getId(), subtask1.getEpicID());

    }

    @Test
    public void setEpicEndTimeTest() {

        var epic1 = manager.createEpic(newEpic());

        epic1.setEndTime(Instant.ofEpochSecond(42));

        assertEquals(Instant.ofEpochSecond(42), epic1.getEndTime());

    }

    @Test
    public void getTaskTypeTest() {

        var task1 = manager.createTask(newTask());

        assertEquals(TaskType.TASK, task1.getTaskType());

    }

    @Test
    public void getDurationTest() {

        var task1 = manager.createTask(newTask());

        assertEquals(0, task1.getDuration());

    }

    @Test
    public void setDurationTest() {

        var task1 = manager.createTask(newTask());

        task1.setDuration(42);

        assertEquals(42, task1.getDuration());

    }

    @Test
    public void setStartTimeTest() {

        var task1 = manager.createTask(newTask());

        task1.setStartTime(Instant.ofEpochSecond(42));

        assertEquals(Instant.ofEpochSecond(42), task1.getStartTime());

    }

    @Test
    public void getTaskNameTest() {

        var task1 = manager.createTask(newTask());

        assertEquals("Task1", task1.getName());

    }

    @Test
    public void getTaskDescriptionTest() {

        var task1 = manager.createTask(newTask());

        assertEquals("Task1", task1.getDescription());

    }

    @Test
    public void createNewTaskTest() {

        var task1 = manager.createTask(newTask());
        var tasks = manager.getTasks();

        assertEquals(List.of(task1), tasks);

    }

    @Test
    public void createNewEpicTest() {

        var epic1 = manager.createEpic(newEpic());
        var epics = manager.getEpics();

        assertEquals(List.of(epic1), epics);

    }

    @Test
    public void createNewSubtaskTest() {

        var epic1 = manager.createEpic(newEpic());
        var subtask1 = manager.createSubtask(newSubtask(epic1));
        var subtasks = manager.getSubtasks();

        assertEquals(List.of(subtask1), subtasks);

    }

    @Test
    public void updateTaskStateTest() {

        var task1 = manager.createTask(newTask());

        task1.setTaskState(TaskState.IN_PROGRESS);
        manager.update(task1);

        var updatedTaskState = manager.getTask(task1.getId()).getTaskState();

        assertEquals(TaskState.IN_PROGRESS, updatedTaskState);

    }

    @Test
    public void updateSubtaskStateDoneTest() {

        var epic1 = manager.createEpic(newEpic());
        var subtask1 = manager.createSubtask(newSubtask(epic1));
        subtask1.setTaskState(TaskState.DONE);

        manager.update(subtask1);

        var updatedEpicState = manager.getEpic(epic1.getId()).getTaskState();
        var updatedSubtaskState = manager.getSubtask(subtask1.getId()).getTaskState();

        assertEquals(TaskState.DONE, updatedEpicState);
        assertEquals(TaskState.DONE, updatedSubtaskState);

    }

    @Test
    public void noSubtaskRemoveIfIncorrectIDTest() {

        var epic1 = manager.createEpic(newEpic());
        var subtask1 = manager.createSubtask(newSubtask(epic1));

        manager.removeSubtask(42);

        assertEquals(List.of(subtask1), manager.getSubtasks());

    }

    @Test
    public void updateEpicStateToInProgressTest() {

        var epic1 = manager.createEpic(newEpic());
        var subtask1 = manager.createSubtask(newSubtask(epic1));
        var subtask2 = manager.createSubtask(newSubtask(epic1));

        subtask1.setTaskState(TaskState.IN_PROGRESS);
        manager.update(subtask1);
        subtask2.setTaskState(TaskState.DONE);
        manager.update(subtask2);

        var updatedEpicState = manager.getEpic(epic1.getId()).getTaskState();

        assertEquals(TaskState.IN_PROGRESS, updatedEpicState);

    }

    @Test
    public void updateEpicTest() {

        var epic1 = manager.createEpic(newEpic());

        epic1.setTaskState(TaskState.IN_PROGRESS);
        manager.update(epic1);

        var updatedEpicState = manager.getEpic(epic1.getId()).getTaskState();

        assertEquals(TaskState.IN_PROGRESS, updatedEpicState);

    }

    @Test
    public void removeAllTasksEpicsSubtasksTest() {

        var task1 = manager.createTask(newTask());
        var epic1 = manager.createEpic(newEpic());
        var subtask1 = manager.createSubtask(newSubtask(epic1));

        manager.removeAllTasksEpicsSubtasks();

        assertEquals(emptyList, manager.getTasks());
        assertEquals(emptyList, manager.getEpics());
        assertEquals(emptyList, manager.getSubtasks());

    }

    @Test
    public void removeTaskTest() {

        var task1 = manager.createTask(newTask());

        manager.removeTask(task1.getId());

        assertEquals(emptyList, manager.getTasks());

    }

    @Test
    public void updateSubtaskStateInProgressTest() {

        var epic1 = manager.createEpic(newEpic());
        var subtask1 = manager.createSubtask(newSubtask(epic1));

        subtask1.setTaskState(TaskState.IN_PROGRESS);
        manager.update(subtask1);

        var updatedEpicState = manager.getEpic(epic1.getId()).getTaskState();
        var updatedSubtaskState = manager.getSubtask(subtask1.getId()).getTaskState();

        assertEquals(TaskState.IN_PROGRESS, updatedEpicState);
        assertEquals(TaskState.IN_PROGRESS, updatedSubtaskState);

    }

    @Test
    public void noTaskRemoveIfIncorrectIDTest() {

        var task1 = manager.createTask(newTask());

        manager.removeTask(42);

        assertEquals(List.of(task1), manager.getTasks());

    }

    @Test
    public void removeEpicTest() {

        var epic1 = manager.createEpic(newEpic());
        var subtask1 = manager.createSubtask(newSubtask(epic1));

        manager.removeEpic(epic1.getId());

        assertEquals(emptyList, manager.getEpics());

    }

    @Test
    public void calculateStartAndEndTimeOfEpicTest() {

        var epic1 = manager.createEpic(newEpic());
        var subtask1 = manager.createSubtask(newSubtask(epic1));
        var subtask2 = manager.createSubtask(newSubtask(epic1));

        assertEquals(subtask1.getStartTime(), epic1.getStartTime());
        assertEquals(subtask2.getEndTime(), epic1.getEndTime());

    }

    @Test
    public void noEpicRemoveIfIncorrectIDTest() {

        var epic1 = manager.createEpic(newEpic());
        var subtask1 = manager.createSubtask(newSubtask(epic1));

        manager.removeEpic(42);

        assertEquals(List.of(epic1), manager.getEpics());

    }

    @Test
    public void tasksToStringTest() {

        var epic1 = manager.createEpic(newEpic());
        var subtask1 = manager.createSubtask(newSubtask(epic1));

        assertEquals(epic1 + "\n" + subtask1 + "\n", Formatter.tasksToString(manager));

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

        var task1 = manager.createTask(newTask());
        var epic1 = manager.createEpic(newEpic());
        var subtask1 = manager.createSubtask(newSubtask(epic1));

        manager.getSubtask(subtask1.getId());
        manager.getEpic(epic1.getId());
        manager.getTask(task1.getId());

        assertEquals(List.of(subtask1, epic1, task1), manager.getHistory());

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
        var task1 = manager.createTask(newTask());
        System.setOut(new PrintStream(outContent));
        manager.printAllTasks();
        assertEquals("№1 1,TASK,Task1,NEW,Task1,1970-01-01T00:00:00Z,0,1970-01-01T00:00:00Z\n",
                outContent.toString());
        System.setOut(System.out);

    }

    @Test
    public void printAllTasksIfListIsEmptyTest() {

        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        manager.printAllTasks();
        assertEquals("Список тасков пуст.\n",
                outContent.toString());
        System.setOut(System.out);

    }

    @Test
    public void printAllSubtasksIfListIsEmptyTest() {

        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        manager.printAllSubtasks();
        assertEquals("Список сабтасков пуст.\n",
                outContent.toString());
        System.setOut(System.out);

    }

    @Test
    public void printAllEpicsIfListIsEmptyTest() {

        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        manager.printAllEpics();
        assertEquals("Список эпиков пуст.\n",
                outContent.toString());
        System.setOut(System.out);

    }

    @Test
    public void printAllSubtasksTest() {

        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        var epic1 = manager.createEpic(newEpic());
        var subtask1 = manager.createSubtask(newSubtask(epic1));
        System.setOut(new PrintStream(outContent));
        manager.printAllSubtasks();
        assertEquals("№2 2,SUBTASK,Subtask1,NEW,Subtask1,1970-01-01T00:00:00Z,0,1970-01-01T00:00:00Z,1\n",
                outContent.toString());
        System.setOut(System.out);

    }

    @Test
    public void printAllEpicsTest() {

        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        var epic1 = manager.createEpic(newEpic());
        System.setOut(new PrintStream(outContent));
        manager.printAllEpics();
        assertEquals("№1 1,EPIC,Epic1,NEW,Epic1,1970-01-01T00:00:00Z,0,1970-01-01T00:00:00Z\n",
                outContent.toString());
        System.setOut(System.out);

    }

    @Test
    public void printPrioritizedTasksTest() {

        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        var task1 = manager.createTask(newTask());
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