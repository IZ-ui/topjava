package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MemoryImplMealDao;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    public static final int TOTAL_CALORIES = 2000;

    private MealDao mealDao;

    @Override
    public void init() throws ServletException {
        mealDao = new MemoryImplMealDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("doGet method in MealServlet");
        String command = request.getParameter("command");
        if (command == null) {
            command = "list";
        }
        switch (command) {
            case "load":
                load(request, response);
                break;
            case "delete":
                delete(request, response);
                break;
            default:
                list(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("doPost method in MealServlet");
        request.setCharacterEncoding("UTF-8");
        add(request, response);
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("delete meal");
        int id = getId(request.getParameter("mealId"));
        mealDao.delete(id);
        response.sendRedirect(request.getContextPath() + "/meals");
    }

    private void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("add or update meal");
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("dateTime"));
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        Meal meal = new Meal(dateTime, description, calories);

        String id = request.getParameter("mealId");
        if (id != null) {
            meal.setId(getId(id));
        }

        mealDao.save(meal);
        response.sendRedirect(request.getContextPath() + "/meals");
    }

    private void load(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("load meal");
        String idString;
        if ((idString = request.getParameter("mealId")) != null) {
            int id = getId(idString);
            Meal meal = mealDao.get(id);
            request.setAttribute("meal", meal);
            response.setCharacterEncoding("UTF-8");
        }

        // send to jsp page: save-meal-form.jsp
        RequestDispatcher dispatcher =
                request.getRequestDispatcher("/save-meal-form.jsp");
        dispatcher.forward(request, response);
    }

    private void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("get all meals");
        List<Meal> meals = mealDao.getAll();
        List<MealTo> mealTos = MealsUtil.filteredByStreams(meals, LocalTime.MIN, LocalTime.MAX, TOTAL_CALORIES);
        request.setAttribute("mealTos", mealTos);
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }

    private int getId(String id) {
        return (id.equals("")) ? 0 : Integer.parseInt(id);
    }
}
