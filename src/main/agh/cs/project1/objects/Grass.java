package agh.cs.project1.objects;

import agh.cs.project1.map.Vector2d;

public class Grass {
    private Vector2d position;
    private int plantEnergy;

    public Grass(Vector2d position, int plantEnergy){
        this.position = position;
        this.plantEnergy = plantEnergy;
    }

    public String toString(){
        return "*";
    }

    public Vector2d getPosition(){
        return new Vector2d(this.position.x, this.position.y);
    }

    public int getPlantEnergy(){
        return this.plantEnergy;
    }
}
