package CityData.utils;

import java.util.Random;

public class GenerateId {
    public static Long generateUniqueId() {
        Random random = new Random();
        long uniqueId = random.nextLong(); // Генерация случайного числа типа long

        // Приведение полученного числа к диапазону от 100000000 до 999999999
        uniqueId = Math.abs(uniqueId % 900000000) + 100000000;

        return uniqueId;
    }
}
