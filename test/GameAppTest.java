import models.Developer;
import models.GameApp;
import models.Rating;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static models.GameApp.Genre.*;
import static org.junit.jupiter.api.Assertions.*;

public class GameAppTest {

    private GameApp gameAppBelowBoundary, gameAppOnBoundary, gameAppAboveBoundary, gameAppInvalidData;
    private Developer developerLego = new Developer("Lego", "www.lego.com");
    private Developer developerSphero = new Developer("Sphero", "www.sphero.com");

    @BeforeEach
    void setUp() {
        //Validation: appSize(1-1000), appVersion(>=1.0), ageRating (0-18), appCost(>=0), level(1-10).
        gameAppBelowBoundary = new GameApp(developerLego, "WeDo", 1, 1.0, 0, false);
        gameAppOnBoundary = new GameApp(developerLego, "Spike", 1000, 2.0, 1.99, true);
        gameAppAboveBoundary = new GameApp(developerLego, "EV3", 1001, 3.5, 2.99, true);
        gameAppInvalidData = new GameApp(developerLego, "", -1, 0, -1.00,false);
        gameAppBelowBoundary.addGenre(GameApp.Genre.Casual);
        gameAppBelowBoundary.addGenre(GameApp.Genre.Anime);
        gameAppOnBoundary.addGenre(GameApp.Genre.Puzzle);
        gameAppAboveBoundary.addGenre(GameApp.Genre.MassivelyMultiplayer);
        gameAppAboveBoundary.addGenre(GameApp.Genre.Racing);
        gameAppInvalidData.addGenre(GameApp.Genre.Action);
    }

    @AfterEach
    void tearDown() {
        gameAppBelowBoundary = gameAppOnBoundary = gameAppAboveBoundary = gameAppInvalidData = null;
        developerLego = developerSphero = null;
    }

    @Nested
    class Getters {

        @Test
        void getDeveloper() {
            assertEquals(developerLego, gameAppBelowBoundary.getDeveloper());
            assertEquals(developerLego, gameAppOnBoundary.getDeveloper());
            assertEquals(developerLego, gameAppAboveBoundary.getDeveloper());
            assertEquals(developerLego, gameAppInvalidData.getDeveloper());
        }

        @Test
        void getAppName() {
            assertEquals("WeDo", gameAppBelowBoundary.getAppName());
            assertEquals("Spike", gameAppOnBoundary.getAppName());
            assertEquals("EV3", gameAppAboveBoundary.getAppName());
            assertEquals("", gameAppInvalidData.getAppName());
        }

        @Test
        void getAppSize() {
            assertEquals(1, gameAppBelowBoundary.getAppSize());
            assertEquals(1000, gameAppOnBoundary.getAppSize());
            assertEquals(0, gameAppAboveBoundary.getAppSize());
            assertEquals(0, gameAppInvalidData.getAppSize());
        }

        @Test
        void getAppVersion() {
            assertEquals(1.0, gameAppBelowBoundary.getAppVersion());
            assertEquals(2.0, gameAppOnBoundary.getAppVersion());
            assertEquals(3.5, gameAppAboveBoundary.getAppVersion());
            assertEquals(1.0, gameAppInvalidData.getAppVersion());
        }

        @Test
        void getAppCost() {
            assertEquals(0, gameAppBelowBoundary.getAppCost());
            assertEquals(1.99, gameAppOnBoundary.getAppCost());
            assertEquals(2.99, gameAppAboveBoundary.getAppCost());
            assertEquals(0, gameAppInvalidData.getAppCost());
        }

        @Test
        void isMultiplayer() {
            assertFalse(gameAppBelowBoundary.isMultiplayer());
            assertTrue(gameAppOnBoundary.isMultiplayer());
            assertTrue(gameAppAboveBoundary.isMultiplayer());
            assertFalse(gameAppInvalidData.isMultiplayer());
        }

        @Test
        void getGenres() {
            assertEquals(2, gameAppBelowBoundary.getGenres().size());
            assertTrue(gameAppBelowBoundary.getGenres().contains(GameApp.Genre.Casual));
            assertTrue(gameAppBelowBoundary.getGenres().contains(GameApp.Genre.Anime));
            assertEquals(1, gameAppOnBoundary.getGenres().size());
            assertTrue(gameAppOnBoundary.getGenres().contains(GameApp.Genre.Puzzle));
            assertEquals(2, gameAppAboveBoundary.getGenres().size());
            assertTrue(gameAppAboveBoundary.getGenres().contains(GameApp.Genre.MassivelyMultiplayer));
            assertTrue(gameAppAboveBoundary.getGenres().contains(GameApp.Genre.Racing));
            assertEquals(1, gameAppInvalidData.getGenres().size());
            assertTrue(gameAppInvalidData.getGenres().contains(GameApp.Genre.Action));
        }
    }

