package edu.cad.utils.gson;

import com.google.gson.annotations.Expose;

public class Option {
    
    @Expose
    private String DisplayText;
    
    @Expose
    private int Value;

    public Option() {
    }

    public Option(String DisplayText, int Value) {
        this.DisplayText = DisplayText;
        this.Value = Value;
    }

    public String getDisplayText() {
        return DisplayText;
    }

    public void setDisplayText(String DisplayText) {
        this.DisplayText = DisplayText;
    }

    public int getValue() {
        return Value;
    }

    public void setValue(int Value) {
        this.Value = Value;
    }
}
