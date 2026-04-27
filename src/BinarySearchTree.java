public class BinarySearchTree<T extends Comparable<T>> {

    private Node<T> root;
    public Node<T> getRoot() { return root; }

    private T findMin(Node<T> node) {
        T min = node.value;
        while (node.left != null) {
            min = node.left.value;
            node = node.left;
        }
        return min;
    }

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

    public void Remove(T value) { root = RemoveRecursive(root, value); }
    private Node<T> RemoveRecursive(Node<T> current, T value) {
        if (current == null) {
            System.out.println("Value " + value + " not found!");
            return null;
        }

        int comparison = value.compareTo(current.value);

        if (comparison < 0) {
            current.left = RemoveRecursive(current.left, value);
        } else if (comparison > 0) {
            current.right = RemoveRecursive(current.right, value);
        } else {
            if (current.left == null) {
                return current.right;
            } else if (current.right == null) {
                return current.left;
            }

            current.value = findMin(current.right);
            current.right = RemoveRecursive(current.right, current.value);
        }

        return current;
    }


    public Node<T> Search(T value) { return SearchRecursive(root, value); }

    private Node<T> SearchRecursive(Node<T> current, T value) {
        if (current == null) {
            System.out.println("Value " + value + " not found!");
            return null;
        }

        int comparison = value.compareTo(current.value);

        if (comparison < 0) {
            return SearchRecursive(current.left, value);
        } else if (comparison > 0) {
            return SearchRecursive(current.right, value);
        } else {
            return current;
        }
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