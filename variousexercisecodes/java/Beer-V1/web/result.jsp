<%@ page import="java.util.*"%>
<html>
<body>
<%
List<String> styles = (List<String>)request.getAttribute("styles");
Iterator<String> it = styles.iterator();
while(it.hasNext()) {
	out.print("<br>try : "+ it.next());
}
%>
</body>
</html>