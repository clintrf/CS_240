package familymap.client.Model;

import static android.graphics.Color.BLUE;
import static android.graphics.Color.GREEN;
import static android.graphics.Color.RED;

public class Settings {
    private boolean lifeLines;
    private int lifeLinesColor;

    private boolean treeLines;
    private int treeLinesColor;

    private boolean spouseLines;
    private int spouseLinesColor;

    private boolean fatherSide;
    private boolean motherSide;

    private boolean maleEvents;
    private boolean femaleEvents;

    private  boolean loggedIn;

    public Settings(){
        setLifeLines(true);
        setLifeLinesColor(GREEN);
        setTreeLines(true);
        setTreeLinesColor(BLUE);
        setSpouseLines(true);
        setSpouseLinesColor(RED);
        setFatherSide(true);
        setMotherSide(true);
        setMaleEvents(true);
        setFemaleEvents(true);
        setLoggedIn(false);
    }


    public boolean isLifeLines() { return lifeLines; }
    public void setLifeLines(boolean lifeLines) { this.lifeLines = lifeLines; }

    public int getLifeLinesColor() { return lifeLinesColor; }
    public void setLifeLinesColor(int lifeLinesColor) { this.lifeLinesColor = lifeLinesColor; }

    public boolean isTreeLines() { return treeLines; }
    public void setTreeLines(boolean treeLines) { this.treeLines = treeLines; }

    public int getTreeLinesColor() { return treeLinesColor; }
    public void setTreeLinesColor(int treeLinesColor) { this.treeLinesColor = treeLinesColor; }

    public boolean isSpouseLines() { return spouseLines; }
    public void setSpouseLines(boolean spouseLines) { this.spouseLines = spouseLines; }

    public int getSpouseLinesColor() { return spouseLinesColor; }
    public void setSpouseLinesColor(int spouseLinesColor) { this.spouseLinesColor = spouseLinesColor; }

    public boolean isLoggedIn() { return loggedIn; }
    public void setLoggedIn(boolean loggedIn) { this.loggedIn = loggedIn; }

    public boolean isFatherSide() { return fatherSide; }
    public void setFatherSide(boolean fatherSide) { this.fatherSide = fatherSide; }

    public boolean isMotherSide() { return motherSide; }
    public void setMotherSide(boolean motherSide) { this.motherSide = motherSide; }

    public boolean isMaleEvents() { return maleEvents; }
    public void setMaleEvents(boolean maleEvents) { this.maleEvents = maleEvents; }

    public boolean isFemaleEvents() { return femaleEvents; }

    public void setFemaleEvents(boolean femaleEvents) { this.femaleEvents = femaleEvents; }
}
