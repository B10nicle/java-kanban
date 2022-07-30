import java.util.HashMap;

public class TaskManager {
    private volatile static TaskManager uniqueInstance;
    HashMap<Long, Task> tasks = new HashMap<>();
    HashMap<Long, Epic> epics = new HashMap<>();
    HashMap<Long, Subtask> subtasks = new HashMap<>();
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

    public Task createTask(Task task) {
        status = Status.NEW;
        task.id = ++idGenerator;
        tasks.put(task.id, task);
        return task;
    }

    public Subtask createSubTask(Subtask subtask) {
        status = Status.NEW;
        subtask.id = ++idGenerator;
        subtasks.put(subtask.id, subtask);
        return subtask;
    }

    public Epic createEpic(Epic epic) {
        status = Status.NEW;
        epic.id = ++idGenerator;
        epics.put(epic.id, epic);
        return epic;
    }

    public void addSubtaskToEpic(Epic epic, Subtask subtask) {
        epic.getIDsOfSubtasks().add(subtask.id);
        subtask.setEpicID(epic.getId());

        System.out.println();
        System.out.println(epic);
        System.out.println(subtask);
    }

    public void printAllTasks(HashMap<Long, Task> tasks) {
        for (Long id : tasks.keySet()) {
            Task value = tasks.get(id);
            System.out.println("№" + id + " " + value);
        }
    }

    public void printAllSubtasks(HashMap<Long, Subtask> subtasks) {
        for (Long id : subtasks.keySet()) {
            Subtask value = subtasks.get(id);
            System.out.println("№" + id + " " + value);
        }
    }

    public void printAllEpics(HashMap<Long, Epic> epics) {
        for (Long id : epics.keySet()) {
            Epic value = epics.get(id);
            System.out.println("№" + id + " " + value);
        }
    }

    public void updateTask(Task task) {
        status = Status.IN_PROGRESS;
        long id = task.getId();
        Task currentTask = tasks.get(id);
        if (currentTask == null) {
            return;
        }
        tasks.put(task.getId(), task);
    }

    public void updateSubtask(Subtask subtask) {
        status = Status.IN_PROGRESS;
        long id = subtask.getId();
        Subtask currentSubtask = subtasks.get(id);
        if (currentSubtask == null) {
            return;
        }
        tasks.put(subtask.getId(), subtask);
    }

    public void updateEpic(Epic epic) {
        status = Status.IN_PROGRESS;
        long id = epic.getId();
        Epic currentEpic = epics.get(id);
        if (currentEpic == null) {
            return;
        }
        tasks.put(epic.getId(), epic);
    }
}
