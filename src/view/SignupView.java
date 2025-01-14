package view;

import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupState;
import interface_adapter.signup.SignupViewModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.*;

public class SignupView extends JPanel implements ActionListener, PropertyChangeListener {
  public final String viewName = "sign up";

  private final JTextField usernameInputField = new JTextField(15);
  private final JPasswordField passwordInputField = new JPasswordField(15);
  private final JPasswordField repeatPasswordInputField = new JPasswordField(15);
  private final SignupController signupController;

  private final JButton signUp;
  private final JButton cancel;

  public SignupView(SignupController controller, SignupViewModel signupViewModel) {
    // TODO: extract this color (and the Color.red below) to another file
    this.setBackground(Color.YELLOW);

    this.signupController = controller;
    signupViewModel.addPropertyChangeListener(this);

    JPanel body = new JPanel();
    body.setBackground(Color.RED);
    body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
    // It seems the y-axis is ignored here
    body.setMaximumSize(new Dimension(400, 300));

    body.setAlignmentX(CENTER_ALIGNMENT);
    body.setAlignmentY(CENTER_ALIGNMENT);

    JPanel titlePanel = new JPanel();
    JLabel title = new JLabel(SignupViewModel.TITLE_LABEL);
    title.setAlignmentX(Component.CENTER_ALIGNMENT);
    titlePanel.add(title);
    titlePanel.setBackground(body.getBackground());

    LabelTextPanel usernameInfo =
        new LabelTextPanel(new JLabel(SignupViewModel.USERNAME_LABEL), usernameInputField);
    LabelTextPanel passwordInfo =
        new LabelTextPanel(new JLabel(SignupViewModel.PASSWORD_LABEL), passwordInputField);
    LabelTextPanel repeatPasswordInfo =
        new LabelTextPanel(
            new JLabel(SignupViewModel.REPEAT_PASSWORD_LABEL), repeatPasswordInputField);
    usernameInfo.setBackground(body.getBackground());
    passwordInfo.setBackground(body.getBackground());
    repeatPasswordInfo.setBackground(body.getBackground());

    JPanel buttons = new JPanel();
    buttons.setBackground(body.getBackground());
    signUp = new JButton(SignupViewModel.SIGNUP_BUTTON_LABEL);
    buttons.add(signUp);
    cancel = new JButton(SignupViewModel.CANCEL_BUTTON_LABEL);
    buttons.add(cancel);

    signUp.addActionListener(
        // This creates an anonymous subclass of ActionListener and instantiates it.
        new ActionListener() {
          public void actionPerformed(ActionEvent evt) {
            if (evt.getSource().equals(signUp)) {
              SignupState currentState = signupViewModel.getState();

              signupController.execute(
                  currentState.getUsername(),
                  currentState.getPassword(),
                  currentState.getRepeatPassword());
            }
          }
        });

    cancel.addActionListener(this);

    // This makes a new KeyListener implementing class, instantiates it, and
    // makes it listen to keystrokes in the usernameInputField.
    //
    // Notice how it has access to instance variables in the enclosing class!
    usernameInputField.addKeyListener(
        new KeyListener() {
          @Override
          public void keyTyped(KeyEvent e) {
            SignupState currentState = signupViewModel.getState();
            String text = usernameInputField.getText() + e.getKeyChar();
            currentState.setUsername(text);
            signupViewModel.setState(currentState);
          }

          @Override
          public void keyPressed(KeyEvent e) {}

          @Override
          public void keyReleased(KeyEvent e) {}
        });

    passwordInputField.addKeyListener(
        new KeyListener() {
          @Override
          public void keyTyped(KeyEvent e) {
            SignupState currentState = signupViewModel.getState();
            currentState.setPassword(
                String.valueOf(passwordInputField.getPassword()) + e.getKeyChar());
            signupViewModel.setState(currentState);
          }

          @Override
          public void keyPressed(KeyEvent e) {}

          @Override
          public void keyReleased(KeyEvent e) {}
        });

    repeatPasswordInputField.addKeyListener(
        new KeyListener() {
          @Override
          public void keyTyped(KeyEvent e) {
            SignupState currentState = signupViewModel.getState();
            currentState.setRepeatPassword(
                String.valueOf(repeatPasswordInputField.getPassword()) + e.getKeyChar());
            signupViewModel.setState(currentState); // Hmm, is this necessary?
          }

          @Override
          public void keyPressed(KeyEvent e) {}

          @Override
          public void keyReleased(KeyEvent e) {}
        });

    body.add(titlePanel);
    body.add(usernameInfo);
    body.add(passwordInfo);
    body.add(repeatPasswordInfo);
    body.add(buttons);

    this.add(Box.createGlue());
    this.add(body);
    this.add(Box.createGlue());

    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    this.setPreferredSize(new Dimension(800, 600));
  }

  /** React to a button click that results in evt. */
  public void actionPerformed(ActionEvent evt) {
    JOptionPane.showConfirmDialog(this, "Cancel not implemented yet.");
  }

  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    SignupState state = (SignupState) evt.getNewValue();
    if (state.getUsernameError() != null) {
      JOptionPane.showMessageDialog(this, state.getUsernameError());
    }
  }
}
