package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.time.LocalDateTime;
import java.util.List;

public interface MealDao {
    List<Meal> getMeals();

    void addMeal(Meal meal);

    Meal getMeal(int id);

    void deleteMeal(int id);
}
