package kanban.tests;

import kanban.managers.taskManagers.exceptions.ManagerSaveException;
import kanban.managers.taskManagers.FileBackedTasksManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import kanban.managers.Managers;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Oleg Khilko
 */

public class FileBackedTasksManagerTest extends TasksManagerTest<FileBackedTasksManager> {

    private Path filePath = Path.of("src/main/resources/results.csv");

    @BeforeEach
    public void loadInitialConditions() {

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

        assertEquals(Map.of(task1.getId(), task1), manager.getTasks());
        assertEquals(Map.of(epic1.getId(), epic1), manager.getEpics());
        assertEquals(Map.of(subtask1.getId(), subtask1), manager.getSubtasks());
        assertEquals(List.of(task1, epic1, subtask1), manager.getHistory());

    }

    @Test
    public void throwManagerSaveExceptionTest() {

        filePath = Path.of("probablyShouldFinallyFall.exe");

        assertThrows(ManagerSaveException.class, () -> FileBackedTasksManager.load(filePath));

    }

}