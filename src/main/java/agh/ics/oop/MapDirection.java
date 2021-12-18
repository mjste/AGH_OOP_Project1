package agh.ics.oop;

public enum MapDirection {
    N,
    NE,
    E,
    SE,
    S,
    SW,
    W,
    NW;

    private static MapDirection[] values = MapDirection.values();

    public static Vector2D toUnitVector(MapDirection dir) {
        return switch (dir) {
            case N -> new Vector2D(0, 1);
            case NE -> new Vector2D(1, 1);
            case E -> new Vector2D(1, 0);
            case SE -> new Vector2D(1, -1);
            case S -> new Vector2D(0, -1);
            case SW -> new Vector2D(-1, -1);
            case W -> new Vector2D(-1, 0);
            case NW -> new Vector2D(-1, 1);
        };
    }

    public static MapDirection changeDirection(MapDirection dir, int diff) {
        return values[(dir.ordinal()+diff+ values.length) % values().length];
    }
}
