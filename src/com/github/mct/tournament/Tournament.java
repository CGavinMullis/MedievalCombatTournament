package com.github.mct.tournament;

//Import Array List
import java.util.ArrayList;

//Import Fighter Class
import com.github.mct.combat.Fighter;



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
     * Creates SubTournament List
     */
    public Tournament()
    {
        //Create ArrayList to hold SubTournaments
        this.subTournaments = new ArrayList<>();
    }

    /**
     * This function runs all SubTournaments, Semi-Finals, and Finals.
     *
     * @return Fighter that won the entire Tournament.
     */
    public Fighter determineWinner()
    {
        //Create ArrayList to Store Fighters From 4 SubTournaments
        ArrayList<Fighter> fighters = new ArrayList<>();

        //Run All 4 SubTournaments
        for(int i = 0; i < 4; i++)
        {
            //Add Fighter to FightersList
            fighters.add(this.subTournaments.get(i).determineWinner());
        }

        //Create Semi-Finals
        SubTournament semiA = new SubTournament(fighters.get(0), fighters.get(1));
        SubTournament semiB = new SubTournament(fighters.get(2), fighters.get(3));

        //Add Semi-Finals to List of SubTournaments
        subTournaments.add(semiA);
        subTournaments.add(semiB);

        //Create Finals with SemiFinal Winners
        SubTournament finals = new SubTournament(semiA.determineWinner(), semiB.determineWinner());

        //Add Finals to List of SubTournaments
        subTournaments.add(finals);

        //Return Finals Winner
        return finals.determineWinner();
    }

    /**
     * This function creates initial 4 SubTournaments SHORT, MEDIUM, LONG, & WILD with numberOfMatches for each.
     *
     * @param numOfMatches An array of length 4 containing integers for the number of matches in each SubTournament.
     *                     Integers correspond in the order [SHORT, MEDIUM, LONG, WILD].
     */
    public void initializeTournament(int[] numOfMatches)
    {
        //Creates First 4 SubTournaments
        for(int i = 0; i < 4; i++)
        {
            //Get Enumeration values
            TournamentArchetype[] type = TournamentArchetype.values();

            //Create SubTournament with a unique archetype & numberOfMatches
            SubTournament temp = new SubTournament(type[i], numOfMatches[i]);

            //Add SubTournament to List of SubTournaments
            this.subTournaments.add(temp);
        }
    }

    /**
     * This function returns the list of SubTournaments Run
     *
     * @return ArrayList Containing All SubTournaments Run.
     */
    public ArrayList<SubTournament> getSubTournaments()
    {
        return this.subTournaments;
    }

}
