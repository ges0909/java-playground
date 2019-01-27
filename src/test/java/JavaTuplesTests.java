import org.javatuples.Pair;
import org.javatuples.Quartet;
import org.javatuples.Triplet;
import org.javatuples.Unit;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class JavaTuplesTests {

    @Test
    void testUnit() {
        final Pair<String, Integer> pair = Pair.with("john", 32);
        final Unit<Integer> unit = pair.removeFrom0();
        assertThat(unit.contains(32)).isEqualTo(true);
    }

    @Test
    void testPair() {
        final Pair<String, Integer> pair = new Pair<>("A pair", 55);
        assertThat(pair.getValue0()).isEqualTo("A pair");
        assertThat(pair.getValue1()).isEqualTo(55);
    }

    @Test
    void testPair2() {
        final List<String> listOfNames = Arrays.asList("john", "doe", "anne", "alex");
        final Pair<String, String> pairFromList = Pair.fromIterable(listOfNames, 2);
        assertThat(pairFromList.getValue0()).isEqualTo("anne");
        assertThat(pairFromList.getValue1()).isEqualTo("alex");
    }

    @Test
    void testPair3() {
        final Pair<String, Integer> john = Pair.with("john", 32);
        final Pair<String, Integer> alex = john.setAt0("alex");
        assertThat(john.toString()).isNotEqualTo(alex.toString());
    }

    @Test
    void testTriplet() {
        final String[] names = new String[]{"john", "doe", "anne"};
        final Triplet<String, String, String> triplet = Triplet.fromArray(names);
        assertThat(triplet.getValue0()).isEqualTo("john");
        assertThat(triplet.getValue1()).isEqualTo("doe");
        assertThat(triplet.getValue2()).isEqualTo("anne");
    }

    @Test
    void testTriplet2() {
        final Pair<String, Integer> pair = Pair.with("john", 32);
        final Triplet<String, Integer, String> triplet = pair.add("1051 SW");
        assertThat(triplet.contains("john")).isEqualTo(true);
        assertThat(triplet.contains(32)).isEqualTo(true);
        assertThat(triplet.contains("1051 SW")).isEqualTo(true);
    }

    @Test
    void testTriplet3() {
        final Pair<String, Integer> pair = Pair.with("john", 32);
        final Triplet<String, String, Integer> triplet = pair.addAt1("1051 SW");
        assertThat(triplet.indexOf("john")).isEqualTo(0);
        assertThat(triplet.indexOf("1051 SW")).isEqualTo(1);
        assertThat(triplet.indexOf(32)).isEqualTo(2);
    }

    @Test
    void testQuartet() {
        final List<String> listOfNames = Arrays.asList("john", "doe", "anne", "alex");
        final Quartet<String, String, String, String> quartet = Quartet.fromCollection(listOfNames);
        assertThat(quartet.getValue0()).isEqualTo("john");
        assertThat(quartet.getValue1()).isEqualTo("doe");
        assertThat(quartet.getValue2()).isEqualTo("anne");
        assertThat(quartet.getValue3()).isEqualTo("alex");
    }

    @Test
    void testQuartet2() {
        final Quartet<String, Double, Integer, String> quartet = Quartet.with("john", 72.5, 32, "1051 SW");
        final String name = quartet.getValue0();
        final Integer age = quartet.getValue2();
        assertThat(name).isEqualTo("john");
        assertThat(age).isEqualTo(32);
        assertThat(age).isEqualTo(32);
    }

    @Test
    void testQuartet3() {
        final Pair<String, Integer> pair1 = Pair.with("john", 32);
        final Pair<String, Integer> pair2 = Pair.with("alex", 45);
        final Quartet<String, Integer, String, Integer> quartet = pair1.add(pair2);
        assertThat(quartet.containsAll(pair1)).isEqualTo(true);
        assertThat(quartet.containsAll(pair2)).isEqualTo(true);
    }

    @Test
    void testQuartet4() {
        final Pair<String, Integer> pair = Pair.with("john", 32);
        final Quartet<String, Integer, String, Integer> quartet = pair.add("alex", 45);
        assertThat(quartet.containsAll("alex", "john", 32, 45)).isEqualTo(true);
    }

    // convert tuple to list
    @Test
    void testQuartet5() {
        final Quartet<String, Double, Integer, String> quartet = Quartet.with("john", 72.5, 32, "1051 SW");
        final List<Object> list = quartet.toList();
        assertThat(list.size()).isEqualTo(4);
    }

    // convert tuple to array
    @Test
    void testQuartet6() {
        final Quartet<String, Double, Integer, String> quartet = Quartet.with("john", 72.5, 32, "1051 SW");
        final Object[] array = quartet.toArray();
        assertThat(array.length).isEqualTo(4);
    }
}
