package mccmd;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class CommandSupplier {

    private final LinkedList<String> commands = new LinkedList<>();

    private CommandSupplier(String[] commands) {
        for(String cmd : commands)
            this.commands.addLast(cmd);
    }

    // Factory ---------------------------------------------------------------------------------------------------------

    public static CommandSupplier fromStrings(String... commands) {
        return new CommandSupplier(commands);
    }

    public static CommandSupplier fromString(String commands) {
        return CommandSupplier.fromStrings(
                Arrays.stream(commands.split("\\r?\\n"))
                    .filter(str -> !str.isBlank())
                    .toArray(String[]::new)
        );
    }

    public static CommandSupplier fromFile(String path) {
        String content = "";
        try {
            content = Files.readString(Path.of(path));
        } catch(Exception e) { e.printStackTrace(); }
        return CommandSupplier.fromString(content);
    }

    // Getters ---------------------------------------------------------------------------------------------------------

    public String next() { return next(true); }
    public String next(boolean consume) {
        if(consume) return commands.pollFirst();
        else return commands.peekFirst();
    }

    public boolean empty() {
        return commands.size() == 0;
    }
}
