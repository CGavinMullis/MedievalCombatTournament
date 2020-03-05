package com.github.mct.ui;

import com.github.mct.combat.Fighter;
import com.github.mct.tournament.Match;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import static java.lang.StrictMath.abs;

public class Jester {

    private int MAX_WIDTH;

    private String emptySpace;

    private Fighter favFighter;

    private Match currMatch;

    private enum jesterMood {
        HAPPY,
        GRUMPY;
    }

    private enum physicalAttribute
    {
        STRENGTH,
        REACH,
        SPEED;
    }

    public Jester(Match m) {
        MAX_WIDTH = 100;
        emptySpace = new String(new char[MAX_WIDTH - 3]).replace("\0", " ");
        currMatch = m;
    }


    public void CommentOnStart() {
        clearConsole();
        physicalAttribute comparisonPoint = getRandomAttribute();
        Fighter f1 = currMatch.getFighter1();
        Fighter f2 = currMatch.getFighter2();
        String namePredictedWinner;
        String namePredictedLoser;

        switch (comparisonPoint)
        {
            case STRENGTH:
                if (f1.StrongerThan(f2))
                {
                    favFighter = f1;
                    namePredictedWinner = favFighter.getName();
                    namePredictedLoser = f2.getName();
                }
                else
                {
                    favFighter = f2;
                    namePredictedWinner = favFighter.getName();
                    namePredictedLoser = f1.getName();
                }
                System.out.println(generateComment(namePredictedWinner + " seems awful strong, I'm not so sure about " + namePredictedLoser + "..."));
                break;
            case REACH:
                if (f1.LongerReachedThan(f2))
                {
                    favFighter = f1;
                    namePredictedWinner = favFighter.getName();
                    namePredictedLoser = f2.getName();
                }
                else
                {
                    favFighter = f2;
                    namePredictedWinner = favFighter.getName();
                    namePredictedLoser = f1.getName();
                }
                System.out.println(generateComment(namePredictedLoser + " has tiny little arms! They'll be no match for the reach on " + namePredictedWinner + "!"));
                break;
            case SPEED:
                if (f1.FasterThan(f2))
                {
                    favFighter = f1;
                    namePredictedWinner = favFighter.getName();
                    namePredictedLoser = f2.getName();
                }
                else
                {
                    favFighter = f2;
                    namePredictedWinner = favFighter.getName();
                    namePredictedLoser = f1.getName();
                }
                System.out.println(generateComment(namePredictedWinner + " is incredibly quick on their feet! " + namePredictedLoser + " on the other hand just got my joke from last week..."));
                break;
        }
        promptEnterKey();

        //System.out.println(generateComment("Death Everywhere! No seriously, like it is everywhere! For real though, have you looked recently? It's crazy, like really crazy. " +
        //        "Just saying... Oh! It's cake time! My favorite time of the day!"));
    }

    public void CommentOnMiddle(Fighter f) {
        clearConsole();
        System.out.println(generateComment(f.getName() + " got Bloodied!"));
        promptEnterKey();
    }

    public void CommentOnEnd(Fighter f) {
        clearConsole();
        System.out.println(generateComment(f.getName() + " was taken Down!!"));
        promptEnterKey();
    }

    public void CommentOnEnd(Fighter f1, Fighter f2) {
        clearConsole();
        System.out.println(generateComment(f1.getName() + " and " + f2.getName() + " knocked each other out. Silly, now we have to start over!"));
        promptEnterKey();
    }

    private String generateComment(String content)
    {
        ArrayList<String> jesterHead = new ArrayList<>();
        String baseline = " " + emptySpace + " \n";
        String line;
        String comment = new String();
        List<String> subText;
        String text = "\"" + content;
        StringBuilder builder;
        int position;
        int offset = 1;
        int subIndex;
        boolean textEnd = false;

        jesterHead.add("       _                   ");
        jesterHead.add("      (_)          _       ");
        jesterHead.add("  _         .=.   (_)      ");
        jesterHead.add(" (_)   _   //(`)_          ");
        jesterHead.add("      //`\\/ |\\ 0`\\\\        ");
        jesterHead.add("      ||-.\\_|_/.-||        ");
        jesterHead.add("      )/ |_____| \\(    _   ");
        jesterHead.add("     0   #/\\ /\\#  0   (_)  ");
        jesterHead.add("        _| o o |_          ");
        jesterHead.add(" _     ((|, ^ ,|))         ");
        jesterHead.add("(_)     `||\\_/||`          ");
        jesterHead.add("         || _ ||      _    ");
        jesterHead.add("         | \\_/ |     (_)   ");
        jesterHead.add("     0.__.\\   /.__.0       ");
        jesterHead.add("      `._  `\"`  _.'        ");
        jesterHead.add("          / ;  \\ \\         ");
        jesterHead.add("       0'-' )/`'-0         ");
        jesterHead.add("           0`              ");


        if (text.length() > (MAX_WIDTH - MAX_WIDTH/3)) {
            subText = splitEqually(text,MAX_WIDTH/2 + 10);
        }
        else
        {
            subText = new ArrayList<>();
            subText.add(text);
        }
        subText.set(subText.size()-1, subText.get(subText.size()-1).concat("\""));
        offset = subText.size()/2;
        subIndex = 0;


        for (int i = 0; i < jesterHead.size(); i++)
        {
            position = 1;
            line = baseline;
            builder = new StringBuilder(line);

            for (char c : jesterHead.get(i).toCharArray())
            {
                builder.setCharAt(position,c);
                position++;
            }

            if(i == ((jesterHead.size()/2)-offset) && !textEnd)
            {
                offset--;
                position = MAX_WIDTH/3;
                for (char c : subText.get(subIndex).toCharArray())
                {
                    builder.setCharAt(position,c);
                    position++;
                }
                subIndex++;
                if (abs(offset) == subText.size()/2 || subText.size() == 1)
                {
                    textEnd = true;
                }
            }
            //System.out.println(builder.toString());
            comment += builder.toString();
        }


        return comment;
    }

    private static List<String> splitEqually(String text, int size) {
        List<String> ret = new ArrayList<String>((text.length() + size - 1) / size);

        for (int start = 0; start < text.length(); start += size) {
            ret.add(text.substring(start, Math.min(text.length(), start + size)));
        }
        return ret;
    }

    private static physicalAttribute getRandomAttribute() {
        Random random = new Random();
        return physicalAttribute.values()[random.nextInt(physicalAttribute.values().length)];
    }

    private static void clearConsole(){
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

    private void promptEnterKey(){
        System.out.println("Press \"ENTER\" to continue...");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }

}
