package dk.dtu.compute.se.pisd.roborally.model.BoardElements;

import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;

public class Conveyor extends NullBoardElement
{
    private final int moveAmount;
    private final Heading orientation;

    Conveyor(Heading orientation, int moveAmount, Space space)
    {
        super(true, space);
        this.orientation = orientation;
        this.moveAmount = moveAmount;
    }

    @Override
    public void activate()
    {
        Player playerToMove = this.getSpace().getPlayer();
        playerToMove.moveController.movePlayerAmountOfTimesWithHeading(playerToMove, orientation, moveAmount);
    }
}
