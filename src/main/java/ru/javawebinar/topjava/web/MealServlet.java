package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.Data;
import ru.javawebinar.topjava.util.MemoryImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private final Data data = new MemoryImpl();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("doGet method in MealServlet");
        String theCommand = request.getParameter("command");
        if (theCommand == null) {
            theCommand = "LIST";
        }
        switch (theCommand) {
            case "LIST":
                listMealTos(request, response);
                break;
            case "ADD":
                addMeal(request, response);
                break;
            case "LOAD":
                loadMeal(request, response);
                break;
            case "UPDATE":
                updateMeal(request, response);
                break;
            case "DELETE":
                deleteMeal(request, response);
                break;
            default:
                listMealTos(request, response);
        }
    }

    private void deleteMeal(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("delete meal");
        int id = Integer.parseInt(request.getParameter("mealId"));
        data.deleteStudent(id);
//        listMealTos(request, response);
        response.sendRedirect(request.getContextPath() + "/meals");
    }

    private void updateMeal(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("update meal");
        int id = Integer.parseInt(request.getParameter("mealId"));
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("dateTime"));
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        data.updateMeal(id, dateTime, description, calories);
        listMealTos(request, response);
    }

    private void loadMeal(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("load meal");
        int id = Integer.parseInt(request.getParameter("mealId"));
        Meal meal = data.getMeal(id);
        request.setAttribute("THE_MEAL", meal);
        response.setCharacterEncoding("UTF-8");

        // send to jsp page: update-meal-form.jsp
        RequestDispatcher dispatcher =
                request.getRequestDispatcher("/update-meal-form.jsp");
        dispatcher.forward(request, response);
    }

    private void addMeal(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("add meal");
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("dateTime"));
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));

        Meal meal = new Meal(dateTime, description, calories);
        data.addMeal(meal);
        listMealTos(request, response);
    }

    private void listMealTos(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("get all meals");
        List<MealTo> mealTos = data.getMealTos();
        request.setAttribute("mealTos", mealTos);
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }
}
