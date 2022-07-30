
public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = TaskManager.getInstance();

        Task task1 = new Task("Отдых", "Отдых");
        Task task2 = new Task("Кот", "Кот");

        taskManager.createTask(task1);
        taskManager.createTask(task2);

        taskManager.printAllTasks(taskManager.tasks);

        taskManager.updateTask(task1);

        taskManager.printAllTasks(taskManager.tasks);

        System.out.println();

        Subtask subtask1 = new Subtask("Купить молоко", "для блинчиков");
        Subtask subtask2 = new Subtask("Купить кофе", "черный, молотый");

        taskManager.createSubTask(subtask1);
        taskManager.createSubTask(subtask2);

        taskManager.printAllSubtasks(taskManager.subtasks);
        taskManager.updateSubtask(subtask1);
        taskManager.printAllSubtasks(taskManager.subtasks);

        System.out.println();

        Epic epic1 = new Epic("Дом", "Дом");
        Epic epic2 = new Epic("Работа", "Работа");

        taskManager.createEpic(epic1);
        taskManager.createEpic(epic2);

        taskManager.printAllEpics(taskManager.epics);
        taskManager.updateEpic(epic1);
        taskManager.printAllEpics(taskManager.epics);

        taskManager.addSubtaskToEpic(epic1, subtask1);
        taskManager.addSubtaskToEpic(epic1, subtask2);

    }
}
