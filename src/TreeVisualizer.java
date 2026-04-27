import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class TreeVisualizer extends JPanel {

    private int getHeight(Node node) {
        if (node == null) return 0;
        return 1 + Math.max(getHeight(node.left), getHeight(node.right));
    }

    private int xCounter = 0;
    private final int H_SPACING = 45;
    private final int V_SPACING = 100;
    private BinarySearchTree tree;
    private Map<Node, Integer> xCoords;

    public TreeVisualizer(BinarySearchTree tree) {
        this.tree = tree;
        this.xCoords = new HashMap<>();
        this.setBackground(Color.WHITE);

        if (tree.getRoot() != null) {
            calculateXCoords(tree.getRoot());
            int height = getHeight(tree.getRoot());
            int width = xCounter * H_SPACING + 100;
            int canvasHeight = height * V_SPACING + 100;
            setPreferredSize(new Dimension(width, canvasHeight));
        }
    }

    private void calculateXCoords(Node node) {
        if (node == null) return;
        calculateXCoords(node.left);
        xCoords.put(node, ++xCounter * H_SPACING);
        calculateXCoords(node.right);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setStroke(new BasicStroke(1.5f));
        g2d.setFont(new Font("SansSerif", Font.PLAIN, 9));

        if (tree.getRoot() != null) {
            drawNodePositions(g2d, tree.getRoot(), 50);
        }
    }

    private void drawNodePositions(Graphics2D g2d, Node node, int y) {
        if (node == null) return;

        int x = xCoords.get(node);

        if (node.left != null) {
            int newX = xCoords.get(node.left);
            int newY = y + V_SPACING;
            g2d.drawLine(x, y + 12, newX, newY - 12);
            drawNodePositions(g2d, node.left, newY);
        }

        if (node.right != null) {
            int newX = xCoords.get(node.right);
            int newY = y + V_SPACING;
            g2d.drawLine(x, y + 12, newX, newY - 12);
            drawNodePositions(g2d, node.right, newY);
        }

        g2d.setColor(Color.WHITE);
        g2d.fillRoundRect(x - 35, y - 12, 70, 24, 15, 15);
        g2d.setColor(Color.BLACK);
        g2d.drawRoundRect(x - 35, y - 12, 70, 24, 15, 15);

        String labelString = node.player.getNickname();
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(labelString);
        g2d.drawString(labelString, x - (textWidth / 2), y + 4);
    }

    private static JFrame activeFrame = null;

    public static void Launch() {
        SwingUtilities.invokeLater(() -> {
            if (activeFrame != null && activeFrame.isVisible()) {
                activeFrame.dispose();
            }

            activeFrame = new JFrame("Visualizador de Árvore Binária de Jogadores");
            activeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            TreeVisualizer panel = new TreeVisualizer(ConsoleMenu.bst);
            JScrollPane scrollPane = new JScrollPane(panel);
            scrollPane.setPreferredSize(new Dimension(1000, 600));

            activeFrame.add(scrollPane);
            activeFrame.pack();
            activeFrame.setLocationRelativeTo(null);
            activeFrame.setVisible(true);
        });
    }
}
