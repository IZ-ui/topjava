package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int MEAL_ID = START_SEQ + 2;

    public static final Meal userMeal1 = new Meal(MEAL_ID, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "uzhinUser", 410);
    public static final Meal userMeal2 = new Meal(MEAL_ID + 1, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "obedUser", 500);
    public static final Meal userMeal3 = new Meal(MEAL_ID + 2, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "zavtrakUser", 1000);
    public static final Meal userMeal4 = new Meal(MEAL_ID + 3, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "uzhinUser", 500);
    public static final Meal userMeal5 = new Meal(MEAL_ID + 4, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "obedUser", 1000);
    public static final Meal userMeal6 = new Meal(MEAL_ID + 5, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "zavtrakUser", 500);
    public static final Meal userMeal7 = new Meal(MEAL_ID + 6, LocalDateTime.of(2020, Month.JANUARY, 30, 0, 0), "edanagraniUser", 100);
    public static final Meal adminMeal1 = new Meal(MEAL_ID + 7, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "obedAdmin", 500);
    public static final Meal adminMeal2 = new Meal(MEAL_ID + 8, LocalDateTime.of(2020, Month.JANUARY, 30, 19, 0), "uzhinAdmin", 1000);

    public static Meal getNew() {
        return new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 30, 11, 0), "newMeal", 555);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(userMeal1);
        updated.setDescription("UpdatedMeal");
        updated.setCalories(333);
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }
}
