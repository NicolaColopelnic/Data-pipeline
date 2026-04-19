import mediator.Mediator;

public class Main {
    public static void main(String[] args) {

        String filePath = "sample_video.mp4";

        Mediator orchestrator = new Mediator(filePath);

        orchestrator.startPipeline();
    }
}