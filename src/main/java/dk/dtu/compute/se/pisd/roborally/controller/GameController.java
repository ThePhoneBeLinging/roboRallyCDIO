/*
 *  This file is part of the initial project provided for the
 *  course "Project in Software Development (02362)" held at
 *  DTU Compute at the Technical University of Denmark.
 *
 *  Copyright (C) 2019, 2020: Ekkart Kindler, ekki@dtu.dk
 *
 *  This software is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; version 2 of the License.
 *
 *  This project is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this project; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 */
package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.roborally.model.*;
import dk.dtu.compute.se.pisd.roborally.view.PlayerView;
import org.jetbrains.annotations.NotNull;

/**
 * ...
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 */
public class GameController
{
    final public Board board;
    final public MoveController moveController;

    public GameController(@NotNull Board board)
    {
        this.board = board;
        this.moveController = new MoveController(this);
    }

    /**
     * This is just some dummy controller operation to make a simple move to see something
     * happening on the board. This method should eventually be deleted!
     *
     * @param space the space to which the current player should move
     * @author Elias
     * @author Emil
     * @return void
     */
    public void moveCurrentPlayerToSpace(@NotNull Space space)
    {
        if (space.getPlayer() == null)
        {
            board.getCurrentPlayer().setSpace(space);
            board.setCurrentPlayer(board.getPlayer(board.getPlayerNumber(board.getCurrentPlayer()) + 1));
            board.setStep(board.getStep() + 1);
        }

    }

    /**
     * Starts Activation phase. This method should be called when the players have pressed finished programming button.
     */
    // XXX: implemented in the current version
    public void finishProgrammingPhase()
    {

        makeProgramFieldsInvisible();
        makeProgramFieldsVisible(0);
        board.setPhase(Phase.ACTIVATION);
        board.setCurrentPlayer(board.getPlayer(0));
        board.setStep(0);
    }

    // XXX: implemented in the current version
    private void makeProgramFieldsInvisible()
    {
        for (int i = 0; i < board.getPlayersNumber(); i++)
        {
            Player player = board.getPlayer(i);
            for (int j = 0; j < Player.NO_REGISTERS; j++)
            {
                CommandCardField field = player.getProgramField(j);
                field.setVisible(false);
            }
        }
    }

    // XXX: implemented in the current version
    private void makeProgramFieldsVisible(int register)
    {
        if (register >= 0 && register < Player.NO_REGISTERS)
        {
            for (int i = 0; i < board.getPlayersNumber(); i++)
            {
                Player player = board.getPlayer(i);
                CommandCardField field = player.getProgramField(register);
                field.setVisible(true);
            }
        }
    }

    /**
     * Executes the registers of the players. This method should be called when the players have pressed the execute registers button.
     * @return void
     */
    // XXX: implemented in the current version
    public void executePrograms() {
    board.setStepMode(false);
    continuePrograms();
}


    /**
     * Continues the execution of the programs of the players. This method should be called when the
     * @return void
     */
    // XXX: implemented in the current version
    public void continuePrograms()
    {
        do
        {
            executeNextStep();
        }
        while (board.getPhase() == Phase.ACTIVATION && !board.isStepMode());
    }

    // XXX: implemented in the current version
    private void executeNextStep() {
        Player currentPlayer = board.getCurrentPlayer();
        if (board.getPhase() == Phase.ACTIVATION && currentPlayer != null) {
            int step = board.getStep();
            if (step >= 0 && step < Player.NO_REGISTERS) {
                CommandCard card = currentPlayer.getProgramField(step).getCard();
                if (card != null) {
                    Command command = card.command;
                    executeCommand(currentPlayer, command);
                }
                int nextPlayerNumber = board.getPlayerNumber(currentPlayer) + 1;
                if (nextPlayerNumber < board.getPlayersNumber()) {
                    board.setCurrentPlayer(board.getPlayer(nextPlayerNumber));
                } else {
                    step++;
                    if (step < Player.NO_REGISTERS) {
                        makeProgramFieldsVisible(step);
                        board.setStep(step);
                        board.setCurrentPlayer(board.getPlayer(0));
                    } else {
                        startProgrammingPhase();
                    }
                }
            } else {
                // this should not happen
                assert false;
            }
        } else {
            // this should not happen
            assert false;
        }
    }


