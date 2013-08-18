/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inc.overlord.graph;

import inc.overlord.common.interfaces.Collector;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * Assumes a DAG is passed in with a set of roots. This is guaranteed
 * to visit each node exactly once at the shortest point from a root.
 * @author Anand Chelian <achelian@yahoo.com>
 */
// TODO replace this with stream once Java 1.8 is out
public class BreadthFirstTraversal {
    public <Data, NodeWrapper extends Node<Data, NodeWrapper>, State> void traverse(Set<NodeWrapper> roots, Collector<NodeWrapper, State> collector, State state) {
        Queue<NodeWrapper> queue = new LinkedList<NodeWrapper>();
        queue.addAll(roots);
        Set<NodeWrapper> visited = new HashSet<NodeWrapper>();
        while (!queue.isEmpty()) {
            NodeWrapper node = queue.remove();
            if (!visited.contains(node)) {
                if (node.isParent()) {
                    queue.addAll(node.getChildren());
                }
                collector.collect(node, state);
                visited.add(node);
            }
        }
    }
}
