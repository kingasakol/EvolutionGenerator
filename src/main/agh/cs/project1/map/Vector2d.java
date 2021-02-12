package agh.cs.project1.map;

public class Vector2d {
    public final int x;
    public final int y;

    public Vector2d(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        String s1 = Integer.toString(this.x);
        String s2 = Integer.toString(this.y);
        return "(" + s1 + "," + s2 + ")";
    }

    public boolean precedes(Vector2d other) {
        return this.x <= other.x && this.y <= other.y;
    }

    public boolean follows(Vector2d other) {
        return this.x >= other.x && this.y >= other.y;
    }

    public Vector2d upperRight(Vector2d other) {
        if (other == null)
            return this;
        int newX, newY;

        newX = Math.max(other.x, this.x);
        newY = Math.max(other.y, this.y);

        return new Vector2d(newX, newY);
    }

    public Vector2d lowerLeft(Vector2d other) {
        if (other == null){
            return this;
        }

        int newX, newY;

        newX = Math.min(other.x, this.x);
        newY = Math.min(other.y, this.y);

        return new Vector2d(newX, newY);
    }

    public Vector2d add(Vector2d other) {
        return new Vector2d(this.x + other.x, this.y + other.y);
    }

    public Vector2d subtract(Vector2d other) {
        return new Vector2d(this.x - other.x, this.y - other.y);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (!(other instanceof Vector2d))
            return false;
        Vector2d that = (Vector2d) other;
        return this.x == that.x && this.y == that.y;
    }

    @Override
    public int hashCode() {
        int hash = 13;
        hash += this.x * 31;
        hash += this.y * 17;
        return hash;
    }

    public Vector2d opposite() {
        return new Vector2d(-this.x, -this.y);
    }
}
