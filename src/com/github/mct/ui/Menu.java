package com.github.mct.ui;

import com.github.mct.tournament.Tournament;
import com.github.mct.tournament.TournamentArchetype;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;



/**
 * Displays an interactive text based menu for the user
 *
 *
 * @author SirNocturne
 *
 */

public class Menu {

    private Tournament currTourn;

    private static Menu single_instance = null;

    private int [] numMatches;

    private boolean exit_Flag;

    protected int MAX_WIDTH;

    protected String emptySpace;

    protected String lineTemplate;

    protected String windowBorder;

    protected String windowDivider;

    protected String windowContent;

    protected ArrayList<String> menuOptions;

    protected String currMenu;


    private Menu()
    {
        MAX_WIDTH = 150;
        emptySpace = new String(new char[MAX_WIDTH - 3]).replace("\0", " ");
        lineTemplate = '|' + emptySpace + "|\n";
        windowBorder = new String(new char[MAX_WIDTH - 3]).replace("\0", "-");
        windowDivider = new String(new char[MAX_WIDTH - 3]).replace("\0", "_");
        menuOptions = new ArrayList<>();
        numMatches = new int[4];
        currTourn = new Tournament();
        defaultMenu();
        exit_Flag = false;

        while(!exit_Flag) {
            update();
        }
    }

    public static Menu getInstance()
    {
        if (single_instance == null)
        {
            single_instance = new Menu();
        }

        return single_instance;
    }


    private void print()
    {
        String content;
        String buttonHeader = new String(new char[MAX_WIDTH - 3]).replace("\0", "/");
        int counter = 0;

        content = stringInsert(lineTemplate, windowBorder, 1);
        content += windowContent;
        content += stringInsert(lineTemplate, windowDivider, 1);
        content += stringInsert(lineTemplate, buttonHeader, 1);

        if (menuOptions != null && menuOptions.size() != 0) {
            for (String button : menuOptions) {
                counter++;
                content += stringInsert(lineTemplate, counter + ") " + button, MAX_WIDTH / 16);
            }
            
            content += stringInsert(lineTemplate, windowBorder, 1);
            content += "| Button Select(1-" + (counter) + "): ";
        }
        clearConsole();
        System.out.print(content);
    }

    private void optionSelect(String menu)
    {
        Scanner in = new Scanner(System.in);
        String selection;
        selection = in.nextLine();

        if(!isNumeric(selection))
        {
            System.out.print("Input must be an integer...\n");
            promptEnterKey();
            update();
            return;
        }

        switch (menu) {
            case "main":
                switch (selection) {
                case "1":
                    matchMenu();
                    tournamentMenu();
                    break;
                case "2":
                    exit_Flag = true;
                    return;
                default:
                    System.out.println("| Button selection must be an integer value from 1 through " + menuOptions.size());
                    promptEnterKey();
                }
                break;
            case "tournament":
                switch (selection) {
                    case "1":
                        clearConsole();
                        announceVictor(currTourn.determineWinner().getName());
                        promptEnterKey();
                        defaultMenu();
                        return;
                    case "2":
                        defaultMenu();
                        return;
                    default:
                        System.out.println("| Button selection must be an integer value from 1 through " + menuOptions.size());
                        promptEnterKey();
                }
                break;
        }
    }

