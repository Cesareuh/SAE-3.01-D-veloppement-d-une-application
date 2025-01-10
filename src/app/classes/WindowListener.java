package app.classes;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class WindowListener implements ChangeListener {
    Modele m;
    public WindowListener(Sujet s){
        m = (Modele) s;
    }
    @Override
    public void changed(ObservableValue observableValue, Object o, Object t1) {
        // Ajuster la taille du viewport et du fileExplorer
        m.getViewport().setMinSize(m.getStage().getWidth() * ((double) 3 / 4), m.getStage().getHeight() - m.getMenuBar().getHeight());
        m.getFileExplorerTree().setMinSize(m.getStage().getWidth() * ((double) 1 / 4), m.getStage().getHeight() - m.getMenuBar().getHeight());
    }
}
