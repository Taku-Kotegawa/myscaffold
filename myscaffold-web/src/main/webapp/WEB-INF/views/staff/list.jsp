<%@ include file="/WEB-INF/views/common/includes/include-datatables.jsp" %>

<section class="content-header">
  <div class="container">
    <div class="row mb-2">
      <div class="col-18">
        <!-- ページタイトルを記入 -->
        <h3>Page Title</h3>
      </div>
      <div class="col-18">
        <!-- ページタイトル右の余白 -->
      </div>
    </div>
  </div>
</section>
<section class="content">
  <div class="container">
    <t:messagesPanel panelClassName="callout" panelTypeClassPrefix="callout-" disableHtmlEscape="true" />
    <!-- ここより下にメインコンテンツを記入 -->
    <div class="row">
      <div class="col-36 text-right">
        <a id="gotoCreate" href="${pageContext.request.contextPath}/staff/create?form"
          class="btn btn-button mr-2">新規登録</a>
      </div>
    </div>


    <table>
      <thead>
        <td>ID</td>
        <td>従業員番号</td>
        <td>名前</td>
        <td>誕生日</td>
        <td>状態</td>
        <td>操作</td>
      </thead>
      <tbody>
        <c:forEach var="staff" items="${staffList}" varStatus="status">
          <tr>
            <td>${f:h(staff.id)}</td>
            <td>${f:h(staff.staffNo)}</td>
            <td>${f:h(staff.name)}</td>
            <td>${staff.birthday.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))}</td>
            <td>${CL_ENTITYSTATUS[f:h(staff.status)]}</td>
            <td>
              <a href="${pageContext.request.contextPath}/staff/${staff.id}">参照</a>
              | <a href="${pageContext.request.contextPath}/staff/${staff.id}/update?form">編集</a>
              | <a href="${pageContext.request.contextPath}/staff/create?form&copy=${staff.id}">複製</a>
            </td>
          </tr>
        </c:forEach>
      </tbody>
    </table>



    <!-- ここより上にメインコンテンツを記入 -->
  </div>
</section>
