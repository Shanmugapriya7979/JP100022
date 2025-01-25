package project_4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class project_4 {

    public static void main(String[] args) {
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);

        boolean continueAnalysis = true;

        while (continueAnalysis) {
            try {
                System.out.print("Enter the path of the text file to analyze: ");
                System.out.println("Enter path as \"C:\\Users\\Admin\\Desktop\\example_file.txt\"");
                String filePath = scanner.nextLine();

                
                filePath = filePath.strip().replaceAll("^\"|\"$", "");

                File file = new File(filePath);

                if (!file.exists() || !file.isFile()) {
                    System.out.println("Invalid file path. Please provide a valid text file.");
                    continue; 
                }

                analyzeFile(file);

            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
                System.out.println("Please try again.\n");
            }

            System.out.print("\nWould you like to analyze another file? (yes/no): ");
            String response = scanner.nextLine().trim().toLowerCase();

            if (!response.equals("yes")) {
                continueAnalysis = false;
                System.out.println("Exiting the program. Goodbye!");
            }
        }
    }

    private static void analyzeFile(File file) throws IOException {
        int lineCount = 0, wordCount = 0, charCount = 0;
        Map<String, Integer> wordFrequency = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lineCount++;
                charCount += line.length();
                String[] words = line.split("\\s+");

                for (String word : words) {
                    wordCount++;
                    word = word.toLowerCase().replaceAll("[^a-zA-Z0-9]", ""); // Normalize the word
                    wordFrequency.put(word, wordFrequency.getOrDefault(word, 0) + 1);
                }
            }
        }

        String mostFrequentWord = wordFrequency.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("None");

        
        System.out.println("\n--- File Analysis ---");
        System.out.println("Number of lines: " + lineCount);
        System.out.println("Number of words: " + wordCount);
        System.out.println("Number of characters: " + charCount);
        System.out.println("Most frequently used word: " + mostFrequentWord + " ("
                + wordFrequency.getOrDefault(mostFrequentWord, 0) + " times)");

        exportResults(file.getName(), lineCount, wordCount, charCount, mostFrequentWord, wordFrequency.getOrDefault(mostFrequentWord, 0));
    }

    private static void exportResults(String fileName, int lines, int words, int chars, String mostFrequentWord, int frequency) {
        String outputFileName = "Analysis_" + fileName + ".txt";

        try (PrintWriter writer = new PrintWriter(new FileWriter(outputFileName))) {
            writer.println("--- File Analysis ---");
            writer.println("Number of lines: " + lines);
            writer.println("Number of words: " + words);
            writer.println("Number of characters: " + chars);
            writer.println("Most frequently used word: " + mostFrequentWord + " (" + frequency + " times)");
            System.out.println("\nAnalysis results exported to: " + outputFileName);
        } catch (IOException e) {
            System.out.println("Error exporting results: " + e.getMessage());
        }
    }
}
