package alessiop86.algorithms.eertree;

import java.util.ArrayList;
import java.util.List;

import static alessiop86.algorithms.eertree.EmptyStringPalindromeNode.INDEX_EMPTY_STRING;

public class Eertree {

    private final char[] string;
    //backing data structure for eertree nodes, an ArrayList for convenience, but it could also have been an array or an HashMap
    private List<PalindromeNode> tree = new ArrayList<>();
    private int currentLongestPalindromeSuffixNodeIndex;

    public Eertree(String str) {
        string = str.toCharArray();
    }

    private void initTree() {
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
        Insertion insertion = new Insertion(letterIndex);
        PalindromeNode longestPalindromePrefix = getLongestPalindromePrefixForNextPalindromeNode(insertion);

        if (isDuplicatePalindrome(insertion.letter, longestPalindromePrefix)) {
            //in this version of the algorithm (from the official paper) we do not keep track of duplicate palindromes.
            //If the problem requires it (e.g. to find out the total number of palindromes including duplicates),
            //you could need to keep track of the duplicate palindromes with a counter inside the PalindromeNode class
            currentLongestPalindromeSuffixNodeIndex = longestPalindromePrefix.getOutgoingNodes().get(insertion.letter).getIndex();
            return false;
        }

        addNewPalindromeNode(longestPalindromePrefix, insertion);
        return true;
    }


    private void addNewPalindromeNode(PalindromeNode longestPalindromePrefix, Insertion insertion) {
        int nextNodeIndex = tree.size(); //current max node index is tree.size() - 1
        //I create a PalindromeNode with a null suffix, I'll update that later
        PalindromeNode newNode = new PalindromeNode(nextNodeIndex);
        if (longestPalindromePrefix.isImaginaryStringPalindromeNode()) {
            //we are ignoring the imaginary prefix (centered at the position -1 of the string) and what comes before
            // the suffix (i.e. an occurrence of addedLetter at the left of the imaginary string,
            // and we just add a new palindrome of length 1 and content 'addedLetter'
            newNode.setLabel("" + insertion.letter);
        } else {
            newNode.setLabel(insertion.letter + longestPalindromePrefix.getLabel() + insertion.letter);
        }
        tree.add(newNode);

        //adding the edge connecting the palindrome prefix to the new PalindromeNode
        longestPalindromePrefix.getOutgoingNodes().put(insertion.letter, newNode);

        //suffix reference to imaginary string is not allowed, change it to the empty string
        if (longestPalindromePrefix.isImaginaryStringPalindromeNode()) {
            newNode.setLongestPalindromeSuffix(tree.get(INDEX_EMPTY_STRING));
        } else {
            PalindromeNode suffixForNewNode = getLongestPalindromeSuffixForNewNode(insertion, longestPalindromePrefix);
            newNode.setLongestPalindromeSuffix(suffixForNewNode);
        }

        currentLongestPalindromeSuffixNodeIndex = newNode.getIndex();
    }

    //if there is already an outgoing node from the longestPalindromeSuffix with the same letter, it means
    //there is already a duplicate of the palindrome we have just found.
    private boolean isDuplicatePalindrome(char letter, PalindromeNode longestPalindromeSuffixForNextPalindrome) {
        return longestPalindromeSuffixForNextPalindrome.getOutgoingNodes().containsKey(letter);
    }

    //I start with the longest palindrome suffix of the previous PalindromeNode added to the eertree, then I go back
    // traversing the eertree using the PalindromeNode.getLongestPalindromeSuffix() references, until I find an appropriate node to use as a prefix
    // for my next PalindromeNode (in worst case I end up hitting the imaginary string)
    private PalindromeNode getLongestPalindromePrefixForNextPalindromeNode(Insertion insertion) {
        PalindromeNode longestPalindromePrefix = tree.get(currentLongestPalindromeSuffixNodeIndex);
        while (isNecessaryToKeepTraversingTheSuffixChain(insertion, longestPalindromePrefix)) {
            longestPalindromePrefix = longestPalindromePrefix.getLongestPalindromeSuffix();
        }
        return longestPalindromePrefix;
    }

    //I start with the longest palindrome suffix of the palindrome prefix I have just used to compute the new PalindromeNode, then I
    //go back traversing the eertree using the PalindromeNode.getLongestPalindromeSuffix() references until I find an
    //appropriate palindrome suitable as a suffix for the new PalindromeNode
    private PalindromeNode getLongestPalindromeSuffixForNewNode(Insertion insertion, PalindromeNode longestPalindromePrefix) {
        PalindromeNode suffixForNewNode = longestPalindromePrefix.getLongestPalindromeSuffix();
        while(isNecessaryToKeepTraversingTheSuffixChain(insertion, suffixForNewNode)) {
            suffixForNewNode = suffixForNewNode.getLongestPalindromeSuffix();
        }
        suffixForNewNode = suffixForNewNode.getOutgoingNodes().get(insertion.letter);
        return suffixForNewNode;
    }

    private boolean isNecessaryToKeepTraversingTheSuffixChain(Insertion insertion, PalindromeNode currentSuffix) {
         if (currentSuffix.isImaginaryStringPalindromeNode()) {
             return false;
         }
        //if B=suffixForNewNode is not imaginary, we need to check that inside the original string xBx is a palindrome
        int indexOfLetterPrecedingTheCurrentSuffix = insertion.letterIndex - currentSuffix.getPalindromeLength() - 1;
        return (indexOfLetterPrecedingTheCurrentSuffix < 0 || insertion.letter != string[indexOfLetterPrecedingTheCurrentSuffix]);
    }

    public static void main(String[] args) {
        String str = "eertree";
        Eertree eertree = new Eertree(str);
        eertree.build();
    }

    class Insertion {
        final char letter;
        final int letterIndex;

        public Insertion(int letterIndex) {
            this.letterIndex = letterIndex;
            this.letter = string[letterIndex];
        }
    }
}
