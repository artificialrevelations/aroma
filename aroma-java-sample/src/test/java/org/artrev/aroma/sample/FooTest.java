package org.artrev.aroma.sample;

import org.artrev.aroma.core.Foo;
import org.junit.Test;

import static junit.framework.Assert.assertNotNull;

public class FooTest {

    @Test
    public void shouldNotBeNull() {
        final Foo foo = new Foo();
        assertNotNull(foo);
    }
}
