/**
 * The Town Class is where it all happens.
 * The Town is designed to manage all the things a Hunter can do in town.
 * This code has been adapted from Ivan Turner's original program -- thank you Mr. Turner!
 */

public class Town {
    // instance variables
    private Hunter hunter;
    private Shop shop;
    private Terrain terrain;
    private String printMessage;
    private boolean toughTown;
    private boolean gameLost;
    private boolean goldFound;
    private String[] str;
    private boolean searched;

    /**
     * The Town Constructor takes in a shop and the surrounding terrain, but leaves the hunter as null until one arrives.
     *
     * @param shop The town's shoppe.
     * @param toughness The surrounding terrain.
     */
    public Town(Shop shop, double toughness) {
        searched = false;
        this.shop = shop;
        this.terrain = getNewTerrain();
        // the hunter gets set using the hunterArrives method, which
        // gets called from a client class
        goldFound = false;
        hunter = null;
        printMessage = "";
        // higher toughness = more likely to be a tough town
        toughTown = (Math.random() < toughness);
        str = new String[4];
        str[0] = "crown";
        str[1] = "trophy";
        str[2] = "dust";
        str[3] = "gem";
    }

    public String treasureSelector() {
        int rand = (int) (Math.random() *4);
        return str[rand];
    }

    public void lookForTreasure() {
        if (!searched) {
            hunter.treasureCollected(treasureSelector());
            searched = true;
        } else {
            System.out.println("You cant hunt anymore!");
        }
    }

    public Terrain getTerrain() {
        return terrain;
    }
    public String getLatestNews() {
        return printMessage;
    }


    /**
     * Assigns an object to the Hunter in town.
     *
     * @param hunter The arriving Hunter.
     */
    public void hunterArrives(Hunter hunter) {
        this.hunter = hunter;
        printMessage = "Welcome to town, " + hunter.getHunterName() + ".";
        if (toughTown) {
            printMessage += "\n" + Colors.CYAN + "It's pretty rough around here, so watch yourself." + Colors.RESET;
        } else {
            printMessage += "\nWe're just a sleepy little town with mild mannered folk.";
        }
    }

    /**
     * Handles the action of the Hunter leaving the town.
     *
     * @return true if the Hunter was able to leave town.
     */
    public boolean leaveTown() {
        boolean canLeaveTown = terrain.canCrossTerrain(hunter);
        if (canLeaveTown) {
            String item = terrain.getNeededItem();
            printMessage = "You used your " + item + " to cross the " + terrain.getTerrainName() + ".";
            if (checkItemBreak()) {
                hunter.removeItemFromKit(item);
                printMessage += "\nUnfortunately, you lost your " + item;
            }
            return true;
        }

        printMessage = "You can't leave town, " + hunter.getHunterName() + ". You don't have a " + terrain.getNeededItem() + ".";
        return false;
    }

    /**
     * Handles calling the enter method on shop whenever the user wants to access the shop.
     *
     * @param choice If the user wants to buy or sell items at the shop.
     */
    public void enterShop(String choice) {
        printMessage = shop.enter(hunter, choice);
    }

    /**
     * Gives the hunter a chance to fight for some gold.<p>
     * The chances of finding a fight and winning the gold are based on the toughness of the town.<p>
     * The tougher the town, the easier it is to find a fight, and the harder it is to win one.
     */
    public void lookForTrouble() {
        double noTroubleChance;
        if (toughTown) {
            noTroubleChance = 0.66;
        } else {
            noTroubleChance = 0.33;
        }
        if (hunter.isSamurai() && hunter.hasSword()) {
            if (Math.random() > noTroubleChance) {
                printMessage = "You couldn't find any trouble";
            } else {
                printMessage = Colors.RED + "You want trouble, stranger!  You got it!\nOof! Umph! Ow!" + Colors.RESET + "\n";
                int goldDiff = (int) (Math.random() * 10) + 1;
                if (Math.random() > noTroubleChance) {
                    printMessage += "Okay, stranger! You proved yer mettle. Here, take my gold.";
                    printMessage += "You have slain your enemy, mighty samurai!!";
                    printMessage += "\nYou won the brawl and receive " + Colors.YELLOW + goldDiff + Colors.RESET + " gold.";
                    hunter.changeGold(goldDiff);
                }
            }
        } else {
            if (Math.random() > noTroubleChance) {
                printMessage = "You couldn't find any trouble";
            } else {
                printMessage = Colors.RED + "You want trouble, stranger!  You got it!\nOof! Umph! Ow!" + Colors.RESET + "\n";
                int goldDiff = (int) (Math.random() * 10) + 1;
                if (Math.random() > noTroubleChance) {
                    System.out.println("Okay, stranger! You proved yer mettle. Here, take my gold.");
                    printMessage += "\nYou won the brawl and receive " + Colors.YELLOW + goldDiff + Colors.RESET + " gold.";
                    hunter.changeGold(goldDiff);
                } else {
                    System.out.println(Colors.RED + "That'll teach you to go lookin' fer trouble in MY town! Now pay up!" + Colors.RESET);
                    printMessage += "\nYou lost the brawl and pay " + Colors.YELLOW + goldDiff + Colors.RESET + " gold.";
                    hunter.changeGold(-goldDiff);
                    if (hunter.getGold() < 0) {
                        gameLost = true;
                    } else {
                        gameLost = false;
                    }
                }
            }
        }
    }
    public String infoString() {
        return "This nice little town is surrounded by " + terrain.getTerrainName() + ".";
    }
    public boolean isGameLost() {
        return gameLost;
    }
    /**
     * Determines the surrounding terrain for a town, and the item needed in order to cross that terrain.
     *
     * @return A Terrain object.
     */
    private Terrain getNewTerrain() {
        double rnd = Math.random()*5+1;
        if (rnd < 1) {
            return new Terrain("Mountains", "Rope");
        } else if (rnd < 2) {
            return new Terrain("Ocean", "Boat");
        } else if (rnd < 3) {
            return new Terrain("Plains", "Horse");
        } else if (rnd < 4) {
            return new Terrain("Desert", "Water");
        } else if(rnd<5) {
            return new Terrain("Jungle", "Machete");
        } else {
            return new Terrain("Marsh", "Boots");
        }
    }

    /**
     * Determines whether a used item has broken.
     *
     * @return true if the item broke.
     */
    public static boolean checkItemBreak() {
        double rand = Math.random();
        return (rand < 0.5);
    }

    public String Dig() {
        if (hunter.hasItemInKit("shovel") && !goldFound) {
            double rand = Math.random();
            int digGold= (int) (Math.random() * 20) + 1;
            if (rand<0.5) {
                goldFound = true;
                //digGold = (int) (Math.random()*20)+1;
                hunter.changeGold(digGold);
                return "You dug up " + digGold + "gold!";
            } else {
                goldFound = true;
                return "You dug but only found dirt!";
            }
        } else if (!hunter.hasItemInKit("shovel")){
            return "You cant dig for gold without a shovel!";
        } else {
            return "You already have gold in this town stop being greedy!";
        }
    }
}