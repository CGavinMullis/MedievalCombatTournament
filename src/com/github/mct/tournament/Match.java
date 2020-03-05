package com.github.mct.tournament;

import com.github.mct.combat.Fighter;
import com.github.mct.ui.Jester;

import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Match stores Fighters, determines the winner of a fight, and uses Jester to comment on the status of the match.
 *
 * @author Brianna Bell & SirNocturne
 *
 */
public class Match {

    private Fighter fighter1;       // both fighters for the match
    private Fighter fighter2;       // both fighters for the match
    private Jester jester;          // jester for commentary

    protected int MAX_WIDTH;

    protected String emptySpace;

    protected String lineTemplate;

    protected String windowBorder;

    protected String windowDivider;

    protected String windowContent;


    /**
     * Overloaded constructor for semifinals and finals, using winners from previous matches
     */
    public Match()
    {
        this.fighter1 = null;
        this.fighter2 = null;
        this.jester = new Jester(this);         // create Jester to comment on the match
        MAX_WIDTH = 100;
        emptySpace = new String(new char[MAX_WIDTH - 3]).replace("\0", " ");
        lineTemplate = '|' + emptySpace + "|\n";
        windowBorder = new String(new char[MAX_WIDTH - 3]).replace("\0", "-");
        windowDivider = new String(new char[MAX_WIDTH - 3]).replace("\0", "_");
    }


    /**
     * This function begins the match and signals Jester to Comment throughout.
     */
    public Fighter PlayMatch()
    {
        int f1AttackAdvantage = 0;
        int f1DefenseAdvantage = 0;
        int f2AttackAdvantage = 0;
        int f2DefenseAdvantage = 0;
        int f1HP = 10;
        int f2HP = 10;
        boolean f1Bloodied = false;
        boolean f2Bloodied = false;

        this.jester.CommentOnStart();       // Jester comments before match begins

        if (fighter1.StrongerThan(fighter2))
        {
            f1AttackAdvantage++;
        }
        else if (fighter2.StrongerThan(fighter1))
        {
            f2AttackAdvantage++;
        }
        if (fighter1.LongerReachedThan(fighter2))
        {
            f1DefenseAdvantage++;
        }
        else if(fighter2.LongerReachedThan(fighter1))
        {
            f2DefenseAdvantage++;
        }
        if (fighter1.FasterThan(fighter2) && fighter1.getWeapon().getArchetype() != fighter2.getWeapon().getArchetype())
        {
            f1AttackAdvantage++;
            f1DefenseAdvantage++;
        }
        else if(fighter2.FasterThan(fighter1) && fighter1.getWeapon().getArchetype() != fighter2.getWeapon().getArchetype())
        {
            f2AttackAdvantage++;
            f2DefenseAdvantage++;
        }

        while(true)
        {
            f2HP -= (rollD6(fighter1.GetAttackPerformance()+f1AttackAdvantage) - rollD6(fighter2.GetDefensePerformance()+f2DefenseAdvantage));
            f1HP -= (rollD6(fighter2.GetAttackPerformance()+f2AttackAdvantage) - rollD6(fighter1.GetDefensePerformance()+f1DefenseAdvantage));

            if (f1HP <= 5)
            {
                if (f1HP <= 0)
                {
                    if(f2HP <= 0)
                    {
                        this.jester.CommentOnEnd(fighter1, fighter2);
                        f1HP = 10;
                        f2HP = 10;
                        f1Bloodied = false;
                        f2Bloodied = false;
                        continue;
                    }
                    this.jester.CommentOnEnd(fighter1);
                    return fighter2;
                }
                if(!f1Bloodied) {
                    SignalMiddleToJester(fighter1);
                    f1Bloodied = true;
                }
            }
            if (f2HP <= 5)
            {
                if (f2HP <= 0)
                {
                    this.jester.CommentOnEnd(fighter2);
                    return fighter1;
                }
                if(!f2Bloodied) {
                    SignalMiddleToJester(fighter2);
                    f2Bloodied = true;
                }
            }
        }
    }

    /**
     * This function signals Jester that the match is halfway through
     */
    public void SignalMiddleToJester(Fighter f)
    {
        this.jester.CommentOnMiddle(f);
    }

