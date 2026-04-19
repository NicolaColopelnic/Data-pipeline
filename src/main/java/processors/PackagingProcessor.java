package processors;

import mediator.Mediator;
import state.Event;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;

public class PackagingProcessor extends Processor {

    public PackagingProcessor() {
        super("Phase 6: Packaging");
    }

    @Override
    public void execute(String sourcePath, Mediator mediator) {
        System.out.println(phaseName + "...");

        try {
            System.out.println("  [DRM Wrapper] Encrypting streamable assets...");
            System.out.println("     Encryption applied to all video variants.");

            String manifestJson = createManifestContent();
            Files.writeString(Paths.get("movie_101/manifest.json"), manifestJson);
            System.out.println("  [Manifest Builder] Produced final manifest.json.");

            mediator.onProcessorSuccess(Event.FINALIZE, "Packaging Phase Completed.");

        } catch (Exception e) {
            System.err.println("Error: Packaging phase failed: " + e.getMessage());
            System.exit(1);
        }
    }

    private String createManifestContent() {
        return "{\n" +
                "  \"timestamp\": \"" + LocalDateTime.now() + "\",\n" +
                "  \"asset_id\": \"movie_101\",\n" +
                "  \"video_codecs\": [\"h264\", \"vp9\", \"hevc\"],\n" +
                "  \"resolutions\": [\"4k\", \"1080p\", \"720p\"],\n" +
                "  \"audio\": {\n" +
                "    \"master\": \"en_original\",\n" +
                "    \"dub\": \"ro_dub_synthetic\"\n" +
                "  },\n" +
                "  \"security\": \"DRM_ENCRYPTED\",\n" +
                "  \"compliance\": \"PASSED\"\n" +
                "}";
    }
}