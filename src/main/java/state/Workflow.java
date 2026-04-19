package state;

public class Workflow {
    public State getNextState(State current, Event event) {
        switch (current) {
            case START:
                if (event == Event.VALIDATE) return State.INGESTED;
                break;

            case INGESTED:
                if (event == Event.ANALYZE) return State.ANALYZED;
                break;

            case ANALYZED:
                if (event == Event.TRANSCODE) return State.VISUALS_PROCESSED;
                break;

            case VISUALS_PROCESSED:
                if (event == Event.DUB) return State.AUDIO_TEXT_PROCESSED;
                break;

            case AUDIO_TEXT_PROCESSED:
                if (event == Event.CHECK_SAFETY) return State.COMPLIANCE_CHECKED;
                break;

            case COMPLIANCE_CHECKED:
                if (event == Event.FINALIZE) return State.PACKAGED;
                break;
        }
        return null;
    }
}