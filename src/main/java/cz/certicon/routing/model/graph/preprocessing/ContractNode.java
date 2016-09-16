/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.certicon.routing.model.graph.preprocessing;

import cz.certicon.routing.model.basic.Pair;
import cz.certicon.routing.model.graph.AbstractNode;
import cz.certicon.routing.model.graph.Graph;
import cz.certicon.routing.model.graph.Metric;
import cz.certicon.routing.model.graph.Node;
import cz.certicon.routing.utils.StringUtils;
import cz.certicon.routing.utils.collections.CollectionUtils;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Michael Blaha {@literal <michael.blaha@certicon.cz>}
 */
public class ContractNode extends AbstractNode<ContractNode, ContractEdge> {

    private final Collection<Node> nodes;

    ContractNode( Graph<ContractNode, ContractEdge> graph, long id, Collection<Node> nodes ) {
        super( graph, id );
        this.nodes = new HashSet<>( nodes );
    }

    public ContractNode mergeWith( ContractNode node, MaxIdContainer nodeMaxIdContainer, MaxIdContainer edgeMaxIdContainer ) {

//        System.out.println( "N-MERGING: graph = " + graph );
//        System.out.println( "N-MERGE " + this );
//        System.out.println( "N-WITH " + node );
        Set<Node> newNodes = new HashSet<>( this.nodes );
        newNodes.addAll( node.nodes );
        ContractNode contractedNode = ( (ContractGraph) getGraph() ).createNode( nodeMaxIdContainer.next(), newNodes );
        Map<ContractNode, Set<ContractEdge>> targetMap = new HashMap<>();
        boolean connected = false;
//        System.out.println( "iterator edges for: " + this );
        for ( ContractEdge edge : getEdges() ) {
            ContractNode target = edge.getOtherNode( this );
            if ( !target.equals( node ) ) {
//                System.out.println( "edge = " + edge + ", target = " + target );
                CollectionUtils.getSet( targetMap, target ).add( edge );
            } else {
                connected = true;
            }
        }
        for ( ContractEdge edge : node.getEdges() ) {
            ContractNode target = edge.getOtherNode( node );
            if ( !target.equals( this ) ) {
//                System.out.println( "edge = " + edge + ", target = " + target );
                CollectionUtils.getSet( targetMap, target ).add( edge );
            }
        }
        for ( Map.Entry<ContractNode, Set<ContractEdge>> entry : targetMap.entrySet() ) {
//            System.out.println( "target map entry = " + entry );
            ContractNode target = entry.getKey();
            Set<ContractEdge> edges = entry.getValue();
            ContractEdge prev = null;
            ContractEdge curr = null;
            for ( ContractEdge edge : edges ) {
                target.removeEdge( edge );
                curr = edge;
                if ( prev != null ) {
                    curr = prev.mergeWith( curr, contractedNode, target, edgeMaxIdContainer.next() );
                } else {
                    curr = ( (ContractGraph) getGraph() ).createEdge( edgeMaxIdContainer.next(), false, contractedNode, target, new HashSet<>( curr.getEdges() ), new Pair<>( Metric.SIZE, edge.getLength( Metric.SIZE ) ) );
                }
//                System.out.println( "curr=" + curr );
                prev = curr;
            }
        }
        getGraph().removeNode( node );
        getGraph().removeNode( this );
//        System.out.println( "N-MERGED NODE " + contractedNode );
//        System.out.println( "N-RESULT: " + graph );
        return contractedNode;
    }

    @Override
    protected String additionalToStringData() {
        return super.additionalToStringData() + ", nodes=" + StringUtils.toArray( nodes );
    }

    public Collection<Node> getNodes() {
        return nodes;
    }

    public static class MaxIdContainer {

        private long maxId;

        public MaxIdContainer( long maxId ) {
            this.maxId = maxId;
        }

        public long getEdgeMaxId() {
            return maxId;
        }

        public long next() {
            return ++maxId;
        }

    }
}
