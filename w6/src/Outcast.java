/* *****************************************************************************
 *
 *  Description:  Outcast implementation.
 *
 *
 **************************************************************************** */
public class Outcast {

    private final WordNet wordNet;
    /**
     *  constructor takes a WordNet object.
     *
     * @param wordnet - WordNet class instance
     */
    public Outcast(WordNet wordnet) {
        this.wordNet = wordnet;
    }

    /**
     * given an array of WordNet nouns, return an outcast.
     *
     * @param nouns
     * @return
     */
    public String outcast(String[] nouns) {

        int maxDist = 0;
        String outcast = nouns[0];
        int currDist;

        for (int i = 0;  i < nouns.length; i++) {
            currDist = 0;
            for (int j = 0; j < nouns.length; j++) {
                if (i == j) {
                    continue;
                }
                currDist += wordNet.distance(nouns[i], nouns[j]);
            }

            if (currDist > maxDist) {
                maxDist = currDist;
                outcast = nouns[i];
            }
        }
        return outcast;
    }
}