    private void defaultMenu()
    {
        String title;

        String titleA = "         ,---.   ,'|\"\\   ,-.,---..-.   .-..--.  ,-.    ";
        String titleB = "|\\    /| | .-'   | |\\ \\  |(|| .-' \\ \\ / // /\\ \\ | |    ";
        String titleC = "|(\\  / | | `-.   | | \\ \\ (_)| `-.  \\ V // /__\\ \\| |    ";
        String titleD = "(_)\\/  | | .-'   | |  \\ \\| || .-'   ) / |  __  || |    ";
        String titleE = "| \\  / | |  `--. /(|`-' /| ||  `--.(_)  | |  |)|| `--. ";
        String titleF = "| |\\/| | /( __.'(__)`--' `-'/( __.'     |_|  (_)|( __.'";
        String titleG = "'-'  '-'(__)               (__)                 (_)    ";

        title = stringInsert(lineTemplate, titleA, (MAX_WIDTH/2) - (titleA.length()/2)-1);
        title += stringInsert(lineTemplate, titleB, (MAX_WIDTH/2) - (titleB.length()/2)-1);
        title += stringInsert(lineTemplate, titleC, (MAX_WIDTH/2) - (titleC.length()/2)-1);
        title += stringInsert(lineTemplate, titleD, (MAX_WIDTH/2) - (titleD.length()/2)-1);
        title += stringInsert(lineTemplate, titleE, (MAX_WIDTH/2) - (titleE.length()/2)-1);
        title += stringInsert(lineTemplate, titleF, (MAX_WIDTH/2) - (titleF.length()/2)-1);
        title += stringInsert(lineTemplate, titleG, (MAX_WIDTH/2) - (titleG.length()/2)-1);

        titleA = "  ,--,  .---.           ,---.     .--.  _______ ";
        titleB = ".' .') / .-. ) |\\    /| | .-.\\   / /\\ \\|__   __|";
        titleC = "|  |(_)| | |(_)|(\\  / | | |-' \\ / /__\\ \\ )| |   ";
        titleD = "\\  \\   | | | | (_)\\/  | | |--. \\|  __  |(_) |   ";
        titleE = " \\  `-.\\ `-' / | \\  / | | |`-' /| |  |)|  | |   ";
        titleF = "  \\____\\)---'  | |\\/| | /( `--' |_|  (_)  `-'   ";
        titleG = "       (_)     '-'  '-'(__)                     ";

        title += stringInsert(lineTemplate, titleA, (MAX_WIDTH/2) - (titleA.length()/2)-1);
        title += stringInsert(lineTemplate, titleB, (MAX_WIDTH/2) - (titleB.length()/2)-1);
        title += stringInsert(lineTemplate, titleC, (MAX_WIDTH/2) - (titleC.length()/2)-1);
        title += stringInsert(lineTemplate, titleD, (MAX_WIDTH/2) - (titleD.length()/2)-1);
        title += stringInsert(lineTemplate, titleE, (MAX_WIDTH/2) - (titleE.length()/2)-1);
        title += stringInsert(lineTemplate, titleF, (MAX_WIDTH/2) - (titleF.length()/2)-1);
        title += stringInsert(lineTemplate, titleG, (MAX_WIDTH/2) - (titleG.length()/2)-1);

        titleA = " _______  .---.  .-. .-.,---.    .-. .-.  .--.           ,---.  .-. .-. _______ ";
        titleB = "|__   __|/ .-. ) | | | || .-.\\   |  \\| | / /\\ \\ |\\    /| | .-'  |  \\| ||__   __|";
        titleC = "  )| |   | | |(_)| | | || `-'/   |   | |/ /__\\ \\|(\\  / | | `-.  |   | |  )| |   ";
        titleD = " (_) |   | | | | | | | ||   (    | |\\  ||  __  |(_)\\/  | | .-'  | |\\  | (_) |   ";
        titleE = "   | |   \\ `-' / | `-')|| |\\ \\   | | |)|| |  |)|| \\  / | |  `--.| | |)|   | |   ";
        titleF = "   `-'    )---'  `---(_)|_| \\)\\  /(  (_)|_|  (_)| |\\/| | /( __.'/(  (_)   `-'   ";
        titleG = "         (_)                (__)(__)            '-'  '-'(__)   (__)             ";

        title += stringInsert(lineTemplate, titleA, (MAX_WIDTH/2) - (titleA.length()/2)-1);
        title += stringInsert(lineTemplate, titleB, (MAX_WIDTH/2) - (titleB.length()/2)-1);
        title += stringInsert(lineTemplate, titleC, (MAX_WIDTH/2) - (titleC.length()/2)-1);
        title += stringInsert(lineTemplate, titleD, (MAX_WIDTH/2) - (titleD.length()/2)-1);
        title += stringInsert(lineTemplate, titleE, (MAX_WIDTH/2) - (titleE.length()/2)-1);
        title += stringInsert(lineTemplate, titleF, (MAX_WIDTH/2) - (titleF.length()/2)-1);
        title += stringInsert(lineTemplate, titleG, (MAX_WIDTH/2) - (titleG.length()/2)-1);

        windowContent = title;
        currMenu = "main";

        menuOptions.clear();
        menuOptions.add("Create Tournament");
        menuOptions.add("Exit");
    }

