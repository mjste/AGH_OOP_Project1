package agh.ics.oop;


import java.util.Arrays;

public class Genome {
    private final int[] genes;

    public Genome(int[] genes) {
        this.genes = genes;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        for (int i = 0; i < 32; i++) {
            string.append(Integer.toString(genes[i]));
        }
        return string.toString();
//        return Arrays.toString(genes);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other instanceof Genome) {
            return Arrays.equals(this.genes, ((Genome) other).genes);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(genes);
    }
}
