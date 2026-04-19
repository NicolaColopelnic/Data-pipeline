package mediator;

import state.*;
import processors.*;

public class Mediator {
    private Workflow workflow = new Workflow();
    private String sourceFilePath;

    private IngestProcessor ingest = new IngestProcessor();
    private AnalysisProcessor analysis = new AnalysisProcessor();
    private VisualsProcessor visuals = new VisualsProcessor();
    private AudioTextProcessor audio = new AudioTextProcessor();
    private ComplianceProcessor compliance = new ComplianceProcessor();
    private PackagingProcessor packaging = new PackagingProcessor();

    public Mediator(String path) {
        this.sourceFilePath = path;
    }

    public void startPipeline() {
        FileState.saveState(State.START);
        handleEvent(Event.VALIDATE);
    }

    public void onProcessorSuccess(Event event, String successMessage) {
        System.out.println(successMessage);

        State currentState = FileState.loadState();
        State nextState = workflow.getNextState(currentState, event);

        if (nextState != null) {
            FileState.saveState(nextState);
            System.out.println("Transition: " + currentState + " -> " + nextState + "\n");
            orchestrateNextStep(nextState);
        }
    }

    private void orchestrateNextStep(State state) {
        switch (state) {
            case INGESTED:
                handleEvent(Event.ANALYZE);
                break;

            case ANALYZED:
                System.out.println("Launching Parallel Tasks: Visuals & Audio/Text...");
                handleEvent(Event.TRANSCODE);
                handleEvent(Event.DUB);
                break;

            case AUDIO_TEXT_PROCESSED:
                handleEvent(Event.CHECK_SAFETY);
                break;

            case COMPLIANCE_CHECKED:
                handleEvent(Event.FINALIZE);
                break;

            case PACKAGED:
                System.out.println("Pipeline successfully finished.");
                break;

            default:
                break;
        }
    }

    private void handleEvent(Event event) {
        switch (event) {
            case VALIDATE -> ingest.execute(sourceFilePath, this);
            case ANALYZE -> analysis.execute(sourceFilePath, this);
            case TRANSCODE -> visuals.execute(sourceFilePath, this);
            case DUB -> audio.execute(sourceFilePath, this);
            case CHECK_SAFETY -> compliance.execute(sourceFilePath, this);
            case FINALIZE -> packaging.execute(sourceFilePath, this);
        }
    }
}