package agh.cs.project1;

import agh.cs.project1.map.Jungle;
import agh.cs.project1.map.Map;
import agh.cs.project1.map.Vector2d;
import agh.cs.project1.objects.Grass;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GrassGrowth {
    private final Map map;
    private final Jungle jungle;
    private final List<Vector2d> jungleFields;
    private final List<Vector2d> savannaFields;

    private final int plantEnergy;
    Random rand = new Random();

    public GrassGrowth(Map map, Jungle jungle, int plantEnergy){
        this.plantEnergy = plantEnergy;
        this.map = map;
        this.jungle = jungle;
        this.jungleFields = new ArrayList<>();
        this.savannaFields = new ArrayList<>();

        findFreeFields();
    }

    private void findFreeFields(){
        this.jungleFields.clear();
        this.savannaFields.clear();
        for(int i = 0; i <= map.getSize().x; i++){
            for(int j = 0; j <= map.getSize().y; j++){
                Vector2d currentField = new Vector2d(i, j);
                if(!map.isOccupied(currentField)){
                    if(currentField.follows(jungle.getLowerLeft()) && currentField.precedes(jungle.getUpperRight())){
                        jungleFields.add(currentField);
                    } else {
                        savannaFields.add(currentField);
                    }
                }
            }
        }
    }

    private void grownGrassSavanna(){
        if(savannaFields.size() > 0){
            int randIndex = rand.nextInt(savannaFields.size());
            Grass grass = new Grass(savannaFields.get(randIndex), this.plantEnergy);
            map.placeGrass(grass);
        }
    }

    private void grownGrassJungle(){
        if(jungleFields.size() > 0){
            int randIndex = rand.nextInt(jungleFields.size());
            Grass grass = new Grass(jungleFields.get(randIndex), this.plantEnergy);
            map.placeGrass(grass);
        }
    }

    public void grownGrass(){
        findFreeFields();
        grownGrassSavanna();
        grownGrassJungle();
    }
}
