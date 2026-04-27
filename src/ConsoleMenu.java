import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ConsoleMenu {
    public static BinarySearchTree<Player> bst = new BinarySearchTree<>();

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
                scanner.next(); // clear invalid input
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.print("Enter player ranking to insert: ");
                    if (scanner.hasNextInt()) {
                        int rank = scanner.nextInt();
                        System.out.print("Enter player nickname: ");
                        String name = scanner.next();
                        bst.Insert(new Player(name, rank));
                    } else {
                        System.out.println("Invalid input.");
                        scanner.next();
                    }
                    break;
                case 2:
                    System.out.print("Enter player ranking to remove: ");
                    if (scanner.hasNextInt()) {
                        bst.Remove(new Player("", scanner.nextInt()));
                    } else {
                        System.out.println("Invalid input.");
                        scanner.next();
                    }
                    break;
                case 3:
                    System.out.print("Enter player ranking to search: ");
                    if (scanner.hasNextInt()) {
                        int val = scanner.nextInt();
                        Player target = new Player("", val);
                        Node<Player> result = bst.Search(target);
                        if (result != null) {
                            System.out.println("Success! Found: " + result.value.toString());
                        }
                    } else {
                        System.out.println("Invalid input.");
                        scanner.next();
                    }
                    break;
                case 4:
                    System.out.println("\nTree contents:");
                    System.out.println(bst.toString());
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
            String line = br.readLine(); // skip header
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length == 2) {
                    bst.Insert(new Player(values[0].trim(), Integer.parseInt(values[1].trim())));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
