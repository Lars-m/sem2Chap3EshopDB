package eshop.model;

import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import eshop.beans.Category;
import eshop.beans.Book;
import eshop.beans.Customer;
import eshop.beans.CartItem;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

public class DataManager {

  private String dbURL = "";
  private String dbUserName = "";
  private String dbPassword = "";
  public static  String DB_VENDOR;

  public void setDbURL(String dbURL) {
    this.dbURL = dbURL;
  }

  public String getDbURL() {
    return dbURL;
  }

  public void setDbUserName(String dbUserName) {
    this.dbUserName = dbUserName;
  }

  public String getDbUserName() {
    return dbUserName;
  }

  public void setDbPassword(String dbPassword) {
    this.dbPassword = dbPassword;
  }

  public String getDbPassword() {
    return dbPassword;
  }

  public Connection getConnection() {
    Connection conn = null;
    try {
         //TimeZone timeZone = TimeZone.getTimeZone("Asia/Kolkata");
         TimeZone timeZone = TimeZone.getTimeZone("Europe/Copenhagen");
     TimeZone.setDefault(timeZone);
      conn = DriverManager.getConnection(getDbURL(), getDbUserName(), getDbPassword());
    } catch (SQLException e) {
      System.out.println("Could not connect to DB: " + e.getMessage());
    }
    return conn;
  }

  public void putConnection(Connection conn) {
    if (conn != null) {
      try {
        conn.close();
      } catch (SQLException e) {
      }
    }
  }

  //---------- Category operations ----------
  public String getCategoryName(String categoryID) {
    Category category = CategoryPeer.getCategoryById(this, categoryID);
    return (category == null) ? null : category.getName();
  }

  public Map<String, String> getCategories() {
    return CategoryPeer.getAllCategories(this);
  }

  public Set<String> getCatIDs() {
    return CategoryPeer.getAllCategories(this).keySet();
  }

  //---------- Book operations ----------
  public ArrayList<Book> getSearchResults(String keyword) {
    return BookPeer.searchBooks(this, keyword);
  }

  public ArrayList<Book> getBooksInCategory(String categoryID) {
    return BookPeer.getBooksByCategory(this, categoryID);
  }

  public Book getBookDetails(String bookID) {
    return BookPeer.getBookById(this, bookID);
  }

  //---------- Order operations ----------
  public long insertOrder(Customer customer, Map<String, CartItem> shoppingCart) {
    long returnValue = 0L;
    long orderId = System.currentTimeMillis();
    Connection connection = getConnection();
    if (connection != null) {
      Statement stmt = null;
      try {
        connection.setAutoCommit(false);
        stmt = connection.createStatement();
        try {
          OrderPeer.insertOrder(stmt, orderId, customer);
          OrderDetailsPeer.insertOrderDetails(stmt, orderId, shoppingCart);
          try {
            stmt.close();
          } finally {
            stmt = null;
          }
          connection.commit();
          returnValue = orderId;
        } catch (SQLException e) {
          System.out.println("Could not insert order: " + e.getMessage());
          try {
            connection.rollback();
          } catch (SQLException ee) {
          }
        }
      } catch (SQLException e) {
        System.out.println("Could not insert order: " + e.getMessage());
      } finally {
        if (stmt != null) {
          try {
            stmt.close();
          } catch (SQLException e) {
          }
        }
        putConnection(connection);
      }
    }
    return returnValue;
  }
}
