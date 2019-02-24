package io.aroma.core;

import android.support.annotation.NonNull;

/**
 * Describes all classes capable of changing a String token to some type. This
 * conversion can be a failing operation as each Conversion returns a Result.
 *
 * @param <A> type of the value returned by the Conversion
 */
public interface Conversion<A> {
    /**
     * Performs a conversion from a String token to a desired type. This
     * operation might fail, both successful result and failure will be wrapped
     * in a {@link Result}.
     *
     * @param token String token parsed directly from the XML input
     * @return {@link Result.Success} if the the conversion is successful,
     * {@link Result.Failure} otherwise.
     */
    Result<A> apply(@NonNull final String token);
}
