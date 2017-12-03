public class Main {

  public static void main(String[] args) {
    BinaryTree tree = BinaryTree.BinaryTreeConstructor.createMockedTree();
    tree.inOrderTraversal().forEach(node -> System.out.println(node.value));
    System.out.println();
    tree.postOrderTraversal().forEach(node -> System.out.println(node.value));
    System.out.println();
    tree.preOrderTraversal().forEach(node -> System.out.println(node.value));
    System.out.println();
    tree.inverse().inOrderTraversal().forEach(node -> System.out.println(node.value));
  }
}
