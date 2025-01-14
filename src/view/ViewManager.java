package view;

import interface_adapter.ViewManagerModel;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.*;

public class ViewManager implements PropertyChangeListener {
  private final CardLayout cardLayout;
  private final JPanel views;
  private final ViewManagerModel viewManagerModel;

  public ViewManager(JPanel views, CardLayout cardLayout, ViewManagerModel viewManagerModel) {
    this.views = views;
    this.cardLayout = cardLayout;
    this.viewManagerModel = viewManagerModel;
    this.viewManagerModel.addPropertyChangeListener(this);
  }

  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    if (evt.getPropertyName().equals("view")) {
      String viewModelName = (String) evt.getNewValue();
      cardLayout.show(views, viewModelName);
    }
  }
}
