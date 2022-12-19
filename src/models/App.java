package models;

import utils.FoundationClassUtilities.Statistics;
import utils.Utilities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Manages the common information relating to an App i.e. app name, app version, a collection of ratings for an app, etc.
 * @author Ryker Zhu
 * @see EducationApp
 * @see ProductivityApp
 * @see GameApp
 */
public abstract class App {

    public Developer getDeveloper() {
        return developer;
    }

    public void setDeveloper(Developer developer) {
        this.developer = developer;
    }

    private final HashSet<Language> languages = new HashSet<>();

    public void addLanguage(Language language) {
        languages.add(language);
    }

    public void removeLanguage(Language language) {
        languages.remove(language);
    }

    public List<Language> getLanguages() {
        return new ArrayList<>(languages);
    }

    private String description = "";

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    /**
     * <strong>Private Field</strong>
     * <p>The developer of the App</p>
     * <p>the developer has to be registered in the system in order to add them as a developer for an app (this validation should be handled in the Driver)</p>
     */
    private Developer developer;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    /**
     * <strong>Private Field</strong>
     * <p>The name of the App</p>
     * <p>must be unique in the system (regardless of case - this validation should be handled in Driver). Default value "No app name"</p>
     */
    private String appName = "No app name";

    public double getAppSize() {
        return appSize;
    }

    public void setAppSize(double appSize) {
        if(appSize >= 1 && appSize <= 1000) this.appSize = appSize;
    }

    /**
     * <strong>Private Field</strong>
     * <p>How many spaces the App will cost</p>
     * <p>measured in MB - range from 1 to 1000 inclusive. Default value 0</p>
     */
    private double appSize = 0;

    public double getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(double appVersion) {
        if(appVersion >= 1.0) this.appVersion = appVersion;
    }
    /**
     * <strong>Private Field</strong>
     * <p>The version of the App</p>
     * <p>version number >= 1.0. Default value 1.0.</p>
     */
    private double appVersion = 1.0;

    public double getAppCost() {
        return appCost;
    }

    public void setAppCost(double appCost) {
        if(appCost >= 0) this.appCost = appCost;
    }

    /**
     * <strong>Private Field</strong>
     * <p>How much a consumer should pay</p>
     * <p>zero or more…no upper limit. default value 0.0 as apps can be free.</p>
     */
    private double appCost = 0;

    public List<Rating> getRatings() {
        return ratings;
    }

    /**
     * <strong>Private Field</strong>
     * <p>contains all ratings for a specific app…the overall rating score is calculated from the arraylist entries. Note how the Driver "simulates" ratings (code is given for that).</p>
     */
    private final List<Rating> ratings = new ArrayList<>();

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    private String currencySymbol = "€";

    /**
     * <strong>Constructor</strong>
     * @param developer The developer of the App
     * @param appName The name of the App
     * @param appSize How many spaces the App will cost
     * @param appVersion The version of the App
     * @param appCost How much a consumer should pay
     */
    public App(Developer developer, String appName, double appSize, double appVersion, double appCost) {
        this.developer = developer;
        this.appName = appName;
        if(Utilities.validRange(appSize, 0, 1000)) this.appSize = appSize;
        if(Utilities.greaterThanOrEqualTo(appVersion, 1.0)) this.appVersion = appVersion;
        if(Utilities.greaterThanOrEqualTo(appCost, 0)) this.appCost = appCost;
    }

    public abstract boolean isRecommendedApp();

    public double calculateRating() {
        if(ratings.isEmpty()) return 0;
        return new Statistics<>(ratings)
                .average((Rating r) -> (double)r.getNumberOfStars(),
                        (Rating r) -> r.getNumberOfStars() != 0);
    }

    public String appSummary() {
        StringBuilder sb = new StringBuilder();
        sb.append(appName)
                .append("(V").append(appVersion).append(") by ") // App version
                .append(developer).append(", ") // Developer name
                .append(appCost == 0 ? "Free" : currencySymbol + appCost) // App cost
                .append(". Rating: ").append(calculateRating()).append(". ") // Rating
                .append("Supported languages: ");
        for(Language language : languages) {
            sb.append(language.toString()).append(", ");
        }
        if(sb.substring(sb.length() - 2, sb.length()).equals(", ")) {
            sb.delete(sb.length() - 2, sb.length());
        }
        sb.append('.');
        return sb.toString();
    }

    public boolean addRating(Rating rating) {
        return ratings.add(rating);
    }

    public String listRatings() {
        if(ratings.isEmpty()) return "No ratings added yet.";
        StringBuilder sb = new StringBuilder();
        for(Rating r : ratings) {
            sb.append(r.toString()).append("\n");
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return appName + "(Version " +
                appVersion + ") by " + developer +
                ", Size: " + appSize + "MB" +
                ", Cost: " + appCost +
                ", Ratings (" + calculateRating() + "): " + ratings;
    }
}
