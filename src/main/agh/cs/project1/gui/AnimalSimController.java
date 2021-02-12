package agh.cs.project1.gui;

import agh.cs.project1.*;
import agh.cs.project1.json.SaveData;
import agh.cs.project1.map.Map;
import agh.cs.project1.map.Vector2d;
import agh.cs.project1.objects.Animal;
import agh.cs.project1.objects.Grass;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.util.List;


public class AnimalSimController  {

    @FXML
    BorderPane borderPane;

    @FXML
    Pane world;

    @FXML
    Pane world1;

    @FXML
    Button startButton;

    @FXML
    Button pauseButton;

    @FXML
    Button startButton1;

    @FXML
    Button pauseButton1;

    @FXML
    TextField numberAnimalText;

    @FXML
    TextField numberGrassesText;

    @FXML
    TextArea dominateGenesTextArea;

    @FXML
    TextField averageEnergyText;

    @FXML
    TextField averageLiveText;

    @FXML
    TextField averageChildText;

    @FXML
    TextArea dominateGenesForChoosedText;

    @FXML
    TextField dayText;

    @FXML
    TextField numberOfDescendantsText;

    @FXML
    CheckBox colorDominateGenes;

    @FXML
    TextField numberAnimalText1;

    @FXML
    TextField numberGrassesText1;

    @FXML
    TextArea dominateGenesTextArea1;

    @FXML
    TextField averageEnergyText1;

    @FXML
    TextField averageLiveText1;

    @FXML
    TextField averageChildText1;

    @FXML
    TextArea dominateGenesForChoosedText1;

    @FXML
    TextField numberOfChildrenForChoosen;

    @FXML
    TextField numberOfChildrenForChoosen1;

    @FXML
    TextField dayText1;

    @FXML
    TextField numberOfDescendantsText1;

    @FXML
    CheckBox colorDominateGenes1;

    @FXML
    TextField deathDayText;

    @FXML
    TextField deathDayText1;

    @FXML
    Button saveDataButton;

    @FXML
    Button saveDataButton1;

    @FXML
    Pane extraPanel;

    @FXML
    CheckBox twoSimulationCheckBox;


    private final int PANE_SIZE = 420;

    public boolean twoSimulation = false;

    private int posX;
    private int posY;
    private boolean checkedCheckBox = false;
    private boolean checkedCheckBox1 = false;

    Simulation sim = new Simulation();
    Simulation sim1;

    private Movement clock;
    private Movement clock1;

    private int mapRatio;

    private WorldPane worldPane;
    private WorldPane worldPane1;


    private class Movement extends AnimationTimer {
        private long FRAMES_PER_SEC = 20L;
        private long INTERVAL = 1000000000L / FRAMES_PER_SEC;
        private long last = 0;

        private int id;
        private boolean start = false;

        private WorldPane worldPane;
        private Simulation sim;
        private boolean isObserve = false;

        private int day = 0;

        public Movement(WorldPane worldPane, Simulation sim, int id){
            this.worldPane = worldPane;
            this.sim = sim;
            this.id = id;
        }

        @Override
        public void handle(long now) {
            if (now - last > INTERVAL) {
                step(this.worldPane, this.sim, this);
                setStatistics(sim, worldPane);
                last = now;
            }
        }
    }

    public void initialize(){
        worldPane = new WorldPane(world, startButton, pauseButton, saveDataButton, numberOfChildrenForChoosen, numberOfDescendantsText,
                deathDayText, dayText, numberAnimalText, numberGrassesText,
                dominateGenesTextArea, averageEnergyText, averageLiveText,
                averageChildText, dominateGenesForChoosedText, sim.getMap(), PANE_SIZE, sim.getJungle());
        world = worldPane.getWorld();
        this.mapRatio = worldPane.getMapRatio();
        worldPane.drawJungle();
        this.clock = new Movement(worldPane, sim, 0);

        if(twoSimulation){
            this.sim1 = new Simulation();
            worldPane1 = new WorldPane(world1, startButton1, pauseButton1, saveDataButton1, numberOfChildrenForChoosen1, numberOfDescendantsText1,
                    deathDayText1, dayText1, numberAnimalText1, numberGrassesText1,
                    dominateGenesTextArea1, averageEnergyText1, averageLiveText1,
                    averageChildText1, dominateGenesForChoosedText1, sim1.getMap(), PANE_SIZE, sim1.getJungle());

            world1 = worldPane1.getWorld();
            worldPane1.drawJungle();

            this.clock1 = new Movement(worldPane1, sim1, 1);
            extraPanel.setVisible(true);
            worldPane1.setVisible(twoSimulation);
        } else {
            if(worldPane1 != null){
                worldPane1.setVisible(twoSimulation);
            }
            extraPanel.setVisible(false);
        }
    }

