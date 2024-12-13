package app;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

import java.io.File;

public class ControlButton implements EventHandler {

    Modele m;
    public ControlButton(Modele m){
        this.m = m;
    }



    @Override
	public void handle(Event event) {
		if(((MenuItem)event.getTarget()).getText().equals("Select directory")){
			DirectoryChooser dirChooser = new DirectoryChooser();
			dirChooser.setTitle("Open Directory");
			File selectedFile = dirChooser.showDialog(m.getStage());
			if(selectedFile != null){
				m.initialiserBlocs(selectedFile);
			}
		}
	}
}
