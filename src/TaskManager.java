import java.util.HashMap;

public class TaskManager {
    private volatile static TaskManager uniqueInstance;
    HashMap<Long, Task> tasks = new HashMap<>();
    HashMap<Long, Epic> epics = new HashMap<>();
    HashMap<Long, Subtask> subtasks = new HashMap<>();
    HashMap<Long, Task> archive = new HashMap<>();

    protected static long idGenerator;
    Status status;

    private TaskManager() {
    }

    public static TaskManager getInstance() {
        if (uniqueInstance == null) {
            synchronized (TaskManager.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new TaskManager();
                }
            }
        }
        return uniqueInstance;
    }

    //создание таска
    public Task createTask(Task task) {
        status = Status.NEW;
        task.id = ++idGenerator;
        tasks.put(task.id, task);
        return task;
    }

    //создание сабтаска
    public Subtask createSubTask(Subtask subtask) {
        status = Status.NEW;
        subtask.id = ++idGenerator;
        subtasks.put(subtask.id, subtask);
        return subtask;
    }

    //создание эпика
    public Epic createEpic(Epic epic) {
        status = Status.NEW;
        epic.id = ++idGenerator;
        epics.put(epic.id, epic);
        return epic;
    }

    //добавление сабтаска в эпик
    public Epic addSubtaskToEpic(Epic epic, Subtask subtask) {
        status = Status.NEW;
        epic.getIDsOfSubtasks().add(subtask.id);
        subtask.setEpicID(epic.getId());
        return epic;
    }

    //удаление сабтаска из эпика и добавление эпика в архив
    public Epic deleteSubtaskFromEpic(Epic epic, Subtask subtask) {
        deleteSubtask(subtask);
        epic.getIDsOfSubtasks().remove(subtask.id);

        //переключение статуса эпика в DONE если список сабтасков пуст
        if (epic.getIDsOfSubtasks().isEmpty()) {
            status = Status.DONE;
        }

        //удаление эпика из маппы эпиков если все сабтаски выполнены
        if (status == Status.DONE) {
            archive.put(epic.getId(), epic);
            epics.remove(epic.getId(), epic);
        }
        return epic;
    }

    //удаление сабтаска и добавление в архив
    private Subtask deleteSubtask(Subtask subtask) {
        status = Status.DONE;
        archive.put(subtask.getId(), subtask);
        subtasks.remove(subtask.getId(), subtask);
        return subtask;
    }

    //переключение таска в DONE, удаление из маппы и добавление в архив
    public Task deleteTask(Task task) {
        status = Status.DONE;
        archive.put(task.getId(), task);
        tasks.remove(task.getId(), task);
        return task;
    }

    //печать списка всех тасков
    public void printAllTasks(HashMap<Long, Task> tasks) {
        for (Long id : tasks.keySet()) {
            Task value = tasks.get(id);
            System.out.println("№" + id + " " + value);
        }
        if (tasks.isEmpty()) {
            System.out.println("Список тасков пуст.");
        }
    }

    //печать списка всех сабтасков
    public void printAllSubtasks(HashMap<Long, Subtask> subtasks) {
        for (Long id : subtasks.keySet()) {
            Subtask value = subtasks.get(id);
            System.out.println("№" + id + " " + value);
        }
        if (subtasks.isEmpty()) {
            System.out.println("Список сабтасков пуст.");
        }
    }

    //печать списка всех эпиков
    public void printAllEpics(HashMap<Long, Epic> epics) {
        for (Long id : epics.keySet()) {
            Epic value = epics.get(id);
            System.out.println("№" + id + " " + value);
        }
        if (epics.isEmpty()) {
            System.out.println("Список эпиков пуст.");
        }
    }

    //печать архива
    public void printArchive(HashMap<Long, Task> archive) {
        System.out.println("Архив:");
        for (Long id : archive.keySet()) {
            Task value = archive.get(id);
            System.out.println("№" + id + " " + value);
        }
        if (archive.isEmpty()) {
            System.out.println("Архив пуст.");
        }
    }

    //обновление таска
    public void updateTask(Task task) {
        status = Status.IN_PROGRESS;
        long id = task.getId();
        Task currentTask = tasks.get(id);
        if (currentTask == null) {
            return;
        }
        tasks.put(task.getId(), task);
        System.out.println("Таск обновлён");
    }

    //обновление сабтаска
    public void updateSubtask(Subtask subtask) {
        status = Status.IN_PROGRESS;
        long id = subtask.getId();
        Subtask currentSubtask = subtasks.get(id);
        if (currentSubtask == null) {
            return;
        }
        subtasks.put(subtask.getId(), subtask);
        System.out.println("Сабтаск обновлён");
    }

    //обновление эпика
    public void updateEpic(Epic epic) {
        status = Status.IN_PROGRESS;
        long id = epic.getId();
        Epic currentEpic = epics.get(id);
        if (currentEpic == null) {
            return;
        }
        epics.put(epic.getId(), epic);
        System.out.println("Эпик обновлён");
    }
}
