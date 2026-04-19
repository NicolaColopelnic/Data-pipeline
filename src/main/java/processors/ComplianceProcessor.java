package processors;

import mediator.Mediator;
import state.Event;

public class ComplianceProcessor extends Processor {
    @Override
    public void execute(String sourcePath, Mediator mediator) {
        System.out.println("Phase 5: Compliance...");
        mediator.onProcessorSuccess(Event.CHECK_SAFETY, "Compliance Phase Complete");
    }
}