    @FXML
    public void handlerStartOne(){
        start(clock);
    }

    @FXML
    public void handlerStartTwo(){
        start(clock1);
    }

    @FXML
    public void handlerStopOne(){
        pause(worldPane, clock);
    }

    @FXML
    public void handlerStopTwo(){
        pause(worldPane1, clock1);
    }

    @FXML
    public void handlerCheckBoxOne(){
        if(clock.start){
            changeAnimalColor(worldPane, sim, checkedCheckBox);
            checkedCheckBox = !checkedCheckBox;
        }
    }

    @FXML
    public void handlerCheckBoxTwo(){
        if(clock1.start){
            changeAnimalColor(worldPane1, sim1, checkedCheckBox1);
            checkedCheckBox1 = !checkedCheckBox1;
        }
    }

    @FXML
    public void saveDataButtonHandlerOne(){
        saveDataToFile(clock);
    }

    @FXML
    public void saveDataButtonHandlerTwo(){
        saveDataToFile(clock1);
    }

    @FXML
    public void getDaysHandlerOne(){
        getDays(clock, dayText);
    }

    @FXML
    public void getDaysHandlerTwo(){
        getDays(clock1, dayText1);
    }

    private void saveDataToFile(Movement clock){
        Statistics statistics = clock.sim.getStatistics();
        new SaveData(statistics.getAverageNumberOfAnimals(), statistics.getAllAverageGrassessNumber(),
                statistics.getAllMostCommonGenes(), statistics.getAllAverageEnergy(), statistics.getAllAverageLiveTime(),
                statistics.getChildrenNumber(), clock.id);
    }

    @FXML
    public void start(Movement clock){
        clock.start();
        clock.start = true;
    }

    @FXML
    public void pause(WorldPane worldPane, Movement clock){
        clock.stop();

        worldPane.getWorld().setOnMouseClicked(event -> {
            posX = (int) event.getX();
            posY = (int) event.getY();
            showChoosedAnimal(posX, posY, clock.sim, worldPane.getDominateGenesForChoosedText());
        });
    }

    public void step(WorldPane worldPane, Simulation sim, Movement clock){
        worldPane.getWorld().getChildren().clear();
        sim.cycle();
        draw(worldPane, sim.getMap());
        if(sim.getAnimalNumber() == 0){
            pause(worldPane, clock);
        }
        if(clock.isObserve){
            if(clock.day == 1){
                pause(worldPane, clock);
                sim.setValuesForObserveAnimal();
                worldPane.getNumberOfChildrenForChoosen().setText(sim.getChildrenNumber() + "");
                worldPane.getNumberOfDescendantsText().setText(sim.getDescendantsNumber() + "");
                if(sim.getObserveDeath() == 0){
                    worldPane.getDeathDayText().setText("-");
                } else {
                    worldPane.getDeathDayText().setText(sim.getObserveDeath() + "");
                }
                clock.isObserve = !clock.isObserve;
                worldPane.getDayText().setText("");
            }
            clock.day--;
        }
        if(sim.getAnimalNumber() == 0){
            clock.stop();
        }
    }

