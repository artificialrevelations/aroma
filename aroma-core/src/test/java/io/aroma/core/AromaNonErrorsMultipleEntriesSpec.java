package io.aroma.core;

import android.content.Context;
import static io.aroma.core.AromaAssert.assertCollectionType;
import static io.aroma.core.AromaAssert.assertMapType;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class AromaNonErrorsMultipleEntriesSpec {
    @Test
    public void no_type_or_collection() {
        // given:
        final Context context = RuntimeEnvironment.application.getApplicationContext();

        // when:
        final Map<String, Collection<String>> tested =
                Aroma.from(context)
                        .parse(R.xml.test_nonerror_default_attrs_multiple_entries);

        // then:
        assertMapType(tested, HashMap.class);
        assertCollectionType(tested, LinkedList.class);
        assertEquals(2, tested.size());
        assertEquals(asList("bar1", "bar2", "bar3"), tested.get("foo"));
        assertEquals(asList("qux1", "qux2", "qux3"), tested.get("baz"));
    }

    @Test
    public void no_type_but_collection_is_set() {
        // given:
        final Context context = RuntimeEnvironment.application.getApplicationContext();

        // when:
        final Map<String, Collection<String>> tested =
                Aroma.from(context)
                        .parse(R.xml.test_nonerror_collection_set_multiple_entries);

        // then:
        assertMapType(tested, HashMap.class);
        assertCollectionType(tested, LinkedHashSet.class);
        assertEquals(2, tested.size());
        assertEquals(asList("bar1", "bar2", "bar3"), tested.get("foo"));
        assertEquals(asList("qux1", "qux2", "qux3"), tested.get("baz"));
    }

    @Test
    public void type_is_set_but_no_collection() {
        // given:
        final Context context = RuntimeEnvironment.application.getApplicationContext();

        // when:
        final Map<String, Collection<String>> tested =
                Aroma.from(context)
                        .parse(R.xml.test_nonerror_type_set_multiple_entries);

        // then:
        assertMapType(tested, LinkedHashMap.class);
        assertCollectionType(tested, LinkedList.class);
        assertEquals(2, tested.size());
        assertEquals(asList("bar1", "bar2", "bar3"), tested.get("foo"));
        assertEquals(asList("qux1", "qux2", "qux3"), tested.get("baz"));
    }

    @Test
    public void type_is_set_and_collection_is_set() {
        // given:
        final Context context = RuntimeEnvironment.application.getApplicationContext();

        // when:
        final Map<String, Collection<String>> tested =
                Aroma.from(context)
                        .parse(R.xml.test_nonerror_attrs_set_multiple_entries);

        // then:
        assertMapType(tested, LinkedHashMap.class);
        assertCollectionType(tested, LinkedHashSet.class);
        assertEquals(2, tested.size());
        assertEquals(asList("bar1", "bar2", "bar3"), tested.get("foo"));
        assertEquals(asList("qux1", "qux2", "qux3"), tested.get("baz"));
    }
}
