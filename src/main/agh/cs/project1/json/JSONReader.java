package agh.cs.project1.json;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileReader;
import java.io.IOException;

public class JSONReader {
    private int width;
    private int height;
    private int startEnergy;
    private int moveEnergy;
    private int plantEnergy;
    private double jungleRatio;
    private int animalNumber;

    public JSONReader(){
        parse("/src/files/startData.json");
    }

    public void parse(String path) {
        try{
            JSONParser jsonParser = new JSONParser();
            FileReader fileReader = new FileReader(System.getProperty("user.dir") + path);
            JSONObject jsonObject = (JSONObject) jsonParser.parse(fileReader);

            this.width = (int) (long) jsonObject.get("width");
            this.height = (int) (long) jsonObject.get("height");
            this.startEnergy = (int) (long) jsonObject.get("startEnergy");
            this.moveEnergy = (int) (long) jsonObject.get("moveEnergy");
            this.plantEnergy = (int) (long) jsonObject.get("plantEnergy");
            this.jungleRatio = (double) jsonObject.get("jungleRatio");
            this.animalNumber = (int) (long) jsonObject.get("animalNumber");
            checkValue();
        } catch(IOException e){
            e.printStackTrace();
        }catch (ParseException e) {
            e.printStackTrace();
        } catch(IllegalArgumentException e){
            System.out.println(e);
            System.exit(1);
        }
    }

    private void checkValue(){
        if(this.width <= 0){
            throw new IllegalArgumentException("Wymiary mapy nie mogą być niedodatnie!");
        }
        if(this.height <= 0){
            throw new IllegalArgumentException("Wymiary mapy nie mogą być niedodatnie!");
        }
        if(this.startEnergy <= 0){
            throw new IllegalArgumentException("Początkowa energia nie może być niedodatnia!");
        }
        if(this.moveEnergy <= 0){
            throw new IllegalArgumentException("Tracona energia nie może być niedodatnia");
        }
        if(this.plantEnergy <= 0){
            throw new IllegalArgumentException("Energia pobierana przez zwierzęta nie może być niedodatnia");
        }
        if(this.jungleRatio >= 1){
            throw new IllegalArgumentException("Stosunek wielkości dżungli do sawanny nie może być większy od 1");
        }
        if(this.animalNumber <= 0){
            throw new IllegalArgumentException("Liczba zwierząt musi być większa od 0");
        }
    }

    public int getWidth(){
        return this.width;
    }

    public int getHeight(){
        return this.height;
    }

    public int getStartEnergy(){
        return this.startEnergy;
    }

    public int getMoveEnergy(){
        return this.moveEnergy;
    }

    public int getPlantEnergy(){
        return this.plantEnergy;
    }

    public double getJungleRatio(){
        return this.jungleRatio;
    }

    public int getAnimalNumber(){
        return this.animalNumber;
    }
}
