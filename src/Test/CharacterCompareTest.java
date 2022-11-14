package Test;

import StringCompare.CharacterCompare;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;


class CharacterCompareTest {

    @org.junit.jupiter.api.Test
    void readFile() {
    }

    @org.junit.jupiter.api.Test
    void compare_character() throws IOException {
        CharacterCompare characterCompare = new CharacterCompare("E:\\workspace\\javaworkspace\\codeDemo01.c",
                "E:\\workspace\\javaworkspace\\codeDemo02.c");
        double percent = characterCompare.compare_character();
        System.out.println(percent);
    }

    @Test
    void keyword_match() throws FileNotFoundException {
        CharacterCompare characterCompare = new CharacterCompare("E:\\workspace\\javaworkspace\\codeDemo01.c",
                "E:\\workspace\\javaworkspace\\codeDemo02.c");
        System.out.println(characterCompare.keyword_match());
    }
}