public class BinarySearchTree<T extends Comparable<T>> {

    private Node<T> root;

    public void Insert(T value) { root = insertRecursive(root, value); }

    private Node<T> insertRecursive(Node<T> current, T value) {

        if (current == null) { return new Node<>(value); }

        int comparison = value.compareTo(current.value);

        if (comparison < 0) {
            current.left = insertRecursive(current.left, value);
        } else if (comparison > 0) {
            current.right = insertRecursive(current.right, value);
        } else {
            System.out.println("Value " + value + " already exists!");
        }

        return current;
    }

    @Override
    public String toString() { return root == null ? "Tree is empty" : toStringRecursive(root).trim(); }

    private String toStringRecursive(Node<T> current) {
        if (current == null) { return ""; }

        return toStringRecursive(current.left)
                + current.value + " "
                + toStringRecursive(current.right);
    }
}