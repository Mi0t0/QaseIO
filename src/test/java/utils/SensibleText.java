package utils;

import lombok.Data;

@Data
public class SensibleText {

    private final boolean isSensitive;
    private final String text;

    public SensibleText(String text, boolean isSensitive) {
        this.text = text;
        this.isSensitive = isSensitive;
    }

    @Override
    public String toString() {
        if (text == null) {
            return "";
        } else if (isSensitive) {
            return "*******";
        } else {
            return text;
        }
    }
}
