package kanban.tests;

import kanban.managers.taskManagers.inMemoryTasksManager.InMemoryTasksManager;
import org.junit.jupiter.api.BeforeEach;

/**
 * @author Oleg Khilko
 */

public class InMemoryTasksManagerTest extends TasksManagerTest {

    @BeforeEach
    public void loadManager() {

        manager = new InMemoryTasksManager();

    }
}