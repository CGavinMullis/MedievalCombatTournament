package com.github.mct.ui;

import java.util.ArrayList;

public class Jester {

    private int MAX_WIDTH;

    private String emptySpace;

    public Jester() {
        MAX_WIDTH = 100;
        emptySpace = new String(new char[MAX_WIDTH - 3]).replace("\0", " ");
    }

    public void commentOnStart() {

        System.out.println(generateComment("I'm so happy to be here!"));
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
        String text = "\"" + content + "\"";
        StringBuilder builder;
        int position;

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

            if(i == (jesterHead.size()/2))
            {
                position = MAX_WIDTH/2;
                for (char c : text.toCharArray())
                {
                    builder.setCharAt(position,c);
                    position++;
                }
            }
            comment += builder.toString();
        }


        return comment;
    }

}
