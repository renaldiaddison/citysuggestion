package renaldiaddison.citysuggestion.helper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public final class Helper {

    public static String parseNullableString(String input) {
        if (input == null || input.trim().isEmpty()) {
            return null;
        }
        return input;
    }

    public static Integer parseNullableInt(String input) {
        if (input == null || input.trim().isEmpty()) {
            return null;
        }
        return Integer.parseInt(input);
    }

    public static Long parseNullableLong(String input) {
        if (input == null || input.trim().isEmpty()) {
            return null;
        }
        return Long.parseLong(input);
    }

    public static Double parseNullableDouble(String input) {
        if (input == null || input.trim().isEmpty()) {
            return null;
        }
        return Double.parseDouble(input);
    }

    public static LocalDate parseNullableLocalDate(String input) {
        if (input == null || input.trim().isEmpty()) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(input, formatter);
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}
