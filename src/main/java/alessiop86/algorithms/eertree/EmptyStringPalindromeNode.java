package alessiop86.algorithms.eertree;

/**
 * Empty String is a special node whose suffix is always the imaginary string, and whose index fixed
 */
public class EmptyStringPalindromeNode extends PalindromeNode {

    public static final int INDEX_EMPTY_STRING = 1;

    public EmptyStringPalindromeNode(ImaginaryStringPalindromeNode imaginaryStringPalindromeNode) {
        super(INDEX_EMPTY_STRING, imaginaryStringPalindromeNode);
        setLabel("");
    }
}