    private void matchMenu()
    {

        TournamentArchetype[] type = TournamentArchetype.values();
        String title;
        Scanner in = new Scanner(System.in);
        String selection;

        String titleA = ",-.    .---.  .-. .-.  ,--,    .-.  .-.,---.    .--.  ,---.   .---.  .-. .-.";
        String titleB = "| |   / .-. ) |  \\| |.' .'     | |/\\| || .-'   / /\\ \\ | .-.\\ / .-. ) |  \\| |";
        String titleC = "| |   | | |(_)|   | ||  |  __  | /  \\ || `-.  / /__\\ \\| |-' )| | |(_)|   | |";
        String titleD = "| |   | | | | | |\\  |\\  \\ ( _) |  /\\  || .-'  |  __  || |--' | | | | | |\\  |";
        String titleE = "| `--.\\ `-' / | | |)| \\  `-) ) |(/  \\ ||  `--.| |  |)|| |    \\ `-' / | | |)|";
        String titleF = "|( __.')---'  /(  (_) )\\____/  (_)   \\|/( __.'|_|  (_)/(      )---'  /(  (_)";
        String titleG = "(_)   (_)    (__)    (__)             (__)           (__)    (_)    (__)    ";

        title = stringInsert(lineTemplate, titleA, (MAX_WIDTH/2) - (titleA.length()/2)-1);
        title += stringInsert(lineTemplate, titleB, (MAX_WIDTH/2) - (titleB.length()/2)-1);
        title += stringInsert(lineTemplate, titleC, (MAX_WIDTH/2) - (titleC.length()/2)-1);
        title += stringInsert(lineTemplate, titleD, (MAX_WIDTH/2) - (titleD.length()/2)-1);
        title += stringInsert(lineTemplate, titleE, (MAX_WIDTH/2) - (titleE.length()/2)-1);
        title += stringInsert(lineTemplate, titleF, (MAX_WIDTH/2) - (titleF.length()/2)-1);
        title += stringInsert(lineTemplate, titleG, (MAX_WIDTH/2) - (titleG.length()/2)-1);

        windowContent = title;
        menuOptions.clear();
        print();

        System.out.print("| Number of matches for the Long Weapon sub-tournament (choice must be a power of two): ");
        selection = in.nextLine();

        while(!isNumeric(selection) || this.isNotPowerOfTwo(Integer.parseInt(selection)))
        {
            System.out.println(" Selection must be an integer of the power of two...");
            promptEnterKey();
            print();
            System.out.print("| Number of matches for the Long Weapon sub-tournament (choice must be a power of two): ");
            selection = in.nextLine();
        }

        numMatches[0] = Integer.parseInt(selection);

        titleA = "         ,---.   ,'|\"\\   ,-..-. .-.         .-.  .-.,---.    .--.  ,---.   .---.  .-. .-.";
        titleB = "|\\    /| | .-'   | |\\ \\  |(|| | | ||\\    /| | |/\\| || .-'   / /\\ \\ | .-.\\ / .-. ) |  \\| |";
        titleC = "|(\\  / | | `-.   | | \\ \\ (_)| | | ||(\\  / | | /  \\ || `-.  / /__\\ \\| |-' )| | |(_)|   | |";
        titleD = "(_)\\/  | | .-'   | |  \\ \\| || | | |(_)\\/  | |  /\\  || .-'  |  __  || |--' | | | | | |\\  |";
        titleE = "| \\  / | |  `--. /(|`-' /| || `-')|| \\  / | |(/  \\ ||  `--.| |  |)|| |    \\ `-' / | | |)|";
        titleF = "| |\\/| | /( __.'(__)`--' `-'`---(_)| |\\/| | (_)   \\|/( __.'|_|  (_)/(      )---'  /(  (_)";
        titleG = "'-'  '-'(__)                       '-'  '-'        (__)           (__)    (_)    (__)    ";

        title = stringInsert(lineTemplate, titleA, (MAX_WIDTH/2) - (titleA.length()/2)-1);
        title += stringInsert(lineTemplate, titleB, (MAX_WIDTH/2) - (titleB.length()/2)-1);
        title += stringInsert(lineTemplate, titleC, (MAX_WIDTH/2) - (titleC.length()/2)-1);
        title += stringInsert(lineTemplate, titleD, (MAX_WIDTH/2) - (titleD.length()/2)-1);
        title += stringInsert(lineTemplate, titleE, (MAX_WIDTH/2) - (titleE.length()/2)-1);
        title += stringInsert(lineTemplate, titleF, (MAX_WIDTH/2) - (titleF.length()/2)-1);
        title += stringInsert(lineTemplate, titleG, (MAX_WIDTH/2) - (titleG.length()/2)-1);

        windowContent = title;
        menuOptions.clear();
        print();

        System.out.print("| Number of matches for the Medium Weapon sub-tournament (choice must be a power of two): ");
        selection = in.nextLine();

        while(!isNumeric(selection) || this.isNotPowerOfTwo(Integer.parseInt(selection)))
        {
            System.out.println(" Selection must be an integer of the power of two...");
            promptEnterKey();
            print();
            System.out.print("| Number of matches for the Medium Weapon sub-tournament (choice must be a power of two): ");
            selection = in.nextLine();
        }

        numMatches[1] = Integer.parseInt(selection);

        titleA = "   .---. .-. .-. .---.  ,---.  _______  .-.  .-.,---.    .--.  ,---.   .---.  .-. .-.";
        titleB = "  ( .-._)| | | |/ .-. ) | .-.\\|__   __| | |/\\| || .-'   / /\\ \\ | .-.\\ / .-. ) |  \\| |";
        titleC = " (_) \\   | `-' || | |(_)| `-'/  )| |    | /  \\ || `-.  / /__\\ \\| |-' )| | |(_)|   | |";
        titleD = " _  \\ \\  | .-. || | | | |   (  (_) |    |  /\\  || .-'  |  __  || |--' | | | | | |\\  |";
        titleE = "( `-'  ) | | |)|\\ `-' / | |\\ \\   | |    |(/  \\ ||  `--.| |  |)|| |    \\ `-' / | | |)|";
        titleF = " `----'  /(  (_) )---'  |_| \\)\\  `-'    (_)   \\|/( __.'|_|  (_)/(      )---'  /(  (_)";
        titleG = "        (__)    (_)         (__)               (__)           (__)    (_)    (__)    ";

        title = stringInsert(lineTemplate, titleA, (MAX_WIDTH/2) - (titleA.length()/2)-1);
        title += stringInsert(lineTemplate, titleB, (MAX_WIDTH/2) - (titleB.length()/2)-1);
        title += stringInsert(lineTemplate, titleC, (MAX_WIDTH/2) - (titleC.length()/2)-1);
        title += stringInsert(lineTemplate, titleD, (MAX_WIDTH/2) - (titleD.length()/2)-1);
        title += stringInsert(lineTemplate, titleE, (MAX_WIDTH/2) - (titleE.length()/2)-1);
        title += stringInsert(lineTemplate, titleF, (MAX_WIDTH/2) - (titleF.length()/2)-1);
        title += stringInsert(lineTemplate, titleG, (MAX_WIDTH/2) - (titleG.length()/2)-1);

        windowContent = title;
        menuOptions.clear();
        print();

        System.out.print("| Number of matches for the Short Weapon sub-tournament (choice must be a power of two): ");
        selection = in.nextLine();

        while(!isNumeric(selection) || this.isNotPowerOfTwo(Integer.parseInt(selection)))
        {
            System.out.println(" Selection must be an integer of the power of two...");
            promptEnterKey();
            print();
            System.out.print("| Number of matches for the Short Weapon sub-tournament (choice must be a power of two): ");
            selection = in.nextLine();
        }

        numMatches[2] = Integer.parseInt(selection);

        titleA = ".-.  .-.,-.,-.     ,'|\"\\   ";
        titleB = "| |/\\| ||(|| |     | |\\ \\  ";
        titleC = "| /  \\ |(_)| |     | | \\ \\ ";
        titleD = "|  /\\  || || |     | |  \\ \\";
        titleE = "|(/  \\ || || `--.  /(|`-' /";
        titleF = "(_)   \\|`-'|( __.'(__)`--' ";
        titleG = "           (_)             ";

        title = stringInsert(lineTemplate, titleA, (MAX_WIDTH/2) - (titleA.length()/2)-1);
        title += stringInsert(lineTemplate, titleB, (MAX_WIDTH/2) - (titleB.length()/2)-1);
        title += stringInsert(lineTemplate, titleC, (MAX_WIDTH/2) - (titleC.length()/2)-1);
        title += stringInsert(lineTemplate, titleD, (MAX_WIDTH/2) - (titleD.length()/2)-1);
        title += stringInsert(lineTemplate, titleE, (MAX_WIDTH/2) - (titleE.length()/2)-1);
        title += stringInsert(lineTemplate, titleF, (MAX_WIDTH/2) - (titleF.length()/2)-1);
        title += stringInsert(lineTemplate, titleG, (MAX_WIDTH/2) - (titleG.length()/2)-1);

        windowContent = title;
        menuOptions.clear();
        print();

        System.out.print("| Number of matches for the Wild sub-tournament (choice must be a power of two): ");
        selection = in.nextLine();

        while(!isNumeric(selection) || this.isNotPowerOfTwo(Integer.parseInt(selection)))
        {
            System.out.println(" Selection must be an integer of the power of two...");
            promptEnterKey();
            print();
            System.out.print("| Number of matches for the Wild sub-tournament (choice must be a power of two): ");
            selection = in.nextLine();
        }

        numMatches[3] = Integer.parseInt(selection);
    }

