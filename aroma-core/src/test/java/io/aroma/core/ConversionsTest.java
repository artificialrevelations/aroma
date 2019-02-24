package io.aroma.core;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class ConversionsTest {
    public static class StringConversion {
        @Test
        public void empty_token_returns_empty_result() {
            // given:
            final String token = "";
            final Conversion<String> tested = Conversions.stringConversion();

            // when:
            final Result<String> result = tested.apply(token);

            // then:
            assertTrue(result instanceof Result.Success);
            assertEquals(result.get(), token);
        }

        @Test
        public void token_converted_to_result() {
            // given:
            final String token = "foobar";
            final Conversion<String> tested = Conversions.stringConversion();

            // when:
            final Result<String> result = tested.apply(token);

            // then:
            assertTrue(result instanceof Result.Success);
            assertEquals(result.get(), token);
        }
    }

    public static class IntegerConversion {
        @Test
        public void token_is_not_parsable_to_integer() {
            // given:
            final String token = "42a";
            final Conversion<Integer> tested = Conversions.integerConversion();

            // when:
            final Result<Integer> result = tested.apply(token);

            // then:
            assertTrue(result instanceof Result.Failure);
            assertTrue(result.getCause() instanceof NumberFormatException);
        }

        @Test
        public void token_is_parsable_to_integer() {
            // given:
            final String token = "42";
            final Integer expected = 42;
            final Conversion<Integer> tested = Conversions.integerConversion();

            // when:
            final Result<Integer> result = tested.apply(token);

            // then:
            assertTrue(result instanceof Result.Success);
            assertEquals(expected, result.get());
        }
    }

    public static class DoubleConversion {
        @Test
        public void token_is_not_parsable_to_double() {
            // given:
            final String token = "42a.42";
            final Conversion<Double> tested = Conversions.doubleConversion();

            // when:
            final Result<Double> result = tested.apply(token);

            // then:
            assertTrue(result instanceof Result.Failure);
            assertTrue(result.getCause() instanceof NumberFormatException);
        }

        @Test
        public void token_is_parsable_to_double() {
            // given:
            final String token = "42.42";
            final Double expected = 42.42d;
            final Conversion<Double> tested = Conversions.doubleConversion();

            // when:
            final Result<Double> result = tested.apply(token);

            // then:
            assertTrue(result instanceof Result.Success);
            assertEquals(expected, result.get());
        }
    }

    public static class ShortConversion {
        @Test
        public void token_is_not_parsable_to_short() {
            // given:
            final String token = "42a";
            final Conversion<Short> tested = Conversions.shortConversion();

            // when:
            final Result<Short> result = tested.apply(token);

            // then:
            assertTrue(result instanceof Result.Failure);
            assertTrue(result.getCause() instanceof NumberFormatException);
        }

        @Test
        public void token_is_parsable_to_short() {
            // given:
            final String token = "42";
            final Short expected = 42;
            final Conversion<Short> tested = Conversions.shortConversion();

            // when:
            final Result<Short> result = tested.apply(token);

            // then:
            assertTrue(result instanceof Result.Success);
            assertEquals(expected, result.get());
        }
    }

    public static class FloatConversion {
        @Test
        public void token_is_not_parsable_to_float() {
            // given:
            final String token = "42a";
            final Conversion<Float> tested = Conversions.floatConversion();

            // when:
            final Result<Float> result = tested.apply(token);

            // then:
            assertTrue(result instanceof Result.Failure);
            assertTrue(result.getCause() instanceof NumberFormatException);
        }

        @Test
        public void token_is_parsable_to_float() {
            // given:
            final String token = "42";
            final Float expected = 42f;
            final Conversion<Float> tested = Conversions.floatConversion();

            // when:
            final Result<Float> result = tested.apply(token);

            // then:
            assertTrue(result instanceof Result.Success);
            assertEquals(expected, result.get());
        }
    }

    public static class BooleanConversion {
        @Test
        public void token_is_not_parsable_to_boolean() {
            // given:
            final String token = "treu";
            final Conversion<Boolean> tested = Conversions.booleanConversion();

            // when:
            final Result<Boolean> result = tested.apply(token);

            // then:
            assertTrue(result instanceof Result.Failure);
            assertTrue(result.getCause() instanceof IllegalArgumentException);
        }

        @Test
        public void token_is_parsable_to_boolean() {
            // given:
            final String token = "false";
            final Boolean expected = false;
            final Conversion<Boolean> tested = Conversions.booleanConversion();

            // when:
            final Result<Boolean> result = tested.apply(token);

            // then:
            assertTrue(result instanceof Result.Success);
            assertEquals(expected, result.get());
        }
    }
}
