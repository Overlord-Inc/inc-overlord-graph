/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inc.overlord.graph.dag;

import inc.overlord.graph.dag.BreadthFirstTraversal;
import static org.junit.Assert.*;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

import inc.overlord.common.Collector;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.junit.Test;
import org.junit.Before;
import org.mockito.Mock;

/**
 *
 * @author Anand Chelian <achelian@yahoo.com>
 */
public class BreadthFirstTraversalTest {
    @Mock
    Collector<StringNode, List<StringNode>> collector;
    BreadthFirstTraversal traversal;
    List<StringNode> nodeList;

    @Before
    public void initialize() {
        initMocks(this);
        nodeList = new LinkedList();
        traversal = new BreadthFirstTraversal();
    }

    @Test
    public void testTrivial() {
        StringNode node = new StringNode("1");
        Set<StringNode> graph = Collections.singleton(node);
        traversal.traverse(graph, collector, nodeList);
        verify(collector).collect(eq(node), eq(nodeList));
    }

    /**
     * Test of traverse method, of class BreadthFirstTraversal.
     */
    @Test
    public void testTraverse() {
        StringNode node1 = new StringNode("1"), node2 = new StringNode("2");
        StringNode node3 = new StringNode("3"), node4 = new StringNode("4");
        node1.addChild(node2);
        node1.addChild(node3);
        node2.addChild(node4);
        node3.addChild(node4);
        Set<StringNode> roots = Collections.singleton(node1);
        traversal.traverse(roots, collector, nodeList);
        verify(collector).collect(eq(node1), eq(nodeList));
        verify(collector).collect(eq(node2), eq(nodeList));
        verify(collector).collect(eq(node3), eq(nodeList));
        verify(collector).collect(eq(node4), eq(nodeList));
    }

}
