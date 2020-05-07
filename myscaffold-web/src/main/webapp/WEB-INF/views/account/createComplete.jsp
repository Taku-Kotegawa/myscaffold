    <div id="wrapper">
        <h1>Welcome ${f:h(firstName)} ${f:h(lastName)}!</h1>
        <h3>Your initial password is <span id=password>${f:h(password)}</span>. Please login and change it.</h3>
        <a href="${f:h(pageContext.request.contextPath)}/login">Back to login page</a>
    </div>
