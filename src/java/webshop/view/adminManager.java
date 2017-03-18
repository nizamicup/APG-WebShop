/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webshop.view;

import javax.inject.Named;
import javax.enterprise.context.ConversationScoped;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.faces.component.html.HtmlDataTable;
import javax.inject.Inject;
import webshop.controller.AdminFacade;
import webshop.model.Gnome;

/**
 *
 * @author Nizam
 */
@Named(value = "adminManager")
@ConversationScoped
public class adminManager implements Serializable {

    /**
     * Creates a new instance of adminManager
     */
   
    @EJB
   private AdminFacade adminFacade;
   private List<Gnome> gnomes;
   private Gnome selectedGnome;
   private boolean showMessage = false;
   private String selectedName;
   private int selectedIndex;
   private Integer selectedUnits = 0;
   private Float selectedPrice = 0f;
  
   private String message;
   @Inject
   private Conversation conversation;
    
    
    
    
    public adminManager() {
    }

     public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public boolean isShowMessage() {
        return showMessage;
    }
    public String getSelectedName() {
        return selectedName;
    }

    public void setSelectedName(String selectedName) {
        this.selectedName = selectedName;
    }

    public Integer getSelectedUnits() {
        return selectedUnits;
    }

    public void setSelectedUnits(Integer selectedUnits) {
        this.selectedUnits = selectedUnits;
    }

    public Float getSelectedPrice() {
        return selectedPrice;
    }

    public void setSelectedPrice(Float selectedPrice) {
        this.selectedPrice = selectedPrice;
    }
    
    public List<Gnome> getGnomes()
   {
      gnomes = adminFacade.getGnomes();
      return gnomes;
   }

    public Gnome getSelectedGnome() {
        return selectedGnome;
    }

    public void setSelectedGnome(Gnome selectedGnome) {
        this.selectedGnome = selectedGnome;
    }
    
    public void selectGnome(String gnomeName){
    selectedGnome = adminFacade.findGnome(gnomeName);
      this.selectedName = selectedGnome.getName();
      selectedPrice = selectedGnome.getPrice();
      selectedUnits = selectedGnome.getStock();
    }
    
     public void displayMessage(String msg) {
        showMessage = true;
        message = msg;
    }
     
    public void editGnome()
    {
        if (selectedGnome == null)
      {
          displayMessage("You must select a gnome to edit.");
         return;
      }
      
      if (selectedPrice < 0)
      {
         displayMessage("The price cannot be negative.");
         return;
      }
      
      if (selectedUnits < 0)
      {
         displayMessage("The selected units must be a positive integer number.");
         return;
      }
      
      selectedGnome.setPrice(selectedPrice);
      selectedGnome.setStock(selectedUnits);
      adminFacade.updateGnome(selectedGnome);
      
      displayMessage("Gnome modified.");
    }
    
    public void addGnome()
   {
      if (selectedName.isEmpty())
      {
         displayMessage("The gnome must have a name.");
         return;
      }
      
      if (selectedPrice < 0)
      {
         displayMessage("The price cannot be negative.");
         return;
      }

      if (selectedUnits < 0)
      {
         displayMessage("The selected units must be a positive integer number.");
         return;
      }
      
      Gnome g = adminFacade.findGnome(selectedName);
      if (g != null)
      {
         displayMessage("This gnome already exists.");
         return;
      }
      
      g = adminFacade.addGnome(selectedName,selectedPrice,selectedUnits);
      gnomes.add(g);
      
      selectedGnome = g;
      
      displayMessage("Gnome added.");
   }
    
    public void removeGnome()
   {
      if (selectedGnome == null)
      {
         displayMessage("You must select a gnome to remove.");
         return;
      }
      
      adminFacade.removeGnome(selectedGnome);
      gnomes.remove(selectedGnome);
      selectedGnome = null;
      selectedName = "";
      selectedPrice = 0f;
      selectedUnits = 0;
      
      displayMessage("Gnome removed.");
   }
     
}
