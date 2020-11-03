package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.service.AbstractMealServiceTest;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.Profiles.DATAJPA;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles({DATAJPA})
public class MealServiceDatajpaTest extends AbstractMealServiceTest {

    @Test
    public void getWithUser() {
        MEAL_MATCHER.assertMatch(service.getWithUser(ADMIN_MEAL_ID, ADMIN_ID), adminMeal1);
        USER_MATCHER.assertMatch(service.getWithUser(ADMIN_MEAL_ID, ADMIN_ID).getUser(), admin);
    }
}
