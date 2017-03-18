/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webshop.controller;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import webshop.model.Gnome;
import webshop.model.ShopingBasket;

/**
 *
 * @author Nizam
 */
@Stateless
public class AdminFacade {

   @PersistenceContext(unitName = "APGWebShopPU")
    private EntityManager em;

    public List<Gnome> getGnomes() {
        Query query = em.createQuery("SELECT g FROM Gnome g");
      List<Gnome> ret = query.getResultList();

      if (ret == null)
      {
         return new ArrayList<Gnome>();
      }
      else
      {
         return ret;
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

    public void updateGnome(Gnome selectedGnome) {
        em.merge(selectedGnome);
    }

    public Gnome addGnome(String selectedName, Float selectedPrice, Integer selectedUnits) {
       Gnome newGnome = new Gnome(selectedName, selectedUnits ,selectedPrice);
       em.persist(newGnome);
       return newGnome;
    }

    public void removeGnome(Gnome selectedGnome) {
       Query q = em.createQuery("SELECT x FROM ShopingBasket x WHERE x.gnome.name = '" + selectedGnome.getName() + "'");
      List<ShopingBasket> bItems = q.getResultList();
      for (ShopingBasket i : bItems)
      {
         em.remove(i);
      }

      q = em.createQuery("DELETE FROM Gnome x WHERE x.name = '" + selectedGnome.getName() + "'");
      q.executeUpdate();
    }
}
