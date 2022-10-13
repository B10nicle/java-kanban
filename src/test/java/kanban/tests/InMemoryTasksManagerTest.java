package kanban.tests;

import kanban.managers.taskManagers.InMemoryTasksManager;
import org.junit.jupiter.api.BeforeEach;
import kanban.managers.Managers;

/**
 * @author Oleg Khilko
 */

public class InMemoryTasksManagerTest extends TasksManagerTest<InMemoryTasksManager> {

    @BeforeEach
    public void loadInitialConditions() {

        manager = new InMemoryTasksManager();

    }

}