<%@ include file="/WEB-INF/views/common/includes/include-datatables.jsp" %>

<section class="content-header">
  <div class="container">
    <div class="row mb-">
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
    <div class="row mb-1">
      <a href="${pageContext.request.contextPath}/joho/create?form">＋新規お知らせ登録</a>
    </div>
    <div class="row mb-1">
      <div class="col-4">
        <label>タイトル</label>
        <input id="col_filter_1" data-column="3" class="dataTables_column_filter form-control">
      </div>
      <div class="col-4">
        <label>カテゴリ</label>
        <select id="col_category" class="dataTables_column_filter form-control">
          <option value=""> -- select -- </option>
          <c:forEach var="item" items="${CL_CATEGORY}">
            <option value="<c:out value=" ${item.key}" />">
            <c:out value="${item.value}" />
            </option>
          </c:forEach>
          <select>
      </div>
      <div class="col-4">
        <label>公開期間(自)</label>
        <div class="input-group" id="localdate001-input-group" data-target-input="nearest">
          <input class="form-control datetimepicker-input" data-target="#localdate001-input-group" />
          <div class="input-group-append" data-target="#localdate001-input-group" data-toggle="datetimepicker">
            <div class="input-group-text"><i class="fa fa-calendar fa-fw"></i></div>
          </div>
        </div>
      </div>
      <div class="col-4">
        <label>公開期間(至)</label>
        <div class="input-group" id="localdate002-input-group" data-target-input="nearest">
          <input class="form-control datetimepicker-input" data-target="#localdate002-input-group" />
          <div class="input-group-append" data-target="#localdate002-input-group" data-toggle="datetimepicker">
            <div class="input-group-text"><i class="fa fa-calendar fa-fw"></i></div>
          </div>
        </div>
      </div>
      <div class="col-4">
        <label>閲覧可能範囲</label>
        <select id="col_browse" class="dataTables_column_filter form-control">
          <option value=""> -- select -- </option>
          <c:forEach var="item" items="${CL_BROWSE}">
            <option value="<c:out value=" ${item.key}" />">
            <c:out value="${item.value}" />
            </option>
          </c:forEach>
          <select>
      </div>
      <div class="col-2">
        <label></label>
        <select id="col_pagesize" class="dataTables_column_filter form-control">
          <option value="10">10</option>
          <option value="20">20</option>
          <option value="50">50</option>
          <option value="100">100</option>
          <select>
      </div>
      <div class="col-14 text-right align-self-end">
        <button class="btn-button btn-primary">検索</button>
      </div>
    </div>

    <table id="johoList" class="table-sm table-striped">
      <thead>
        <tr class="title">
          <th>タイトル</th>
          <th>カテゴリ</th>
          <th>公開日付</th>
          <th>公開期限</th>
          <th>添付ファイル</th>
          <th>閲覧可能範囲</th>
          <th>操作</th>
        </tr>
      </thead>
      <tbody></tbody>
    </table>

    <!-- ここより上にメインコンテンツを記入 -->
  </div>
</section>

<!-- Page script -->
<script>
  $(document)
    .ready(
      function () {

        var table = $('#johoList').DataTable({

          // 一覧に表示する項目とJSONの項目にマッピング
          'columns': [{
              'data': 'kjTitle',
              className: ''
            },
            {
              'data': 'cdCategoryLabel',
              className: ''
            },
            {
              'data': 'tmRelease',
              className: ''
            },
            {
              'data': 'tmLimit',
              className: ''
            },
            {
              'data': 'dtFile',
              className: ''
            },
            {
              'data': 'cdBrowseLabel',
              className: ''
            },
            {
              'data': 'operation',
              className: ''
            },
          ],

          "searching": false,
          "ordering": false,

          // 項目別の設定
          'columnDefs': [
          ],

          // 初期ソート
          'order': [
            [3, 'asc'],
            [4, 'asc']
          ],

          'dom': "<'row'<'col-36'tr>>"
              + "<'row'<'col-15 d-inline-flex'i><'col-21'p>>",

          // ボタンの表示
          'buttons': ['colvis', 'stateClear', 'createnew'],

          // データロード後処理
          'initComplete': function (settings, json) {
            // グローバルフィルターの仕様変更(Enterキーでサーバに送信
            fnGlobalFilterOnReturn(table);
          },
        });

        // ページネーション後に画面トップに戻る
        table.on('page.dt', function () {
          $('html, body').animate({
            scrollTop: 0
          }, 300);
        });

        // 項目単位フィルタの追加
        //addFieldFilter(table)
        //addFieldFilter2(table)

      });
</script>
