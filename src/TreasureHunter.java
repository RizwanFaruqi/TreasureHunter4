
import java.util.Scanner;

/**
 * This class is responsible for controlling the Treasure Hunter game.<p>
 * It handles the display of the menu and the processing of the player's choices.<p>
 * It handles all the display based on the messages it receives from the Town object. <p>
 *
 * This code has been adapted from Ivan Turner's original program -- thank you Mr. Turner!
 */
public class TreasureHunter {
    // static variables
    private static final Scanner SCANNER = new Scanner(System.in);
    // instance variables
    private Town currentTown;
    private Hunter hunter;
    private boolean hardMode;
    private boolean testMode;
    public boolean easyMode;
    private boolean normalMode;
    private boolean itemCanBreak;
    private boolean samuraiMode;
    /**
     * Constructs the Treasure Hunter game.
     */
    public TreasureHunter() {
        // these will be initialized in the play method
        currentTown = null;
        hunter = null;
        hardMode = false;
        testMode = false;
        easyMode = false;
        normalMode = false;
        itemCanBreak = true;
        samuraiMode = false;
    }

    /**
     * Starts the game; this is the only public method
     */
    public void play() {
        welcomePlayer();
        enterTown();
        showMenu();
    }

    /**
     * Creates a hunter object at the beginning of the game and populates the class member variable with it.
     */

    private boolean itemBreakCheck() {
        if (!itemCanBreak) {
            return false;
        } else {
            return Town.checkItemBreak();
        }
    }

    private void welcomePlayer() {
        System.out.println("Welcome to TREASURE HUNTER!");
        System.out.println("Going hunting for the big treasure, eh?");
        System.out.print("What's your name, Hunter? ");
        String name = SCANNER.nextLine().toLowerCase();

        // set hunter instance variable
        hunter = new Hunter(samuraiMode, name, 20);

        System.out.print("Easy mode (e), Normal Mode (n), Hard Mode (h), Test Mode (t)? ");
        String hard = SCANNER.nextLine().toLowerCase();
        if (hard.equals("e")) {
            easyMode = true;
            hunter.changeGold(20);
            itemCanBreak = false;
        } else if (hard.equals("n")) {
            normalMode = true;
        } else if (hard.equals("h")) {
            hardMode = true;
        } else if (hard.equals("s")){
            samuraiMode = true;
            hunter = new Hunter(samuraiMode, name, 20);
        } else if (hard.equals("t")) {
            testMode = true;
        }

        if (easyMode) {
            hunter.changeGold(20);
        }
        if (testMode) {
            hunter.changeGold(80);
        }
    }

    /**
     * Creates a new town and adds the Hunter to it.
     */
    private void enterTown() {
        double markdown = 0;
        double toughness = 0;
        if (hardMode) {
            // in hard mode, you get less money back when you sell items
            markdown = 0.25;
            // and the town is "tougher"
            toughness = 0.75;
        } else if (testMode) {
            hunter.testMode();
        }
         else if (easyMode) {
            markdown =1;
            toughness = 0.25;
        }
         else if (normalMode) {
            markdown =0.5;
            toughness = 0.50;
        }
        // note that we don't need to access the Shop object
        // outside of this method, so it isn't necessary to store it as an instance
        // variable; we can leave it as a local variable
        Shop shop;
        if (samuraiMode) {
            shop = new Shop(samuraiMode, markdown);
        } else {
            shop = new Shop(markdown);
        }
        // creating the new Town -- which we need to store as an instance
        // variable in this class, since we need to access the Town
        // object in other methods of this class
        currentTown = new Town(shop, toughness);

        // calling the hunterArrives method, which takes the Hunter
        // as a parameter; note this also could have been done in the
        // constructor for Town, but this illustrates another way to associate
        // an object with an object of a different class
        currentTown.hunterArrives(hunter);
    }

    /**
     * Displays the menu and receives the choice from the user.<p>
     * The choice is sent to the processChoice() method for parsing.<p>
     * This method will loop until the user chooses to exit.
     */
    private void showMenu() {
        String choice = "";
        while (!choice.equals("x") && !currentTown.isGameLost()) {
            System.out.println();
            System.out.println(currentTown.getLatestNews());
            System.out.println("***");
            System.out.println(hunter.infoString());
            System.out.println(currentTown.infoString());
            System.out.println("(B)uy something at the shop.");
            System.out.println("(S)ell something at the shop.");
            System.out.println("(E)xplore surrounding terrain.");
            System.out.println("(M)ove on to a different town.");
            System.out.println("(L)ook for trouble!");
            System.out.println("(D)ig for gold!");
            System.out.println("(H)unt for treasure!");
            System.out.println("Give up the hunt and e(X)it.");
            System.out.println();
            System.out.print("What's your next move? ");
            choice = SCANNER.nextLine().toLowerCase();
            processChoice(choice);
            if (hunter.winGame()) {
                break;
            }
        }
        if (hunter.winGame()) {
            System.out.println("You win congratulations!!");
        } else if (currentTown.isGameLost()) {
            System.out.println("Games Over you lose!");
        }
    }

    /**
     * Takes the choice received from the menu and calls the appropriate method to carry out the instructions.
     * @param choice The action to process.
     */
    private void processChoice(String choice) {
        if (choice.equals("b") || choice.equals("s")) {
            currentTown.enterShop(choice);
        } else if (choice.equals("e")) {
            System.out.println(currentTown.getTerrain().infoString());
        } else if (choice.equals("m")) {
            if (currentTown.leaveTown()) {
                // This town is going away so print its news ahead of time.
                System.out.println(currentTown.getLatestNews());
                enterTown();
            }
        } else if (choice.equals("l")) {
            currentTown.lookForTrouble();
        } else if (choice.equals("d")) {
            System.out.println(currentTown.Dig());
        } else if (choice.equals("x")) {
            System.out.println("Fare thee well, " + hunter.getHunterName() + "!");
        } else if (choice.equals("h")) {
            currentTown.lookForTreasure();
        }else {
            System.out.println("Yikes! That's an invalid option! Try again.");
        }
    }
    public boolean isEasyMode() {
        return easyMode;
    }
}