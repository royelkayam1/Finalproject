package main.java;// Income.java

import java.util.ArrayList; // נדרש עבור fromCsvString המעודכן
import java.util.List;     // נדרש עבור fromCsvString המעודכן


public class Income {
    private double amount;
    private String date; // פורמט צפוי: YYYY-MM-DD
    private String description;

    // בנאי
    public Income(double amount, String date, String description) {
        this.amount = amount;
        this.date = date;
        this.description = description;
    }

    // Getters
    public double getAmount() {
        return amount;
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

    public void setDate(String date) {
        this.date = date;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * מחזיר ייצוג קריא של ההכנסה עבור המשתמש.
     * @return מחרוזת מעוצבת המתארת את ההכנסה.
     */
    @Override
    public String toString() {
        return "בתאריך " + date + " קיבלת סכום של " + String.format("%.2f", amount) + " עבור " + description + ".";
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
            return "";
        }
        if (value.contains(",") || value.contains("\"") || value.contains("\n") || value.trim().isEmpty()) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        return value;
    }

    /**
     * ממיר את אובייקט ההכנסה למחרוזת בפורמט CSV.
     * פורמט: amount,date,description
     * @return מחרוזת המייצגת את ההכנסה בפורמט CSV.
     */
    public String toCsvString() {
        return String.format("%.2f", amount) + "," +
                escapeForCsv(date) + "," +
                escapeForCsv(description);
    }

    /**
     * בונה אובייקט Income ממחרוזת בפורמט CSV, תוך טיפול בשדות עם תווי Escape.
     * מפריד את המחרוזת לפי פסיקים, תוך התחשבות במירכאות כפולות העוטפות שדות.
     * @param csvLine שורת ה-CSV המייצגת הכנסה.
     * @return אובייקט Income חדש אם הניתוח מצליח, או null אם יש שגיאת פורמט.
     */
    public static Income fromCsvString(String csvLine) {
        // ביטוי רגולרי שמפריד לפי פסיקים, אך לא מפריד פסיקים שנמצאים בתוך מירכאות כפולות.
        String[] partsArray = csvLine.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

        List<String> finalParsedParts = new ArrayList<>();
        for (String part : partsArray) {
            if (part.startsWith("\"") && part.endsWith("\"") && part.length() > 1) {
                finalParsedParts.add(part.substring(1, part.length() - 1).replace("\"\"", "\""));
            } else {
                finalParsedParts.add(part);
            }
        }

        if (csvLine.endsWith(",") && (finalParsedParts.isEmpty() || !finalParsedParts.get(finalParsedParts.size() - 1).isEmpty())) {
            finalParsedParts.add("");
        }

        if (finalParsedParts.size() == 3) { // ודא שיש בדיוק 3 חלקים לאובייקט Income
            try {
                double amount = Double.parseDouble(finalParsedParts.get(0));
                String date = finalParsedParts.get(1);
                String description = finalParsedParts.get(2);
                return new Income(amount, date, description);
            } catch (NumberFormatException e) {
                System.err.println("שגיאה בפורמט המספר בסכום ההכנסה בשורת CSV: '" + csvLine + "' - " + e.getMessage());
                return null;
            }
        }
        System.err.println("שגיאה בפורמט שורת CSV עבור הכנסה (מספר חלקים לא נכון או שגיאת ניתוח): '" + csvLine + "'");
        return null;
    }
}