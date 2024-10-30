package oo2.redictado.DataClassSniffer;

import oo2.redictado.Aroma;
import oo2.redictado.AromaReport;
import oo2.redictado.antlr4.BythonParserBaseVisitor;
import oo2.redictado.antlr4.BythonParser;

public class DataClassSnifferVisitor extends BythonParserBaseVisitor {
    private final AromaReport report;
    private final String snifferName;

    public DataClassSnifferVisitor(AromaReport report, String snifferName) {
        this.report = report;
        this.snifferName = snifferName;
    }

  
}
