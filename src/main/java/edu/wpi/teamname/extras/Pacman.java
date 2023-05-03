package edu.wpi.teamname.extras;

import java.net.URISyntaxException;
import javax.swing.JFrame;

public class Pacman extends JFrame {

  public Pacman() {
    Model m = new Model();
    m.setFrame(this);
    add(m);
  }

  // All Pacman code used from GitHub
  // https://github.com/Gaspared/Pacman

  /** * Initializes the game and runs it */
  public static void pacBear() throws URISyntaxException {
    Sound.setSong(Song.JETPACKJOYRIDE);
    Pacman pac = new Pacman();
    pac.setVisible(true);
    pac.setTitle("PAC-DROPBEAR!");
    pac.setSize(400, 440);
    pac.setDefaultCloseOperation(EXIT_ON_CLOSE);
    pac.setLocationRelativeTo(null);
  }
}
