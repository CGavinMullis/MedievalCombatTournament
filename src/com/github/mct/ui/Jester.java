package com.github.mct.ui;

import com.github.mct.combat.Fighter;
import com.github.mct.tournament.Match;
import java.util.*;
import static java.lang.StrictMath.abs;

/**
 * Comments on its associated match by comparing fighter stats,
 * and reacting to key events during combat
 *
 * @author Gregory Lofink
 *
 */

public class Jester {

    private int MAX_WIDTH;

    private String emptySpace;

    private Fighter favFighter;

    private Fighter nonFavFighter;

    private Match currMatch;

    private boolean strengthDiff;

    private boolean reachDiff;

    private boolean speedDiff;

    /**
     * Private Enumerated Type for Jester's Mood
     *
     */
    private enum jesterMood {
        HAPPY,
        GRUMPY,
        SHOCKED,
        ELATED,
        SMUG;
    }

    /**
     * Private Enumerated Type for physicalAttributes
     *
     */
    private enum physicalAttribute
    {
        STRENGTH,
        REACH,
        SPEED;
    }

    /**
     * Private Enumerated Type for comment types
     *
     */
    private enum commentType
    {
        STRENGTH,
        REACH,
        SPEED,
        BLOODIED,
        FAVBLOODIED,
        DOUBLEBLOODIED,
        DOUBLETAKEDOWN,
        TAKEDOWN,
        TAKEDOWNFAV;
    }

    /**
     * Constructor for Jester Class
     *
     * Attaches Match that initialized it
     *
     * @param m Match that created this Jester
     */
    public Jester(Match m) {
        MAX_WIDTH = 150;
        emptySpace = new String(new char[MAX_WIDTH - 3]).replace("\0", " ");
        currMatch = m;
    }


    /**
     * Called by Match at the beginning of Combat. Prints Jester's commentary on his fav fighter based on stats
     */
    public void CommentOnStart() {
        physicalAttribute comparisonPoint;
        Fighter f1 = currMatch.getFighter1();
        Fighter f2 = currMatch.getFighter2();
        String comment = null;
        strengthDiff = true;
        reachDiff = true;
        speedDiff = true;


        while(comment == null && (strengthDiff || reachDiff || speedDiff)) {
            comparisonPoint = getRandomAttribute();
            switch (comparisonPoint) {
                case STRENGTH:
                    if (strengthDiff) {
                        if (f1.StrongerThan(f2)) {
                            favFighter = f1;
                            nonFavFighter = f2;
                        } else if (f2.StrongerThan(f1)) {
                            favFighter = f2;
                            nonFavFighter = f1;
                        } else {
                            strengthDiff = false;
                            break;
                        }
                        comment = generateComment(getRandomComment(favFighter,nonFavFighter,commentType.STRENGTH), jesterMood.HAPPY);
                    }
                    break;
                case REACH:
                    if(reachDiff) {
                        if (f1.LongerReachedThan(f2)) {
                            favFighter = f1;
                            nonFavFighter = f2;
                        } else if (f2.LongerReachedThan(f1)) {
                            favFighter = f2;
                            nonFavFighter = f1;

                        } else {
                            reachDiff = false;
                            break;
                        }
                        comment = generateComment(getRandomComment(favFighter,nonFavFighter,commentType.REACH), jesterMood.HAPPY);
                    }
                    break;
                case SPEED:
                    if(speedDiff) {
                        if (f1.FasterThan(f2)) {
                            favFighter = f1;
                            nonFavFighter = f2;
                        } else if (f2.FasterThan((f1))) {
                            favFighter = f2;
                            nonFavFighter = f1;
                        } else {
                            speedDiff = false;
                            break;
                        }
                        comment = generateComment(getRandomComment(favFighter,nonFavFighter,commentType.SPEED), jesterMood.HAPPY);
                    }
                    break;
            }
        }

        if(comment == null)
        {
            comment = generateComment("Wow! Both of these fighters seem equally matched! I have no idea who is going to win!", jesterMood.SMUG);
        }
        System.out.println(comment);
    }

