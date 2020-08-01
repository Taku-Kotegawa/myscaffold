<style>
  .form-control__view {
    box-shadow: unset;
    background-color: unset;
  }
</style>

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
    <t:messagesPanel />
    <!-- ここより下にメインコンテンツを記入 -->

    <form:form modelAttribute="staffForm" enctype="multipart/form-data" autocomplete="off">

      <!-- EnterキーによるPOSTを無効化 -->
      <input type="submit" disabled style="display:none" />

      <!-- 隠しフィールド -->
      <form:hidden path="id" />
      <form:hidden path="version" />

      <!-- 操作ボタン -->
      <div class="row">
        <div class="col-12">
          <c:if test="${staff.status == 0 || staff.status == 2}">
            <div class="col-36 pt-3">
              <b>状態: ${CL_ENTITYSTATUS[f:h(staff.status)]}</b>
            </div>
          </c:if>
        </div>
        <div class="col-24 text-right">
          <c:if test="${buttonState.gotoList__view}">
            <a id="gotoList" name="gotoList" href="${pageContext.request.contextPath}/staff/list"
              class="btn btn-button mr-2" <c:if test="${buttonState.gotoList__disabled}">disabled</c:if> >一覧に戻る</a>
          </c:if>
          <c:if test="${buttonState.view__view}">
            <a id="view" name="view" disabled="${buttonState.view__disabled}" class="btn btn-button mr-2"
              href="${pageContext.request.contextPath}/staff/${staff.id}" <c:if
              test="${buttonState.view__disabled}">disabled</c:if> >参照</a>
          </c:if>
          <c:if test="${buttonState.gotoUpdate__view}">
            <a id="gotoUpdate" name="gotoUpdate" href="${pageContext.request.contextPath}/staff/${staff.id}/update?form"
              class="btn btn-button mr-2" <c:if test="${buttonState.gotoUpdate__disabled}">disabled</c:if> >編集</a>
          </c:if>
          <c:if test="${buttonState.saveAsDraft__view}">
            <button id="saveAsDraft" name="saveAsDraft" value="true" type="submit" class="btn btn-button mr-2" <c:if
              test="${buttonState.saveAsDraft__disabled}">disabled</c:if>
          >下書き保存</button>
          </c:if>
          <c:if test="${buttonState.cancelDraft__view}">
            <button id="cancelDraft" name="cancelDraft" value="true" type="submit" class="btn btn-button mr-2" <c:if
              test="${buttonState.cancelDraft__disabled}">disabled</c:if>
          >下書きの取消</button>
          </c:if>
          <c:if test="${buttonState.save__view}">
            <button id="save" name="save" type="submit" class="btn btn-button mr-2" <c:if
              test="${buttonState.save__disabled}">disabled</c:if>>保存</button>
          </c:if>
          <c:if test="${buttonState.invalid__view}">
            <a id="invalid" name="invalid" disabled="${buttonState.invalid__disabled}" class="btn btn-button mr-2"
              href="${pageContext.request.contextPath}/staff/${staff.id}/invalid" <c:if
              test="${buttonState.invalid__disabled}">disabled</c:if> >無効化</a>
          </c:if>
          <c:if test="${buttonState.delete__view}">
            <a id="delete" name="delete" disabled="${buttonState.delete__disabled}" class="btn btn-button mr-2"
              href="${pageContext.request.contextPath}/staff/${staff.id}/delete" <c:if
              test="${buttonState.delete__disabled}">disabled</c:if> >削除(復元不能)</a>
          </c:if>
        </div>
      </div>

      <hr />

      <div class="row">
        <div class="col-12">
          <!-- ラベル -->
          <c:if test="${fieldState.staffNo__input || fieldState.staffNo__view}">
            <form:label path="staffNo">従業員番号</form:label>
          </c:if>
          <!-- 入力 -->
          <c:if test="${fieldState.staffNo__input}">
            <form:input path="staffNo" cssClass="form-control" cssErrorClass="form-control is-invalid"
              disabled="${fieldState.staffNo__disabled}" readonly="${fieldState.staffNo__readonly}" />
            <form:errors path="staffNo" cssClass="invalid-feedback" />
          </c:if>
          <!-- 隠しフィールド-->
          <c:if test="${fieldState.staffNo__hidden}">
            <form:hidden path="staffNo" />
          </c:if>
          <!-- 参照用-->
          <c:if test="${fieldState.staffNo__view}">
            <div class="form-control form-control__view">
              ${f:h(staff.staffNo)}
            </div>
          </c:if>
        </div>
      </div>
      <div class="row">
        <div class="col-12">
          <!-- ラベル -->
          <c:if test="${fieldState.name__input || fieldState.name__view}">
            <form:label path="name">氏名</form:label>
          </c:if>
          <!-- 入力 -->
          <c:if test="${fieldState.staffNo__input}">
            <form:input path="name" cssClass="form-control" cssErrorClass="form-control is-invalid"
              disabled="${fieldState.name__disabled}" readonly="${fieldState.name__readonly}" />
            <form:errors path="name" cssClass="invalid-feedback" />
          </c:if>
          <!-- 隠しフィールド-->
          <c:if test="${fieldState.name__hidden}">
            <form:hidden path="name" />
          </c:if>
          <!-- 参照用-->
          <c:if test="${fieldState.name__view}">
            <div class="form-control form-control__view">
              ${f:h(staff.name)}
            </div>
          </c:if>
        </div>
      </div>
      <div class="row">
        <div class="col-12">
          <!-- ラベル -->
          <c:if test="${fieldState.birthday__input || fieldState.birthday__view}">
            <form:label path="birthday">生年月日</form:label>
          </c:if>
          <!-- 入力 -->
          <c:if test="${fieldState.birthday__input}">
            <div class="input-group datetime" id="birthday-input-group" data-target-input="nearest">
              <form:input path="birthday" cssClass="form-control datetimepicker-input"
                cssErrorClass="form-control datetimepicker-input is-invalid" data-target="#birthday-input-group"
                disabled="${fieldState.birthday__disabled}" readonly="${fieldState.birthday__readonly}" />
              <div class="input-group-append" data-target="#birthday-input-group" data-toggle="datetimepicker">
                <div class="input-group-text"><i class="far fa-clock fa-fw"></i></div>
              </div>
            </div>
            <form:errors path="birthday" cssClass="invalid-feedback" />
          </c:if>
          <!-- 隠しフィールド-->
          <c:if test="${fieldState.birthday__hidden}">
            <form:hidden path="birthday" />
          </c:if>
          <!-- 参照用-->
          <c:if test="${fieldState.birthday__view}">
            <div class="form-control form-control__view">
              ${staff.birthday.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))}
            </div>
          </c:if>
        </div>
      </div>
    </form:form>

    <!-- ここより上にメインコンテンツを記入 -->
  </div>
</section>

<br>
<br>
<br>
<br>
<%@ include file="/WEB-INF/views/common/includes/include-debug.jsp" %>
