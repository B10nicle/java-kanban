package kanban.managers.taskManagers.fileBackedTasksManager.exceptions;

/**
 * @author Oleg Khilko
 */

public class NotSupportedTypeException extends RuntimeException {

    public NotSupportedTypeException(String message) {
        super(message);
    }

}