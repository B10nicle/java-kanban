package kanban;

import kanban.manager.TaskManager;
import kanban.tasks.Epic;
import kanban.tasks.Subtask;
import kanban.tasks.Task;

public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();

        System.out.println("Создание тасков:");
        Task task1 = new Task("Отдых", "Отдых");
        Task task2 = new Task("Кот", "Кот");
        taskManager.createTask(task1);
        taskManager.createTask(task2);
        taskManager.printAllTasks(taskManager.getTasks());
        System.out.println("Таски созданы и добавлены в маппу.\n");

        System.out.println("Обновление тасков:");
        taskManager.updateTask(task1);

        System.out.println("\nУдаление тасков:");
        System.out.println("До:");
        taskManager.printAllTasks(taskManager.getTasks());
        taskManager.deleteTask(task1);
        taskManager.deleteTask(task2);
        System.out.println("После:");
        taskManager.printAllTasks(taskManager.getTasks());
        taskManager.printArchive(taskManager.getArchive());

        System.out.println("\nСоздание сабтасков:");
        Subtask subtask1 = new Subtask("Купить молоко", "для блинчиков");
        Subtask subtask2 = new Subtask("Купить кофе", "черный, молотый");
        taskManager.createSubTask(subtask1);
        taskManager.createSubTask(subtask2);
        taskManager.printAllSubtasks(taskManager.getSubtasks());
        System.out.println("Сабтаски созданы и добавлены в маппу.\n");

        System.out.println("Обновление сабтасков:");
        taskManager.updateSubtask(subtask1);

        System.out.println("\nСоздание эпиков:");
        Epic epic1 = new Epic("Дом", "Дом");
        taskManager.createEpic(epic1);
        taskManager.printAllEpics(taskManager.getEpics());
        System.out.println("Эпики созданы и добавлены в маппу.\n");

        System.out.println("Обновление эпиков:");
        taskManager.updateEpic(epic1);

        System.out.println("До добавления сабтасков в эпик:");
        taskManager.printAllEpics(taskManager.getEpics());
        taskManager.addSubtaskToEpic(epic1, subtask1);
        taskManager.addSubtaskToEpic(epic1, subtask2);
        System.out.println("После добавления сабтасков в эпик:");
        taskManager.printAllEpics(taskManager.getEpics());

        System.out.println("\nДо удаления сабтасков из эпика:");
        taskManager.printAllEpics(taskManager.getEpics());
        taskManager.deleteSubtaskFromEpic(epic1, subtask1);
        taskManager.deleteSubtaskFromEpic(epic1, subtask2);
        System.out.println("После удаления сабтасков из эпика:");
        taskManager.printArchive(taskManager.getArchive());

        System.out.println("\nTotal info:");
        taskManager.printAllTasks(taskManager.getTasks());
        taskManager.printAllEpics(taskManager.getEpics());
        taskManager.printAllSubtasks(taskManager.getSubtasks());
        System.out.println();
        taskManager.printArchive(taskManager.getArchive());
    }
}
