
public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = TaskManager.getInstance();

        System.out.println("Создание тасков:");
        Task task1 = new Task("Отдых", "Отдых");
        Task task2 = new Task("Кот", "Кот");
        taskManager.createTask(task1);
        taskManager.createTask(task2);
        taskManager.printAllTasks(taskManager.tasks);
        System.out.println("Таски созданы и добавлены в маппу.\n");

        System.out.println("Обновление тасков:");
        taskManager.updateTask(task1);

        System.out.println("\nУдаление тасков:");
        System.out.println("До:");
        taskManager.printAllTasks(taskManager.tasks);
        taskManager.deleteTask(task1);
        taskManager.deleteTask(task2);
        System.out.println("После:");
        taskManager.printAllTasks(taskManager.tasks);
        taskManager.printArchive(taskManager.archive);

        System.out.println("\nСоздание сабтасков:");
        Subtask subtask1 = new Subtask("Купить молоко", "для блинчиков");
        Subtask subtask2 = new Subtask("Купить кофе", "черный, молотый");
        taskManager.createSubTask(subtask1);
        taskManager.createSubTask(subtask2);
        taskManager.printAllSubtasks(taskManager.subtasks);
        System.out.println("Сабтаски созданы и добавлены в маппу.\n");

        System.out.println("Обновление сабтасков:");
        taskManager.updateSubtask(subtask1);

        System.out.println("\nСоздание эпиков:");
        Epic epic1 = new Epic("Дом", "Дом");
        taskManager.createEpic(epic1);
        taskManager.printAllEpics(taskManager.epics);
        System.out.println("Эпики созданы и добавлены в маппу.\n");

        System.out.println("Обновление эпиков:");
        taskManager.updateEpic(epic1);

        System.out.println("До добавления сабтасков в эпик:");
        taskManager.printAllEpics(taskManager.epics);
        taskManager.addSubtaskToEpic(epic1, subtask1);
        taskManager.addSubtaskToEpic(epic1, subtask2);
        System.out.println("После добавления сабтасков в эпик:");
        taskManager.printAllEpics(taskManager.epics);

        System.out.println("\nДо удаления сабтасков из эпика:");
        taskManager.printAllEpics(taskManager.epics);
        taskManager.deleteSubtaskFromEpic(epic1, subtask1);
        taskManager.deleteSubtaskFromEpic(epic1, subtask2);
        System.out.println("После удаления сабтасков из эпика:");
        taskManager.printArchive(taskManager.archive);

        System.out.println("\nTotal info:");
        taskManager.printAllTasks(taskManager.tasks);
        taskManager.printAllEpics(taskManager.epics);
        taskManager.printAllSubtasks(taskManager.subtasks);
        System.out.println();
        taskManager.printArchive(taskManager.archive);
    }
}
