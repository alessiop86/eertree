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

    /**
     * Constructor to create a new PalindromeNode, representing a Palindrome contained in the original string.
     */
    public PalindromeNode(int index, PalindromeNode longestPalindromeSuffix, char addedLetter) {
        this(index, longestPalindromeSuffix);
        if (longestPalindromeSuffix.isImaginaryStringPalindromeNode()) {
            //we are ignoring the imaginary suffix (centered at the position -1 of the string) and what comes before
            // the suffix (i.e. an occurrence of addedLetter at the left of the imaginary string,
            // and we just add a new palindrome of length 1 and content 'addedLetter'
            this.label = "" + addedLetter;
        } else {
            this.label = addedLetter + longestPalindromeSuffix.label + addedLetter;
        }
    }

    protected PalindromeNode(int index, PalindromeNode longestPalindromeSuffix) {
        this.longestPalindromeSuffix = longestPalindromeSuffix;
        this.setIndex(index);
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