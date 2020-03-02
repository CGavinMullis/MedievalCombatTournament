package com.github.mct.tournament;

//Import Random Generator
import java.util.Random;

//Import WeaponArchetype Class
import com.github.mct.combat.weapons.WeaponArchetype;

//Import Fighter Class
import com.github.mct.combat.Fighter;

//Import Jester Class
import com.github.mct.ui.Jester;

// for preventing null input parameters
import org.jetbrains.annotations.NotNull;

/**
 * Match stores Fighters, determines the winner of a fight, and uses Jester to comment on the status of the match.
 *
 * @author Brianna Bell
 *
 */
public class Match {

    private Fighter fighterA;       // both fighters for the match
    private Fighter fighterB;       // both fighters for the match
    private Jester jester;          // jester for commentary
    private Fighter winner;         // winner of the match
    Random rand;                    // random number generator for dice rolling

    /**
     * Constructor for a match that creates two Fighters with a type of Weapon based on the Tournament Archetype
     * and a Jester.
     * @param type the type of the tournament so that fighters will be evenly matched
     */
    public Match(TournamentArchetype type)
    {
        //Create Fighters with Weapons based on Tournament Archetype
        switch(type)
        {
            case SHORT:         // initialize both fighters with short weapons
                this.fighterA = new Fighter(WeaponArchetype.SHORT);
                this.fighterB = new Fighter(WeaponArchetype.SHORT);
                break;
            case MEDIUM:        // initialize both fighters with medium weapons
                this.fighterA = new Fighter(WeaponArchetype.MEDIUM);
                this.fighterB = new Fighter(WeaponArchetype.MEDIUM);
                break;
            case LONG:          // initialize both fighters with long weapons
                this.fighterA = new Fighter(WeaponArchetype.LONG);
                this.fighterB = new Fighter(WeaponArchetype.LONG);
                break;
            case WILD:          // randomly Select Two Weapon Types
                this.fighterA = new Fighter(getRandomEnum());
                this.fighterB = new Fighter(getRandomEnum());
                break;
        }

        SetAttributes(this.fighterA);       // set attributes on each fighter
        SetAttributes(this.fighterB);

        this.jester = new Jester();         // create Jester to comment on the match
        rand = new Random();                // seed random number generator
    }

    /**
     * Overloaded constructor for semifinals and finals, using winners from previous matches
     * @param firstFighter the winner from a previous match
     * @param secondFighter the winner from a previous match
     */
    public Match(@NotNull Fighter firstFighter, @NotNull Fighter secondFighter)
    {
        this.fighterA = new Fighter(firstFighter);          // deep copy to local fighter
        this.fighterB = new Fighter(secondFighter);         // deep copy to local fighter

        this.jester = new Jester();         // create Jester to comment on the match
        rand = new Random();                // seed random number generator
    }

    /**
     * This function begins the match and signals Jester to Comment throughout.
     */
    public void playMatch()
    {
        this.jester.commentOnStart();       // Jester comments before match begins

        winner = new Fighter(Combat());     // winner is determined through combat

        this.jester.commentOnEnd();         // Jester comments after match ends
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

    /**
     * Sets new attributes on fighter as dice rolls of a six-sided die
     * @param fighter the newly initialized fighter with no attributes yet
     */
    private void SetAttributes(@NotNull Fighter fighter)
    {
        int strength = rand.nextInt(5) + 1;         // determined by a roll of a six-sided die
        int reach = rand.nextInt(5) + 1;
        int speed = rand.nextInt(5) + 1;
        fighter.StoreAttributes(strength, reach, speed);   // store generated attributes on this fighter
    }

    /**
     * Runs rounds of combat until a winner is determined
     * @return the winning fighter
     */
    private Fighter Combat()
    {
        Fight();            // opponents attack each other


        while( !(fighterA.IsDefeated() || fighterB.IsDefeated()) )  // while both fighters are undefeated
        {
            // if either opponent is half health, signal to Jester that it's the middle of the match
            if(fighterA.IsBloodied() || fighterB.IsBloodied()) { this.signalMiddleToJester(); }
            Fight();        // fighters keep fighting
        }

        if(fighterA.IsDefeated() && fighterB.IsDefeated())      // if the match is a tie
        {
            return Combat();        // redo the match
        }
        else if(fighterA.IsDefeated() && !fighterB.IsDefeated())    // if fighterB is undefeated
        {
            return fighterB;                                    // return the winner as fighterB
        }
        else {
            return fighterA;                                    // return the winner as fighterA
        }
    }

    /**
     * Compares the strengths of the two fighters and increment attack ratings
     */
    private void SetAttackRatings()
    {
        if(fighterB.isStrongerThan(fighterA))
        {
            fighterB.IncrementAttackRating();
        }
        else if (fighterA.isStrongerThan(fighterB))
        {
            fighterA.IncrementAttackRating();
        }
    }

    /**
     * Compares the reaches of the two fighters and increment defense ratings
     */
    private void SetDefenseRatings()
    {
        if(fighterB.canReachFartherThan((fighterA)))
        {
            fighterB.IncrementDefenseRating();
        }
        else if (fighterA.canReachFartherThan(fighterB))
        {
            fighterA.IncrementDefenseRating();
        }
    }

    /**
     * Compares the speed of the two fighters and increment both ratings
     */
    private void SetBoth()
    {
        if(fighterA.GetWeaponArchetype() != fighterB.GetWeaponArchetype())
        {
            if(fighterA.isFasterThan(fighterB))
            {
                fighterA.IncrementAttackRating();
                fighterA.IncrementDefenseRating();
            }
            else if(fighterB.isFasterThan(fighterA))
            {
                fighterB.IncrementAttackRating();
                fighterB.IncrementDefenseRating();
            }
        }
    }

    /**
     * Checks ratings of both opponents and each fighter takes damage
     */
    private void Fight()
    {
        SetAttackRatings();     // check strength and set attack rating
        SetDefenseRatings();    // check reach and set defense rating
        SetBoth();              // check speed and set both ratings

        // each fighter takes damage equal to their opponent's attack minus their defense
        fighterA.TakeDamage(fighterB.GetAttackPerformance() - fighterA.GetDefensePerformance());
        fighterB.TakeDamage(fighterA.GetAttackPerformance() - fighterB.GetDefensePerformance());
    }
}
