<%@ include file="/WEB-INF/views/common/includes/include-datatables.jsp" %>
<style>
  div.dataTables_wrapper div.dataTables_filter {
    text-align: unset;
  }
</style>

<section class="content-header">
  <div class="container">
    <div class="row mb-2">
      <div class="col-18">
        <!-- ページタイトルを記入 -->
        <h3>アカウントリスト</h3>
      </div>
      <div class="col-18 text-right">
        <!-- ページタイトル右の余白 -->
        <a href="create?form" class="btn-button">新規登録</a>
      </div>
    </div>
  </div>
</section>
<section class="content">
  <div class="container">
    <t:messagesPanel />
    <!-- ここより下にメインコンテンツを記入 -->

    <div class="card">
      <div class="card-body">
        <div class="row">
          <div class-="co4">
            <label>氏名</label>
            <input type="text" id="col_filter_1" data-column="1" class="form-control dataTables_column_filter">
          </div>
        </div>

      </div>
    </div>


    <table id="acountlist" class="table-sm">
      <thead>
        <tr class="filter">
          <th></th>
          <th></th>
          <th></th>
          <th></th>
          <th></th>
          <th></th>
        </tr>
        <tr class="title">
          <th></th>
          <th>username</th>
          <th>firstName</th>
          <th>lastName</th>
          <th>email</th>
          <th>url</th>
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

        // 項目単位フィルタ用のInputフィールドを追加する。
        $('tr.filter th').each(function () {
          var idx = $(this).index();
          if (1 <= idx) {
            $(this).html('<input type="text" id="col_filter_' + idx + '" data-column="' + idx +
              '" class="dataTables_column_filter form-control" />');
          }
        });

        // DataTablesの設定する要素を指定
        var target = '#acountlist';

        var table = $(target).DataTable({

          // 一覧に表示する項目とJSONの項目にマッピング
          'columns': [{
              'data': 'username',
              className: ''
            },
            {
              'data': 'username',
              className: ''
            },
            {
              'data': 'firstName',
              className: ''
            },
            {
              'data': 'lastName',
              className: ''
            },
            {
              'data': 'email',
              className: ''
            },
            {
              'data': 'url',
              className: ''
            },
          ],

          // 主キー項目を指定
          'rowId': 'username',

          // 項目別の設定
          'columnDefs': [{
            'targets': 0,
            'checkboxes': {
              'selectRow': true,
              'orderable': false,
              'searchable': false
            },
          }, ],

          // 初期ソート
          'order': [
            [1, 'asc']
          ],

          // ボタンの表示
          'buttons': ['colvis', 'excel', 'stateClear', 'createnew'],

          // データロード後処理
          'initComplete': function (settings, json) {

            // ローディング画面のクリア
            $('#loading').hide();

            // グローバルフィルターでEnterキーでサーバに送信
            fnGlobalFilterOnReturn(table);

          },

        });

        // グローバルフィルタがENTERで動作する様に変更
        //        $(target).dataTable().fnFilterOnReturn();

        // 項目単位フィルタの追加
        addFieldFilter(table)

      });
</script>
