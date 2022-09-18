package kanban.managers.taskManagers.fileBackedTasksManager.exceptions;

/**
 * @author Oleg Khilko
 */

public class ManagerSaveException extends RuntimeException {

    public ManagerSaveException(String message) {
        super(message);
    }

}