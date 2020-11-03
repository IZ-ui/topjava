package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;

import static ru.javawebinar.topjava.MealTestData.MEAL_MATCHER;
import static ru.javawebinar.topjava.MealTestData.meals;
import static ru.javawebinar.topjava.Profiles.DATAJPA;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles({DATAJPA})
public class UserServiceDatajpaTest extends AbstractUserServiceTest {

    @Test
    public void getWithMeal() {
        USER_MATCHER.assertMatch(service.getWithMeal(USER_ID), user);
        MEAL_MATCHER.assertMatch(service.getWithMeal(USER_ID).getMeals(), meals);
    }
}
