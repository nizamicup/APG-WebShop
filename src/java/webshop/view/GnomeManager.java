/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webshop.view;

import javax.inject.Named;
import javax.enterprise.context.ConversationScoped;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.inject.Inject;
import webshop.controller.GnomeFacade;
import webshop.model.Customer;
import webshop.model.Gnome;
import webshop.model.ShopingBasket;

/**
 *
 * @author Nizam
 */
@Named(value = "gnomeManager")
@ConversationScoped
public class GnomeManager implements Serializable {

    private List<Gnome> gnomes;
    private Gnome selectedGnome;
    private boolean showMessage = false;

    public boolean isShowMessage() {
        return showMessage;
    }
    private String message;
    private int selectedUnits = 0;
    @EJB
    private GnomeFacade gnomeFacade;

    @Inject
    private Conversation conversation;

    public GnomeManager() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Gnome> getGnomes() {
        gnomes = gnomeFacade.getGnomes();
        return gnomes;
    }

    public Gnome getSelectedGnome() {
        return selectedGnome;
    }

    public void setSelectedGnome(Gnome selectedGnome) {
        this.selectedGnome = selectedGnome;
    }

    public int getSelectedUnits() {
        return selectedUnits;
    }

    public void setSelectedUnits(int selectedUnits) {
        this.selectedUnits = selectedUnits;
    }

    public void selectGnome() {

    }

    public void displayMessage(String msg) {
        showMessage = true;
        message = msg;
    }

    public void addToCart(String customerName, String gnomeName, int units) {

        //basketFacade.createBasketItem(customerName, gnomeName, units);
        if (units == 0) {
            displayMessage("You must select positive value for product unit");
            return;
        }
        if (gnomeName.isEmpty()) {
            displayMessage("You must select a product to buy");
            return;
        }

        selectedGnome = gnomeFacade.updateStock(gnomeName, units);
        if (selectedGnome == null) {
            displayMessage("You cannot buy more units than available.");
            return;
        }

        ShopingBasket exist = gnomeFacade.findBasketItem(customerName, gnomeName);
        if (exist == null) {
            gnomeFacade.addToCart(customerName, gnomeName, selectedUnits);
            displayMessage("Product "+gnomeName +" is successfully added to your basket");
        }
        else
        {            
            exist.setAmount(exist.getAmount() + selectedUnits);
            gnomeFacade.updateItem(exist);
           
        }
         displayMessage("Product has been added to your basket");
         selectedGnome=null;
         selectedUnits=0;
         

    }
    public void pay(String userName){
     gnomeFacade.deleteItems(userName);
    // items.clear();
   }

}
