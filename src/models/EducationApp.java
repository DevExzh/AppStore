package models;

import utils.Utilities;

/**
 * Manages the specific information relating to an Education App i.e. the level the app is aimed at (1-10)
 * @author Ryker Zhu
 * @see App
 */
public class EducationApp extends App {
    private int level = 0;

    /**
     * <strong>Constructor</strong>
     * @see App#App(Developer, String, double, double, double)
     * @param developer  The developer of the App
     * @param appName    The name of the App
     * @param appSize    How many spaces the App will cost
     * @param appVersion The version of the App
     * @param appCost    How much a consumer should pay
     */
    public EducationApp(Developer developer, String appName, double appSize, double appVersion, double appCost, int level) {
        super(developer, appName, appSize, appVersion, appCost);
        setLevel(level);
    }

    /**
     * This method returns a boolean indicating if the app is recommended or not.
     */
    @Override
    public boolean isRecommendedApp() {
        return getAppCost() > 0.99 &&
                Utilities.greaterThanOrEqualTo(calculateRating(), 3.5) &&
                Utilities.greaterThanOrEqualTo(level, 3);

    }

    @Override
    public String appSummary() {
        return super.appSummary() + ", level " + getLevel() + ".";
    }

    @Override
    public String toString() {
        return super.toString() + ", Level: " + getLevel() + ".";
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        if(Utilities.validRange(level, 1, 10)) this.level = level;
    }
}
