package processors;

import mediator.Mediator;
import state.Event;

public class PackagingProcessor extends Processor {
    public PackagingProcessor() {
        super("Phase 6: Packaging");
    }
    @Override
    public void execute(String sourcePath, Mediator mediator) {
        System.out.println("Phase 6: Packaging...");
        mediator.onProcessorSuccess(Event.FINALIZE, "Packaging phase Complete");
    }
}