    private void  tournamentMenu()
    {
        currTourn.initializeTournament(numMatches);
        genTournMap();
        menuOptions.add("Run Tournament");
        menuOptions.add("Start Over");
        currMenu = "tournament";
    }

    private void genTournMap()
    {
        String map;
        String temp = "Long Weapon Tournament: " + numMatches[0] + " Matche(s)";
        map = stringInsert(lineTemplate, temp, (MAX_WIDTH/2) - (temp.length()/2)-1);
        temp = "Medium Weapon Tournament: " + numMatches[1] + " Matche(s)";
        map += stringInsert(lineTemplate, temp, (MAX_WIDTH/2) - (temp.length()/2)-1);
        temp = "Short Weapon Tournament: " + numMatches[2] + " Matche(s)";
        map += stringInsert(lineTemplate, temp, (MAX_WIDTH/2) - (temp.length()/2)-1);
        temp = "Wild Weapon Tournament: " + numMatches[3] + " Matche(s)";
        map += stringInsert(lineTemplate, temp, (MAX_WIDTH/2) - (temp.length()/2)-1);
        windowContent = map;
    }

    private void announceVictor(String name)
    {
        ArrayList<String> victoryMessage = new ArrayList<>();
        ArrayList<String> victorName = generateStylizedName(name);
        String temp;
        String announcement = new String();

        victoryMessage.add("     ,-.   .---.   _______ .-. .-.,---.         ");
        victoryMessage.add("     |(|  ( .-._) |__   __|| | | || .-'         ");
        victoryMessage.add("     (_) (_) \\      )| |   | `-' || `-.         ");
        victoryMessage.add("     | | _  \\ \\    (_) |   | .-. || .-'         ");
        victoryMessage.add("     | |( `-'  )     | |   | | |)||  `--.       ");
        victoryMessage.add("     `-' `----'      `-'   /(  (_)/( __.'       ");
        victoryMessage.add("                          (__)   (__)           ");
        victoryMessage.add(".-.   .-.,-.  ,--, _______  .---.  ,---.   .-.  ");
        victoryMessage.add(" \\ \\ / / |(|.' .')|__   __|/ .-. ) | .-.\\  |  ) ");
        victoryMessage.add("  \\ V /  (_)|  |(_) )| |   | | |(_)| `-'/  | /  ");
        victoryMessage.add("   ) /   | |\\  \\   (_) |   | | | | |   (   |/   ");
        victoryMessage.add("  (_)    | | \\  `-.  | |   \\ `-' / | |\\ \\  (    ");
        victoryMessage.add("         `-'  \\____\\ `-'    )---'  |_| \\)\\(_)   ");
        victoryMessage.add("                           (_)         (__)     ");

        for(int i = 0; i < victorName.size(); i++)
        {
            temp = stringInsert(lineTemplate, victorName.get(i),MAX_WIDTH/2 - (victorName.get(i).length()/2)-1);
            announcement += temp;
        }

        for(int i = 0; i < victoryMessage.size(); i++)
        {
            temp = stringInsert(lineTemplate, victoryMessage.get(i),MAX_WIDTH/2 - (victoryMessage.get(i).length()/2)-1);
            announcement += temp;
        }

        windowContent = announcement;
        menuOptions.clear();
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

    protected static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            int i = Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    private void update()
    {
        print();
        optionSelect(currMenu);
    }

    protected void promptEnterKey(){
        System.out.println("Press \"ENTER\" to continue...");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }

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
}
