package at.fhburgenland.util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.Scanner;

public class Helper {

    private static final Scanner scanner = new Scanner(System.in);

    public static int readInt(String prompt) {
        while (true) {
            System.out.println();
            System.out.println(prompt);
            String line = scanner.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Nur Ganzzahlen erlaubt!");
            }
        }
    }

    public static String readStr(String prompt){
        while (true){
            System.out.println();
            System.out.println(prompt);
            String line = scanner.nextLine().trim();
            if (!line.isEmpty()){
                return line;
            }
            System.out.println("Eingabe darf nicht leer sein!");
        }
    }

    public static LocalDate readDate(String prompt) {
        while (true) {
            System.out.println();
            System.out.println(prompt);
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) {
                System.out.println("Only non-blank inpout allowed!");
                continue;
            }

            try {
                return LocalDate.parse(line, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            } catch (DateTimeParseException e) {
                System.out.println("Ungültiges Datum eingegeben!");
            }
        }
    }

    public static LocalTime readTime(String prompt) {
        while (true) {
            System.out.println();
            System.out.println(prompt);
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) {
                System.out.println("Leere Eingabe nicht erlaubt!");
                continue;
            }

            try {
                return LocalTime.parse(line,DateTimeFormatter.ofPattern("HH:mm"));
            } catch (DateTimeParseException e) {
                System.out.println("Ungültige Uhrzeit eingegeben!");
            }
        }
    }

    public static int readIntFromCollection(String prompt, Collection<Integer> allowedIntegers) {
        while (true) {
            System.out.println();
            System.out.println(prompt + " " + allowedIntegers + ": ");
            String line = scanner.nextLine().trim();
            try {
                Integer input = Integer.parseInt(line);
                if (allowedIntegers.contains(input)) {
                    return Integer.parseInt(line);
                } else {
                    System.out.println("Nur Ganzzahlen aus " + allowedIntegers + " erlaubt!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Nur Ganzzahlen erlaubt!");
            }
        }
    }

    /**
     * Von @{Rajeh Abdulhadi} und @{Michael Mayr} als Tipp geholt.
     * Wird jedoch NIE benutzt!
     * @param prompt
     * @return
     */
    public static boolean readYesNo(String prompt) {
        while (true) {
            System.out.println();
            System.out.println(prompt + " (y/n): ");

            // Cast input to lowercase
            String line = scanner.nextLine().trim().toLowerCase();

            // Allow abbreviation and entire word
            if (line.equals("y") || line.equals("yes")) {
                return true;
            }
            if (line.equals("n") || line.equals("no")) {
                return false;
            }
            System.out.println("Only 'y' or 'n' allowed!");
        }
    }
}