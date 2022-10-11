package kanban.servers;

import kanban.managers.taskManagers.TasksManager;
import kanban.servers.handlers.HomepageHandler;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import kanban.tasks.Epic;
import kanban.tasks.Task;
import kanban.utils.Formatter;
import com.google.gson.Gson;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.io.OutputStream;
import java.io.IOException;
import java.util.regex.Pattern;

/**
 * @author Oleg Khilko
 */

public class HttpTaskServer {

    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    private static final int PORT = 8080;
    private final TasksManager manager;
    private final Gson gson;

    public HttpTaskServer(TasksManager manager) throws IOException {

        this.manager = manager;

        var httpServer = HttpServer.create();

        httpServer.bind(new InetSocketAddress(PORT), 0);

        httpServer.createContext("/tasks", new TasksHandler());
        httpServer.createContext("/", new HomepageHandler());

        gson = Formatter.createGson();

        httpServer.start();
        System.out.println("HTTP-сервер запущен на " + PORT + " порту!");

    }

    class TasksHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange exchange) throws IOException {

            System.out.println("Началась обработка /tasks запроса от клиента.");

            var path = exchange.getRequestURI().getPath();

            var method = exchange.getRequestMethod();

            byte[] response = new byte[0];

            int responseCode = 404;

            try {

                switch (method) {

                    case "GET":

                        if (Pattern.matches("^/tasks$", path)) {

                            var prioritizedTasksToJson = gson.toJson(manager.getPrioritizedTasks());

                            if (!prioritizedTasksToJson.isEmpty()) {

                                response = prioritizedTasksToJson.getBytes(DEFAULT_CHARSET);

                                exchange.getResponseHeaders().add("Content-Type", "application/json");

                                responseCode = 200;

                            } else {

                                responseCode = 400;

                            }
                        }

                        if (Pattern.matches("^/tasks/task$", path)) {

                            var tasksToJson = gson.toJson(manager.getTasksByID());

                            if (!tasksToJson.isEmpty()) {

                                response = tasksToJson.getBytes(DEFAULT_CHARSET);

                                exchange.getResponseHeaders().add("Content-Type", "application/json");

                                responseCode = 200;

                            } else {

                                responseCode = 400;

                            }
                        }

                        if (Pattern.matches("^/tasks/epic$", path)) {

                            var epicsToJson = gson.toJson(manager.getEpicsByID());

                            if (!epicsToJson.isEmpty()) {

                                response = epicsToJson.getBytes(DEFAULT_CHARSET);

                                exchange.getResponseHeaders().add("Content-Type", "application/json");

                                responseCode = 200;

                            } else {

                                responseCode = 400;

                            }
                        }

                        if (Pattern.matches("^/tasks/subtask$", path)) {

                            var subtasksToJson = gson.toJson(manager.getSubtasksByID());

                            if (!subtasksToJson.isEmpty()) {

                                response = subtasksToJson.getBytes(DEFAULT_CHARSET);

                                exchange.getResponseHeaders().add("Content-Type", "application/json");

                                responseCode = 200;

                            } else {

                                responseCode = 400;

                            }
                        }

                        if (Pattern.matches("^/tasks/task/\\d$", path)) {

                            int id = Integer.parseInt(path.replaceFirst("/tasks/task/", ""));

                            var taskByIDToJson = gson.toJson(manager.getTasks().get(id - 1));

                            if (!taskByIDToJson.isEmpty()) {

                                response = taskByIDToJson.getBytes(DEFAULT_CHARSET);

                                exchange.getResponseHeaders().add("Content-Type", "application/json");

                                responseCode = 200;

                            } else {

                                responseCode = 400;

                            }
                        }

                        if (Pattern.matches("^/tasks/history$", path)) {

                            var historyToJson = gson.toJson(manager.getHistory());

                            if (!historyToJson.isEmpty()) {

                                response = historyToJson.getBytes(DEFAULT_CHARSET);

                                exchange.getResponseHeaders().add("Content-Type", "application/json");

                                responseCode = 200;

                            } else {

                                responseCode = 400;

                            }
                        }

                        if (Pattern.matches("^/tasks/subtask/epic/\\d+$", path)) {

                            int id = Integer.parseInt(path.replaceFirst("/tasks/subtask/epic/", ""));

                            var subtaskEpicToJson = gson.toJson(manager.getSubtasks().get(id));

                            if (!subtaskEpicToJson.isEmpty()) {

                                response = subtaskEpicToJson.getBytes(DEFAULT_CHARSET);

                                exchange.getResponseHeaders().add("Content-Type", "application/json");

                                responseCode = 200;

                            } else {

                                responseCode = 400;

                            }
                        }

                        break;

                    case "POST":

                        if (Pattern.matches("^/tasks/task$", path)) {

                            var inputStream = exchange.getRequestBody();
                            var body = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);
                            var task = gson.fromJson(body, Task.class);

                            responseCode = 200;

                            if (!manager.getTasksByID().containsKey(task.getId())) {

                                manager.createTask(task);
                                System.out.println("Задача #" + task.getId() + " создана.\n");
                                System.out.println(body);

                            } else {

                                manager.update(task);
                                System.out.println("Задача #" + task.getId() + " обновлена.\n");
                                System.out.println(body);

                            }

                        }

                        if (Pattern.matches("^/tasks/epic$", path)) {

                            var inputStream = exchange.getRequestBody();
                            var body = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);
                            var epic = gson.fromJson(body, Epic.class);

                            responseCode = 200;

                            if (!manager.getEpicsByID().containsKey(epic.getId())) {

                                manager.createEpic(epic);
                                System.out.println("Задача #" + epic.getId() + " создана.\n");
                                System.out.println(body);

                            } else {

                                manager.update(epic);
                                System.out.println("Задача #" + epic.getId() + " обновлена.\n");
                                System.out.println(body);

                            }

                        }
                        if (Pattern.matches("^/tasks/subtask$", path)) {
                            InputStream inputStream = httpExchange.getRequestBody();
                            String body = new String(inputStream.readAllBytes(), CHARSET_BY_DEFAULT);
                            SubTask subTask = gson.fromJson(body, SubTask.class);
                            httpExchange.sendResponseHeaders(200, 0);
                            if (!taskManager.getSubTasksPerId().containsKey(subTask.getId())) {
                                taskManager.addNewSubTask(subTask, taskManager.getEpic(subTask.getEpicId()).getSubTaskIds());
                                System.out.printf("Задача %d создана!\n", subTask.getId());
                                System.out.println(body);
                            } else {
                                taskManager.updateTask(subTask, subTask.getId());
                                System.out.printf("Задача %d обновлена!\n", subTask.getId());
                                System.out.println(body);
                            }

                        }
                        break;


                    case "DELETE":


                    default:

                        System.out.println("Метод " + method + " не поддерживается.");

                }

                exchange.sendResponseHeaders(responseCode, 0);

                try (OutputStream os = exchange.getResponseBody()) {

                    os.write(response);

                }

            } catch (IOException e) {

                System.out.println("Ошибка выполнения запроса: " + e.getMessage());

            } finally {

                exchange.close();

            }

        }

    }

}