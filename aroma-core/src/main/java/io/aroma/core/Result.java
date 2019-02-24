package io.aroma.core;

import android.support.annotation.NonNull;

import java.util.NoSuchElementException;

/**
 * @param <A>
 */
public abstract class Result<A> {
    public abstract A get();

    public abstract Throwable getCause();

    /**
     * @param <A>
     */
    public static class Success<A> extends Result<A> {
        private final A value;

        public Success(@NonNull final A value) {
            this.value = value;
        }

        @Override
        public A get() {
            return value;
        }

        @Override
        public Throwable getCause() {
            throw new NoSuchElementException("Result.Success does not contain an error!");
        }
    }

    /**
     * @param <A>
     */
    public static class Failure<A> extends Result<A> {
        private final Throwable throwable;

        public Failure(@NonNull final Throwable throwable) {
            this.throwable = throwable;
        }

        @Override
        public A get() {
            throw new NoSuchElementException("Result.Failure does not contain a value!");
        }

        @Override
        public Throwable getCause() {
            return throwable;
        }
    }
}
