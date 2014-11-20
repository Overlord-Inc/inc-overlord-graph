/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inc.overlord.graph;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * Assumes a DAG is passed in with a set of roots. This is guaranteed
 * to visit each node exactly once at the shortest point from a root.
 * @author Anand Chelian <achelian@yahoo.com>
 * @param <D>
 * @param <N>
 */
public class BreadthFirstTraversal<D, N extends Node<D, N>> implements Spliterator<N> {
    final Set<N> roots;
    final LinkedList<N> queue = new LinkedList<>();
    final Iterator<N> iterator;
    final Set<N> visited = new HashSet<>();
    BreadthFirstTraversal(final Set<N> roots) {
        this.roots = roots;
        this.queue.addAll(roots);
        this.iterator = queue.iterator();
    }

    @Override
    public boolean tryAdvance(Consumer<? super N> action) {
        if (iterator.hasNext()) {
            N node = iterator.next();
            if (!visited.contains(node)) {
                queue.addAll(node.getChildren());
                visited.add(node);
            }
            action.accept(node);
            return true;
        }
        return false;
    }

    @Override
    public void forEachRemaining(Consumer<? super N> action) {
        while (iterator.hasNext()) {
            N node = iterator.next();
            if (!visited.contains(node)) {
                queue.addAll(node.getChildren());
                visited.add(node);
            }
            action.accept(node);
        }
    }
    /**
     * 
     * @return 
     */
    @Override
    public Spliterator<N> trySplit() {
        return null;
    }

    /**
     * Don't know how many nodes are actually there.
     * @return 
     */
    @Override
    public long estimateSize() {
        return Long.MAX_VALUE;
    }

    @Override
    public int characteristics() {
        return IMMUTABLE;
    }
}
