package com.hpt.ecommercev1.service;

import com.hpt.ecommercev1.dao.RoleDAO;
import com.hpt.ecommercev1.dao.UserDAO;
import com.hpt.ecommercev1.entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.hpt.ecommercev1.utils.Constants.*;

/**
 * @author Hoang Pham Thong
 * @version 1.0
 * @since 2023-2-2
 */
public class EmployeeService {
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final UserDAO userDAO = UserDAO.getInstance();
    private final RoleDAO roleDAO = RoleDAO.getInstance();

    public EmployeeService(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    /**
     * Returns a list of employees that respond back to user.
     *
     * @throws ServletException If the request for the GET could not be handled
     * @throws IOException      If an input or output error is detected when the servlet handles the GET request
     */
    public void listEmployee() throws ServletException, IOException {
        listEmployee(null);
    }

    /**
     * Returns a list of employees and a response message to the user.
     *
     * @param message A message specified to display to the user
     * @throws ServletException If the request for the GET could not be handled
     * @throws IOException      If an input or output error is detected when the servlet handles the GET request
     */
    public void listEmployee(String message) throws ServletException, IOException {
        // default page is 1
        int pageNumber = (request.getParameter("pageNumber") == null) ? 1 : Integer.parseInt(request.getParameter("pageNumber"));

        // default keyword is empty
        String keyword = (request.getParameter("keyword") == null) ? "" : request.getParameter("keyword");

        int numberOfEmployees = userDAO.countByKeyword("");
        int totalKeywordResults = userDAO.countByKeyword(keyword);

        List<User> employees = userDAO.findAll(keyword, DEFAULT_SORT_FIELD, DEFAULT_SORT_TYPE, DEFAULT_PAGE_SIZE, pageNumber);

        long totalPages = totalKeywordResults / DEFAULT_PAGE_SIZE;
        if (numberOfEmployees % DEFAULT_PAGE_SIZE != 0) totalPages++;

        request.setAttribute("currentPage", pageNumber);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("keyword", keyword);
        request.setAttribute("listEmployees", employees);

        if (message != null) request.setAttribute("message", message);

        String employeePage = "employee.jsp";
        request.getRequestDispatcher(employeePage).forward(request, response);
    }

    public void createEmployee() throws ServletException, IOException {
        request.setAttribute("roles", roleDAO.findAllRolesExceptAdmin());
        String createEmployeePage = "employee-form.jsp";
        request.getRequestDispatcher(createEmployeePage).forward(request, response);
    }
}
