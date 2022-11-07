package StringCompare;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
       check the code duplications by character;
     */
public class CharacterCompare {
    List<String> keyword_list = new ArrayList<>();
    String[] keyword = {"auto", "break", "case", "char", "const", "continue", "default",
            "do", "double", "else", "enum", "extern", "float", "for", "goto", "if", "int",
            "long", "register", "return", "short", "signed", "sizeof", "static", "struct", "switch",
            "typedef", "union", "unsigned", "void", "volatile", "while"};
    private File file1 = null;
    private File file2 = null;
    private FileInputStream input = null;
    private FileOutputStream output = null;

    public CharacterCompare(String filepath1, String filepath2) throws FileNotFoundException {
        file1 = new File(filepath1);
        file2 = new File(filepath2);

        if (file1 != null && file2 != null) {
            input = new FileInputStream(file1);
            output = new FileOutputStream(file2);
        }
        //add the keyword into the list
        keyword_list.addAll(Arrays.asList(keyword));
    }


}
