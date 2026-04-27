public class Main {
    public static void main(String[] args) {


        System.out.println("--- Integer Tree ---");
        BinarySearchTree<Integer> intTree = new BinarySearchTree<>();
        intTree.Insert(50);
        intTree.Insert(30);
        intTree.Insert(70);
        intTree.Insert(20);
        intTree.Insert(40);
        intTree.Insert(50); // should trigger the else

        System.out.printf("%nSorted Ints: ");
        System.out.println(intTree);

        System.out.println();

        System.out.println("--- String Tree ---");
        BinarySearchTree<String> stringTree = new BinarySearchTree<>();
        stringTree.Insert("Banana");
        stringTree.Insert("Apple");
        stringTree.Insert("Cherry");

        System.out.printf("%nSorted Strings: ");
        System.out.println(stringTree);

        System.out.println("--- Double Tree ---");
        BinarySearchTree<Double> doubleTree = new BinarySearchTree<>();
        doubleTree.Insert(10.5);
        doubleTree.Insert(5.2);
        doubleTree.Insert(15.7);
        doubleTree.Insert(2.1);

        System.out.printf("Sorted Doubles: %s%n", doubleTree);

    }
}
