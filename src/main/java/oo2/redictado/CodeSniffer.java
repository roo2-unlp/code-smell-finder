package oo2.redictado;

public interface CodeSniffer {
    /**
     * Detect a sniff in the code.
     *
     * @param code the code to be analyzed
     * @param report the report to be updated with the sniff results
     */
    public void sniff(String code, AromaReport report);
}
