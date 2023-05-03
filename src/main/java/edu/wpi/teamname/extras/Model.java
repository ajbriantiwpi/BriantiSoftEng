package edu.wpi.teamname.extras;

import java.awt.*;
import java.awt.event.*;
import java.net.URISyntaxException;
import javax.swing.*;
import lombok.Getter;
import lombok.Setter;

public class Model extends JPanel implements ActionListener {

  @Getter @Setter private JFrame frame;
  private Dimension d;
  private final Font smallFont = new Font("Arial", Font.BOLD, 14);
  private boolean inGame = false;
  private boolean dying = false;

  private final int BLOCK_SIZE = 24;
  private final int N_BLOCKS = 15;
  private final int SCREEN_SIZE = N_BLOCKS * BLOCK_SIZE;
  private final int MAX_GHOSTS = 4;
  private final int PACMAN_SPEED = 2;

  private int N_GHOSTS = 4;
  private int lives, score;
  private int[] dx, dy;
  private int[] ghost_x, ghost_y, ghost_dx, ghost_dy, ghostSpeed;
  private Image heart, ghost, backG;
  private Image up, down, left, right;
  private int pacman_x, pacman_y, pacmand_x, pacmand_y;
  private int req_dx, req_dy;

  // 16 dot, 0 wall, 1 left, 2 top, 4 right, 8 bottom
  private final short levelData[] = {
    19, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 22,
    17, 16, 16, 16, 16, 24, 16, 16, 16, 16, 16, 16, 16, 16, 20,
    25, 24, 24, 24, 28, 0, 17, 16, 16, 16, 16, 16, 16, 16, 20,
    0, 0, 0, 0, 0, 0, 17, 16, 16, 16, 16, 16, 16, 16, 20,
    19, 18, 18, 18, 18, 18, 16, 16, 16, 16, 24, 24, 24, 24, 20,
    17, 16, 16, 16, 16, 16, 16, 16, 16, 20, 0, 0, 0, 0, 21,
    17, 16, 16, 16, 16, 16, 16, 16, 16, 20, 0, 27, 26, 0, 20,
    17, 16, 16, 16, 24, 16, 16, 16, 16, 20, 0, 0, 0, 0, 21,
    17, 16, 16, 20, 0, 17, 16, 16, 16, 16, 18, 18, 18, 18, 20,
    17, 24, 24, 28, 0, 25, 24, 24, 16, 16, 16, 16, 16, 16, 20,
    21, 0, 0, 0, 0, 0, 0, 0, 17, 16, 24, 24, 24, 16, 20,
    17, 18, 18, 22, 0, 19, 18, 18, 16, 20, 27, 18, 30, 17, 20,
    17, 16, 16, 20, 0, 17, 16, 16, 16, 16, 22, 21, 19, 16, 20,
    17, 16, 16, 20, 0, 17, 16, 16, 16, 16, 16, 16, 16, 16, 20,
    25, 24, 24, 24, 26, 24, 24, 24, 24, 24, 24, 24, 24, 24, 28
  };

  private final int validSpeeds[] = {1, 2, 3, 4, 6, 8};
  private final int maxSpeed = 2;

  private int currentSpeed = 1;
  private short[] screenData;
  private Timer timer;

  public Model() {

    loadImages();
    initVariables();
    addKeyListener(new TAdapter());
    setFocusable(true);
    initGame();
  }

  String filepath = "src/main/resources/edu/wpi/teamname/images/PacIcons";

  /** * Loads in the images into their respective variables */
  private void loadImages() {
    backG = new ImageIcon(filepath + "/backG.png").getImage();
    down = new ImageIcon(filepath + "/down.png").getImage();
    up = new ImageIcon(filepath + "/up.png").getImage();
    left = new ImageIcon(filepath + "/left.png").getImage();
    right = new ImageIcon(filepath + "/right.png").getImage();
    ghost = new ImageIcon(filepath + "/ghost.png").getImage();
    heart = new ImageIcon(filepath + "/heart.png").getImage();
  }

  /** * Initializes variables */
  private void initVariables() {

    screenData = new short[N_BLOCKS * N_BLOCKS];
    d = new Dimension(400, 400);
    ghost_x = new int[MAX_GHOSTS];
    ghost_dx = new int[MAX_GHOSTS];
    ghost_y = new int[MAX_GHOSTS];
    ghost_dy = new int[MAX_GHOSTS];
    ghostSpeed = new int[MAX_GHOSTS];
    dx = new int[4];
    dy = new int[4];

    timer = new Timer(40, this);
    timer.start();
  }

