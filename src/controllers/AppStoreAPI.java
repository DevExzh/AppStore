package controllers;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import models.*;
import utils.*;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.random;
import static utils.RatingUtility.generateRandomRating;

/**
 * The responsibility for this class is to store and manage a list of Apps. Note that App is the super class in the hierarchy pictured below, so any subclass objects can be added to this list of Apps e.g. an object of GameApp can be added to it.
 * @author Ryker Zhu
 * @author Mairead Meagher
 */
public class AppStoreAPI {

    //TODO refer to the spec and add in the required methods here (make note of which methods are given to you first!)

    //---------------------
    // Method to simulate ratings (using the RatingUtility).
    // This will be called from the Driver (see skeleton code)
    //---------------------
    // TODO UNCOMMENT THIS COMPLETED method as you start working through this class
    //---------------------
    /*
    public void simulateRatings(){
        for (App app :apps) {
            app.addRating(generateRandomRating());
        }
    }*/

    //---------------------
    // Validation methods
    //---------------------
    // TODO UNCOMMENT THIS COMPlETED method as you start working through this class
    //---------------------
    /*
    public boolean isValidIndex(int index) {
        return (index >= 0) && (index < apps.size());
    }*/

    //---------------------
    // Persistence methods
    //---------------------
    // TODO UNCOMMENT THIS COMPLETED CODE block as you start working through this class
    //---------------------
    /*
    @SuppressWarnings("unchecked")
    public void load() throws Exception {
        //list of classes that you wish to include in the serialisation, separated by a comma
        Class<?>[] classes = new Class[]{App.class, EducationApp.class, GameApp.class, ProductivityApp.class, Rating.class};

        //setting up the xstream object with default security and the above classes
        XStream xstream = new XStream(new DomDriver());
        XStream.setupDefaultSecurity(xstream);
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
    */
}
