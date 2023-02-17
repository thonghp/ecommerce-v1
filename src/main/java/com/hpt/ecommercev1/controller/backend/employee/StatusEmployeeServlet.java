package com.hpt.ecommercev1.controller.backend.employee;

import com.hpt.ecommercev1.service.EmployeeService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "StatusEmployeeServlet", value = "/backend/status_employee")
public class StatusEmployeeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EmployeeService service = new EmployeeService(request, response);
        service.updateEmployeeStatus();
    }
}
