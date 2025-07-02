package main.java;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;

public class FinancialTrackerGUI extends Application {

    private String currentUsername;
    private String selectedCategory = "";

    public FinancialTrackerGUI(String username) {
        this.currentUsername = username;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("מערכת מעקב פיננסי אישי - ברוך הבא, " + currentUsername + "!");

        // יצירת פריסה ראשית VBox
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(25));
        root.setStyle("-fx-background-color: #F8F8FF;"); // צבע רקע יפה (Ghost White)

        // ------------------ הוספות ושינויי עיצוב (כמו במסך הכניסה) ------------------

        // 1. לוגו קטן (אייקון)
        // ** חשוב: ודא שקובץ התמונה (כמו app_logo.png) נמצא בתיקיית src/main/resources/images/ **
        try {
            Image logoImage = new Image(getClass().getResourceAsStream("/images/app_logo.png"));
            ImageView logoView = new ImageView(logoImage);
            logoView.setFitWidth(60); // גודל הלוגו
            logoView.setFitHeight(60);
            root.getChildren().add(logoView);
        } catch (Exception e) {
            System.err.println("שגיאה בטעינת לוגו במסך הראשי: " + e.getMessage());
        }

        // 2. כותרת גדולה יותר וצבעונית
        Label welcomeLabel = new Label("ברוכים הבאים למערכת מעקב פיננסי אישי!");
        welcomeLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #191970;"); // כותרת גדולה יותר וצבע כחול-כהה

        Label instructionsLabel = new Label("הזן פרטי הוצאה/הכנסה ובחר קטגוריה:");
        instructionsLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #36454F;"); // צבע אפור כהה

        root.getChildren().addAll(welcomeLabel, instructionsLabel);

        // ------------------ סוף הוספות ושינויי עיצוב ------------------


        // יצירת GridPane בתוך ה-VBox לניהול שדות הקלט והכפתורים
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(10); // הגדלת הרווח האנכי
        grid.setHgap(10); // הגדלת הרווח האופקי
        grid.setAlignment(Pos.CENTER); // מרכוז ה-GridPane בתוך ה-VBox

        // 1. שדה תיאור
        Label descriptionLabel = new Label("תיאור:");
        descriptionLabel.setStyle("-fx-font-size: 14px;");
        GridPane.setConstraints(descriptionLabel, 0, 0);
        TextField descriptionInput = new TextField();
        descriptionInput.setPromptText("הזן תיאור");
        descriptionInput.setMaxWidth(250); // הגבלת רוחב
        GridPane.setConstraints(descriptionInput, 1, 0);

        // 2. שדה סכום
        Label amountLabel = new Label("סכום:");
        amountLabel.setStyle("-fx-font-size: 14px;");
        GridPane.setConstraints(amountLabel, 0, 1);
        TextField amountInput = new TextField();
        amountInput.setPromptText("הזן סכום");
        amountInput.setMaxWidth(250); // הגבלת רוחב
        GridPane.setConstraints(amountInput, 1, 1);

        // 3. שדה תאריך
        Label dateLabel = new Label("תאריך:");
        dateLabel.setStyle("-fx-font-size: 14px;");
        GridPane.setConstraints(dateLabel, 0, 2);
        DatePicker datePicker = new DatePicker();
        datePicker.setMaxWidth(250); // הגבלת רוחב
        GridPane.setConstraints(datePicker, 1, 2);

        // 4. כפתורי קטגוריות
        Label categoryLabel = new Label("בחר קטגוריה:");
        categoryLabel.setStyle("-fx-font-size: 14px;");
        GridPane.setConstraints(categoryLabel, 0, 3);

        FlowPane categoryButtonsPane = new FlowPane();
        categoryButtonsPane.setHgap(7); // רווח אופקי בין כפתורים
        categoryButtonsPane.setVgap(7); // רווח אנכי בין שורות כפתורים

        String[] categories = {"אוכל", "תחבורה", "בידור", "קניות", "ביגוד", "טיול", "חשבונות", "משכורת", "אחר"};
        for (String category : categories) {
            Button categoryButton = new Button(category);
            categoryButton.setStyle("-fx-background-color: #ADD8E6; -fx-text-fill: black; -fx-font-size: 13px; -fx-padding: 5 10;"); // עיצוב כפתור קטגוריה
            categoryButton.setOnAction(e -> {
                selectedCategory = category;
                System.out.println("נבחרה קטגוריה: " + selectedCategory);
                // כאן תוכל להוסיף קוד שישנה את צבע הכפתור הנבחר
                // (לדוגמה: יאפס את צבעי כל הכפתורים האחרים ויצבע את הכפתור הנוכחי בצבע הדגשה)
            });
            categoryButtonsPane.getChildren().add(categoryButton);
        }
        GridPane.setConstraints(categoryButtonsPane, 1, 3);

