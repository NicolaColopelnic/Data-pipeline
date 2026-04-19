package state;

public enum State {
    START,
    INGESTED,
    ANALYZED,
    VISUALS_PROCESSED,
    AUDIO_TEXT_PROCESSED,
    COMPLIANCE_CHECKED,
    PACKAGED
}