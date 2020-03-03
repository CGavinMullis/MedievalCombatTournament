package com.github.mct.combat;

//Import WeaponArchetype Class
import com.github.mct.combat.weapons.WeaponArchetype;

// Import Weapon Class
import com.github.mct.combat.weapons.Weapon;
import org.jetbrains.annotations.NotNull;

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
    private WeaponArchetype weaponArchetype;    // fighter's weapon archetype
    private int damage;             // damage taken
    Random rand;                    // random number generator for dice rolling

    /**
     * Constructor for a fighter that sets the weapon and seeds the random number generator for rolling the dice.
     */
    public Fighter(WeaponArchetype weapon_Archetype)
    {
//        this.weapon = MakeWeapon(weapon_Archetype);     // set fighter's weapon
//        this.weaponArchetype = weapon_Archetype;        // set fighter's weapon archetype
        this.damage = 0;
        this.attackRating = 0;
        this.defenseRating = 0;
    }

    /**
     * Overloaded copy constructor
     * @param fighter the fighter to copy from
     */
    public Fighter(@NotNull Fighter fighter)
    {
//        this.weapon = MakeWeapon(fighter.GetWeaponArchetype());     // set fighter's weapon
//        this.weaponArchetype = fighter.GetWeaponArchetype();        // set fighter's weapon archetype
        this.strength = fighter.GetStrength();                      // set fighter's strength
        this.reach = fighter.GetReach();                            // set fighter's reach
        this.speed = fighter.GetSpeed();                            // set fighter's speed
        this.name = fighter.GetName();                              // set fighter's name
        this.damage = 0;
    }

    /*****************************FOR DEBUGGING ONLY**************************/
    public int GetDamage()
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
    public void StoreAttributes(int Strength, int Reach, int Speed) {
        strength = Strength;
        reach = Reach;
        speed = Speed;
        name = GetRandomName();
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
    public boolean isStrongerThan(@NotNull Fighter opponent){
            if (strength > opponent.GetStrength()) return true;
            else return false;
    }
    /**
     * Checks current fighter's reach score against opponent fighter's reach score.
     */
    public boolean canReachFartherThan(@NotNull Fighter opponent){
        if (reach > opponent.GetReach()) return true;
        else return false;
    }
    /**
     * Checks current fighter's speed score against opponent fighter's speed score.
     */
    public boolean isFasterThan(@NotNull Fighter opponent){
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
    public int GetDefensePerformance(){
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
     * Public accessor for name.
     *
     * @return Returns name.
     */
    public String GetName(){
        return name;
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

    /**
     * Chooses name randomly from array of fantasy names
     * @return random name
     */
    private String GetRandomName()
    {
        // fantasy names provided by https://nameberry.com/userlist/view/28759
        rand = new Random();                        // seed random number generator
        String[] names = {"Abrielle","Adair","Adara","Adriel","Aiyana","Alissa","Alixandra","Altair","Amara","Anatola","Anya","Arcadia","Ariadne","Arianwen","Aurelia","Aurelian","Aurelius","Avalon","Acalia","Alaire","Auristela","Bastian","Breena","Brielle","Briallan","Briseis","Cambria","Cara","Carys","Caspian","Cassia","Cassiel","Cassiopeia","Cassius","Chaniel","Cora","Corbin","Cyprian","Daire","Darius","Destin","Drake","Drystan","Dagen","Devlin","Devlyn","Eira","Eirian","Elysia","Eoin","Evadne","Eliron","Evanth","Fineas","Finian","Fyodor","Gareth","Gavriel","Griffin","Guinevere","Gaerwn","Ginerva","Hadriel","Hannelore","Hermione","Hesperos","Iagan","Ianthe","Ignacia","Ignatius","Iseult","Isolde","Jessalyn","Kara","Kerensa","Korbin","Kyler","Kyra","Katriel","Kyrielle","Leala","Leila","Lilith","Liora","Lucien","Lyra","Leira","Liriene","Liron","Maia","Mathieu","Mireille","Mireya","Maylea","Meira","Natania","Nerys","Nuriel","Nyssa","Neirin","Nyfain","Oisin","Oralie","Orion","Orpheus","Ozara","Oleisa","Orinthea","Peregrine","Persephone","Perseus","Petronela","Phelan","Pryderi","Pyralia","Pyralis","Qadira","Quintessa","Quinevere","Raisa","Remus","Rhyan","Rhydderch","Riona","Renfrew","Saoirse","Sarai","Sebastian","Seraphim","Seraphina","Sirius","Sorcha","Saira","Sarielle","Serian","Severin","Tavish","Tearlach","Terra","Thalia","Thaniel","Theia","Torian","Torin","Tressa","Tristana","Uriela","Urien","Ulyssia","Vanora","Vespera","Vasilis","Xanthus","Xara","Xylia","Yadira","Yseult","Yakira","Yeira","Yeriel","Yestin","Zaira","Zephyr","Zora","Zorion","Zaniel","Zarek"};
        int choice = rand.nextInt(166);     // randomly choose one out of the 167 names
        return names[choice];                      // return chosen name
    }

    /**
     * Increment damage on current fighter
     * @param Damage damage taken
     */
    public void TakeDamage(int Damage)
    {
        if(Damage > 0) damage += Damage;    // only take damage if it is a positive value
    }

    /**
     * Tells when the fighter is at half health
     * @return if the fighter is bloodied or not
     */
    public boolean IsBloodied()
    {
        if(damage >= 5) return true;
        else return false;
    }

    /**
     * Tells when the fighter has been defeated
     * @return if the fighter is defeated or not
     */
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
    public WeaponArchetype GetWeaponArchetype()
    {
        return weaponArchetype;
    }
}