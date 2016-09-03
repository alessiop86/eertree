package alessiop86.algorithms;

import java.util.ArrayList;
import java.util.List;

public class Eertree {

    private final char[] string;
    private List<PalindromeNode> tree = new ArrayList<>();
    private int longestPalindromeSuffixNodeIndex;

    public Eertree(String str) {
        string = str.toCharArray();
    }

    void initTree() {
        PalindromeNode imaginaryString = PalindromeNode.imaginaryString();
        tree.add(imaginaryString.index, imaginaryString);

        PalindromeNode emptyString = PalindromeNode.emptyString(imaginaryString);
        tree.add(emptyString.index, emptyString);

        longestPalindromeSuffixNodeIndex = emptyString.index;
    }

    private void build() {
        initTree();
        for (int i = 0; i < string.length ; i++) {
            addLetter(i);
        }
        System.out.println(tree);
    }

    /**
     * Returning true or false if a new palindrome has been added to the eertree. It is not really used in this example
     */
    public boolean addLetter(int letterIndex) {
        int cursorLargestSuffixPalindrome = longestPalindromeSuffixNodeIndex;
        char letter = string[letterIndex];

        while (necessaryToMoveTheCursorToAShorterPalindromeSuffix(letterIndex, cursorLargestSuffixPalindrome, letter)) {
            cursorLargestSuffixPalindrome = tree.get(cursorLargestSuffixPalindrome).longestProperSuffixPalindrome.index;
            //I go back until I find an appropriate node to which add the letter, ultimately ending up with the main node
            //cursorLargestSuffixPalindrome = tree[cursorLargestSuffixPalindrome].largestProperSuffixPalindromeNodeIndex;
        }

        if (tree.get(cursorLargestSuffixPalindrome).outgoingNodes.containsKey(letter)) {//if (tree[cursorLargestSuffixPalindrome].next[let] != 0) {
            longestPalindromeSuffixNodeIndex = tree.get(cursorLargestSuffixPalindrome).outgoingNodes.get(letter).index;//  longestPalindromeSuffixNodeIndex = tree[cursorLargestSuffixPalindrome].next[let];
            return false; //no need to add already there
        }

        int nextNodeIndex = tree.size();
        PalindromeNode newNode = new PalindromeNode(nextNodeIndex, tree.get(cursorLargestSuffixPalindrome), letter);

        tree.add(newNode);
        tree.get(cursorLargestSuffixPalindrome).outgoingNodes.put(letter,newNode);

        longestPalindromeSuffixNodeIndex = newNode.index;
        return true;
    }

    private boolean necessaryToMoveTheCursorToAShorterPalindromeSuffix(int pos, int cursorLargestSuffixPalindrome, char letter) {
        int index = pos - tree.get(cursorLargestSuffixPalindrome).getPalindromeLength() - 1;
        return index < 0 || letter != string[index];
    }

    public static void main(String[] args) {
        String str = "eertree";
        Eertree eertree = new Eertree(str);
        eertree.build();
    }

}
