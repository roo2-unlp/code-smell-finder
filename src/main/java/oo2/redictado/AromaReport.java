package oo2.redictado;

import java.util.ArrayList;
import java.util.List;

/**
 * Report of the aromas detected in a code.
 */
public class AromaReport {
    private String code;
    private List<Aroma> aromas;

    public AromaReport(String code) {
        this.code = code;
        this.aromas = new ArrayList<Aroma>();
    }

    public String getCode() {
        return this.code;
    }

    public List<Aroma> getAromas() {
        return List.copyOf(this.aromas);
    }

    /**
     * Adds an aroma to the report.
     *
     * @param aroma The aroma to add.
     */
    public void addAroma(Aroma aroma) {
        this.aromas.add(aroma);
    }

    /**
     * Returns whether the code has any stinking aroma.
     *
     * @return True if the code has any stinking aroma, false otherwise.
     */
    public boolean stinks() {
        return this.aromas.stream().anyMatch(Aroma::stinks);
    }
}
