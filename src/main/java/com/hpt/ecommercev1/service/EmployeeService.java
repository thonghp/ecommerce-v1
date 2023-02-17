package com.hpt.ecommercev1.service;

import com.hpt.ecommercev1.dao.RoleDAO;
import com.hpt.ecommercev1.dao.UserDAO;
import com.hpt.ecommercev1.entity.Address;
import com.hpt.ecommercev1.entity.HashGenerator;
import com.hpt.ecommercev1.entity.Role;
import com.hpt.ecommercev1.entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.hpt.ecommercev1.utils.Constants.*;
import static com.hpt.ecommercev1.utils.FileUploadUtil.*;

/**
 * @author Hoang Pham Thong
 * @version 1.0
 * @since 2023-2-2
 */
public class EmployeeService {
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final UserDAO userDAO = UserDAO.getInstance();

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
        listEmployee(null, 1, "");
    }

    /**
     * Returns a list of employees and a response message to the user.
     *
     * @param message A message specified to display to the user
     * @throws ServletException If the request for the GET could not be handled
     * @throws IOException      If an input or output error is detected when the servlet handles the GET request
     */
    public void listEmployee(String message, int pageNum, String keyword) throws ServletException, IOException {
        // default page is 1
        int pageNumber = (request.getParameter("pageNumber") == null) ? pageNum : Integer.parseInt(request.getParameter("pageNumber"));

        // default keyword is empty
        String search = (request.getParameter("keyword") == null) ? keyword : request.getParameter("keyword");

        int numberOfEmployees = userDAO.countByKeyword("");
        int totalKeywordResults = userDAO.countByKeyword(search);

        List<User> employees = userDAO.findAll(search, DEFAULT_SORT_FIELD, DEFAULT_SORT_TYPE, DEFAULT_PAGE_SIZE, pageNumber);
        System.out.println("employees: " + employees.get(0));

        long totalPages = totalKeywordResults / DEFAULT_PAGE_SIZE;
        if (numberOfEmployees % DEFAULT_PAGE_SIZE != 0) totalPages++;

        request.setAttribute("currentPage", pageNumber);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("keyword", search);
        request.setAttribute("listEmployees", employees);

        if (message != null) request.setAttribute("message", message);

        String employeePage = "employee.jsp";
        request.getRequestDispatcher(employeePage).forward(request, response);
    }

    public Map<Role, String> getCheckedRoles(User user) {
        Map<Role, String> roleMap = new HashMap<>();

        RoleDAO.getInstance().findAllRolesExceptAdmin().forEach(role -> roleMap.put(role, ""));
        user.getRoles().forEach(role -> roleMap.put(role, "checked"));
        return roleMap;
    }

    public void createEmployee() throws ServletException, IOException {
        createEmployee(new User());
    }

    public void createEmployee(User user) throws ServletException, IOException {
        request.setAttribute("checkedRoles", getCheckedRoles(user));
        String createEmployeePage = "employee_form.jsp";
        request.getRequestDispatcher(createEmployeePage).forward(request, response);
    }

    private User readAllFields(User user) {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String phone = request.getParameter("phone");
        String street = request.getParameter("street");
        String ward = request.getParameter("ward");
        String district = request.getParameter("district");
        String city = request.getParameter("city");
        String[] roles = request.getParameterValues("role");
        boolean enabled = "on".equals(request.getParameter("enable"));

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
        user.setPhoneNumber(phone);
        user.setEnabled(enabled);

        Address address = new Address();
        address.setStreet(street);
        address.setWard(ward);
        address.setDistrict(district);
        address.setCity(city);
        user.setAddress(address);

        for (String roleId : roles) {
            Role role = new Role();
            role.setId(Integer.parseInt(roleId));
            user.addRole(role);
        }

        return user;
    }

    /**
     * Get employee information from the form and save it in the database.
     *
     * @throws ServletException If the request for the POST could not be handled.
     * @throws IOException      If an input or output error is detected when the servlet handles the POST request.
     */
    public void saveEmployee() throws ServletException, IOException {
        User user = readAllFields(new User());

        boolean existEmployee = userDAO.checkEmailExists(user.getEmail());
        if (existEmployee) {
            String message = "Email " + user.getEmail() + " đã tồn tại";
            request.setAttribute("message", message);
            request.setAttribute("user", user);

            createEmployee(user);
        } else {
            String fileName = extractFileName(request, "image");

            if (!fileName.isEmpty()) {
                user.setImagePath(fileName);
                userDAO.save(user);

                Integer id = userDAO.findByEmail(user.getEmail()).getId();
                String nameDirectoryServer = "employee" + File.separator + id;

                saveFileOnServer(request, nameDirectoryServer, "image", fileName);
                saveFileOnApp(request, nameDirectoryServer, fileName);
            } else {
                userDAO.save(user);
            }

            String message = "Nhân viên " + user.getLastName() + " " + user.getFirstName() + " đã được thêm thành công !";
            listEmployee(message, 1, "");
        }
    }

    /**
     * Get the employee's infornamtions by id from request and display it to user.
     *
     * @throws ServletException If the request for the GET could not be handled
     * @throws IOException      If an input or output error is detected when the servlet handles the GET request
     */
    public void editEmployee() throws ServletException, IOException {
        Integer id = Integer.valueOf(request.getParameter("id"));

        User user = userDAO.findById(id);

        Map<Role, String> checkedRoles = getCheckedRoles(user);

        request.setAttribute("checkedRoles", checkedRoles);

        String message;

        if (user == null) {
            message = "Không tìm thấy nhân viên";
            listEmployee(message, 1, "");
        } else {
            request.setAttribute("user", user);
            request.setAttribute("title", "Admin - Chỉnh sửa nhân viên");
            createEmployee(user);
        }
    }

    /**
     * Get the employee's information from the request and update it into the database.
     *
     * @throws ServletException If the request for the POST could not be handled
     * @throws IOException      If an input or output error is detected when the servlet handles the POST request
     */
    public void updateEmployee() throws ServletException, IOException {
        Integer id = Integer.valueOf(request.getParameter("id"));
        Integer addressId = Integer.valueOf(request.getParameter("addressId"));

        User user = readAllFields(new User());
        user.setId(id);
        user.getAddress().setId(addressId);

        User userById = userDAO.findById(id);
        User userByEmail = userDAO.findByEmail(user.getEmail());

        String message;

        if (userById == null) {
            message = "Không tìm thấy nhân viên";
            listEmployee(message, 1, "");
        } else {
            if (userByEmail != null && !id.equals(userByEmail.getId())) {
                message = "Email " + user.getEmail() + " đã tồn tại!!!";
                request.setAttribute("message", message);
                request.setAttribute("user", user);
                request.setAttribute("title", "Chỉnh sửa nhân viên");

                createEmployee(user);
            } else {
                if (!user.getPassword().isEmpty())
                    user.setPassword(HashGenerator.generateMD5(user.getPassword()));
                else
                    user.setPassword(userById.getPassword());

                String fileName = extractFileName(request, "image");

                if (!fileName.isEmpty()) {
                    user.setImagePath(fileName);
                    String nameDirectoryServer = "employee" + File.separator + id;
                    String directoryServerPath = getDirectoryServerPath(request, nameDirectoryServer);

                    cleanDir(directoryServerPath);
                    cleanDir(DEFAULT_APP_IMAGE_DIRECTORY + nameDirectoryServer);

                    saveFileOnServer(request, nameDirectoryServer, "image", fileName);
                    saveFileOnApp(request, nameDirectoryServer, fileName);
                } else {
                    user.setImagePath(userById.getImagePath());
                }

                userDAO.update(user);
                String fullName = user.getFirstName() + " " + user.getLastName();
                message = "Nhân viên " + fullName + " đã được cập nhật thành công !";
                listEmployee(message, 1, fullName);
            }
        }
    }
}
