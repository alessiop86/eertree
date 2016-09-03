package alessiop86.algorithms.eertree;

public class EmptyStringPalindromeNode extends PalindromeNode {

    private static final int INDEX_EMPTY_STRING = 1;

    public EmptyStringPalindromeNode(ImaginaryStringPalindromeNode imaginaryStringPalindromeNode) {
        super(INDEX_EMPTY_STRING, imaginaryStringPalindromeNode);
        setLabel("");
    }
}
