package com.oskarskalski.cms.features;

public class CodeGenerator {
    public static String generate(int length) {
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int drawNumber = (int) Math.floor(Math.random() * 2);
            boolean letter = drawNumber == 0;
            if (letter) {
                char generateCharacter = (char) (Math.random() * ('z' - 'a') + 'a');
                code.append(generateCharacter);
            } else {
                int generateNumber = (int) Math.floor(Math.random() * 10);
                code.append(generateNumber);
            }
        }
        return code.toString();
    }
}
