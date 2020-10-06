package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MemoryImplMealDao implements MealDao {
    private static Map<Integer, Meal> mapMeals = new ConcurrentHashMap<>();
    public static final AtomicInteger TOTAL_ID = new AtomicInteger(1);

    static {
        mapMeals.put(TOTAL_ID.getAndIncrement(), new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        mapMeals.put(TOTAL_ID.getAndIncrement(), new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        mapMeals.put(TOTAL_ID.getAndIncrement(), new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        mapMeals.put(TOTAL_ID.getAndIncrement(), new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        mapMeals.put(TOTAL_ID.getAndIncrement(), new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        mapMeals.put(TOTAL_ID.getAndIncrement(), new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        mapMeals.put(TOTAL_ID.getAndIncrement(), new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
        mapMeals.forEach((k, v) -> v.setId(k));
    }

    public List<Meal> getMeals() {
        return new ArrayList<>(mapMeals.values());
    }

    public synchronized void addMeal(Meal meal) {
        int id = meal.getId() == 0 ? TOTAL_ID.getAndIncrement() : meal.getId();
        meal.setId(id);
        mapMeals.put(id, meal);
    }

    public Meal getMeal(int mealId) {
        return mapMeals.get(mealId);
    }

    public void deleteMeal(int id) {
        mapMeals.remove(id);
    }
}
