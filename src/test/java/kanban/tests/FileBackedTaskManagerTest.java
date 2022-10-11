package kanban.tests;

import kanban.managers.Managers;
import kanban.managers.taskManagers.fileBackedTasksManager.FileBackedTasksManager;
import kanban.managers.taskManagers.exceptions.ManagerSaveException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Oleg Khilko
 */

public class FileBackedTaskManagerTest extends TasksManagerTest {

    private Path filePath = Path.of("src/main/resources/results.csv");

    @BeforeEach
    public void loadManager() {

        manager = Managers.getDefaultFileBackedManager();

    }

    @Test
    public void loadFromFileTest() {

        var task1 = manager.createTask(newTask());
        var epic1 = manager.createEpic(newEpic());
        var subtask1 = manager.createSubtask(newSubtask(epic1));

        manager.getTask(task1.getId());
        manager.getEpic(epic1.getId());
        manager.getSubtask(subtask1.getId());
        manager = FileBackedTasksManager.load(filePath);

        assertEquals(List.of(task1), manager.getTasks());
        assertEquals(List.of(epic1), manager.getEpics());
        assertEquals(List.of(subtask1), manager.getSubtasks());
        assertEquals(List.of(task1, epic1, subtask1), manager.getHistory());

    }

    @Test
    public void throwManagerSaveExceptionTest() {

        filePath = Path.of("probablyShouldFinallyFall.exe");

        assertThrows(ManagerSaveException.class, () -> FileBackedTasksManager.load(filePath));

    }

}