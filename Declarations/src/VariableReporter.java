import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class VariableReporter {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<String> lines = new ArrayList<>();
        while (scanner.hasNextLine()) {
            lines.add(scanner.nextLine());
        }// end while
        scanner.close();

        ParseVariables.parseVariables(lines);
    }// end main
}// end class
