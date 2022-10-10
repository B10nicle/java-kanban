package kanban;

import kanban.servers.KVServer;

import java.io.IOException;

/**
 * @author Oleg Khilko
 */

public class Main {

    public static void main(String[] args) throws IOException {

        new KVServer().start();

    }

}