/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webshop.controller;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import webshop.model.Customer;
import webshop.model.ICustomer;

/**
 *
 * @author Nizam
 */
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Stateless
@LocalBean
public class CustomerFacade {

    @PersistenceContext(unitName = "APGWebShopPU")
    private EntityManager em;

    public ICustomer register(String userName, String password) {
        Customer customer = new Customer(userName, password);
        em.persist(customer);
        return customer;
    }

    public boolean alreadyRegisterd(String userName) {
        Customer customer = em.find(Customer.class, userName);
        if (customer == null) {
            return false;
        }
        return true;
    }

    public ICustomer find(String userName, String password) {
        try {
            ICustomer found = em.find(Customer.class, userName);
            return found;
        } catch (Exception ex) {
            return null;
        }
    }
    public Customer findByName(String userName) {
        
            Customer found = em.find(Customer.class, userName);
            return found;
       
    }

    public void logout(String userName, String password) {

    }

    public List<Customer> getCustomers() {
        Query query = em.createQuery("SELECT x FROM Customer x");
      List<Customer> ret = query.getResultList();

      if (ret == null)
      {
         return new ArrayList<Customer>();
      }
      else
      {
         return ret;
      }
    }
    
    public void updateCustomer(Customer acc)
   {
      em.merge(acc);
   }

}
