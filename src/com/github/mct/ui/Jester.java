package com.github.mct.ui;

import java.util.ArrayList;
import java.util.List;

import static java.lang.StrictMath.abs;

public class Jester {

    private int MAX_WIDTH;

    private String emptySpace;

    public Jester() {
        MAX_WIDTH = 100;
        emptySpace = new String(new char[MAX_WIDTH - 3]).replace("\0", " ");
    }

    public void commentOnStart() {

        System.out.println(generateComment("Death Everywhere! No seriously, like it is everywhere! For real though, have you looked recently? It's crazy, like really crazy. " +
                "Just saying... Oh! It's cake time! My favorite time of the day!"));
    }

    public void commentOnMiddle() {

    }

    public void commentOnEnd() {

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


        if (text.length() > ((MAX_WIDTH - MAX_WIDTH / 3))) {
            subText = splitEqually(text,MAX_WIDTH/2 + 10);
        }
        else
        {
            subText = new ArrayList<>();
            subText.add(text);
        }
        subText.add(subText.size()-1, subText.get(subText.size()-1).concat("\""));
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
        // Give the list the right capacity to start with. You could use an array
        // instead if you wanted.
        List<String> ret = new ArrayList<String>((text.length() + size - 1) / size);

        for (int start = 0; start < text.length(); start += size) {
            ret.add(text.substring(start, Math.min(text.length(), start + size)));
        }
        return ret;
    }

}
