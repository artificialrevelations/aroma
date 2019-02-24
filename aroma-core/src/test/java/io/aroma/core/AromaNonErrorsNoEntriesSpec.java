package io.aroma.core;

import android.content.Context;
import static io.aroma.core.AromaAssert.assertMapType;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class AromaNonErrorsNoEntriesSpec {
    @Test
    public void no_type_or_collection() {
        // given:
        final Context context = RuntimeEnvironment.application.getApplicationContext();

        // when:
        final Map<String, Collection<String>> tested =
                Aroma.from(context)
                        .parse(R.xml.test_nonerror_default_attrs_no_entries);

        // then:
        assertTrue(tested.isEmpty());
        assertMapType(tested, HashMap.class);
    }

    @Test
    public void no_type_but_collection_is_set() {
        // given:
        final Context context = RuntimeEnvironment.application.getApplicationContext();

        // when:
        final Map<String, Collection<String>> tested =
                Aroma.from(context)
                        .parse(R.xml.test_nonerror_collection_set_no_entries);

        // then:
        assertTrue(tested.isEmpty());
        assertMapType(tested, HashMap.class);
    }

    @Test
    public void type_is_set_but_no_collection() {
        // given:
        final Context context = RuntimeEnvironment.application.getApplicationContext();

        // when:
        final Map<String, Collection<String>> tested =
                Aroma.from(context)
                        .parse(R.xml.test_nonerror_type_set_no_entries);

        // then:
        assertTrue(tested.isEmpty());
        assertMapType(tested, LinkedHashMap.class);
    }

    @Test
    public void type_is_set_and_collection_is_set() {
        // given:
        final Context context = RuntimeEnvironment.application.getApplicationContext();

        // when:
        final Map<String, Collection<String>> tested =
                Aroma.from(context)
                        .parse(R.xml.test_nonerror_attrs_set_no_entries);

        // then:
        assertTrue(tested.isEmpty());
        assertMapType(tested, LinkedHashMap.class);
    }
}
