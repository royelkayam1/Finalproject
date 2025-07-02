package main.java;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image; // ייבוא חדש לתמונה
import javafx.scene.image.ImageView; // ייבוא חדש לתמונה
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox; // ייבוא חדש - נשתמש בVBox במקום GridPane כRoot
import javafx.stage.Stage;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class LoginScreen extends Application {

    private static final String USERS_FILE = "users.ser";
    private Map<String, String> users = new HashMap<>();

    // הודעת שגיאה/הצלחה שתוצג למשתמש
    private Label messageLabel;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("ברוך הבא למערכת מעקב פיננסי"); // כותרת החלון הכללית

        // טעינת משתמשים קיימים מקובץ
        loadUsers();

        // VBox ראשי לסידור הרכיבים - עדיף מ-GridPane כאלמנט שורש לעיצוב
        VBox root = new VBox(15); // רווח של 15 פיקסלים בין אלמנטים
        root.setAlignment(Pos.CENTER); // מרכוז כל התוכן
        root.setPadding(new Insets(30)); // ריפוד מסביב לתוכן
        root.setStyle("-fx-background-color: #E0FFFF;"); // צבע רקע יפה (Light Cyan)

        // ------------------ הוספות ושינויי עיצוב ------------------

        // 1. לוגו קטן (אייקון)
        // ** חשוב: אם יש לך קובץ תמונה, שים אותו בתיקיית src/main/resources/images/
        // לדוגמה: src/main/resources/images/app_logo.png
        try {
            Image logoImage = new Image(getClass().getResourceAsStream("/images/app_logo.png"));
            ImageView logoView = new ImageView(logoImage);
            logoView.setFitWidth(80); // גודל הלוגו
            logoView.setFitHeight(80);
            root.getChildren().add(logoView); // הוסף את הלוגו ל-VBox הראשי
        } catch (Exception e) {
            System.err.println("שגיאה בטעינת לוגו (ודא שהקובץ קיים ב-src/main/resources/images/app_logo.png): " + e.getMessage());
            // אם אין לוגו, זה פשוט לא יוסיף אותו.
        }

        // 2. כותרת גדולה יותר וצבעונית
        Label appTitleLabel = new Label("מערכת מעקב פיננסי");
        appTitleLabel.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: #2F4F4F;"); // גודל גופן, הדגשה וצבע (Dark Slate Gray)
        root.getChildren().add(appTitleLabel);

        // הודעת שגיאה/הצלחה שתהיה גלובלית לשיטה
        messageLabel = new Label();
        messageLabel.setStyle("-fx-text-fill: red; -fx-font-size: 14px;"); // הודעות שגיאה באדום
        root.getChildren().add(messageLabel);

        // יצירת GridPane בתוך ה-VBox לסידור שדות הקלט והכפתורים
        GridPane inputGrid = new GridPane();
        inputGrid.setHgap(10);
        inputGrid.setVgap(10);
        inputGrid.setPadding(new Insets(10, 0, 10, 0)); // ריפוד פנימי
        inputGrid.setAlignment(Pos.CENTER); // מרכוז ה-GridPane בתוך ה-VBox

        Label userLabel = new Label("שם משתמש:");
        inputGrid.add(userLabel, 0, 0); // שורה 0, עמודה 0

        TextField userTextField = new TextField();
        userTextField.setPromptText("הקלד שם משתמש");
        userTextField.setMaxWidth(200); // הגבלת רוחב
        inputGrid.add(userTextField, 1, 0); // שורה 0, עמודה 1

        Label passLabel = new Label("סיסמה:");
        inputGrid.add(passLabel, 0, 1); // שורה 1, עמודה 0

        PasswordField passField = new PasswordField();
        passField.setPromptText("הקלד סיסמה");
        passField.setMaxWidth(200); // הגבלת רוחב
        inputGrid.add(passField, 1, 1); // שורה 1, עמודה 1

        Button btnLogin = new Button("התחבר");
        btnLogin.setStyle("-fx-font-size: 16px; -fx-background-color: #4CAF50; -fx-text-fill: white; -fx-padding: 8 15 8 15;"); // כפתור ירוק, ריפוד
        inputGrid.add(btnLogin, 1, 2); // שורה 2, עמודה 1

        Button btnRegister = new Button("הרשם");
        btnRegister.setStyle("-fx-font-size: 16px; -fx-background-color: #008CBA; -fx-text-fill: white; -fx-padding: 8 15 8 15;"); // כפתור כחול
        inputGrid.add(btnRegister, 0, 2); // שורה 2, עמודה 0

        root.getChildren().add(inputGrid); // הוספת ה-GridPane ל-VBox הראשי

        // ------------------ סוף שינויי עיצוב ------------------


        // פעולת כניסה
        btnLogin.setOnAction(e -> {
            String username = userTextField.getText();
            String password = passField.getText();

            if (users.containsKey(username) && users.get(username).equals(password)) {
                messageLabel.setText("התחברת בהצלחה!");
                messageLabel.setStyle("-fx-text-fill: green; -fx-font-size: 14px;"); // הודעת הצלחה בירוק
                System.out.println("User '" + username + "' logged in successfully.");

                // ודא שהקוד להפעלת FinancialTrackerGUI הוא כאן
                primaryStage.close(); // סגור את חלון הכניסה
                try {
                    new FinancialTrackerGUI(username).start(new Stage());
                } catch (Exception ex) {
                    ex.printStackTrace();
                    messageLabel.setText("שגיאה בטעינת המערכת הראשית.");
                    messageLabel.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
                }
            } else {
                messageLabel.setText("שם משתמש או סיסמה שגויים.");
                messageLabel.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
            }
        });

        // פעולת הרשמה
        btnRegister.setOnAction(e -> {
            String username = userTextField.getText();
            String password = passField.getText();

            if (username.isEmpty() || password.isEmpty()) {
                messageLabel.setText("שם משתמש וסיסמה אינם יכולים להיות ריקים.");
                messageLabel.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
            } else if (users.containsKey(username)) {
                messageLabel.setText("שם המשתמש כבר קיים. בחר שם אחר.");
                messageLabel.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
            } else {
                users.put(username, password); // הוספת משתמש חדש למפה
                saveUsers(); // שמירת המשתמשים לקובץ
                createEmptyUserDataFile(username); // יצירת קובץ נתונים ריק למשתמש החדש
                messageLabel.setText("ההרשמה בוצעה בהצלחה! כעת התחבר.");
                messageLabel.setStyle("-fx-text-fill: green; -fx-font-size: 14px;"); // הודעת הצלחה בירוק
            }
        });

        Scene scene = new Scene(root, 400, 550); // גודל חלון שהותאם לעיצוב החדש
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void loadUsers() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(USERS_FILE))) {
            users = (Map<String, String>) ois.readObject();
            System.out.println("Users loaded: " + users.keySet());
        } catch (FileNotFoundException e) {
            System.out.println("Users file not found. Starting with empty user list.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void saveUsers() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(USERS_FILE))) {
            oos.writeObject(users);
            System.out.println("Users saved: " + users.keySet());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createEmptyUserDataFile(String username) {
        String filename = "data_" + username + ".ser";
        File userFile = new File(filename);
        if (!userFile.exists()) {
            try {
                userFile.createNewFile();
                System.out.println("Created empty data file for user: " + filename);
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Error creating empty data file for user: " + username);
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}