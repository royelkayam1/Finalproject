package main.java;// Expense.java

import java.util.ArrayList; // נדרש עבור fromCsvString המעודכן
import java.util.List;     // נדרש עבור fromCsvString המעודכן


public class Expense {
    private double amount;
    private String category;
    private String date; // פורמט צפוי: YYYY-MM-DD
    private String description;

    // בנאי
    public Expense(double amount, String category, String date, String description) {
        this.amount = amount;
        this.category = category;
        this.date = date;
        this.description = description;
    }

    // Getters
    public double getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    // Setters
    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * מחזיר ייצוג קריא של ההוצאה עבור המשתמש.
     * @return מחרוזת מעוצבת המתארת את ההוצאה.
     */
    @Override
    public String toString() {
        return "בתאריך " + date + " הוצאת סכום של " + String.format("%.2f", amount) + " על " + category + " (" + description + ").";
    }

    // --- מתודות מעודכנות לניהול קבצים (עבור CSV) ---

    /**
     * פונקציית עזר לטיפול בתווים מיוחדים ב-CSV.
     * עוטפת את המחרוזת במירכאות כפולות אם היא מכילה פסיק, מירכאה כפולה, תו שורה חדשה, או שהיא ריקה/רווחים בלבד.
     * כל מירכאה כפולה קיימת בתוך המחרוזת מוכפלת ("").
     * @param value המחרוזת לטיפול.
     * @return המחרוזת לאחר הטיפול, מוכנה לפלט CSV.
     */
    private String escapeForCsv(String value) {
        if (value == null) {
            return ""; // מחזיר מחרוזת ריקה אם הערך הוא null
        }
        // בודק אם יש צורך לעטוף במירכאות
        if (value.contains(",") || value.contains("\"") || value.contains("\n") || value.trim().isEmpty()) {
            // מכפיל מירכאות כפולות קיימות, ואז עוטף את כל המחרוזת במירכאות כפולות
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        // אם אין תווים מיוחדים, מחזיר את הערך כפי שהוא
        return value;
    }

    /**
     * ממיר את אובייקט ההוצאה למחרוזת בפורמט CSV.
     * פורמט: amount,category,date,description
     * @return מחרוזת המייצגת את ההוצאה בפורמט CSV.
     */
    public String toCsvString() {
        return String.format("%.2f", amount) + "," +
                escapeForCsv(category) + "," +
                escapeForCsv(date) + "," +
                escapeForCsv(description);
    }

    /**
     * בונה אובייקט Expense ממחרוזת בפורמט CSV, תוך טיפול בשדות עם תווי Escape.
     * מפריד את המחרוזת לפי פסיקים, תוך התחשבות במירכאות כפולות העוטפות שדות.
     * @param csvLine שורת ה-CSV המייצגת הוצאה.
     * @return אובייקט Expense חדש אם הניתוח מצליח, או null אם יש שגיאת פורמט.
     */
    public static Expense fromCsvString(String csvLine) {
        // ביטוי רגולרי שמפריד לפי פסיקים, אך לא מפריד פסיקים שנמצאים בתוך מירכאות כפולות.
        // זהו ביטוי רגולרי סטנדרטי לפיצול CSV בסיסי.
        String[] partsArray = csvLine.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

        List<String> finalParsedParts = new ArrayList<>();
        for (String part : partsArray) {
            // מסיר מירכאות עוטפות ומבטל את הכפלת המירכאות הפנימיות (un-escape)
            if (part.startsWith("\"") && part.endsWith("\"") && part.length() > 1) {
                finalParsedParts.add(part.substring(1, part.length() - 1).replace("\"\"", "\""));
            } else {
                finalParsedParts.add(part);
            }
        }

        // טיפול במקרים של שדות ריקים בסוף השורה (לדוגמה: "val1,val2,")
        // ה-split עם regex עשוי להשמיט אותם
        if (csvLine.endsWith(",") && (finalParsedParts.isEmpty() || !finalParsedParts.get(finalParsedParts.size() - 1).isEmpty())) {
            finalParsedParts.add(""); // הוסף שדה ריק עבור הפסיק הסופי
        }


        if (finalParsedParts.size() == 4) { // ודא שיש בדיוק 4 חלקים לאובייקט Expense
            try {
                double amount = Double.parseDouble(finalParsedParts.get(0));
                String category = finalParsedParts.get(1);
                String date = finalParsedParts.get(2);
                String description = finalParsedParts.get(3);
                return new Expense(amount, category, date, description);
            } catch (NumberFormatException e) {
                System.err.println("שגיאה בפורמט המספר בסכום ההוצאה בשורת CSV: '" + csvLine + "' - " + e.getMessage());
                return null; // מחזיר null במקרה של שגיאת ניתוח
            }
        }
        System.err.println("שגיאה בפורמט שורת CSV עבור הוצאה (מספר חלקים לא נכון או שגיאת ניתוח): '" + csvLine + "'");
        return null; // מחזיר null במקרה של שגיאת פורמט
    }
}