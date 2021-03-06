<section class="content-header">
  <div class="container">
    <div class="row mb-2">
      <div class="col-18">
        <!-- ページタイトルを記入 -->
        <h3>パスワードの再発行</h3>
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

    <form:form action="${f:h(pageContext.request.contextPath)}/reissue/create" method="POST"
      modelAttribute="createReissueInfoForm" autocomplete="off" >

      <div class="form-group row">
        <form:label path="username" cssClass="col-4 col-form-label">
          ユーザ名 *
        </form:label>
        <div class="col-10">
          <form:input path="username" cssClass="form-control" cssErrorClass="form-control is-invalid" />
          <form:errors path="username" cssClass="invalid-feedback" />
        </div>
      </div>
      <br>
      <input id="submit" type="submit" value="再発行" class="btn-button" />
    </form:form>

    <!-- ここより上にメインコンテンツを記入 -->
  </div>
</section>

