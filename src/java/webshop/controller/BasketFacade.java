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
public class BasketFacade {

    @PersistenceContext(unitName = "APGWebShopPU")
    private EntityManager em;

    public List<ShopingBasket> getBasketItemList(String userName) {
       Query result = em.createQuery("SELECT x FROM ShopingBasket x WHERE x.customer.userName = '" + userName+ "'");

        List<ShopingBasket> ret = result.getResultList();

      if (ret == null)
      {
         return new ArrayList<ShopingBasket>();
      }
      else
      {
         return ret;
      }
    }
    public void deleteItems(String userName){
       Query query = em.createQuery("DELETE FROM ShopingBasket x WHERE x.customer.userName = '" + userName + "'");
      query.executeUpdate();
    }
}
