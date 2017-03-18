/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webshop.model;

/**
 *
 * @author Nizam
 */
public interface IShopingBasket {
    public Customer getCustomerName();
    public int getAmount();
    public Gnome getGnome();
    public float getTotal();
}
