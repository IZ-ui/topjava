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
    private Map<Integer, Meal> mapMeals = new ConcurrentHashMap<>();
    private final AtomicInteger totalId = new AtomicInteger(1);

    {
        save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
    }

    public List<Meal> getAll() {
        return new ArrayList<>(mapMeals.values());
    }

    public synchronized Meal save(Meal meal) {
        int id = meal.getId();
        if (id == 0) {
            id = totalId.getAndIncrement();
            meal.setId(id);
            mapMeals.put(id, meal);
        } else {
            if (mapMeals.replace(meal.getId(), meal) == null) {
                return null;
            }
        }
        return meal;
    }

    public Meal get(int mealId) {
        return mapMeals.get(mealId);
    }

    public void delete(int id) {
        mapMeals.remove(id);
    }
}
