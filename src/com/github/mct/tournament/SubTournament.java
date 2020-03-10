package com.github.mct.tournament;

//Import ArrayList
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

//Import Fighter Class
import com.github.mct.combat.Fighter;



/**
 * SubTournament stores Matches, the Tournament Archetype, and determines the Winner of a SubTournament.
 *
 * @author Gavin Mullis & Gregory Lofink
 *
 */
public class SubTournament {
    /**
     * Stores the current Round of Matches
     */
    private ArrayList<Match> matches;

    /**
     * Stores all rounds of Matches
     */
    private ArrayList<ArrayList<Match>> subMatches;

    /**
     * Stores the Tournament Archetype of the SubTournament
     */
    private TournamentArchetype archetype;

    /**
     * Stores the number of Matches to be played in the SubTournament
     */
    private int numberOfMatches;

    private ArrayList<Fighter> winners;

    protected int MAX_WIDTH;

    protected String emptySpace;

    protected String lineTemplate;

    protected String windowBorder;

    protected String windowDivider;

    protected String windowContent;

    /**
     * Creates a SubTournament of numberOfMatches Matches and (2*numberOfMatches) Fighters.
     * The TournamentArchetype passed declares the weapons used in the SubTournament.
     * In a Wild SubTournament Fighters can use all Weapon types.
     *
     * @param type TournamentArchetype of the SubTournament to be created.
     * @param numberOfMatches Integer containing the number of matches to be played.
     *                        This integer should be a power of two.
     *                        If the integer is not a power of two it will be decremented until it is a power of two.
     */
    public SubTournament(TournamentArchetype type, int numberOfMatches)
    {
        //Check Range of numberOfMatches
        if(isNotPowerOfTwo(numberOfMatches))
        {
            //Decrement numberOfMatches until it is a power of two
            while(isNotPowerOfTwo(numberOfMatches))
            {
                numberOfMatches--;
            }
        }

        MAX_WIDTH = 150;
        emptySpace = new String(new char[MAX_WIDTH - 3]).replace("\0", " ");
        lineTemplate = '|' + emptySpace + "|\n";
        windowBorder = new String(new char[MAX_WIDTH - 3]).replace("\0", "-");
        windowDivider = new String(new char[MAX_WIDTH - 3]).replace("\0", "_");
        this.winners = new ArrayList<>();
        //Set Number of Matches
        this.numberOfMatches = numberOfMatches;

        //Number of Rounds
        //j = 0;

        //Set Archetype
        this.archetype = type;

        //Create ArrayList of Matches
        this.matches = new ArrayList<>();

        for (int i = 0 ; i < numberOfMatches ; i++)
        {
            Match m = new Match();
            this.matches.add(m);
        }

        //Create ArrayList of ArrayList of Matches.
        this.subMatches = new ArrayList<>();
    }


    /**
     * This function determines the winner of a SubTournament and returns a Fighter
     *
     * @return Fighter that won the SubTournament
     */
    public Fighter determineWinner()
    {
        boolean victor = false;

        while(!victor) {

            for (Match m : this.matches) {
                announceFighter(m.getFighter1(), m.getFighter2());
                promptEnterKey();
                winners.add(m.PlayMatch());
            }

            matches.clear();

            if(winners.size() > 1)
            {
                for(int i = 0 ; i < winners.size() ; i = i + 2)
                {
                    Match m = new Match();
                    m.setFighter1(winners.get(i));
                    m.setFighter2(winners.get(i+1));
                    matches.add(m);
                }
                winners.clear();
            }
            else
            {
                victor = true;
            }
        }

        return winners.get(winners.size()-1);

    }

    /**
     * Returns the TournamentArchetype for the SubTournament
     *
     * @return TournamentArchetype of the tournament
     */
    public TournamentArchetype getArchetype()
    {
        return this.archetype;
    }

    /**
     * Returns the entire arraylist of matches
     *
     * @return Arraylist  of Matches. Contains each round of matches.
     */
    public ArrayList<Match> getMatches()
    {
        return this.matches;
    }

    /**
     * Returns True if the passed integer is NOT a power of two, Else False.
     *
     * @param n Value to be Checked
     * @return Boolean value
     */
    private boolean isNotPowerOfTwo(int n)
    {
        //0 Matches are not Valid
        if(n == 0)
        {
            return true;
        }

        //Intermediate Value for calculation
        double intermediateVal = (Math.log(n) / Math.log(2));

        //If ceiling and floor are the same value is a power of two
        return (int) (Math.ceil(intermediateVal)) != (int) (Math.floor(intermediateVal));
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

    /**
     * This function prints the contents of the menu to the terminal
     */
    private void print()
    {
        String content;

        content = stringInsert(lineTemplate, windowBorder, 1);
        content += windowContent;
        content += stringInsert(lineTemplate, windowDivider, 1);

        clearConsole();
        System.out.print(content);
    }

    /**
     * This function inserts the insert String into the target String at the position specified by position.
     *
     * @param target String being inserted into.
     * @param insert String to be inserted into target String.
     * @param position Integer position specifying where to insert the insert String into the target String.
     *
     * @return Returns target string with insertion from insert string
     */
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

    /**
     * This function prompts the user to press the ENTER key to proceed
     */
    protected void promptEnterKey(){
        System.out.println("Press \"ENTER\" to continue...");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }

    /**
     * This function clears the terminal of the content currently being displayed.
     */
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

    /**
     * This function takes a normal string and returns an ArrayList containing an ASCII art stylized version of that string.
     *
     * @param name
     */
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
}