    public void draw(WorldPane worldPane, Map map){
        worldPane.drawJungle();
        for(int i = 0; i <= map.getSize().x; i++){
            for(int j = 0; j <= map.getSize().y; j++){
                Vector2d currentPosition = new Vector2d(i, j);
                if(map.isOccupied(currentPosition)){
                    if(map.objectAt(currentPosition) instanceof Animal){
                        Rectangle rectangle = new Rectangle(mapRatio, mapRatio);
                        rectangle.setX(mapRatio * i);
                        rectangle.setY(mapRatio * j);
                        if(((Animal) map.objectAt(currentPosition)).getIsObserve()){
                            rectangle.setFill(Color.DARKMAGENTA);
                        } else {
                            rectangle.setFill(setAnimalColor((Animal) map.objectAt(currentPosition)));
                        }
                        worldPane.getWorld().getChildren().add(rectangle);
                    }
                    else if(map.objectAt(currentPosition) instanceof Grass){
                        Rectangle rectangle = new Rectangle(mapRatio, mapRatio);
                        rectangle.setX(mapRatio * i);
                        rectangle.setY(mapRatio * j);
                        rectangle.setFill(Color.DARKGREEN);
                        worldPane.getWorld().getChildren().add(rectangle);
                    }
                }
            }
        }
    }

    private Color setAnimalColor(Animal animal){
        int energy = animal.getEnergy();
        if(sim.getStartEnergy() * 0.75 <= energy){
            return Color.GRAY;
        }
        if(sim.getStartEnergy() * 0.5 <= energy){
            return Color.DARKBLUE;
        }
        if(sim.getStartEnergy() * 0.25 <= energy){
            return Color.BLACK;
        }
        return Color.RED;
    }

    private void setStatistics(Simulation sim, WorldPane worldPane){
        worldPane.getNumberAnimalText().setText("" + sim.getAnimalNumber());
        worldPane.getNumberGrassesText().setText("" + sim.getGrassessNumber());
        worldPane.getDominateGenesTextArea().setText("" + sim.getMostCommonGene());
        worldPane.getAverageEnergyText().setText("" + sim.getStatistics().getAverageEnergy());
        worldPane.getAverageLiveText().setText("" + sim.getStatistics().getAverageLiveTime());
        worldPane.getAverageChildText().setText("" + sim.getStatistics().getAverageNumberOfChildren());
    }

    private void showObservedAnimal(int posX, int posY, Simulation sim){
        int x = (int) posX / mapRatio;
        int y = (int) posY / mapRatio;
        Vector2d position = new Vector2d(x, y);

        sim.observeAnimal(position);
    }

    private void showChoosedAnimal(int posX, int posY, Simulation sim, TextArea dominateGenesForChoosedText){
        int x = (int) posX / mapRatio;
        int y = (int) posY / mapRatio;
        Vector2d position = new Vector2d(x, y);
        Animal animal = sim.getMap().getChoosedObject(position);

        if(animal != null){
            dominateGenesForChoosedText.setText(animal.getGenes().toString());
        }
    }

    public void changeAnimalColor(WorldPane worldPane, Simulation sim, boolean checkedCheckBox){
        Pane pane = worldPane.getWorld();
        if(!checkedCheckBox){
            List<Vector2d> animalWithCommonGenes = sim.getAnimalsWithMostCommonGenes();
            for(Vector2d position: animalWithCommonGenes){
                Rectangle rectangle = new Rectangle(mapRatio, mapRatio);
                rectangle.setFill(Color.GOLD);
                rectangle.setX(position.x * mapRatio);
                rectangle.setY(position.y * mapRatio);
                pane.getChildren().add(rectangle);
            }
        } else {
            pane.getChildren().clear();
            worldPane.drawJungle();
            draw(worldPane, sim.getMap());
        }
    }

    private void getDays(Movement clock, TextField dayText){
        int input = 0;
        try {
            input = Integer.parseInt(dayText.getText());
        } catch (NumberFormatException e){
            System.out.println("Niewłaściwy format danych, podaj liczbę");
            System.out.println(e);
        }
        if(input > 0){
            clock.day = input;
            clock.isObserve = true;
            showObservedAnimal(posX, posY, clock.sim);
        } else {
            throw new IllegalArgumentException("Liczba epok do obserwacji musi być większa od 0");
        }
    }

    @FXML
    public void changeNumberOfSimulation(){
        twoSimulation = !twoSimulation;
        initialize();
    }
}
