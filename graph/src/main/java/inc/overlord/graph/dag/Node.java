/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inc.overlord.graph.dag;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;

/**
 *
 * @author Anand Chelian <achelian@yahoo.com>
 * @param <D>
 *          The data type contained.
 * @param <N>
 *          The node.
 */
@Data
public abstract class Node<D, N extends Node<D, N>> implements Cloneable {
  final D data;
  Set<N> children;

  protected Node(D data) {
    this.data = data;
  }

  public D getData() {
    return data;
  }

  public boolean addChild(N child) {
    if (children == null) {
      children = new HashSet<>();
    }
    return children.add(child);
  }

  public Set<N> getChildren() {
    return children;
  }

  public boolean isParent() {
    return children != null && !children.isEmpty();
  }

  @Override
  public Object clone() throws CloneNotSupportedException {
    return super.clone();
  }
}
