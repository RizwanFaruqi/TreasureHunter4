import java.awt.*;
import java.util.Scanner;

/**
 * The Shop class controls the cost of the items in the Treasure Hunt game. <p>
 * The Shop class also acts as a go between for the Hunter's buyItem() method. <p>
 * This code has been adapted from Ivan Turner's original program -- thank you Mr. Turner!
 */

public class Shop {
    // constants
    private static  int WATER_COST = 2;
    private static  int ROPE_COST = 4;
    private static  int MACHETE_COST = 6;
    private static  int HORSE_COST = 12;
    private static  int BOAT_COST = 20;
    private static  int SHOVEL_COST = 8;
    private static int Sword_cost;
    // static variables
    private static final Scanner SCANNER = new Scanner(System.in);

    // instance variables
    private double markdown;
    private Hunter customer;
    private boolean samurai;
    private boolean Sword = false;

    /**
     * The Shop constructor takes in a markdown value and leaves customer null until one enters the shop.
     *
     * @param markdown Percentage of markdown for selling items in decimal format.
     */
    public Shop(double markdown) {
        this.markdown = markdown;
        customer = null; // customer is set in the enter method
    }

    public Shop(boolean Samurai, double markdown) {
        samurai = false;
        Samurai = samurai;
        if (samurai = true) {
            this.markdown = markdown;
            Sword_cost = 0;
        }
    }    /**
         * Method for entering the shop.
         *
         * @param hunter the Hunter entering the shop
         * @param buyOrSell String that determines if hunter is "B"uying or "S"elling
         * @return a String to be used for printing in the latest news
         */
    public String enter(Hunter hunter, String buyOrSell) {
        customer = hunter;
        if (buyOrSell.equals("b")) {
            System.out.println("Welcome to the shop! We have the finest wares in town.");
            System.out.println("Currently we have the following items:");
            System.out.println(inventory());
            System.out.print("What're you lookin' to buy? ");
            String item = SCANNER.nextLine().toLowerCase();
            int cost = checkMarketPrice(item, true);
            if (cost == 0) {
                System.out.println("We ain't got none of those.");
            } else {
                System.out.print("It'll cost you " + cost + " gold. Buy it (y/n)? ");
                String option = SCANNER.nextLine().toLowerCase();
                if (option.equals("y")) {
                    buyItem(item);
                }
            }
        } else  {
            System.out.println("What're you lookin' to sell? ");
            System.out.print("You currently have the following items: " + Colors.PURPLE + customer.getInventory() + Colors.RESET);
            String item = SCANNER.nextLine().toLowerCase();
            int cost = checkMarketPrice(item, false);
            if (cost == 0) {
                System.out.println("We don't want none of those.");
            } else  {
                System.out.print("It'll get you " + cost + " gold. Sell it (y/n)? ");
                String option = SCANNER.nextLine().toLowerCase();
                if (option.equals("y")) {
                    sellItem(item);
                }
            }
        }
        return "You left the shop";
    }

    /**
     * A method that returns a string showing the items available in the shop
     * (all shops sell the same items).
     *
     * @return the string representing the shop's items available for purchase and their prices.
     */
    public String inventory() {
        String str = "Water: " + WATER_COST + " gold\n";
        str += "Rope: " + ROPE_COST + " gold\n";
        str += "Machete: " + MACHETE_COST + " gold\n";
        str += "Horse: " + HORSE_COST + " gold\n";
        str += "Boat: " + BOAT_COST + " gold\n";
        str += "Shovel: " + SHOVEL_COST + " gold\n";
        return Colors.PURPLE + str + Colors.RESET;
    }

    /**
     * A method that lets the customer (a Hunter) buy an item.
     *
     * @param item The item being bought.
     */
    public void buyItem(String item) {
        if (samurai) {
            WATER_COST = 0;
            ROPE_COST = 0;
            MACHETE_COST = 0;
            HORSE_COST = 0;
            BOAT_COST = 0;
            int costOfItem = checkMarketPrice(item, true);
            if (customer.buyItem(item, costOfItem)) {
                System.out.println("Ye' got yerself a " + item + ". Come again soon.");
            } else {
                System.out.println("Hmm, either you don't have enough gold or you've already got one of those!");
            }
        } else {
            WATER_COST = 2;
            ROPE_COST = 4;
            MACHETE_COST = 6;
            HORSE_COST = 12;
            BOAT_COST = 20;
            int costOfItem = checkMarketPrice(item, true);
            if (customer.buyItem(item, costOfItem)) {
                System.out.println("Ye' got yerself a " + item + ". Come again soon.");
            } else {
                System.out.println("Hmm, either you don't have enough gold or you've already got one of those!");
            }
        }
    }

    /**
     * A pathway method that lets the Hunter sell an item.
     *
     * @param item The item being sold.
     */
    public void sellItem(String item) {
        int buyBackPrice = checkMarketPrice(item, false);
        if (customer.sellItem(item, buyBackPrice)) {
            System.out.println("Pleasure doin' business with you.");
        } else {
            System.out.println("Stop stringin' me along!");
        }
    }

    /**
     * Determines and returns the cost of buying or selling an item.
     *
     * @param item The item in question.
     * @param isBuying Whether the item is being bought or sold.
     * @return The cost of buying or selling the item based on the isBuying parameter.
     */
    public int checkMarketPrice(String item, boolean isBuying) {
        if (isBuying) {
            return getCostOfItem(item);
        } else {
            return getBuyBackCost(item);
        }
    }

    /**
     * Checks the item entered against the costs listed in the static variables.
     *
     * @param item The item being checked for cost.
     * @return The cost of the item or 0 if the item is not found.
     */
    public int getCostOfItem(String item) {
        if (item.equals("water")) {
            return WATER_COST;
        } else if (item.equals("rope")) {
            return ROPE_COST;
        } else if (item.equals("machete")) {
            return MACHETE_COST;
        } else if (item.equals("horse")) {
            return HORSE_COST;
        } else if (item.equals("boat")) {
            return BOAT_COST;
        } else if (item.equals("shovel")){
            return SHOVEL_COST;
        } else {
            return 0;
        }
    }

    /**
     * Checks the cost of an item and applies the markdown.
     *
     * @param item The item being sold.
     * @return The sell price of the item.
     */
    public int getBuyBackCost(String item) {
        int cost = (int) (getCostOfItem(item) * markdown);
        return cost;
    }
}