package processors;

import mediator.Mediator;
import state.Event;

public class AnalysisProcessor extends Processor {

    public AnalysisProcessor() {
        super("Phase 2: Analysis");
    }

    @Override
    public void execute(String filePath, Mediator mediator) {
        System.out.println(phaseName + "...");
        System.out.println("  [Intro-Outro detector] - Introduction ending detected at: 00:00:15");
        System.out.println("  [Credits roller] - Credits detected at: 00:06:10. 'Watch Next' flag set.");

        // simulate metadata:
        String[] scenes = {"Establishing Shot (00:00:00)", "Dialogue (00:02:20)", "Image Gallery (00:03:40)", "Interview (00:04:00)", "Ending Scene (00:06:00)"};
        System.out.println("  [Scene indexer] - Scenes detected: ");
        for (String scene : scenes) {
            System.out.println("    - " + scene);
        }
        mediator.onProcessorSuccess(Event.ANALYZE, "Analysis Phase Complete.");
    }
}
