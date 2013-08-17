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
 */
@Data
public abstract class Node<D, W extends Node<D, W>> {
    final D data;
    Set<W> children;

    public boolean addChild(W child) {
        if (children == null) {
            children = new HashSet<W>();
        }
        return children.add(child);
    }

    public boolean isParent() {
        return children != null && !children.isEmpty();
    }
}
