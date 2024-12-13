package app;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.input.ContextMenuEvent;

public class ControlClicDroit implements EventHandler {

    Modele m;
    public ControlClicDroit(Modele m){
        this.m = m;
    }

    @Override
    public void handle(Event event) {
        VueContext vc;
        ContextMenuEvent contextEvent = (ContextMenuEvent)event;
        if(event.getTarget() instanceof VueBloc){
            vc = new VueMenuContextBloc();
        }else{
            vc = new VueMenuContextVide();
        }

        vc.show(m.getStage(), contextEvent.getScreenX(), contextEvent.getScreenY());

    }
}
