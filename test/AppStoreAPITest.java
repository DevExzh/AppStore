import controllers.AppStoreAPI;
import models.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AppStoreAPITest {

    private EducationApp edAppBelowBoundary, edAppOnBoundary, edAppAboveBoundary, edAppInvalidData;
    private ProductivityApp prodAppBelowBoundary, prodAppOnBoundary, prodAppAboveBoundary, prodAppInvalidData;
    private GameApp gameAppBelowBoundary, gameAppOnBoundary, gameAppAboveBoundary, gameAppInvalidData;

    private Developer developerLego = new Developer("Lego", "www.lego.com");
    private Developer developerSphero = new Developer("Sphero", "www.sphero.com");
    private Developer developerEAGames = new Developer("EA Games", "www.eagames.com");
    private Developer developerKoolGames = new Developer("Kool Games", "www.koolgames.com");
    private Developer developerApple = new Developer("Apple", "www.apple.com");
    private Developer developerMicrosoft = new Developer("Microsoft", "www.microsoft.com");

    private AppStoreAPI appStore = new AppStoreAPI();
    private AppStoreAPI emptyAppStore = new AppStoreAPI();

    @BeforeEach
    void setUp() {

        //Validation: appSize(1-1000), appVersion(>=1.0), ageRating (0-18), appCost(>=0), level(1-10).
        edAppBelowBoundary = new EducationApp(developerLego, "WeDo", 1, 1.0, 0,  1);

        edAppOnBoundary = new EducationApp(developerLego, "Spike", 1000, 2.0,
                1.99, 10);

        edAppAboveBoundary = new EducationApp(developerLego, "EV3", 1001, 3.5,  2.99,  11);

        edAppInvalidData = new EducationApp(developerLego, "", -1, 0, -1.00,  0);


        //Validation: appSize(1-1000), appVersion(>=1.0), ageRating (0-18), appCost(>=0),
        prodAppBelowBoundary = new ProductivityApp(developerApple, "NoteKeeper", 1, 1.0, 0.0);

        prodAppOnBoundary = new ProductivityApp(developerMicrosoft, "Outlook", 1000, 2.0, 1.99);

        prodAppAboveBoundary = new ProductivityApp(developerApple, "Pages", 1001, 3.5, 2.99);

        prodAppInvalidData = new ProductivityApp(developerMicrosoft, "", -1, 0, -1.00);


        //Validation: appSize(1-1000), appVersion(>=1.0), ageRating (0-18), appCost(>=0),
        gameAppBelowBoundary = new GameApp(developerEAGames, "Tetris", 1, 1.0, 0.0,  false);

        gameAppOnBoundary = new GameApp(developerKoolGames, "CookOff", 1000, 2.0, 1.99,  true);

        gameAppAboveBoundary = new GameApp(developerEAGames, "Empires", 1001, 3.5,  2.99, false);

        gameAppInvalidData = new GameApp(developerKoolGames, "", -1, 0,  -1.00,  true);

        // Re-write the code

        // EducationApp
        appStore.addApp(edAppOnBoundary); // index 0
        appStore.addApp(edAppBelowBoundary); // index 1
        appStore.addApp(edAppAboveBoundary); // index 2
        appStore.addApp(edAppInvalidData); // index 3

        // ProductivityApp
        appStore.addApp(prodAppOnBoundary); // index 4
        appStore.addApp(prodAppBelowBoundary); // index 5
        appStore.addApp(prodAppAboveBoundary); // index 6
        appStore.addApp(prodAppInvalidData); // index 7

        // GameApp
        appStore.addApp(gameAppOnBoundary); // index 8
        appStore.addApp(gameAppBelowBoundary); // index 9
        appStore.addApp(gameAppAboveBoundary); // index 10
        appStore.addApp(gameAppInvalidData); // index 11
    }

    @AfterEach
    void tearDown() {
        edAppBelowBoundary = edAppOnBoundary = edAppAboveBoundary = edAppInvalidData = null;
        gameAppBelowBoundary = gameAppOnBoundary = gameAppAboveBoundary = gameAppInvalidData = null;
        prodAppBelowBoundary = prodAppOnBoundary = prodAppAboveBoundary = prodAppInvalidData = null;
        developerApple = developerEAGames = developerKoolGames = developerLego = developerMicrosoft = null;
        appStore = emptyAppStore = null;
    }

    @Nested
    class GettersAndSetters {

    }

    // Written by Ryker Zhu
    @Nested
    class CRUDMethods {
        @Test
        void addingAnAppToArrayList() {
            App app = setupProductivityAppWithRating(2,4); // index 12
            assertEquals(12, appStore.numberOfApps());
            assertTrue(appStore.addApp(app));
            assertTrue(appStore.getAppByName("Evernote").getAppName().contains("Evernote"));
            assertEquals(13, appStore.numberOfApps());
            appStore.deleteAppByIndex(12);
        }

        @Test
        void gettingAppByName() {
            App app = setupProductivityAppWithRating(2,4); // index 12
            appStore.addApp(app);
            assertNull(appStore.getAppByName("AppThatCannotExist"));
            assertEquals("Evernote", appStore.getAppByName("Evernote").getAppName());
            assertEquals("Evernote", appStore.getAppByName("eVeRnoTe").getAppName());
            appStore.deleteAppByIndex(12);
        }

        @Test
        void gettingAppByIndex() {
            assertNull(appStore.getAppByIndex(-1));
            assertNull(appStore.getAppByIndex(666));
            assertEquals(edAppOnBoundary,appStore.getAppByIndex(0));
            assertEquals(prodAppBelowBoundary,appStore.getAppByIndex(5));
        }

        @Test
        void removingAnAppThatDoesNotExistReturnsNull() {
            assertNull(appStore.deleteAppByIndex(666));
            assertNull(appStore.deleteAppByIndex(-200));
            assertNull(appStore.deleteAppByIndex(13));
        }

        @Test
        void removingAnAppFromArrayList() {
            App app = setupProductivityAppWithRating(2,4); // index 12
            appStore.addApp(app);
            assertEquals(13, appStore.numberOfApps());
            assertEquals(app, appStore.deleteAppByIndex(12));
            assertEquals(12, appStore.numberOfApps());
        }
    }

    @Nested
    class ListingMethods {

        @Test
        void listAllAppsReturnsNoAppsStoredWhenArrayListIsEmpty() {
            assertEquals(0, emptyAppStore.numberOfApps());
            assertTrue(emptyAppStore.listAllApps().toLowerCase().contains("no apps"));
        }

        @Test
        void listAllAppsReturnsAppsStoredWhenArrayListHasAppsStored() {
            assertEquals(12, appStore.numberOfApps());
            String apps = appStore.listAllApps();
            //checks for objects in the string
            assertTrue(apps.contains("WeDo"));
            assertTrue(apps.contains("Outlook"));
            assertTrue(apps.contains("Empires"));
            assertTrue(apps.contains("NoteKeeper"));
            assertTrue(apps.contains("EV3"));
            assertTrue(apps.contains("CookOff"));
        }

        @Test
        void listRecommendedAppsReturnsNoAppsWhenRecommendedAppsDoNotExist() {
            assertEquals(12, appStore.numberOfApps());

            String apps = appStore.listAllRecommendedApps();
            //checks for the three objects in the string
            assertTrue(apps.contains("No recommended apps"));
        }

        @Test
        void listRecommendedAppsReturnsRecommendedAppsWhenTheyExist() {
            assertEquals(12, appStore.numberOfApps());

            //adding recommended apps to the list
            appStore.addApp(setupGameAppWithRating(5,4));
            appStore.addApp(setupEducationAppWithRating(3,4));
            appStore.addApp(setupProductivityAppWithRating(3,4));
            assertEquals(15, appStore.numberOfApps());

            String apps = appStore.listAllRecommendedApps();
            System.out.println(apps);
            //checks for the three objects in the string
            assertTrue(apps.contains("MazeRunner"));
            assertTrue(apps.contains("Evernote"));
            assertTrue(apps.contains("WeDo"));
        }


    }

    @Nested
    class ReportingMethods {

    }

    @Nested
    class SearchingMethods {

    }

    @Nested
    class SortingMethods {

        @Test
        void sortByNameAscendingReOrdersList() {
            assertEquals(12, appStore.numberOfApps());
            //checks the order of the objects in the list
            assertEquals(edAppOnBoundary, appStore.getAppByIndex(0));
            assertEquals(edAppBelowBoundary, appStore.getAppByIndex(1));
            assertEquals(edAppAboveBoundary, appStore.getAppByIndex(2));
            assertEquals(edAppInvalidData, appStore.getAppByIndex(3));
            assertEquals(prodAppOnBoundary, appStore.getAppByIndex(4));
            assertEquals(prodAppBelowBoundary, appStore.getAppByIndex(5));
            assertEquals(prodAppAboveBoundary, appStore.getAppByIndex(6));

            appStore.sortAppsByNameAscending();

            assertEquals(gameAppOnBoundary, appStore.getAppByIndex(3));
            assertEquals(edAppAboveBoundary, appStore.getAppByIndex(4));
            assertEquals(gameAppAboveBoundary, appStore.getAppByIndex(5));
            assertEquals(prodAppBelowBoundary, appStore.getAppByIndex(6));
            assertEquals(prodAppOnBoundary, appStore.getAppByIndex(7));
        }

        @Test
        void sortByNameAscendingDoesntCrashWhenListIsEmpty() {
            assertEquals(0,emptyAppStore.numberOfApps());
            emptyAppStore.sortAppsByNameAscending();
        }

    }

    //--------------------------------------------
    // Helper Methods
    //--------------------------------------------
    EducationApp setupEducationAppWithRating(int rating1, int rating2) {
        //setting all conditions to true
        EducationApp edApp = new EducationApp(developerLego, "WeDo", 1,
                1.0, 1.00, 3);
        edApp.addRating(new Rating(rating1, "John Doe", "Very Good"));
        edApp.addRating(new Rating(rating2, "Jane Doe", "Excellent"));

        return edApp;
    }

    GameApp setupGameAppWithRating(int rating1, int rating2) {
        GameApp gameApp = new GameApp(developerEAGames, "MazeRunner", 1,
                1.0, 1.00, true);
        gameApp.addRating(new Rating(rating1, "John Soap", "Exciting Game"));
        gameApp.addRating(new Rating(rating2, "Jane Soap", "Nice Game"));
        return gameApp;
    }

    ProductivityApp setupProductivityAppWithRating(int rating1, int rating2) {
        ProductivityApp productivityApp = new ProductivityApp(developerApple, "Evernote", 1,
                1.0, 1.99);

        productivityApp.addRating(new Rating(rating1, "John101", "So easy to add a note"));
        productivityApp.addRating(new Rating(rating2, "Jane202", "So useful"));
        return productivityApp;
    }

}
