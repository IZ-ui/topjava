package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;


@ContextConfiguration({
        "classpath:spring/spring.xml",
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal meal = service.get(MEAL_ID, UserTestData.USER_ID);
        assertMatch(meal, userMeal1);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(MEAL_ID, UserTestData.ADMIN_ID));
    }

    @Test
    public void delete() {
        service.delete(MEAL_ID, UserTestData.USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(MEAL_ID, UserTestData.USER_ID));
    }

    @Test
    public void deletedNotFound() throws Exception {
        assertThrows(NotFoundException.class, () -> service.delete(MEAL_ID, UserTestData.ADMIN_ID));
    }

    @Test
    public void getBetweenInclusive() {
        List<Meal> meals = service.getBetweenInclusive(LocalDate.of(2020, Month.JANUARY, 20),
                LocalDate.of(2020, Month.JANUARY, 30), UserTestData.USER_ID);
        assertMatch(meals, userMeal4, userMeal5, userMeal6, userMeal7);
    }

    @Test
    public void getBetweenInclusiveNullNull() {
        List<Meal> meals = service.getBetweenInclusive(null, null, UserTestData.USER_ID);
        assertMatch(meals, userMeal1, userMeal2, userMeal3, userMeal4, userMeal5, userMeal6, userMeal7);
    }

    @Test
    public void getAll() {
        List<Meal> userMeals = service.getAll(UserTestData.USER_ID);
        assertMatch(userMeals, userMeal1, userMeal2, userMeal3, userMeal4, userMeal5, userMeal6, userMeal7);
        List<Meal> adminMeals = service.getAll(UserTestData.ADMIN_ID);
        assertMatch(adminMeals, adminMeal2, adminMeal1);
    }

    @Test
    public void update() {
        Meal updated = getUpdated();
        service.update(updated, UserTestData.USER_ID);
        assertMatch(service.get(MEAL_ID, UserTestData.USER_ID), updated);
    }

    @Test
    public void updateNotFound() {
        Meal updated = getUpdated();
        assertThrows(NotFoundException.class, () -> service.update(updated, UserTestData.ADMIN_ID));
    }

    @Test
    public void create() {
        Meal newMeal = getNew();
        Meal created = service.create(newMeal, UserTestData.USER_ID);
        Integer newId = created.getId();
        newMeal.setId(newId);
        assertMatch(created, newMeal);
        assertMatch(service.get(newId, UserTestData.USER_ID), newMeal);
    }

    @Test
    public void createWithSameDate() {
        Meal newMeal = getNew();
        newMeal.setDateTime(userMeal1.getDateTime());
        assertThrows(DuplicateKeyException.class, () -> service.create(newMeal, UserTestData.USER_ID));
    }
}