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

        System.out.println("Создание тасков:");
        Task task1 = new Task("Отдых", "Отдых", Status.NEW);
        Task task2 = new Task("Кот", "Кот", Status.NEW);
        Task task3 = new Task("Хлеб", "Хлеб", Status.NEW);
        Task task4 = new Task("Работа", "Работа", Status.NEW);
        Task task5 = new Task("Кружка", "Кружка", Status.NEW);
        taskManager.createTask(task1);
        taskManager.createTask(task2);
        taskManager.createTask(task3);
        taskManager.createTask(task4);
        taskManager.createTask(task5);
        taskManager.printAllTasks();
        System.out.println("Таски созданы и добавлены в маппу.\n");

        //---------------пришло с фронта---------------//
        task1.setStatus(Status.IN_PROGRESS);
        task2.setStatus(Status.IN_PROGRESS);
        task3.setStatus(Status.IN_PROGRESS);
        task4.setStatus(Status.IN_PROGRESS);
        task5.setStatus(Status.IN_PROGRESS);
        //---------------------------------------------//

        System.out.println("Обновление тасков:");
        taskManager.updateTask(task1);
        taskManager.updateTask(task2);
        taskManager.updateTask(task3);
        taskManager.updateTask(task4);
        taskManager.updateTask(task5);

        System.out.println("\nУдаление тасков:" + "\nДо:");
        taskManager.printAllTasks();

        //---------------пришло с фронта---------------//
        task1.setStatus(Status.DONE);
        task2.setStatus(Status.DONE);
        task3.setStatus(Status.DONE);
        task4.setStatus(Status.DONE);
        task5.setStatus(Status.DONE);
        //---------------------------------------------//

        taskManager.deleteTask(task1);
        taskManager.deleteTask(task2);
        taskManager.deleteTask(task3);
        taskManager.deleteTask(task4);
        taskManager.deleteTask(task5);
        System.out.println("После:");
        taskManager.printAllTasks();

        //---------------------------------------------//

        System.out.println("\nСоздание сабтасков:");
        Subtask subtask1 = new Subtask("Купить молоко", "для блинчиков", Status.NEW);
        Subtask subtask2 = new Subtask("Купить кофе", "черный, молотый", Status.NEW);
        Subtask subtask3 = new Subtask("Купить хлеб", "белый", Status.NEW);
        Subtask subtask4 = new Subtask("Купить мясо", "отбивные", Status.NEW);
        Subtask subtask5 = new Subtask("Купить колу", "холодную", Status.NEW);
        taskManager.createSubTask(subtask1);
        taskManager.createSubTask(subtask2);
        taskManager.createSubTask(subtask3);
        taskManager.createSubTask(subtask4);
        taskManager.createSubTask(subtask5);
        taskManager.printAllSubtasks();
        System.out.println("Сабтаски созданы и добавлены в маппу.\n");

        //---------------пришло с фронта---------------//
        subtask1.setStatus(Status.IN_PROGRESS);
        subtask2.setStatus(Status.IN_PROGRESS);
        subtask3.setStatus(Status.IN_PROGRESS);
        subtask4.setStatus(Status.IN_PROGRESS);
        subtask5.setStatus(Status.IN_PROGRESS);
        //---------------------------------------------//

        System.out.println("Обновление сабтасков:");
        taskManager.updateSubtask(subtask1);
        taskManager.updateSubtask(subtask2);
        taskManager.updateSubtask(subtask3);
        taskManager.updateSubtask(subtask4);
        taskManager.updateSubtask(subtask5);

        System.out.println("\nУдаление сабтасков:" + "\nДо:");
        taskManager.printAllSubtasks();

        //---------------пришло с фронта---------------//
        subtask1.setStatus(Status.DONE);
        subtask2.setStatus(Status.DONE);
        subtask3.setStatus(Status.DONE);
        subtask4.setStatus(Status.DONE);
        subtask5.setStatus(Status.DONE);
        //---------------------------------------------//

        taskManager.deleteSubtask(subtask1);
        taskManager.deleteSubtask(subtask2);
        taskManager.deleteSubtask(subtask3);
        taskManager.deleteSubtask(subtask4);
        taskManager.deleteSubtask(subtask5);
        System.out.println("После:");
        taskManager.printAllSubtasks();

        //---------------------------------------------//

        System.out.println("\nСоздание эпиков:");
        Epic epic1 = new Epic("Эпик1", "Дом", Status.NEW);
        Epic epic2 = new Epic("Эпик2", "Сын", Status.NEW);
        Epic epic3 = new Epic("Эпик3", "Дерево", Status.NEW);
        Epic epic4 = new Epic("Эпик4", "Гроза", Status.NEW);
        Epic epic5 = new Epic("Эпик5", "Музыка", Status.NEW);
        taskManager.createEpic(epic1);
        taskManager.createEpic(epic2);
        taskManager.createEpic(epic3);
        taskManager.createEpic(epic4);
        taskManager.createEpic(epic5);
        taskManager.printAllEpics();
        System.out.println("Эпики созданы и добавлены в маппу.\n");

        //---------------пришло с фронта---------------//
        epic1.setStatus(Status.IN_PROGRESS);
        epic2.setStatus(Status.IN_PROGRESS);
        epic3.setStatus(Status.IN_PROGRESS);
        epic4.setStatus(Status.IN_PROGRESS);
        epic5.setStatus(Status.IN_PROGRESS);
        //---------------------------------------------//

        System.out.println("Обновление эпиков:");
        taskManager.updateEpic(epic1);
        taskManager.updateEpic(epic2);
        taskManager.updateEpic(epic3);
        taskManager.updateEpic(epic4);
        taskManager.updateEpic(epic5);

        System.out.println("\nДо добавления сабтасков в эпик:");
        taskManager.printAllEpics();
        taskManager.addSubtaskToEpic(epic1, subtask1);
        taskManager.addSubtaskToEpic(epic1, subtask2);
        taskManager.addSubtaskToEpic(epic2, subtask3);
        taskManager.addSubtaskToEpic(epic3, subtask4);
        taskManager.addSubtaskToEpic(epic4, subtask5);
        System.out.println("\nПосле добавления сабтасков в эпик:");
        taskManager.printAllEpics();

        System.out.println("\nУдаление сабтасков из эпиков:" + "\nДо:");
        taskManager.printAllEpics();

        //---------------пришло с фронта---------------//
        epic1.setStatus(Status.DONE);
        epic2.setStatus(Status.DONE);
        epic3.setStatus(Status.DONE);
        epic4.setStatus(Status.DONE);
        epic5.setStatus(Status.DONE);
        //---------------------------------------------//

        taskManager.deleteSubtaskFromEpic(epic1, subtask1);
        taskManager.deleteSubtaskFromEpic(epic1, subtask2);
        taskManager.deleteSubtaskFromEpic(epic2, subtask3);
        taskManager.deleteSubtaskFromEpic(epic3, subtask4);
        taskManager.deleteSubtaskFromEpic(epic4, subtask5);
        taskManager.deleteSubtaskFromEpic(epic5);
        System.out.println("После удаления сабтасков из эпиков");
        taskManager.printAllEpics();

        //---------------------------------------------//

        System.out.println("\nИстория просмотров: ");
        Managers.getDefaultHistory().getHistory();
    }
}
