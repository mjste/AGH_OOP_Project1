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
        switch (dir) {
            case N:
                return new Vector2D(0, 1);
            case NE:
                return new Vector2D(1, 1);
            case E:
                return new Vector2D(1, 0);
            case SE:
                return new Vector2D(1, -1);
            case S:
                return new Vector2D(0, -1);
            case SW:
                return new Vector2D(-1, -1);
            case W:
                return new Vector2D(-1, 0);
            case NW:
                return new Vector2D(-1, 1);
            default:
                throw new IllegalArgumentException();
        }
    }

    public static MapDirection changeDirection(MapDirection dir, int diff) {
        return values[(dir.ordinal()+diff+ values.length) % values().length];
    }
}
