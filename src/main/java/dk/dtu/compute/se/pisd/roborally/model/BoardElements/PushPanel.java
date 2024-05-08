package dk.dtu.compute.se.pisd.roborally.model.BoardElements;

import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Space;

/**
 * @author Emil
 */
public class PushPanel extends BoardElement
{
    private final Heading orientation;

    public PushPanel(Heading orientation, Space space)
    {
        super(orientation, true, space);
        this.orientation = orientation;
        space.board.addBoardElement(Board.PUSH_PANELS_INDEX, this);
    }

    /**
     * @author Emil
     */
    @Override
    public void activate()
    {
        this.getSpace().getPlayer().moveController.movePlayerAmountOfTimesWithHeading(this.getSpace().getPlayer(),
                orientation, 1);
    }

}
