/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webshop.view;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
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
    private String page;
    private boolean showMessage = false;
    private List<Customer> customers;
    private Customer selectedUser;

    public boolean isShowMessage() {
        return showMessage;
    }

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

    public ICustomer getCurrentCustomer() {
        return currentCustomer;
    }

    public void setCurrentCustomer(ICustomer currentCustomer) {
        this.currentCustomer = currentCustomer;
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
        } else if (userName.equals("admin") || customerFacade.alreadyRegisterd(userName)) {
            displayMessage("User is already registered, choose another one!");
        } else {
            currentCustomer = customerFacade.register(userName, password);
            displayMessage("Now you are registed!");
        }
    }

    public String login() {
        startConversation();
        Customer customer = (Customer) customerFacade.find(userName, password);
        addAdmin();
        if (customer == null) {
           displayMessage("You are not registered in the system");
        } 
        else if (customer.isBanned()) 
        {
            displayMessage("You have been banned. You cannot enter into the system.");
        }
        else if (customer.getPassword().equals(password) == false)
        {
            displayMessage("Incorrect user name or password");
        }
        else
        {
            if(customer.getUserName().equals("admin")){
              page = "administrator";
            }
            else {
              page= "Home";
            }
        }
        return page;

    }

    public void displayMessage(String msg) {
        showMessage = true;
        message = msg;
    }

    public String logout() {
        stopConversation();
       // current=null;
        userName = null;
        return "customer";
    }

    public List<Customer> getCustomers() {
        customers = customerFacade.getCustomers();

        Customer admin = null;
        for (Customer c : customers) {
            if (c.getUserName().equals("Admin")) {
                admin = c;
                break;
            }
        }

        if (admin != null) {
            customers.remove(admin);
        }

        return customers;
    }
    
    private void addAdmin()
   {
      Customer adminAcc = customerFacade.findByName("admin");
      if (adminAcc == null)
                     currentCustomer = customerFacade.register("admin", "admin");
   }

   public void banOrAllowUser(String userName)
   {
       
      selectedUser = customerFacade.findByName(userName);
      
      boolean value = !selectedUser.isBanned();
      selectedUser.setBanned(value);
      customerFacade.updateCustomer(selectedUser);
   }

}
