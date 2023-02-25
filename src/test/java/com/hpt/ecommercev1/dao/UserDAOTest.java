package com.hpt.ecommercev1.dao;

import com.hpt.ecommercev1.entity.Address;
//import com.hpt.ecommercev1.entity.HashGenerator;
import com.hpt.ecommercev1.entity.Role;
import com.hpt.ecommercev1.entity.User;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class UserDAOTest {
    private final UserDAO userDAO = UserDAO.getInstance();

    @Test
    public void testSaveWithOneRole() {
        Address address = new Address();
        address.setStreet("đường số 12");
        address.setWard("bình hưng hòa");
        address.setDistrict("bình tân");
        address.setCity("hồ chí minh");

        Role role = new Role();
        role.setId(2);

        User user = new User();
        user.setFirstName("thông");
        user.setLastName("hoàng");
        user.setPhoneNumber("0123");
        user.setAddress(address);
        user.setEmail("thong@gmail.com");
        user.setPassword("thong123");
        user.setEnabled(true);
        user.addRole(role);

        userDAO.save(user);

        assertEquals("role", 1, user.getRoles().size());
        assertEquals("address", "hồ chí minh", user.getAddress().getCity());
        assertEquals("email", "thong@gmail.com", user.getEmail());
    }

    @Test
    public void testSaveWithTwoRoles() {
        Address address = new Address();
        address.setStreet("khu phố 6");
        address.setWard("linh trung");
        address.setDistrict("thủ đức");
        address.setCity("hồ chí minh");

        Role role1 = new Role();
        role1.setId(2);
        Role role2 = new Role();
        role2.setId(3);

        User user = new User();
        user.setFirstName("vinh");
        user.setLastName("hoàng");
        user.setPhoneNumber("0123");
        user.setAddress(address);
        user.setEmail("vinh@gmail.com");
        user.setPassword("vinh123");
        user.setEnabled(true);
        user.addRole(role1);
        user.addRole(role2);

        userDAO.save(user);

        assertEquals("role", 2, user.getRoles().size());
    }

    @Test
    public void findById() {
        User user = userDAO.findById(1);
        System.out.println(user);
        assertNotNull(user);
    }

    @Test
    public void findByEmail() {
        User user = userDAO.findByEmail("thong@gmail.com");

        assertNotNull(user);
    }

    @Test
    public void update() {
        User user = userDAO.findById(1);
        user.setEmail("thonghoang@gmail.com");

        Address address = new Address();
        address.setId(1);
        address.setStreet("113/4 đường số 12");
        address.setWard("bình hưng hòa");
        address.setDistrict("bình tân");
        address.setCity("hồ chí minh");
        user.setAddress(address);

        Set<Role> roles = new HashSet<>();
        Role role = new Role();
        role.setId(3);
        roles.add(role);
        user.setRoles(roles);

        System.out.println(user);

        userDAO.update(user);

        assertEquals("email", "thonghoang@gmail.com", user.getEmail());
        assertEquals("street", "113/4 đường số 12", user.getAddress().getStreet());
        assertTrue("role", user.getRoles().contains(role));
    }

    @Test
    public void delete() {
        Integer id = 1;
        userDAO.delete(id);

        User user = userDAO.findById(1);

        assertNull(user);
    }

    @Test
    public void testCountByKeyword() {
        String keyword = "";
        int count = userDAO.countByKeyword(keyword);

        assertEquals(2, count);
    }

    @Test
    public void testUpdateEnableStatusFalse() {
        Integer id = 1;
        boolean enabled = false;
        userDAO.updateEnableStatus(id, enabled);

        User user = userDAO.findById(1);

        assertFalse(user.isEnabled());
    }

    @Test
    public void testUpdateEnableStatusTrue() {
        Integer id = 1;
        boolean enabled = true;
        userDAO.updateEnableStatus(id, enabled);

        User user = userDAO.findById(1);

        assertTrue(user.isEnabled());
    }

//    @Test
//    public void testDoLoginSuccess() {
//        String email = "thong@gmail.com";
//        String password = HashGenerator.generateMD5("thong123");
//        User user = userDAO.doLogin(email, password);
//
//        assertNotNull(user);
//    }

//    @Test
//    public void testDoLoginFail() {
//        String email = "thong@gmail.com";
//        String password = HashGenerator.generateMD5("thong456");
//        User user = userDAO.doLogin(email, password);
//
//        assertNull(user);
//    }

//    @Test
//    public void testDoLoginWithEnableFalse() {
//        userDAO.updateEnableStatus(1, false);
//        String email = "thong@gmail.com";
//        String password = HashGenerator.generateMD5("thong123");
//        User user = userDAO.doLogin(email, password);
//
//        assertNull(user);
//    }

    @Test
    public void testFindAll() {
        String keyword = "";
        String sortField = "id";
        String sortType = "ASC";
        int pageSize = 5;
        int pageIndex = 1;

        List<User> users = userDAO.findAll(keyword, sortField, sortType, pageSize, pageIndex);
        users.forEach(System.out::println);

        assertEquals(2, users.size());
    }

    @Test
    public void testFindAllByKeyword() {
        String keyword = "thong";
        String sortField = "id";
        String sortType = "ASC";
        int pageSize = 5;
        int pageIndex = 1;

        List<User> users = userDAO.findAll(keyword, sortField, sortType, pageSize, pageIndex);
        users.forEach(System.out::println);

        assertEquals(1, users.size());
    }

    @Test
    public void testCheckEmailExists() {
        String email = "thong@gmail.com";
        boolean exists = userDAO.checkEmailExists(email);

        assertTrue(exists);
    }
}