package com.github.mct;

import com.github.mct.combat.weapons.Weapon;
import com.github.mct.combat.weapons.WeaponArchetype;
import com.github.mct.combat.weapons.WeaponFactory;

/**
 * Creates, loads, and runs all needed classes for MCT
 *
 *
 * @author SirNocturne
 *
 */
class Main {
    public static void main(String[] args) {

        WeaponArchetype type = WeaponArchetype.SHORT;
        WeaponFactory factory = new WeaponFactory();
        Weapon device = factory.MakeWeapon(type);

        System.out.println("Weapon = " + device.getAttackRating() + ", " + device.getDefenseRating());
    }
}
