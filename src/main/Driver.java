package main;

import controllers.AppStoreAPI;
import controllers.DeveloperAPI;
import models.*;
import utils.FoundationClassUtilities;
import utils.ScannerInput;
import utils.Utilities;

/**
 * The responsibility for this class is to manage the User Interface (UI) i.e. the menu and user input/output. This class should be the only class that has System.out.println() or Scanner reads in it. This class contains an object of AppStoreAPI and an object of DeveloperAPI.
 * @author Ryker Zhu
 * @author Mairead Meagher
 * @link <a href="https://github.com/DevExzh/">GitHub Link</a>
 */
public class Driver {
    private final DeveloperAPI developerAPI = new DeveloperAPI();
    private final AppStoreAPI appStoreAPI = new AppStoreAPI();
    private String currencySymbol = "€";

    public static void main(String[] args) {
        new Driver().start();
    }

    public void start() {
        loadAllData();
        runMainMenu();
    }

    private int mainMenu() {
        System.out.println("""
                 -------------App Store------------
                |  1) Developer - Management MENU  |
                |  2) App - Management MENU        |
                |  3) Reports MENU                 |
                |----------------------------------|
                |  4) Search                       |
                |  5) Sort                         |
                |----------------------------------|
                |  6) Recommended Apps             |
                |  7) Random App of the Day        |
                |  8) Simulate ratings             |
                |----------------------------------|
                |  9) Region preferences           |
                |----------------------------------|
                |  20) Save all                    |
                |  21) Load all                    |
                |----------------------------------|
                |  0) Exit                         |
                 ----------------------------------""");
        return ScannerInput.validNextInt("==>> ");
    }

    private void runMainMenu() {
        int option = mainMenu();
        while (option != 0) {
            switch (option) {
                case 1 -> runDeveloperMenu();
                case 2 -> runAppMenu();
                case 3 -> runReportMenu();
                case 4 -> searchAppsBySpecificCriteria();
                case 5 -> appStoreAPI.sortAppsByNameAscending(); // Sort Apps by Name
                case 6 -> // print the recommended apps
                        System.out.println(appStoreAPI.listAllRecommendedApps());
                case 7 -> {
                    System.out.println("=== App of the Day ==="); // print the random app of the day
                    System.out.println(appStoreAPI.randomApp());
                }
                case 8 -> simulateRatings();
                case 9 -> runRegionMenu();
                case 20 -> saveAllData();
                case 21 -> loadAllData();
                default -> System.out.println("Invalid option entered: " + option);
            }
            ScannerInput.validNextLine("\n Press the enter key to continue");
            option = mainMenu();
        }
        exitApp();
    }

    private void runRegionMenu() {
        switch (FoundationClassUtilities.scanValidInteger("""
                -------- Region Preference --------
                |  1) Change the currency         |
                |  0) RETURN to main menu         |
                |---------------------------------|
                """, (option) -> option >= 0 && option <= 1)) {
            default:
                ScannerInput.validNextLine("Invalid option... Return to main menu.\nPress any key to continue...");
            case 0:
                return;
            case 1:
                currencySymbol = switch (FoundationClassUtilities.scanValidInteger("""
                        --------------------------------
                            1) EUR €         2) CNY ￥
                            3) USD $         4) GBP £
                       
                         Current currency:\s
                        """ + currencySymbol +
                        "--------------------------------", (option) -> option >= 0 && option <= 1)) {
                    default -> "€";
                    case 2 -> "￥";
                    case 3 -> "$";
                    case 4 -> "£";
                };
                for(int i = 0; i < appStoreAPI.numberOfApps(); ++i) {
                    App app = appStoreAPI.getAppByIndex(i);
                    if(!currencySymbol.equals(app.getCurrencySymbol())) {
                        app.setCurrencySymbol(currencySymbol);
                    }
                }
                break;
        }
    }

    private void exitApp() {
        saveAllData();
        System.out.println("Exiting....");
        System.exit(0);
    }

