package CityData;

import Commands.Command;
import Commands.Show;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Execute {
    protected static void executeCommand(String text) throws IOException {
        String[] parts = text.split(" ", 2);
        String command = parts[0];
        String element = parts.length > 1 ? parts[1] : "";
        Map<String, Command> map = new HashMap<>();
        map.put("show", (new Show()));
    }
}
