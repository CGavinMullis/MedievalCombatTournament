package com.github.mct.tournament;

import java.util.*;
import com.github.mct.combat.Fighter;
import com.github.mct.combat.weapons.WeaponArchetype;
import com.github.mct.combat.weapons.WeaponFactory;


/**
 * Tournament stores SubTournaments and determines the winner of a Tournament
 *
 * @author Gavin Mullis & Gregory Lofink
 *
 */
public class Tournament {

    /**
     * Stores all SubTournaments in the Tournament
     */
    private ArrayList<SubTournament> subTournaments;

    private ArrayList<Match> semiMatches;

    private WeaponFactory WF;

    protected int MAX_WIDTH;

    protected String emptySpace;

    protected String lineTemplate;

    protected String windowBorder;

    protected String windowDivider;

    protected String windowContent;

    /**
     * Constructor for Tournament Class
     *
     * Creates SubTournament List
     */
    public Tournament()
    {
        MAX_WIDTH = 150;
        emptySpace = new String(new char[MAX_WIDTH - 3]).replace("\0", " ");
        lineTemplate = '|' + emptySpace + "|\n";
        windowBorder = new String(new char[MAX_WIDTH - 3]).replace("\0", "-");
        windowDivider = new String(new char[MAX_WIDTH - 3]).replace("\0", "_");
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
            announceTournament(st.getArchetype());
            promptEnterKey();
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

        announceSemis();
        promptEnterKey();
        for (Match m : this.semiMatches) {
            announceFighter(m.getFighter1(), m.getFighter2());
            promptEnterKey();
            winners.add(m.PlayMatch());
        }

        announceFinals();
        promptEnterKey();

        finalMatch = new Match();
        finalMatch.setFighter1(winners.get(0));
        finalMatch.setFighter2(winners.get(1));
        winners.clear();

        announceFighter(finalMatch.getFighter1(), finalMatch.getFighter2());
        promptEnterKey();
        subTournaments.clear();
        semiMatches.clear();
        return finalMatch.PlayMatch();
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

    private static WeaponArchetype getRandomWeaponType() {
        Random random = new Random();
        return WeaponArchetype.values()[random.nextInt(WeaponArchetype.values().length)];
    }

    private void announceFighter(Fighter f1, Fighter f2)
    {
        String lineUp = new String();
        String temp;
        ArrayList<String> f1Name = generateStylizedName(f1.getName());
        ArrayList<String> vs = generateStylizedName("0");
        ArrayList<String> f2Name = generateStylizedName(f2.getName());

        for (int i = 0; i < vs.size() ; i++)
        {
            temp = f1Name.get(i) + "   " + vs.get(i) + "   " + f2Name.get(i);
            if (temp.length() > MAX_WIDTH - 2)
            {
                temp = temp.substring(0,MAX_WIDTH-6);
            }
            temp = stringInsert(lineTemplate, temp, MAX_WIDTH/2 - (temp.length()/2)-1);
            lineUp += temp;
        }
        this.windowContent = lineUp;
        print();
    }
    private ArrayList<String> generateStylizedName(String name)
    {
        Map<Character, ArrayList<String>> stylizedLetters = new HashMap<>();
        ArrayList<String> finalName = new ArrayList<>();
        ArrayList<Character> nameAsCharArray = new ArrayList<>();

        for (char c : name.toCharArray())
        {
            nameAsCharArray.add(c);
        }


        ArrayList<String> sA = new ArrayList<>();
        sA.add("  ___  ");
        sA.add(" / _ \\ ");
        sA.add("/ /_\\ \\");
        sA.add("|  _  |");
        sA.add("| | | |");
        sA.add("\\_| |_/");

        ArrayList<String> sB = new ArrayList<>();
        sB.add("______ ");
        sB.add("| ___ \\");
        sB.add("| |_/ /");
        sB.add("| ___ \\");
        sB.add("| |_/ /");
        sB.add("\\____/ ");

        ArrayList<String> sC = new ArrayList<>();
        sC.add(" _____ ");
        sC.add("/  __ \\");
        sC.add("| /  \\/");
        sC.add("| |    ");
        sC.add("| \\__/\\");
        sC.add(" \\____/");

        ArrayList<String> sD = new ArrayList<>();
        sD.add("______ ");
        sD.add("|  _  \\");
        sD.add("| | | |");
        sD.add("| | | |");
        sD.add("| |/ / ");
        sD.add("|___/  ");

        ArrayList<String> sE = new ArrayList<>();
        sE.add(" _____ ");
        sE.add("|  ___|");
        sE.add("| |__  ");
        sE.add("|  __| ");
        sE.add("| |___ ");
        sE.add("\\____/ ");

        ArrayList<String> sF = new ArrayList<>();
        sF.add("______ ");
        sF.add("|  ___|");
        sF.add("| |_   ");
        sF.add("|  _|  ");
        sF.add("| |    ");
        sF.add("\\_|    ");

        ArrayList<String> sG = new ArrayList<>();
        sG.add(" _____ ");
        sG.add("|  __ \\");
        sG.add("| |  \\/");
        sG.add("| | __ ");
        sG.add("| |_\\ \\");
        sG.add(" \\____/");

        ArrayList<String> sH = new ArrayList<>();
        sH.add(" _   _ ");
        sH.add("| | | |");
        sH.add("| |_| |");
        sH.add("|  _  |");
        sH.add("| | | |");
        sH.add("\\_| |_/");

        ArrayList<String> sI = new ArrayList<>();
        sI.add(" _____ ");
        sI.add("|_   _|");
        sI.add("  | |  ");
        sI.add("  | |  ");
        sI.add(" _| |_ ");
        sI.add(" \\___/ ");

        ArrayList<String> sJ = new ArrayList<>();
        sJ.add("   ___ ");
        sJ.add("  |_  |");
        sJ.add("    | |");
        sJ.add("    | |");
        sJ.add("/\\__/ /");
        sJ.add("\\____/ ");

        ArrayList<String> sK = new ArrayList<>();
        sK.add(" _   __");
        sK.add("| | / /");
        sK.add("| |/ / ");
        sK.add("|    \\ ");
        sK.add("| |\\  \\");
        sK.add("\\_| \\_/");

        ArrayList<String> sL = new ArrayList<>();
        sL.add(" _     ");
        sL.add("| |    ");
        sL.add("| |    ");
        sL.add("| |    ");
        sL.add("| |____");
        sL.add("\\_____/");

        ArrayList<String> sM = new ArrayList<>();
        sM.add("___  ___");
        sM.add("|  \\/  |");
        sM.add("| .  . |");
        sM.add("| |\\/| |");
        sM.add("| |  | |");
        sM.add("\\_|  |_/");

        ArrayList<String> sN = new ArrayList<>();
        sN.add(" _   _ ");
        sN.add("| \\ | |");
        sN.add("|  \\| |");
        sN.add("| . ` |");
        sN.add("| |\\  |");
        sN.add("\\_| \\_/");

        ArrayList<String> sO = new ArrayList<>();
        sO.add(" _____ ");
        sO.add("|  _  |");
        sO.add("| | | |");
        sO.add("| | | |");
        sO.add("\\ \\_/ /");
        sO.add(" \\___/ ");

        ArrayList<String> sP = new ArrayList<>();
        sP.add("______ ");
        sP.add("| ___ \\");
        sP.add("| |_/ /");
        sP.add("|  __/ ");
        sP.add("| |    ");
        sP.add("\\_|    ");

        ArrayList<String> sQ = new ArrayList<>();
        sQ.add(" _____ ");
        sQ.add("|  _  |");
        sQ.add("| | | |");
        sQ.add("| | | |");
        sQ.add("\\ \\/' /");
        sQ.add(" \\_/\\_\\");

        ArrayList<String> sR = new ArrayList<>();
        sR.add("______ ");
        sR.add("| ___ \\");
        sR.add("| |_/ /");
        sR.add("|    / ");
        sR.add("| |\\ \\ ");
        sR.add("\\_| \\_|");

        ArrayList<String> sS = new ArrayList<>();
        sS.add(" _____ ");
        sS.add("/  ___|");
        sS.add("\\ `--. ");
        sS.add(" `--. \\");
        sS.add("/\\__/ /");
        sS.add("\\____/ ");

        ArrayList<String> sSLower = new ArrayList<>();
        sSLower.add("     ");
        sSLower.add("     ");
        sSLower.add(" ___ ");
        sSLower.add("/ __|");
        sSLower.add("\\__ \\");
        sSLower.add("|___/");

        ArrayList<String> sT = new ArrayList<>();
        sT.add(" _____ ");
        sT.add("|_   _|");
        sT.add("  | |  ");
        sT.add("  | |  ");
        sT.add("  | |  ");
        sT.add("  \\_/  ");

        ArrayList<String> sU = new ArrayList<>();
        sU.add(" _   _ ");
        sU.add("| | | |");
        sU.add("| | | |");
        sU.add("| | | |");
        sU.add("| |_| |");
        sU.add(" \\___/ ");

        ArrayList<String> sV = new ArrayList<>();
        sV.add(" _   _ ");
        sV.add("| | | |");
        sV.add("| | | |");
        sV.add("| | | |");
        sV.add("\\ \\_/ /");
        sV.add(" \\___/ ");

        ArrayList<String> sVLower = new ArrayList<>();
        sVLower.add("       ");
        sVLower.add("       ");
        sVLower.add("__   __");
        sVLower.add("\\ \\ / /");
        sVLower.add(" \\ V / ");
        sVLower.add("  \\_/  ");

        ArrayList<String> sW = new ArrayList<>();
        sW.add(" _    _ ");
        sW.add("| |  | |");
        sW.add("| |  | |");
        sW.add("| |/\\| |");
        sW.add("\\  /\\  /");
        sW.add(" \\/  \\/ ");

        ArrayList<String> sX = new ArrayList<>();
        sX.add("__   __");
        sX.add("\\ \\ / /");
        sX.add(" \\ V / ");
        sX.add(" /   \\ ");
        sX.add("/ /^\\ \\");
        sX.add("\\/   \\/");

        ArrayList<String> sY = new ArrayList<>();
        sY.add("__   __");
        sY.add("\\ \\ / /");
        sY.add(" \\ V / ");
        sY.add("  \\ /  ");
        sY.add("  | |  ");
        sY.add("  \\_/  ");

        ArrayList<String> sZ = new ArrayList<>();
        sZ.add(" ______");
        sZ.add("|___  /");
        sZ.add("   / / ");
        sZ.add("  / /  ");
        sZ.add("./ /___");
        sZ.add("\\_____/");

        ArrayList<String> s0 = new ArrayList<>();
        s0.add("             ");
        s0.add("             ");
        s0.add("__   __  ___ ");
        s0.add("\\ \\ / / / __|");
        s0.add(" \\ V /  \\__ \\");
        s0.add("  \\_/   |___/");

        ArrayList<String> sSpace = new ArrayList<>();
        sSpace.add("  ");
        sSpace.add("  ");
        sSpace.add("  ");
        sSpace.add("  ");
        sSpace.add("  ");
        sSpace.add("  ");

        ArrayList<String> sDash = new ArrayList<>();
        sDash.add("        ");
        sDash.add("        ");
        sDash.add(" ______ ");
        sDash.add("|______|");
        sDash.add("        ");
        sDash.add("        ");



        stylizedLetters.put('A',sA);
        stylizedLetters.put('B',sB);
        stylizedLetters.put('C',sC);
        stylizedLetters.put('D',sD);
        stylizedLetters.put('E',sE);
        stylizedLetters.put('F',sF);
        stylizedLetters.put('G',sG);
        stylizedLetters.put('H',sH);
        stylizedLetters.put('I',sI);
        stylizedLetters.put('J',sJ);
        stylizedLetters.put('K',sK);
        stylizedLetters.put('L',sL);
        stylizedLetters.put('M',sM);
        stylizedLetters.put('N',sN);
        stylizedLetters.put('O',sO);
        stylizedLetters.put('P',sP);
        stylizedLetters.put('Q',sQ);
        stylizedLetters.put('R',sR);
        stylizedLetters.put('S',sS);
        stylizedLetters.put('T',sT);
        stylizedLetters.put('U',sU);
        stylizedLetters.put('V',sV);
        stylizedLetters.put('W',sW);
        stylizedLetters.put('X',sX);
        stylizedLetters.put('Y',sY);
        stylizedLetters.put('Z',sZ);

        stylizedLetters.put('a',sA);
        stylizedLetters.put('b',sB);
        stylizedLetters.put('c',sC);
        stylizedLetters.put('d',sD);
        stylizedLetters.put('e',sE);
        stylizedLetters.put('f',sF);
        stylizedLetters.put('g',sG);
        stylizedLetters.put('h',sH);
        stylizedLetters.put('i',sI);
        stylizedLetters.put('j',sJ);
        stylizedLetters.put('k',sK);
        stylizedLetters.put('l',sL);
        stylizedLetters.put('m',sM);
        stylizedLetters.put('n',sN);
        stylizedLetters.put('o',sO);
        stylizedLetters.put('p',sP);
        stylizedLetters.put('q',sQ);
        stylizedLetters.put('r',sR);
        stylizedLetters.put('s',sS);
        stylizedLetters.put('t',sT);
        stylizedLetters.put('u',sU);
        stylizedLetters.put('v',sV);
        stylizedLetters.put('w',sW);
        stylizedLetters.put('x',sX);
        stylizedLetters.put('y',sY);
        stylizedLetters.put('z',sZ);

        stylizedLetters.put('0',s0);
        stylizedLetters.put(' ',sSpace);
        stylizedLetters.put('-',sDash);


        int count = 0;
        for (String s : stylizedLetters.get(nameAsCharArray.get(0)))
        {
            finalName.add(s);
        }
        for(int i = 1; i < nameAsCharArray.size() ; i++)
        {
            for(String s : stylizedLetters.get(nameAsCharArray.get(i)))
            {
                finalName.set(count, finalName.get(count) + " " + s );
                count++;
            }
            count = 0;
        }
        return finalName;

    }

    private void announceTournament(TournamentArchetype type)
    {
        ArrayList<String> currTourn = generateStylizedName(type.toString());
        ArrayList<String> sWeapon = generateStylizedName("WEAPON");
        ArrayList<String> sTournament = generateStylizedName("TOURNAMENT");
        String temp;
        String announcement = new String();
        for (int i = 0; i < currTourn.size() ; i++)
        {
            temp = stringInsert(lineTemplate, currTourn.get(i), MAX_WIDTH/2 - (currTourn.get(i).length()/2)-1);
            announcement += temp;
        }

        for (int i = 0; i < sWeapon.size() ; i++)
        {
            temp = stringInsert(lineTemplate, sWeapon.get(i), MAX_WIDTH/2 - (sWeapon.get(i).length()/2)-1);
            announcement += temp;
        }

        for (int i = 0; i < sWeapon.size() ; i++)
        {
            temp = stringInsert(lineTemplate, sTournament.get(i), MAX_WIDTH/2 - (sTournament.get(i).length()/2)-1);
            announcement += temp;
        }

        this.windowContent = announcement;
        print();
    }

    private void announceSemis()
    {
        ArrayList<String> currTourn = generateStylizedName("Semi-Finals");
        String temp;
        String announcement = new String();

        for (int i = 0; i < currTourn.size() ; i++)
        {
            temp = stringInsert(lineTemplate, currTourn.get(i), MAX_WIDTH/2 - (currTourn.get(i).length()/2)-1);
            announcement += temp;
        }
        this.windowContent = announcement;
        print();
    }

    private void announceFinals()
    {
        ArrayList<String> currTourn = generateStylizedName("Finals");
        String temp;
        String announcement = new String();

        for (int i = 0; i < currTourn.size() ; i++)
        {
            temp = stringInsert(lineTemplate, currTourn.get(i), MAX_WIDTH/2 - (currTourn.get(i).length()/2)-1);
            announcement += temp;
        }
        this.windowContent = announcement;
        print();
    }

    private void print()
    {
        String content;

        content = stringInsert(lineTemplate, windowBorder, 1);
        content += windowContent;
        content += stringInsert(lineTemplate, windowDivider, 1);

        clearConsole();
        System.out.print(content);
    }

    protected String stringInsert(String target, String insert, int position)
    {
        int length = target.length();
        int i = position;

        StringBuilder builder = new StringBuilder(target);

        for (char c : insert.toCharArray())
        {
            builder.setCharAt(position, c);
            position++;
        }

        return builder.toString();
    }

    protected void promptEnterKey(){
        System.out.println("Press \"ENTER\" to continue...");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }

    protected static void clearConsole(){
        try
        {
            final String os = System.getProperty("os.name");

            if (os.contains("Windows"))
            {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                //Runtime.getRuntime().exec("cls");
            }
            else
            {
                Runtime.getRuntime().exec("clear");
            }
        }
        catch (final Exception e)
        {
            System.out.println("Error: " + e.getMessage());
        }
    }

}
