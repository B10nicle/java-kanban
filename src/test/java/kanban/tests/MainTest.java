package kanban.tests;

import org.junit.jupiter.api.Test;
import kanban.Main;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Oleg Khilko
 */

public class MainTest {

    @Test
    public void mainTest() throws IOException {

        System.out.println("main");
        String[] args = null;
        final InputStream original = System.in;
        final FileInputStream fips = new FileInputStream("README.md");
        System.setIn(fips);
        Main.main(args);
        System.setIn(original);

    }

}