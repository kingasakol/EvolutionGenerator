package agh.cs.project1.objects;

import java.util.Arrays;
import java.util.Random;

public class Genes {
    private final int [] genesArray = new int[32];
    private final int [] numberOfGene = new int[8];
    private Random rand = new Random();

    public Genes(){
        for(int i = 0; i < 8; i++){
            numberOfGene[i] = 0;
        }
        createRandomGenes();
        Arrays.sort(this.genesArray);
    }

    private void createRandomGenes(){
        for(int i = 0; i < 32; i++){
            this.genesArray[i] = rand.nextInt(8);
            this.numberOfGene[this.genesArray[i]]++;
        }
        while(!isAllNumber());
    }

    public Genes(Genes animal1, Genes animal2){
        int x1 = rand.nextInt(30);
        int x2 = x1 + 1 + rand.nextInt(30 - x1);

        for(int i = 0; i <= x1; i++){
            this.genesArray[i] = animal1.getGenesArray()[i];
            this.numberOfGene[this.genesArray[i]]++;
        }
        for(int i = x1 + 1; i <= x2; i++){
            this.genesArray[i] = animal2.getGenesArray()[i];
            this.numberOfGene[this.genesArray[i]]++;
        }
        for(int i = x2 + 1; i < 32; i++){
            this.genesArray[i] = animal1.getGenesArray()[i];
            this.numberOfGene[this.genesArray[i]]++;
        }
        while(!isAllNumber());
    }

    private boolean isAllNumber(){
        for(int i = 0; i < 8; i++){
            if(numberOfGene[i] == 0){
                int j = rand.nextInt(32);
                numberOfGene[genesArray[j]]--;
                numberOfGene[i]++;
                genesArray[j] = i;
                return false;
            }
        }
        return true;
    }

    public int chooseRandomGene(){
        int x = rand.nextInt(32);
        return genesArray[x];
    }

    public String toString(){
        String s = "";
        for(int i = 0; i < 32; i++){
            s += genesArray[i];
            if(i == 15){
                s += "\n";
            }
        }
        return s;
    }

    public int[] getGenesArray() {
        return genesArray;
    }

    @Override
    public boolean equals(Object other){
        if(this == other){
            return true;
        }
        if(other == null){
            return false;
        }

        for(int i = 0; i < 8; i++){
            if(this.numberOfGene[i] != ((Genes) other).numberOfGene[i])
                return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 13;
        int [] genesArrayThis = this.getGenesArray();
        for(int i = 0; i < 4; i += 8){
            hash += genesArrayThis[i] * 31;
            hash += genesArrayThis[i + 1] * 11;
            hash += genesArrayThis[i + 2] * 7;
            hash += genesArrayThis[i + 3] * 17;
            hash += genesArrayThis[i + 4] * 29;
            hash += genesArrayThis[i + 5] * 13;
            hash += genesArrayThis[i + 6] * 19;
            hash += genesArrayThis[i + 7] * 71;
        }
        return hash;
    }
}
