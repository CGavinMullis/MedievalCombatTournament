package com.github.mct.combat.weapons;

public class Weapon {
    private WeaponArchetype archetype;
    private int attackRating;
    private int defenseRating;

    public Weapon(WeaponArchetype arch, int aR, int dR)
    {
        this.archetype = arch;
        this.attackRating = aR;
        this.defenseRating = dR;
    }

    public WeaponArchetype getArchetype() {
        return archetype;
    }

    public int getAttackRating() {
        return attackRating;
    }

    public int getDefenseRating() {
        return defenseRating;
    }
}