  /**
   * Executes the main logic of the game, which includes moving and drawing the pacman and ghosts,
   * and checking the maze. If the player is currently dying, the death() method is called.
   *
   * @param g2d the Graphics2D object used for drawing the game.
   */
  private void playGame(Graphics2D g2d) throws URISyntaxException {

    if (dying) {

      death();

    } else {

      movePacman();
      drawPacman(g2d);
      moveGhosts(g2d);
      checkMaze();
    }
  }

  /**
   * Draws the introduction screen on the game window, prompting the user to start the game by
   * pressing the SPACE key.
   *
   * @param g2d the Graphics2D object used for drawing the game.
   */
  private void showIntroScreen(Graphics2D g2d) {

    String start = "Press SPACE to start";
    g2d.setColor(Color.white);
    g2d.drawString(start, (SCREEN_SIZE) / 4, 150);
  }

  /**
   * Draws the current score and remaining lives on the game window.
   *
   * @param g the Graphics2D object used for drawing the game.
   */
  private void drawScore(Graphics2D g) {
    g.setFont(smallFont);
    g.setColor(new Color(5, 181, 79));
    String s = "Score: " + score;
    g.drawString(s, SCREEN_SIZE / 2 + 96, SCREEN_SIZE + 16);

    for (int i = 0; i < lives; i++) {
      g.drawImage(heart, i * 28 + 8, SCREEN_SIZE + 1, this);
    }
  }

  /**
   * Checks if the maze has been completed (i.e., all the pellets have been eaten). If so,
   * increments the score and the number of ghosts and increases the player's speed. Also plays a
   * sound effect, changes the background music and hides the game window.
   */
  private void checkMaze() throws URISyntaxException {

    int i = 0;
    boolean finished = true;

    while (i < N_BLOCKS * N_BLOCKS && finished) {

      if ((screenData[i]) != 0) {
        finished = false;
      }

      i++;
    }

    if (finished) {
      // games done
      score += 50;
      if (N_GHOSTS < MAX_GHOSTS) {
        N_GHOSTS++;
      }
      if (currentSpeed < maxSpeed) {
        currentSpeed++;
      }

      frame.setVisible(false);
      Sound.playSFX(SFX.VINE);
      Sound.setSong(Song.OTJANBIRD1);
      // initLevel();
    }
  }

  /**
   * Handles the death of the player, which involves playing a sound effect, decreasing the number
   * of remaining lives, and continuing the level.
   */
  private void death() throws URISyntaxException {
    Sound.playSFX(SFX.VINE);
    lives--;

    if (lives == 0) {
      inGame = false;
    }

    continueLevel();
  }

  /**
   * Updates the positions of the ghosts and redraws them on the screen. If a ghost collides with
   * the player's Pacman character, sets the game state to "dying".
   *
   * @param g2d the Graphics2D object used for drawing
   */
  private void moveGhosts(Graphics2D g2d) {

    int pos;
    int count;

    for (int i = 0; i < N_GHOSTS; i++) {
      if (ghost_x[i] % BLOCK_SIZE == 0 && ghost_y[i] % BLOCK_SIZE == 0) {
        pos = ghost_x[i] / BLOCK_SIZE + N_BLOCKS * (int) (ghost_y[i] / BLOCK_SIZE);

        count = 0;

        if ((screenData[pos] & 1) == 0 && ghost_dx[i] != 1) {
          dx[count] = -1;
          dy[count] = 0;
          count++;
        }

        if ((screenData[pos] & 2) == 0 && ghost_dy[i] != 1) {
          dx[count] = 0;
          dy[count] = -1;
          count++;
        }

        if ((screenData[pos] & 4) == 0 && ghost_dx[i] != -1) {
          dx[count] = 1;
          dy[count] = 0;
          count++;
        }

        if ((screenData[pos] & 8) == 0 && ghost_dy[i] != -1) {
          dx[count] = 0;
          dy[count] = 1;
          count++;
        }

        if (count == 0) {

          if ((screenData[pos] & 15) == 15) {
            ghost_dx[i] = 0;
            ghost_dy[i] = 0;
          } else {
            ghost_dx[i] = -ghost_dx[i];
            ghost_dy[i] = -ghost_dy[i];
          }

        } else {

          count = (int) (Math.random() * count);

          if (count > 3) {
            count = 3;
          }

          ghost_dx[i] = dx[count];
          ghost_dy[i] = dy[count];
        }
      }

      ghost_x[i] = ghost_x[i] + (ghost_dx[i] * ghostSpeed[i]);
      ghost_y[i] = ghost_y[i] + (ghost_dy[i] * ghostSpeed[i]);
      drawGhost(g2d, ghost_x[i] + 1, ghost_y[i] + 1);

      if (pacman_x > (ghost_x[i] - 12)
          && pacman_x < (ghost_x[i] + 12)
          && pacman_y > (ghost_y[i] - 12)
          && pacman_y < (ghost_y[i] + 12)
          && inGame) {

        dying = true;
      }
    }
  }

