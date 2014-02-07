/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inc.overlord.graph;

import inc.overlord.common.Transformer;
import inc.overlord.common.Collector;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;
import lombok.Data;

class TraversalSelectionState<W> {
    final Set<W> roots = new HashSet<W>();
    final Map<W, W> nodeMap = new IdentityHashMap<W, W>();
}

/**
 * Assumes a DAG, and inverts it. Leaves become roots, roots become
 * leaves.
 * @author Anand Chelian <achelian@yahoo.com>
 */
@Data
public class InvertGraph<D, W extends Node<D, W>> implements Collector<W, TraversalSelectionState<W>>, Transformer<Set<W>, Set<W>> {

    final Transformer<W, W> factory;

    /**
     * {@inheritDoc}
     */
    @Override
    public void collect(W node, TraversalSelectionState<W> state) {
        W reverseNode = state.nodeMap.get(node);
        if (reverseNode == null) {
            reverseNode = factory.transform(node);
            state.nodeMap.put(node, reverseNode);
        }
        if (node.getChildren() != null && !node.getChildren().isEmpty()) {
            for (W child: node.getChildren()) {
                W reverseChild = state.nodeMap.get(child);
                if (reverseChild == null) {
                    reverseChild = factory.transform(child);
                    state.nodeMap.put(child, reverseChild);
                }
                reverseChild.addChild(reverseNode);
            }
        }
        else {
            state.roots.add(reverseNode);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<W> transform(Set<W> input) {
        TraversalSelectionState state = new TraversalSelectionState<W>();
        BreadthFirstTraversal traversal = new BreadthFirstTraversal();
        traversal.traverse(input, this, state);
        return state.roots;
    }
}
