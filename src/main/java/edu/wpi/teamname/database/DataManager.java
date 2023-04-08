package edu.wpi.teamname.database;

import edu.wpi.teamname.navigation.Edge;
import edu.wpi.teamname.navigation.Move;
import edu.wpi.teamname.navigation.Node;
import edu.wpi.teamname.servicerequest.ItemsOrdered;
import edu.wpi.teamname.servicerequest.ServiceRequest;
import edu.wpi.teamname.servicerequest.requestitem.Flower;
import edu.wpi.teamname.servicerequest.requestitem.Meal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class DataManager {
    private static final String DB_URL =
            "jdbc:postgresql://database.cs.wpi.edu:5432/teamddb?currentSchema=\"teamD\"";
    private static final String DB_USER = "teamd";
    private static final String DB_PASSWORD = "teamd40";

    /** Main function that connects to the database, or it will display an error if it does not. */
    public Connection DbConnection() {
        Connection connection = null;

        System.out.print("--- Connecting To Database... ---");
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println(" Successfully connected to database");
            return connection;
        } catch (SQLException e) {
            System.out.println(" Connection Failed! Check output console");
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    //------------------------DAO Methods------------------------
    public void syncAll(){
        syncMoves(); syncNodes(); syncEdges(); syncItemsOrdered(); syncItemsOrdered(); syncServiceRequests(); syncLogin(); syncFlowers(); syncMeals();}
    public void syncMoves(){}
    public void syncNodes(){}
    public void syncEdges(){}
    public void syncItemsOrdered(){}
    public void syncServiceRequests(){}
    public void syncLogin(){}
    public void syncFlowers(){}
    public void syncMeals(){}
    public void addAll(){
        addMoves(); addNodes(); addEdges(); addItemsOrdered(); addServiceRequests(); addLogin(); addFlowers(); addMeals();}
    public void addMoves(){}
    public void addNodes(){}
    public void addEdges(){}
    public void addItemsOrdered(){}
    public void addServiceRequests(){}
    public void addLogin(){}
    public void addFlowers(){}
    public void addMeals(){}
    public void deleteMoves(){}
    public void deleteNodes(){}
    public void deleteEdges(){}
    public void deleteItemsOrdered(){}
    public void deleteServiceRequests(){}
    public void deleteLogin(){}
    public void deleteFlowers(){}
    public void deleteMeals(){}
    public ArrayList<Move> getMoves(){return null;}
    public ArrayList<Node> getNodes(){return null;}
    public ArrayList<Edge> getEdges(){return null;}
    public ArrayList<ItemsOrdered> getItemsOrdered(){return null;}
    public ArrayList<ServiceRequest> getServiceRequests(){return null;}
    public ArrayList<Login> getLogin(){return null;}
    public ArrayList<Flower> getFlowers(){return null;}
    public ArrayList<Meal> getMeals(){return null;}




}
