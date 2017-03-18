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

/**
 *
 * @author Nizam
 */
@Entity
public class Gnome implements Serializable, IGnome {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String name;
    private int stock;
    private float price;

    public void setName(String name) {
        this.name = name;
    }
    
    public Gnome()
   {
   }


   public Gnome(String name, int stock, float price)
   {
      this.name = name;
      this.stock = stock;
      this.price = price;
   }


    @Override
    public String getName() {
        return this.name;
    }

    public void setPrice(float price) {
        this.price = price;
    }
    
    @Override
    public float getPrice() {
        return this.price;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public int getStock() {
        return this.stock;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (name != null ? name.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Gnome)) {
            return false;
        }
        Gnome other = (Gnome) object;
        if ((this.name == null && other.name != null) || (this.name != null && !this.name.equals(other.name))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "webshop.model.Gnome[ name=" + name + " ]";
    }

}
