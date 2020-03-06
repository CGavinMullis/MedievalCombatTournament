package com.github.mct.combat.weapons;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class WeaponFactory {
    /**
     * Map that relates the weapon archetype to array list of weapon names
     */
    private Map<WeaponArchetype, List<String>> weaponClosest;

    /**
     * Array list for short weapons
     */
    private List<String> shortWeap;

    /**
     * Array list for medium weapons
     */
    private List<String> medWeap;

    /**
     * Array list for long weapons
     */
    private List<String> longWeap;

    /**
     * Map that relates the weapon name to the weapon attack rating
     */
    private Map<String, Integer> atkRatings;

    /**
     * Map that relates the weapon name to the weapon defense rating
     */
    private Map<String, Integer> defRatings;

    /**
     * Constructor for Weapon Factory
     * Populates the maps
     */
    public WeaponFactory()
    {

        //Create Weapon Closet
        weaponClosest = new HashMap<>();
        //Short weapons
        shortWeap = new ArrayList<>();
        weaponClosest.put(WeaponArchetype.SHORT, shortWeap);
        weaponClosest.get(WeaponArchetype.SHORT).add("Dagger");
        weaponClosest.get(WeaponArchetype.SHORT).add("Cestus");
        weaponClosest.get(WeaponArchetype.SHORT).add("Gladius");
        //Medium weapons
        medWeap = new ArrayList<>();
        weaponClosest.put(WeaponArchetype.MEDIUM, medWeap);
        weaponClosest.get(WeaponArchetype.MEDIUM).add("Staff");
        weaponClosest.get(WeaponArchetype.MEDIUM).add("Hand-and-a-half sword");
        weaponClosest.get(WeaponArchetype.MEDIUM).add("Rapier");
        //Long weapons
        longWeap = new ArrayList<>();
        weaponClosest.put(WeaponArchetype.LONG, longWeap);
        weaponClosest.get(WeaponArchetype.LONG).add("Halberd");
        weaponClosest.get(WeaponArchetype.LONG).add("Lance");
        weaponClosest.get(WeaponArchetype.LONG).add("Two-handed sword");

        //Create the attack ratings
        atkRatings = new HashMap<>();
        //Long weapons
        atkRatings.put("Halberd", 3);
        atkRatings.put("Lance", 1);
        atkRatings.put("Two-handed sword", 2);

        //Create the defense ratings

    }

    /**
     * Function that will create a weapon of the given archetype
     * The specific weapon is randomly selected from the ones that meet the archetype requirement
     * (No unparameterized because a weapon must be provided values)
     *
     * @param arch The weapon archetype
     * //@return Returns a weapon of the given archetype and initialized attack and defense ratings
     */
    public /*Weapon*/void MakeWeapon(WeaponArchetype arch)
    {
        //print all weapons of that type
        if(weaponClosest.containsKey(arch)) {
            System.out.println(weaponClosest.get(arch)); //add .get(i) to access specific member
        }
    }
}
