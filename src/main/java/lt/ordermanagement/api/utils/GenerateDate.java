package lt.ordermanagement.api.utils;

import java.time.LocalDate;

public class GenerateDate {

    /**
     * Gets the current date as a string.
     *
     * @return A string representation of the current date in 'YYYY-MM-DD' format.
     */
    public static String generateCurrentDate() {
        return LocalDate.now().toString();
    }
}
