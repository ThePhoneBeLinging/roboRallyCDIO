package dk.dtu.compute.se.pisd.roborally.model.BoardElements;

import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Space;
import javafx.scene.image.Image;

/**
 * @author Frederik
 */
public class Gear extends NullBoardElement
{
    private final boolean isClockwise;

    /**
     * @author Frederik
     * @param isWalkable
     * @param space
     * @param isClockwise
     */
    public Gear(boolean isWalkable, Space space, boolean isClockwise)
    {
        super(isWalkable, space);
        this.isClockwise = isClockwise;
        if(this.isClockwise) {
            setImage(new Image("file:src/main/resources/images/gearRight.png"));
        }else {
            setImage(new Image("file:src/main/resources/images/gearLeft.png"));
        }
        space.board.addBoardElement(Board.GEARS_INDEX, this);
    }

    /**
     * @author Frederik
     */
    public void activate()
    {
        if (isClockwise)
        {
            getSpace().getPlayer().setHeading(getSpace().getPlayer().getHeading().next());
        }
        else
        {
            getSpace().getPlayer().setHeading(getSpace().getPlayer().getHeading().prev());
        }
    }
}