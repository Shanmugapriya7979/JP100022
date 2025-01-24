package project_6;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class project_6 {

    static class Task {
        String description;
        boolean isCompleted;

        Task(String description, boolean isCompleted) {
            this.description = description;
            this.isCompleted = isCompleted;
        }

        public String toString() {
            return (isCompleted ? "[✔]" : "[  ]") + " " + description;
        }
    }

    public static void main(String[] args) {
        ArrayList<Task> tasks = new ArrayList<>();
        String fileName = "Tasks.txt";
        loadTasksFromFile(tasks, fileName);

        Scanner ip = new Scanner(System.in);
        int choice = -1;

        do {
        	System.out.println("\n	Task loaded");
            System.out.println("\n\n--- To-Do List Manager ---");
            System.out.println("1. Add Task");
            System.out.println("2. View Tasks");
            System.out.println("3. Mark Task as Completed");
            System.out.println("4. Delete Task");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            try {
                choice = ip.nextInt();
                ip.nextLine();
            } catch (Exception e) {
                System.out.println("\nInvalid input! Please enter an integer.");
                ip.next();
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.println("\nEnter the task description: ");
                    String desc = ip.nextLine();
                    if (desc.isEmpty()) {
                        System.out.println("Task description cannot be empty. Please try again.");
                    } else {
                        tasks.add(new Task(desc, false));
                        System.out.println("Task added!");
                        saveTasksToFile(tasks, fileName);
                    }
                    break;

                case 2:
                    System.out.println("\nYour Tasks:");
                    if (tasks.isEmpty()) {
                        System.out.println("No tasks recorded.");
                    } else {
                        for (int i = 0; i < tasks.size(); i++) {
                            System.out.println((i + 1) + ". " + tasks.get(i));
                        }
                    }
                    break;

                case 3:
                    System.out.print("\nEnter task number to mark as completed: ");
                    try {
                        int completeIndex = ip.nextInt() - 1;
                        if (completeIndex >= 0 && completeIndex < tasks.size()) {
                            tasks.get(completeIndex).isCompleted = true;
                            System.out.println("Task marked as completed!");
                            saveTasksToFile(tasks, fileName);
                        } else {
                            System.out.println("Invalid task number.");
                        }
                    } catch (Exception e) {
                        System.out.println("Invalid input! Please enter a valid task number.");
                        ip.next();
                    }
                    break;

                case 4:
                    System.out.print("\nEnter task number to delete: ");
                    try {
                        int deleteIndex = ip.nextInt() - 1;
                        if (deleteIndex >= 0 && deleteIndex < tasks.size()) {
                            tasks.remove(deleteIndex);
                            System.out.println("Task deleted!");
                            saveTasksToFile(tasks, fileName);
                        } else {
                            System.out.println("Invalid task number.");
                        }
                    } catch (Exception e) {
                        System.out.println("Invalid input! Please enter a valid task number.");
                        ip.next();
                    }
                    break;

                case 5:
                    System.out.println("\nExiting To-Do List Manager. Goodbye!");
                    break;

                default:
                    System.out.println("\nInvalid choice. Please try again.");
            }
        } while (choice != 5);

        ip.close();
    }

    private static void loadTasksFromFile(ArrayList<Task> tasks, String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";", 2);
                if (parts.length == 2) {
                    boolean isCompleted = Boolean.parseBoolean(parts[0]);
                    String description = parts[1];
                    tasks.add(new Task(description, isCompleted));
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading tasks from file: " + e.getMessage());
        }
    }

    private static void saveTasksToFile(ArrayList<Task> tasks, String fileName) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (Task task : tasks) {
                bw.write(task.isCompleted + ";" + task.description);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving tasks to file: " + e.getMessage());
        }
    }
}
