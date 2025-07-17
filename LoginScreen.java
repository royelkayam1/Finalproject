package main;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class LoginScreen extends Application {

    private static final String USERS_FILE = "users.ser";
    private Map<String, String> users = new HashMap<>();

    @Override
    public void start(Stage primaryStage) {
        loadUsers();

        primaryStage.setTitle("ברוכים הבאים למערכת ניהול פיננסי");

        Label title = new Label("ברוכים הבאים למערכת ניהול פיננסי");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        TextField usernameField = new TextField();
        usernameField.setPromptText("שם משתמש");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("סיסמה");

        Button loginButton = new Button("התחבר");
        Button registerButton = new Button("הרשם");

        Label messageLabel = new Label();

        loginButton.setOnAction(e -> {
            String user = usernameField.getText();
            String pass = passwordField.getText();

            if (users.containsKey(user) && users.get(user).equals(pass)) {
                messageLabel.setText("התחברות הצליחה!");
                messageLabel.setStyle("-fx-text-fill: green;");
                primaryStage.close();
                new FinancialTrackerGUI(user).start(new Stage());
            } else {
                messageLabel.setText("שם משתמש או סיסמה שגויים.");
                messageLabel.setStyle("-fx-text-fill: red;");
            }
        });

        registerButton.setOnAction(e -> {
            String user = usernameField.getText();
            String pass = passwordField.getText();
            if (user.isEmpty() || pass.isEmpty()) {
                messageLabel.setText("נא למלא שם משתמש וסיסמה.");
                messageLabel.setStyle("-fx-text-fill: red;");
            } else if (users.containsKey(user)) {
                messageLabel.setText("המשתמש כבר קיים.");
                messageLabel.setStyle("-fx-text-fill: red;");
            } else {
                users.put(user, pass);
                saveUsers();
                messageLabel.setText("ההרשמה הצליחה! עכשיו התחבר.");
                messageLabel.setStyle("-fx-text-fill: green;");
            }
        });

        VBox root = new VBox(10, title, usernameField, passwordField, loginButton, registerButton, messageLabel);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);

        primaryStage.setScene(new Scene(root, 350, 300));
        primaryStage.show();
    }

    private void loadUsers() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(USERS_FILE))) {
            users = (Map<String, String>) ois.readObject();
        } catch (Exception e) {
            users = new HashMap<>();
        }
    }

    private void saveUsers() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(USERS_FILE))) {
            oos.writeObject(users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
