package dk.dtu.compute.se.pisd.roborally.model.BoardElements.Walls;

import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.BoardElements.BoardElement;
import dk.dtu.compute.se.pisd.roborally.model.BoardElements.ElementsEnum;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Space;
import javafx.scene.image.Image;

/**
 * @author
 */
public class CornerWall extends BoardElement
{
    public final Heading heading2;

    public CornerWall(Heading heading1, Heading heading2, Space space)
    {
        super(heading1, true, space);
        this.heading2 = heading2;
        space.board.addBoardElement(Board.NOT_ACTIVATE_ABLE_INDEX, this);
        if((heading1 == Heading.NORTH && heading2 == Heading.WEST)||(heading1 == Heading.EAST && heading2 == Heading.NORTH)||(heading1 == Heading.SOUTH && heading2 == Heading.EAST)||(heading1 == Heading.WEST && heading2 == Heading.SOUTH)) {
            setImage(new Image("file:src/main/resources/images/cornerWall.png"));
        }
        else {
            setImage(new Image("file:src/main/resources/images/cornerWall2.png"));
        }
        this.setType(ElementsEnum.CORNERWALL);

    }

    public Heading getHeading2()
    {
        return heading2;
    }

    /**
     * @param heading
     * @return boolean that states if the player can walk out of the space that the corner wall is on
     * @author Frederik
     */
    @Override
    public boolean getCanWalkOutOf(Heading heading)
    {
        return getIsWalkable() && (getHeading() != heading && this.heading2 != heading);
    }

    /**
     * @param heading
     * @return boolean that states if the player can walk into the space
     * @author Frederik
     */
    @Override
    public boolean getCanWalkInto(Heading heading)
    {
        Heading headingToCheck = heading.next().next();

        return getIsWalkable() && (getHeading() != headingToCheck || this.heading2 != headingToCheck);
    }
}
