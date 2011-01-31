<%@ page import="masterjava.web2.model.SimpleTableEntity" %>
{response:{status:0,data:[
<%
    SimpleTableEntity entity = (SimpleTableEntity) request.getAttribute("data");
    out.print(entity.toJson());
%>
]}}
