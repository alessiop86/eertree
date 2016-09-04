package alessiop86.algorithms.eertree;

/**
 * Imaginary String is a special node, with a different behaviour from the standard PalindromeNode:
 * - index fixed
 * - palindrome length -1
 * - autoloop as the longest palindrome suffix reference
 */
public class ImaginaryStringPalindromeNode extends PalindromeNode {

    private static final int INDEX_IMAGINARY_STRING = 0;

    public ImaginaryStringPalindromeNode() {
        super(INDEX_IMAGINARY_STRING);
    }

    @Override
    public String getLabel() {
        return "-1";
    }

    @Override
    public PalindromeNode getLongestPalindromeSuffix() {
        return this;
    }

    @Override
    public int getPalindromeLength() {
        return -1;
    }

    @Override
    public boolean isImaginaryStringPalindromeNode() {
        return true;
    }

}