    @Nested
    class Setters {

        @Test
        void setDeveloper() {
            //no validation in models
            assertEquals(developerLego, gameAppBelowBoundary.getDeveloper());
            gameAppBelowBoundary.setDeveloper(developerSphero);
            assertEquals(developerSphero, gameAppBelowBoundary.getDeveloper());
        }

        @Test
        void setAppName() {
            //no validation in models
            assertEquals("WeDo", gameAppBelowBoundary.getAppName());
            gameAppBelowBoundary.setAppName("Mindstorms");
            assertEquals("Mindstorms", gameAppBelowBoundary.getAppName());
        }

        @Test
        void setAppSize() {
            //Validation: appSize(1-1000)
            assertEquals(1, gameAppBelowBoundary.getAppSize());

            gameAppBelowBoundary.setAppSize(1000);
            assertEquals(1000, gameAppBelowBoundary.getAppSize()); //update

            gameAppBelowBoundary.setAppSize(1001);
            assertEquals(1000, gameAppBelowBoundary.getAppSize()); //no update

            gameAppBelowBoundary.setAppSize(2);
            assertEquals(2, gameAppBelowBoundary.getAppSize()); //update

            gameAppBelowBoundary.setAppSize(0);
            assertEquals(2, gameAppBelowBoundary.getAppSize()); //no update
        }

        @Test
        void setAppVersion() {
            //Validation: appVersion(>=1.0)
            assertEquals(1.0, gameAppBelowBoundary.getAppVersion());

            gameAppBelowBoundary.setAppVersion(2.0);
            assertEquals(2.0, gameAppBelowBoundary.getAppVersion()); //update

            gameAppBelowBoundary.setAppVersion(0.0);
            assertEquals(2.0, gameAppBelowBoundary.getAppVersion()); //no update

            gameAppBelowBoundary.setAppVersion(1.0);
            assertEquals(1.0, gameAppBelowBoundary.getAppVersion()); //update
        }

        @Test
        void setAppCost() {
            //Validation: appCost(>=0)
            assertEquals(0.0, gameAppBelowBoundary.getAppCost());

            gameAppBelowBoundary.setAppCost(1.0);
            assertEquals(1.0, gameAppBelowBoundary.getAppCost()); //update

            gameAppBelowBoundary.setAppCost(-1);
            assertEquals(1.0, gameAppBelowBoundary.getAppCost()); //no update

            gameAppBelowBoundary.setAppCost(0.0);
            assertEquals(0.0, gameAppBelowBoundary.getAppCost()); //update
        }

        @Test
        void setMultiplayer() {
            gameAppBelowBoundary.setMultiplayer(true);
            assertTrue(gameAppBelowBoundary.isMultiplayer());
            gameAppOnBoundary.setMultiplayer(false);
            assertFalse(gameAppOnBoundary.isMultiplayer());
            gameAppAboveBoundary.setMultiplayer(false);
            assertFalse(gameAppAboveBoundary.isMultiplayer());
            gameAppInvalidData.setMultiplayer(true);
            assertTrue(gameAppInvalidData.isMultiplayer());
        }

        @Test
        void addGenres() {
            assertTrue(gameAppOnBoundary.addGenre(RPG));
            assertTrue(gameAppOnBoundary.addGenre(Sports));
            assertTrue(gameAppOnBoundary.getGenres().contains(RPG));
            assertTrue(gameAppOnBoundary.getGenres().contains(Sports));
            assertTrue(gameAppAboveBoundary.addGenre(Survival));
            assertTrue(gameAppAboveBoundary.getGenres().contains(Survival));
            assertFalse(gameAppAboveBoundary.getGenres().contains(Indie));
            assertTrue(gameAppInvalidData.addGenre(Strategy));
            assertTrue(gameAppInvalidData.getGenres().contains(Strategy));
        }

