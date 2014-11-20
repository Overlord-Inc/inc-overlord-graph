/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inc.overlord.graph;

import java.util.HashSet;
import java.util.Set;
import lombok.Data;

/**
 *
 * @author Anand Chelian <achelian@yahoo.com>
 * @param <D> The data type contained.
 * @param <W> The node.
 */
@Data
public abstract class Node<D, W extends Node<D, W>> implements Cloneable {
    final D data;
    Set<W> children;

    protected Node(D data) {
      this.data = data;
    }

    public D getData() {
        return data;
    }

    public boolean addChild(W child) {
        if (children == null) {
            children = new HashSet<>();
        }
        return children.add(child);
    }

    public Set<W> getChildren() {
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
