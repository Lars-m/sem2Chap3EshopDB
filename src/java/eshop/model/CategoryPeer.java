package eshop.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;
import eshop.beans.Category;
import java.util.Map;

public class CategoryPeer {
  public static Category getCategoryById(DataManager dataManager,
      String categoryId) {
    Category category = null;
    Connection connection = dataManager.getConnection();
    if (connection != null) {
      try {
        Statement s = connection.createStatement();
        String sql = "select category_id, category_name from"
          + " categories where category_id=" + categoryId;
        try {
          try (ResultSet rs = s.executeQuery(sql)) {
            if (rs.next()) {
              category = new Category(rs.getInt(1), rs.getString(2));
              }
            }
          }
        finally { s.close(); }
        }
      catch (SQLException e) {
        System.out.println("Could not get categories: " + e.getMessage());
        }
      finally {
        dataManager.putConnection(connection);
        }
      }
    return category;
    }

  public static Map<String, String> getAllCategories(DataManager dataManager) {
    Map<String, String> categories = new Hashtable<String, String>();
    Connection connection = dataManager.getConnection();
    if (connection != null) {
      try {
        Statement s = connection.createStatement();
        String sql = "select category_id, category_name from categories";
        try {
          ResultSet rs = s.executeQuery(sql);
          try {
            while (rs.next()) {
              categories.put(rs.getString(1), rs.getString(2));
              }
            }
          finally { rs.close(); }
          }
        finally {s.close(); }
        }
      catch (SQLException e) {
        System.out.println("Could not get categories: " + e.getMessage());
        }
      finally {
        dataManager.putConnection(connection);
        }
      }
    return categories;
    }
  }
