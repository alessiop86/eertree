package alessiop86.algorithms.eertree;

import static alessiop86.algorithms.eertree.EmptyStringPalindromeNode.INDEX_EMPTY_STRING;

import java.util.ArrayList;
import java.util.List;

public class Eertree {

    private final char[] string;
    //backing data structure for eertree nodes, an ArrayList for convenience, but it could also have been an array or an HashMap
    private List<PalindromeNode> tree = new ArrayList<>();
    private int currentLongestPalindromeSuffixNodeIndex;

    public Eertree(String str) {
        string = str.toCharArray();
    }

    void initTree() {
        ImaginaryStringPalindromeNode imaginaryString = new ImaginaryStringPalindromeNode();
        tree.add(imaginaryString.getIndex(), imaginaryString);

        PalindromeNode emptyString = new EmptyStringPalindromeNode(imaginaryString);
        tree.add(emptyString.getIndex(), emptyString);

        currentLongestPalindromeSuffixNodeIndex = emptyString.getIndex();
    }

    private void build() {
        initTree();
        for (int i = 0; i < string.length ; i++) {
            addLetter(i);
        }
        System.out.println(tree);
    }

    /**
     * Returning true or false if a new palindrome has been added to the eertree.
     * This returned boolean is not used in this example but it could be useful depending on the problem to which
     * the eertree is applied
     */
    public boolean addLetter(int letterIndex) {
        char letter = string[letterIndex];
        PalindromeNode longestPalindromeSuffixForNextPalindrome = getLongestPalindromeSuffixForNextPalindromeNode(letterIndex, letter);


        if (isDuplicatePalindrome(letter, longestPalindromeSuffixForNextPalindrome)) {
            //in this version of the algorithm (from the official paper) we do not keep track of duplicate palindromes.
            //If the problem where the Eertree requires it (e.g. to find out the total number of palindromes including duplicates),
            //you could need to keep track of the duplicate palindromes with a counter inside the PalindromeNode class
            currentLongestPalindromeSuffixNodeIndex = longestPalindromeSuffixForNextPalindrome.getOutgoingNodes().get(letter).getIndex();
            return false;
        }

        int nextNodeIndex = tree.size(); //current max node index is tree.size() - 1
        PalindromeNode newNode = new PalindromeNode(nextNodeIndex, longestPalindromeSuffixForNextPalindrome, letter);
        addPalindromeNode(newNode, letter);
        return true;
    }


    private void addPalindromeNode(PalindromeNode newNode, char addedLetter) {
        tree.add(newNode);

        //adding the edge connecting the longestPalindromeSuffix to the new PalindromeNode
        newNode.getLongestPalindromeSuffix().getOutgoingNodes().put(addedLetter, newNode);

        //suffix reference to imaginary string is not allowed, change it to the empty string
        if (newNode.getLongestPalindromeSuffix().isImaginaryStringPalindromeNode()) {
            newNode.setLongestPalindromeSuffix(tree.get(INDEX_EMPTY_STRING));
        }

        currentLongestPalindromeSuffixNodeIndex = newNode.getIndex();
    }

    //if there is already an outgoing node from the longestPalindromeSuffix with the same letter, it means
    //there is already a duplicate of the palindrome we have just found.
    private boolean isDuplicatePalindrome(char letter, PalindromeNode longestPalindromeSuffixForNextPalindrome) {
        return longestPalindromeSuffixForNextPalindrome.getOutgoingNodes().containsKey(letter);
    }

    //I start with the longest palindrome suffix added to the eertree, then I go back traversing the eertree using
    //the PalindromeNode.getLongestPalindromeSuffix() references, until I find an appropriate node to which add the letter
    //or until i hit the imaginary string.
    private PalindromeNode getLongestPalindromeSuffixForNextPalindromeNode(int letterIndex, char letter) {
        PalindromeNode longestPalindromeSuffixForNextPalindrome = tree.get(currentLongestPalindromeSuffixNodeIndex);
        while (isNecessaryToMoveTheCursorToAShorterPalindromeSuffix(letterIndex, letter, longestPalindromeSuffixForNextPalindrome)) {
            longestPalindromeSuffixForNextPalindrome = tree.get(longestPalindromeSuffixForNextPalindrome.getLongestPalindromeSuffix().getIndex());
        }
        return longestPalindromeSuffixForNextPalindrome;
    }

    private boolean isNecessaryToMoveTheCursorToAShorterPalindromeSuffix(int letterIndex, char letter, PalindromeNode longestSuffixPalindrome) {
        int index = letterIndex - longestSuffixPalindrome.getPalindromeLength() - 1;
        return index < 0 || letter != string[index];
    }

    public static void main(String[] args) {
        String str = "eertree";
        Eertree eertree = new Eertree(str);
        eertree.build();
    }

}
