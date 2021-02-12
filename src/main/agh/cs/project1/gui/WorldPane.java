package agh.cs.project1.gui;

import agh.cs.project1.map.Jungle;
import agh.cs.project1.map.Map;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class WorldPane {

    Pane world;

    Button startButton;
    Button pauseButton;
    Button saveDataButton;

    TextField numberAnimalText;
    TextField numberGrassesText;
    TextArea dominateGenesTextArea;
    TextField averageEnergyText;
    TextField averageLiveText;
    TextField averageChildText;

    TextField numberOfChildrenForChoosen;
    TextField numberOfDescendantsText;
    TextField deathDayText;
    TextField dayText;

    TextArea dominateGenesForChoosedText;

    Jungle jungle;
    private int paneSize;
    private int width;
    private int height;

    private int mapRatioX;
    private int mapRatioY;
    private int mapRatio;

    public WorldPane(Pane world, Button startButton, Button pauseButton, Button saveDataButton, TextField numberOfChildrenForChoosen, TextField numberOfDescendantsText,
                     TextField deathDayText, TextField dayText, TextField numberAnimalText, TextField numberGrassesText,
                     TextArea dominateGenesTextArea, TextField averageEnergyText, TextField averageLiveText,
                     TextField averageChildText, TextArea dominateGenesForChoosedText, Map map, int paneSize, Jungle jungle){
        this.startButton = startButton;
        this.pauseButton = pauseButton;
        this.saveDataButton = saveDataButton;

        this.jungle = jungle;
        this.world = world;
        this.paneSize = paneSize;
        this.width = map.getSize().x;
        this.height = map.getSize().y;

        setResizeMap();
        setMapSize();

        this.numberOfChildrenForChoosen = numberOfChildrenForChoosen;
        this.numberOfDescendantsText = numberOfDescendantsText;
        this.deathDayText = deathDayText;
        this.dayText = dayText;

        this.numberAnimalText = numberAnimalText;
        this.numberGrassesText = numberGrassesText;
        this.dominateGenesTextArea = dominateGenesTextArea;
        this.averageEnergyText = averageEnergyText;
        this.averageLiveText = averageLiveText;
        this.averageChildText = averageChildText;

        this.dominateGenesForChoosedText = dominateGenesForChoosedText;

        setVisibleElements();
    }

    //skalowanie wielkości elementów na mapie wyświetlanej w animacji
    public void setResizeMap(){
        this.mapRatioX = (int) this.paneSize / width;
        this.mapRatioY = (int) this.paneSize / height;
        this.mapRatio = Math.min(this.paneSize / width, this.paneSize / height);
    }

    //ustalanie wymiarów mapy wyświtlanej w animacji
    public void setMapSize(){
        if(width > height){
            int x = mapRatioY * height + mapRatioY;
            this.world.setPrefWidth(mapRatioX * width + mapRatioX + mapRatio);
            this.world.setMaxWidth(mapRatioX * width + mapRatioX + mapRatio);
            this.world.setMinWidth(mapRatioX * width + mapRatioX + mapRatio);
            this.world.setMaxHeight(x * (int) height / width + mapRatio);
            this.world.setPrefHeight(x * (int) height / width + mapRatio);
            this.world.setMinHeight(x * (int) height / width + mapRatio);
        } else if(width < height){
            this.world.setPrefHeight(this.paneSize + mapRatio);
            this.world.setMaxHeight(this.paneSize + mapRatio);
            this.world.setMinHeight(this.paneSize + mapRatio);
            this.world.setMaxWidth(this.paneSize * (int) width / height + mapRatio);
            this.world.setPrefWidth(this.paneSize * (int) width / height + mapRatio);
            this.world.setMinWidth(this.paneSize * (int) width / height + mapRatio);
        } else {
            this.world.setPrefHeight(mapRatioY * height + mapRatioY);
            this.world.setMinHeight(mapRatioY * height + mapRatioY);
            this.world.setMaxHeight(mapRatioY * height + mapRatioY);
            this.world.setPrefWidth(mapRatioX * width + mapRatioX);
            this.world.setMinWidth(mapRatioX * width + mapRatioX);
        }
        this.world.setBackground(new Background((new BackgroundFill(Color.YELLOWGREEN, null, null))));
    }

    public void drawJungle(){
        Rectangle jungleRectangle = new Rectangle(this.jungle.getWidth() * mapRatio + mapRatio,
                this.jungle.getHeight() * mapRatio + mapRatio);
        jungleRectangle.setX(this.jungle.getLowerLeft().x * mapRatio);
        jungleRectangle.setY(this.jungle.getLowerLeft().y * mapRatio);
        jungleRectangle.setFill(Color.GREEN);
        world.getChildren().add(jungleRectangle);
    }

    private void setVisibleElements(){
        this.startButton.setVisible(true);
        this.pauseButton.setVisible(true);
        this.saveDataButton.setVisible(true);
    }

    public void setVisible(boolean visible){
        this.startButton.setVisible(visible);
        this.pauseButton.setVisible(visible);
        this.saveDataButton.setVisible(visible);
        world.setVisible(visible);
    }

    public int getMapRatio(){
        return this.mapRatio;
    }

    public Pane getWorld(){
        return world;
    }

    public TextField getNumberOfChildrenForChoosen() {
        return numberOfChildrenForChoosen;
    }

    public TextField getNumberOfDescendantsText() {
        return numberOfDescendantsText;
    }

    public TextField getDeathDayText() {
        return deathDayText;
    }

    public TextField getDayText() {
        return dayText;
    }

    public TextField getNumberAnimalText() {
        return numberAnimalText;
    }

    public TextField getNumberGrassesText() {
        return numberGrassesText;
    }

    public TextArea getDominateGenesTextArea() {
        return dominateGenesTextArea;
    }

    public TextField getAverageEnergyText() {
        return averageEnergyText;
    }

    public TextField getAverageLiveText() {
        return averageLiveText;
    }

    public TextField getAverageChildText() {
        return averageChildText;
    }

    public TextArea getDominateGenesForChoosedText() {
        return dominateGenesForChoosedText;
    }
}