  /**
   * Draws a ghost image at the specified position on the screen.
   *
   * @param g2d the graphics context in which to draw the ghost image.
   * @param x the x-coordinate of the top-left corner of the ghost image.
   * @param y the y-coordinate of the top-left corner of the ghost image.
   */
  private void drawGhost(Graphics2D g2d, int x, int y) {
    g2d.drawImage(ghost, x, y, this);
  }

  /**
   * Moves the Pacman character on the game board by checking for valid movements and updating the
   * character's position. Also checks for and updates score when Pacman consumes a pellet.
   */
  private void movePacman() {

    int pos;
    short ch;

    if (pacman_x % BLOCK_SIZE == 0 && pacman_y % BLOCK_SIZE == 0) {
      pos = pacman_x / BLOCK_SIZE + N_BLOCKS * (int) (pacman_y / BLOCK_SIZE);
      ch = screenData[pos];

      if ((ch & 16) != 0) {
        screenData[pos] = (short) (ch & 15);
        score++;
      }

      if (req_dx != 0 || req_dy != 0) {
        if (!((req_dx == -1 && req_dy == 0 && (ch & 1) != 0)
            || (req_dx == 1 && req_dy == 0 && (ch & 4) != 0)
            || (req_dx == 0 && req_dy == -1 && (ch & 2) != 0)
            || (req_dx == 0 && req_dy == 1 && (ch & 8) != 0))) {
          pacmand_x = req_dx;
          pacmand_y = req_dy;
        }
      }

      // Check for standstill
      if ((pacmand_x == -1 && pacmand_y == 0 && (ch & 1) != 0)
          || (pacmand_x == 1 && pacmand_y == 0 && (ch & 4) != 0)
          || (pacmand_x == 0 && pacmand_y == -1 && (ch & 2) != 0)
          || (pacmand_x == 0 && pacmand_y == 1 && (ch & 8) != 0)) {
        pacmand_x = 0;
        pacmand_y = 0;
      }
    }
    pacman_x = pacman_x + PACMAN_SPEED * pacmand_x;
    pacman_y = pacman_y + PACMAN_SPEED * pacmand_y;
  }

  /**
   * Draws the Pacman character according to its current direction.
   *
   * @param g2d Graphics2D object used for drawing
   */
  private void drawPacman(Graphics2D g2d) {

    if (req_dx == -1) {
      g2d.drawImage(left, pacman_x + 1, pacman_y + 1, this);
    } else if (req_dx == 1) {
      g2d.drawImage(right, pacman_x + 1, pacman_y + 1, this);
    } else if (req_dy == -1) {
      g2d.drawImage(up, pacman_x + 1, pacman_y + 1, this);
    } else {
      g2d.drawImage(down, pacman_x + 1, pacman_y + 1, this);
    }
  }

  /**
   * This method is responsible for drawing the maze on the screen. It iterates through the
   * levelData array and screenData array to determine which blocks of the maze need to be drawn and
   * what color to use. It then uses Graphics2D methods to draw the walls, pellets, and background
   * of the maze.
   *
   * @param g2d the Graphics2D object used to draw the maze
   */
  private void drawMaze(Graphics2D g2d) {

    short i = 0;
    int x, y;

    g2d.drawImage(backG, 0, 0, this);

    for (y = 0; y < SCREEN_SIZE; y += BLOCK_SIZE) {
      for (x = 0; x < SCREEN_SIZE; x += BLOCK_SIZE) {

        g2d.setColor(new Color(52, 52, 52));
        g2d.setStroke(new BasicStroke(5));

        if ((levelData[i] == 0)) {
          g2d.fillRect(x, y, BLOCK_SIZE, BLOCK_SIZE);
        }

        if ((screenData[i] & 1) != 0) {
          g2d.drawLine(x, y, x, y + BLOCK_SIZE - 1);
        }

        if ((screenData[i] & 2) != 0) {
          g2d.drawLine(x, y, x + BLOCK_SIZE - 1, y);
        }

        if ((screenData[i] & 4) != 0) {
          g2d.drawLine(x + BLOCK_SIZE - 1, y, x + BLOCK_SIZE - 1, y + BLOCK_SIZE - 1);
        }

        if ((screenData[i] & 8) != 0) {
          g2d.drawLine(x, y + BLOCK_SIZE - 1, x + BLOCK_SIZE - 1, y + BLOCK_SIZE - 1);
        }

        if ((screenData[i] & 16) != 0) {
          g2d.setColor(new Color(255, 183, 0));
          g2d.fillOval(x + 10, y + 10, 6, 6);
        }

        i++;
      }
    }
  }

