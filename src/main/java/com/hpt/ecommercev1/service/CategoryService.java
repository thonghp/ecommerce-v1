package com.hpt.ecommercev1.service;

import com.hpt.ecommercev1.dao.CategoryDAO;
import com.hpt.ecommercev1.entity.Category;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.hpt.ecommercev1.utils.Constants.*;

/**
 * @author Hoang Pham Thong
 * @version 1.0
 * @since 2023-2-4
 */
public class CategoryService {
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final CategoryDAO categoryDAO = CategoryDAO.getInstance();

    public CategoryService(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    /**
     * Returns a list of categories that respond back to user.
     *
     * @throws ServletException If the request for the GET could not be handled
     * @throws IOException      If an input or output error is detected when the servlet handles the GET request
     */
    public void listCategory() throws ServletException, IOException {
        listCategory(null, 1, "");
    }

    /**
     * Returns a list of categories and a response message to the user.
     *
     * @param message A message specified to display to the user
     * @throws ServletException If the request for the GET could not be handled
     * @throws IOException      If an input or output error is detected when the servlet handles the GET request
     */
    public void listCategory(String message, int pageNum, String keyword) throws ServletException, IOException {
        // default page is 1
        int pageNumber = (request.getParameter("pageNumber") == null) ? pageNum : Integer.parseInt(request.getParameter("pageNumber"));

        // default keyword is empty
        String search = (request.getParameter("keyword") == null) ? keyword : request.getParameter("keyword");

        int numberOfCategories = categoryDAO.countByKeyword("");
        int totalKeywordResults = categoryDAO.countByKeyword(search);

        List<Category> categories = categoryDAO.findAll(search, DEFAULT_SORT_FIELD, DEFAULT_SORT_TYPE, DEFAULT_PAGE_SIZE, pageNumber);

        long totalPages = totalKeywordResults / DEFAULT_PAGE_SIZE;
        if (numberOfCategories % DEFAULT_PAGE_SIZE != 0) totalPages++;

        request.setAttribute("currentPage", pageNumber);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("keyword", search);
        request.setAttribute("listCategories", categories);

        if (message != null) request.setAttribute("message", message);

        String employeePage = "category.jsp";
        request.getRequestDispatcher(employeePage).forward(request, response);
    }
}
