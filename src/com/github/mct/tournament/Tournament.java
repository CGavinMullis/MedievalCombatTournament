package com.github.mct.tournament;

//Import Array List
import java.util.ArrayList;
import java.util.Random;

//Import Fighter Class
import com.github.mct.combat.Fighter;
import com.github.mct.combat.weapons.WeaponArchetype;
import com.github.mct.combat.weapons.WeaponFactory;
import com.github.mct.ui.Jester;


/**
 * Tournament stores SubTournaments and determines the winner of a Tournament
 *
 * @author Gavin Mullis
 *
 */
public class Tournament {

    /**
     * Stores all SubTournaments in the Tournament
     */
    private ArrayList<SubTournament> subTournaments;

    /**
     * Matches that spawn from individual sub tournaments
     */
    private ArrayList<Match> semiMatches;

    /**
     * Weapon Factory for creating weapons
     */
    private WeaponFactory WF;

    /**
     * Constructor for Tournament Class
     *
     * Creates SubTournament List
     */
    public Tournament()
    {
        //Create ArrayList to hold SubTournaments
        this.subTournaments = new ArrayList<>();
        this.WF = new WeaponFactory();
        this.semiMatches = new ArrayList<>();
    }

    /**
     * This function runs all SubTournaments, Semi-Finals, and Finals.
     *
     * @return Fighter that won the entire Tournament.
     */
    public Fighter determineWinner()
    {
        //Create ArrayList to Store Fighters From 4 SubTournaments
        ArrayList<Fighter> winners = new ArrayList<>();
        boolean victor = false;
        Match finalMatch;

        //Run All 4 SubTournaments
        for(SubTournament st : subTournaments)
        {
            winners.add(st.determineWinner());
        }

        for(int i = 0 ; i < winners.size() ; i = i + 2)
        {
            Match m = new Match();
            m.setFighter1(winners.get(i));
            m.setFighter2(winners.get(i+1));
            semiMatches.add(m);
        }
        winners.clear();

        for (Match m : this.semiMatches) {
            winners.add(m.PlayMatch());
        }

        finalMatch = new Match();
        finalMatch.setFighter1(winners.get(0));
        finalMatch.setFighter2(winners.get(1));
        winners.clear();

        return finalMatch.PlayMatch();


        /*
        for(int i = 0; i < 4; i++)
        {
            //Add Fighter to FightersList
            winners.add(this.subTournaments.get(i).determineWinner());
        }


        //Create Semi-Finals
        SubTournament semiA = new SubTournament(fighters.get(0), fighters.get(1));
        SubTournament semiB = new SubTournament(fighters.get(2), fighters.get(3));

        //Add Semi-Finals to List of SubTournaments
        subTournaments.add(semiA);
        subTournaments.add(semiB);

        //Create Finals with SemiFinal Winners
        SubTournament finals = new SubTournament(semiA.determineWinner(), semiB.determineWinner());

        //Add Finals to List of SubTournaments
        subTournaments.add(finals);

        //Return Finals Winner
        return finals.determineWinner(); */
    }

    /**
     * This function creates initial 4 SubTournaments SHORT, MEDIUM, LONG, & WILD with numberOfMatches for each.
     *
     * @param numOfMatches An array of length 4 containing integers for the number of matches in each SubTournament.
     *                     Integers correspond in the order [SHORT, MEDIUM, LONG, WILD].
     */
    public void initializeTournament(int[] numOfMatches)
    {
        Fighter f1;
        Fighter f2;
        Random r = new Random();
        //Creates First 4 SubTournaments
        for(int i = 0; i < 3; i++)
        {
            //Get Enumeration values
            TournamentArchetype[] type = TournamentArchetype.values();
            WeaponArchetype[] weaponType = WeaponArchetype.values();

            //Create SubTournament with a unique archetype & numberOfMatches
            SubTournament temp = new SubTournament(type[i], numOfMatches[i]);

            for (Match m : temp.getMatches())
            {
                f1 = new Fighter(GetRandomName(), WF.MakeWeapon(weaponType[i]), (r.nextInt(9)+1),(r.nextInt(9)+1),(r.nextInt(9)+1));
                f2 = new Fighter(GetRandomName(), WF.MakeWeapon(weaponType[i]), (r.nextInt(9)+1),(r.nextInt(9)+1),(r.nextInt(9)+1));
                m.setFighter1(f1);
                m.setFighter2(f2);
            }

            //Add SubTournament to List of SubTournaments
            this.subTournaments.add(temp);
        }

        //Create SubTournament with a unique archetype & numberOfMatches
        SubTournament temp = new SubTournament(TournamentArchetype.WILD, numOfMatches[numOfMatches.length-1]);

        for (Match m : temp.getMatches())
        {
            f1 = new Fighter(GetRandomName(), WF.MakeWeapon(getRandomWeaponType()), (r.nextInt(9)+1),(r.nextInt(9)+1),(r.nextInt(9)+1));
            f2 = new Fighter(GetRandomName(), WF.MakeWeapon(getRandomWeaponType()), (r.nextInt(9)+1),(r.nextInt(9)+1),(r.nextInt(9)+1));
            m.setFighter1(f1);
            m.setFighter2(f2);
        }

        //Add SubTournament to List of SubTournaments
        this.subTournaments.add(temp);

    }

