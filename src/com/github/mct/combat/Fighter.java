package com.github.mct.combat;

//Import WeaponArchetype Class
import com.github.mct.combat.weapons.WeaponArchetype;

// Randomization for dice rolling
import java.util.Random;

/**
 * Fighter stores stats, weapon, and comparison functions.
 *
 * @author Brianna Bell
 *
 */
public class Fighter{

    private int strength;           // how strong the fighter is
    private int reach;              // how far the fighter can reach
    private int speed;              // how fast the fighter is
    private int attackRating;       // attack rating of fighter
    private int defenseRating;      // defense rating of fighter
    private String name;            // name of fighter
    private Weapon weapon; // fighter's weapon
    Random rand;                    // random number generator for dice rolling

    /**
     * Constructor for a fighter that sets the weapon and seeds the random number generator for rolling the dice.
     */
    public Fighter(Weapon Weapon)
    {
        rand = new Random();            // seed random number generator
        weapon = Weapon;                // set fighter's weapon
    }

    /**
     * Sets the fighter's stats using input parameters.
     */
    public void StoreAttributes(int Strength, int Reach, int Speed) {
        strength = Strength;
        reach = Reach;
        speed = Speed;
    }

    /**
     * Increments attack rating.
     */
    public void IncrementAttackRating(){
        attackRating++;
    }
    /**
     * Increments defense rating.
     */
    public void IncrementDefenseRating(){
        defenseRating++;
    }

    /**
     * Checks current fighter's strength score against opponent fighter's strength score.
     */
    public boolean isStrongerThan(Fighter opponent){
        if (strength > opponent.GetStrength()) return true;
        else return false;
    }
    /**
     * Checks current fighter's reach score against opponent fighter's reach score.
     */
    public boolean canReachFartherThan(Fighter opponent){
        if (reach > opponent.GetReach()) return true;
        else return false;
    }
    /**
     * Checks current fighter's speed score against opponent fighter's speed score.
     */
    public boolean isFasterThan(Fighter opponent){
        if (speed > opponent.GetSpeed()) return true;
        else return false;
    }
    /**
     * For every point of attack of current weapon, roll a d6 and add to attack performance sum.
     *
     * @return sum of six sided dice rolls for every attack point on weapon
     */
    public int GetAttackPerformance(){
        int attackPerf = 0;                                 // initialize sum of attack performance to zero
        for(int i = 0; i < weapon.GetAttack(); i++)
        {
            attackPerf += rand.nextInt(5) + 1;              // returns a random integer of 0-5 and then adds 1 to get a value from 1-6
        }
        return attackPerf;                                  // return sum of attack performance
    }

    /**
     * For every point of defense of current weapon, roll a d6 and add to defense performance sum.
     *
     * @return sum of six sided dice rolls for every defense point on weapon
     */
    public int GetDefensePerformance(){
        int defensePerf = 0;                                 // initialize sum of defense performance to zero
        for(int i = 0; i < weapon.GetDefense(); i++)
        {
            defensePerf += rand.nextInt(5) + 1;             // returns a random integer of 0-5 and then adds 1 to get a value from 1-6
        }
        return defensePerf;                                 // return sum of attack performance
    }

    /**
     * Public accessor for strength.
     *
     * @return Returns strength.
     */
    public int GetStrength(){
        return strength;
    }

    /**
     * Public accessor for reach.
     *
     * @return Returns reach.
     */
    public int GetReach(){
        return reach;
    }

    /**
     * Public accessor for speed.
     *
     * @return Returns speed.
     */
    public int GetSpeed(){
        return speed;
    }

    /**
     * Public accessor for attack rating.
     *
     * @return Returns attack rating.
     */
    public int GetAttackRating(){
        return attackRating;
    }

    /**
     * Public accessor for defense rating.
     *
     * @return Returns defense rating.
     */
    public int GetDefenseRating(){
        return defenseRating;
    }
}