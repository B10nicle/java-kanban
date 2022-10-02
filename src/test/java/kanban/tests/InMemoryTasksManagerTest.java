package kanban.tests;

import org.junit.jupiter.api.BeforeEach;
import kanban.managers.Managers;

/**
 * @author Oleg Khilko
 */

public class InMemoryTasksManagerTest extends TasksManagerTest {

    @BeforeEach
    public void loadManager() {

        manager = Managers.getDefault();

    }

}