/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webshop.view;

import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.component.html.HtmlDataTable;
import javax.inject.Inject;
import webshop.controller.BasketFacade;
import webshop.controller.GnomeFacade;
import webshop.model.IShopingBasket;
import webshop.model.ShopingBasket;

/**
 *
 * @author Nizam
 */
@Named(value = "basketManager")
@ConversationScoped
public class ShopingBasketManager implements Serializable{

  @EJB
   private BasketFacade basketFacade;
   @EJB
    private GnomeFacade gnomeFacade;
   private List<ShopingBasket> items;
   private ShopingBasket selectedItem;

    
   private HtmlDataTable table;
   private int selectedIndex;
   private Integer selectedUnits = 0;
   private boolean availableMessages = false;
   private String message;
   @Inject
   private Conversation conversation;

    public ShopingBasketManager() {
    }
    public HtmlDataTable getTable() {
        return table;
    }

    public void setTable(HtmlDataTable table) {
        this.table = table;
    }
    
    public List<ShopingBasket> getBasketItems(String userName)
   {
      items = basketFacade.getBasketItemList(userName);
      return items;
   }

    public float getTotal()
   {
      float total = 0;
      for (IShopingBasket i : items)
      {
         total += i.getTotal();
      }
      return total;
   }
    public ShopingBasket getSelectedItem() {
        return selectedItem;
    }

     public void selectItem()
   {
      selectedIndex = table.getRowIndex();
      selectedItem = (ShopingBasket)table.getRowData();
   }
   public void pay(String userName){
     gnomeFacade.deleteItems(userName);
     items.clear();
   }
    
     
}
