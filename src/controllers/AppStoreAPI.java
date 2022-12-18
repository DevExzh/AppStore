package controllers;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import models.*;
import utils.FoundationClassUtilities;
import utils.ISerializer;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static utils.RatingUtility.generateRandomRating;

/**
 * The responsibility for this class is to store and manage a list of Apps. Note that App is the super class in the hierarchy pictured below, so any subclass objects can be added to this list of Apps e.g. an object of GameApp can be added to it.
 * @author Ryker Zhu
 * @author Mairead Meagher
 */
public class AppStoreAPI implements ISerializer {

    /**
     * <strong>Private Field</strong>
     * <p>ArrayList that contains all the App stored</p>
     */
    private List<App> apps = new ArrayList<>();

    /**
     * <strong>CRUD Method</strong>
     * <p>Add a new App object to the ArrayList</p>
     * @param app App to be added
     * @return Boolean indicating whether the App is successfully added
     */
    public boolean addApp(App app) {
        return apps.add(app);
    }

    /**
     * <strong>CRUD Method</strong>
     * <p>Remove the App object at given index</p>
     * @param index The index of the App to be removed
     * @return The App object that was removed
     */
    public App deleteAppByIndex(int index) {
        if(!isValidIndex(index)) return null;
        App app = apps.get(index);
        apps.remove(app);
        return app;
    }

    /**
     * <strong>CRUD Method</strong>
     * @param index
     * @return
     */
    public App getAppByIndex(int index) {
        if(!isValidIndex(index)) return null;
        return apps.get(index);
    }

    /**
     * <strong>CRUD Method</strong>
     * @param name
     * @return
     */
    public App getAppByName(String name) {
        for(App app : apps) {
            if(app.getAppName().equalsIgnoreCase(name)) {
                return app;
            }
        }
        return null;
    }

