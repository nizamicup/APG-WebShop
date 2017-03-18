/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webshop.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author Nizam
 */
@Entity
public class ShopingBasket implements Serializable, IShopingBasket {

    private static final long serialVersionUID = 1L;
    
    @GeneratedValue(strategy = GenerationType.AUTO)
    
    @ManyToOne
    private Customer customer;
    @Id
    @ManyToOne
    private Gnome gnome;
    private int amount;
    private float total;

    public ShopingBasket(Customer customer, Gnome gnome, int amount) {
        this.customer = customer;
        this.gnome = gnome;
        this.amount = amount;
        calcTotal();
    }

    public ShopingBasket() {
    }

    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (gnome != null ? gnome.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ShopingBasket)) {
            return false;
        }
        ShopingBasket other = (ShopingBasket) object;
        if ((this.gnome == null && other.gnome != null) || (this.gnome != null && !this.gnome.equals(other.gnome))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "webshop.model.ShopingBasket[ gnome=" + gnome + " ]";
    }

    @Override
    public Customer getCustomerName() {
        return customer;
    }

    @Override
    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
        calcTotal();
    }

    @Override
    public Gnome getGnome() {
        return gnome;
    }

    @Override
    public float getTotal() {
        return this.total;
        
    }
    
    public void calcTotal()
    {
        total=amount*gnome.getPrice();
    }
}
