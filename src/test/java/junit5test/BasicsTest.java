package junit5test;

import org.junit.jupiter.api.*;

import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit5 examples.
 *
 * <ul>
 * <li>run tests on command line with <code>$ ./gradlew test</code></li>
 * <li>either test classes nor test methods need to be <code>public</code></li>
 * <li>generate this <i>Javadoc</i> in <i>Eclipse</i> with
 * <code>Export... &gt; Javadoc</code></li>
 * </ul>
 *
 * @see <a href= "http://www.ibm.com/developerworks/library/j-introducing-junit5-part1-jupiter-api/index.html">Introducing
 * JUnit 5</a>
 */
class BasicsTest {
    final static Logger log = Logger.getLogger(BasicsTest.class.getName());

    @BeforeAll
    static void init() { // class method, therefore has to be static
        log.finest("@BeforeAll");
    }

    @AfterAll
    static void done() { // class method, therefore has to be static
        log.finest("@AfterAll");
    }

    @BeforeEach
    void setUp() {
        log.finest("@BeforeEach");
    }

    @AfterEach
    void tearDown() {
        log.finest("@AfterEach");
    }

    @DisplayName("@Disabled")
    @Disabled
    @Test
    void testDisabled() {
        assert 1 == 0 : "This test fails always.";
    }

    /**
     * Shows the naming of tests.
     */
    @DisplayName("@DisplayName")
    @Test
    void testSimple() {
        String actual = "simple";
        assertNotNull(actual);
        assertEquals("simple", actual);
    }

    @DisplayName("TestInfo")
    @Test
    void testSimple(TestInfo info /* test meta data */) {
        String actual = "simple";
        assertEquals("TestInfo", info.getDisplayName());
        assertTrue("simple".equals(actual));
    }

    @DisplayName("@Tag")
    @Tag("prod")
    @Tag("dev")
    @Test
    void testTags(TestInfo info) {
        String actual = "simple";
        assertNotNull(actual);
        assertEquals("simple", actual);
        Set<String> tags = info.getTags();
        assertThat(tags, contains("prod", "dev"));
    }

    @DisplayName("@DisplayName")
    @Test
    void testSimpleWithLambda() {
        String actual = "simple";
        assertNotNull(actual, () -> "null instance");
        assertEquals("simple", actual, () -> "string compare failed");
    }

    @DisplayName("@Nested")
    @Nested
    class AssertTests {

        @BeforeEach
        void setUp() {
            log.finest("AssertTests // @BeforeEach");
        }

        @AfterEach
        void tearDown() {
            log.finest("AssertTests // @AfterEach");
        }

        @Test
        @DisplayName("assertAll: grouping of assertions")
        /**
         * Shows the the usage of <code>assertAll</code>.
         *
         * All of the assertions contained within it are performed, even if one or more
         * of them fail. This is in contrast to single assertion statements, where the
         * test will fail at the point, where the first assertion fails.
         */
        void testAssertAll() {
            String actual = "simple";
            assertAll("any error message", () -> assertNotNull(actual), () -> assertEquals("simple", actual),
                    () -> assertTrue("simple".equals(actual)));
        }

        @Test
        @DisplayName("assertTrue: with lambda")
        void testAssertWithLambda() {
            assertTrue(Stream.of(1, 2, 3).mapToInt(i -> i).sum() > 5, () -> "sum should be greater than 5");
        }

        @Test
        @DisplayName("assertThrows: expected exceptions")
        @SuppressWarnings("null")
        void testAssertThrows() {
            String s = null;
            assertThrows(NullPointerException.class, () -> s.length(), "NPE caught"); // provoke NPE
        }
    }

}
