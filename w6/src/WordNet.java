import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Topological;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/* *****************************************************************************
 *
 *  Description:  WordNet implementation.
 *
 *
 **************************************************************************** */
public class WordNet {

    private final SAP sap;

    private final Map<Integer, String> wordMap = new HashMap<>();

    private final Map<String, Boolean> allWords = new HashMap<>();

    /**
     * constructor takes the name of the two input files.
     * @param synsetsPath
     * @param hypernymsPath
     */
    public WordNet(String synsetsPath, String hypernymsPath) {

        if (synsetsPath == null || hypernymsPath == null) {
            throw new IllegalArgumentException("Null argument exception");
        }

        In fSynsets = new In(synsetsPath);
        String[] allSynsets = fSynsets.readAllLines();
        for (String synset : allSynsets) {
            String[] synsetItem = synset.split(",");
            int synsetId = Integer.parseInt(synsetItem[0]);

            this.wordMap.put(synsetId, synsetItem[1]);

        }
        In fHypernyms = new In(hypernymsPath);

        String[] allHypernyms = fHypernyms.readAllLines();
        Digraph digraph = new Digraph(allSynsets.length);

        for (String hypernymStr : allHypernyms) {
            String[] hypernymItem = hypernymStr.split(",");
            int synsetId = Integer.parseInt(hypernymItem[0]);
            int count = 0;
            for (String hypernymDetail: hypernymItem) {
                if (count != 0) {
                    digraph.addEdge(synsetId, Integer.parseInt(hypernymDetail));
                }
                count++;
            }
        }

        int numOfRoots = 0;
        for (int vertId = 0; vertId < digraph.V(); vertId++) {
            if (digraph.outdegree(vertId) == 0) {
                numOfRoots++;
            }
        }
        if (numOfRoots > 1) {
            throw new IllegalArgumentException("It is not rooted DAG");
        }
        DirectedCycle directedCycle = new DirectedCycle(digraph);
        Topological topological = new Topological(digraph);
        if (directedCycle.hasCycle() || !topological.hasOrder()) {
            throw new IllegalArgumentException("It is not rooted DAG");
        }

        this.sap = new SAP(digraph);

        for (Map.Entry<Integer, String> wordEntry : this.wordMap.entrySet()) {
            String val = wordEntry.getValue();
            List<String> entryAsList = Arrays.asList(val.split(" "));
            for (String word : entryAsList) {
                allWords.put(word, Boolean.TRUE);
            }
        }
    }

    /**
     * returns all WordNet nouns
     * @return
     */
    public Iterable<String> nouns() {

        return allWords.keySet();
    }

    /**
     * is the word a WordNet noun?
     * @param word
     * @return
     */
    public boolean isNoun(String word) {
        if (word == null) {
            throw new IllegalArgumentException("Argument is null");
        }
        return allWords.containsKey(word);
    }

    /**
     * Find all available words entries for nounA and nounB
     * @param nounA - word
     * @param nounB - word
     * @return
     */
    private List<List<Integer>> getWordsEntries(String nounA, String nounB) {
        List<Integer> aList = new ArrayList<>();
        List<Integer> bList = new ArrayList<>();
        List<List<Integer>> bothLists = new ArrayList<>();
        for (Map.Entry<Integer, String> wordEntry : this.wordMap.entrySet()) {
            String val = wordEntry.getValue();
            List<String> entryAsList = Arrays.asList(val.split(" "));
            boolean foundA = entryAsList.contains(nounA);
            boolean foundB = entryAsList.contains(nounB);

            if (foundA) {
                aList.add(wordEntry.getKey());
            }
            if (foundB) {
                bList.add(wordEntry.getKey());
            }
        }
        bothLists.add(aList);
        bothLists.add(bList);

        return bothLists;
    }

    /**
     * distance between nounA and nounB (defined below).
     *
     * @param nounA - word
     * @param nounB - word
     * @return
     */
    public int distance(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException("The noun is not a WordNet noun");
        }

        List<List<Integer>> wordsEntries = getWordsEntries(nounA, nounB);

        return this.sap.length(wordsEntries.get(0), wordsEntries.get(1));
    }

    /**
     *  a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB in a shortest ancestral.
     *  path (defined below)
     * @param nounA - word
     * @param nounB - word
     * @return
     */
    public String sap(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException("The noun is not a WordNet noun");
        }
        List<List<Integer>> wordsEntries = getWordsEntries(nounA, nounB);
        int ancestorId = this.sap.ancestor(wordsEntries.get(0), wordsEntries.get(1));

        if (ancestorId != -1) {
            return this.wordMap.get(ancestorId);
        }
        return "";
    }
}