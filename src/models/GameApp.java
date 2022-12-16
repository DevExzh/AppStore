package models;

import java.util.HashSet;

/**
 * Manages the specific information relating to a Game App i.e. multiplayer.
 * @author Ryker Zhu
 * @see App
 */
public class GameApp extends App {

    public enum Genre {
        Action, Adventure, Casual,
        Indie, MassivelyMultiplayer,
        Racing, RPG, Simulation,
        Sports, Strategy, RolePlaying,
        Puzzle, Anime, Survival
    }

    private HashSet<Genre> genres = new HashSet<>();

    public HashSet<Genre> getGenres() {
        return genres;
    }

    public void addGenre(Genre genre) {
        genres.add(genre);
    }

    public void removeGenre(Genre genre) {
        genres.remove(genre);
    }

    public boolean isMultiplayer() {
        return isMultiplayer;
    }

    public void setMultiplayer(boolean multiplayer) {
        isMultiplayer = multiplayer;
    }

    private boolean isMultiplayer;

    /**
     * <strong>Constructor</strong>
     * @see App#App(Developer, String, double, double, double)
     * @param developer  The developer of the App
     * @param appName    The name of the App
     * @param appSize    How many spaces the App will cost
     * @param appVersion The version of the App
     * @param appCost    How much a consumer should pay
     */
    public GameApp(Developer developer, String appName, double appSize, double appVersion, double appCost) {
        super(developer, appName, appSize, appVersion, appCost);
    }

    @Override
    public boolean isRecommendedApp() {
        return false;
    }
}
