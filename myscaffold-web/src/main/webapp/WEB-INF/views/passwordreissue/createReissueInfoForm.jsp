
    <div id="wrapper">
        <h1>Reissue password</h1>
        <t:messagesPanel />
        <form:form
            action="${f:h(pageContext.request.contextPath)}/reissue/create"
            method="POST" modelAttribute="createReissueInfoForm">
            <table>
                <tr>
                    <th><form:label path="username" cssErrorClass="error-label">Username</form:label>
                    </th>
                    <td><form:input path="username" cssErrorClass="error-input" /></td>
                    <td><form:errors path="username" cssClass="error-messages" /></td>
                </tr>
            </table>

            <input id="submit" type="submit" value="Reissue password" />
        </form:form>
    </div>
