package com.github.mct.tournament;

import com.github.mct.combat.Fighter;

import java.util.ArrayList;

/**
 * SubTournament stores Matches, the Tournament Archetype, and determines the winner of a SubTournament.
 *
 * @author Gavin Mullis
 *
 */
public class SubTournament {
    private ArrayList<Match> matches;
    private ArrayList<ArrayList<Match>> subMatches;
    private TournamentArchetype archetype;
    private int j;

    /**
     * SubTournament Constructor
     * Creates a SubTournament of numberOfMatches Matches and (2*numberOfMatches) Fighters.
     * The TournamentArchetype passed declares the weapons used in the SubTournament.
     */
    public SubTournament(TournamentArchetype type, int numberOfMatches)
    {
        //Check Range of numberOfMatches
        if(!isPowerOfTwo(numberOfMatches))
        {
            throw new IllegalArgumentException("Number of Matches is not a power of two.");
        }

        //Number of Rounds
        j = 0;

        //Set Archetype
        this.archetype = type;

        //Create ArrayList of Matches
        this.matches = new ArrayList<>();

        //If Not the Final Match
        if(numberOfMatches != 1)
        {
            //Create numberOfMatches Matches with TournamentArchetype
            for (int i = 0; i < numberOfMatches; i++)
            {
                //Create New Match with TournamentArchetype
                Match temp = new Match(this.archetype);

                //Play Match
                temp.playMatch();

                //Add Match to list
                this.matches.add(temp);
            }

            //Add Current Matches to subMatches list
            this.subMatches = new ArrayList<>();
            this.subMatches.add(matches);

            //Use Winners from previous matches for SubSubTournaments

            //Until we are left with 1 match
            while (numberOfMatches != 1)
            {
                //Create new List of Matches
                this.matches = new ArrayList<>();

                //Iterate Across Previous Matches
                for (int i = 0; i < numberOfMatches; i += 2)
                {
                    //Get Winner from Match i & Match i+2
                    Match temp = new Match(subMatches.get(j).get(i).determineWinner(), subMatches.get(j).get(i + 1).determineWinner());

                    //Play Match
                    temp.playMatch();

                    //Add Match to List
                    this.matches.add(temp);
                }

                //Add Matches to subMatches
                this.subMatches.add(this.matches);

                //New Number of Matches
                numberOfMatches = numberOfMatches / 2;

                //New Index of Previous Matches
                j++;
            }

            //Final Match
            this.matches = new ArrayList<>();

            //Get Winner from Match 0 and Match 1
            Match temp = new Match(subMatches.get(j).get(0).determineWinner(), subMatches.get(j).get(1).determineWinner());

            //Play Match
            temp.playMatch();

            //Add Match to List
            this.matches.add(temp);

            //New Index of Previous Matches
            j++;
        }

        //There is only 1 Match
        else
        {
            //Add Current Matches to subMatches list
            this.subMatches = new ArrayList<>();

            //Final Match
            this.matches = new ArrayList<>();

            //Create Random Fighters
            Match temp = new Match(this.archetype);

            //Play Match
            temp.playMatch();

            //Add Match to List
            this.matches.add(temp);
        }

        //Add Match to SubMatches
        this.subMatches.add(matches);
    }

    /**
     * This function determines the winner of a SubTournament and returns a Fighter
     */
    public Fighter determineWinner()
    {
        //Return Winner from Final Match
        return this.subMatches.get(j).get(0).determineWinner();
    }

    /**
     * Returns True if the passed integer is a power of two, Else False
     *
     * @param n Value to be Checked
     * @return Boolean value
     */
    private boolean isPowerOfTwo(int n)
    {
        //0 Matches are not Valid
        if(n == 0)
        {
            return false;
        }

        //Intermediate Value for calculation
        double intermediateVal = (Math.log(n) / Math.log(2));

        //If ceiling and floor are the same value is a power of two
        return (int)(Math.ceil(intermediateVal)) == (int)(Math.floor(intermediateVal));
    }



}
