<div id="wrapper">
    <span id="expiredMessage"> <t:messagesPanel />
    </span>
    <h1 id="title">Hello world!</h1>

    <p>Welcome ${f:h(account.firstName)} ${f:h(account.lastName)}</p>

    <c:if test="${!empty lastLoginDate}">
        <p id="lastLogin">Last login date is ${f:h(lastLoginDate)}.</p>
    </c:if>

    <div>
        <a id="info" href="${f:h(pageContext.request.contextPath)}/accounts">
            Account Information
        </a>
    </div>

    <sec:authorize url="/unlock">
    <div>
        <a id="unlock" href="${f:h(pageContext.request.contextPath)}/unlock?form">
            Unlock Account
        </a>
    </div>
    </sec:authorize>

    <form:form action="${f:h(pageContext.request.contextPath)}/logout">
        <button id="logout">Logout</button>
    </form:form>

    </div>

    <br><br><hr><br><br>

    <h2>Session</h2>
    <table>
    <%
    Enumeration enum_session = session.getAttributeNames();
    while(enum_session.hasMoreElements()) {
      String key = (String)enum_session.nextElement();

      out.println("<tr>");
          out.println("<td>");
          out.println(key);
          out.println("</td>");
          out.println("<td>");
          out.println(session.getAttribute(key));
          out.println("</td>");
      out.println("</tr>");
    }
    %>
    </table>


    <h2>Request</h2>
    <table>
    <%
    Enumeration enum_request = request.getAttributeNames();
    while(enum_request.hasMoreElements()) {
      String key = (String)enum_request.nextElement();

      out.println("<tr>");
          out.println("<td>");
          out.println(key);
          out.println("</td>");
          out.println("<td>");
          out.println(request.getAttribute(key));
          out.println("</td>");
      out.println("</tr>");
    }
    %>
    </table>

    <h2>application</h2>
    <table>
    <%
    Enumeration enum_application = application.getAttributeNames();
    while(enum_application.hasMoreElements()) {
      String key = (String)enum_application.nextElement();

      out.println("<tr>");
          out.println("<td>");
          out.println(key);
          out.println("</td>");
          out.println("<td>");
          out.println(application.getAttribute(key));
          out.println("</td>");
      out.println("</tr>");
    }
    %>
    </table>

    <h2>config</h2>
    <table>
    <%
    Enumeration enum_config = config.getInitParameterNames();
    while(enum_config.hasMoreElements()) {
      String key = (String)enum_config.nextElement();

      out.println("<tr>");
          out.println("<td>");
          out.println(key);
          out.println("</td>");
          out.println("<td>");
          out.println(config.getInitParameter(key));
          out.println("</td>");
      out.println("</tr>");
    }
    %>
    </table>

</div>
