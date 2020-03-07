package com.github.mct.combat;

//Import WeaponArchetype Class
import com.github.mct.combat.weapons.WeaponArchetype;

// Import Weapon Class
import com.github.mct.combat.weapons.Weapon;
import com.github.mct.combat.weapons.WeaponFactory;
//import org.jetbrains.annotations.NotNull;

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
    private Weapon weapon;          // fighter's weapon
   // private WeaponArchetype weaponArchetype;    // fighter's weapon archetype
   // private int damage;             // damage taken
   // Random rand;                    // random number generator for dice rolling

    /**
     * Constructor for a fighter that sets the weapon and seeds the random number generator for rolling the dice.
     */
    public Fighter(String n, Weapon w, int str, int r, int s)
    {
        this.weapon = w;     // set fighter's weapon
        this.name = n;
        this.strength = str;
        this.reach = r;
        this.speed = s;
        this.attackRating = this.weapon.getAttackRating();
        this.defenseRating = this.weapon.getDefenseRating();
    }

    /**
     * Overloaded copy constructor
     * @param fighter the fighter to copy from
     */
    /*
    public Fighter(Fighter fighter)
    {
//        this.weapon = MakeWeapon(fighter.GetWeaponArchetype());     // set fighter's weapon
//        this.weaponArchetype = fighter.GetWeaponArchetype();        // set fighter's weapon archetype
        this.strength = fighter.getStrength();                      // set fighter's strength
        this.reach = fighter.getReach();                            // set fighter's reach
        this.speed = fighter.getSpeed();                            // set fighter's speed
        this.name = fighter.getName();                              // set fighter's name
        this.damage = 0;
    }
/*
    /*****************************FOR DEBUGGING ONLY**************************/
    /*public int GetDamage()
    {
        return damage;
    }
    public int getAttackRating()
    {
        return attackRating;
    }
    public int getDefenseRating()
    {
        return defenseRating;
    }
    /***********************************************************************/

    /**
     * Sets the fighter's stats using input parameters.
     */
    /*
    public void StoreAttributes(int Strength, int Reach, int Speed) {
        strength = Strength;
        reach = Reach;
        speed = Speed;
        name = null;
    }

    /**
     * Increments attack rating.
     */
    /*
    public void IncrementAttackRating(){
        attackRating++;
    }
    /**
     * Increments defense rating.
     */
    /*
    public void IncrementDefenseRating(){
        defenseRating++;
    }
/*
    /**
     * Checks current fighter's strength score against opponent fighter's strength score.
     */
    public boolean StrongerThan(Fighter opponent){
            if (strength > opponent.getStrength()) return true;
            else return false;
    }
    /**
     * Checks current fighter's reach score against opponent fighter's reach score.
     */
    public boolean LongerReachedThan(Fighter opponent){
        if (reach > opponent.getReach()) return true;
        else return false;
    }
    /**
     * Checks current fighter's speed score against opponent fighter's speed score.
     */
    public boolean FasterThan(Fighter opponent){
        if (speed > opponent.getSpeed()) return true;
        else return false;
    }
    /**
     * For every point of attack of current weapon, roll a d6 and add to attack performance sum.
     *
     * @return sum of six sided dice rolls for every attack point on weapon
     */
    /*
    public int getAttackPerformance(){
        int attackPerf = 0;                                 // initialize sum of attack performance to zero
        rand = new Random();                                // seed random number generator
        for(int i = 0; i < attackRating; i++)
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
    /*
    public int getDefensePerformance(){
        int defensePerf = 0;                                // initialize sum of defense performance to zero
        rand = new Random();                                // seed random number generator
        for(int i = 0; i < defenseRating; i++)
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
    public int getStrength(){
        return strength;
    }

    /**
     * Public accessor for reach.
     *
     * @return Returns reach.
     */
    public int getReach(){
        return reach;
    }

    /**
     * Public accessor for speed.
     *
     * @return Returns speed.
     */
    public int getSpeed(){
        return speed;
    }

    /**
     * Public accessor for name.
     *
     * @return Returns name.
     */
    public String getName(){
        return name;
    }

    /**
     * Public accessor for attack rating.
     *
     * @return Returns attack rating.
     */
    public int GetAttackPerformance(){
        return attackRating;
    }

    /**
     * Public accessor for defense rating.
     *
     * @return Returns defense rating.
     */
    public int GetDefensePerformance(){
        return defenseRating;
    }

    public Weapon getWeapon() {return weapon;}

 /*
    /**
     * Increment damage on current fighter
     * @param Damage damage taken
     */
 /*
    public void TakeDamage(int Damage)
    {
        if(Damage > 0) damage += Damage;    // only take damage if it is a positive value
    }

    /**
     * Tells when the fighter is at half health
     * @return if the fighter is bloodied or not
     */
 /*
    public boolean IsBloodied()
    {
        if(damage >= 5) return true;
        else return false;
    }

    /**
     * Tells when the fighter has been defeated
     * @return if the fighter is defeated or not
     */
 /*
    public boolean IsDefeated()
    {
        if(damage >= 10) { return true; }
        else return false;
    }

    /**
     * Public accessor for weapon archetype.
     *
     * @return Returns weapon archetype.
     */
 /*
    public WeaponArchetype GetWeaponArchetype()
    {
        return weaponArchetype;
    } */
}