package edu.wpi.teamname;

import static org.junit.Assert.assertEquals;

import edu.wpi.teamname.navigation.AlgoStrategy.AStarAlgo;
import edu.wpi.teamname.navigation.AlgoStrategy.BFSAlgo;
import edu.wpi.teamname.navigation.AlgoStrategy.DFSAlgo;
import edu.wpi.teamname.navigation.Graph;
import edu.wpi.teamname.navigation.Node;
import java.util.ArrayList;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class AlgoTests {
  public static void main(String[] args) {
    Result result = JUnitCore.runClasses(AlgoTests.class);

    for (Failure failure : result.getFailures()) {
      System.out.println(failure.toString());
    }

    System.out.println(result.wasSuccessful());
    System.out.println("DONE!");
  }

  // Test1: Test AStar pathfinding
  @Test
  public void test1() throws Exception {

    Graph g = new Graph();
    ArrayList<Node> listForPath = new ArrayList<>();
    AStarAlgo A = new AStarAlgo();
    int startId = g.getNodes().get((120 - 100) / 5).getId();
    int targetId = g.getNodes().get((110 - 100) / 5).getId();

    listForPath = A.getPathBetween(g, startId, targetId);

    String printStr = "";
    for (Node n : listForPath) {
      System.out.print(n.getId() + " ");
      printStr += n.getId() + " ";
    }

    assertEquals(printStr, "120 115 200 1795 100 190 130 110 ");
  }
  // Test2: Test AStar pathfinding
  @Test
  public void test2() throws Exception {

    Graph g = new Graph();
    ArrayList<Node> listForPath = new ArrayList<>();
    AStarAlgo A = new AStarAlgo();
    int startId = g.getNodes().get((1500 - 100) / 5).getId();
    int targetId = g.getNodes().get((1510 - 100) / 5).getId();

    listForPath = A.getPathBetween(g, startId, targetId);

    String printStr = "";
    for (Node n : listForPath) {
      printStr += n.getId() + " ";
    }

    assertEquals(printStr, "1500 1505 1510 ");
  }

  //Test3: tests BFS pathfinding: this will return a path with the smallest number of nodes visited
  //This will not necessarily be the shortest path
  @Test
  public void test3() throws Exception {

    Graph g = new Graph();
    ArrayList<Node> listForPath = new ArrayList<>();
    //AStarAlgo A = new AStarAlgo();
    BFSAlgo B = new BFSAlgo();
    int startId = g.getNodes().get((120 - 100) / 5).getId();
    int targetId = g.getNodes().get((110 - 100) / 5).getId();

    listForPath = B.getPathBetween(g, startId, targetId);

    String printStr = "";
    for (Node n : listForPath) {
      printStr += n.getId() + " ";
    }

    assertEquals(printStr, "120 115 200 1795 100 190 130 110 ");
  }

  //Test4: tests BFS pathfinding: this will return a path with the smallest number of nodes visited
  //This will not necessarily be the shortest path
  @Test
  public void test4() throws Exception {

    Graph g = new Graph();
    ArrayList<Node> listForPath = new ArrayList<>();
    //AStarAlgo A = new AStarAlgo();
    BFSAlgo B = new BFSAlgo();
    int startId = g.getNodes().get((1500 - 100) / 5).getId();
    int targetId = g.getNodes().get((1510 - 100) / 5).getId();

    listForPath = B.getPathBetween(g, startId, targetId);

    String printStr = "";
    for (Node n : listForPath) {
      printStr += n.getId() + " ";
    }

    assertEquals(printStr, "1500 1505 1510 ");
  }

  //Test5: tests DFS pathfinding: this will return a path with the smallest number of nodes visited
  //This will not necessarily be the shortest path
  @Test
  public void test5() throws Exception {

    Graph g = new Graph();
    ArrayList<Node> listForPath = new ArrayList<>();
    //AStarAlgo A = new AStarAlgo();
    //BFSAlgo B = new BFSAlgo();
    DFSAlgo D = new DFSAlgo();
    int startId = g.getNodes().get((120 - 100) / 5).getId();
    int targetId = g.getNodes().get((1010 - 100) / 5).getId();

    listForPath = D.getPathBetween(g, startId, targetId);

    String printStr = "";
    for (Node n : listForPath) {
      printStr += n.getId() + " ";
    }

    assertEquals(printStr, "120 115 200 1795 100 190 130 110 ");
  }

  //Test6: tests DFS pathfinding: this will return a path with the smallest number of nodes visited
  //This will not necessarily be the shortest path
  @Test
  public void test6() throws Exception {

    Graph g = new Graph();
    ArrayList<Node> listForPath = new ArrayList<>();
    //AStarAlgo A = new AStarAlgo();
    //BFSAlgo B = new BFSAlgo();
    DFSAlgo D = new DFSAlgo();
    int startId = g.getNodes().get((1500 - 100) / 5).getId();
    int targetId = g.getNodes().get((1510 - 100) / 5).getId();

    listForPath = D.getPathBetween(g, startId, targetId);

    String printStr = "";
    for (Node n : listForPath) {
      printStr += n.getId() + " ";
    }

    assertEquals(printStr, "1500 1505 1510 ");
  }
}
