package com.github.mct.combat;

import com.github.mct.combat.weapons.Weapon;


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
}