    //---------------------
    // App Management
    // --------------------
    private void runAppMenu() {
        switch (FoundationClassUtilities.scanValidInteger("""
                |--------App Store Menu--------|
                |  1) Add an app               |
                |  2) Update an app            |
                |  3) Delete an app            |
                |  0) RETURN to main menu      |
                |------------------------------|
                """, (choice) -> choice >= 0 && choice <= 5)) {
            default:
                ScannerInput.validNextLine("Invalid option... Return to main menu.\nPress any key to continue...");
            case 0:
                return;
            case 1: // Add an app
                String appType = FoundationClassUtilities.scanValidString(
                        "Which type of App would you like to add [Education / Game / Productivity]? ",
                        new String[] {"e", "education", "g", "game", "p", "productivity"});
                Developer developer = readValidDeveloperByName();
                String appName = ScannerInput.validNextLine("Please enter the name of the App: ");
                double appSize = FoundationClassUtilities.scanValidDouble("Please enter the size of the App: ",
                        (value) -> value >= 1 && value <= 1000); // [1, 1000]
                double appVersion = FoundationClassUtilities.scanValidDouble("Please enter the version of the App: ",
                        (value) -> Utilities.greaterThanOrEqualTo(value, 1.0)); // >= 1.0
                double appCost = FoundationClassUtilities.scanValidDouble("Please enter the cost of the App: ",
                        (value) -> Utilities.greaterThanOrEqualTo(value, 0)); // >= 0
                System.out.println(appStoreAPI.addApp(switch (appType.toLowerCase()) {
                    case "education", "e" ->
                            new EducationApp(developer, appName, appSize, appVersion, appCost,
                                    ScannerInput.validNextInt("Please enter the level of the education app: "));
                    case "game", "g" ->
                            new GameApp(developer, appName, appSize, appVersion, appCost, Utilities.YNtoBoolean(
                                    ScannerInput.validNextChar("Does the game support multiplayer? [Y/n]: ")));
                    case "productivity", "p" ->
                            new ProductivityApp(developer, appName, appSize, appVersion, appCost);
                    default -> // Will never reach here, just to be safe
                            new ProductivityApp(developer, "", -1, -1, -1);
                }) ? "The app has been added successfully." : "There was an error adding the app.");
                break;
            case 2: // Update an app
                appStoreAPI.listAllApps();
                App appToBeUpdated = appStoreAPI.getAppByIndex(
                        FoundationClassUtilities.scanValidInteger(
                                "Please choose the index of the App you want to update: ",
                                appStoreAPI::isValidIndex));
                switch (FoundationClassUtilities.scanValidString(
                        "Which property would you like to update? [Name / Description / Size / Cost / Version]",
                        new String[] {"n", "name", "d", "description", "s", "size", "c", "cost", "v", "version"})) {
                    case "n", "name" -> appToBeUpdated.setAppName(
                            ScannerInput.validNextLine("Please enter the name of the App: "));
                    case "d", "description" -> appToBeUpdated.setDescription(
                            ScannerInput.validNextLine("Please enter the description of the App: "));
                    case "s", "size" -> appToBeUpdated.setAppSize(
                            FoundationClassUtilities.scanValidDouble("Please enter the size of the App: ",
                                    (value) -> value >= 1 && value <= 1000));
                    case "c", "cost" -> appToBeUpdated.setAppCost(
                            FoundationClassUtilities.scanValidDouble("Please enter the cost of the App: ",
                                    (value) -> Utilities.greaterThanOrEqualTo(value, 0)));
                    case "v", "version" -> appToBeUpdated.setAppVersion(
                            FoundationClassUtilities.scanValidDouble("Please enter the version of the App: ",
                                    (value) -> Utilities.greaterThanOrEqualTo(value, 1.0)));
                }
                break;
            case 3: // Delete an app
                appStoreAPI.listAllApps();
                System.out.println(
                        appStoreAPI.deleteAppByIndex(
                                FoundationClassUtilities.scanValidInteger(
                                        "Please choose the index of the App you want to delete: ",
                                        appStoreAPI::isValidIndex)) == null
                                ? "There was an error deleting the specified App."
                                : "The App has been deleted successfully.");
                break;

        }
    }

    //---------------------
    // Reports
    // --------------------
    private void runReportMenu() {

    }

