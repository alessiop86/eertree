package alessiop86.algorithms;

public class Eertree {

    private static final int MAXN = 105000;

    int stringLength;
    char[] s = new char[MAXN]; //why MAXN?
    Node[] tree = new Node[MAXN]; //why MAXN?
    int suff; 			// max suffix palindrome
    //long ans;

    public Eertree(String str) {
        s = str.toCharArray();
        stringLength = str.length();
    }

    class Node {
        //int next[] = new int[26]; //strictly following the paper I should have used a balanced BST here
        int outgoingNodesIndexes[] = new int[26]; //strictly following the paper I should have used a balanced BST here.
                                                 // Each Node can have at most 1 outgoing node for every symbol of the alphabet in my case
        int len;
        int sufflink;
    }

    void initTree() {
        //2 suffixes palindromes until now (number of nodes??? check)
        suff = 2;

        //imaginary string
        tree[1] = new Node();
        tree[1].len = -1;
        tree[1].sufflink = 1;

        //empty string
        tree[2] = new Node();
        tree[2].len = 0;
        tree[2].sufflink = 1;
    }



    public boolean addLetter(int pos) {
        int cur = suff;
        int curlen = 0;
        int let = s[pos] - 'a'; //to transform in char
        //char letter = s[pos];


        while (true) {
            curlen = tree[cur].len;
            if (pos - 1 - curlen >= 0 && s[pos - 1 - curlen] == s[pos])
                break;
            cur = tree[cur].sufflink;
        }
        if (tree[cur].outgoingNodesIndexes[let] != 0) {//if (tree[cur].next[let] != 0) {
            suff = tree[cur].outgoingNodesIndexes[let];//  suff = tree[cur].next[let];
            return false;
        }

//        num++;
//        suff = num;
//        tree[num].len = tree[cur].len + 2;
//        tree[cur].next[let] = num;
//
//        if (tree[num].len == 1) {
//            tree[num].sufflink = 2;
//            tree[num].num = 1;
//            return true;
//        }

//        while (true) {
//            cur = tree[cur].sufflink;
//            curlen = tree[cur].len;
//            if (pos - 1 - curlen >= 0 && s[pos - 1 - curlen] == s[pos]) {
//                tree[num].sufflink = tree[cur].next[let];
//                break;
//            }
//        }

        //tree[num].num = 1 + tree[tree[num].sufflink].num;

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
