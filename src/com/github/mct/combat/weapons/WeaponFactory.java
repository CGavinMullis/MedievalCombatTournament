package com.github.mct.combat.weapons;
import java.util.*;

public class WeaponFactory {

    private Map<String, int[]> ratings;

    private Map<WeaponArchetype, ArrayList<String>> weaponTypes;

    public Weapon getWeapon(WeaponArchetype type)
    {
        weaponTypes = new HashMap<>();
        weaponTypes.put(WeaponArchetype.LONG,new ArrayList<String>(Arrays.asList("Hallberd", "Lance", "Two-handed sword")));
        weaponTypes.put(WeaponArchetype.MEDIUM,new ArrayList<String>(Arrays.asList("Staff", "Hand-and-a-half sword", "Rapier")));
        weaponTypes.put(WeaponArchetype.SHORT,new ArrayList<String>(Arrays.asList("Dagger", "Cestus", "Gladius")));

        ratings = new HashMap<>();
        // Long Weapons
        ratings.put("Hallberd",new int[]{3,0});
        ratings.put("Lance", new int[]{1,2});
        ratings.put("Two-handed sword", new int[]{2,2});
        // Medium Weapons
        ratings.put("Staff", new int[]{1,3});
        ratings.put("Hand-and-a-half sword", new int[]{3,2});
        ratings.put("Rapier", new int[]{3,1});
        // Short Weapons
        ratings.put("Dagger", new int[]{4,1});
        ratings.put("Cestus", new int[]{5,0});
        ratings.put("Gladius", new int[]{3,3});

        String weaponName = getRandomWeapon(type);
        int attrat, deffrat;
        attrat = ratings.get(weaponName)[0];
        deffrat = ratings.get(weaponName)[1];

        return new Weapon(type,attrat,deffrat);
    }

    private String getRandomWeapon(WeaponArchetype type) {
        Random random = new Random();
        return this.weaponTypes.get(type).get(random.nextInt(this.weaponTypes.get(type).size()));
    }
}
