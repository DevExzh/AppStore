package models;

import utils.Utilities;

/**
 * Manages the specific information relating to a Productivity App - in this case, we have no additional fields
 * @author Ryker Zhu
 * @see App
 */
public class ProductivityApp extends App {
    /**
     * <strong>Constructor</strong>
     * @see App#App(Developer, String, double, double, double) 
     * @param developer  The developer of the App
     * @param appName    The name of the App
     * @param appSize    How many spaces the App will cost
     * @param appVersion The version of the App
     * @param appCost    How much a consumer should pay
     */
    public ProductivityApp(Developer developer, String appName, double appSize, double appVersion, double appCost) {
        super(developer, appName, appSize, appVersion, appCost);
    }

    @Override
    public boolean isRecommendedApp() {
        return Utilities.greaterThanOrEqualTo(getAppCost(), 1.99) &&
                calculateRating() > 3.0;
    }
}
