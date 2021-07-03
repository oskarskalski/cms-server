package com.oskarskalski.cms.features;

public class CodeGenerator {
    public static String generate(int length) {
        StringBuilder code = new StringBuilder();

        for (int i = 0; i < length; i++) {
            char generateCharacter = (char) (Math.random() * ('z' - 'a') + 'a');
            code.append(generateCharacter);
        }

        return code.toString();
    }
}
