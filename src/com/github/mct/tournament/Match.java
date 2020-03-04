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

    public static void main(String args[])
    {
        Match match = new Match(TournamentArchetype.LONG);
        match.playMatch();
//        int str = match.fighterB.GetStrength();
//        int rea = match.fighterB.GetReach();
//        int spe = match.fighterB.GetSpeed();
//        System.out.println(String.format("Strength: %d",str));
//        System.out.println(String.format("Reach: %d",rea));
//        System.out.println(String.format("Speed: %d",spe));
//        Match match1 = new Match(match.fighterA, match.fighterB);
//
//        int str = match1.fighterB.GetStrength();
//        int rea = match1.fighterB.GetReach();
//        int spe = match1.fighterB.GetSpeed();
//        System.out.println(String.format("Strength: %d",str));
//        System.out.println(String.format("Reach: %d",rea));
//        System.out.println(String.format("Speed: %d",spe));


    }

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
     * Constructor with fighters
     * @param fighterA blah
     * @param fighterB blah
     */
    public Match(Fighter fighterA, Fighter fighterB)
    {

    }


    /**
     * This function begins the match and signals Jester to Comment throughout.
     */
    public void playMatch()
    {
        this.jester.commentOnStart();       // Jester comments before match begins
        System.out.println("Jester says it's started");
        System.out.println(String.format("%s vs %s",fighterA.GetName(),fighterB.GetName()));

        winner = new Fighter(Combat());     // winner is determined through combat
        this.jester.commentOnEnd();         // Jester comments after match ends
        System.out.println("And the winner is...");
        System.out.println(winner.GetName());

    }

    /**
     * This function signals Jester that the match is halfway through
     */
    public void signalMiddleToJester()
    {
        this.jester.commentOnMiddle();
    }

    public Fighter determineWinner()
    {
        return null;
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
        rand = new Random();                        // seed random number generator
        int strength = rand.nextInt(35) + 1;         // determined by a roll of a six-sided die
        rand = new Random();                        // seed random number generator
        int reach = rand.nextInt(35) + 1;
        rand = new Random();                        // seed random number generator
        int speed = rand.nextInt(35) + 1;
        fighter.StoreAttributes(strength, reach, speed);   // store generated attributes on this fighter
    }

    /**
     * Runs rounds of combat until a winner is determined
     * @return the winning fighter
     */
    private Fighter Combat()
    {
        Fight();            // opponents attack each other
        int fightCount = 1;
        while( !fighterA.IsDefeated() && !fighterB.IsDefeated() )  // while both fighters are undefeated
        {
            // if either opponent is half health, signal to Jester that it's the middle of the match
            if(fighterA.IsBloodied() || fighterB.IsBloodied()) {
                System.out.println("Halfway there");

                this.signalMiddleToJester(); }

            System.out.println(String.format("Fighter A's damage: %d",fighterA.GetDamage()));
            System.out.println(String.format("Fighter B's damage: %d",fighterB.GetDamage()));
            fightCount += 1;
            if(fightCount >= 5){
                // jester interferes
            }
            Fight();        // fighters keep fighting
        }

        System.out.println("Final scores:");
        System.out.println(String.format("Fighter A's damage: %d",fighterA.GetDamage()));
        System.out.println(String.format("Fighter B's damage: %d",fighterB.GetDamage()));

        if(fighterA.IsDefeated() && fighterB.IsDefeated())      // if the match is a tie
        {
            System.out.println("It's a tie!");
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
        System.out.println(String.format("Fighter A's strength: %d",fighterA.GetStrength()));
        System.out.println(String.format("Fighter B's strength: %d",fighterB.GetStrength()));

        if(fighterB.isStrongerThan(fighterA))
        {
            System.out.println("Fighter B is stronger than Fighter A");
            fighterB.IncrementAttackRating();
        }
        else if (fighterA.isStrongerThan(fighterB))
        {
            System.out.println("Fighter A is stronger than Fighter B");
            fighterA.IncrementAttackRating();
        }
    }

    /**
     * Compares the reaches of the two fighters and increment defense ratings
     */
    private void SetDefenseRatings()
    {
        System.out.println(String.format("Fighter A's Reach: %d",fighterA.GetReach()));
        System.out.println(String.format("Fighter B's Reach: %d",fighterB.GetReach()));

        if(fighterB.canReachFartherThan((fighterA)))
        {
            System.out.println("Fighter B reaches farther than Fighter A");
            fighterB.IncrementDefenseRating();
        }
        else if (fighterA.canReachFartherThan(fighterB))
        {
            System.out.println("Fighter A reaches farther than Fighter B");
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
            System.out.println(String.format("Fighter A's speed: %d",fighterA.GetSpeed()));
            System.out.println(String.format("Fighter B's speed: %d",fighterB.GetSpeed()));

            if(fighterA.isFasterThan(fighterB))
            {
                System.out.println("Fighter A is faster than Fighter B");
                fighterA.IncrementAttackRating();
                fighterA.IncrementDefenseRating();
            }
            else if(fighterB.isFasterThan(fighterA))
            {
                System.out.println("Fighter B is faster than Fighter A");
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
        System.out.println("\nFight begins!");

        SetAttackRatings();     // check strength and set attack rating
        SetDefenseRatings();    // check reach and set defense rating
        SetBoth();              // check speed and set both ratings

        System.out.println(String.format("Fighter A's attack rating: %d",fighterA.GetAttackRating()));
        System.out.println(String.format("Fighter A's defense rating: %d",fighterA.GetDefenseRating()));
        System.out.println(String.format("Fighter B's attack rating: %d",fighterB.GetAttackRating()));
        System.out.println(String.format("Fighter B's defense rating: %d",fighterB.GetDefenseRating()));


        int bap = fighterB.GetAttackPerformance();
        int adp = fighterA.GetDefensePerformance();
        int aap = fighterA.GetAttackPerformance();
        int bdp = fighterB.GetDefensePerformance();


        System.out.println(String.format("Fighter B's Attack Performance: %d",bap));
        System.out.println(String.format("Fighter A's Defense Performance: %d",adp));
        System.out.println(" ");
        System.out.println(String.format("Fighter A's Attack Performance: %d",aap));
        System.out.println(String.format("Fighter B's Defense Performance: %d",bdp));

        // each fighter takes damage equal to their opponent's attack minus their defense
//        fighterA.TakeDamage(fighterB.GetAttackPerformance() - fighterA.GetDefensePerformance());
//        fighterB.TakeDamage(fighterA.GetAttackPerformance() - fighterB.GetDefensePerformance());
        fighterA.TakeDamage(bap - adp);
        fighterB.TakeDamage(aap - bdp);

    }
}