  /**
   * Initializes the game by setting the player's starting lives and score to zero, initializing the
   * level, and setting the player's initial speed to 1.
   */
  private void initGame() {

    lives = 3;
    score = 0;
    initLevel();
    currentSpeed = 1;
  }

  /**
   * Initializes the current level by copying the level data into the screen data array and
   * continuing the level. The function loops through each element in the screen data array and sets
   * it equal to the corresponding element in the level data array. Finally, the function calls the
   * continueLevel() method to set up the level.
   */
  private void initLevel() {

    int i;
    for (i = 0; i < N_BLOCKS * N_BLOCKS; i++) {
      screenData[i] = levelData[i];
    }

    continueLevel();
  }

  /**
   * Initializes a new level by setting the starting positions and speeds for the ghosts and Pacman,
   * and resetting their directions and movement. Called when the player completes a level or loses
   * a life and continues playing. For each ghost, sets its starting position to (4 * BLOCK_SIZE, 4
   * * BLOCK_SIZE), and its starting direction to move horizontally (dx) with a speed randomly
   * selected from validSpeeds array, alternating the direction for each ghost. Sets Pacman's
   * starting position to (7 * BLOCK_SIZE, 11 * BLOCK_SIZE), and resets his direction to stay still.
   * Sets dying to false, indicating that the player has not lost a life and is not currently dying.
   */
  private void continueLevel() {

    int dx = 1;
    int random;

    for (int i = 0; i < N_GHOSTS; i++) {

      ghost_y[i] = 4 * BLOCK_SIZE; // start position
      ghost_x[i] = 4 * BLOCK_SIZE;
      ghost_dy[i] = 0;
      ghost_dx[i] = dx;
      dx = -dx;
      random = (int) (Math.random() * (currentSpeed + 1));

      if (random > currentSpeed) {
        random = currentSpeed;
      }

      ghostSpeed[i] = validSpeeds[random];
    }

    pacman_x = 7 * BLOCK_SIZE; // start position
    pacman_y = 11 * BLOCK_SIZE;
    pacmand_x = 0; // reset direction move
    pacmand_y = 0;
    req_dx = 0; // reset direction controls
    req_dy = 0;
    dying = false;
  }

  /**
   * Overrides the default paintComponent method to draw the game screen. This method first clears
   * the screen with a black color, then draws the maze and the player score. If the game is in
   * progress, it calls the playGame method to update and draw the game objects. If the game is not
   * in progress, it shows the introductory screen. Finally, it synchronizes the graphics state and
   * disposes of the graphics object.
   *
   * @param g the Graphics object used for drawing
   */
  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    Graphics2D g2d = (Graphics2D) g;

    g2d.setColor(Color.black);
    g2d.fillRect(0, 0, d.width, d.height);

    drawMaze(g2d);
    drawScore(g2d);

    if (inGame) {
      try {
        playGame(g2d);
      } catch (URISyntaxException e) {
        throw new RuntimeException(e);
      }
    } else {
      showIntroScreen(g2d);
    }

    Toolkit.getDefaultToolkit().sync();
    g2d.dispose();
  }

  // controls
  class TAdapter extends KeyAdapter {

    /**
     * * If the game is currently in progress, the direction of Pac-Man's movement is changed
     * accordingly. If the game is not currently in progress, the user can start a new game by
     * pressing the spacebar. If the user presses the escape key during the game, the game is paused
     * and the current song playing in the background is changed to a new song.
     *
     * @param e the event to be processed
     */
    @Override
    public void keyPressed(KeyEvent e) {

      int key = e.getKeyCode();

      if (inGame) {
        if (key == KeyEvent.VK_LEFT) {
          req_dx = -1;
          req_dy = 0;
        } else if (key == KeyEvent.VK_RIGHT) {
          req_dx = 1;
          req_dy = 0;
        } else if (key == KeyEvent.VK_UP) {
          req_dx = 0;
          req_dy = -1;
        } else if (key == KeyEvent.VK_DOWN) {
          req_dx = 0;
          req_dy = 1;
        } else if (key == KeyEvent.VK_ESCAPE && timer.isRunning()) {
          inGame = false;
          frame.setVisible(false);
          try {
            Sound.setSong(Song.OTJANBIRD1);
          } catch (URISyntaxException ex) {
            throw new RuntimeException(ex);
          }
        }
      } else {
        if (key == KeyEvent.VK_SPACE) {
          inGame = true;
        }
      }
    }
  }

  /**
   * This method is called when an action is performed. In this case, it triggers a repaint of the
   * game screen.
   *
   * @param e the action event
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    repaint();
  }
}
