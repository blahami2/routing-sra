/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.certicon.routing.model.graph;

import java.util.Comparator;
import java.util.List;

/**
 * Basic implementation of the {@link Node} interface.
 *
 * @author Michael Blaha {@literal <blahami2@gmail.com>}
 */
public class SimpleNode extends AbstractNode<SimpleNode, SimpleEdge> {

    SimpleNode( Graph<SimpleNode, SimpleEdge> graph, long id ) {
        super( graph, id );
    }

    private static String toString( List<SimpleEdge> list ) {
        StringBuilder sb = new StringBuilder();
        sb.append( "[" );
        for ( SimpleEdge edge : list ) {
            sb.append( edge != null ? edge.getId() : "null" ).append( "," );
        }
        sb.replace( sb.length() - 1, sb.length(), "]" );
        return sb.toString();
    }

    public static Comparator<SimpleNode> getIdComparator() {
        return new Comparator<SimpleNode>() {
            @Override
            public int compare( SimpleNode o1, SimpleNode o2 ) {
                return Long.compare( o1.getId(), o2.getId() );
            }
        };
    }

    @Override
    protected SimpleNode newInstance( Graph<SimpleNode, SimpleEdge> newGraph, long id ) {
        return new SimpleNode( newGraph, id );
    }
}
