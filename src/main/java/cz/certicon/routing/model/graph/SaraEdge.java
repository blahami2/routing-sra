/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.certicon.routing.model.graph;

/**
 * {@link Edge} implementation for the {@link SaraGraph}.
 *
 * @author Michael Blaha {@literal <blahami2@gmail.com>}
 */
public class SaraEdge extends AbstractEdge<SaraNode, SaraEdge> {

    protected SaraEdge(Graph<SaraNode, SaraEdge> graph, long id, boolean oneway, SaraNode source, SaraNode target, int sourceIndex, int targetIndex) {
        super( graph, id, oneway, source, target, sourceIndex, targetIndex );
    }

    @Override
    protected SaraEdge newInstance( Graph<SaraNode, SaraEdge> newGraph, long id, boolean oneway, SaraNode newSource, SaraNode newTarget, int sourceIndex, int targetIndex ) {
        return new SaraEdge( newGraph, id, oneway, newSource, newTarget, sourceIndex, targetIndex );
    }

}