    // XXX: implemented in the current version
    private void executeCommand(@NotNull Player player, Command command)
    {
        if (player.board == board && command != null)
        {
            // XXX This is a very simplistic way of dealing with some basic cards and
            //     their execution. This should eventually be done in a more elegant way
            //     (this concerns the way cards are modelled as well as the way they are executed).

            switch (command)
            {
                case FORWARD:
                    this.moveForward(player);
                    break;
                case RIGHT:
                    this.turnRight(player);
                    break;
                case LEFT:
                    this.turnLeft(player);
                    break;
                case FAST_FORWARD:
                    this.fastForward(player);
                    break;
                default:
                    // DO NOTHING (for now)
            }
        }
    }

    /**
     * Starts the programming phase of the game. This method should be called when the game
     * @return void
     */
    // XXX: implemented in the current version
    public void startProgrammingPhase()
    {
        board.setPhase(Phase.PROGRAMMING);
        board.getPhase();
        board.setCurrentPlayer(board.getPlayer(0));
        board.setStep(0);

        for (int i = 0; i < board.getPlayersNumber(); i++)
        {
            Player player = board.getPlayer(i);
            if (player != null)
            {
                for (int j = 0; j < Player.NO_REGISTERS; j++)
                {
                    CommandCardField field = player.getProgramField(j);
                    field.setCard(null);
                    field.setVisible(true);
                }
                for (int j = 0; j < Player.NO_CARDS; j++)
                {
                    CommandCardField field = player.getCardField(j);
                    field.setCard(generateRandomCommandCard());
                    field.setVisible(true);
                }
            }
        }
    }

    /**
     * Moves the player one step forward in the direction of the player's heading.
     * @author Adel
     * @param player the player to be moved
     * @return void
     */
    public void moveForward(@NotNull Player player) {
        Space currentSpace = player.getSpace();
        Heading heading = player.getHeading();
        Space newSpace=board.getNeighbour(currentSpace, heading);
        player.setSpace(newSpace);
    }

    public void moveFowardRepeatedly(@NotNull Player player, int amountOfMoves)
    {
        for (int i = 0; i < amountOfMoves; i++)
        {
            moveForward(player);
        }
    }

    /**
     * Turns the player to the right by changing the player's heading to the next heading in the enumeration.
     * @param player the player to be turned
     * @author Mustafa
     * @return void
     */
    public void turnRight(@NotNull Player player)
    {
        Heading heading = player.getHeading();
        player.setHeading(heading.next());

    }
    /**
     * Turns the player to the left by changing the player's heading to the previous heading in the enumeration.
     * @param player the player to be turned
     * @author Mustafa
     * @return void
     */
    public void turnLeft(@NotNull Player player)
    {
        Heading heading = player.getHeading();
        player.setHeading(heading.prev());
    }
    /**
     * Moves the player two steps forward in the direction of the player's heading.
     * @param player the player to be moved
     * @author Adel
     * @return void
     */
    public void fastForward(@NotNull Player player)
    {
        moveFowardRepeatedly(player,2);
    }

    // XXX: implemented in the current version
    private CommandCard generateRandomCommandCard()
    {
        Command[] commands = Command.values();
        int random = (int) (Math.random() * commands.length);
        return new CommandCard(commands[random]);
    }

    // XXX: implemented in the current version
    public void executeStep()
    {
        board.setStepMode(true);
        continuePrograms();
    }

    public boolean moveCards(@NotNull CommandCardField source, @NotNull CommandCardField target)
    {
        CommandCard sourceCard = source.getCard();
        CommandCard targetCard = target.getCard();
        if (sourceCard != null && targetCard == null)
        {
            target.setCard(sourceCard);
            source.setCard(null);
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * A method called when no corresponding controller operation is implemented yet. This
     * should eventually be removed.
     */
    public void notImplemented()
    {
        // XXX just for now to indicate that the actual method is not yet implemented
        assert false;
    }

}