    private void print()
    {
        String content;
        String buttonHeader = new String(new char[MAX_WIDTH - 3]).replace("\0", "/");
        int counter = 0;

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


    public Fighter getFighter1() {
        return fighter1;
    }

    public Fighter getFighter2() {
        return fighter2;
    }

    public void setFighter1(Fighter f)
    {
        fighter1 = f;
    }

    public void setFighter2(Fighter f)
    {
        fighter2 = f;
    }

    private int rollD6(int rolls)
    {
        Random r = new Random();
        int sum = 0;
        for (int i = 0 ; i < rolls ; i++)
        {
            sum += r.nextInt(5)+1;
        }
        return sum;
    }

    private static void clearConsole(){
        try
        {
            final String os = System.getProperty("os.name");

            if (os.contains("Windows"))
            {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
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

    private void generateRoundTitle(int roundNum)
    {
        ArrayList<String> sRound = new ArrayList<>();
        ArrayList<String> sDigit = this.generateRoundNumber(roundNum);
        String roundTitle = new String();
        sRound.add(" _______  _______           _        ______  ");
        sRound.add("(  ____ )(  ___  )|\\     /|( (    /|(  __  \\ ");
        sRound.add("| (    )|| (   ) || )   ( ||  \\  ( || (  \\  )");
        sRound.add("| (____)|| |   | || |   | ||   \\ | || |   ) |");
        sRound.add("|     __)| |   | || |   | || (\\ \\) || |   | |");
        sRound.add("| (\\ (   | |   | || |   | || | \\   || |   ) |");
        sRound.add("| ) \\ \\__| (___) || (___) || )  \\  || (__/  )");
        sRound.add("|/   \\__/(_______)(_______)|/    )_)(______/ ");

        for (int i = 0; i < sRound.size() ; i++)
        {
            String s = sRound.get(i) + " " + sDigit.get(i);
            roundTitle += stringInsert(lineTemplate, s, (MAX_WIDTH/2) - (s.length()/2)-1);
        }

    }

    private ArrayList<String> generateRoundNumber(int requestedNum)
    {
        Map<Integer, ArrayList<String>> oneDigitNums = new HashMap<>();

        ArrayList<String> sZero = new ArrayList<>();
        sZero.add(" _______ ");
        sZero.add("(  __   )");
        sZero.add("| (  )  |");
        sZero.add("| | /   |");
        sZero.add("| (/ /) |");
        sZero.add("|   / | |");
        sZero.add("|  (__) |");
        sZero.add("(_______)");

        ArrayList<String> sOne = new ArrayList<>();
        sOne.add("  __   ");
        sOne.add(" /  \\  ");
        sOne.add(" \\/) ) ");
        sOne.add("   | | ");
        sOne.add("   | | ");
        sOne.add("   | | ");
        sOne.add(" __) (_");
        sOne.add(" \\____/");

        ArrayList<String> sTwo = new ArrayList<>();
        sTwo.add(" _______ ");
        sTwo.add("/ ___   )");
        sTwo.add("\\/   )  |");
        sTwo.add("    /   )");
        sTwo.add("  _/   / ");
        sTwo.add(" /   _/  ");
        sTwo.add("(   (__/\\");
        sTwo.add("\\_______/");

        ArrayList<String> sThree = new ArrayList<>();
        sThree.add(" ______  ");
        sThree.add("/ ___  \\ ");
        sThree.add("\\/   \\  \\");
        sThree.add("   ___) /");
        sThree.add("  (___ ( ");
        sThree.add("      ) \\");
        sThree.add("/\\___/  /");
        sThree.add("\\______/ ");

        ArrayList<String> sFour = new ArrayList<>();
        sFour.add("    ___   ");
        sFour.add("   /   )  ");
        sFour.add("  / /) |  ");
        sFour.add(" / (_) (_ ");
        sFour.add("(____   _)");
        sFour.add("     ) (  ");
        sFour.add("     | |  ");
        sFour.add("     (_)  ");

        ArrayList<String> sFive = new ArrayList<>();
        sFive.add(" _______ ");
        sFive.add("(  ____ \\");
        sFive.add("| (    \\/");
        sFive.add("| (____  ");
        sFive.add("(_____ \\ ");
        sFive.add("      ) )");
        sFive.add("/\\____) )");
        sFive.add("\\______/ ");

        ArrayList<String> sSix = new ArrayList<>();
        sSix.add("  ______ ");
        sSix.add(" / ____ \\");
        sSix.add("( (    \\/");
        sSix.add("| (____  ");
        sSix.add("|  ___ \\ ");
        sSix.add("| (   ) )");
        sSix.add("( (___) )");
        sSix.add(" \\_____/ ");

        ArrayList<String> sSeven = new ArrayList<>();
        sSeven.add(" ______  ");
        sSeven.add("/ ___  \\ ");
        sSeven.add("\\/   )  )");
        sSeven.add("    /  / ");
        sSeven.add("   /  /  ");
        sSeven.add("  /  /   ");
        sSeven.add(" /  /    ");
        sSeven.add(" \\_/     ");

        ArrayList<String> sEight = new ArrayList<>();
        sEight.add("  _____  ");
        sEight.add(" / ___ \\ ");
        sEight.add("( (___) )");
        sEight.add(" \\     / ");
        sEight.add(" / ___ \\ ");
        sEight.add("( (   ) )");
        sEight.add("( (___) )");
        sEight.add(" \\_____/ ");

        ArrayList<String> sNine = new ArrayList<>();
        sNine.add("  _____  ");
        sNine.add(" / ___ \\ ");
        sNine.add("( (   ) )");
        sNine.add("( (___) |");
        sNine.add(" \\____  |");
        sNine.add("      ) |");
        sNine.add("/\\____) )");
        sNine.add("\\______/ ");

        oneDigitNums.put(0,sZero);
        oneDigitNums.put(1,sOne);
        oneDigitNums.put(2,sTwo);
        oneDigitNums.put(3,sThree);
        oneDigitNums.put(4,sFour);
        oneDigitNums.put(5,sFive);
        oneDigitNums.put(6,sSix);
        oneDigitNums.put(7,sSeven);
        oneDigitNums.put(8,sEight);
        oneDigitNums.put(9,sNine);

        return oneDigitNums.get(requestedNum);
    }
}
