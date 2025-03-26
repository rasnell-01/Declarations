import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import static java.lang.System.out;


public class ControlChecker {
    private static final Set<String> KEYWORDS = Set.of("if", "else", "while", "do", "for");
    private static final Set<String> OPERATORS = Set.of("=", "<", "<=", ">", ">=", "==", "!=", "+", "-", "*", "/");
    private static final Set<String> DELIMITERS = Set.of("(", ")", "{", "}", ";");

    public static void main(String[] args) {
        if (args.length != 1) {
            out.println("Usage: java ControlChecker <filename>");
            return;
        }// end if

        String filename = args[0];
        List<String> statements = readFromFile(filename);

        if (statements == null || statements.isEmpty()) {
            out.println("ERROR: Unable to read from file or file is empty");
            return;
        }// end if

        for (String statement : statements) {
            out.println("Processing: " + statement);
            List<String> tokens = tokenize(statement);
            if (tokens == null) {
                out.println("LEXICAL ERROR: The control statement is not in the correct format");
                continue;
            }// end if

            if (!isValidSyntax(tokens)) {
                out.println("SYNTAX ERROR: The control statement is not in the correct format");
                continue;
            }// end if

            out.println("SUCCESS: The control statement is in the correct format");
        }// end for
    }// end main

    private static List<String> readFromFile(String filename) {
        List<String> statements = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    statements.add(line.trim());
                }// end if
            }// end while
        } catch (IOException e) {
            return null;
        }// end try catch
        return statements;
    }// end method

    private static List<String> tokenize(String statement) {
        List<String> tokens = new ArrayList<>();
        StringTokenizer tokenizer = new StringTokenizer(statement, "(){};=<>!+-*/ ", true);
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken().trim();
            if (token.isEmpty()) continue;

            if (KEYWORDS.contains(token) ||
                    OPERATORS.contains(token) ||
                    DELIMITERS.contains(token) ||
                    token.matches("[a-zA-Z_][a-zA-Z0-9_]*") ||
                    token.matches("\\d+")) {
                tokens.add(token);
            } else {
                return null;
            }// end if else
        }// end while
        return tokens;
    }// end method

    private static boolean isValidSyntax(List<String> tokens) {
        if (tokens.isEmpty()) return false;

        String firstToken = tokens.get(0);
        if (!KEYWORDS.contains(firstToken)) return false;

        if (firstToken.equals("if")) {
            if (tokens.size() < 5 || !tokens.get(1).equals("(")) return false;

            int closingParenIndex = tokens.indexOf(")");
            if (closingParenIndex == -1 || closingParenIndex + 2 >= tokens.size()) return false;

            if (!tokens.get(closingParenIndex + 2).equals("=")) return false;

            if (!tokens.get(tokens.size() - 1).equals(";")) return false;

            int elseIndex = tokens.indexOf("else");
            if (elseIndex != -1) {
                if (elseIndex + 2 >= tokens.size() || !tokens.get(elseIndex + 2).equals("=") ||
                        !tokens.get(tokens.size() - 1).equals(";")) {
                    return false;
                }// end if
            }// end if

            return true;
        }// end if

        if (firstToken.equals("while")) {
            return tokens.size() >= 5 &&
                    tokens.get(1).equals("(") &&
                    tokens.contains(")") &&
                    tokens.contains("=") &&
                    tokens.getLast().equals(";");
        }// end if

        if (firstToken.equals("do")) {
            return tokens.size() >= 6 &&
                    tokens.get(1).matches("[a-zA-Z_][a-zA-Z0-9_]*") &&
                    tokens.get(2).equals("=") &&
                    tokens.get(tokens.size() - 3).equals("while") &&
                    tokens.get(tokens.size() - 2).equals("(") &&
                    tokens.getLast().equals(")");
        }// end if

        if (firstToken.equals("for")) {
            return tokens.size() >= 9 &&
                    tokens.get(1).equals("(") &&
                    tokens.get(2).matches("[a-zA-Z_][a-zA-Z0-9_]*") &&
                    tokens.get(3).equals("=") &&
                    tokens.get(5).equals(";") &&
                    tokens.get(7).equals(";") &&
                    tokens.get(tokens.size() - 2).equals(")") &&
                    tokens.getLast().equals(";");
        }// end if

        return false;
    }// end method
}// end class
