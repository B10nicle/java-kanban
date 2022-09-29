/*
import kanban.managers.Managers;
import kanban.managers.taskManagers.TasksManager;
import kanban.tasks.Epic;
import kanban.tasks.Subtask;
import kanban.tasks.enums.TaskState;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

*/
/**
 * @author Oleg Khilko
 *//*


public class EpicTaskStateTest extends Assertions {

    static TasksManager manager = Managers.getDefault();

    @Test // 1a
    public void checkEpicTaskStateIfListOfSubtasksIsEmpty() {

        Epic epic1 = manager.createEpic(
                new Epic("Epic1", "Epic1_description"));

        if (epic1.getSubtasks().isEmpty())
            assertEquals(TaskState.NEW, epic1.getTaskState());

    }

    @Test // 1b
    public void checkEpicTaskStateIfAllTasksInListOfSubtasksAreNew() {

        Epic epic1 = manager.createEpic(
                new Epic("Epic1", "Epic1_description"));

        Subtask subtask1 = manager.createSubtask(
                new Subtask("Subtask1", "Subtask1_description", epic1.getId()));
        Subtask subtask2 = manager.createSubtask(
                new Subtask("Subtask2", "Subtask2_description", epic1.getId()));

        assertEquals(TaskState.NEW, epic1.getTaskState());

    }

    @Test // 1c
    public void checkEpicTaskStateIfAllTasksInListOfSubtasksAreDone() {

        Epic epic1 = manager.createEpic(
                new Epic("Epic1", "Epic1_description"));

        Subtask subtask1 = manager.createSubtask(
                new Subtask("Subtask1", "Subtask1_description", epic1.getId()));
        Subtask subtask2 = manager.createSubtask(
                new Subtask("Subtask2", "Subtask2_description", epic1.getId()));

        subtask1.setTaskState(TaskState.DONE);
        manager.update(subtask1);

        subtask2.setTaskState(TaskState.DONE);
        manager.update(subtask2);

        assertEquals(TaskState.DONE, epic1.getTaskState());

    }

    @Test // 1d
    public void checkEpicTaskStateIfNotAllTasksInListOfSubtasksAreDone() {

        Epic epic1 = manager.createEpic(
                new Epic("Epic1", "Epic1_description"));

        Subtask subtask1 = manager.createSubtask(
                new Subtask("Subtask1", "Subtask1_description", epic1.getId()));
        Subtask subtask2 = manager.createSubtask(
                new Subtask("Subtask2", "Subtask2_description", epic1.getId()));

        subtask2.setTaskState(TaskState.DONE);
        manager.update(subtask2);

        assertEquals(TaskState.IN_PROGRESS, epic1.getTaskState());

    }

    @Test // 1e
    public void checkEpicTaskStateIfAllTasksInListOfSubtasksAreInProgress() {

        Epic epic1 = manager.createEpic(
                new Epic("Epic1", "Epic1_description"));

        Subtask subtask1 = manager.createSubtask(
                new Subtask("Subtask1", "Subtask1_description", epic1.getId()));
        Subtask subtask2 = manager.createSubtask(
                new Subtask("Subtask2", "Subtask2_description", epic1.getId()));

        subtask1.setTaskState(TaskState.IN_PROGRESS);
        manager.update(subtask1);

        subtask2.setTaskState(TaskState.IN_PROGRESS);
        manager.update(subtask2);

        assertEquals(TaskState.IN_PROGRESS, epic1.getTaskState());

    }

}*/
