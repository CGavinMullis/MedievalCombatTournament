package com.github.mct.tournament;

//Import ArrayList
import java.util.ArrayList;

//Import Fighter Class
import com.github.mct.combat.Fighter;



/**
 * SubTournament stores Matches, the Tournament Archetype, and determines the Winner of a SubTournament.
 *
 * @author Gavin Mullis
 *
 */
public class SubTournament {
    /**
     * Stores the current Round of Matches
     */
    private ArrayList<Match> matches;

    /**
     * Stores all rounds of Matches
     */
    private ArrayList<ArrayList<Match>> subMatches;

    /**
     * Stores the Tournament Archetype of the SubTournament
     */
    private TournamentArchetype archetype;

    /**
     * Stores the number of Matches to be played in the SubTournament
     */
    private int numberOfMatches;

    private ArrayList<Fighter> winners;

    /**
     * Stores the current round of the SubTournament
     */
    //private int j;

    /**
     * Creates a SubTournament of numberOfMatches Matches and (2*numberOfMatches) Fighters.
     * The TournamentArchetype passed declares the weapons used in the SubTournament.
     * In a Wild SubTournament Fighters can use all Weapon types.
     *
     * @param type TournamentArchetype of the SubTournament to be created.
     * @param numberOfMatches Integer containing the number of matches to be played.
     *                        This integer should be a power of two.
     *                        If the integer is not a power of two it will be decremented until it is a power of two.
     */
    public SubTournament(TournamentArchetype type, int numberOfMatches)
    {
        //Check Range of numberOfMatches
        if(isNotPowerOfTwo(numberOfMatches))
        {
            //Decrement numberOfMatches until it is a power of two
            while(isNotPowerOfTwo(numberOfMatches))
            {
                numberOfMatches--;
            }
        }

        this.winners = new ArrayList<>();
        //Set Number of Matches
        this.numberOfMatches = numberOfMatches;

        //Number of Rounds
        //j = 0;

        //Set Archetype
        this.archetype = type;

        //Create ArrayList of Matches
        this.matches = new ArrayList<>();

        for (int i = 0 ; i < numberOfMatches ; i++)
        {
            Match m = new Match();
            this.matches.add(m);
        }

        //Create ArrayList of ArrayList of Matches.
        this.subMatches = new ArrayList<>();
    }


    /**
     * Constructor for a semi-final or final SubTournament
     *
     * @param fighterA The first Fighter of the SubTournament
     * @param fighterB The second Fighter of the SubTournament
     */
    /*
    public SubTournament(Fighter fighterA, Fighter fighterB)
    {
        //Set Number of Matches to 1
        this.numberOfMatches = 1;

        //Number of Rounds
        j = 0;

        //Set Archetype to Wild
        this.archetype = TournamentArchetype.WILD;

        //Create ArrayList of Matches
        this.matches = new ArrayList<>();

        //Create ArrayList of ArrayList of Matches.
        this.subMatches = new ArrayList<>();
    } */

    /**
     * This function determines the winner of a SubTournament and returns a Fighter
     *
     * @return Fighter that won the SubTournament
     */
    public Fighter determineWinner()
    {
        boolean victor = false;

        while(!victor) {

            for (Match m : this.matches) {
                winners.add(m.PlayMatch());
            }

            matches.clear();

            if(winners.size() > 1)
            {
                for(int i = 0 ; i < winners.size() ; i = i + 2)
                {
                    Match m = new Match();
                    m.setFighter1(winners.get(i));
                    m.setFighter2(winners.get(i+1));
                    matches.add(m);
                }
                winners.clear();
            }
            else
            {
                victor = true;
            }
        }

        return winners.get(winners.size()-1);






        //If Not the Final Match
    /*    if(numberOfMatches != 1)
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
            this.subMatches.add(matches);

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
            //Final Match
            this.matches = new ArrayList<>();

            //Create Random Fighters
            Match temp = new Match(this.archetype);

            //Play Match
            temp.playMatch();

            //Add Match to List
            this.matches.add(temp);
        }

        //Add Last Match to SubMatches
        this.subMatches.add(matches);

        //Return Winner from Final Match
        return this.subMatches.get(j).get(0).determineWinner(); */
    }

    /**
     * Returns the TournamentArchetype for the SubTournament
     *
     * @return TournamentArchetype of the tournament
     */
    public TournamentArchetype getArchetype()
    {
        return this.archetype;
    }

    /**
     * Returns the entire arraylist of subMatches, each index is a list of matches
     *
     * @return Arraylist of Arraylist of Matches. Contains each round of matches.
     */
    public ArrayList<Match> getMatches()
    {
        return this.matches;
    }

    /**
     * Returns True if the passed integer is NOT a power of two, Else False.
     *
     * @param n Value to be Checked
     * @return Boolean value
     */
    private boolean isNotPowerOfTwo(int n)
    {
        //0 Matches are not Valid
        if(n == 0)
        {
            return true;
        }

        //Intermediate Value for calculation
        double intermediateVal = (Math.log(n) / Math.log(2));

        //If ceiling and floor are the same value is a power of two
        return (int) (Math.ceil(intermediateVal)) != (int) (Math.floor(intermediateVal));
    }
}
