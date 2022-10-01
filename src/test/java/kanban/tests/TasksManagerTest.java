package kanban.tests;

import kanban.managers.taskManagers.exceptions.IntersectionException;
import kanban.managers.taskManagers.TasksManager;
import kanban.tasks.enums.TaskState;
import kanban.utils.Formatter;
import org.junit.jupiter.api.Test;
import kanban.tasks.Subtask;
import kanban.tasks.Epic;
import kanban.tasks.Task;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Oleg Khilko
 */

public abstract class TasksManagerTest {

    List<Task> emptyList = new ArrayList<>();
    protected TasksManager manager;

    private Task newTask() {

        return new Task("Task1", "Task1",
                Instant.EPOCH, 0);

    }

    private Epic newEpic() {

        return new Epic("Epic1", "Epic1");

    }

    private Subtask newSubtask(Epic epic) {

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
    public void convertTasksToStringTest() {

        var epic = manager.createEpic(newEpic());
        var subtask = manager.createSubtask(newSubtask(epic));

        assertEquals(epic + "\n" + subtask + "\n", Formatter.tasksToString(manager));

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
}