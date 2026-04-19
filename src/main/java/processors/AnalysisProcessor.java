package processors;

import mediator.Mediator;
import state.Event;

public class AnalysisProcessor extends Processor {
    @Override
    public void execute(String sourcePath, Mediator mediator) {
        System.out.println("Phase 2: Analysing...");
        mediator.onProcessorSuccess(Event.ANALYZE, "Analysis Complete");
    }
}