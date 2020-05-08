<!DOCTYPE html>
<html class="no-js">

<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta name="viewport" content="width=device-width" />
    <script type="text/javascript">
    </script>
    <c:set var="titleKey">
        <tiles:insertAttribute name="title" ignore="true" />
    </c:set>
    <title>
        <spring:message code="${titleKey}" text="myscaffold" />
    </title>
    <link rel="stylesheet"
        href="${pageContext.request.contextPath}/resources/AdminLTE/dist/css/AdminLTE-core.css">
    <link rel="stylesheet"
        href="${pageContext.request.contextPath}/resources/AdminLTE/dist/css/AdminLTE-pages.css">
</head>

<body class="login-page">
    <div class="login-box">
        <div class="login-logo">
            <a href="#"><b>My scaffold</b></a>
        </div>
        <!-- /.login-logo -->
        <div class="card">
            <div class="card-body">
                <p class="login-box-msg">Sign in to start your session</p>
                <div>
                    <c:if test="${param.containsKey('error')}">
                        <span id="loginError">
                            <t:messagesPanel messagesType="error"
                                messagesAttributeName="SPRING_SECURITY_LAST_EXCEPTION" />
                        </span>
                    </c:if>
                </div>
                <form:form
                    action="${f:h(pageContext.request.contextPath)}/login">
                    <div class="input-group mb-3">
                        <input type="text" id="username" name="username"
                            autocomplete="off" class="form-control"
                            placeholder="ユーザ名">
                    </div>
                    <div class="input-group mb-3">
                        <input type="password" id="password" name="password"
                            autocomplete="off" class="form-control"
                            placeholder="パスワード">
                    </div>
                    <div class="row">
                        <!-- /.col -->
                        <div class="col-8"></div>
                        <div class="col-4">
                            <input id="login" name="submit" type="submit"
                                value="login"
                                class="btn btn-primary btn-block" />
                        </div>
                        <!-- /.col -->
                    </div>
                </form:form>
                <br><br>

                <p class="mb-1">
                    <a id="create"
                        href="${f:h(pageContext.request.contextPath)}/accounts/create?form">Create
                        new account</a>
                </p>
                <p class="mb-0">
                    <a id="forgotten"
                        href="${f:h(pageContext.request.contextPath)}/reissue/create?form">I've
                        forgotten my password</a>
                </p>
            </div>
            <!-- /.login-card-body -->
        </div>
    </div>
    <!-- /.login-box -->
</body>

</html>
