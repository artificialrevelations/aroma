package io.aroma.core;

import android.support.annotation.NonNull;

/**
 * A set of general purpose {@link Conversion} implementations.
 */
public final class Conversions {
    private Conversions() {
        throw new IllegalStateException("Conversions cannot be instantiated!");
    }

    /**
     * Returns a basic converter that takes a String token and converts it
     * to a String.
     *
     * @return converter from String token to String
     */
    public static Conversion<String> stringConversion() {
        return new Conversion<String>() {
            @Override
            public Result<String> apply(@NonNull final String token) {
                return new Result.Success<String>(token);
            }
        };
    }

    /**
     * Returns a basic converter that takes a String token and converts it to
     * an Integer.
     *
     * @return converter from String token to Integer
     */
    public static Conversion<Integer> integerConversion() {
        return new Conversion<Integer>() {
            @Override
            public Result<Integer> apply(@NonNull final String token) {
                try {
                    return new Result.Success<Integer>(Integer.parseInt(token));
                } catch (final Throwable throwable) {
                    return new Result.Failure<Integer>(throwable);
                }
            }
        };
    }

    /**
     * Returns a basic converter that takes a String token and converts it to
     * a Double.
     *
     * @return converter from String token to Double
     */
    public static Conversion<Double> doubleConversion() {
        return new Conversion<Double>() {
            @Override
            public Result<Double> apply(@NonNull String token) {
                try {
                    return new Result.Success<Double>(Double.parseDouble(token));
                } catch (final Throwable throwable) {
                    return new Result.Failure<Double>(throwable);
                }
            }
        };
    }

    /**
     * Returns a basic converter that takes a String token and converts it to
     * a Short.
     *
     * @return converter from String token to Short
     */
    public static Conversion<Short> shortConversion() {
        return new Conversion<Short>() {
            @Override
            public Result<Short> apply(@NonNull final String token) {
                try {
                    return new Result.Success<Short>(Short.parseShort(token));
                } catch (final Throwable throwable) {
                    return new Result.Failure<Short>(throwable);
                }
            }
        };
    }

    /**
     * Returns a basic converter that takes a String token and converts it to
     * a Float.
     *
     * @return converter from String token to Float
     */
    public static Conversion<Float> floatConversion() {
        return new Conversion<Float>() {
            @Override
            public Result<Float> apply(@NonNull final String token) {
                try {
                    return new Result.Success<Float>(Float.parseFloat(token));
                } catch (final Throwable throwable) {
                    return new Result.Failure<Float>(throwable);
                }
            }
        };
    }

    /**
     * Returns a basic converter that takes a String token and converts it to
     * a Boolean.
     *
     * @return converter from String token to Boolean
     */
    public static Conversion<Boolean> booleanConversion() {
        return new Conversion<Boolean>() {
            @Override
            public Result<Boolean> apply(@NonNull final String token) {
                if (token.equalsIgnoreCase("true"))
                    return new Result.Success<Boolean>(Boolean.TRUE);

                if (token.equalsIgnoreCase("false"))
                    return new Result.Success<Boolean>(Boolean.FALSE);

                return new Result.Failure<Boolean>(new IllegalArgumentException("Cannot parse to boolean!"));
            }
        };
    }
}
