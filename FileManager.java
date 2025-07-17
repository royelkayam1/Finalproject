package main;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

// ודא שאתה מייבא את מחלקות Expense ו-Income!
// אם הן נמצאות בחבילה (package) מסוימת, שנה זאת בהתאם:
// לדוגמה:
// import com.yourpackage.Expense;
// import com.yourpackage.Income;
// אם הן באותה חבילה, אין צורך ביבוא מפורש.


public class FileManager {

    // --- מתודות לשמירה וטעינה של הוצאות ---

    /**
     * שומר רשימה של אובייקטי Expense לקובץ CSV.
     * כל אובייקט Expense מומר לשורת CSV ונשמר כשורה נפרדת בקובץ.
     *
     * @param expenses רשימת ההוצאות לשמירה.
     * @param filePath הנתיב המלא לקובץ היעד (לדוגמה: "expenses.csv").
     * @return true אם השמירה הצליחה, false אם אירעה שגיאת קלט/פלט.
     */
    public static boolean saveExpenses(List<Expense> expenses, String filePath) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            for (Expense expense : expenses) {
                writer.println(expense.toCsvString()); // קורא למתודה toCsvString מהאובייקט Expense
            }
            System.out.println("✅ ההוצאות נשמרו בהצלחה לקובץ: " + filePath);
            return true;
        } catch (IOException e) {
            System.err.println("❌ שגיאה בשמירת הוצאות לקובץ " + filePath + ": " + e.getMessage());
            return false;
        }
    }

    /**
     * טוען רשימה של אובייקטי Expense מקובץ CSV.
     * כל שורה בקובץ מומרת לאובייקט Expense.
     *
     * @param filePath הנתיב המלא לקובץ המקור.
     * @return רשימה של אובייקטי Expense שנטענו. אם הקובץ לא קיים, ריק, או אירעה שגיאה, מוחזרת רשימה ריקה.
     */
    public static List<Expense> loadExpenses(String filePath) {
        List<Expense> loadedExpenses = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) { // התעלם משורות ריקות לחלוטין
                    continue;
                }
                Expense expense = Expense.fromCsvString(line); // קורא למתודה הסטטית fromCsvString
                if (expense != null) { // אם האובייקט נוצר בהצלחה (fromCsvString לא החזיר null)
                    loadedExpenses.add(expense);
                }
            }
            System.out.println("✅ ההוצאות נטענו בהצלחה מקובץ: " + filePath);
        } catch (IOException e) {
            System.err.println("❌ שגיאה בטעינת הוצאות מקובץ " + filePath + ": " + e.getMessage());
            // במקרה של שגיאה (לדוגמה, הקובץ לא קיים), נחזיר את הרשימה הריקה שנוצרה, במקום רשימה עם נתונים חלקיים או null.
        }
        return loadedExpenses;
    }

    // --- מתודות חדשות לשמירה וטעינה של הכנסות ---

    /**
     * שומר רשימה של אובייקטי Income לקובץ CSV.
     * כל אובייקט Income מומר לשורת CSV ונשמר כשורה נפרדת בקובץ.
     *
     * @param incomes רשימת ההכנסות לשמירה.
     * @param filePath הנתיב המלא לקובץ היעד (לדוגמה: "incomes.csv").
     * @return true אם השמירה הצליחה, false אם אירעה שגיאת קלט/פלט.
     */
    public static boolean saveIncomes(List<Income> incomes, String filePath) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            for (Income income : incomes) {
                writer.println(income.toCsvString()); // קורא למתודה toCsvString מהאובייקט Income
            }
            System.out.println("✅ ההכנסות נשמרו בהצלחה לקובץ: " + filePath);
            return true;
        } catch (IOException e) {
            System.err.println("❌ שגיאה בשמירת הכנסות לקובץ " + filePath + ": " + e.getMessage());
            return false;
        }
    }

    /**
     * טוען רשימה של אובייקטי Income מקובץ CSV.
     * כל שורה בקובץ מומרת לאובייקט Income.
     *
     * @param filePath הנתיב המלא לקובץ המקור.
     * @return רשימה של אובייקטי Income שנטענו. אם הקובץ לא קיים, ריק, או אירעה שגיאה, מוחזרת רשימה ריקה.
     */
    public static List<Income> loadIncomes(String filePath) {
        List<Income> loadedIncomes = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) { // התעלם משורות ריקות לחלוטין
                    continue;
                }
                Income income = Income.fromCsvString(line); // קורא למתודה הסטטית fromCsvString
                if (income != null) { // אם האובייקט נוצר בהצלחה (fromCsvString לא החזיר null)
                    loadedIncomes.add(income);
                }
            }
            System.out.println("✅ ההכנסות נטענו בהצלחה מקובץ: " + filePath);
        } catch (IOException e) {
            System.err.println("❌ שגיאה בטעינת הכנסות מקובץ " + filePath + ": " + e.getMessage());
        }
        return loadedIncomes;
    }
}