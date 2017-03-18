/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webshop.controller;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import webshop.model.Customer;
import webshop.model.Gnome;
import webshop.model.ICustomer;
import webshop.model.ShopingBasket;

/**
 *
 * @author Nizam
 */
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Stateless
@LocalBean
public class GnomeFacade {

    @PersistenceContext(unitName = "APGWebShopPU")
    private EntityManager em;
    @EJB
    private CustomerFacade custFacade;

    public List<Gnome> getGnomes() {

        Query query = em.createQuery("SELECT g FROM Gnome g");
        List<Gnome> gnomes = query.getResultList();
        if (gnomes == null) {
            return new ArrayList<Gnome>();
        } else {
            return gnomes;
        }
    }

    public Gnome findGnome(String gnomeName) {
       try {
            Gnome ret = em.find(Gnome.class, gnomeName);
            return ret;
        } catch (Exception e) {
            return null;
        }
        
       
        
    }

    public void addToCart(String customerName, String gnomeName, int unit) {

        try {
            Customer customer = custFacade.findByName(customerName);
            Gnome gnome = findGnome(gnomeName);
            ShopingBasket basket = new ShopingBasket(customer, gnome, unit);
            em.persist(basket);

        } catch (Exception ex) {

        }

    }
    
     public ShopingBasket findBasketItem(String userName,String gnomeName) {
        
              Query result1 = em.createQuery("SELECT x FROM ShopingBasket x WHERE x.customer.userName = '" + userName+ "'");
  
       Query result = em.createQuery("SELECT x FROM ShopingBasket x WHERE x.customer.userName = '" + userName + "' and x.gnome.name = '" + gnomeName + "'");
      try
      {
         ShopingBasket ret = (ShopingBasket) result.getSingleResult();
         return ret;
      }
      catch (Exception e)
      {
         return null;
      }
         
       /*  ShopingBasket basketItem = em.find(ShopingBasket.class, gnomeName);
        if(basketItem == null)
            return false;
        else
            return true;*/
     }

    public Gnome updateStock(String gnomeName, int units) {
        Gnome g = findGnome(gnomeName);
      int newUnits = g.getStock() - units;

      if (g.getStock() < units)
      {
         return null;
      }
      else
      {
         g.setStock(newUnits);
         em.merge(g);
         return g;
      }
    }

    public void updateItem(ShopingBasket exist) {
        em.merge(exist);
    }
    
    public void deleteItems(String userName){
       Query query = em.createQuery("DELETE FROM ShopingBasket x WHERE x.customer.userName = '" + userName + "'");
      query.executeUpdate();
    }

}
