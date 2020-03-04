package com.github.mct.ui;

import com.github.mct.tournament.Tournament;
import com.github.mct.tournament.TournamentArchetype;

import java.util.ArrayList;
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

    private Jester currJester;

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
        MAX_WIDTH = 100;
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
                        break;
                    case "2":
                        defaultMenu();
                        return;
                    default:
                        System.out.println("| Button selection must be an integer value from 1 through " + menuOptions.size());
                        currJester = new Jester();
                        currJester.commentOnStart();
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

        while(!isNumeric(selection) || Integer.parseInt(selection) % 2 != 0)
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

        while(!isNumeric(selection) || Integer.parseInt(selection) % 2 != 0)
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

        while(!isNumeric(selection) || Integer.parseInt(selection) % 2 != 0)
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

        while(!isNumeric(selection) || Integer.parseInt(selection) % 2 != 0)
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
        map = stringInsert(lineTemplate, "<<placeholder>>", (MAX_WIDTH/2) - (15/2)-1);
        windowContent = map;
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
}
