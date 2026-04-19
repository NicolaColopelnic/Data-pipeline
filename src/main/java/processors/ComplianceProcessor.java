package processors;

import mediator.Mediator;
import state.Event;

public class ComplianceProcessor extends Processor {
    public ComplianceProcessor() {
        super("Phase 5: Compliance");
    }
    @Override
    public void execute(String sourcePath, Mediator mediator) {
        System.out.println("Phase 5: Compliance...");
        mediator.onProcessorSuccess(Event.CHECK_SAFETY, "Compliance Phase Complete");
    }
}
