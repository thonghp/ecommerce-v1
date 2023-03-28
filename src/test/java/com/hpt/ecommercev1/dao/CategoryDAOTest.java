package com.hpt.ecommercev1.dao;

import com.hpt.ecommercev1.entity.Category;
import org.junit.Test;

import static org.junit.Assert.*;

public class CategoryDAOTest {
    private final CategoryDAO categoryDAO = CategoryDAO.getInstance();

    @Test
    public void testSaveRootCategory() {
        Category category = new Category();
        category.setName("Đồ chơi");
        category.setAlias("Đồ-chơi");
        category.setParentId(0);
        category.setEnabled(true);

        categoryDAO.save(category);

        long parentIdExpect = 0;
        long parentIdActual = category.getParentId();

        assertEquals("name", "Đồ chơi", category.getName());
        assertEquals("parentId", parentIdExpect, parentIdActual);
        assertTrue("enabled", category.isEnabled());
    }

    @Test
    public void testSaveChildCategory() {
        Category category = new Category();
        category.setName("Lắp ráp");
        category.setAlias("Lắp-ráp");
        category.setParentId(1);
        category.setEnabled(true);

        categoryDAO.save(category);

        long parentIdExpect = 1;
        long parentIdActual = category.getParentId();

        assertEquals("name", "Lắp ráp", category.getName());
        assertEquals("parentId", parentIdExpect, parentIdActual);
        assertTrue("enabled", category.isEnabled());
    }

    @Test
    public void testFindById() {
        Category category = categoryDAO.findById(1);
        long parentIdExpect = 0;
        long parentIdActual = category.getParentId();

        assertEquals("name", "Đồ chơi", category.getName());
        assertEquals("parentId", parentIdExpect, parentIdActual);
    }

    @Test
    public void testFindByName() {
        Category category = categoryDAO.findByName("Đồ chơi");
        long parentIdExpect = 0;
        long parentIdActual = category.getParentId();

        assertEquals("name", "Đồ chơi", category.getName());
        assertEquals("parentId", parentIdExpect, parentIdActual);
    }

    @Test
    public void testUpdateRootCategory() {
        Category category = categoryDAO.findById(1);
        category.setName("Đồ chơi mới");
        category.setAlias("Đồ-chơi-mới");
        category.setParentId(0);

        categoryDAO.update(category);

        System.out.println(category);

        long parentIdExpect = 0;
        long parentIdActual = category.getParentId();

        assertEquals("name", "Đồ chơi mới", category.getName());
        assertEquals("parentId", parentIdExpect, parentIdActual);
        assertTrue("enabled", category.isEnabled());
    }

    @Test
    public void testUpdateChildCategory() {
        Category category = categoryDAO.findById(2);
        category.setName("Lego");
        category.setAlias("Lego");
        category.setParentId(2);

        categoryDAO.update(category);

        System.out.println(category);

        long parentIdExpect = 2;
        long parentIdActual = category.getParentId();

        assertEquals("name", "Lego", category.getName());
        assertEquals("parentId", parentIdExpect, parentIdActual);
        assertTrue("enabled", category.isEnabled());
    }

    @Test
    public void testCheckParentIdExists() {
        boolean result = categoryDAO.checkParentIdExists(1);
        assertTrue(result);
    }

    @Test
    public void testDelete() {
        Integer id = 2;
        categoryDAO.delete(id);

        Category category = categoryDAO.findById(id);

        assertNull(category);
    }

    @Test
    public void testUpdateEnableStatusFalse() {
        Integer id = 1;
        boolean enabled = false;
        categoryDAO.updateEnabledStatus(id, enabled);

        Category category = categoryDAO.findById(id);

        assertFalse(category.isEnabled());
    }

    @Test
    public void testUpdateEnableStatusTrue() {
        Integer id = 1;
        boolean enabled = true;
        categoryDAO.updateEnabledStatus(id, enabled);

        Category category = categoryDAO.findById(1);

        assertTrue(category.isEnabled());
    }
}