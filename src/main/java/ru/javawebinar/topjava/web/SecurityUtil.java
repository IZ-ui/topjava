package ru.javawebinar.topjava.web;

import static ru.javawebinar.topjava.util.MealsUtil.DEFAULT_CALORIES_PER_DAY;

public class SecurityUtil {
    private static int userId;

    public static int authUserId() {
        return userId == 0 ? 1 : getUserId();
    }

    public static int authUserCaloriesPerDay() {
        return DEFAULT_CALORIES_PER_DAY;
    }

    public static void setAuthUserId(int userId) {
        SecurityUtil.userId = userId;
    }

    public static int getUserId() {
        return userId;
    }
}