package processors;

import mediator.Mediator;
import state.Event;

public class VisualsProcessor extends Processor{
    public VisualsProcessor() {
        super("Phase 3: Visuals");
    }
    @Override
    public void execute(String sourcePath, Mediator mediator) {
        System.out.println("Phase 3: Visuals...");
        mediator.onProcessorSuccess(Event.TRANSCODE, "Visuals Phase Complete");
    }
}
