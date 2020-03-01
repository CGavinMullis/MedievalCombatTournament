package com.github.mct.tournament;

import java.util.ArrayList;

/**
 * SubTournament stores Matches, the Tournament Archetype, and determines the winner of a SubTournament.
 *
 * @author Gavin Mullis
 *
 */
public class SubTournament {
    private ArrayList<Match> matches;
    private TournamentArchetype archetype;

    /**
     * SubTournament Constructor
     * A SubTournament is a best of 3 style tournament using 1 tournament archetype.
     */
    public SubTournament(int subTournamentNumber)
    {
        if(subTournamentNumber > 3 || subTournamentNumber < 0)
        {
            throw new AssertionError("subTournamentNumber must be between in the range [0,3]!");
        }

        //Get Archetype
        this.archetype = TournamentArchetype.values()[subTournamentNumber];

        //Create 3 Matches with Archetype
        for(int i = 0; i < 3; i++)
        {
            Match temp = new Match(this.archetype);
            if(temp != null)
            {
                this.matches.add(temp);
            }

        }

    }

    /**
     * This function determines the winner of a SubTournament.
     */
    public void determineWinner()
    {
        //Code
    }

}
