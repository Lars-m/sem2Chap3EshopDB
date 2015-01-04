package eshop.model;

import java.sql.SQLException;
import java.sql.Statement;
import eshop.beans.CartItem;
import java.util.Map;

public class OrderDetailsPeer {
 
  public static void insertOrderDetails(Statement stmt, long orderId,
          Map<String, CartItem> shoppingCart) throws SQLException {
    //Get correct SQL for sequence nextval for either oracle or derby;
    String NEXT_VALUE = DataManager.DB_VENDOR.equals("oracle")? "SEQ_ORDERDETAILS.NEXTVAL" : "NEXT VALUE FOR SEQ_ORDERDETAILS";
    String sql;
    for (CartItem item : shoppingCart.values()) {
      //CartItem item = enumList.nextElement();
      sql = "insert into order_details (id,order_id, book_id, quantity, price, title, author) values" +
              "("+NEXT_VALUE+"," +  orderId + ","
              + item.getBookID() + "," + item.getQuantity() + ","
              + item.getPrice() + ",'" + item.getTitle() + "','"
              + item.getAuthor() + "')";
      stmt.executeUpdate(sql);
    }
  }
}
