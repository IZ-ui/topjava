package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);


        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
        System.out.println(filteredByStreams2(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
}

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with excess. Implement by cycles
        // Базовое задание
        Map<LocalDate, Integer> mapForTotalCals = new HashMap<>();
        for(UserMeal u : meals) {
            LocalDateTime currentLocalDateTime = u.getDateTime();
            if(TimeUtil.isBetweenHalfOpen(LocalTime.from(currentLocalDateTime), startTime, endTime))
                mapForTotalCals.merge(LocalDate.from(currentLocalDateTime), u.getCalories(), Integer::sum);
        }

        List<UserMealWithExcess> resultList = new ArrayList<>();
        for(UserMeal u : meals) {
            LocalDateTime currentLocalDateTime = u.getDateTime();
            if(TimeUtil.isBetweenHalfOpen(LocalTime.from(currentLocalDateTime), startTime, endTime)) {
                boolean excess = false;
                if (mapForTotalCals.get(LocalDate.from(currentLocalDateTime)) > caloriesPerDay)
                    excess = true;
                resultList.add(new UserMealWithExcess(currentLocalDateTime, u.getDescription(), u.getCalories(), excess));
            }
        }

        return resultList;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // Optional (Java 8 Stream API) <<<first var>>>
        Map<LocalDate, Integer> mapForTotalCals = meals
                .stream()
                .filter(u -> TimeUtil.isBetweenHalfOpen(LocalTime.from(u.getDateTime()), startTime, endTime))
                .collect(Collectors.toMap(
                        u -> LocalDate.from(u.getDateTime()),
                        UserMeal::getCalories,
                        Integer::sum));

        List<UserMealWithExcess> resultList = new ArrayList<>();

        meals.stream()
                .filter(u -> TimeUtil.isBetweenHalfOpen(LocalTime.from(u.getDateTime()), startTime, endTime))
                .forEach(u -> {
                    if (mapForTotalCals.get(LocalDate.from(u.getDateTime())) < caloriesPerDay) {
                        resultList.add(new UserMealWithExcess(u.getDateTime(), u.getDescription(), u.getCalories(),false));
                    } else {
                        resultList.add(new UserMealWithExcess(u.getDateTime(), u.getDescription(), u.getCalories(),true));
                    }
                });

        return resultList;
    }

    public static List<UserMealWithExcess> filteredByStreams2(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // Optional (Java 8 Stream API) <<<second var>>>
        Map<LocalDate, Integer> mapForTotalCals = meals
                .stream()
                .filter(u -> TimeUtil.isBetweenHalfOpen(LocalTime.from(u.getDateTime()), startTime, endTime))
                .collect(Collectors.toMap(
                        u -> LocalDate.from(u.getDateTime()),
                        UserMeal::getCalories,
                        Integer::sum));

        List<UserMealWithExcess> resultList = new ArrayList<>();

        meals.stream()
                .filter(u -> TimeUtil.isBetweenHalfOpen(LocalTime.from(u.getDateTime()), startTime, endTime))
                .filter(u -> mapForTotalCals.get(LocalDate.from(u.getDateTime())) < caloriesPerDay)
                .map(u -> new UserMealWithExcess(u.getDateTime(), u.getDescription(), u.getCalories(), false))
                .forEach(resultList::add);

        meals.stream()
                .filter(u -> TimeUtil.isBetweenHalfOpen(LocalTime.from(u.getDateTime()), startTime, endTime))
                .filter(u -> mapForTotalCals.get(LocalDate.from(u.getDateTime())) > caloriesPerDay)
                .map(u -> new UserMealWithExcess(u.getDateTime(), u.getDescription(), u.getCalories(), true))
                .forEach(resultList::add);

        return resultList;
    }

}
