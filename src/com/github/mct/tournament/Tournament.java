package com.github.mct.tournament;

import java.util.ArrayList;

/**
 * Tournament stores SubTournaments and determines the winner of a Tournament
 *
 * @author Gavin Mullis
 *
 */
public class Tournament {

    /**
     * Stores all SubTournaments in the Tournament
     */
    ArrayList<SubTournament> subTournaments;

    /**
     * Constructor for Tournament Class
     *
     * Creates 4 subTournaments
     */
    public Tournament(int[] numOfMatches)
    {
        //Create ArrayList to hold SubTournaments
        this.subTournaments = new ArrayList<>();

        //Creates First 4 SubTournaments
        for(int i = 0; i < 4; i++)
        {
            TournamentArchetype[] type = TournamentArchetype.values();
            SubTournament temp = new SubTournament(type[i], numOfMatches[i]);
            this.subTournaments.add(temp);
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