        // 5. כפתור הוספת הוצאה
        Button addExpenseButton = new Button("הוסף הוצאה");
        addExpenseButton.setStyle("-fx-background-color: #FF6347; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 16px; -fx-padding: 10 20;"); // צבע אדום-כתום
        GridPane.setConstraints(addExpenseButton, 0, 4);

        // לוגיקה לכפתור הוספת הוצאה (עם הודעת אישור)
        addExpenseButton.setOnAction(e -> {
            String description = descriptionInput.getText();
            String amountText = amountInput.getText();
            LocalDate date = datePicker.getValue();

            if (description.isEmpty() || amountText.isEmpty() || selectedCategory.isEmpty() || date == null) {
                showAlert(AlertType.ERROR, "שגיאה", "שדות חסרים!", "אנא מלא את כל השדות, בחר קטגוריה ותאריך.");
                return;
            }

            try {
                double amount = Double.parseDouble(amountText);
                // כאן בעתיד תשלח את הנתונים לשמירה בקובץ/מסד נתונים
                System.out.println("הוצאה חדשה: " + description + ", סכום: " + amount + ", קטגוריה: " + selectedCategory + ", תאריך: " + date);
                showAlert(AlertType.INFORMATION, "הצלחה!", "הוצאה נוספה בהצלחה", "הוצאה: " + description + "\nסכום: " + amount + " ש\"ח\nקטגוריה: " + selectedCategory + "\nתאריך: " + date);

                // נקה את השדות לאחר ההוספה
                descriptionInput.clear();
                amountInput.clear();
                datePicker.setValue(null);
                selectedCategory = ""; //אפס את הקטגוריה הנבחרת
                // (יש צורך להוסיף קוד לאיפוס צבעי כפתורי הקטגוריה)
            } catch (NumberFormatException ex) {
                showAlert(AlertType.ERROR, "שגיאה", "קלט לא חוקי", "הסכום חייב להיות מספר תקין.");
            }
        });


        // 6. כפתור הוספת הכנסה
        Button addIncomeButton = new Button("הוסף הכנסה");
        addIncomeButton.setStyle("-fx-background-color: #3CB371; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 16px; -fx-padding: 10 20;"); // צבע ירוק
        GridPane.setConstraints(addIncomeButton, 1, 4);

        // לוגיקה לכפתור הוספת הכנסה (עם הודעת אישור)
        addIncomeButton.setOnAction(e -> {
            String description = descriptionInput.getText();
            String amountText = amountInput.getText();
            LocalDate date = datePicker.getValue();

            if (description.isEmpty() || amountText.isEmpty() || selectedCategory.isEmpty() || date == null) {
                showAlert(AlertType.ERROR, "שגיאה", "שדות חסרים!", "אנא מלא את כל השדות, בחר קטגוריה ותאריך.");
                return;
            }

            try {
                double amount = Double.parseDouble(amountText);
                // כאן בעתיד תשלח את הנתונים לשמירה בקובץ/מסד נתונים
                System.out.println("הכנסה חדשה: " + description + ", סכום: " + amount + ", קטגוריה: " + selectedCategory + ", תאריך: " + date);
                showAlert(AlertType.INFORMATION, "הצלחה!", "הכנסה נוספה בהצלחה", "הכנסה: " + description + "\nסכום: " + amount + " ש\"ח\nקטגוריה: " + selectedCategory + "\nתאריך: " + date);

                // נקה את השדות לאחר ההוספה
                descriptionInput.clear();
                amountInput.clear();
                datePicker.setValue(null);
                selectedCategory = "";
                // (יש צורך להוסיף קוד לאיפוס צבעי כפתורי הקטגוריה)
            } catch (NumberFormatException ex) {
                showAlert(AlertType.ERROR, "שגיאה", "קלט לא חוקי", "הסכום חייב להיות מספר תקין.");
            }
        });

        // הוספת ה-GridPane ל-VBox הראשי
        root.getChildren().add(grid);

        Scene scene = new Scene(root, 700, 650); // גודל חלון גדול יותר כדי להתאים לתוכן
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // שיטה חדשה להצגת הודעות קופצות (Alerts)
    private void showAlert(AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait(); // הצג את ההודעה וחכה שהמשתמש יסגור אותה
    }
}