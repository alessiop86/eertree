package alessiop86.algorithms;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class PalindromeNode {

    public static final int INDEX_IMAGINARY_STRING = 0;
    public static final int INDEX_EMPTY_STRING = 1;

    String label;
    int index;
    PalindromeNode longestProperSuffixPalindrome;
    Map<Character,PalindromeNode> outgoingNodes = new HashMap<>();

    public int getPalindromeLength() {
        if (index == INDEX_IMAGINARY_STRING) {
            return -1;
        } else {
            return label.length();
        }
    }

    /**
     * Constructor to create a new PalindromeNode, representing a (distinct) Palindrome contained in the original string
     */
    public PalindromeNode(int index, PalindromeNode suffix, char addedLetter) {
        this(index, suffix);
        if (suffix.index == INDEX_IMAGINARY_STRING) {
            this.label = "" + addedLetter;
        } else {
            this.label = addedLetter + suffix.label + addedLetter;
        }
    }

    private PalindromeNode(int index, PalindromeNode suffix) {
        this.longestProperSuffixPalindrome = suffix;
        this.index = index;
    }
    /**
     * Factory method to create a special PalindromeNode representing the Empty String
     */
    public static PalindromeNode emptyString(PalindromeNode imaginaryString) {
        PalindromeNode emptyString = new PalindromeNode(INDEX_EMPTY_STRING, imaginaryString);
        emptyString.label="";
        return emptyString;
    }

    /**
     * Factory method to create a special PalindromeNode representing the Imaginary String
     */
    public static PalindromeNode imaginaryString() {
        PalindromeNode imaginaryString = new PalindromeNode(INDEX_IMAGINARY_STRING, null);
        imaginaryString.label="-1";
        imaginaryString.longestProperSuffixPalindrome=imaginaryString;
        return imaginaryString;
    }

    @Override
    public String toString() {
        String outgoingNodesCompressedFormat = outgoingNodes.values()
                .stream()
                .map(node -> node.label)
                .collect(Collectors.joining(", "));
        return String.format("PalindromeNode[Label='%s', Length=%d, OutgoingNodes=%d:[%s], LongestPalindromeSuffix=%s\n",
                label, getPalindromeLength(), outgoingNodes.size(), outgoingNodesCompressedFormat, longestProperSuffixPalindrome.label);
    }
}