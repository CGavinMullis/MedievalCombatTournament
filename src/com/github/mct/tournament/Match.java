package com.github.mct.tournament;

import com.github.mct.combat.Fighter;
import com.github.mct.ui.Jester;


import java.util.*;

/**
 * Match stores Fighters, determines the winner of a fight, and uses Jester to comment on the status of the match.
 *
 * @author Brianna Bell & Gregory Lofink
 *
 */
public class Match {

    private Fighter fighter1;       // both fighters for the match
    private Fighter fighter2;       // both fighters for the match
    private Jester jester;          // jester for commentary


    private int f1HP;

    private int f2HP;

    private int f1Strike;

    private int f2Strike;

    private int f1AttackAdvantage;
    private int f1DefenseAdvantage;
    private int f2AttackAdvantage;
    private int f2DefenseAdvantage;

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
        MAX_WIDTH = 150;
        emptySpace = new String(new char[MAX_WIDTH - 3]).replace("\0", " ");
        lineTemplate = '|' + emptySpace + "|\n";
        windowBorder = new String(new char[MAX_WIDTH - 3]).replace("\0", "-");
        windowDivider = new String(new char[MAX_WIDTH - 3]).replace("\0", "_");
        f1Strike = 0;
        f2Strike = 0;
    }


    /**
     * This function begins the match and signals Jester to Comment throughout.
     */
    public Fighter PlayMatch()
    {
        f1AttackAdvantage = 0;
        f1DefenseAdvantage = 0;
        f2AttackAdvantage = 0;
        f2DefenseAdvantage = 0;
        int roundNum = 1;
        f1HP = 10;
        f2HP = 10;
        boolean f1Bloodied = false;
        boolean f2Bloodied = false;


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

        generateRoundTitle(roundNum);
        print();

        this.jester.CommentOnStart();       // Jester comments before match begins
        promptEnterKey();

        while(true)
        {
            f2Strike = rollD6(fighter2.GetAttackPerformance()+f2AttackAdvantage) - rollD6(fighter1.GetDefensePerformance()+f1DefenseAdvantage);
            f1Strike = rollD6(fighter1.GetAttackPerformance()+f1AttackAdvantage) - rollD6(fighter2.GetDefensePerformance()+f2DefenseAdvantage);
            f2HP = strikeFighter (f2HP,f1Strike);
            f1HP = strikeFighter (f1HP,f2Strike);

            roundNum++;
            generateRoundTitle(roundNum);
            print();

            if(f1HP <= 0)
            {
                if (f2HP <= 0)
                {
                    this.jester.CommentOnEnd(fighter1, fighter2);
                    promptEnterKey();
                    f1HP = 10;
                    f2HP = 10;
                    f1Bloodied = false;
                    f2Bloodied = false;
                    roundNum = 1;
                    f2Strike = 0;
                    f1Strike = 0;
                    generateRoundTitle(roundNum);
                    print();
                    this.jester.CommentOnStart();
                    promptEnterKey();
                    continue;
                }
                this.jester.CommentOnEnd(fighter1);
                promptEnterKey();
                return fighter2;
            }
            if(f2HP <= 0)
            {
                if (f1HP <= 0)
                {
                    this.jester.CommentOnEnd(fighter1, fighter2);
                    promptEnterKey();
                    f1HP = 10;
                    f2HP = 10;
                    f1Bloodied = false;
                    f2Bloodied = false;
                    roundNum = 1;
                    generateRoundTitle(roundNum);
                    f2Strike = 0;
                    f1Strike = 0;
                    print();
                    this.jester.CommentOnStart();
                    promptEnterKey();
                    continue;
                }
                this.jester.CommentOnEnd(fighter2);
                promptEnterKey();
                return fighter1;
            }

            if (f1HP <= 5 && !f1Bloodied) {
                if (f2HP <= 5 && !f2Bloodied) {
                    SignalMiddleToJester(fighter1, fighter2);
                    f2Bloodied = true;
                    promptEnterKey();
                    continue;
                }
                f1Bloodied = true;
                SignalMiddleToJester(fighter1);
            }

            if (f2HP <= 5 && !f2Bloodied) {
                f2Bloodied = true;
                SignalMiddleToJester(fighter2);
            }

            promptEnterKey();
        }
    }

    /**
     * This function signals Jester that the match is halfway through
     */
    private void SignalMiddleToJester(Fighter f)
    {
        this.jester.CommentOnMiddle(f);
    }

    private void SignalMiddleToJester(Fighter f1, Fighter f2)
    {
        this.jester.CommentOnMiddle(f1,f2);
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
        Random r;
        int sum = 0;
        for (int i = 0 ; i < rolls ; i++)
        {
            r = new Random();
            sum += r.nextInt(5)+1;
        }
        return sum;
    }

    private int strikeFighter(int f, int damage)
    {
        int result;
        while(damage < 0)
        {
            damage++;
        }

        result = f - damage;

        while(result < 0 )
        {
            result++;
        }

        return result;
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
        String f1Stats = new String();
        String f2Stats = new String();
        String line = new String();

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

        roundTitle += stringInsert(lineTemplate, emptySpace,1);

        f1Stats = this.fighter1.getName();
        f2Stats = this.fighter2.getName();
        line = stringInsert(lineTemplate, f1Stats, (MAX_WIDTH/8));
        line = stringInsert(line, f2Stats, MAX_WIDTH-(MAX_WIDTH/4));
        roundTitle += line;

        f1Stats = "Strength: " + this.fighter1.getStrength();
        f2Stats = "Strength: " + this.fighter2.getStrength();
        line = stringInsert(lineTemplate, f1Stats, (MAX_WIDTH/8));
        line = stringInsert(line, f2Stats, MAX_WIDTH-(MAX_WIDTH/4));
        roundTitle += line;

        f1Stats = "Reach: " + this.fighter1.getReach();
        f2Stats = "Reach: " + this.fighter2.getReach();
        line = stringInsert(lineTemplate, f1Stats, (MAX_WIDTH/8));
        line = stringInsert(line, f2Stats, MAX_WIDTH-(MAX_WIDTH/4));
        roundTitle += line;

        f1Stats = "Speed: " + this.fighter1.getSpeed();
        f2Stats = "Speed: " + this.fighter2.getSpeed();
        line = stringInsert(lineTemplate, f1Stats, (MAX_WIDTH/8));
        line = stringInsert(line, f2Stats, MAX_WIDTH-(MAX_WIDTH/4));
        roundTitle += line;

        f1Stats = "Weapon: " + this.fighter1.getWeapon().getArchetype().toString();
        f2Stats = "Weapon: " + this.fighter2.getWeapon().getArchetype().toString();;
        line = stringInsert(lineTemplate, f1Stats, (MAX_WIDTH/8));
        line = stringInsert(line, f2Stats, MAX_WIDTH-(MAX_WIDTH/4));
        roundTitle += line;

        f1Stats = "Attack: " + this.fighter1.GetAttackPerformance() + "d6 + " + this.f1AttackAdvantage + "d6";
        f2Stats = "Attack: " + this.fighter2.GetAttackPerformance() + "d6 + " + this.f2AttackAdvantage + "d6";
        line = stringInsert(lineTemplate, f1Stats, (MAX_WIDTH/8));
        line = stringInsert(line, f2Stats, MAX_WIDTH-(MAX_WIDTH/4));
        roundTitle += line;

        f1Stats = "Defense: " + this.fighter1.GetDefensePerformance() + "d6 + " + this.f1DefenseAdvantage + "d6";
        f2Stats = "Defense: " + this.fighter2.GetDefensePerformance() + "d6 + " + this.f2DefenseAdvantage + "d6";
        line = stringInsert(lineTemplate, f1Stats, (MAX_WIDTH/8));
        line = stringInsert(line, f2Stats, MAX_WIDTH-(MAX_WIDTH/4));
        roundTitle += line;

        f1Stats = "HP: " + this.f1HP + " (-" + f2Strike + ')';
        f2Stats = "HP: " + this.f2HP + " (-" + f1Strike + ')';
        line = stringInsert(lineTemplate, f1Stats, (MAX_WIDTH/8));
        line = stringInsert(line, f2Stats, MAX_WIDTH-(MAX_WIDTH/4));
        roundTitle += line;

        windowContent = roundTitle;
    }

    private ArrayList<String> generateRoundNumber(int requestedNum)
    {
        Map<Integer, ArrayList<String>> oneDigitNums = new HashMap<>();
        ArrayList<Integer> digits = new ArrayList<>();
        ArrayList<String> finalNumber = new ArrayList<>();
        while (requestedNum > 0)
        {
            digits.add(requestedNum % 10);
            requestedNum = requestedNum/10;
        }

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

        if(digits.size() > 1)
        {
            //String temp = new String();
            int count = 0;
            for (String s : oneDigitNums.get(digits.get(0)))
            {
                finalNumber.add(s);
            }
            for(int i = 1; i < digits.size() ; i++)
            {
                for(String s : oneDigitNums.get(digits.get(i)))
                {
                    finalNumber.set(count, s + " " + finalNumber.get(count));
                    count++;
                }
                count = 0;
            }
            return finalNumber;
        }
        else {
            return oneDigitNums.get(digits.get(0));
        }
    }

    private void promptEnterKey(){
        System.out.println("Press \"ENTER\" to continue...");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }
}
