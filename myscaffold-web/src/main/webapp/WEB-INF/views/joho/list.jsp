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
        <div class="row mb-3">
          <div class-="co4">
            <label>氏名</label>
            <input type="text" id="col_filter_3" data-column="3" class="dataTables_column_filter form-control">
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

        var table = $('#joholist').DataTable({

          // 一覧に表示する項目とJSONの項目にマッピング
          'columns': [
            {'data': 'kjTitle',              className: ''},
            {'data': 'cdCategoryLabel',      className: ''},
            {'data': 'tmRelease',            className: ''},
            {'data': 'tmLimit',              className: ''},
            {'data': 'dtFile',               className: ''},
            {'data': 'cdBrowseLabel',        className: ''},
            {'data': 'operation',            className: ''},
          ],

          // 項目別の設定
          'columnDefs': [
          ],

          // 初期ソート
          'order': [
            [3, 'asc'], [4, 'asc']
          ],

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
