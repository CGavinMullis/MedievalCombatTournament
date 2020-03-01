package com.github.mct.tournament;

//Import Random Generator
import java.util.Random;

//Import WeaponArchetype Class
import com.github.mct.combat.weapons.WeaponArchetype;

//Import Fighter Class
import com.github.mct.combat.Fighter;

//Import Jester Class
import com.github.mct.ui.Jester;

/**
 * Match stores Fighters, determines the winner of a fight, and uses Jester to comment on the status of the match.
 *
 * @author Gavin Mullis
 *
 */
public class Match {

    //Two Fighters
    private Fighter fighterA;
    private Fighter fighterB;
    private Jester jester;

    /**
     * Constructor for a match that creates two Fighters with a type of Weapon based on the Tournament Archetype
     * and a Jester.
     */
    public Match(TournamentArchetype type)
    {
        //Create Fighters with Weapons based on Tournament Archetype
        switch(type)
        {
            case SHORT:
                this.fighterA = new Fighter(WeaponArchetype.SHORT);
                this.fighterB = new Fighter(WeaponArchetype.SHORT);
                break;
            case MEDIUM:
                this.fighterA = new Fighter(WeaponArchetype.MEDIUM);
                this.fighterB = new Fighter(WeaponArchetype.MEDIUM);
                break;
            case LONG:
                this.fighterA = new Fighter(WeaponArchetype.LONG);
                this.fighterB = new Fighter(WeaponArchetype.LONG);
                break;
            case WILD:
                //Randomly Select Two Weapon Types
                this.fighterA = new Fighter(getRandomEnum());
                this.fighterB = new Fighter(getRandomEnum());
                break;
        }

        //Create Jester To comment on Match
        this.jester = new Jester();
    }

    /**
     * This function begins the match and signals Jester to Comment throughout.
     */
    public void playMatch()
    {
        //Start Match
        this.jester.commentOnStart();

        //Middle
        this.signalMiddleToJester();

        //Match Complete
        this.jester.commentOnEnd();

    }

    /**
     * This function signals Jester that the match is halfway through
     */
    public void signalMiddleToJester()
    {
        this.jester.commentOnMiddle();
    }

    /**
     * Function Used when creating Fighters in Wild SubTournament
     *
     * @return Returns a random WeaponArchetype
     */
    private WeaponArchetype getRandomEnum()
    {
        //Get Enum Values
        WeaponArchetype[] values = WeaponArchetype.values();
        //Create Random Object
        Random rand = new Random();
        //Get Value in range of enumeration
        int randInt = rand.nextInt(values.length);
        //Select Archetype Value with randInt
        return values[randInt];
    }

}