    /**
     * Called by Match whenever a fighter gets bloodied. Prints Jester's commentary on a fighter going below half health.
     */
    public void CommentOnMiddle(Fighter f) {
        if (f == favFighter) {
            System.out.println(generateComment(getRandomComment(f,f,commentType.FAVBLOODIED), jesterMood.SHOCKED));
        }
        else{
            System.out.println(generateComment(getRandomComment(f,f,commentType.BLOODIED), jesterMood.ELATED));
        }
    }

    /**
     * Called by Match whenever both fighter gets bloodied. Prints Jester's commentary on both fighters going below half health.
     */
    public void CommentOnMiddle(Fighter f1, Fighter f2) {
        System.out.println(generateComment(getRandomComment(f1,f2,commentType.DOUBLEBLOODIED), jesterMood.SHOCKED));
    }

    /**
     * Called by Match whenever a fighter goes down. Prints Jester's commentary on fighters going down.
     */
    public void CommentOnEnd(Fighter f) {
        if (f == favFighter) {
            System.out.println(generateComment(getRandomComment(f,f,commentType.TAKEDOWNFAV), jesterMood.GRUMPY));
        }
        else
        {
            System.out.println(generateComment(getRandomComment(f,f,commentType.TAKEDOWN), jesterMood.HAPPY));
        }
    }

    /**
     * Called by Match whenever both fighters go down. Prints Jester's commentary on both fighters going down.
     */
    public void CommentOnEnd(Fighter f1, Fighter f2) {
        System.out.println(generateComment(getRandomComment(f1,f2,commentType.DOUBLEBLOODIED), jesterMood.SMUG));
    }

