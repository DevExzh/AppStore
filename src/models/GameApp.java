package models;

import utils.Utilities;

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

    private final HashSet<Genre> genres = new HashSet<>();

    public HashSet<Genre> getGenres() {
        return genres;
    }

    public boolean addGenre(Genre genre) {
        return genres.add(genre);
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
    public GameApp(Developer developer, String appName, double appSize, double appVersion, double appCost, boolean isMultiplayer) {
        super(developer, appName, appSize, appVersion, appCost);
        this.isMultiplayer = isMultiplayer;
    }

    @Override
    public boolean isRecommendedApp() {
        return isMultiplayer && Utilities.greaterThanOrEqualTo(calculateRating(), 4.0);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append(". ").append(isMultiplayer ? "Multiplayer, " : "Single-player, ");
        sb.append("Genres: ");
        for (Genre genre : getGenres()) {
            sb.append(genre.toString()).append(", ");
        }
        if(sb.substring(sb.length() - 2, sb.length()).equals(", ")) {
            sb.delete(sb.length() - 2, sb.length());
        }
        return sb.toString();
    }
}
