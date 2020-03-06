package com.github.mct.combat.weapons;

/**
 * Class that defines weapon attributes
 *
 * @author Elizabeth Staley
 */
public class Weapon {
    /**
     * Stores the weapon type
     */
    private WeaponArchetype archetype;
    /**
     * Stores the weapon attack rating
     */
    private int attackRating;
    /**
     * Stores the weapon defense rating
     */
    private int defenseRating;

    /**
     * Parameterized Weapon Constructor
     * (No unparameterized because a weapon must be provided values)
     *
     * @param arch Enumeration value of the archetype of weapon
     * @param aR The attack rating
     * @param dR The defense rating
     */
    public Weapon(WeaponArchetype arch, int aR, int dR)
    {
        this.archetype = arch;
        this.attackRating = aR;
        this.defenseRating = dR;
    }

    /**
     * Getter for weapon archetype, no setter because weapons cannot be changed after creation
     */
    public WeaponArchetype getArchetype() {
        return archetype;
    }

    /**
     * Getter for weapon attack rating, no setter because weapons cannot be changed after creation
     */
    public int getAttackRating() {
        return attackRating;
    }

    /**
     * Getter for weapon defense rating, no setter because weapons cannot be changed after creation
     */
    public int getDefenseRating() {
        return defenseRating;
    }
}
