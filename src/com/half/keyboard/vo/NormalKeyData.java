/**
 * 
 */
package com.half.keyboard.vo;

/**
 * @author Thinkpad
 *
 */
public class NormalKeyData {
	private KeyValue primaryLetter;
	private KeyValue secondaryLetter;
	private KeyValue primarySymbol;
	private KeyValue secondarySymbol;

	private KeyValue primaryNubmer;
    private KeyValue secondaryNubmer;

	private KeyValue function;
	private KeyValue special;

    public KeyValue getPrimaryLetter() {
        return primaryLetter;
    }

    public void setPrimaryLetter(KeyValue primaryLetter) {
        this.primaryLetter = primaryLetter;
    }

    public KeyValue getSecondaryLetter() {
        return secondaryLetter;
    }

    public void setSecondaryLetter(KeyValue secondaryLetter) {
        this.secondaryLetter = secondaryLetter;
    }

    public KeyValue getPrimarySymbol() {
        return primarySymbol;
    }

    public void setPrimarySymbol(KeyValue primarySymbol) {
        this.primarySymbol = primarySymbol;
    }

    public KeyValue getSecondarySymbol() {
        return secondarySymbol;
    }

    public void setSecondarySymbol(KeyValue secondarySymbol) {
        this.secondarySymbol = secondarySymbol;
    }

    public KeyValue getPrimaryNubmer() {
        return primaryNubmer;
    }

    public void setPrimaryNubmer(KeyValue primaryNubmer) {
        this.primaryNubmer = primaryNubmer;
    }

    public KeyValue getSecondaryNubmer() {
        return secondaryNubmer;
    }

    public void setSecondaryNubmer(KeyValue secondaryNubmer) {
        this.secondaryNubmer = secondaryNubmer;
    }

    public KeyValue getFunction() {
        return function;
    }

    public void setFunction(KeyValue function) {
        this.function = function;
    }

    public KeyValue getSpecial() {
        return special;
    }

    public void setSpecial(KeyValue special) {
        this.special = special;
    }
}
