package oo2.redictado;

public class Aroma {

    private final String name;
    private final String message;
    private final boolean stinks;

    /**
     * Creates an Aroma with the result of the analysis of a Bad Smell in the code
     * @param name the name of the Bad Smell
     * @param message the message that describes the Bad Smell in the code
     * @param stinks true if the Bad Smell is present in the code, false otherwise
     */
    public Aroma(String name, String message, boolean stinks) {
        this.name = name;
        this.message = message;
        this.stinks = stinks;
    }

    /**
     * @return the name of the Bad Smell
     */
    public String name() {
        return this.name;
    }

    /**
     * @return the message that describes the Bad Smell in the code
     */
    public String message() {
        return this.message;
    }

    /**
     * @return true if the Bad Smell is present in the code, false otherwise
     */
    public boolean stinks() {
        return this.stinks;
    }
}
