package kanban;

import kanban.manager.Managers;
import kanban.manager.taskmanager.TaskManager;
import kanban.task.Epic;
import kanban.task.Subtask;
import kanban.task.Task;
import kanban.task.enums.Status;

public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = Managers.getDefault();

        Task task1 = taskManager.createTask(new Task("Отдых", "Отдых"));
        Task task2 = taskManager.createTask(new Task("Кот", "Кот"));

        Epic epic1 = taskManager.createEpic(new Epic("Эпик1", "Дом"));
        Epic epic2 = taskManager.createEpic(new Epic("Эпик2", "Сын"));

        Subtask subtask1 = taskManager.createSubtask(new Subtask("Купить молоко", "для блинчиков", epic1.getId()));
        Subtask subtask2 = taskManager.createSubtask(new Subtask("Купить кофе", "черный, молотый", epic1.getId()));
        Subtask subtask3 = taskManager.createSubtask(new Subtask("Купить хлеб", "белый", epic2.getId()));

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

        for (Task task : taskManager.getHistory()) {
            System.out.println(task);
        }
        System.out.println("|<------------------------>|\n");

        task1.setStatus(Status.NEW);
        task2.setStatus(Status.NEW);
        epic1.setStatus(Status.NEW);
        epic2.setStatus(Status.NEW);
        subtask1.setStatus(Status.NEW);
        subtask2.setStatus(Status.NEW);
        subtask3.setStatus(Status.NEW);
        //---------------------------------------------//
        System.out.println("Обновления статусов:");
        subtask1.setStatus(Status.IN_PROGRESS);
        taskManager.updateTask(subtask1);

        subtask3.setStatus(Status.DONE);
        taskManager.updateTask(subtask3);
        //---------------------------------------------//
        subtask1.setStatus(Status.DONE);
        taskManager.updateSubtask(subtask1);

        subtask2.setStatus(Status.DONE);
        taskManager.updateSubtask(subtask2);
        //---------------------------------------------//

        taskManager.deleteTask(task1.getId());

        taskManager.deleteEpic(epic1.getId());

        taskManager.deleteAllTasksEpicsSubtasks();

    }
}
