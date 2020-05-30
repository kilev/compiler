package com.kil.service.compiler.lexis;

import com.google.common.collect.Lists;
import com.kil.common.Utils;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@UtilityClass
public class LexingUtils {

    public List<CodeCharacter> getCodeCharacters(String code) {
        List<CodeCharacter> codeCharacters = new ArrayList<>();

        int line = 0;
        int index = 0;
        for (Character character : Lists.charactersOf(code)) {
            CodeCharacter codeCharacter = new CodeCharacter(character, line, index);
            codeCharacters.add(codeCharacter);

            if (character.equals(Utils.getLineSeparator())) {
                line++;
                index = 0;
            } else {
                index++;
            }
        }
        return codeCharacters;
    }

    public String getString(List<CodeCharacter> codeCharacterList, CodeCharacter... codeCharacters) {
        return getCharacterStream(codeCharacterList, codeCharacters)
                .map(Object::toString)
                .collect(Collectors.joining(""));
    }

    public Stream<Character> getCharacterStream(List<CodeCharacter> codeCharacterList, CodeCharacter... codeCharacters) {
        return Stream.concat(codeCharacterList.stream(), Stream.of(codeCharacters))
                .map(CodeCharacter::getCharacter);
    }

}
