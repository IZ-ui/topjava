package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static org.junit.Assert.assertNull;
import static ru.javawebinar.topjava.MealTestData.MEAL_MATCHER;
import static ru.javawebinar.topjava.MealTestData.meals;
import static ru.javawebinar.topjava.Profiles.DATAJPA;
import static ru.javawebinar.topjava.UserTestData.*;
import static org.junit.Assert.assertThrows;

@ActiveProfiles({DATAJPA})
public class UserServiceDatajpaTest extends AbstractUserServiceTest {

    @Test
    public void getWithMeal() {
        User actualUser = service.getWithMeal(USER_ID);
        USER_MATCHER.assertMatch(actualUser, user);
        MEAL_MATCHER.assertMatch(actualUser.getMeals(), meals);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.getWithMeal(NOT_FOUND));
    }

    @Test
    public void getWithNoMeals() {
        assertNull(getNew().getMeals());
    }
}
