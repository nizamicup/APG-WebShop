/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webshop.controller;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import webshop.model.Customer;
import webshop.model.ICustomer;

/**
 *
 * @author Nizam
 */
@Stateless
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

    public void logout(String userName, String password) {

    }
}
