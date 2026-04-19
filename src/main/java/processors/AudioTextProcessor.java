package processors;

import mediator.Mediator;
import state.Event;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class AudioTextProcessor extends Processor {

    public AudioTextProcessor() {
        super("Phase 4: Audio & Text Services");
    }

    @Override
    public void execute(String sourcePath, Mediator mediator) {
        System.out.println(phaseName + "...");

        try {
            new File("movie_101/text").mkdirs();
            new File("movie_101/audio").mkdirs();

            String transcript = "00:00:00 - Host: Charles Schulz, the creator of 'Peanuts'\n" +
                    "00:00:16 - Cartoon Character: Relax here Charlie Brown. We will solve this problem together.\n" +
                    "00:01:18 - Narrator: The simple lines of the beloved characters were actually quite complex.";
            Files.writeString(Paths.get("movie_101/text/source_transcript.txt"), transcript);
            System.out.println("  [Speech-to-Text] Generated English transcript.");

            String translation = "00:00:10 - Gazda: Charles Schulz, creatorul desenului 'Peanuts'.\n" +
                    "00:00:16 - Personaj: Relaxeaza-te aici, Charlie Brown. Vom rezolva aceasta problema impreuna.\n" +
                    "00:01:18 - Narator: Liniile simple ale indragitelor personaje sunt de fapt mai complexe.";
            Files.writeString(Paths.get("movie_101/text/ro_translation.txt"), translation);
            System.out.println("  [Translator] Translated transcript to Romanian.");

            String audioPath = "movie_101/audio/ro_dub_synthetic.aac";
            ProcessBuilder pb = new ProcessBuilder(
                    "ffmpeg", "-y", "-f", "lavfi", "-i", "anullsrc=r=44100:cl=stereo", "-t", "3", "-c:a", "aac", audioPath
            );
            pb.start().waitFor();
            System.out.println("  [AI Dubber] Generated synthetic audio track.");

            mediator.onProcessorSuccess(Event.DUB, "Audio/Text Phase Completed.");

        } catch (Exception e) {
            System.err.println("Error: Audio/Text Phase failed: " + e.getMessage());
            System.exit(1);
        }
    }
}