package main.java;

import java.util.ArrayList;
import java.util.List;

// ודא שאתה מייבא את מחלקות Expense ו-Income!
// אם הן נמצאות בחבילה (package) מסוימת, שנה זאת בהתאם:
// לדוגמה:
// import com.yourpackage.Expense;
// import com.yourpackage.Income;
// אם הן באותה חבילה, אין צורך ביבוא מפורש.

public class FinancialManager {
    private List<Expense> expenses; // רשימה שבה נשמרות ההוצאות
    private List<Income> incomes;   // רשימה שבה נשמרות ההכנסות

    // Constructor
    public FinancialManager() {
        this.expenses = new ArrayList<>();
        this.incomes = new ArrayList<>(); // אתחול רשימת ההכנסות
    }

    // --- מתודות לניהול הוצאות (קיימות + clearExpenses) ---

    /**
     * מוסיף הוצאה חדשה לרשימה.
     * @param expense אובייקט הוצאה להוספה.
     */
    public void addExpense(Expense expense) {
        this.expenses.add(expense);
    }

    /**
     * מחזיר עותק של כל ההוצאות ברשימה.
     * @return רשימה של כל ההוצאות.
     */
    public List<Expense> getAllExpenses() {
        return new ArrayList<>(expenses); // מחזיר עותק
    }

    /**
     * מוחק הוצאה מהרשימה לפי אינדקס.
     * @param index האינדקס של ההוצאה למחיקה.
     * @return true אם המחיקה הצליחה, false אחרת (אינדקס לא חוקי).
     */
    public boolean deleteExpense(int index) {
        if (index >= 0 && index < expenses.size()) {
            expenses.remove(index);
            return true;
        }
        return false;
    }

    /**
     * מעדכן הוצאה קיימת ברשימה.
     * @param index האינדקס של ההוצאה לעדכון.
     * @param updatedExpense אובייקט הוצאה מעודכן.
     * @return true אם העדכון הצליח, false אחרת (אינדקס לא חוקי).
     */
    public boolean updateExpense(int index, Expense updatedExpense) {
        if (index >= 0 && index < expenses.size()) {
            expenses.set(index, updatedExpense);
            return true;
        }
        return false;
    }

    /**
     * מסנן הוצאות לפי קטגוריה נתונה.
     * @param category הקטגוריה לפיה יש לסנן.
     * @return רשימה של הוצאות התואמות לקטגוריה.
     */
    public List<Expense> getExpensesByCategory(String category) {
        List<Expense> filteredExpenses = new ArrayList<>();
        for (Expense expense : expenses) {
            if (expense.getCategory().equalsIgnoreCase(category)) {
                filteredExpenses.add(expense);
            }
        }
        return filteredExpenses;
    }

    /**
     * מסנן הוצאות לפי טווח תאריכים נתון.
     * @param startDate תאריך ההתחלה (פורמט YYYY-MM-DD).
     * @param endDate תאריך הסיום (פורמט YYYY-MM-DD).
     * @return רשימה של הוצאות בטווח התאריכים.
     */
    public List<Expense> getExpensesByDateRange(String startDate, String endDate) {
        List<Expense> filteredExpenses = new ArrayList<>();
        for (Expense expense : expenses) {
            if (expense.getDate().compareTo(startDate) >= 0 && expense.getDate().compareTo(endDate) <= 0) {
                filteredExpenses.add(expense);
            }
        }
        return filteredExpenses;
    }

    /**
     * מנקה את כל ההוצאות הנוכחיות ברשימה.
     */
    public void clearExpenses() {
        this.expenses.clear();
    }

    // --- מתודות חדשות לניהול הכנסות ---

    /**
     * מוסיף הכנסה חדשה לרשימה.
     * @param income אובייקט הכנסה להוספה.
     */
    public void addIncome(Income income) {
        this.incomes.add(income);
    }

    /**
     * מחזיר עותק של כל ההכנסות ברשימה.
     * @return רשימה של כל ההכנסות.
     */
    public List<Income> getAllIncomes() {
        return new ArrayList<>(incomes); // מחזיר עותק
    }

    /**
     * מוחק הכנסה מהרשימה לפי אינדקס.
     * @param index האינדקס של ההכנסה למחיקה.
     * @return true אם המחיקה הצליחה, false אחרת (אינדקס לא חוקי).
     */
    public boolean deleteIncome(int index) {
        if (index >= 0 && index < incomes.size()) {
            incomes.remove(index);
            return true;
        }
        return false;
    }

    /**
     * מעדכן הכנסה קיימת ברשימה.
     * @param index האינדקס של ההכנסה לעדכון.
     * @param updatedIncome אובייקט הכנסה מעודכן.
     * @return true אם העדכון הצליח, false אחרת (אינדקס לא חוקי).
     */
    public boolean updateIncome(int index, Income updatedIncome) {
        if (index >= 0 && index < incomes.size()) {
            incomes.set(index, updatedIncome);
            return true;
        }
        return false;
    }

    /**
     * מנקה את כל ההכנסות הנוכחיות ברשימה.
     */
    public void clearIncomes() {
        this.incomes.clear();
    }

    // --- מתודות לחישוב מאזן ---

    /**
     * מחשב את סך כל ההוצאות.
     * @return סכום כל ההוצאות.
     */
    public double getTotalExpensesAmount() {
        double total = 0;
        for (Expense expense : expenses) {
            total += expense.getAmount();
        }
        return total;
    }

    /**
     * מחשב את סך כל ההכנסות.
     * @return סכום כל ההכנסות.
     */
    public double getTotalIncomesAmount() {
        double total = 0;
        for (Income income : incomes) {
            total += income.getAmount();
        }
        return total;
    }

    /**
     * מחשב את המאזן הפיננסי (סה"כ הכנסות - סה"כ הוצאות).
     * @return המאזן הפיננסי הנוכחי.
     */
    public double getBalance() {
        return getTotalIncomesAmount() - getTotalExpensesAmount();
    }
}