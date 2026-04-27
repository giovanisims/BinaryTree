public class BinarySearchTree {

    private Node root;

    public Node getRoot() {
        return root;
    }

    private Player findMinPlayer(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node.player;
    }

    public void insert(Player player) {
        if (search(player.getNickname())) {
            return;
        }
        root = insert(root, player);
    }

    private Node insert(Node current, Player player) {
        if (current == null) {
            return new Node(player);
        }

        if (player.getRanking() < current.player.getRanking()) {
            current.left = insert(current.left, player);
        } else if (player.getRanking() > current.player.getRanking()) {
            current.right = insert(current.right, player);
        }

        return current;
    }

    public Player remove(String name) {
        Node target = search(root, name);
        if (target == null) {
            return null;
        }

        Player removedPlayer = target.player;
        root = remove(root, name);
        return removedPlayer;
    }

    private Node remove(Node current, String name) {
        if (current == null) {
            return null;
        }

        Node target = search(root, name);
        if (target == null) {
            return current;
        }

        int targetRank = target.player.getRanking();

        if (targetRank < current.player.getRanking()) {
            current.left = remove(current.left, name);
        } else if (targetRank > current.player.getRanking()) {
            current.right = remove(current.right, name);
        } else {
            if (current.left == null) {
                return current.right;
            } else if (current.right == null) {
                return current.left;
            }

            current.player = findMinPlayer(current.right);
            current.right = remove(current.right, current.player.getNickname());
        }

        return current;
    }

    public boolean search(String name) {
        return search(root, name) != null;
    }

    private Node search(Node current, String name) {
        if (current == null) {
            return null;
        }

        if (current.player.getNickname().equalsIgnoreCase(name)) {
            return current;
        }

        Node leftResult = search(current.left, name);
        if (leftResult != null) {
            return leftResult;
        }

        return search(current.right, name);
    }

    @Override
    public String toString() {
        if (root == null) {
            return "Tree is empty";
        }
        return toStringRecursive(root).trim();
    }

    private String toStringRecursive(Node current) {
        if (current == null) {
            return "";
        }

        return toStringRecursive(current.left) + current.player.toString() + "\n" + toStringRecursive(current.right);
    }
}