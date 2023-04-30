package edu.wpi.teamname.extras;

import javax.swing.JFrame;

public class Pacman extends JFrame{

    public Pacman() {
        add(new Model());
    }


    public static void main(String[] args) {

        Pacman pac = new Pacman();
        pac.setVisible(true);
        pac.setTitle("Pacbear");
        pac.setSize(400,440);
        pac.setDefaultCloseOperation(EXIT_ON_CLOSE);
        pac.setLocationRelativeTo(null);

    }

}

