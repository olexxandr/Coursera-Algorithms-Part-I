import org.junit.Assert;
import org.junit.Test;
import java.io.IOException;

/* *****************************************************************************
 *
 *  Description:  OutcastTest implementation.
 *
 *
 **************************************************************************** */
public class OutcastTest {
    public WordNet createInstance() throws IOException {

        String hypernyms = "resources/hypernyms.txt";
        String synsets = "resources/synsets.txt";

        return new WordNet(synsets, hypernyms);
    }

    @Test
    public void outcast() throws Exception {
        Outcast outcast = new Outcast(createInstance());

        String outcastWord = outcast.outcast(new String[] {"horse", "zebra", "cat", "bear", "table"});
        Assert.assertEquals("table", outcastWord);

        outcastWord = outcast.outcast(new String[] {"water", "soda",
                "bed", "orange_juice", "milk",  "apple_juice", "tea",  "coffee"});
        Assert.assertEquals("bed", outcastWord);

        outcastWord = outcast.outcast(new String[] {"apple", "pear", "peach", "banana", "lime",  "lemon",
                "blueberry", "strawberry", "mango", "watermelon", "potato"});
        Assert.assertEquals("potato", outcastWord);

    }
}