package main;
import javafx.application.Application;


import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FinancialTrackerGUI extends Application {

    private String username;

    public FinancialTrackerGUI() {
        this.username = "Guest";
    }

    public FinancialTrackerGUI(String username) {
        this.username = username;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("ניהול פיננסי - משתמש: " + username);

        Label welcomeLabel = new Label("שלום, " + username + "! ברוך הבא למערכת.");
        welcomeLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Button addExpenseBtn = new Button("הוסף הוצאה");
        Button addIncomeBtn = new Button("הוסף הכנסה");
        Button viewSummaryBtn = new Button("הצג סיכום");
        Button exportBtn = new Button("ייצא לקובץ");

        // פעולות לדוגמה – כרגע רק הודעה
        addExpenseBtn.setOnAction(e -> showAlert("הוספת הוצאה"));
        addIncomeBtn.setOnAction(e -> showAlert("הוספת הכנסה"));
        viewSummaryBtn.setOnAction(e -> showAlert("הצגת סיכום הוצאות והכנסות"));
        exportBtn.setOnAction(e -> showAlert("ייצוא לקובץ CSV"));

        VBox root = new VBox(10, welcomeLabel, addExpenseBtn, addIncomeBtn, viewSummaryBtn, exportBtn);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);

        primaryStage.setScene(new Scene(root, 400, 300));
        primaryStage.show();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("פעולה");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
