package io.generators.core;

import com.google.common.base.Predicate;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Returns only values generated by {@code delegate} that satisfy given {@code predicate}
 */
public class FilteringGenerator<T> implements Generator<T> {
    private final Generator<T> delegate;
    private final Predicate<T> predicate;

    /**
     * Creates new generator
     *
     * @param delegate  actual generator
     * @param predicate actual filter
     *
     * @throws java.lang.NullPointerException when delegate or predicate are null
     */
    public FilteringGenerator(Generator<T> delegate, Predicate<T> predicate) {
        this.delegate = checkNotNull(delegate, "Delegate generator can't be null");
        this.predicate = checkNotNull(predicate, "Predicate can't be null");

    }

    @Override
    public T next() {
        T next;
        do {
            next = delegate.next();
        } while (!predicate.apply(next));

        return next;
    }
}
