package alessiop86.algorithms.eertree;

public class ImaginaryStringPalindromeNode extends PalindromeNode {

    private static final int INDEX_IMAGINARY_STRING = 0;

    public ImaginaryStringPalindromeNode() {
        super(INDEX_IMAGINARY_STRING, null);
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
