package agh.cs.project1;

import agh.cs.project1.objects.Animal;
import agh.cs.project1.objects.Genes;

import java.util.HashMap;
import java.util.List;

public class Statistics implements IGenesControl{

    private final HashMap<Genes, Integer> allCommonGenes = new HashMap<>();
    private final List<Animal> animals;
    private final List<Animal> historyList;

    private double childrenNumber;

    private double sumEnergy;
    private double sumLiveTime;
    private int sumOfAnimal;
    private int sumOfGrassess;

    private Genes mostCommonGenes;

    private double dayAverageEnergy;
    private double dayAverageChildrenNumber;
    private double dayAverageLiveTime;

    private int day;

    public Statistics(List animals, List historyList){
        this.historyList = historyList;
        this.animals = animals;
    }

    public double getAverageEnergy(){
        if(animals.size() > 0){
            int energySum = 0;

            for(Animal animal: animals){
                energySum += animal.getEnergy();
            }
            this.dayAverageEnergy = (double) energySum / animals.size();
            return (double) energySum / animals.size();
        }
        return 0;
    }

    public double getAverageLiveTime(){
        if(!historyList.isEmpty()){
            int sumAge = 0;
            for(Animal animal: this.historyList){
                sumAge += animal.getAge();
            }
            this.dayAverageLiveTime = (double) sumAge / this.historyList.size();
            return (double) sumAge / this.historyList.size();
        }
        return 0;
    }

    public double getAverageNumberOfChildren(){
       if(animals.size() > 0){
           int allChilds = 0;
           for(Animal animal: animals){
               allChilds += animal.getChilds();
           }
           this.dayAverageChildrenNumber = allChilds / animals.size();
           return this.dayAverageChildrenNumber;
       }
       return 0;
    }

    public void updateStatistics(int day, Genes gene, int grassessNumber, int animalNumber, int childs){
        this.day = day;
        updateDominateGenes(gene);
        updateGrassessNumber(grassessNumber);
        updateNumberOfAnimal(animalNumber);
        updateAverageEnergy();
        upddateChildrenNumber(childs);
        updateAverageLiveTime();
    }

    private void updateNumberOfAnimal(int animalNumber){
        this.sumOfAnimal += animalNumber;
    }

    private void updateGrassessNumber(int grassessNumber){
        this.sumOfGrassess += grassessNumber;
    }

    public void updateDominateGenes(Genes genes){
        addGenes(genes);
    }

    private void updateAverageEnergy(){
        this.sumEnergy += this.dayAverageEnergy;
    }

    private void updateAverageLiveTime(){
        this.sumLiveTime += this.dayAverageLiveTime;
    }

    private void upddateChildrenNumber(int childs){
        this.childrenNumber += childs;
    }

    @Override
    public void setDominateGenes(){
        if(allCommonGenes.size() > 0){
            int maxCount = 0;
            Genes dominateGene = null;
            for(java.util.Map.Entry<Genes, Integer> item: allCommonGenes.entrySet()){
                if(item.getValue() > maxCount){
                    maxCount = item.getValue();
                    dominateGene = item.getKey();
                }
            }
            this.mostCommonGenes = dominateGene;
        }
    }

    @Override
    public void addGenes(Genes genes){
        if(allCommonGenes.containsKey(genes)){
            allCommonGenes.put(genes, allCommonGenes.get(genes) + 1);
        } else {
            allCommonGenes.put(genes, 1);
        }
        setDominateGenes();
    }

    public double getAllAverageEnergy(){
        return this.sumEnergy / this.day;
    }

    public double getAllAverageLiveTime(){
        return this.sumLiveTime / this.day;
    }

    public double getAverageNumberOfAnimals() {
        return this.sumOfAnimal / this.day;
    }

    public double getAllAverageGrassessNumber() {
        return this.sumOfGrassess / this.day;
    }

    public double getChildrenNumber() {
        return childrenNumber / this.day;
    }

    public Genes getAllMostCommonGenes(){
        return this.mostCommonGenes;
    }
}
