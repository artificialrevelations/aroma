package io.aroma.core;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.support.annotation.NonNull;
import android.support.annotation.XmlRes;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

import static io.aroma.core.Conversions.stringConversion;

/**
 * Simple Android Resource Map parser that allows generating multimaps from
 * android resources. A multimap can have multiple values for a single key.
 * <p>
 * Basic supported XML format:
 * <pre>
 * {@code
 *
 * <map>
 *     <!-- key as attribute -->
 *     <entry key="foo">bar</entry>
 *     <!-- key and value both as attributes -->
 *     <entry key="baz" value="qux" />
 *     <!-- key and value both as tags -->
 *     <entry>
 *         <key>quux</key>
 *         <value>corge</value>
 *     </entry>
 * </map>
 * }
 * </pre>
 * To produce a the multimap from the given XML resource id:
 * <pre>
 * {@code
 *
 * final Map<String, Collections<String>> parsedMap =
 *     Aroma.from(androidContext)
 *          .parse(R.xml.my_map_in_resource);
 *
 * }
 * </pre>
 * <p>
 * By default Aroma parser reads both keys and values as strings. If a specific
 * type is needed then a special {@link Conversion} can be passed for key or
 * value (or both). There is a set of predefined converters that allow converting
 * to base types. Please check {@link Conversions} for more examples.
 * <p>
 * Below is an example of generating a multimap that has keys stored as Integers
 * and values stored as Doubles:
 * <pre>
 * {@code
 *
 *  final Map<Integer, Collection<Double>> parsedMap =
 *      Aroma.from(androidContext)
 *           .withKeyConversion(Conversions.integerConversion())
 *           .withValueConversion(Conversions.doubleConversion())
 *           .parse(R.xml.my_map_in_resource);
 *
 * }
 * </pre>
 * <p>
 * Type of the generated {@link Map} and used {@link Collection} can be specified
 * both in XML or in code. In XML it's specified through {@code map} tag attributes:
 * <ul>
 * <li>type - can be a HASHMAP, LINKED_HASHMAP, TREEMAP</li>
 * <li>collection - can be a LIST, SET, ORDERED_SET</li>
 * </ul>
 * <p>
 * Below is an example of an XML with map type and collection specified:
 * <pre>
 * {@code
 *
 * <map type="LINKED_HASHMAP" collection="ORDERED_SET">
 *     <entry key="foo" value="bar" />
 *     <entry key="baz" value="qux" />
 *     <!-- other entries -->
 * </map>
 *
 * }
 * </pre>
 * <p>
 * It's possible to specify both map type and collection type manually in code.
 * If specified like this, types specified in XML will be overwritten.
 * <p>
 * Below is an example of generating a multimap that has specified map and
 * collection type:
 * <pre>
 * {@code
 *
 *  final Map<String, Collection<String>> parsedMap =
 *      Aroma.from(androidContext)
 *           .withMapType(MapTypes.TREEMAP)
 *           .withCollectionType(CollectionTypes.SET)
 *           .parse(R.xml.my_map_in_resource);
 *
 * }
 * </pre>
 *
 * @param <A> type of the parsed key
 * @param <B> type of the parsed value
 * @see Conversion
 * @see Conversions
 * @see Result
 */
public final class Aroma<A, B> {
    /**
     * Specifies all the {@link Map} types supported by the Aroma parser.
     */
    public enum MapTypes {
        HASHMAP,
        TREEMAP,
        LINKED_HASHMAP
    }

    /**
     * Specifies all the {@link Collection} types supported by the Aroma parser.
     */
    public enum CollectionTypes {
        LIST,
        SET,
        ORDERED_SET
    }

    @NonNull
    private final Context context;

    @NonNull
    private final Conversion<A> keyConverter;
    @NonNull
    private final Conversion<B> valueConverter;

    @NonNull
    private final MapTypes mapType;
    @NonNull
    private final CollectionTypes collectionType;

    private final boolean continueOnError;

    private Aroma(@NonNull final Context context,
                  @NonNull final Conversion<A> keyConverter,
                  @NonNull final Conversion<B> valueConverter,
                  @NonNull final MapTypes mapType,
                  @NonNull final CollectionTypes collectionType,
                  final boolean continueOnError) {
        this.context = context;
        this.keyConverter = keyConverter;
        this.valueConverter = valueConverter;
        this.mapType = mapType;
        this.collectionType = collectionType;
        this.continueOnError = continueOnError;
    }

    /**
     * Creates an instance of Aroma parser from the given Android {@link Context}.
     * Calling a {@linkplain Aroma#parse(int) parse method} immediately after
     * creating the parser with {@link Aroma#from(Context)} will cause it to use
     * default values.
     *
     * @param context android context
     * @return instance of the Aroma parser
     */
    public static Aroma<String, String> from(@NonNull final Context context) {
        return new Aroma<String, String>(
                context,
                stringConversion(),
                stringConversion(),
                MapTypes.HASHMAP,
                CollectionTypes.LIST,
                false
        );
    }

    /**
     * Creates an instance of Aroma parser that has a custom key conversion set.
     * By default map keys are strings, if keys should be
     * a specific or custom type a conversion needs to be set.
     *
     * @param conversion conversion used for key conversion
     * @param <C>        type of the converted key
     * @return instance of the Aroma parser
     */
    public <C> Aroma<C, B> withKeyConversion(@NonNull final Conversion<C> conversion) {
        return new Aroma<C, B>(
                context,
                conversion,
                valueConverter,
                mapType,
                collectionType,
                continueOnError
        );
    }

