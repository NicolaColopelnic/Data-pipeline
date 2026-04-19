package processors;

import mediator.Mediator;
import state.Event;

public class ComplianceProcessor extends Processor {

    public ComplianceProcessor() {
        super("Phase 5: Compliance & Branding");
    }

    @Override
    public void execute(String sourcePath, Mediator mediator) {
        System.out.println(phaseName + "...");

        try {
            System.out.println("  [Safety Scanner] Scanning for restricted content...");
            System.out.println("     All clear. No blurring required.");

            System.out.println("  [Regional Branding] Overlaying studio logos...");
            System.out.println("  [Action] 'Netflix Original' watermark applied.");

            mediator.onProcessorSuccess(Event.CHECK_SAFETY, "Compliance phase completed.");

        } catch (Exception e) {
            System.err.println("  Error: Compliance phase failed: " + e.getMessage());
            System.exit(1);
        }
    }
}