    /**
     * Generates a comment for the Jester.
     *
     * @param content String that will be used as textual content for the comment.
     * @param mood Enum Type that specifies which mood that the Jester is in while making the comment.
     *
     * @return String comment of textual content combined with relevant Jester face.
     */
    private String generateComment(String content, jesterMood mood)
    {
        ArrayList<String> jesterHead;
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
        jesterHead = generateJesterFace(mood);

        if (text.length() > (MAX_WIDTH - MAX_WIDTH/3 - 4)) {
            subText = splitEqually(text,MAX_WIDTH - MAX_WIDTH/3 - 4);
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

    /**
     * Used to split a string to equal specified sizes.
     *
     * @param text String to split.
     * @param size Size of desired split pieces.
     *
     * @return List of string pieces.
     */
    private static List<String> splitEqually(String text, int size) {
        List<String> ret = new ArrayList<String>((text.length() + size - 1) / size);

        for (int start = 0; start < text.length(); start += size) {
            ret.add(text.substring(start, Math.min(text.length(), start + size)));
        }
        return ret;
    }

    /**
     * Returns a random physical attribute from the physicalAttribute Enum values.
     *
     * @return Random physical attribute.
     */
    private static physicalAttribute getRandomAttribute() {
        Random random = new Random();
        return physicalAttribute.values()[random.nextInt(physicalAttribute.values().length)];
    }

    /**
     * Returns a random physical attribute from the physicalAttribute Enum values
     *
     * @return ArrayList containing a Jester face of specified mood.
     */
    private ArrayList<String> generateJesterFace(jesterMood mood)
    {
        Map<jesterMood, ArrayList<String>> jesterFaces = new HashMap<>();
        ArrayList<String> happyJester = new ArrayList<>();
        happyJester.add("       _                   ");
        happyJester.add("      (_)          _       ");
        happyJester.add("  _         .=.   (_)      ");
        happyJester.add(" (_)   _   //(`)_          ");
        happyJester.add("      //`\\/ |\\ 0`\\\\        ");
        happyJester.add("      ||-.\\_|_/.-||        ");
        happyJester.add("      )/ |_____| \\(    _   ");
        happyJester.add("     0   #/\\ /\\#  0   (_)  ");
        happyJester.add("        _| o o |_          ");
        happyJester.add(" _     ((|, ^ ,|))         ");
        happyJester.add("(_)     `||\\_/||`          ");
        happyJester.add("         || _ ||      _    ");
        happyJester.add("         | \\_/ |     (_)   ");
        happyJester.add("     0.__.\\   /.__.0       ");
        happyJester.add("      `._  `\"`  _.'        ");
        happyJester.add("          / ;  \\ \\         ");
        happyJester.add("       0'-' )/`'-0         ");
        happyJester.add("           0`              ");

        ArrayList<String> grumpyJester = new ArrayList<>();
        grumpyJester.add("       _                   ");
        grumpyJester.add("      (_)          _       ");
        grumpyJester.add("  _         .=.   (_)      ");
        grumpyJester.add(" (_)   _   //(`)_          ");
        grumpyJester.add("      //`\\/ |\\ 0`\\\\        ");
        grumpyJester.add("      ||-.\\_|_/.-||        ");
        grumpyJester.add("      )/ |_____| \\(    _   ");
        grumpyJester.add("     0   #_   _#  0   (_)  ");
        grumpyJester.add("        _| o o |_          ");
        grumpyJester.add(" _     ((|, ^ ,|))         ");
        grumpyJester.add("(_)     `|     |`          ");
        grumpyJester.add("         |/---\\|      _    ");
        grumpyJester.add("         |     |     (_)   ");
        grumpyJester.add("     0.__.\\   /.__.0       ");
        grumpyJester.add("      `._  `\"`  _.'        ");
        grumpyJester.add("          / ;  \\ \\         ");
        grumpyJester.add("       0'-' )/`'-0         ");
        grumpyJester.add("           0`              ");

        ArrayList<String> shockedJester = new ArrayList<>();
        shockedJester.add("       _                   ");
        shockedJester.add("      (_)          _       ");
        shockedJester.add("  _         .=.   (_)      ");
        shockedJester.add(" (_)   _   //(`)_          ");
        shockedJester.add("      //`\\/ |\\ 0`\\\\        ");
        shockedJester.add("      ||-.\\_|_/.-||        ");
        shockedJester.add("      )/ |_____| \\(    _   ");
        shockedJester.add("     0   #/   \\#  0   (_)  ");
        shockedJester.add("        _| o o |_          ");
        shockedJester.add(" _     ((|, ^ ,|))         ");
        shockedJester.add("(_)     `| ___ |`          ");
        shockedJester.add("         ||___||      _    ");
        shockedJester.add("         |     |     (_)   ");
        shockedJester.add("     0.__.\\   /.__.0       ");
        shockedJester.add("      `._  `\"`  _.'        ");
        shockedJester.add("          / ;  \\ \\         ");
        shockedJester.add("       0'-' )/`'-0         ");
        shockedJester.add("           0`              ");

        ArrayList<String> elatedJester = new ArrayList<>();
        elatedJester.add("       _                   ");
        elatedJester.add("      (_)          _       ");
        elatedJester.add("  _         .=.   (_)      ");
        elatedJester.add(" (_)   _   //(`)_          ");
        elatedJester.add("      //`\\/ |\\ 0`\\\\        ");
        elatedJester.add("      ||-.\\_|_/.-||        ");
        elatedJester.add("      )/ |_____| \\(    _   ");
        elatedJester.add("     0   # \\ / |#  0   (_)  ");
        elatedJester.add("        _| o o |_          ");
        elatedJester.add(" _     ((|, ^ ,|))         ");
        elatedJester.add("(_)     `||\\_/||`          ");
        elatedJester.add("         || _ ||      _    ");
        elatedJester.add("         | \\_/ |     (_)   ");
        elatedJester.add("     0.__.\\   /.__.0       ");
        elatedJester.add("      `._  `\"`  _.'        ");
        elatedJester.add("          / ;  \\ \\         ");
        elatedJester.add("       0'-' )/`'-0         ");
        elatedJester.add("           0`              ");

        ArrayList<String> smugJester = new ArrayList<>();
        smugJester.add("       _                   ");
        smugJester.add("      (_)          _       ");
        smugJester.add("  _         .=.   (_)      ");
        smugJester.add(" (_)   _   //(`)_          ");
        smugJester.add("      //`\\/ |\\ 0`\\\\        ");
        smugJester.add("      ||-.\\_|_/.-||        ");
        smugJester.add("      )/ |_____| \\(    _   ");
        smugJester.add("     0   # \\ __|#  0   (_)  ");
        smugJester.add("        _| o o |_          ");
        smugJester.add(" _     ((|, ^ ,|))         ");
        smugJester.add("(_)     `|     |`          ");
        smugJester.add("         |\\__ /|      _    ");
        smugJester.add("         |     |     (_)   ");
        smugJester.add("     0.__.\\   /.__.0       ");
        smugJester.add("      `._  `\"`  _.'        ");
        smugJester.add("          / ;  \\ \\         ");
        smugJester.add("       0'-' )/`'-0         ");
        smugJester.add("           0`              ");

        jesterFaces.put(jesterMood.HAPPY, happyJester);
        jesterFaces.put(jesterMood.GRUMPY, grumpyJester);
        jesterFaces.put(jesterMood.SHOCKED, shockedJester);
        jesterFaces.put(jesterMood.ELATED, elatedJester);
        jesterFaces.put(jesterMood.SMUG, smugJester);

        return jesterFaces.get(mood);
    }

    /**
     * Returns a random comment from the specified category of comment types.
     *
     * @param fav The Jester's predicted winner.
     * @param nfav The Jester's predicted loser.
     * @param type The category of comment to return.
     * @return Random comment based on comment type.
     */
    private String getRandomComment(Fighter fav, Fighter nfav, commentType type) {
        ArrayList<String> strComments = new ArrayList<>();
        ArrayList<String> rComments = new ArrayList<>();
        ArrayList<String> sComments = new ArrayList<>();
        ArrayList<String> bComments = new ArrayList<>();
        ArrayList<String> fbComments = new ArrayList<>();
        ArrayList<String> dbComments = new ArrayList<>();
        ArrayList<String> dtComments = new ArrayList<>();
        ArrayList<String> tComments = new ArrayList<>();
        ArrayList<String> tfComments = new ArrayList<>();
        Random random = new Random();

        // Strength based comments
        strComments.add(fav.getName() + " seems awful strong, I'm not so sure about " + nfav.getName() + "...");
        strComments.add(fav.getName() + " is bringing the gun show! " + nfav.getName() + " on the other hand looks like they could use a sandwhich!");
        // Reach based comments
        rComments.add(nfav.getName() + " has tiny little arms! They'll be no match for the reach on " + fav.getName() + "!");
        rComments.add(fav.getName() + " must be 8ft tall!"  + "Too bad " + fav.getName() + "is a midget!");
        // Speed based comments
        sComments.add(fav.getName() + " is incredibly quick on their feet! " + nfav.getName() + " on the other hand just got my joke from last week...");
        sComments.add(fav.getName() + " must be the fastest fighter alive! " + " Well at least next to " + nfav.getName() + "anyway...");
        // Bloodied based comments
        bComments.add(fav.getName() + " got Bloodied!");
        // FavBloodied based comments
        fbComments.add("What? " +fav.getName() + " got bloodied!");
        // Double Bloodied based comments
        dbComments.add(fav.getName() + " and " + nfav.getName() + " got Bloodied!");
        // Double Takedown based comments
        dtComments.add(fav.getName() + " and " + nfav.getName() + " knocked each other out. Silly, now we have to start over!");
        // Takedown based comments
        tComments.add(fav.getName() + " was taken down!!");
        // TakedownFav based comments
        tfComments.add(fav.getName() + " was taken down... Will admit, didn't see that coming");

        switch(type)
        {
            case STRENGTH:
                return strComments.get(random.nextInt(strComments.size()));
            case REACH:
                return rComments.get(random.nextInt(rComments.size()));
            case SPEED:
                return sComments.get(random.nextInt(sComments.size()));
            case BLOODIED:
                return bComments.get(random.nextInt(bComments.size()));
            case FAVBLOODIED:
                return fbComments.get(random.nextInt(fbComments.size()));
            case DOUBLEBLOODIED:
                return dbComments.get(random.nextInt(dbComments.size()));
            case  TAKEDOWN:
                return tComments.get(random.nextInt(tComments.size()));
            case DOUBLETAKEDOWN:
                return dtComments.get(random.nextInt(dtComments.size()));
            case TAKEDOWNFAV:
                return tfComments.get(random.nextInt(tfComments.size()));
            default:
                return null;
        }
    }

}
