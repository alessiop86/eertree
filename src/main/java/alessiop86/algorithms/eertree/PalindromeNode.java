package alessiop86.algorithms.eertree;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class PalindromeNode {

    private String label;
    private int index;
    private PalindromeNode longestPalindromeSuffix;
    //Outgoing nodes representing the palindromes xAx that can be obtained adding a letter x to the current PalindromeNode A
    //(If the current PalindromeNode
    private Map<Character,PalindromeNode> outgoingNodes = new HashMap<>();
    protected PalindromeNode(int index) {
        this.index = index;
    }

    public boolean isImaginaryStringPalindromeNode() {
        return false;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getPalindromeLength() {
        return label.length();
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public PalindromeNode getLongestPalindromeSuffix() {
        return longestPalindromeSuffix;
    }

    public void setLongestPalindromeSuffix(PalindromeNode longestPalindromeSuffix) {
        this.longestPalindromeSuffix = longestPalindromeSuffix;
    }


    /**
     * Not required by the algorithm, it is just for outputting the Eertree in a human-friendly fashion
     */
    @Override
    public String toString() {
        String outgoingNodesCompressedFormat = outgoingNodes.values()
                .stream()
                .map(node -> node.label)
                .collect(Collectors.joining(", "));
        return String.format("PalindromeNode[Label='%s', Length=%d, OutgoingNodes=[%s], LongestPalindromeSuffix=%s\n",
                label, getPalindromeLength(), outgoingNodesCompressedFormat, getLongestPalindromeSuffix().getLabel());
    }

    public Map<Character, PalindromeNode> getOutgoingNodes() {
        return outgoingNodes;
    }

    public void setOutgoingNodes(Map<Character, PalindromeNode> outgoingNodes) {
        this.outgoingNodes = outgoingNodes;
    }
}