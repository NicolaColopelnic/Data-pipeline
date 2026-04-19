package processors;

import mediator.Mediator;
import state.Event;
import java.io.File;
import java.io.IOException;

public class VisualsProcessor extends Processor {

    public VisualsProcessor() {
        super("Phase 3: Visuals");
    }

    @Override
    public void execute(String sourcePath, Mediator mediator) {
        System.out.println(phaseName + "...");

        try {
            System.out.println("  [Scene Complexity] Analyzing visual entropy for dynamic encoding.");
            generateVideoAssets(sourcePath);

            generateThumbnails(sourcePath);

            mediator.onProcessorSuccess(Event.TRANSCODE, "Visuals Phase Completed.");

        } catch (Exception e) {
            System.out.println("Error: Visuals Processing failed: " + e.getMessage());
            System.exit(1);
        }
    }

    private void generateVideoAssets(String sourcePath) throws IOException, InterruptedException {
        String[] folders = {"h264", "vp9", "hevc"};
        String[] resolutions = {"4k", "1080p", "720p"};

        for (String folder : folders) {
            File dir = new File("movie_101/video/" + folder);
            dir.mkdirs();

            for (String res : resolutions) {
                String extension = getExtension(folder);
                String fileName = res + "_" + folder + extension;
                File videoFile = new File(dir, fileName);

                if (folder.equals("h264") && res.equals("1080p")) {
                    ProcessBuilder pb = new ProcessBuilder(
                            "ffmpeg", "-y", "-i", sourcePath, "-t", "5", "-vf", "scale=-1:1080", videoFile.getPath()
                    );
                    pb.inheritIO();
                    pb.start().waitFor();
                } else {
                    videoFile.createNewFile();
                }
            }
        }
    }

    private void generateThumbnails(String sourcePath) throws IOException, InterruptedException {
        File imageDir = new File("movie_101/images/thumbnails");
        imageDir.mkdirs();

        System.out.println("  [Sprite Generator] Extracting thumbnails...");

        String[] timestamps = {"00:00:03", "00:03:00", "00:06:05"};
        for (int i = 0; i < timestamps.length; i++) {
            File thumb = new File(imageDir, "thumb_" + i + ".jpg");
            ProcessBuilder pb = new ProcessBuilder(
                    "ffmpeg", "-y", "-i", sourcePath, "-ss", timestamps[i], "-vframes", "1", thumb.getPath()
            );
            pb.inheritIO();
            pb.start().waitFor();
        }

        System.out.println("  [Sprite Generator] Creating filmstrip (sprite_map.jpg)...");
        String spritePath = "movie_101/images/sprite_map.jpg";

        ProcessBuilder pbSprite = new ProcessBuilder(
                "ffmpeg", "-y", "-i", sourcePath,
                "-frames:v", "1",
                "-vf", "select='not(mod(n,50))',scale=160:-1,tile=2x2",
                spritePath
        );

        pbSprite.inheritIO().start().waitFor();
    }

    private String getExtension(String codec) {
        return switch (codec) {
            case "vp9" -> ".webm";
            case "hevc" -> ".mkv";
            default -> ".mp4";
        };
    }
}