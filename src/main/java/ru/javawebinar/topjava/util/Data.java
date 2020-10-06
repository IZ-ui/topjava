package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.time.LocalDateTime;
import java.util.List;

public interface Data {
    List<MealTo> getMealTos();

    void addMeal(Meal meal);

    Meal getMeal(int id);

    void updateMeal(int id, LocalDateTime dateTime, String description, int calories);

    void deleteStudent(int id);
}
