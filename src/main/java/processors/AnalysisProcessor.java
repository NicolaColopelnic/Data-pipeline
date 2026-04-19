package processors;

import mediator.Mediator;
import state.Event;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class AnalysisProcessor extends Processor {

    public AnalysisProcessor() {
        super("Phase 2: Analysis");
    }

    @Override
    public void execute(String filePath, Mediator mediator) {
        System.out.println(phaseName + "...");

        System.out.println("  [Intro-Outro detector] Introduction ending detected at: 00:00:15");
        System.out.println("  [Credits roller] Credits detected at: 00:06:10. 'Watch Next' flag set.");

        String[] scenes = {"Establishing Shot", "Dialogue", "Image Gallery", "Interview", "Ending Scene"};
        String[] timestamps = {"00:00:00", "00:02:20", "00:03:40", "00:04:00", "00:06:00"};

        System.out.println("  [Scene indexer] Categorizing segments...");

        try {
            File metadataDir = new File("movie_101/metadata");
            if (!metadataDir.exists()) {
                metadataDir.mkdirs();
            }

            StringBuilder jsonBuilder = new StringBuilder();
            jsonBuilder.append("{\n  \"movie_id\": \"movie_101\",\n  \"scenes\": [\n");

            for (int i = 0; i < scenes.length; i++) {
                jsonBuilder.append("    { \"type\": \"").append(scenes[i])
                        .append("\", \"timestamp\": \"").append(timestamps[i]).append("\" }");
                if (i < scenes.length - 1) jsonBuilder.append(",");
                jsonBuilder.append("\n");
            }
            jsonBuilder.append("  ]\n}");

            Files.writeString(Paths.get("movie_101/metadata/scene_analysis.json"), jsonBuilder.toString());

        } catch (Exception e) {
            System.err.println("Error: Failed to create metadata file: " + e.getMessage());
        }

        mediator.onProcessorSuccess(Event.ANALYZE, "Analysis Phase Completed.");
    }
}