<% response.setStatus(HttpServletResponse.SC_BAD_REQUEST); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Invalid Character Error!</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/app/css/styles.css">
</head>
<body>
    <div id="wrapper">
        <h1>Invalid Character Error!</h1>
        <div class="error">
            <c:if test="${!empty exceptionCode}">[${f:h(exceptionCode)}]</c:if>
        </div>
        <br>
        <br>
        <br>
        <br>
        <br>
        <br>
        <br>
        <br>
        <br>
        <br>
        <br>
        <br>
        <br>
        <br>
        <br>
        <br>
        <br>
        <br>
        <br>
        <br>
        <br>
        <br>
        <br>
        <br>
    </div>
</body>
</html>