    /**
     * This function returns the list of SubTournaments Run
     *
     * @return ArrayList Containing All SubTournaments Run.
     */
    public ArrayList<SubTournament> getSubTournaments()
    {
        return this.subTournaments;
    }

    /**
     * Chooses name randomly from array of fantasy names
     *
     * @return random name
     */
    private String GetRandomName()
    {
        // fantasy names provided by https://nameberry.com/userlist/view/28759
        Random r = new Random();                        // seed random number generator
        String[] names = {"Abrielle","Adair","Adara","Adriel","Aiyana","Alissa","Alixandra","Altair","Amara","Anatola","Anya","Arcadia","Ariadne","Arianwen","Aurelia","Aurelian","Aurelius","Avalon","Acalia","Alaire","Auristela","Bastian","Breena","Brielle","Briallan","Briseis","Cambria","Cara","Carys","Caspian","Cassia","Cassiel","Cassiopeia","Cassius","Chaniel","Cora","Corbin","Cyprian","Daire","Darius","Destin","Drake","Drystan","Dagen","Devlin","Devlyn","Eira","Eirian","Elysia","Eoin","Evadne","Eliron","Evanth","Fineas","Finian","Fyodor","Gareth","Gavriel","Griffin","Guinevere","Gaerwn","Ginerva","Hadriel","Hannelore","Hermione","Hesperos","Iagan","Ianthe","Ignacia","Ignatius","Iseult","Isolde","Jessalyn","Kara","Kerensa","Korbin","Kyler","Kyra","Katriel","Kyrielle","Leala","Leila","Lilith","Liora","Lucien","Lyra","Leira","Liriene","Liron","Maia","Mathieu","Mireille","Mireya","Maylea","Meira","Natania","Nerys","Nuriel","Nyssa","Neirin","Nyfain","Oisin","Oralie","Orion","Orpheus","Ozara","Oleisa","Orinthea","Peregrine","Persephone","Perseus","Petronela","Phelan","Pryderi","Pyralia","Pyralis","Qadira","Quintessa","Quinevere","Raisa","Remus","Rhyan","Rhydderch","Riona","Renfrew","Saoirse","Sarai","Sebastian","Seraphim","Seraphina","Sirius","Sorcha","Saira","Sarielle","Serian","Severin","Tavish","Tearlach","Terra","Thalia","Thaniel","Theia","Torian","Torin","Tressa","Tristana","Uriela","Urien","Ulyssia","Vanora","Vespera","Vasilis","Xanthus","Xara","Xylia","Yadira","Yseult","Yakira","Yeira","Yeriel","Yestin","Zaira","Zephyr","Zora","Zorion","Zaniel","Zarek"};
        int choice = r.nextInt(166);     // randomly choose one out of the 167 names
        return names[choice];                      // return chosen name
    }

    /**
     * Returns a randomly selected Weapon Archetype
     *
     * @return Weapon Archetype that has been randomly selected
     */
    private static WeaponArchetype getRandomWeaponType() {
        Random random = new Random();
        return WeaponArchetype.values()[random.nextInt(WeaponArchetype.values().length)];
    }

}
