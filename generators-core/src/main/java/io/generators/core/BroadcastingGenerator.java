package io.generators.core;

import com.google.common.collect.ImmutableList;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Generator that broadcasts all the values generated by the {@code delegate} generator to supplied {@link Consumer}s
 */
public class BroadcastingGenerator<T> implements Generator<T> {
    private final Generator<T> delegate;
    private final List<Consumer<T>> consumers;

    /**
     * Creates new instance that broadcasts values generated by {@code delegate} to {@code consumers}
     *
     * @param delegate  actual generator
     * @param consumers consumers listening for generation, the list is defensively copied
     * @throws java.lang.NullPointerException when delegate is null, consumers is null or one of the elements in consumer list is null
     */
    public BroadcastingGenerator(Generator<T> delegate, List<Consumer<T>> consumers) {
        this.delegate = checkNotNull(delegate, "delegate can't be null");
        this.consumers = ImmutableList.copyOf(consumers);
    }


    /**
     * Creates new instance that broadcasts values generated by {@code delegate} to {@code consumers}
     *
     * @param delegate actual generator
     * @param first    consumer
     * @param others   other consumer
     * @throws java.lang.NullPointerException when delegate is null, first consumer is null or one of the elements in others consumers is null
     */
    @SafeVarargs
    public BroadcastingGenerator(Generator<T> delegate, Consumer<T> first, Consumer<T>... others) {
        this(delegate, ImmutableList.<Consumer<T>>builder()
                .add(first)
                .add(others)
                .build());
    }

    @Override
    public T next() {
        T generated = delegate.next();
        for (Consumer<T> consumer : consumers) {
            consumer.consume(generated);
        }
        return generated;
    }
}
