<%@page import="java.util.Map"%>
<%@page language="java" contentType="text/html"%>
<%@page import="java.util.Enumeration"%>

<jsp:useBean id="dataManager" scope="application" class="eshop.model.DataManager"/>
<div class="menu"> 
  <div class="box">
    <div class="title">Quick Search</div>
    <p>Book Title/Author:</p>
    <form style="border: 0px solid; padding: 0; margin: 0;">
      <input type="hidden" name="action" value="search"/>
      <input id="text" type="text" name="keyword" size="15"/>
      <input id="submit" type="submit" value="Search"/>
      </form>
    </div>
  <div class="box">
    <div class="title">Categories</div>
<%
    Map<String, String> categories = dataManager.getCategories();
     for (String categoryId : categories.keySet()) {
        out.println("<p><a href=?action=selectCatalog&id="
        + categoryId.toString() + ">" + categories.get(categoryId) + "</a></p>"
        );
      }
  %>
    </div>
  </div>
