package view;

import interface_adapter.logged_in.LoggedInState;
import interface_adapter.logged_in.LoggedInViewModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.*;

public class LoggedInView extends JPanel implements ActionListener, PropertyChangeListener {

  public final String viewName = "logged in";
  private final LoggedInViewModel loggedInViewModel;

  JLabel username;

  final JButton logOut;

  /** A window with a title and a JButton. */
  public LoggedInView(LoggedInViewModel loggedInViewModel, ChatView chatView) {
    this.loggedInViewModel = loggedInViewModel;
    this.loggedInViewModel.addPropertyChangeListener(this);

    JLabel title = new JLabel("Logged In Screen");
    title.setAlignmentX(Component.CENTER_ALIGNMENT);

    JPanel usernameGroup = new JPanel();
    JLabel usernameInfo = new JLabel("Currently logged in: ");
    username = new JLabel();
    usernameGroup.add(usernameInfo);
    usernameGroup.add(username);

    JPanel buttons = new JPanel();
    logOut = new JButton(LoggedInViewModel.LOGOUT_BUTTON_LABEL);
    buttons.add(logOut);

    logOut.addActionListener(this);

    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

    this.add(title);
    this.add(usernameGroup);
    this.add(chatView);
    this.add(buttons);
  }

  /** React to a button click that results in evt. */
  public void actionPerformed(ActionEvent evt) {
    System.out.println("Click " + evt.getActionCommand());
  }

  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    LoggedInState state = (LoggedInState) evt.getNewValue();
    username.setText(state.getUsername());
  }
}