        @Test
        void removeGenres() {
            gameAppBelowBoundary.removeGenre(GameApp.Genre.Casual);
            assertFalse(gameAppBelowBoundary.getGenres().contains(GameApp.Genre.Casual));
            gameAppAboveBoundary.removeGenre(GameApp.Genre.Racing);
            assertFalse(gameAppAboveBoundary.getGenres().contains(GameApp.Genre.Racing));
        }
    }

    @Nested
    class ObjectStateMethods {

        @Test
        void appSummaryReturnsCorrectString() {
            GameApp gameApp = setupGameAppWithRating(3, 4);
            String stringContents = gameApp.appSummary();

            assertTrue(stringContents.contains(gameApp.getAppName() + "(V" + gameApp.getAppVersion()));
            assertTrue(stringContents.contains(gameApp.getDeveloper().toString()));
            assertTrue(stringContents.contains("€" + gameApp.getAppCost()));
            assertTrue(stringContents.contains("Rating: " + gameApp.calculateRating()));
        }

        @Test
        void toStringReturnsCorrectString() {
            GameApp gameApp = setupGameAppWithRating(3, 4);
            String stringContents = gameApp.toString();

            assertTrue(stringContents.contains(gameApp.getAppName()));
            assertTrue(stringContents.contains("(Version " + gameApp.getAppVersion()));
            assertTrue(stringContents.contains(gameApp.getDeveloper().toString()));
            assertTrue(stringContents.contains(gameApp.getAppSize() + "MB"));
            assertTrue(stringContents.contains("Cost: " + gameApp.getCurrencySymbol() + gameApp.getAppCost()));
            assertTrue(stringContents.contains("Ratings (" + gameApp.calculateRating()));
            assertTrue(stringContents.contains("Genres: "));

            //contains list of ratings too
            assertTrue(stringContents.contains("John Doe"));
            assertTrue(stringContents.contains("Very Good"));
            assertTrue(stringContents.contains("Jane Doe"));
            assertTrue(stringContents.contains("Excellent"));

            // contains genres too
            assertTrue(stringContents.contains("Adventure"));
            assertTrue(stringContents.contains("Simulation"));
            assertTrue(stringContents.contains("RolePlaying"));
        }

    }

    @Nested
    class RecommendGameApp {
        @Test
        void appIsNotRecommendedWhenRatingIsLessThan4() {
            //setting all conditions to true with ratings of 4 and 3 (i.e. 3.5)
            GameApp gameApp = setupGameAppWithRating(4, 3);
            //verifying recommended app returns false (rating not high enough
            assertFalse(gameApp.isRecommendedApp());
        }

        @Test
        void appIsNotRecommendedWhenNoRatingsExist() {
            //setting all conditions to true with no ratings
            GameApp gameApp = new GameApp(developerLego, "WeDo", 1,
                    1.0, 1.00,  true);
            //verifying recommended app returns true
            assertFalse(gameApp.isRecommendedApp());
        }

        @Test
        void appIsNotRecommendedWhenIsSinglePlayer() {
            GameApp gameApp = setupGameAppWithRating(4, 5);
            gameApp.setMultiplayer(false);
            assertFalse(gameApp.isRecommendedApp());
        }

        @Test
        void appIsRecommendedWhenAllOfTheConditionsAreTrue() {
            //setting all conditions to true with ratings of 3 and 4 (i.e. 3.5)
            GameApp gameApp = setupGameAppWithRating(5, 4);

            //verifying recommended app returns true
            assertTrue(gameApp.isRecommendedApp());
        }

    }

    GameApp setupGameAppWithRating(int rating1, int rating2) {
        //setting all conditions to true
        GameApp gameApp = new GameApp(developerLego, "WeDo", 1,
                1.0, 1.00,  true);
        gameApp.addRating(new Rating(rating1, "John Doe", "Very Good"));
        gameApp.addRating(new Rating(rating2, "Jane Doe", "Excellent"));

        // verifying all conditions are true for a recommended educational app
        assertEquals(2, gameApp.getRatings().size());  //two ratings are added
        assertEquals(1.0, gameApp.getAppCost(), 0.01);
        assertEquals(((rating1 + rating2) / 2.0), gameApp.calculateRating(), 0.01);
        assertTrue(gameApp.isMultiplayer());

        gameApp.addGenre(Adventure);
        gameApp.addGenre(Simulation);
        gameApp.addGenre(RolePlaying);

        return gameApp;
    }
}
