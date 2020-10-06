package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MemoryImpl implements Data {
    private static Map<Integer, Meal> MAP_MEALS = new ConcurrentHashMap<>();
    public static final AtomicInteger TOTAL_ID = new AtomicInteger(0);
    private static final Integer TOTAL_CALORIES = 2000;

    static {
        MAP_MEALS.put(TOTAL_ID.getAndIncrement(), new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        MAP_MEALS.put(TOTAL_ID.getAndIncrement(), new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        MAP_MEALS.put(TOTAL_ID.getAndIncrement(), new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        MAP_MEALS.put(TOTAL_ID.getAndIncrement(), new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        MAP_MEALS.put(TOTAL_ID.getAndIncrement(), new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        MAP_MEALS.put(TOTAL_ID.getAndIncrement(), new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        MAP_MEALS.put(TOTAL_ID.getAndIncrement(), new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
    }


    public List<MealTo> getMealTos() {
        List<Meal> meals = new ArrayList<>(MAP_MEALS.values());
        MAP_MEALS.forEach((k, v) -> v.setId(k));
        return MealsUtil.filteredByStreams(meals, LocalTime.MIN, LocalTime.MAX, TOTAL_CALORIES);
    }

    public void addMeal(Meal meal) {
        int id = TOTAL_ID.getAndIncrement();
        meal.setId(id);
        MAP_MEALS.put(id, meal);
    }

    public Meal getMeal(int mealId) {
        return MAP_MEALS.get(mealId);
    }

    public void updateMeal(int id, LocalDateTime dateTime, String description, int calories) {
        MAP_MEALS.get(id).setDateTime(dateTime);
        MAP_MEALS.get(id).setDescription(description);
        MAP_MEALS.get(id).setCalories(calories);
    }

    public void deleteStudent(int id) {
        MAP_MEALS.remove(id);
    }
}
