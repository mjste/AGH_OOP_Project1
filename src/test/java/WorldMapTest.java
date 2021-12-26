import agh.ics.oop.Animal;
import agh.ics.oop.Vector2D;
import agh.ics.oop.WorldMap;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class WorldMapTest {
    @Test
    public void placeTest() {
        WorldMap map = new WorldMap();

        Animal animal1 = new Animal(map, new Vector2D(0, 0), 100);
        Animal animal2 = new Animal(map, new Vector2D(10, 0), 100);
        Animal animal3 = new Animal(map, new Vector2D(10, 10), 100);

        map.placeAnimal(animal1);
        map.placeAnimal(animal2);
        map.placeAnimal(animal3);

        List<Animal> animalList1 = map.animalsAt(new Vector2D(0, 0));
        List<Animal> animalList2 = map.animalsAt(new Vector2D(1, 0));
        List<Animal> animalList3 = map.animalsAt(new Vector2D(10, 10));

        assertSame(animalList1.get(0), animal1);
        assertEquals(0, animalList2.size());
        assertEquals(1, animalList3.size());
    }
}
