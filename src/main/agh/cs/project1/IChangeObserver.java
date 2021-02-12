package agh.cs.project1;

import agh.cs.project1.map.Vector2d;
import agh.cs.project1.objects.Animal;

public interface IChangeObserver {
    void positionChanged(Vector2d oldPosition, Vector2d newPosition, Animal animal);

    void liveChanged(Animal animal);
}
