package familymap.client.Model;

import familymap.client.R;

public class Settings {
    private boolean loggedIn;
    private String mapType;

    private boolean lifeLines;
    private int lifeLinesColor;

    private boolean treeLines;
    private int treeLinesColor;

    private boolean spouseLines;
    private int spouseLinesColor;

    Settings(){
        setLoggedIn(false);
        setMapType("n");

        setLifeLines(true);
        setTreeLines(true);
        setSpouseLines(true);

        setLifeLinesColor(R.color.green_color);
        setTreeLinesColor(R.color.blue_color);
        setSpouseLinesColor(R.color.red_color);


    }

    public boolean isLoggedIn() { return loggedIn; }
    public void setLoggedIn(boolean loggedIn) { this.loggedIn = loggedIn; }

    public String getMapType() { return mapType; }
    public void setMapType(String mapType) { this.mapType = mapType; }

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
}