    /**
     * <strong>Report Method</strong>
     * @return
     */
    public String listAllApps() {
        if(apps.isEmpty()) return "No apps";
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < apps.size(); i++) {
            sb.append(i).append(": ").append(apps.get(i)).append('\n');
        }
        return sb.toString();
    }

    /**
     * <strong>Report Method</strong>
     * @return
     */
    public String listSummaryOfAllApps() {
        if(apps.isEmpty()) return "No apps";
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < apps.size(); i++) {
            sb.append(i).append(": ").append(apps.get(i).appSummary()).append('\n');
        }
        return sb.toString();
    }

    public String listAllGameApps() {
        if(apps.isEmpty()) return "No Game apps";
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < apps.size(); i++) {
            if(apps.get(i) instanceof GameApp) {
                sb.append(i).append(": ").append(apps.get(i)).append('\n');
            }
        }
        if(sb.isEmpty()) sb.append("No Game apps");
        return sb.toString();
    }

    public String listAllEducationApps() {
        if(apps.isEmpty()) return "No Education apps";
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < apps.size(); i++) {
            if(apps.get(i) instanceof EducationApp) {
                sb.append(i).append(": ").append(apps.get(i)).append('\n');
            }
        }
        if(sb.isEmpty()) sb.append("No Education apps");
        return sb.toString();
    }

    public String listAllProductivityApps() {
        if(apps.isEmpty()) return "No Productivity apps";
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < apps.size(); i++) {
            if(apps.get(i) instanceof ProductivityApp) {
                sb.append(i).append(": ").append(apps.get(i)).append('\n');
            }
        }
        if(sb.isEmpty()) sb.append("No Productivity apps");
        return sb.toString();
    }

    public String listAllAppsByName(String name) {
        if(apps.isEmpty()) return "No apps for name " + name + " exists";
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < apps.size(); i++) {
            if(apps.get(i).getAppName().equalsIgnoreCase(name)) {
                sb.append(i).append(": ").append(apps.get(i)).append('\n');
            }
        }
        if(sb.isEmpty()) sb.append("No apps for name " + name + " exists");
        return sb.toString();
    }

    public String listAllAppsAboveOrEqualAGivenStarRating(int rating) {
        if(apps.isEmpty()) return "No apps have a rating of " + rating + " or above";
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < apps.size(); i++) {
            if(apps.get(i).calculateRating() >= rating)
                sb.append(i).append(": ").append(apps.get(i)).append('\n');
        }
        if(sb.isEmpty()) return "No apps have a rating of " + rating + " or above";
        return sb.toString();
    }

    public String listAllRecommendedApps() {
        if(apps.isEmpty()) return "No recommended apps";
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < apps.size(); i++) {
            if(apps.get(i).isRecommendedApp()) {
                sb.append(i).append(": ").append(apps.get(i)).append('\n');
            }
        }
        if(sb.isEmpty()) sb.append("No recommended apps");
        return sb.toString();
    }

    public String listAllAppsByChosenDeveloper(Developer developer) {
        if(apps.isEmpty()) return "No apps for developer: " + developer;
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < apps.size(); i++) {
            if(apps.get(i).getDeveloper().equals(developer)) {
                sb.append(i).append(": ").append(apps.get(i)).append('\n');
            }
        }
        if(sb.isEmpty()) sb.append("No apps for developer: " + developer);
        return sb.toString();
    }

    public int numberOfAppsByChosenDeveloper(Developer developer) {
        if(apps.isEmpty()) return 0;
        return new FoundationClassUtilities.Statistics<>(apps).total(
                (App app) -> app.getDeveloper().equals(developer));
    }

    public App randomApp() {
        if(apps.isEmpty()) return null;
        return apps.get(new Random().nextInt(apps.size()));
    }

    //---------------------
    // Method to simulate ratings (using the RatingUtility).
    // This will be called from the Driver (see skeleton code)
    //---------------------
    public void simulateRatings(){
        for (App app :apps) {
            app.addRating(generateRandomRating());
        }
    }

    /**
     * <strong>Validation Method</strong>
     * <p>Checks whether the given index is valid</p>
     * @param index The index to check
     * @return true if the index is valid
     */
    public boolean isValidIndex(int index) {
        return (index >= 0) && (index < apps.size());
    }

    /**
     * <strong>Validation Method</strong>
     * <p>Checks whether the given appName is valid</p>
     * @param appName The name of the App
     * @return true if the name of the App is valid
     */
    public boolean isValidAppName(String appName) {
        for(App app : apps) {
            if(app.getAppName().equals(appName))
                return true;
        }
        return false;
    }

    //---------------------
    // Persistence methods
    //---------------------

    @SuppressWarnings("unchecked")
    public void load() throws Exception {
        //list of classes that you wish to include in the serialisation, separated by a comma
        Class<?>[] classes = new Class[]
                {App.class, EducationApp.class, GameApp.class, ProductivityApp.class, Rating.class,
                int.class, boolean.class, String.class, double.class};

        XStream xstream = new XStream(new DomDriver());
        xstream.allowTypes(classes);

        //doing the actual serialisation to an XML file
        ObjectInputStream in = xstream.createObjectInputStream(new FileReader(fileName()));
        apps = (List<App>) in.readObject();
        in.close();
    }

    public void save() throws Exception {
        XStream xstream = new XStream(new DomDriver());
        ObjectOutputStream out = xstream.createObjectOutputStream(new FileWriter(fileName()));
        out.writeObject(apps);
        out.close();
    }

    public String fileName(){
        return "apps.xml";
    }

    /**
     * <strong>Sorting Method</strong>
     * @param apps The list of apps
     * @param i Index of the App
     * @param j Index of the App
     */
    private void swapApps(List<App> apps, int i, int j) {
        App temp = apps.get(i);
        apps.set(i, apps.get(j));
        apps.set(j, temp);
    }

    public void sortAppsByNameAscending() {
        // Selection sort
        for(int i = apps.size() - 1; i >= 0; i--) {
            int highestIndex = 0;
            for(int j = 0; j <= i; j++) {
                if(apps.get(j).getAppName().compareTo(apps.get(highestIndex).getAppName()) > 0) {
                    highestIndex = j;
                }
            }
            swapApps(apps, i, highestIndex);
        }
    }

    /**
     * The number of apps that are currently in the list of apps
     * @return The count of apps that are currently in the list
     */
    public int numberOfApps() {
        return apps.size();
    }
}
