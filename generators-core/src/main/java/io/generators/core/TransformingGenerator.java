package io.generators.core;

import com.google.common.base.Function;

import javax.annotation.Nonnull;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Transforms value &lt;F&gt; generated by {@code delegate} to &lt;T&gt; using {@code function}
 *
 * @author Tomas Klubal
 */
public class TransformingGenerator<F, T> implements Generator<T> {
    private final Generator<F> delegate;
    private final Function<F, T> function;

    /**
     * Creates new generator
     *
     * @param delegate actual generator
     * @param function to be used for transformation
     * @throws java.lang.NullPointerException when delegate or function are null
     */
    public TransformingGenerator(@Nonnull Generator<F> delegate, @Nonnull Function<F, T> function) {
        this.delegate = checkNotNull(delegate, "Delegate generator can't be null");
        this.function = checkNotNull(function, "Function can't be null");
    }

    @Override
    public T next() {
        return function.apply(delegate.next());
    }
}
