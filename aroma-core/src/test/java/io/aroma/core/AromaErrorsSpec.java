package io.aroma.core;

import android.content.Context;
import static io.aroma.core.AromaAssert.assertCollectionType;
import static io.aroma.core.AromaAssert.assertMapType;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class AromaErrorsSpec {
    //TODO: Should we handle null arguments?

    @Test
    public void map_tag_is_missing() {
        //TODO: Should this case be recoverable with continueOnError?

        // given:
        final Context context = RuntimeEnvironment.application.getApplicationContext();

        // when:
        final Map<String, Collection<String>> tested =
                Aroma.from(context).parse(R.xml.test_error_map_tag_missing);

        // then:
        assertTrue(tested.isEmpty());
        assertTrue(tested instanceof HashMap);
    }

    @Test
    public void map_tag_is_not_a_root_tag() {
        //TODO: Should this case be recoverable with continueOnError?

        // given:
        final Context context = RuntimeEnvironment.application.getApplicationContext();

        // when:
        final Map<String, Collection<String>> tested =
                Aroma.from(context).parse(R.xml.test_error_map_tag_not_root_tag);

        // then:
        assertTrue(tested.isEmpty());
        assertTrue(tested instanceof HashMap);
    }

    @Test
    public void map_tag_has_unknown_attributes() {
        // given:
        final Context context = RuntimeEnvironment.application.getApplicationContext();

        // when:
        final Map<String, Collection<String>> tested =
                Aroma.from(context)
                        .parse(R.xml.test_error_map_tag_unknown_attrs);

        // then:
        assertTrue(tested.isEmpty());
        assertTrue(tested instanceof LinkedHashMap);
    }

    @Test
    public void map_tag_has_unknown_attributes_continue_on_error() {
        // given:
        final Context context = RuntimeEnvironment.application.getApplicationContext();

        // when:
        final Map<String, Collection<String>> tested =
                Aroma.from(context)
                        .continueOnError(true)
                        .parse(R.xml.test_error_map_tag_unknown_attrs);

        // then:
        assertMapType(tested, LinkedHashMap.class);
        assertCollectionType(tested, LinkedHashSet.class);
        assertEquals(1, tested.size());
        assertEquals(asList("unknown", "attributes"), tested.get("map_tag_with"));
    }

    @Test
    public void map_tag_has_unknown_child_tags() {
        // given:
        final Context context = RuntimeEnvironment.application.getApplicationContext();

        // when:
        final Map<String, Collection<String>> tested =
                Aroma.from(context)
                        .parse(R.xml.test_error_map_tag_unknown_child_tags);

        // then:
        assertTrue(tested.isEmpty());
        assertMapType(tested, LinkedHashMap.class);
    }

    @Test
    public void map_tag_has_unknown_child_tags_continue_on_error() {
        // given:
        final Context context = RuntimeEnvironment.application.getApplicationContext();

        // when:
        final Map<String, Collection<String>> tested =
                Aroma.from(context)
                        .continueOnError(true)
                        .parse(R.xml.test_error_map_tag_unknown_child_tags);

        // then:
        assertMapType(tested, LinkedHashMap.class);
        assertCollectionType(tested, LinkedHashSet.class);
        assertEquals(1, tested.size());
        assertEquals(asList("unknown", "tags"), tested.get("map_with"));
    }

    @Test
    public void map_tag_has_entry_tags_with_unknown_attributes() {
        // given:
        final Context context = RuntimeEnvironment.application.getApplicationContext();

        // when:
        final Map<String, Collection<String>> tested =
                Aroma.from(context)
                        .parse(R.xml.test_error_map_tag_unknown_entry_tag_attrs);

        // then:
        assertTrue(tested.isEmpty());
        assertMapType(tested, LinkedHashMap.class);
    }

    @Test
    public void map_tag_has_entry_tags_with_unknown_attributes_continue_on_error() {
        // given:
        final Context context = RuntimeEnvironment.application.getApplicationContext();

        // when:
        final Map<String, Collection<String>> tested =
                Aroma.from(context)
                        .continueOnError(true)
                        .parse(R.xml.test_error_map_tag_unknown_entry_tag_attrs);

        // then:
        assertMapType(tested, LinkedHashMap.class);
        assertCollectionType(tested, LinkedHashSet.class);
        assertEquals(1, tested.size());
        assertEquals(asList("unknown", "attributes"), tested.get("entry_with"));
    }

    @Test
    public void key_conversion_error() {
        // given:
        final Context context = RuntimeEnvironment.application.getApplicationContext();

        // when:
        final Map<Boolean, Collection<String>> tested =
                Aroma.from(context)
                        .withKeyConversion(Conversions.booleanConversion())
                        .parse(R.xml.test_error_conversion_key_type);

        // then:
        assertTrue(tested.isEmpty());
        assertMapType(tested, HashMap.class);
    }

    @Test
    public void key_conversion_error_continue_on_error() {
        // given:
        final Context context = RuntimeEnvironment.application.getApplicationContext();

        // when:
        final Map<Boolean, Collection<String>> tested =
                Aroma.from(context)
                        .continueOnError(true)
                        .withKeyConversion(Conversions.booleanConversion())
                        .parse(R.xml.test_error_conversion_key_type);

        // then:
        assertMapType(tested, HashMap.class);
        assertCollectionType(tested, LinkedList.class);
        assertEquals(1, tested.size());
        assertEquals(asList("key", "converted", "correctly"), tested.get(Boolean.FALSE));
    }

    @Test
    public void value_conversion_error() {
        // given:
        final Context context = RuntimeEnvironment.application.getApplicationContext();

        // when:
        final Map<String, Collection<Boolean>> tested =
                Aroma.from(context)
                        .withValueConversion(Conversions.booleanConversion())
                        .parse(R.xml.test_error_conversion_value_type);

        // then:
        assertTrue(tested.isEmpty());
        assertMapType(tested, HashMap.class);
    }

    @Test
    public void value_conversion_error_continue_on_error() {
        // given:
        final Context context = RuntimeEnvironment.application.getApplicationContext();

        // when:
        final Map<String, Collection<Boolean>> tested =
                Aroma.from(context)
                        .continueOnError(true)
                        .withValueConversion(Conversions.booleanConversion())
                        .parse(R.xml.test_error_conversion_value_type);

        // then:
        assertMapType(tested, HashMap.class);
        assertCollectionType(tested, LinkedList.class);
        assertEquals(1, tested.size());
        assertEquals(asList(true, false), tested.get("value_conversion"));
    }

    @Test
    public void value_and_key_conversion_error() {
        // given:
        final Context context = RuntimeEnvironment.application.getApplicationContext();

        // when:
        final Map<Integer, Collection<Integer>> tested =
                Aroma.from(context)
                        .withKeyConversion(Conversions.integerConversion())
                        .withValueConversion(Conversions.integerConversion())
                        .parse(R.xml.test_error_conversion_key_value_type);

        // then:
        assertTrue(tested.isEmpty());
        assertMapType(tested, HashMap.class);
    }

    @Test
    public void value_and_key_conversion_error_continue_on_error() {
        // given:
        final Context context = RuntimeEnvironment.application.getApplicationContext();

        // when:
        final Map<Integer, Collection<Integer>> tested =
                Aroma.from(context)
                        .continueOnError(true)
                        .withKeyConversion(Conversions.integerConversion())
                        .withValueConversion(Conversions.integerConversion())
                        .parse(R.xml.test_error_conversion_key_value_type);

        // then:
        assertMapType(tested, HashMap.class);
        assertCollectionType(tested, LinkedList.class);
        assertEquals(1, tested.size());
        assertEquals(Collections.singletonList(42), tested.get(1));
    }
}
