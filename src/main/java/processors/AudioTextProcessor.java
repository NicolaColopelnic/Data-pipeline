package processors;

import mediator.Mediator;
import state.Event;

public class AudioTextProcessor extends Processor {

    public AudioTextProcessor() {
        super("Phase 4: Audio & Text");
    }

    @Override
    public void execute(String sourcePath, Mediator mediator) {
        System.out.println("Phase 4: Audio & Text...");
        mediator.onProcessorSuccess(Event.DUB, "Audio/Text Phase Complete");
    }
}
