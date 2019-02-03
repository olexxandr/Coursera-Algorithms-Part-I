import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


/* *****************************************************************************
 *
 *  Description:  SAPTest implementation.
 *
 *
 **************************************************************************** */
public class SAPTest {

    @Test
    public void ancestor() throws Exception {
        In in = new In("resources/digraph1.txt");
        Digraph g = new Digraph(in);
        SAP sap = new SAP(g);
        int v = 3;
        int w = 11;
        int ancestor = sap.ancestor(v, w);
        Assert.assertEquals(1, ancestor);

        v = 9;
        w = 12;
        ancestor = sap.ancestor(v, w);
        Assert.assertEquals(5, ancestor);

        v = 7;
        w = 2;
        ancestor = sap.ancestor(v, w);
        Assert.assertEquals(0, ancestor);

        v = 1;
        w = 6;
        ancestor = sap.ancestor(v, w);
        Assert.assertEquals(-1, ancestor);
    }


    @Test
    public void length() throws Exception {
        In in = new In("resources/digraph1.txt");
        Digraph g = new Digraph(in);
        SAP sap = new SAP(g);
        int v = 3;
        int w = 11;
        int lenght = sap.length(v, w);
        Assert.assertEquals(4, lenght);

        v = 9;
        w = 12;
        lenght = sap.length(v, w);
        Assert.assertEquals(3, lenght);

        v = 7;
        w = 2;
        lenght = sap.length(v, w);
        Assert.assertEquals(4, lenght);

        v = 1;
        w = 6;
        lenght = sap.length(v, w);
        Assert.assertEquals(-1, lenght);
    }

    @Test
    public void test1() throws Exception {
        In in = new In("resources/digraph3.txt");
        Digraph g = new Digraph(in);
        SAP sap = new SAP(g);
        int v = 7;
        int w = 14;


        int lenght, count = 0;

        while (count < 225) {
            lenght = sap.length(v, w);
            Assert.assertEquals(5, lenght);
            count++;
        }

        in = new In("resources/digraph2.txt");
        g = new Digraph(in);
        sap = new SAP(g);
        v = 0;
        w = 1;
        count = 0;
        while (count < 36) {
            lenght = sap.length(v, w);
            Assert.assertEquals(1, lenght);
            count++;
        }
        in = new In("resources/digraph5.txt");
        g = new Digraph(in);
        sap = new SAP(g);
        v = 13;
        w = 8;
        count = 0;
        while (count < 484) {
            lenght = sap.length(v, w);
            Assert.assertEquals(3, lenght);
            count++;
        }
    }

    @Test
    public void test2() throws Exception {
        In in = new In("resources/digraph-wordnet.txt");
        Digraph g = new Digraph(in);
        SAP sap = new SAP(g);
        int v = 61500, w = 60452;

        int lenght = sap.length(v, w);
        Assert.assertEquals(16, lenght);
    }

    @Test
    public void test14() throws Exception {
        In in = new In("resources/digraph2.txt");
        Digraph g = new Digraph(in);
        SAP sap1 = new SAP(g);
        SAP sap2 = new SAP(g);
        int v = 0, w = 1;

        int lenght1, lenght2;
        lenght1 = sap1.length(v, w);
        lenght2 = sap2.length(v, w);
        StdOut.printf(" length1 digraph2.txt = %d\n", lenght1);
        StdOut.printf(" length2 digraph2.txt = %d\n", lenght2);
        Assert.assertEquals(1, lenght1);
        Assert.assertEquals(lenght1, lenght2);


        in = new In("resources/digraph5.txt");
        g = new Digraph(in);
        sap1 = new SAP(g);
        sap2 = new SAP(g);
        v = 0;
        w = 7;

        lenght1 = sap1.length(v, w);
        lenght2 = sap2.length(v, w);
        Assert.assertEquals(3, lenght1);
        Assert.assertEquals(lenght1, lenght2);
    }

    @Test
    public void length1() throws Exception {
        In in = new In("resources/digraph25.txt");
        Digraph g = new Digraph(in);
        SAP sap = new SAP(g);
        List<Integer> v = new ArrayList<>();
        v.add(13);
        v.add(23);
        v.add(24);

        List<Integer> w = new ArrayList<>();
        w.add(6);
        w.add(16);
        w.add(17);
        int length = sap.length(v, w);
        Assert.assertEquals(4, length);
    }

    @Test
    public void ancestor1() throws Exception {
        In in = new In("resources/digraph25.txt");
        Digraph g = new Digraph(in);
        SAP sap = new SAP(g);
        List<Integer> v = new ArrayList<>();
        v.add(13);
        v.add(23);
        v.add(24);

        List<Integer> w = new ArrayList<>();
        w.add(6);
        w.add(16);
        w.add(17);
        int ancestor = sap.ancestor(v, w);
        Assert.assertEquals(3, ancestor);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ancestor19() {
        In in = new In("resources/digraph25.txt");
        Digraph g = new Digraph(in);
        SAP sap = new SAP(g);
        List<Integer> v = new ArrayList<>();
        v.add(1);
        v.add(2);
        v.add(6);
        v.add(9);
        v.add(12);

        List<Integer> w = new ArrayList<>();
        w.add(0);
        w.add(5);
        w.add(7);
        w.add(8);
        w.add(null);
        boolean hasException = false;

        int ancestor = sap.ancestor(v, w);
    }
}