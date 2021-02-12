package agh.cs.project1.json;

import agh.cs.project1.objects.Genes;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SaveData {

    public SaveData(double animalNumber, double grassessNumber, Genes genes, double energy,
                    double liveTime, double childs, int number){
        save(animalNumber, grassessNumber, genes, energy, liveTime, childs, number);
    }

    private void save(double animalNumber, double grassessNumber, Genes genes, double energy,
                      double liveTime, double childs, int number){
        try {
            File file = new File(System.getProperty("user.dir") + "/src/files/statistics" + number + ".txt");
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write("Statystki dla symulacji: " + number);
            fileWriter.write("\nSrednia liczba wszystkich zwierząt: " + animalNumber);
            fileWriter.write("\nSrednia liczba trawy: " + grassessNumber);
            fileWriter.write("\nDominujący genotyp: " + genesArrayToString(genes));
            fileWriter.write("\nSrednia liczba energii zwierząt: " + energy);
            fileWriter.write("\nSrednia liczba życia zwierząt: " + liveTime);
            fileWriter.write("\nSrednia liczba dzieci zwierząt: " + childs);
            fileWriter.close();
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    private String genesArrayToString(Genes genes){
        String s = "";
        int genesArray[] = genes.getGenesArray();
        for(int i = 0; i < 32; i++){
            s += genesArray[i];
        }
        return s;
    }
}
