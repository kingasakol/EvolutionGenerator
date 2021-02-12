package agh.cs.project1.map;

public class Jungle {
    private final Vector2d upperRight;
    private final Vector2d lowerLeft;
    private Map map;

    public Jungle(Map map, double jungleRatio){
        this.map = map;
        this.upperRight = new Vector2d((int) ((map.getSize().x / 2) + (jungleRatio * map.getSize().x / 2)),
                (int) ((map.getSize().y / 2) + (jungleRatio * map.getSize().y / 2)));
        this.lowerLeft = new Vector2d((int) ((map.getSize().x / 2) - (jungleRatio * map.getSize().x / 2)),
                (int) ((map.getSize().y / 2) - (jungleRatio * map.getSize().y / 2)));
    }

    public Vector2d getUpperRight() {
        return upperRight;
    }

    public Vector2d getLowerLeft() {
        return lowerLeft;
    }

    public int getWidth(){
        return upperRight.x - lowerLeft.x;
    }

    public int getHeight(){
        return upperRight.y - lowerLeft.y;
    }
}
