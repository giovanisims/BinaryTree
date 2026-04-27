import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ConsoleMenu {
    public static BinarySearchTree bst = new BinarySearchTree();

    public static void run() {
        loadData();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n--- Menu ---");
            System.out.println("1. Insert");
            System.out.println("2. Remove");
            System.out.println("3. Search");
            System.out.println("4. Print Tree (In-order)");
            System.out.println("5. Launch GUI Visualizer");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");

            int choice = -1;
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
            } else {
                scanner.next();
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.print("Enter player nickname: ");
                    String name = scanner.next();
                    System.out.print("Enter player ranking to insert: ");
                    if (scanner.hasNextInt()) {
                        int rank = scanner.nextInt();
                        if (bst.search(name)) {
                            System.out.println("Failed to insert: Player '" + name + "' already exists!");
                        } else {
                            bst.insert(new Player(name, rank));
                            System.out.println("Successfully inserted player: " + name);
                        }
                    } else {
                        System.out.println("Invalid input.");
                        scanner.next();
                    }
                    break;
                case 2:
                    System.out.print("Enter player nickname to remove: ");
                    String removeName = scanner.next();
                    Player removed = bst.remove(removeName);
                    if (removed != null) {
                        System.out.println("Successfully removed player: " + removed.getNickname());
                    } else {
                        System.out.println("Player " + removeName + " not found!");
                    }
                    break;
                case 3:
                    System.out.print("Enter player nickname to search: ");
                    String searchName = scanner.next();
                    boolean found = bst.search(searchName);
                    if (found) {
                        System.out.println("Success! Found player " + searchName + " in the tree.");
                    } else {
                        System.out.println("Player " + searchName + " not found!");
                    }
                    break;
                case 4:
                    System.out.println("\nTree contents:");
                    System.out.println(bst);
                    break;
                case 5:
                    System.out.println("Launching visualizer...");
                    TreeVisualizer.Launch();
                    break;
                case 6:
                    System.out.println("Goodbye!");
                    running = false;
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option. Please choose between 1 and 6.");
            }
        }
        scanner.close();
    }

    private static void loadData() {
        try (BufferedReader br = new BufferedReader(new FileReader("src/players.csv"))) {
            String line = br.readLine();

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length == 2) {
                    bst.insert(new Player(values[0].trim(), Integer.parseInt(values[1].trim())));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
