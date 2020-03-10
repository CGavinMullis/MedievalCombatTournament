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
     * Constructor for fighter that sets up base stats, weapon, and name.
     * @param name the name of the fighter
     * @param weapon the fighter's weapon
     * @param str the fighter's strength
     * @param rea the fighter's reach
     * @param spe the fighter's speed
     */
    public Fighter(String name, Weapon weapon, int str, int rea, int spe)
    {
        // sets the stats on the current fighter to input arguments
        this.weapon = weapon;
        this.name = name;
        this.strength = str;
        this.reach = rea;
        this.speed = spe;
        // sets the rating of the fighter to the rating of their weapon
        this.attackRating = this.weapon.getAttackRating();
        this.defenseRating = this.weapon.getDefenseRating();
    }

    /**
     * Checks the strength of the current fighter against strength of opponent
     * @param opponent the opposing fighter being compared to
     * @return if the current fighter is stronger than the opponent fighter
     */
    public boolean StrongerThan(Fighter opponent){
        return strength > opponent.getStrength();
    }

    /**
     * Checks the reach of the current fighter against reach of opponent
     * @param opponent the opposing fighter being compared to
     * @return if the current fighter reaches farther than the opponent fighter
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
     * Accessor for fighter's reach
     * @return reach value from 1 to 10
     */
    public int getReach(){
        return reach;
    }

    /**
     * Accessor for fighter's speed
     * @return speed value from 1 to 10
     */
    public int getSpeed(){
        return speed;
    }

    /**
     * Accessor for fighter's name
     * @return name of the fighter
     */
    public String getName(){
        return name;
    }

    /**
     * Accessor for fighter's attack performance
     * @return attack performance value
     */
    public int GetAttackPerformance(){
        return attackRating;
    }

    /**
     * Accessor for fighter's defense performance
     * @return defense performance value
     */
    public int GetDefensePerformance(){
        return defenseRating;
    }

    /**
     * Accessor for fighter's weapon
     * @return fighter's weapon
     */
    public Weapon getWeapon(){
        return weapon;
    }

}