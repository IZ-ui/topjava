package ru.javawebinar.topjava.repository.jdbc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static ru.javawebinar.topjava.MealTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class JdbcMealRepositoryTest {

    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private JdbcMealRepository repository;

    @Test
    public void saveUpdate() {
        Meal updated = getUpdated();
        repository.save(updated, UserTestData.USER_ID);
        assertMatch(repository.get(MEAL_ID, UserTestData.USER_ID), updated);
    }

    @Test
    public void saveCreate() {
        Meal newMeal = getNew();
        Meal created = repository.save(newMeal, UserTestData.USER_ID);
        Integer newId = created.getId();
        newMeal.setId(newId);
        assertMatch(created, newMeal);
        assertMatch(repository.get(newId, UserTestData.USER_ID), newMeal);
    }

    @Test
    public void delete() {
        repository.delete(MEAL_ID, UserTestData.USER_ID);
        assertMatch(repository.get(MEAL_ID, UserTestData.USER_ID), null);
    }

    @Test
    public void deleteNull() {
        assertFalse(repository.delete(MEAL_ID, UserTestData.ADMIN_ID));
    }

    @Test
    public void get() {
        Meal meal = repository.get(MEAL_ID, UserTestData.USER_ID);
        assertMatch(meal, userMeal1);
    }

    @Test
    public void getNull() {
        Meal meal = repository.get(MEAL_ID, UserTestData.ADMIN_ID);
        assertMatch(meal, null);
    }

    @Test
    public void getAll() {
        List<Meal> meals = repository.getAll(UserTestData.USER_ID);
        assertMatch(meals, userMeal1, userMeal2, userMeal3, userMeal4, userMeal5, userMeal6, userMeal7);
        List<Meal> adminMeals = repository.getAll(UserTestData.ADMIN_ID);
        assertMatch(adminMeals, adminMeal2, adminMeal1);
    }

    @Test
    public void getBetweenHalfOpen() {
        List<Meal> meals = repository.getBetweenHalfOpen(LocalDateTime.of(2020, Month.JANUARY, 30, 9, 0),
                LocalDateTime.of(2020, Month.JANUARY, 31, 11, 0), UserTestData.USER_ID);
        assertMatch(meals, userMeal3, userMeal4, userMeal5, userMeal6);
    }
}