package processors;

import mediator.Mediator;
import state.Event;

public abstract class Processor {
    protected String phaseName;

    public Processor(String phaseName) {
        this.phaseName = phaseName;
    }

    public abstract void execute(String sourcePath, Mediator mediator);

}