    /**
     * Creates an instance of Aroma parser that has a custom value conversion set.
     * By default map values (stored in the collection) are strings, if values
     * should be a specific or custom type a conversion needs to be set.
     *
     * @param conversion conversion used for value conversion
     * @param <C>        type of the converted value
     * @return instance of the Aroma parser
     */
    public <C> Aroma<A, C> withValueConversion(@NonNull final Conversion<C> conversion) {
        return new Aroma<A, C>(
                context,
                keyConverter,
                conversion,
                mapType,
                collectionType,
                continueOnError
        );
    }

    /**
     * Creates an instance of Aroma parser that will produce the specified type
     * of the map. By default created map is specified in the XML resource, if
     * there is no map type defined in XML then a {@link MapTypes#HASHMAP} will
     * be used. If map type is defined in XML and in code using this method then
     * map type specified by this call will take precedence.
     *
     * @param type type of the map returned by the parser
     * @return instance of the Aroma parser
     */
    public Aroma<A, B> withMapType(@NonNull final MapTypes type) {
        return new Aroma<A, B>(
                context,
                keyConverter,
                valueConverter,
                type,
                collectionType,
                continueOnError
        );
    }

    /**
     * Creates an instance of Aroma parser that will produce the specified type
     * of the collection stored in the map. By default created collection is
     * specified in the XML resource, if there is no collection type defined in
     * XML then a {@link CollectionTypes#LIST} will be used. If collection type
     * is defined in XML and in code using this method then collection type
     * specified by this call will take precedence.
     *
     * @param type type of the collection stored in the map returned by the parser
     * @return instance of the Aroma parser
     */
    public Aroma<A, B> withCollectionType(@NonNull final CollectionTypes type) {
        return new Aroma<A, B>(
                context,
                keyConverter,
                valueConverter,
                mapType,
                type,
                continueOnError
        );
    }

    /**
     * Specifies if the created parser should continue if there are issues while
     * parsing. Parsing issues might involve key or value conversions, unknown
     * arguments, unknown attributes, missing arguments, missing attributes.
     * By default parser will not continue on any error, setting this method
     * to true will cause the parser to keep parsing despite the errors. This
     * might cause the resulting map to be missing keys, values or be not
     * complete.
     *
     * @param continueOnError if set to true then parser will try to continue
     *                        parsing despite the found issues
     * @return instance of the Aroma parser
     */
    public Aroma<A, B> continueOnError(final boolean continueOnError) {
        return new Aroma<A, B>(
                context,
                keyConverter,
                valueConverter,
                mapType,
                collectionType,
                continueOnError
        );
    }

    private static String TAG_MAP = "map";
    private static String TAG_ENTRY = "entry";
    private static String TAG_KEY = "key";
    private static String TAG_VALUE = "value";

    private static String ATTRIBUTE_MAP_TYPE = "type";
    private static String ATTRIBUTE_MAP_TYPE_HASHMAP = "HASHMAP";
    private static String ATTRIBUTE_MAP_TYPE_LINKED_HASHMAP = "LINKED_HASHMAP";
    private static String ATTRIBUTE_MAP_TYPE_TREEMAP = "TREEMAP";

    private static String ATTRIBUTE_MAP_COLLECTION = "collection";
    private static String ATTRIBUTE_ENTRY_KEY = "key";
    private static String ATTRIBUTE_ENTRY_VALUE = "value";

    /**
     * Performs parsing of the given XML resource.
     *
     * @param resourceId
     * @return
     */
    public Map<A, Collection<B>> parse(@XmlRes final int resourceId) {
        final Resources resources = context.getResources();
        final XmlResourceParser parser = resources.getXml(resourceId);

        try {
            int eventType = parser.getEventType();
            while (eventType != XmlResourceParser.END_DOCUMENT) {
                // parse map tag
                if (XmlResourceParser.START_TAG == eventType && TAG_MAP.equalsIgnoreCase(parser.getName())) {
                    final int attributeCount = parser.getAttributeCount();
                    for (int i = 0; i < attributeCount; i++) {
                        String attributeName = parser.getAttributeName(i);
                        String attributeValue = parser.getAttributeValue(i);

                        if (ATTRIBUTE_MAP_TYPE.equalsIgnoreCase(attributeName)) {

                        } else if (ATTRIBUTE_MAP_COLLECTION.equalsIgnoreCase(attributeName)) {

                        } else {
                            // unknown attribute name
                        }
                    }
                }

                eventType = parser.next();
            }
        } catch (final XmlPullParserException xppe) {

        } catch (final IOException ioe) {

        }

        // on error recover with an empty map
        return Collections.emptyMap();
    }


    static <A, B> Map<A, Collection<B>> getMap(@NonNull final MapTypes mapType) {
        switch (mapType) {
            case LINKED_HASHMAP:
                return new LinkedHashMap<A, Collection<B>>();
            case TREEMAP:
                return new TreeMap<A, Collection<B>>();
            case HASHMAP:
            default:
                return new HashMap<A, Collection<B>>();
        }
    }

    static <A> Collection<A> getCollection(@NonNull final CollectionTypes collectionType) {
        switch (collectionType) {
            case SET:
                return new HashSet<A>();
            case ORDERED_SET:
                return new LinkedHashSet<A>();
            case LIST:
            default:
                return new LinkedList<A>();
        }
    }
}
