package agh.cs.project1;

import agh.cs.project1.json.JSONReader;
import agh.cs.project1.map.Jungle;
import agh.cs.project1.map.Map;
import agh.cs.project1.map.Vector2d;
import agh.cs.project1.objects.Animal;
import agh.cs.project1.objects.Genes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Simulation implements IEngine, IGenesControl {

    private final List<Animal> animals;
    private final List<Animal> historyList = new ArrayList<>();
    private final HashMap<Genes, Integer> genesHashMap = new HashMap<>();
    private Genes mostCommonGene;
    private int grassNumber;

    int width;
    int height;
    int animalNumber;

    private final Map map;
    private final Jungle jungle;
    private final double jungleRatio;
    private final GrassGrowth grassGrowth;
    private int day;
    private final int plantEnergy;
    private final int startEnergy;
    private final int moveEnergy;

    private int observeChildren;
    private int observeDescendants;
    private int observeDeath;
    private int childrenNumber;
    private int descendantsNumber;

    private final JSONReader jsonReader;

    private final Statistics statistics;
    private int newChilds = 0;

    public Simulation() {
        jsonReader = new JSONReader();
        this.width = jsonReader.getWidth();
        this.height = jsonReader.getHeight();
        this.animalNumber = jsonReader.getAnimalNumber();
        this.jungleRatio = jsonReader.getJungleRatio();
        this.plantEnergy = jsonReader.getPlantEnergy();
        this.startEnergy = jsonReader.getStartEnergy();
        this.moveEnergy = jsonReader.getMoveEnergy();

        this.map = new Map(width, height);
        this.jungle = new Jungle(this.map, jungleRatio);
        this.grassGrowth = new GrassGrowth(map, jungle, plantEnergy);
        this.animals = new ArrayList<>();

        createAnimal(animalNumber, map, startEnergy);
        this.day = 0;
        this.grassNumber = 0;
        this.statistics = new Statistics(animals, historyList);
    }

    public void createAnimal(int animalNumber, Map map, int startEnergy) {
        for (int i = 0; i < animalNumber; i++) {
            Animal animal = new Animal(map, startEnergy);
            animals.add(animal);
            addGenes(animal.getGenes());
        }
    }

    public void cycle() {
        this.day++;
        run(this.day);
        deleteDeathAnimals();
        map.eatGrass();
        reproduce();
        grassGrowth.grownGrass();
        setDominateGenes();

        statistics.updateStatistics(this.day, this.mostCommonGene, getGrassessNumber(),
                getAnimalNumber(), this.newChilds);

        this.newChilds = 0;
    }

    @Override
    public void run(int day) {
        for (Animal animal : animals) {
            animal.turn();
            animal.move(this.moveEnergy);
        }
    }

    public void reproduce() {
        Animal[][] parents = map.getAnimalsToReproduce();
        if (parents != null) {
            for (Animal[] pair : parents) {
                Animal animal1 = pair[0];
                Animal animal2 = pair[1];
                Animal child = new Animal(this.map, animal1, animal2);
                animals.add(child);
                addGenes(child.getGenes());
                newChilds++;
            }
        }
    }

    private void deleteDeathAnimals() {
        animals.removeIf(animal -> {
            if (animal.isDeath()) {
                animal.setDeathTime(day);
                animal.removeObserver(map);
                deleteGenes(animal.getGenes());
                historyList.add(animal);
                map.liveChanged(animal);
            }
            return animal.isDeath();
        });
    }

    private void deleteGenes(Genes genes) {
        if (genesHashMap.containsKey(genes)) {
            int number = genesHashMap.get(genes);
            if (number > 1) {
                genesHashMap.put(genes, number - 1);
            } else {
                genesHashMap.remove(genes);
            }
        }
        setDominateGenes();
    }

    @Override
    public void setDominateGenes() {
        if (genesHashMap.size() > 0) {
            int maxCount = 0;
            Genes dominateGene = null;
            for (java.util.Map.Entry<Genes, Integer> item : genesHashMap.entrySet()) {
                if (item.getValue() > maxCount) {
                    maxCount = item.getValue();
                    dominateGene = item.getKey();
                }
            }
            this.mostCommonGene = dominateGene;
        }
    }

    @Override
    public void addGenes(Genes genes) {
        if (genesHashMap.containsKey(genes)) {
            genesHashMap.put(genes, genesHashMap.get(genes) + 1);
        } else {
            genesHashMap.put(genes, 1);
        }
        setDominateGenes();
    }

    public List<Vector2d> getAnimalsWithMostCommonGenes() {
        List<Vector2d> animalWithCommonGene = new ArrayList<>();
        for (Animal animal : animals) {
            if (animal.getGenes().equals(this.mostCommonGene)) {
                animalWithCommonGene.add(animal.getPosition());
            }
        }
        return animalWithCommonGene;
    }

    public void observeAnimal(Vector2d position) {
        if (map.getChoosedObject(position) != null) {
            Animal animal = map.getChoosedObject(position);
            animal.setAsChoosen();
            this.observeChildren = animal.getChilds();
            this.observeDescendants = 0;
        }
    }

    public void setValuesForObserveAnimal() {
        int descendants = 0;
        int children = 0;

        Animal observeAnimal = null;
        for (Animal animal : animals) {
            if (animal.getIsObserve()) {
                observeAnimal = animal;
            }
            if (animal.getIsDescedants()) {
                descendants += 1;
            }
            if (animal.getIsChild()) {
                children += 1;
            }
        }

        for (Animal animal : historyList) {
            if (observeAnimal == null) {
                if (animal.getIsObserve()) {
                    observeAnimal = animal;
                    this.observeDeath = observeAnimal.getDeathTime();
                }
            }
            if (animal.getIsDescedants()) {
                descendants += 1;
            }
            if (animal.getIsChild()) {
                children += 1;
            }
        }

        this.childrenNumber = children - this.observeChildren;

        if (this.childrenNumber < 0) {
            this.childrenNumber = 0;
        }
        this.descendantsNumber = descendants + this.childrenNumber;

        unchoosedAnimal();
    }

    private void unchoosedAnimal() {
        for (Animal animal : animals) {
            if (animal.getIsObserve()) {
                animal.setAsUnChoosen();
            }
            if (animal.getIsChild()) {
                animal.setIsNotChild();
            }
            if (animal.getIsDescedants()) {
                animal.setIsNotDescedants();
            }
        }
        for(Animal animal: historyList){
            if (animal.getIsObserve()) {
                animal.setAsUnChoosen();
            }
            if (animal.getIsChild()) {
                animal.setIsNotChild();
            }
            if (animal.getIsDescedants()) {
                animal.setIsNotDescedants();
            }
        }
    }

    public int getChildrenNumber() {
        return this.childrenNumber;
    }

    public int getDescendantsNumber() {
        return this.descendantsNumber;
    }

    public int getObserveDeath() {
        return this.observeDeath;
    }

    public int getStartEnergy() {
        return startEnergy;
    }

    public Map getMap() {
        return this.map;
    }

    public Jungle getJungle() {
        return this.jungle;
    }

    public Statistics getStatistics() {
        return statistics;
    }

    public int getAnimalNumber() {
        return this.animals.size();
    }

    public int getGrassessNumber() {
        return this.map.getGrassNumber();
    }

    public Genes getMostCommonGene() {
        return this.mostCommonGene;
    }
}
