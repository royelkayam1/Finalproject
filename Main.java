package main.java;

import java.util.List;
import java.util.Scanner;

// ודא שאתה מייבא את כל המחלקות הדרושות: Expense, Income, FinancialManager, FileManager
// אם הן נמצאות בחבילה (package) מסוימת, שנה זאת בהתאם. לדוגמה:
// import com.yourpackage.Expense;
// import com.yourpackage.Income;
// import com.yourpackage.FinancialManager; // זכור ששינינו את השם
// import com.yourpackage.FileManager;
// אם הן באותה חבילה, אין צורך ביבוא מפורש.


public class Main {

    // הגדר נתיבים קבועים לקובצי הנתונים (הקבצים ייווצרו/ייקראו בתיקייה שבה מריצים את התוכנית)
    private static final String EXPENSES_DATA_FILE = "expenses.csv";
    private static final String INCOMES_DATA_FILE = "incomes.csv";

    public static void main(String[] args) {
        // --- זהו השינוי היחיד שנעשה בקובץ Main.java ---
        // במקום כל הקוד הקונסולי שהיה כאן, כעת אנחנו מפעילים את מסך הכניסה הגרפי.
        // כל לוגיקת הקונסולה הקודמת (התפריט, הסורק וכו') לא תופעל יותר מנקודת כניסה זו.
        LoginScreen.main(args);
        // --------------------------------------------------
    }

    // --- פונקציות עזר עבור הוצאות (קיימות, הותאמו ל-financialManager) ---
    // כל הפונקציות האלה נשארות כאן כרגע כחלק מהקוד ההיסטורי שלך,
    // אך הן לא יקראו ישירות על ידי ה-GUI באופן אוטומטי.
    // אם נרצה להשתמש בלוגיקה שלהן ב-GUI, נצטרך להעביר אותן
    // בצורה מסודרת לתוך FinancialTrackerGUI או מחלקות אחרות.

    private static void addExpense(Scanner scanner, FinancialManager financialManager) {
        System.out.println("\n------------------------------------------------------");
        System.out.println("  הוספת הוצאה חדשה  ");
        System.out.println("------------------------------------------------------");
        System.out.print("הכנס סכום (לדוגמה, 50.00): ");
        try {
            double amount = Double.parseDouble(scanner.nextLine());
            System.out.print("הכנס קטגוריה (אוכל, דיור, תחבורה): ");
            String category = scanner.nextLine();
            System.out.print("הכנס תאריך (YYYY-MM-DD, לדוגמה 2024-03-15): ");
            String date = scanner.nextLine();
            System.out.print("הכנס תיאור (לדוגמה, ארוחת ערב במסעדה): ");
            String description = scanner.nextLine();

            Expense newExpense = new Expense(amount, category, date, description);
            financialManager.addExpense(newExpense); // שימוש ב-financialManager
            System.out.println("✅ ההוצאה נוספה בהצלחה!");
        } catch (NumberFormatException e) {
            System.out.println("❌ קלט לא חוקי! אנא הזן מספר עבור הסכום.");
        }
    }

    private static void displayAllExpenses(FinancialManager financialManager) {
        System.out.println("\n------------------------------------------------------");
        System.out.println("  כל ההוצאות  ");
        System.out.println("------------------------------------------------------");
        System.out.println("פעולה זו מציגה את כל ההוצאות שהזנת עד כה.");
        List<Expense> allExpenses = financialManager.getAllExpenses(); // שימוש ב-financialManager
        if (allExpenses.isEmpty()) {
            System.out.println(" אין הוצאות להצגה.");
        } else {
            for (int i = 0; i < allExpenses.size(); i++) {
                Expense expense = allExpenses.get(i);
                System.out.println((i + 1) + ". " + expense.toString());
            }
        }
    }

