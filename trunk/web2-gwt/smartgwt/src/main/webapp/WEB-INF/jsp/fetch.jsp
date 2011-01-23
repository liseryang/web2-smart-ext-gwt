<%@ page import="masterjava.model.SimpleTableEntity" %>
<%@ page import="java.util.List" %>
{
response:{
status:0,
<%
    List<SimpleTableEntity> list = (List<SimpleTableEntity>) request.getAttribute("data");
    int length = list.size();
%>
totalRows:<%=length%>,
data:[
<%
    int i = 1;
    for (masterjava.model.SimpleTableEntity entity : list) {
        out.print(entity.toJson());
        if (i != length) {
            out.print(",");
        }
        i++;
    }
%>
]}}