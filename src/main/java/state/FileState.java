package state;

import java.io.*;
import java.nio.file.*;

public class FileState {
    private static final String FILE_PATH = "pipeline_state.txt";

    public static void saveState(State state) {
        try {
            Files.writeString(Paths.get(FILE_PATH), state.name());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static State loadState() {
        try {
            String data = Files.readString(Paths.get(FILE_PATH)).trim();
            return State.valueOf(data);
        } catch (Exception e) {
            return State.START;
        }
    }
}