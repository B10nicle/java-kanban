package kanban;

import kanban.manager.taskmanager.InMemoryTaskManager;
import kanban.manager.Managers;
import kanban.task.Epic;
import kanban.task.Subtask;
import kanban.task.Task;

public class Main {
    public static void main(String[] args) {
        InMemoryTaskManager taskManager = new InMemoryTaskManager();

        System.out.println("Создание тасков:");
        Task task1 = new Task("Отдых", "Отдых");
        Task task2 = new Task("Кот", "Кот");
        Task task3 = new Task("Хлеб", "Хлеб");
        Task task4 = new Task("Работа", "Работа");
        Task task5 = new Task("Кружка", "Кружка");
        taskManager.createTask(task1);
        taskManager.createTask(task2);
        taskManager.createTask(task3);
        taskManager.createTask(task4);
        taskManager.createTask(task5);
        taskManager.printAllTasks(taskManager.getTasks());
        System.out.println("Таски созданы и добавлены в маппу.\n");

        System.out.println("Обновление тасков:");
        taskManager.updateTask(task1);
        taskManager.updateTask(task2);
        taskManager.updateTask(task3);
        taskManager.updateTask(task4);
        taskManager.updateTask(task5);

        System.out.println("\nУдаление тасков:" + "\nДо:");
        taskManager.printAllTasks(taskManager.getTasks());
        taskManager.deleteTask(task1);
        taskManager.deleteTask(task2);
        taskManager.deleteTask(task3);
        taskManager.deleteTask(task4);
        taskManager.deleteTask(task5);
        System.out.println("После:");
        taskManager.printAllTasks(taskManager.getTasks());

        //---------------------------------------------//

        System.out.println("\nСоздание сабтасков:");
        Subtask subtask1 = new Subtask("Купить молоко", "для блинчиков");
        Subtask subtask2 = new Subtask("Купить кофе", "черный, молотый");
        Subtask subtask3 = new Subtask("Купить хлеб", "белый");
        Subtask subtask4 = new Subtask("Купить мясо", "отбивные");
        Subtask subtask5 = new Subtask("Купить колу", "холодную");
        taskManager.createSubTask(subtask1);
        taskManager.createSubTask(subtask2);
        taskManager.createSubTask(subtask3);
        taskManager.createSubTask(subtask4);
        taskManager.createSubTask(subtask5);
        taskManager.printAllSubtasks(taskManager.getSubtasks());
        System.out.println("Сабтаски созданы и добавлены в маппу.\n");

        System.out.println("Обновление сабтасков:");
        taskManager.updateSubtask(subtask1);
        taskManager.updateSubtask(subtask2);
        taskManager.updateSubtask(subtask3);
        taskManager.updateSubtask(subtask4);
        taskManager.updateSubtask(subtask5);

        System.out.println("\nУдаление сабтасков:" + "\nДо:");
        taskManager.printAllSubtasks(taskManager.getSubtasks());
        taskManager.deleteSubtask(subtask1);
        taskManager.deleteSubtask(subtask2);
        taskManager.deleteSubtask(subtask3);
        taskManager.deleteSubtask(subtask4);
        taskManager.deleteSubtask(subtask5);
        System.out.println("После:");
        taskManager.printAllSubtasks(taskManager.getSubtasks());

        //---------------------------------------------//

        System.out.println("\nСоздание эпиков:");
        Epic epic1 = new Epic("Эпик1", "Дом");
        Epic epic2 = new Epic("Эпик2", "Сын");
        Epic epic3 = new Epic("Эпик3", "Дерево");
        Epic epic4 = new Epic("Эпик4", "Гроза");
        Epic epic5 = new Epic("Эпик5", "Музыка");
        taskManager.createEpic(epic1);
        taskManager.createEpic(epic2);
        taskManager.createEpic(epic3);
        taskManager.createEpic(epic4);
        taskManager.createEpic(epic5);
        taskManager.printAllEpics(taskManager.getEpics());
        System.out.println("Эпики созданы и добавлены в маппу.\n");

        System.out.println("Обновление эпиков:");
        taskManager.updateEpic(epic1);
        taskManager.updateEpic(epic2);
        taskManager.updateEpic(epic3);
        taskManager.updateEpic(epic4);
        taskManager.updateEpic(epic5);

        System.out.println("\nДо добавления сабтасков в эпик:");
        taskManager.printAllEpics(taskManager.getEpics());
        taskManager.addSubtaskToEpic(epic1, subtask1);
        taskManager.addSubtaskToEpic(epic1, subtask2);
        taskManager.addSubtaskToEpic(epic2, subtask3);
        taskManager.addSubtaskToEpic(epic3, subtask4);
        taskManager.addSubtaskToEpic(epic4, subtask5);
        System.out.println("\nПосле добавления сабтасков в эпик:");
        taskManager.printAllEpics(taskManager.getEpics());

        System.out.println("\nУдаление сабтасков из эпиков:" + "\nДо:");
        taskManager.printAllEpics(taskManager.getEpics());
        taskManager.deleteSubtaskFromEpic(epic1, subtask1);
        taskManager.deleteSubtaskFromEpic(epic1, subtask2);
        taskManager.deleteSubtaskFromEpic(epic2, subtask3);
        taskManager.deleteSubtaskFromEpic(epic3, subtask4);
        taskManager.deleteSubtaskFromEpic(epic4, subtask5);
        taskManager.deleteSubtaskFromEpic(epic5);
        System.out.println("После удаления сабтасков из эпиков");
        taskManager.printAllEpics(taskManager.getEpics());

        //---------------------------------------------//

        System.out.println("\nИстория просмотров: ");
        for (Task task : taskManager.getHistoryManager()) {
            System.out.println(task);
        }

        System.out.println(Managers.getDefault());
        System.out.println(Managers.getDefaultHistory());
    }
}
