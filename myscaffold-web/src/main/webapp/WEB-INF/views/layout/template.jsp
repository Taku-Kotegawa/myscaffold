<!DOCTYPE html>
<html class="no-js" lang="ja">

<head>
  <meta charset="utf-8" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
  <meta name="viewport" content="width=device-width" />
  <script type="text/javascript">
  </script>

<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
  integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<!--  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
  integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
 <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"
  integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script> -->

<!-- Select2 -->
<link href="https://cdn.jsdelivr.net/npm/select2@4.0.13/dist/css/select2.min.css" rel="stylesheet" />
<script src="https://cdn.jsdelivr.net/npm/select2@4.0.13/dist/js/select2.min.js"></script>


<style>
</style>
  <c:set var="titleKey">
    <tiles:insertAttribute name="title" ignore="true" />
  </c:set>
  <title>
    <spring:message code="${titleKey}" text="myscaffold" />
  </title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/AdminLTE/dist/css/AdminLTE.css">
  <!-- Font Awesome -->
  <link rel="stylesheet"
    href="${pageContext.request.contextPath}/resources/AdminLTE/plugins/fontawesome-free/css/all.min.css">
</head>

<body class="layout-top-nav layout-navbar-fixed layout-footer-fixed">
  <div class="wrapper">
    <tiles:insertAttribute name="header" />
    <div class="content-wrapper">
      <tiles:insertAttribute name="body" />
    </div>
  </div>
</body>

</html>
