package com.github.mct.tournament;

import java.util.ArrayList;

/**
 * Tournament stores SubTournaments and determines the winner of a Tournament
 *
 * @author Gavin Mullis
 *
 */
public class Tournament {
    ArrayList<SubTournament> subTournaments;

    /**
     * Constructor for Tournament Class
     *
     * Creates 4 subTournaments
     */
    public Tournament()
    {
        //Creates SubTournaments
        for(int i = 0; i < 4; i++)
        {
            SubTournament temp = new SubTournament(i);
            if(temp != null)
            {
                this.subTournaments.add(temp);
            }

        }
    }

    /**
     * This function determines the winner after all sub tournaments have been run.
     */
    public void determineWinner()
    {
        //Code
    }

    /**
     * This function sets up the tournament
     */
    public void initializeTournament()
    {
        //Code
    }

}
