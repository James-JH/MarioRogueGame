package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import game.WalletManager;
import game.items.Purchasable;

/**
 * Allow Purchasable items to be purchased
 * @author ChunKau Mok (Peter)
 * @version 1.0
 */
public class PurchaseAction extends Action{

    private Purchasable item;
    private Actor seller;

    /**
     * Public constructor of this action
     * @param item is the item to be sold/bought
     * @param seller is the actor selling the item
     */
    public PurchaseAction(Purchasable item, Actor seller) {
        this.item = item;
        this.seller = seller;
    }

    /**
     * Called upon this action is executed
     * @param actor is the buyer of the item
     * @param map is the gamemap the actor on
     * @return a descriptive string of the result of this action
     */
    @Override
    public String execute(Actor buyer, GameMap map) {
        WalletManager walletManager = WalletManager.getInstance();
        try {
            
            Integer itemPrice = this.item.getPrice();

            if (walletManager.canActorAfford(buyer, itemPrice)) {
                walletManager.subActorCredit(buyer, itemPrice);
                walletManager.addActorCredit(seller, itemPrice);
                seller.removeItemFromInventory((Item) this.item);
                buyer.addItemToInventory((Item) this.item);
                return this.menuDescription(buyer);
            } else {
                return buyer + " can not afford" + item;
            }

        } catch (Exception e) {
            return e.toString();
        }
    }

    /**
     * @param actor is the buyer of this purchase
     * @return a string containing the info of this purcahse action
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " bought " + (Item) item + " from " + this.seller + " for $" + item.getPrice();
    }
    
}