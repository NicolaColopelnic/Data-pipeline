package processors;

import mediator.Mediator;
import state.Event;

public class IngestProcessor extends Processor {
    @Override
    public void execute(String sourcePath, Mediator mediator) {
        System.out.println("Phase 1: Ingesting...");
        mediator.onProcessorSuccess(Event.VALIDATE, "Ingest phase Complete");
    }
}