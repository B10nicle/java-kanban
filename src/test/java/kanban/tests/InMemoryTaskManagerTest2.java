package kanban.tests;

import kanban.managers.taskManagers.inMemoryTasksManager.InMemoryTasksManager;
import org.junit.jupiter.api.BeforeEach;

/**
 * @author Oleg Khilko
 */

public class InMemoryTaskManagerTest2 extends TaskManagerTest {

    @BeforeEach
    public void loadingManager() {

        manager = new InMemoryTasksManager();

    }

}