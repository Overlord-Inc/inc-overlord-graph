/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inc.overlord.graph.dag;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Anand Chelian <achelian@yahoo.com>
 */
public class NodeTest {

    /**
     * Test of getData method, of class Node.
     */
    @Test
    public void testData() {
        String data = "foo";
        StringNode node = new StringNode(data);
        assertEquals(data, node.getData());
    }

    /**
     * Test of getNodes method, of class Node.
     */
    @Test
    public void testGetNodes() {
        String data1 = "data1", data2 = "data2";
        StringNode node1 = new StringNode(data1), node2 = new StringNode(data2);
        Set<StringNode> children = Collections.singleton(node2);
        node1.setChildren(children);
        assertEquals(children, node1.getChildren());
    }

    /**
     * Test of equals method, of class Node.
     */
    @Test
    public void testEquals() {
        String data1 = "data1", data2 = "data2";
        StringNode node1 = new StringNode(null), node2 = new StringNode(null);
        assertTrue(node1.equals(node2));
        node1 = new StringNode(data1);
        assertFalse(node1.equals(node2));
        node2 = new StringNode(data1);
        assertTrue(node1.equals(node2));
        node2 = new StringNode(data2);
        assertFalse(node1.equals(node2));
        String data3 = "data3";
        StringNode node3 = new StringNode(data3);
        Set<StringNode> children = new HashSet<StringNode>(Collections.singleton(node3));
        node1.children = children;
        assertNotEquals(node1, node2);
        node2.children = children;
        // TODO put this back in when collections work correctly
        //assertTrue(node1.equals(node2));
    }

    /**
     * Test of canEqual method, of class Node.
     */
    @Test
    public void testCanEqual() {
        StringNode node1 = new StringNode(null), node2 = new StringNode(null);
        assertTrue(node1.canEqual(node2));
        assertFalse(node1.canEqual(new Object()));
    }

    /**
     * Test of hashCode method, of class Node.
     */
    @Test
    public void testHashCode() {
        StringNode node1 = new StringNode(null), node2 = new StringNode(null);
        assertEquals(node1.hashCode(), node2.hashCode());
        StringNode instance = new StringNode(null);
        int expResult = 1023;
        int result = instance.hashCode();
        assertEquals(expResult, result);
        String data = "data";
        node1 = new StringNode(data);
        node2 = new StringNode(data);
        assertEquals(node1.hashCode(), node2.hashCode());
        StringNode node3 = new StringNode("data3");
        Set<StringNode> children = new HashSet<StringNode>();
        children.add(node3);
        node1.children = children;
        node2.children = children;
        assertEquals(node1.hashCode(), node2.hashCode());
    }

    /**
     * Test of toString method, of class Node.
     */
    @Test
    public void testToString() {
        StringNode node = new StringNode("data");
        String expResult = "StringNode(super=KeyedNode(super=Node(data=data, children=null)))";
        assertEquals(expResult, node.toString());
    }
}
