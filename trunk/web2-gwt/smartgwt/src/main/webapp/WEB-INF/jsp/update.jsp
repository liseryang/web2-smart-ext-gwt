<%@ page import="masterjava.model.SimpleTableEntity" %>
{response:{status:0,data:[
<%
    SimpleTableEntity entity = (SimpleTableEntity) request.getAttribute("data");
    out.print(entity.toJson());
%>
]}}
