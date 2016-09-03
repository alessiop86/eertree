package alessiop86.algorithms;

public class Eertree {

    private static final int MAXN = 105000;

    public Eertree(String str) {
        s = str.toCharArray();
        len = str.length();
    }

    class Node {
        int next[] = new int[26]; ;
        int len;
        int sufflink;
    }

    void initTree() {
        suff = 2;
        tree[1] = new Node();
        tree[1].len = -1;
        tree[1].sufflink = 1;
        tree[2] = new Node();
        tree[2].len = 0;
        tree[2].sufflink = 1;
    }

    int len;
    char[] s = new char[MAXN]; //why MAXN?
    Node[] tree = new Node[MAXN]; //why MAXN?
    int suff; 			// max suffix palindrome
    long ans;

    public boolean addLetter(int pos) {
        int cur = suff;
        int curlen = 0;
        int let = s[pos] - 'a';

        while (true) {
            curlen = tree[cur].len;
            if (pos - 1 - curlen >= 0 && s[pos - 1 - curlen] == s[pos])
                break;
            cur = tree[cur].sufflink;
        }
        if (tree[cur].next[let] != 0) {
            suff = tree[cur].next[let];
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

        System.out.println(eertree.ans);
    }



    private void build() {
        initTree();
        for (int i = 0; i < len; i++) {
            addLetter(i);
//            ans += tree[suff].num;
        }
    }
}
