/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webshop.view;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import webshop.controller.CustomerFacade;
import webshop.model.Customer;
import webshop.model.ICustomer;

/**
 *
 * @author Nizam
 */
@Named(value = "customerManager")
@ConversationScoped
public class CustomerManager implements Serializable {

    /**
     * Creates a new instance of CustomerManager
     */
    private String userName;
    private String password;
    private ICustomer currentCustomer;
    private String message;

    @EJB
    private CustomerFacade customerFacade;

    public String getMessage() {
        return message;
    }

    @Inject
    private Conversation conversation;

    private void startConversation() {
        if (conversation.isTransient()) {
            conversation.begin();
        }
    }

    private void stopConversation() {
        if (!conversation.isTransient()) {
            conversation.end();
        }
    }

    public CustomerManager() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void createCustomer() {
        if (userName.isEmpty()) {
            displayMessage("You must provide a User Name");
        } else if (password.isEmpty()) {
            displayMessage("You must provide a Passsword");
        } else if (customerFacade.alreadyRegisterd(userName)) {
            displayMessage("User is already registered, choose another one!");
        } else {
            currentCustomer = customerFacade.register(userName, password);
            displayMessage("Now you are registed!");
        }
    }

    public String login() {
        startConversation();
        ICustomer customer = customerFacade.find(userName, password);
        if (customer.getUserName().equals(userName) && customer.getPassword().equals(password)) {
            currentCustomer = customer;
            return "Home";
        }
        return "Home";
    }

    public void displayMessage(String msg) {
        message = msg;
    }
    
    public String logout()
    {
        stopConversation();
        currentCustomer=null;
        userName=null;
        password=null;
        return "customer";
    }
}