    private static void deleteExpense(Scanner scanner, FinancialManager financialManager) {
        System.out.print("\nהכנס את מספר ההוצאה שברצונך למחוק: ");
        try {
            int expenseToDelete = Integer.parseInt(scanner.nextLine()) - 1;
            if (financialManager.deleteExpense(expenseToDelete)) { // שימוש ב-financialManager
                System.out.println("✅ ההוצאה נמחקה בהצלחה.");
            } else {
                System.out.println("❌ מספר הוצאה לא חוקי.");
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ קלט לא חוקי. אנא הזן מספר.");
        }
    }

    private static void updateExpense(Scanner scanner, FinancialManager financialManager) {
        System.out.print("\nהכנס את מספר ההוצאה שברצונך לעדכן: ");
        try {
            int expenseToUpdate = Integer.parseInt(scanner.nextLine()) - 1;
            List<Expense> allExpenses = financialManager.getAllExpenses(); // שימוש ב-financialManager

            if (expenseToUpdate >= 0 && expenseToUpdate < allExpenses.size()) {
                System.out.print("הכנס סכום חדש: ");
                double newAmount = Double.parseDouble(scanner.nextLine());
                System.out.print("הכנס קטגוריה חדשה (אוכל, דיור, תחבורה): ");
                String newCategory = scanner.nextLine();
                System.out.print("הכנס תאריך חדש (YYYY-MM-DD): ");
                String newDate = scanner.nextLine();
                System.out.print("הכנס תיאור חדש: ");
                String newDescription = scanner.nextLine();

                Expense updatedExpense = new Expense(newAmount, newCategory, newDate, newDescription);
                if (financialManager.updateExpense(expenseToUpdate, updatedExpense)) { // שימוש ב-financialManager
                    System.out.println("✅ ההוצאה עודכנה בהצלחה.");
                } else {
                    System.out.println("❌ אירעה שגיאה בעדכון ההוצאה.");
                }
            } else {
                System.out.println("❌ מספר הוצאה לא חוקי.");
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ קלט לא חוקי. אנא הזן מספר.");
        }
    }

    private static void filterExpensesByCategory(Scanner scanner, FinancialManager financialManager) {
        System.out.print("\nהכנס קטגוריה לסינון (אוכל, דיור, תחבורה): ");
        String categoryToFilter = scanner.nextLine();
        List<Expense> expensesByCategory = financialManager.getExpensesByCategory(categoryToFilter); // שימוש ב-financialManager
        System.out.println("\n------------------------------------------------------");
        System.out.println("  הוצאות לפי קטגוריה: " + categoryToFilter + "  ");
        System.out.println("------------------------------------------------------");
        System.out.println("פעולה זו מציגה את ההוצאות שלך לפי הקטגוריה שתבחר.");
        if (expensesByCategory.isEmpty()) {
            System.out.println(" אין הוצאות בקטגוריה זו.");
        } else {
            for (int i = 0; i < expensesByCategory.size(); i++) {
                Expense expense = expensesByCategory.get(i);
                System.out.println((i + 1) + ". " + expense.toString());
            }
        }
    }

    private static void filterExpensesByDateRange(Scanner scanner, FinancialManager financialManager) {
        System.out.print("\nהכנס תאריך התחלה (YYYY-MM-DD): ");
        String startDate = scanner.nextLine();
        System.out.print("הכנס תאריך סיום (YYYY-MM-DD): ");
        String endDate = scanner.nextLine();
        List<Expense> expensesByDateRange = financialManager.getExpensesByDateRange(startDate, endDate); // שימוש ב-financialManager
        System.out.println("\n------------------------------------------------------");
        System.out.println("  הוצאות בין " + startDate + " ל-" + endDate + "  ");
        System.out.println("------------------------------------------------------");
        System.out.println("פעולה זו מציגה את ההוצאות שלך בטווח התאריכים שתבחר.");
        if (expensesByDateRange.isEmpty()) {
            System.out.println(" אין הוצאות בטווח תאריכים אלה.");
        } else {
            for (int i = 0; i < expensesByDateRange.size(); i++) {
                Expense expense = expensesByDateRange.get(i);
                System.out.println((i + 1) + ". " + expense.toString());
            }
        }
    }

    // --- פונקציות עזר חדשות עבור הכנסות ---

    private static void addIncome(Scanner scanner, FinancialManager financialManager) {
        System.out.println("\n------------------------------------------------------");
        System.out.println("  הוספת הכנסה חדשה  ");
        System.out.println("------------------------------------------------------");
        System.out.print("הכנס סכום (לדוגמה, 1500.00): ");
        try {
            double amount = Double.parseDouble(scanner.nextLine());
            System.out.print("הכנס תאריך (YYYY-MM-DD, לדוגמה 2024-06-01): ");
            String date = scanner.nextLine();
            System.out.print("הכנס תיאור (לדוגמה, משכורת): ");
            String description = scanner.nextLine();

            Income newIncome = new Income(amount, date, description);
            financialManager.addIncome(newIncome);
            System.out.println("✅ ההכנסה נוספה בהצלחה!");
        } catch (NumberFormatException e) {
            System.out.println("❌ קלט לא חוקי! אנא הזן מספר עבור הסכום.");
        }
    }

    private static void displayAllIncomes(FinancialManager financialManager) {
        System.out.println("\n------------------------------------------------------");
        System.out.println("  כל ההכנסות  ");
        System.out.println("------------------------------------------------------");
        System.out.println("פעולה זו מציגה את כל ההכנסות שהזנת עד כה.");
        List<Income> allIncomes = financialManager.getAllIncomes();
        if (allIncomes.isEmpty()) {
            System.out.println(" אין הכנסות להצגה.");
        } else {
            for (int i = 0; i < allIncomes.size(); i++) {
                Income income = allIncomes.get(i);
                System.out.println((i + 1) + ". " + income.toString());
            }
        }
    }

    private static void deleteIncome(Scanner scanner, FinancialManager financialManager) {
        System.out.print("\nהכנס את מספר ההכנסה שברצונך למחוק: ");
        try {
            int incomeToDelete = Integer.parseInt(scanner.nextLine()) - 1;
            if (financialManager.deleteIncome(incomeToDelete)) {
                System.out.println("✅ ההכנסה נמחקה בהצלחה.");
            } else {
                System.out.println("❌ מספר הכנסה לא חוקי.");
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ קלט לא חוקי. אנא הזן מספר.");
        }
    }

    private static void updateIncome(Scanner scanner, FinancialManager financialManager) {
        System.out.print("\nהכנס את מספר ההכנסה שברצונך לעדכן: ");
        try {
            int incomeToUpdate = Integer.parseInt(scanner.nextLine()) - 1;
            List<Income> allIncomes = financialManager.getAllIncomes();

            if (incomeToUpdate >= 0 && incomeToUpdate < allIncomes.size()) {
                System.out.print("הכנס סכום חדש: ");
                double newAmount = Double.parseDouble(scanner.nextLine());
                System.out.print("הכנס תאריך חדש (YYYY-MM-DD): ");
                String newDate = scanner.nextLine();
                System.out.print("הכנס תיאור חדש: ");
                String newDescription = scanner.nextLine();

                Income updatedIncome = new Income(newAmount, newDate, newDescription);
                if (financialManager.updateIncome(incomeToUpdate, updatedIncome)) {
                    System.out.println("✅ ההכנסה עודכנה בהצלחה.");
                } else {
                    System.out.println("❌ אירעה שגיאה בעדכון ההכנסה.");
                }
            } else {
                System.out.println("❌ מספר הכנסה לא חוקי.");
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ קלט לא חוקי. אנא הזן מספר.");
        }
    }

    // --- פונקציות עזר חדשות עבור דוחות ---

    private static void displayBalance(FinancialManager financialManager) {
        System.out.println("\n------------------------------------------------------");
        System.out.println("  מאזן פיננסי  ");
        System.out.println("------------------------------------------------------");
        double totalIncomes = financialManager.getTotalIncomesAmount();
        double totalExpenses = financialManager.getTotalExpensesAmount();
        double balance = financialManager.getBalance();

        System.out.printf("סה\"כ הכנסות: %.2f ש\"ח%n", totalIncomes);
        System.out.printf("סה\"כ הוצאות: %.2f ש\"ח%n", totalExpenses);
        System.out.printf("מאזן נוכחי: %.2f ש\"ח%n", balance);

        if (balance >= 0) {
            System.out.println("🎉 כל הכבוד! אתם במאזן חיובי.");
        } else {
            System.out.println("⚠️ שימו לב! אתם במאזן שלילי. כדאי לבדוק את ההוצאות.");
        }
    }
}