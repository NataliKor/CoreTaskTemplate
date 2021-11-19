package jm.task.core.jdbc.util;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class ReadFile {

    //считывает заданное подключение из файла properties
    public static String readLineInFile() {
        String result = null;
        String path = "D:\\CoreTaskTemplate\\src\\main\\java\\resources\\properties";
        try(Scanner scanner = new Scanner(new File(path))) {
            String str = scanner.nextLine();
            result = str.substring(str.indexOf(":") + 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
