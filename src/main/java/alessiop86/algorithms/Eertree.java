package alessiop86.algorithms;

public class Eertree {

    private static final int MAXN = 105000;

    int stringLength;
    char[] s = new char[MAXN]; //why MAXN?
    Node[] tree = new Node[MAXN]; //why MAXN?
    int biggerIndexOfPalindromeThatIsMaxSuffixPalindrome;            // max suffix palindrome
    //long ans;

    public Eertree(String str) {
        s = str.toCharArray();
        stringLength = str.length();
    }

    class Node {
        //int next[] = new int[26]; //strictly following the paper I should have used a balanced BST here
        int outgoingNodesIndexes[] = new int[26]; //strictly following the paper I should have used a balanced BST here.
        // Each PalindromeNode can have at most 1 outgoing node for every symbol of the alphabet in my case
        int lengthOfThePalindromeRepresentedByThisNode;
        int largestProperSuffixPalindromeNodeIndex;
    }

    void initTree() {
        //2 suffixes palindromes until now (number of nodes??? check)
        biggerIndexOfPalindromeThatIsMaxSuffixPalindrome = 2;

        //imaginary string
        tree[1] = new Node();
        tree[1].lengthOfThePalindromeRepresentedByThisNode = -1;
        tree[1].largestProperSuffixPalindromeNodeIndex = 1;

        //empty string
        tree[2] = new Node();
        tree[2].lengthOfThePalindromeRepresentedByThisNode = 0;
        tree[2].largestProperSuffixPalindromeNodeIndex = 1;
    }


    public boolean addLetter(int pos) {
        int cur = biggerIndexOfPalindromeThatIsMaxSuffixPalindrome;
        int curlen = 0;
        int let = s[pos] - 'a'; //to transform in char
        //char letter = s[pos];


        while (true) {
            curlen = tree[cur].lengthOfThePalindromeRepresentedByThisNode;
            boolean longEnoughToBeAProperSuffixPalindrome = pos - 1 - curlen >= 0;
            boolean currentLetterEqualsToLetterBeforeTheLastSuffixPalindrome = longEnoughToBeAProperSuffixPalindrome && s[pos - 1 - curlen] == s[pos];
            if (longEnoughToBeAProperSuffixPalindrome && currentLetterEqualsToLetterBeforeTheLastSuffixPalindrome)
                break;
            //I go back until I find an appropriate node to which add the letter, ultimately ending up with the main node
            cur = tree[cur].largestProperSuffixPalindromeNodeIndex;
        }
        if (tree[cur].outgoingNodesIndexes[let] != 0) {//if (tree[cur].next[let] != 0) {
            biggerIndexOfPalindromeThatIsMaxSuffixPalindrome = tree[cur].outgoingNodesIndexes[let];//  biggerIndexOfPalindromeThatIsMaxSuffixPalindrome = tree[cur].next[let];
            return false;
        }

//        num++;
//        biggerIndexOfPalindromeThatIsMaxSuffixPalindrome = num;
//        tree[num].palindromeLength = tree[cur].palindromeLength + 2;
//        tree[cur].next[let] = num;
//
//        if (tree[num].palindromeLength == 1) {
//            tree[num].largestProperSuffixPalindromeNodeIndex = 2;
//            tree[num].num = 1;
//            return true;
//        }

//        while (true) {
//            cur = tree[cur].largestProperSuffixPalindromeNodeIndex;
//            curlen = tree[cur].palindromeLength;
//            if (pos - 1 - curlen >= 0 && s[pos - 1 - curlen] == s[pos]) {
//                tree[num].largestProperSuffixPalindromeNodeIndex = tree[cur].next[let];
//                break;
//            }
//        }

        //tree[num].num = 1 + tree[tree[num].largestProperSuffixPalindromeNodeIndex].num;
    System.out.println("I need to add cur" + cur);
        return true;
    }


    public static void main(String[] args) {

        String str = "eertree";
        Eertree eertree = new Eertree(str);

        eertree.build();

        //System.out.println(eertree.ans);
    }


    private void build() {
        initTree();
        for (int i = 0; i < stringLength; i++) {
            addLetter(i);
        }
    }
}
