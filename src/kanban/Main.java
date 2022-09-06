package kanban;

import kanban.managers.Managers;
import kanban.managers.taskManagers.TaskManager;
import kanban.tasks.Epic;
import kanban.tasks.Subtask;
import kanban.tasks.Task;
import kanban.tasks.enums.TaskState;

/**
 * @author Oleg Khilko
 */

public class Main {

    public static void main(String[] args) {
        var taskManager = Managers.getDefault();
        testsForSprint5(taskManager);
    }

    private static void testsForSprint4(TaskManager taskManager) {

        var task1 = taskManager.createTask(
                new Task("Отдых", "Отдых"));
        var task2 = taskManager.createTask(
                new Task("Кот", "Кот"));

        var epic1 = taskManager.createEpic(
                new Epic("Эпик1", "Дом"));
        var epic2 = taskManager.createEpic(
                new Epic("Эпик2", "Сын"));

        var subtask1 = taskManager.createSubtask(
                new Subtask("Купить молоко", "для блинчиков", epic1.getId()));
        var subtask2 = taskManager.createSubtask(
                new Subtask("Купить кофе", "черный, молотый", epic1.getId()));
        var subtask3 = taskManager.createSubtask(
                new Subtask("Купить хлеб", "белый", epic2.getId()));

        taskManager.printAllTasks();
        taskManager.printAllEpics();
        taskManager.printAllSubtasks();
        //---------------------------------------------//

        System.out.println("\n|<------getHistory()------>|");
        taskManager.getTask(task1.getId());
        taskManager.getTask(task2.getId());
        taskManager.getSubtask(subtask1.getId());
        taskManager.getSubtask(subtask2.getId());
        taskManager.getSubtask(subtask3.getId());
        taskManager.getEpic(epic1.getId());
        taskManager.getEpic(epic2.getId());

        for (var task : taskManager.getHistory()) {
            System.out.println(task);
        }
        System.out.println("|<------------------------>|\n");

        task1.setTaskState(TaskState.NEW);
        task2.setTaskState(TaskState.NEW);
        epic1.setTaskState(TaskState.NEW);
        epic2.setTaskState(TaskState.NEW);
        subtask1.setTaskState(TaskState.NEW);
        subtask2.setTaskState(TaskState.NEW);
        subtask3.setTaskState(TaskState.NEW);
        //---------------------------------------------//
        System.out.println("Обновления статусов:");
        subtask1.setTaskState(TaskState.IN_PROGRESS);
        taskManager.updateTask(subtask1);

        subtask3.setTaskState(TaskState.DONE);
        taskManager.updateTask(subtask3);
        //---------------------------------------------//
        subtask1.setTaskState(TaskState.DONE);
        taskManager.updateSubtask(subtask1);

        subtask2.setTaskState(TaskState.DONE);
        taskManager.updateSubtask(subtask2);
        //---------------------------------------------//

        taskManager.removeTask(task1.getId());

        taskManager.removeEpic(epic1.getId());

        taskManager.removeAllTasksEpicsSubtasks();

    }

    private static void testsForSprint5(TaskManager taskManager) {

        System.out.println("\nСоздайте две задачи, эпик с тремя подзадачами и эпик без подзадач");
        System.out.println("Выполняю:");

        var epic1 = (new Epic("Эпик1", "Дом"));
        var epic2 = (new Epic("Эпик2", "Сын"));

        epic1 = taskManager.createEpic(epic1);
        epic2 = taskManager.createEpic(epic2);

        var subtask1 = new Subtask("Купить молоко", "для блинчиков", epic1.getId());
        var subtask2 = new Subtask("Купить кофе", "черный, молотый", epic1.getId());
        var subtask3 = new Subtask("Купить хлеб", "белый", epic1.getId());

        subtask1 = taskManager.createSubtask(subtask1);
        subtask2 = taskManager.createSubtask(subtask2);
        subtask3 = taskManager.createSubtask(subtask3);

        System.out.println(epic1);
        System.out.println(epic2);
        System.out.println(subtask1);
        System.out.println(subtask2);
        System.out.println(subtask3);
        System.out.println("Выполнено");

        System.out.println("\nЗапросите созданные задачи несколько раз в разном порядке");
        System.out.println("Выполняю:");
        System.out.println(taskManager.getEpic(epic1.getId()));
        System.out.println(taskManager.getEpic(epic2.getId()));
        System.out.println(taskManager.getSubtask(subtask1.getId()));
        System.out.println(taskManager.getSubtask(subtask2.getId()));
        System.out.println(taskManager.getEpic(epic2.getId()));
        System.out.println(taskManager.getSubtask(subtask1.getId()));
        System.out.println(taskManager.getSubtask(subtask3.getId()));
        System.out.println(taskManager.getEpic(epic1.getId()));
        System.out.println("Выполнено");

        System.out.println("\nПосле каждого запроса выведите историю и убедитесь, что в ней нет повторов");
        System.out.println("Выполняю:");
        System.out.println(taskManager.getEpic(epic1.getId()));
        System.out.println(taskManager.getHistory());
        System.out.println(taskManager.getEpic(epic2.getId()));
        System.out.println(taskManager.getHistory());
        System.out.println(taskManager.getSubtask(subtask1.getId()));
        System.out.println(taskManager.getHistory());
        System.out.println(taskManager.getSubtask(subtask2.getId()));
        System.out.println(taskManager.getHistory());
        System.out.println(taskManager.getEpic(epic2.getId()));
        System.out.println(taskManager.getHistory());
        System.out.println(taskManager.getSubtask(subtask1.getId()));
        System.out.println(taskManager.getHistory());
        System.out.println(taskManager.getSubtask(subtask3.getId()));
        System.out.println(taskManager.getHistory());
        System.out.println(taskManager.getEpic(epic1.getId()));
        System.out.println(taskManager.getHistory());
        System.out.println("Количество записей в истории: " + taskManager.getHistory().size());
        System.out.println("Выполнено");

        System.out.println("\nУдалите задачу, которая есть в истории, и проверьте, что при печати она не будет выводиться");
        System.out.println("Выполняю:");
        taskManager.removeSubtask(subtask2.getId());
        System.out.println("Количество записей в истории: " + taskManager.getHistory().size());
        System.out.println("Выполнено");

        System.out.println("\nУдалите эпик с тремя подзадачами и убедитесь, что из истории удалился как сам эпик, так и все его подзадачи");
        System.out.println("Выполняю:");
        taskManager.removeEpic(epic1.getId());
        System.out.println("Количество записей в истории: " + taskManager.getHistory().size());
        System.out.println(taskManager.getHistory());
        System.out.println("Выполнено");
    }

}