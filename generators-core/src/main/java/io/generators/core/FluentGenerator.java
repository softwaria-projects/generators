package io.generators.core;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableList;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Provides fluent interface for creating generators
 *
 * @author Tomas Klubal
 */
public class FluentGenerator<T> implements Generator<T> {

    private final Generator<T> delegate;

    private FluentGenerator(@Nonnull Generator<T> delegate) {
        this.delegate = checkNotNull(delegate, "delegate can't be null");
    }

    /**
     * Creates FLuentGenerator delegating generation to {@code delegate}
     *
     * @param generator generator to delegate generation to
     * @param <T>       type of the generators
     * @return FluentGenerator
     * @throws java.lang.NullPointerException when {@code delegate} is null
     */
    @CheckReturnValue
    public static <T> FluentGenerator<T> from(@Nonnull Generator<T> generator) {
        return new FluentGenerator<>(generator);
    }

    /**
     * Wraps the delegate generator in {@link io.generators.core.UniqueGenerator}
     *
     * @return FluentGenerator generating only unique values
     */
    @CheckReturnValue
    public FluentGenerator<T> unique() {
        return from(new UniqueGenerator<>(delegate));
    }

    /**
     * Wraps generated values in a specified type.
     *
     * @param type type to wrap generated values with
     * @param <K>  the type
     * @return FluentGenerator generating <K>
     * @see io.generators.core.TypeGenerator
     */
    @CheckReturnValue
    public <K> FluentGenerator<K> ofType(@Nonnull Class<K> type) {
        return from(Generators.ofType(checkNotNull(type), delegate));
    }

    /**
     * Creates Iterable over <T> which values are generated by this FluentGenerator
     *
     * @param size size of the iterable
     * @return Iterable over <T> which values are generated
     * @see io.generators.core.GeneratorIterable
     */
    public Iterable<T> toIterable(int size) {
        return new GeneratorIterable<>(size, delegate);
    }

    /**
     * Returns generator that transforms generated values from <T> to <G> using provided {@code function}
     *
     * @param function used for the transformation
     * @param <G>      result of the transformation
     * @return FluentGenerator doing transformation
     * @see io.generators.core.TransformingGenerator
     */
    @CheckReturnValue
    public <G> FluentGenerator<G> transform(@Nonnull Function<T, G> function) {
        return from(new TransformingGenerator<>(delegate, checkNotNull(function)));
    }

    /**
     * Returns FluentGenerator that filters values using provided {@code predicate}
     *
     * @param predicate to use for filtering
     * @return Filtering FluentGenerator
     * @see io.generators.core.FilteringGenerator
     */
    @CheckReturnValue
    public FluentGenerator<T> filter(@Nonnull Predicate<T> predicate) {
        return from(new FilteringGenerator<>(delegate, checkNotNull(predicate)));
    }

    /**
     * Returns FluentGenerator that published generated values to provided {@code first} and {@code other} consumers
     *
     * @param first  consumer
     * @param others consumers
     * @return Broadcasting FluentGenerator
     * @see io.generators.core.BroadcastingGenerator
     */
    @SafeVarargs
    @CheckReturnValue
    public final FluentGenerator<T> publishTo(@Nonnull  Consumer<T> first, @Nonnull Consumer<T>... others) {
        final ImmutableList<Consumer<T>> consumers = ImmutableList.<Consumer<T>>builder()
                .add(first)
                .add(others)
                .build();
        return publishTo(consumers);

    }

    /**
     * Returns FluentGenerator that published generated values to provided {@code consumers}
     *
     * @param consumers to publish/broadcast to
     * @return Broadcasting FluentGenerator
     * @see io.generators.core.BroadcastingGenerator
     */
    @CheckReturnValue
    public FluentGenerator<T> publishTo(@Nonnull List<Consumer<T>> consumers) {
        return from(new BroadcastingGenerator<>(delegate, checkNotNull(consumers)));
    }

    @Override
    public T next() {
        return delegate.next();
    }

}
