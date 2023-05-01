package edu.wpi.teamname.extras;

import javax.swing.JFrame;

public class Pacman extends JFrame {

  public Pacman() {
    Model m = new Model();
    m.setFrame(this);
    add(m);
  }

  // All Pacman code used from GitHub
  // https://github.com/Gaspared/Pacman
  public static void pacBear() {

    Pacman pac = new Pacman();
    pac.setVisible(true);
    pac.setTitle("PAC-DROPBEAR!");
    pac.setSize(400, 440);
    pac.setDefaultCloseOperation(EXIT_ON_CLOSE);
    pac.setLocationRelativeTo(null);
  }
}
