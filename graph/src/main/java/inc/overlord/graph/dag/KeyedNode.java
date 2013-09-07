/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inc.overlord.graph.dag;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 *
 * @author Anand Chelian <achelian@yahoo.com>
 */
@Data @EqualsAndHashCode(callSuper = true) @ToString(callSuper = true)
public abstract class KeyedNode<D, K, W extends KeyedNode<D, K, W>> extends Node<D, W> {
    public KeyedNode(D data) {
        super(data);
    }
    public abstract K getKey();

    public Set<K> getChildKeys() {
        if (children != null && !children.isEmpty()) {
            Set<K> result = new HashSet<K>(children.size());
            for (W child: children) {
                result.add(child.getKey());
            }
            return result;
        }
        return Collections.emptySet();
    }
}
