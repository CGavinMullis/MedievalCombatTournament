package com.github.mct.combat.weapons;

import java.util.*;

/**
 * Class that creates a weapon of the given archetype
 *
 * @author Elizabeth Staley
 */
public class WeaponFactory {
    /**
     * Map that relates the weapon archetype to array list of weapon names
     */
    private Map<WeaponArchetype, ArrayList<String>> weaponClosest;

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
        weaponClosest.put(WeaponArchetype.SHORT, new ArrayList<>());
        weaponClosest.get(WeaponArchetype.SHORT).add("Dagger");
        weaponClosest.get(WeaponArchetype.SHORT).add("Cestus");
        weaponClosest.get(WeaponArchetype.SHORT).add("Gladius");
        //Medium weapons
        weaponClosest.put(WeaponArchetype.MEDIUM, new ArrayList<>());
        weaponClosest.get(WeaponArchetype.MEDIUM).add("Staff");
        weaponClosest.get(WeaponArchetype.MEDIUM).add("Hand-and-a-half sword");
        weaponClosest.get(WeaponArchetype.MEDIUM).add("Rapier");
        //Long weapons
        weaponClosest.put(WeaponArchetype.LONG, new ArrayList<>());
        weaponClosest.get(WeaponArchetype.LONG).add("Halberd");
        weaponClosest.get(WeaponArchetype.LONG).add("Lance");
        weaponClosest.get(WeaponArchetype.LONG).add("Two-handed sword");

        //Create the attack ratings
        atkRatings = new HashMap<>();
        //Short weapons
        atkRatings.put("Dagger", 4);
        atkRatings.put("Cestus", 5);
        atkRatings.put("Gladius", 3);
        //Medium weapons
        atkRatings.put("Staff", 1);
        atkRatings.put("Hand-and-a-half sword", 3);
        atkRatings.put("Rapier", 3);
        //Long weapons
        atkRatings.put("Halberd", 3);
        atkRatings.put("Lance", 1);
        atkRatings.put("Two-handed sword", 2);

        //Create the defense ratings
        defRatings = new HashMap<>();
        //Short weapons
        defRatings.put("Dagger", 1);
        defRatings.put("Cestus", 0);
        defRatings.put("Gladius", 3);
        //Medium weapons
        defRatings.put("Staff", 3);
        defRatings.put("Hand-and-a-half sword", 2);
        defRatings.put("Rapier", 1);
        //Long weapons
        defRatings.put("Halberd", 0);
        defRatings.put("Lance", 2);
        defRatings.put("Two-handed sword", 2);
    }

    /**
     * Function that will create a weapon of the given archetype
     * The specific weapon is randomly selected from the ones that meet the archetype requirement
     * (No unparameterized because a weapon must be provided values)
     *
     * @param arch The weapon archetype
     * @return Returns a weapon of the given archetype and initialized attack and defense ratings
     */
    public Weapon MakeWeapon(WeaponArchetype arch)
    {
        //Get array list of all weapons that satisfy that archetype
        ArrayList<String> temp = new ArrayList<>();
        if(weaponClosest.containsKey(arch)) {
            temp = weaponClosest.get(arch);
        }

        //select random weapon from the given archetype
        Random rand = new Random();
        String weaponName = temp.get(rand.nextInt(temp.size()));

        //Create weapon
        int aR = atkRatings.get(weaponName);
        int dR = defRatings.get(weaponName);

        return new Weapon(arch, aR, dR);
    }
}
