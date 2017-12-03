import java.util.*;
import java.util.stream.*;

public class BinaryTree {
  private Node root;

  private BinaryTree() {}

  public Stream<Node> inOrderTraversal() {
    List<Node> nodes = new ArrayList<>();
    inOrderTraversal(root, nodes);
    return nodes.stream();
  }

  public Stream<Node> postOrderTraversal() {
    List<Node> nodes = new ArrayList<>();
    postOrderTraversal(root, nodes);
    return nodes.stream();
  }

  public Stream<Node> preOrderTraversal() {
    List<Node> nodes = new ArrayList<>();
    preOrderTraversal(root, nodes);
    return nodes.stream();
  }

  public BinaryTree inverse() {
    inverse(root);
    return this;
  }

  public void inverse(Node node) {
    if(node == null) {
      return;
    }

    Node temp = node.left;
    node.left = node.right;
    node.right = temp;
    
    inverse(node.right);
    inverse(node.left);
  }

  private void preOrderTraversal(Node node, List<Node> nodes) {
    if(node != null) {
      nodes.add(node);
      preOrderTraversal(node.left, nodes);
      preOrderTraversal(node.right, nodes);
    }
  }

  private void postOrderTraversal(Node node, List<Node> nodes) {
    if(node == null) {
      return;
    }

    postOrderTraversal(node.left, nodes);
    postOrderTraversal(node.right, nodes);
    nodes.add(node);
  }

  private void inOrderTraversal(Node node, List<Node> traversedNodes) {
    if(node == null) {
      return;
    }

    inOrderTraversal(node.left, traversedNodes);
    traversedNodes.add(node);
    inOrderTraversal(node.right, traversedNodes);
  }

  public static class Node {
    public int value;
    public Node right;
    public Node left;

    public Node(int value, Node left, Node right) {
      this.value = value;
      this.right = right;
      this.left = left;
    }
  }

  public static class BinaryTreeConstructor {
    /*This method cannot be static, as it is impossible to reference type parameter
    * T from static context!
    */
    public static BinaryTree createMockedTree() {
      BinaryTree tree = new BinaryTree();
      tree.root = new Node(10,
        new Node(5,
          new Node(2, null, null),
          new Node(6, null, null)),
        new Node(15,
          new Node(11, null, null),
          new Node(16, null, null)));
      return tree;
    }

  }
}
