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
    Map<Character,PalindromeNode> outgoingNodes = new HashMap<>();

    /**
     * Constructor to create a new PalindromeNode, representing a (distinct) Palindrome contained in the original string
     */
    public PalindromeNode(int index, PalindromeNode longestPalindromeSuffix, char addedLetter) {
        this(index, longestPalindromeSuffix);
        if (longestPalindromeSuffix.isImaginaryStringPalindromeNode()) {
            this.label = "" + addedLetter;
        } else {
            this.label = addedLetter + longestPalindromeSuffix.label + addedLetter;
        }
    }

    protected PalindromeNode(int index, PalindromeNode longestPalindromeSuffix) {
        this.longestPalindromeSuffix = longestPalindromeSuffix;
        this.setIndex(index);
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
        return String.format("PalindromeNode[Label='%s', Length=%d, OutgoingNodes=%d:[%s], LongestPalindromeSuffix=%s\n",
                label, getPalindromeLength(), outgoingNodes.size(), outgoingNodesCompressedFormat, getLongestPalindromeSuffix().getLabel());
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
}