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



    <!-- ここより上にメインコンテンツを記入 -->
  </div>
</section>


      <div class="form-group row">
        <form:label path="username" cssClass="col-4 col-form-label">
          ユーザ名 *
        </form:label>
        <div class="col-10 form-control">
          ${f:h(accountCreateForm.username)}
          <form:hidden path="username" />
        </div>
      </div>
