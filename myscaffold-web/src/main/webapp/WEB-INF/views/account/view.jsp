
    <div id="wrapper">
        <h1>Account Information</h1>
        <img src="${f:h(pageContext.request.contextPath)}/accounts/image" width="100" height="100"/>
        <table>
            <tr>
                <th>Username</th>
                <td>${f:h(account.username)}</td>
            </tr>
            <tr>
                <th>First name</th>
                <td>${f:h(account.firstName)}</td>
            </tr>
            <tr>
                <th>Last name</th>
                <td>${f:h(account.lastName)}</td>
            </tr>
            <tr>
                <th>E-Mail</th>
                <td>${f:h(account.email)}</td>
            </tr>
            <tr>
                <th>URL</th>
                <td>${f:link(account.url)}</td>
            </tr>
            <tr>
                <th>Profile</th>
                <td>${f:br(f:h(account.profile))}</td>
            </tr>
        </table>

        <a id="changePassword" href="${f:h(pageContext.request.contextPath)}/password?form">Change Password</a>
        <br>
        <a href="${f:h(pageContext.request.contextPath)}/">Back to Top</a>
    </div>
