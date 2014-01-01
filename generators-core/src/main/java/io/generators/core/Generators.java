package io.generators.core;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;

import java.util.List;
import java.util.Locale;
import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;
import static io.generators.core.MoreFunctions.toLowerCase;
import static io.generators.core.MoreFunctions.toUpperCase;
import static java.lang.Math.pow;

/**
 * Utility class listing all generators for convenience
 */
public final class Generators {
    public static final Generator<Integer> positiveInts = new RandomPositiveIntegerGenerator();
    public static final Generator<Long> positiveLongs = new RandomPositiveLongGenerator();
    public static final Generator<String> alphabetic10 = new RandomAlphabeticStringGenerator(10);

    private Generators() {
    }

    public static Generator<Integer> positiveInts(int from, int to) {
        return new RandomPositiveIntegerGenerator(from, to);
    }

    public static Generator<Long> positiveLongs(long from, long to) {
        return new RandomPositiveLongGenerator(from, to);
    }

    public static <T> Generator<T> broadcasting(Generator<T> delegate, List<Consumer<T>> consumers) {
        return new BroadcastingGenerator<>(delegate, consumers);
    }


    public static Generator<String> alphabetic(int length) {
        return new RandomAlphabeticStringGenerator(length);
    }

    public static <T> Generator<T> ofInstance(T instance) {
        return new GeneratorOfInstance<>(instance);
    }

    public static <T, V> Generator<T> ofType(Class<T> type, Generator<V> valueGenerator) {
        return new TypeGenerator<>(type, valueGenerator);
    }

    public static <T> Iterable<T> iterable(int limit, Generator<T> generator) {
        return new GeneratorIterable<>(limit, generator);
    }

    public static <T> Generator<T> biased(int percentageBiasTowardsFirst, Generator<T> firstGenerator, Generator<T> secondGenerator) {
        return new BiasedGenerator<>(percentageBiasTowardsFirst, firstGenerator, secondGenerator);
    }

    public static <T> List<T> listFrom(int limit, Generator<T> generator) {
        return FluentIterable.from(new GeneratorIterable<>(generator)).limit(limit).toList();
    }

    public static <T> Set<T> setFrom(int limit, Generator<T> generator) {
        return FluentIterable.from(new GeneratorIterable<>(generator)).limit(limit).toSet();
    }

    public static <T extends Enum<T>> Generator<T> randomEnum(Class<T> enumClass) {
        return new RandomEnumGenerator<>(enumClass);
    }

    public static Generator<Integer> nDigitPositiveInteger(int digits) {
        checkArgument(digits > 0 && digits < 11, "Number of digits must be between 1  and 10");
        int from = (int) pow(10, digits - 1);
        int to = (int) pow(10, digits);
        return new RandomPositiveIntegerGenerator(from, to);
    }

    public static <F,T> Generator<T> transform(Generator<F>  delegate, Function<F,T> transformation) {
        return new TransformingGenerator<>(delegate,transformation);
    }

    public static Generator<String> upperCase(GeneratorOfInstance<String> delegate, Locale locale) {
        return transform(delegate, toUpperCase(locale));
    }

    public static Generator<String> upperCase(GeneratorOfInstance<String> delegate) {
        return transform(delegate, toUpperCase());
    }

    public static Generator<String> lowerCase(GeneratorOfInstance<String> delegate) {
        return transform(delegate, toLowerCase());
    }

    public static Generator<String> lowerCase(Generator<String> delegate, Locale locale) {
        return transform(delegate, toLowerCase(locale));
    }
}
