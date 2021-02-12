package agh.cs.project1.map;

import agh.cs.project1.IChangeObserver;
import agh.cs.project1.IWorldMap;
import agh.cs.project1.objects.Animal;
import agh.cs.project1.objects.Grass;

import java.util.*;
import java.util.stream.Collectors;

public class Map implements IWorldMap, IChangeObserver {

    private final HashMap<Vector2d, List<Animal>> animalHashMap = new HashMap<>();
    private final HashMap<Vector2d, Grass> grassHashMap = new HashMap<>();
    private Vector2d upperRight;
    private List<Animal> historyList = new ArrayList<>();

    Random rand = new Random();

    public Map(int width, int height){
        upperRight = new Vector2d(width - 1, height - 1);
    }

    @Override
    public boolean place(Animal animal) {
        if(!animalHashMap.containsKey(animal.getPosition())){
            animalHashMap.put(animal.getPosition(), new ArrayList<>());
        }
        animalHashMap.get(animal.getPosition()).add(animal);
        animal.addObserver(this);
        return true;
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        if(animalHashMap.get(position) != null){
            return true;
        }
        return grassHashMap.get(position) != null;
    }

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition, Animal animal) {
        animalHashMap.get(oldPosition).remove(animal);
        if(animalHashMap.get(oldPosition).isEmpty()){
            animalHashMap.remove(oldPosition);
        }
        if(!animalHashMap.containsKey(animal.getPosition())){
            animalHashMap.put(animal.getPosition(), new ArrayList<>());
        }
        animalHashMap.get(newPosition).add(animal);
    }

    @Override
    public Object objectAt(Vector2d position) {
        if(animalHashMap.get(position) != null){
            return animalHashMap.get(position).get(0);
        }
        return grassHashMap.get(position);
    }

    public void placeGrass(Grass grass){
        if (!this.isOccupied(grass.getPosition())) {
            grassHashMap.put(grass.getPosition(), grass);
        }
    }

    public void eatGrass(){
        List<Grass> grassessToEat = this.grassHashMap.values().stream().collect(Collectors.toList());

        for(Grass grass: grassessToEat){
            if (this.animalHashMap.containsKey(grass.getPosition())){
                List<Animal> animalsInOneField = sortedAnimalsInOneFiledByEnergy(grass.getPosition());

                if(animalsInOneField.size() > 1){
                    List<Animal> strenghtsAnimals = new ArrayList<>();

                    Animal strongest = animalsInOneField.get(0);
                    int maxEnergy = strongest.getEnergy();
                    strenghtsAnimals.add(strongest);

                    for(Animal animal: animalsInOneField){
                        if(animal.getEnergy() == maxEnergy){
                            strenghtsAnimals.add(animal);
                        }
                    }

                    int energyToEat = grass.getPlantEnergy() / strenghtsAnimals.size();

                    for(Animal animal: strenghtsAnimals){
                        animal.eatGrass(energyToEat);
                    }
                } else {
                    Animal animal = animalsInOneField.get(0);
                    animal.eatGrass(grass.getPlantEnergy());
                }
                this.grassHashMap.remove(grass.getPosition());
            }
        }
    }

    public Animal [][] getAnimalsToReproduce(){
        List<Animal[]> animalsToReproduce = new ArrayList<>();

        for(java.util.Map.Entry<Vector2d, List<Animal>> item: animalHashMap.entrySet()) {
            if(item.getValue().size() > 1){
                List <Animal> animalsInOneField = sortedAnimalsInOneFiledByEnergy(item.getKey());
                Animal[] strongestAnimal = getTwoRandStrongestAnimals(animalsInOneField);

                Animal strongest = strongestAnimal[0];
                Animal strongest1 = strongestAnimal[1];

                if(strongest.canReproduce() && strongest1.canReproduce()){
                    Animal [] pairOfAnimal = {strongest, strongest1};
                    animalsToReproduce.add(pairOfAnimal);
                }
            }
        }
        return animalsToReproduce.toArray(new Animal[animalsToReproduce.size()][]);
    }

    private Animal[] getTwoRandStrongestAnimals(List<Animal> animalsInOneField){
        List<Animal> array = new ArrayList();
        List<Animal> array1 = new ArrayList();
        int i = 1;

        while(i < animalsInOneField.size() &&
                animalsInOneField.get(i - 1).getEnergy() == animalsInOneField.get(i).getEnergy()){
            array.add(animalsInOneField.get(i - 1));
            i++;
        }
        i++;
        while(i < animalsInOneField.size() &&
                animalsInOneField.get(i - 1).getEnergy() == animalsInOneField.get(i).getEnergy()){
            array1.add(animalsInOneField.get(i - 1));
            i++;
        }
        if(array.size() == 2){
            return new Animal[] {array.get(0), array.get(1)};
        } else if (array.size() == 1 && array1.size() > 1){
            return new Animal[] {array.get(0), array1.get(rand.nextInt(array1.size()))};
        }
        return new Animal[] {animalsInOneField.get(0), animalsInOneField.get(1)};
    }

    private List<Animal> sortedAnimalsInOneFiledByEnergy(Vector2d position){
        List<Animal> animals = animalHashMap.get(position);

        animals.sort((animal1, animal2) -> {
            if(animal1 == animal2){
                return 0;
            } else if(animal1.getEnergy() <= animal2.getEnergy()){
                return 1;
            } else {
                return -1;
            }
        });
        return animals;
    }

    public Vector2d getSize(){
        return upperRight;
    }

    @Override
    public void liveChanged(Animal animal) {
        this.animalHashMap.get(animal.getPosition()).remove(animal);
        if(this.animalHashMap.get(animal.getPosition()).isEmpty()){
            this.animalHashMap.remove(animal.getPosition());
        }
    }

    public Animal getChoosedObject(Vector2d position){
        if(animalHashMap.containsKey(position)){
            return animalHashMap.get(position).get(0);
        } else {
            return null;
        }
    }

    public int getGrassNumber(){
        return grassHashMap.size();
    }
}
