package use_case.signup;

public class SignupInputData {

  private final String username;
  private final String password;
  private final String repeatPassword;

  public SignupInputData(String username, String password, String repeatPassword) {
    this.username = username;
    this.password = password;
    this.repeatPassword = repeatPassword;
  }

  String getUsername() {
    return username;
  }

  String getPassword() {
    return password;
  }

  public String getRepeatPassword() {
    return repeatPassword;
  }
}
