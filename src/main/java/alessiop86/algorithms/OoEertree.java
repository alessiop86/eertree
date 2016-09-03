package alessiop86.algorithms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OoEertree {

    class PalindromeNode {
        String label;
        int index;
        int palindromeLength;
        PalindromeNode longestProperSuffixPalindrome;
        Map<Character,PalindromeNode> outgoingNodes = new HashMap<>();

        @Override
        public String toString() {
            String outgoingNodesCompressedFormat = outgoingNodes.values()
                                                                .stream()
                                                                .map(node -> node.label)
                                                                .collect(Collectors.joining(", "));
            return String.format("PalindromeNode[Label=%s,Length=%d,OutgoingNodes=%d:[%s],LongestPalindromeSuffix=%s\n",
                                label, palindromeLength, outgoingNodes.size(), outgoingNodesCompressedFormat, longestProperSuffixPalindrome.label);
        }
    }

    char[] s;
    List<PalindromeNode> tree = new ArrayList<>();
    int biggerIndexOfPalindromeThatIsMaxSuffixPalindrome;

    public OoEertree(String str) {
        s = str.toCharArray();
    }



    int INDEX_IMAGINARY_STRING = 0;
    int INDEX_EMPTY_STRING = 1;

    void initTree() {
        biggerIndexOfPalindromeThatIsMaxSuffixPalindrome = INDEX_EMPTY_STRING; // index o

        PalindromeNode imaginaryString = new PalindromeNode();
        imaginaryString.palindromeLength=-1;
        imaginaryString.label="-1";
        imaginaryString.index= INDEX_IMAGINARY_STRING;
        imaginaryString.longestProperSuffixPalindrome=imaginaryString;
        tree.add(INDEX_IMAGINARY_STRING, imaginaryString);

        PalindromeNode emptyString = new PalindromeNode();
        emptyString.palindromeLength=0;
        emptyString.label="";
        emptyString.index=INDEX_EMPTY_STRING;
        emptyString.longestProperSuffixPalindrome=imaginaryString;
        tree.add(INDEX_EMPTY_STRING, emptyString);
    }

    public boolean addLetter(int letterIndex) {
        int cursorLargestSuffixPalindrome = biggerIndexOfPalindromeThatIsMaxSuffixPalindrome;
        char letter = s[letterIndex];

        while (necessaryToMoveTheCursorToAShorterPalindromeSuffix(letterIndex, cursorLargestSuffixPalindrome, letter)) {
            cursorLargestSuffixPalindrome = tree.get(cursorLargestSuffixPalindrome).longestProperSuffixPalindrome.index;
            //I go back until I find an appropriate node to which add the letter, ultimately ending up with the main node
            //cursorLargestSuffixPalindrome = tree[cursorLargestSuffixPalindrome].largestProperSuffixPalindromeNodeIndex;

        }

        if (tree.get(cursorLargestSuffixPalindrome).outgoingNodes.containsKey(letter)) {//if (tree[cursorLargestSuffixPalindrome].next[let] != 0) {
            biggerIndexOfPalindromeThatIsMaxSuffixPalindrome = tree.get(cursorLargestSuffixPalindrome).outgoingNodes.get(letter).index;//  biggerIndexOfPalindromeThatIsMaxSuffixPalindrome = tree[cursorLargestSuffixPalindrome].next[let];
            return false; //no need to add already there
        }

        PalindromeNode newNode = new PalindromeNode();

        newNode.longestProperSuffixPalindrome = tree.get(cursorLargestSuffixPalindrome);
        newNode.index = tree.size();

        if (cursorLargestSuffixPalindrome == INDEX_IMAGINARY_STRING) {
            newNode.label = "" + letter;
        }
        else {
            newNode.label = letter + tree.get(cursorLargestSuffixPalindrome).label + letter;
        }
        newNode.palindromeLength = newNode.label.length();
        tree.add(newNode);
        tree.get(cursorLargestSuffixPalindrome).outgoingNodes.put(letter,newNode);

        biggerIndexOfPalindromeThatIsMaxSuffixPalindrome = newNode.index;
        return true;
    }

    private boolean necessaryToMoveTheCursorToAShorterPalindromeSuffix(int pos, int cursorLargestSuffixPalindrome, char letter) {
        int index = pos - tree.get(cursorLargestSuffixPalindrome).palindromeLength - 1;
        return index < 0 || letter != s[index];
    }


    public static void main(String[] args) {
        String str = "eertree";
        OoEertree eertree = new OoEertree(str);
        eertree.build();
    }

    private void build() {
        initTree();
        for (int i = 0; i < s.length ; i++) {
            addLetter(i);
        }
        System.out.println(tree);
    }
}