    //--------------------------------------------------
    //  Developer Management - Menu Items
    //--------------------------------------------------
    private int developerMenu() {
        System.out.println("""
                 -------Developer Menu-------
                |   1) Add a developer       |
                |   2) List developer        |
                |   3) Update developer      |
                |   4) Delete developer      |
                |   0) RETURN to main menu   |
                 ----------------------------""");
        return ScannerInput.validNextInt("==>> ");
    }

    private void runDeveloperMenu() {
        int option = developerMenu();
        while (option != 0) {
            switch (option) {
                case 1 -> addDeveloper();
                case 2 -> System.out.println(developerAPI.listDevelopers());
                case 3 -> updateDeveloper();
                case 4 -> deleteDeveloper();
                default -> System.out.println("Invalid option entered" + option);
            }
            ScannerInput.validNextLine("\n Press the enter key to continue");
            option = developerMenu();
        }
    }

    private void addDeveloper() {
        String developerName = ScannerInput.validNextLine("Please enter the developer name: ");
        String developerWebsite = ScannerInput.validNextLine("Please enter the developer website: ");

        if (developerAPI.addDeveloper(new Developer(developerName, developerWebsite))) {
            System.out.println("Add successful");
        } else {
            System.out.println("Add not successful");
        }
    }

    private void updateDeveloper() {
        System.out.println(developerAPI.listDevelopers());
        Developer developer = readValidDeveloperByName();
        if (developer != null) {
            String developerWebsite = ScannerInput.validNextLine("Please enter new website: ");
            if (developerAPI.updateDeveloperWebsite(developer.getDeveloperName(), developerWebsite))
                System.out.println("Developer Website Updated");
            else
                System.out.println("Developer Website NOT Updated");
        } else
            System.out.println("Developer name is NOT valid");
    }

    private void deleteDeveloper() {
        String developerName = ScannerInput.validNextLine("Please enter the developer name: ");
        if (developerAPI.removeDeveloper(developerName) != null) {
            System.out.println("Delete successful");
        } else {
            System.out.println("Delete not successful");
        }
    }

    private Developer readValidDeveloperByName() {
        String developerName = ScannerInput.validNextLine("Please enter the developer's name: ");
        if (developerAPI.isValidDeveloper(developerName)) {
            return developerAPI.getDeveloperByName(developerName);
        } else {
            return null;
        }
    }

    private void searchAppsBySpecificCriteria() {
        System.out.println("""
                What criteria would you like to search apps by:
                  1) App Name
                  2) Developer Name
                  3) Rating (all apps of that rating or above)""");
        int option = ScannerInput.validNextInt("==>> ");
        switch (option) {
            case 1 -> searchAppsByName();
            case 2 -> searchAppsByDeveloper(readValidDeveloperByName());
            case 3 -> searchAppsEqualOrAboveAStarRating();
            default -> System.out.println("Invalid option");
        }
    }

    private void searchAppsEqualOrAboveAStarRating() {
        System.out.println(
                appStoreAPI.listAllAppsAboveOrEqualAGivenStarRating(
                        FoundationClassUtilities.scanValidInteger(
                                "Please enter the rating that you want to search: ",
                                (integer) -> integer >= 1 && integer <= 5)));
    }

    private void searchAppsByDeveloper(Developer developer) {
        System.out.println(appStoreAPI.listAllAppsByChosenDeveloper(developer));
    }

    private void searchAppsByName() {
        System.out.println(appStoreAPI.listAllAppsByName(ScannerInput.validNextLine("Please enter a name to search: ")));
    }

    private void simulateRatings() {
        // simulate random ratings for all apps (to give data for recommended apps and reports etc.).
        if (appStoreAPI.numberOfApps() > 0) {
            System.out.println("Simulating ratings...");
            appStoreAPI.simulateRatings();
            System.out.println(appStoreAPI.listSummaryOfAllApps());
        } else {
            System.out.println("No apps");
        }
    }

    //--------------------------------------------------
    //  Persistence Menu Items
    //--------------------------------------------------

    private void saveAllData() {
        try {
            developerAPI.save();
            appStoreAPI.save();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadAllData() {
        try {
            developerAPI.load();
            appStoreAPI.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
