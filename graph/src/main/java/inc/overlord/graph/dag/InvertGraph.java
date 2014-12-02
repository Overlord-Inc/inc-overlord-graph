/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inc.overlord.graph.dag;

import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Assumes a DAG, and inverts it. Leaves become roots, roots become leaves.
 * 
 * @author Anand Chelian <achelian@yahoo.com>
 */
public class InvertGraph<D, N extends Node<D, N>> implements Consumer<N> {
  IdentityHashMap<N, N> reverseNodes = new IdentityHashMap<>();
  Set<N> reverseRoots = new HashSet<>();

  /**
   * {@inheritDoc}
   * 
   * @param node
   */
  @Override
  public void accept(N node) {
    N reverseNode = reverseNodes.get(node);
    if (reverseNode == null) {
      reverseNode = cloneWithoutConnections(node);
    }
    if (node.getChildren() != null && !node.getChildren().isEmpty()) {
      for (N child : node.getChildren()) {
        N reverseChild = reverseNodes.get(child);
        if (reverseChild == null) {
          reverseChild = cloneWithoutConnections(child);
        }
        reverseChild.addChild(reverseNode);
      }
    }
    else {
      reverseRoots.add(reverseNode);
    }
  }

  private N cloneWithoutConnections(N node) throws RuntimeException {
    try {
      @SuppressWarnings("unchecked")
      N reverseNode = (N) node.clone();
      reverseNode.setChildren(new HashSet<>());
      reverseNodes.put(node, reverseNode);
      return reverseNode;
    }
    catch (CloneNotSupportedException e) {
      throw new RuntimeException(e);
    }
  }

}
