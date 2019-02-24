package io.aroma.core;

import java.util.Collection;
import java.util.Map;

/**
 * A collection of Aroma specific asserts.
 */
class AromaAssert {
    /**
     * Checks if the map produced by the Aroma parser is of the specified type.
     *
     * @param map  map produced by the parser
     * @param type type of map that should be produced by the parser
     * @param <A>  key type
     * @param <B>  value type
     */
    static <A, B> void assertMapType(final Map<A, Collection<B>> map,
                                     final Class<? extends Map> type) {
        if (!map.getClass().isAssignableFrom(type)) {
            throw new AssertionError("Incorrect map type!");
        }
    }

    /**
     * Checks if the map produced by the Aroma parser contains collections of
     * the specified type.
     *
     * @param map  map produced by the parser
     * @param type type of collection that should be produced by the parser
     * @param <A>  key type
     * @param <B>  value type
     */
    static <A, B> void assertCollectionType(final Map<A, Collection<B>> map,
                                            final Class<? extends Collection> type) {
        final Collection<Collection<B>> values = map.values();
        if (values.isEmpty())
            throw new AssertionError("Map is empty!");

        for (final Collection<B> collection : values) {
            if (!collection.getClass().isAssignableFrom(type)) {
                throw new AssertionError("Incorrect collection type!");
            }
        }
    }
}
