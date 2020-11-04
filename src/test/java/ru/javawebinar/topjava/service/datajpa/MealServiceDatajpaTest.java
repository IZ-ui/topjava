package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.AbstractMealServiceTest;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.Profiles.DATAJPA;
import static ru.javawebinar.topjava.UserTestData.*;
import static org.junit.Assert.assertThrows;

@ActiveProfiles(DATAJPA)
public class MealServiceDatajpaTest extends AbstractMealServiceTest {

    @Test
    public void getWithUser() {
        Meal meal = service.getWithUser(ADMIN_MEAL_ID, ADMIN_ID);
        MEAL_MATCHER.assertMatch(meal, adminMeal1);
        USER_MATCHER.assertMatch(meal.getUser(), admin);
    }

    @Test
    public void getWithUserNotFound() {
        assertThrows(NotFoundException.class, () -> service.getWithUser(MealTestData.NOT_FOUND, ADMIN_ID));
    }

    @Test
    public void gitWithNotOwnMealWithUser() {
        assertThrows(NotFoundException.class, () -> service.getWithUser(ADMIN_MEAL_ID, USER_ID));
    }
}
