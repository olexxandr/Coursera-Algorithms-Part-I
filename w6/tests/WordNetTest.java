import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/* *****************************************************************************
 *
 *  Description:  WordNetTest implementation.
 *
 *
 **************************************************************************** */
public class WordNetTest {

    public WordNet createInstance() throws IOException {

        String hypernyms = "resources/hypernyms.txt";
        String synsets = "resources/synsets.txt";

        return new WordNet(synsets, hypernyms);
    }

    @Test
    public void nouns() throws Exception {
        WordNet wordNet = createInstance();
        Iterable<String> iterable = wordNet.nouns();
        ArrayList<String> s = new ArrayList<>();
        Iterator<String> iterator = iterable.iterator();
        while (iterator.hasNext()) {
            s.add(iterator.next());
        }
        Assert.assertTrue(s.size() == 119188);
    }

    @Test
    public void isNoun() throws Exception {
        WordNet wordNet = createInstance();
        boolean isNoun = wordNet.isNoun("worm");
        Assert.assertEquals(true, isNoun);

        isNoun = wordNet.isNoun("not_a_word");
        Assert.assertEquals(false, isNoun);
    }

    @Test
    public void distance() throws Exception {
        WordNet wordNet = createInstance();
        int distance = wordNet.distance("worm", "bird");
        Assert.assertEquals(5, distance);


        distance = wordNet.distance("white_marlin", "mileage");
        Assert.assertEquals(23, distance);

        distance = wordNet.distance("Black_Plague", "black_marlin");
        Assert.assertEquals(33, distance);

        distance = wordNet.distance("American_water_spaniel", "histology");
        Assert.assertEquals(27, distance);

        distance = wordNet.distance("Brown_Swiss", "barrel_roll");
        Assert.assertEquals(29, distance);

        distance = wordNet.distance("individual", "edible_fruit");
        Assert.assertEquals(7, distance);
    }

    @Test
    public void test2() throws Exception {
        String hypernyms = "resources/hypernyms15Path.txt";
        String synsets = "resources/synsets15.txt";

        WordNet wordNet = new WordNet(synsets, hypernyms);
        String sap = wordNet.sap("o", "f");
        Assert.assertEquals("o", sap);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test8HypernymsInvalidCycle() throws Exception {
        String hypernyms = "resources/hypernyms3InvalidCycle.txt";
        String synsets = "resources/synsets3.txt";
        WordNet wordNet = new WordNet(synsets, hypernyms);

    }

    @Test(expected = IllegalArgumentException.class)
    public void test8HypernymsInvalidTwoRoots() throws Exception {
        String hypernyms = "resources/hypernyms3InvalidTwoRoots.txt";
        String synsets = "resources/synsets3.txt";
        WordNet wordNet = new WordNet(synsets, hypernyms);
    }

    @Test
    public void sap() throws Exception {
        WordNet wordNet = createInstance();
        String sap = wordNet.sap("worm", "bird");
        Assert.assertEquals("animal animate_being beast brute creature fauna", sap);
    }
}