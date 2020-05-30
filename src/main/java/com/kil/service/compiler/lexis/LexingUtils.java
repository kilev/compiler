package com.kil.service.compiler.lexis;

import com.kil.service.compiler.lexis.domain.CodeCharacter;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@UtilityClass
public class LexingUtils {

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
