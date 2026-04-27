import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;
import javafx.application.Platform;

public class TreeVisualizer extends Application {

    // Helper method to get height
    private int getHeight(Node node) {
        if (node == null) {
            return 0;
        }
        return 1 + Math.max(getHeight(node.left), getHeight(node.right));
    }

    private int xCounter = 0;
    private final int H_SPACING = 90;
    private final int V_SPACING = 60;

    private void calculateXCoords(Node node, Map<Node, Integer> xCoords) {
        if (node == null) return;
        calculateXCoords(node.left, xCoords);
        xCoords.put(node, ++xCounter * H_SPACING);
        calculateXCoords(node.right, xCoords);
    }

    public void drawTree(Canvas canvas, BinarySearchTree tree) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        gc.setFont(new Font(10));

        if (tree.getRoot() != null) {
            Map<Node, Integer> xCoords = new HashMap<>();
            xCounter = 0;
            calculateXCoords(tree.getRoot(), xCoords);

            // Adjust canvas width based on the number of nodes
            canvas.setWidth(xCounter * H_SPACING + 100);

            drawNodePositions(gc, tree.getRoot(), xCoords, 50);
        }
    }

    private void drawNodePositions(GraphicsContext gc, Node node, Map<Node, Integer> xCoords, double y) {
        if (node == null) {
            return;
        }

        double x = xCoords.get(node);

        if (node.left != null) {
            double newX = xCoords.get(node.left);
            double newY = y + V_SPACING;
            gc.strokeLine(x, y + 15, newX, newY - 15);
            drawNodePositions(gc, node.left, xCoords, newY);
        }

        if (node.right != null) {
            double newX = xCoords.get(node.right);
            double newY = y + V_SPACING;
            gc.strokeLine(x, y + 15, newX, newY - 15);
            drawNodePositions(gc, node.right, xCoords, newY);
        }

        // Draw rectangular nodes (rounded)
        gc.setFill(Color.WHITE);
        gc.fillRoundRect(x - 40, y - 15, 80, 30, 20, 20);
        gc.setStroke(Color.BLACK);
        gc.strokeRoundRect(x - 40, y - 15, 80, 30, 20, 20);

        gc.setFill(Color.BLACK);
        String label = node.player.getNickname() + " (" + node.player.getRanking() + ")";
        gc.fillText(label, x - (label.length() * 2.8), y + 4);
    }

    private static boolean isLaunched = false;
    private static Stage activeStage = null;

    @Override
    public void start(Stage primaryStage) {
        activeStage = primaryStage;
        primaryStage.setTitle("Visualizador de Árvore Binária de Jogadores");

        BinarySearchTree bst = ConsoleMenu.bst;

        int height = getHeight(bst.getRoot());
        int canvasHeight = 100 + height * V_SPACING;

        Canvas canvas = new Canvas(800, canvasHeight); // Width will be dynamically adjusted in drawTree
        drawTree(canvas, bst);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(canvas);
        scrollPane.setPannable(true);
        scrollPane.setFitToHeight(true);

        Scene scene = new Scene(scrollPane, 1000, 600);
        primaryStage.setScene(scene);

        // Prevent implicit exit so JavaFX doesn't completely die when window closes
        Platform.setImplicitExit(false);

        primaryStage.show();
    }

    public static void Launch() {
        if (!isLaunched) {
            isLaunched = true;
            new Thread(() -> {
                try {
                    Application.launch(TreeVisualizer.class);
                } catch (Exception e) {
                    System.out.println("GUI failed to start.");
                }
            }).start();
        } else {
            // Already launched in this session. Run later on the FX thread.
            Platform.runLater(() -> {
                try {
                    if (activeStage != null && activeStage.isShowing()) {
                        activeStage.close(); // Close the old window
                    }
                    new TreeVisualizer().start(new Stage()); // Open a fresh one
                } catch (Exception e) {
                    System.out.println("Could not open GUI.");
                }
            });
        }
    }
}
