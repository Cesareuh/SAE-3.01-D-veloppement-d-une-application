package app;

import javafx.event.EventHandler;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

public class ControlDragNDrop implements EventHandler<DragEvent> {
    private TreeView<String> fileExplorer;
    private Pane viewport;
    private Modele m;

    public ControlDragNDrop(Modele modele){
        this.m = modele;
        this.fileExplorer = modele.getFileExplorerTree();
        this.viewport = modele.getViewport();
    }

    @Override
    public void handle(DragEvent dragEvent) {
        switch(dragEvent.getEventType().getName()){
            case "DRAG_OVER":
                if(dragEvent.getGestureSource() != viewport && dragEvent.getDragboard().hasFiles() && dragEvent.getGestureSource() instanceof TreeItem<?>) {
                    dragEvent.acceptTransferModes(TransferMode.COPY);
                }
                dragEvent.consume();
                break;
            case "DRAG_DROPPED":
                if(dragEvent.getDragboard().hasFiles()) {
                    List<File> file = dragEvent.getDragboard().getFiles();
                    System.out.println(file.getFirst().getName());
                }
        }
    }
}
