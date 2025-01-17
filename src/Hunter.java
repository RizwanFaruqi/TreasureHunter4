import java.util.ArrayList;
import java.util.List;
/**
 * Hunter Class<br /><br />
 * This class represents the treasure hunter character (the player) in the Treasure Hunt game.
 * This code has been adapted from Ivan Turner's original program -- thank you Mr. Turner!
 */

public class
Hunter {
    //instance variables
    private String hunterName;
    private String[] kit;
    private  int gold;
    private String[] treasureFound;
    private String[] checkedList;
    int num;
    List<String> myList;
    private boolean samurai;
    /**
     * The base constructor of a Hunter assigns the name to the hunter and an empty kit.
     *
     * @param hunterName The hunter's name.
     * @param startingGold The gold the hunter starts with.
     */
    public Hunter(Boolean samurai, String hunterName, int startingGold) {
        if (samurai) {
            myList = new ArrayList<>();
            this.hunterName = hunterName;
            kit = new String[8]; // only 6 possible items can be stored in kit
            gold = startingGold;
            num = 0;
            checkedList = new String[3];
            treasureFound = new String[4];
            treasureFound[0] = "crown";
            treasureFound[1] = "dust";
            treasureFound[2] = "trophy";
            treasureFound[3] = "gem";
            checkedList[0] = "";
            checkedList[1] = "";
            checkedList[2] = "";
        } else {
            myList = new ArrayList<>();
            this.hunterName = hunterName;
            kit = new String[7]; // only 6 possible items can be stored in kit
            gold = startingGold;
            num = 0;
            checkedList = new String[3];
            treasureFound = new String[4];
            treasureFound[0] = "crown";
            treasureFound[1] = "dust";
            treasureFound[2] = "trophy";
            treasureFound[3] = "gem";
            checkedList[0] = "";
            checkedList[1] = "";
            checkedList[2] = "";
        }
    }
    //Accessors
    public String getHunterName() {
        return hunterName;
    }

    public boolean isSamurai() {
        return samurai;
    }

    /**
     * Updates the amount of gold the hunter has.
     *
     * @param modifier Amount to modify gold by.
     */
    public void changeGold(int modifier) {
        gold += modifier;
    }


    /**
     * Buys an item from a shop.
     *
     * @param item The item the hunter is buying.
     * @param costOfItem The cost of the item.
     * @return true if the item is successfully bought.
     */
    public boolean buyItem(String item, int costOfItem) {
        if ((costOfItem == 0 && !isSamurai()) || gold < costOfItem || hasItemInKit(item)) {
            return false;
        }
        gold -= costOfItem;
        addItem(item);
        return true;
    }

    public boolean hasSword () {
        for (int i = 0; i < kit.length && kit[i] != null; i++) {
            if (kit[i].equals("sword")) {
                return true;
            }
        }
        return false;
    }

    /**
     * The Hunter is selling an item to a shop for gold.<p>
     * This method checks to make sure that the seller has the item and that the seller is getting more than 0 gold.
     *
     * @param item The item being sold.
     * @param buyBackPrice the amount of gold earned from selling the item
     * @return true if the item was successfully sold.
     */
    public boolean sellItem(String item, int buyBackPrice) {
        if (buyBackPrice <= 0 || !hasItemInKit(item)) {
            return false;
        }
        gold += buyBackPrice;
        removeItemFromKit(item);
        return true;
    }

    /**
     * Removes an item from the kit by setting the index of the item to null.
     *
     * @param item The item to be removed.
     */
    public void removeItemFromKit(String item) {
        int itmIdx = findItemInKit(item);

        // if item is found
        if (itmIdx >= 0) {
            kit[itmIdx] = null;
        }
    }

    /**
     * Checks to make sure that the item is not already in the kit.
     * If not, it assigns the item to an index in the kit with a null value ("empty" position).
     *
     * @param item The item to be added to the kit.
     * @return true if the item is not in the kit and has been added.
     */
    private boolean addItem(String item) {
        if (!hasItemInKit(item)) {
            int idx = emptyPositionInKit();
            kit[idx] = item;
            return true;
        }
        return false;
    }

    /**
     * Checks if the kit Array has the specified item.
     *
     * @param item The search item
     * @return true if the item is found.
     */
    public boolean hasItemInKit(String item) {
        for (String tmpItem : kit) {
            if (item.equals(tmpItem)) {
                // early return
                return true;
            }
        }
        return false;
    }

     /**
     * Returns a printable representation of the inventory, which
     * is a list of the items in kit, with a space between each item.
     *
     * @return The printable String representation of the inventory.
     */
    public String getInventory() {
        String printableKit = "";
        String space = " ";
        for (String item : kit) {
            if (item != null) {
                printableKit += item + space;
            }
        }
        return printableKit;
    }

    public String getTreasure() {
        String printableTreasure = "";
        String space = " ";
        for (String treasure : myList) {
            if (treasure != null) {
                printableTreasure += treasure + space;
            }
        }
        return printableTreasure;
    }


    /**
     * @return A string representation of the hunter.
     */
    public String infoString() {
        String str = hunterName + " has " + Colors.YELLOW +  gold + Colors.RESET + " gold";
        if (!kitIsEmpty()) {
            str += " and " + getInventory();
        }
        if (!treasureIsEmpty()) {
            str+="\nTreasures found: " + getTreasure();
        } else {
            str+="\nTreasures found: none";
        }
        return str;
    }
    /**
     * Searches kit Array for the index of the specified value.
     *
     * @param item String to look for.
     * @return The index of the item, or -1 if not found.
     */
    private int findItemInKit(String item) {
        for (int i = 0; i < kit.length; i++) {
            String tmpItem = kit[i];

            if (item.equals(tmpItem)) {
                return i;
            }
        }
        return -1;
    }
    /**
     * Check if the kit is empty - meaning all elements are null.
     *
     * @return true if kit is completely empty.
     */
    private boolean kitIsEmpty() {
        for (String string : kit) {
            if (string != null) {
                return false;
            }
        }
        return true;
    }

    private boolean treasureIsEmpty() {
        for (String treasure : myList) {
            if (treasure != null) {
                return false;
            }
        }
        return true;
    }

    /**
     * Finds the first index where there is a null value.
     *
     * @return index of empty index, or -1 if not found.
     */
    private int emptyPositionInKit() {
        for (int i = 0; i < kit.length; i++) {
            if (kit[i] == null) {
                return i;
            }
        }
        return -1;
    }
    /**
     * reinitialize the kit to a list full of all items
     */
    public boolean testMode() {
        addItem("Water");
        addItem("Rope");
        addItem("Machete");
        addItem("Horse");
        addItem("Boat");
        addItem("Boots");
        addItem("Shovel");
        return true;
    }
    public int getGold() {
        return gold;
    }
    public void treasureCollected(String item) {
        String elementToAdd = item;
        if (!myList.contains(elementToAdd)) {
            myList.add(elementToAdd);
            System.out.println("You found " + item +"!");
        }

//        //int counter = 0;
//        if (!item.equals("dust")) {
//            for (int i=0; i< checkedList.length; i++) {
//                if (checkedList[i].equals(item)) {
//                    counter++;
//                }
//            }
//            if (counter==0) {
//                checkedList[num] = item;
//                num++;
//            }
//            System.out.println("You found " + item +"!");
//        } else {
//            System.out.println("You found dust!");
    }

    public boolean winGame() {
        if (myList.contains("gem") && myList.contains("crown") && myList.contains("trophy")) {
            return true;
        }
        return false;
    }
}
