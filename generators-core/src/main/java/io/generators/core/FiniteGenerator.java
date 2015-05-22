package io.generators.core;

/**
 * Generator that should generate finite number of values (should as it depends on the condition)
 *
 * @param <T> type of generated value
 * @author Tomas Klubal
 * @since 2.0
 */
public interface FiniteGenerator<T> extends Generator<T> {

    /**
     * Executes action for every value generated by this generator
     *
     * @param action to be executed
     */
    default void foreach(Consumer<T> action) {
        while (!finished()) {
            action.consume(next());
        }
    }
}
