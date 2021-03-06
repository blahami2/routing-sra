/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.certicon.routing.algorithm.sara.preprocessing.overlay;

import lombok.Getter;

/**
 * Border data shared by exit and entry Zero border edges at level 0.
 * @author Blahoslav Potoček <potocek@merica.cz>
 */
public class ZeroBorder extends BorderData<ZeroNode, ZeroEdge> {

    /**
     * lift over exitEdge.source for forward (source->targte) direction
     */
    @Getter
    private final OverlayLift exit1;

    /**
     * lift over exitEdge.target for forward (source->targte) direction
     */
    @Getter
    private final OverlayLift entry1;

    /**
     * lift over entryEdge.target for backward (target->source) direction
     */
    @Getter
    private final OverlayLift exit2;

    /**
     * lift over entryEdge.source for backward (target->source) direction
     */
    @Getter
    private final OverlayLift entry2;

    public ZeroBorder(ZeroEdge exit, ZeroEdge entry, boolean doubleWay) {
        super(exit, entry);

        this.exit1 = new OverlayLift(exit, true, true);
        this.entry1 = new OverlayLift(entry, true, false);

        if (doubleWay) {
            this.exit2 = new OverlayLift(entry, false, true);
            this.entry2 = new OverlayLift(exit, false, false);
        } else {
            this.exit2 = null;
            this.entry2 = null;
        }
    }
}
