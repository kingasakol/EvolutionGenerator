package agh.cs.project1.objects;

import agh.cs.project1.*;
import agh.cs.project1.map.MapDirection;
import agh.cs.project1.map.Vector2d;

import java.util.*;

public class Animal{

    private Vector2d position;
    private MapDirection direction;
    private int energy;
    private final Genes genes;
    private final IWorldMap map;
    private final int startEnergy;
    public ArrayList<IChangeObserver> observers = new ArrayList<>();

    private boolean isDescedant;
    private boolean isChild;
    private boolean isObserve = false;

    private int age;
    private int childs;
    private int deathTime;

    Random rand = new Random();

    public Animal(IWorldMap map, int startEnergy){
        this.map = map;
        this.energy = startEnergy;
        this.startEnergy = startEnergy;
        this.direction = MapDirection.values()[rand.nextInt(8)];
        this.position = new Vector2d(rand.nextInt(map.getSize().x), rand.nextInt(map.getSize().y));
        this.genes = new Genes();

        this.age = 0;
        this.childs = 0;

        this.map.place(this);
        this.isDescedant = false;
        this.isChild = false;
    }

    public Animal(IWorldMap map, Animal animal1, Animal animal2){
        this.map = map;
        this.energy = animal1.energy / 4 + animal2.energy / 4;
        this.startEnergy = animal1.startEnergy;
        this.direction = MapDirection.values()[rand.nextInt(8)];
        this.position = animalChildPosition(animal1.getPosition());
        this.genes = new Genes(animal1.getGenes(), animal2.getGenes());

        if(animal1.getIsObserve() || animal2.getIsObserve()){
            this.isChild = true;
        } else if(animal1.getIsDescedants() || animal2.getIsDescedants() || animal1.getIsChild() || animal2.getIsChild()){
            this.isDescedant = true;
        } else {
            this.isDescedant = false;
            this.isChild = false;
        }

        this.age = 0;
        this.childs = 0;

        animal1.reproduce();
        animal2.reproduce();

        this.map.place(this);
    }

    public void reproduce(){
        this.energy -= this.energy / 4;
        this.childs++;
    }

    public boolean canReproduce(){
        return this.energy >= this.startEnergy / 2;
    }

    public Vector2d animalChildPosition(Vector2d animalPosition){
        List<Vector2d> suggestedPosition = new ArrayList<>();

        Vector2d positionForChild = animalPosition.add(this.direction.toUnitVector());
        for(int i = 0; i < 8; i++){
            if(!(map.objectAt(positionForChild) instanceof Animal)){
                suggestedPosition.add(positionForChild);
            } else {
                positionForChild = this.direction.next().toUnitVector();
            }
        }
        if(suggestedPosition.size() > 0){
            int index = rand.nextInt(suggestedPosition.size());
            return suggestedPosition.get(index);
        }

        MapDirection randDirection = MapDirection.values()[rand.nextInt(8)];
        positionForChild = animalPosition.add(randDirection.toUnitVector());
        return positionForChild;
    }

    public void eatGrass(int grassEnergy){
        this.energy += grassEnergy;
    }

    public String toString(){
        return direction.toString();
    }

    public void move(int moveEnergy) {
        Vector2d newPosition = this.position.add(this.direction.toUnitVector());
        this.energy -= moveEnergy;
        this.age++;
        Vector2d size = map.getSize();
        newPosition = new Vector2d((newPosition.x + size.x + 1) % (size.x + 1),
                (newPosition.y + 1 + size.y) % (size.y + 1));
        Vector2d oldPosition = this.position;
        this.position = newPosition;
        positionChanged(oldPosition, this.position);
    }

    public void turn(){
        for(int i = 0; i < this.genes.chooseRandomGene(); i++){
            this.direction = this.direction.next();
        }
    }

    public void addObserver(IChangeObserver observer){
        this.observers.add(observer);
    }

    public void removeObserver(IChangeObserver observer){
        this.observers.remove(observer);
    }

    private void positionChanged(Vector2d oldPosition, Vector2d newPosition){
        for(IChangeObserver observer: this.observers) {
            observer.positionChanged(oldPosition, newPosition, this);
        }
    }

    public void setDeathTime(int deathTime){
        this.deathTime = deathTime;
    }

    public boolean getIsObserve(){
        return isObserve;
    }

    public Vector2d getPosition(){
        return this.position;
    }

    public int getAge() {
        return age;
    }

    public int getChilds() {
        return childs;
    }

    public int getDeathTime(){
        return deathTime;
    }

    public int getEnergy() {
        return energy;
    }

    public Genes getGenes() {
        return genes;
    }

    public boolean isDeath() {
        return this.energy <= 0;
    }

    public void setAsChoosen(){
        this.isObserve = true;
    }

    public void setAsUnChoosen(){
        this.isObserve = false;
    }

    public void setIsNotChild(){
        this.isChild = false;
    }

    public void setIsNotDescedants(){
        this.isDescedant = false;
    }

    public boolean getIsChild(){
        return this.isChild;
    }

    public boolean getIsDescedants(){
        return this.isDescedant;
    }
}
