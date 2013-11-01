package net.softwaria.generators;

/**
 * Utility class listing all generators for convenience
 */
public final class Generators {
    public static final Generator<Integer> positiveInts = new RandomPositiveIntegerGenerator();
    public static final Generator<String> alphabetic10 = new RandomAlphabeticStringGenerator(10);

    private Generators() {
    }

    public static Generator<Integer> positiveInts(int from, int to) {
        return new RandomPositiveIntegerGenerator(from, to);
    }

    public static Generator<String> alphabetic(int length) {
        return new RandomAlphabeticStringGenerator(length);
    }
}
