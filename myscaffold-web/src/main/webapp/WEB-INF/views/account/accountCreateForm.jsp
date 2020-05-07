
    <div id="wrapper">
        <h1>Create Account</h1>
        <t:messagesPanel />
        <form:form
            action="${f:h(pageContext.request.contextPath)}/accounts/create"
            method="POST" modelAttribute="accountCreateForm"
            enctype="multipart/form-data">
            <table>
                <tr>
                    <th>Username</th>
                    <td><form:input path="username" autocomplete="off"/></td>
                    <td><form:errors path="username" cssClass="error-messages"/></td>
                </tr>
                <tr>
                    <th>First name</th>
                    <td><form:input path="firstName" autocomplete="off"/></td>
                    <td><form:errors path="firstName" cssClass="error-messages"/></td>
                </tr>
                <tr>
                    <th>Last name</th>
                    <td><form:input path="lastName" autocomplete="off"/></td>
                    <td><form:errors path="lastName" cssClass="error-messages"/></td>
                </tr>
                <tr>
                    <th>E-mail</th>
                    <td><form:input path="email" autocomplete="off"/></td>
                    <td><form:errors path="email" cssClass="error-messages"/></td>
                </tr>
                <tr>
                    <th>E-mail(Confirmation)</th>
                    <td><form:input path="confirmEmail" autocomplete="off"/></td>
                    <td><form:errors path="confirmEmail" cssClass="error-messages"/></td>
                </tr>
                <tr>
                    <th>URL</th>
                    <td><form:input path="url" autocomplete="off"/></td>
                    <td><form:errors path="url" cssClass="error-messages"/></td>
                </tr>
                <tr>
                    <th>Image</th>
                    <td><form:input type="file" path="image" /></td>
                    <td><form:errors path="image" cssClass="error-messages"/></td>
                </tr>
                <tr>
                    <th>Profile</th>
                    <td><form:textarea path="profile" autocomplete="off"/></td>
                    <td><form:errors path="profile" cssClass="error-messages"/></td>
                </tr>
            </table>

            <input type="submit" id="confirm" name="confirm"
                value="Go to confirmation page" />
        </form:form>
        <a href="${f:h(pageContext.request.contextPath)}/login">Back to login page</a>
    </div>
