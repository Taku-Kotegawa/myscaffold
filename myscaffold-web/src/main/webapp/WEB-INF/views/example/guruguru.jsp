<%@ page import="java.util.Enumeration"%>

<style>
table td:nth-child(2) {
  word-break : break-all;
}
</style>


<section class="content">
  <div class="container">
    <t:messagesPanel />
    <!-- ここより下にメインコンテンツを記入 -->

    <form:form action="guruguru" method="POST" modelAttribute="guruguruForm" autocomplete="off">

      <input type="submit" name="submit" value="送信ボタン">

      <table class="table">
        <tr>
          <th>ラベル</th>
          <th>フォーム</th>
          <th>エラー</th>
          <th>EL式</th>
        </tr>

        <tr>
          <td>
            <form:label path="input001">1. 文字列型</form:label>
          </td>
          <td>
            <form:input path="input001" />
          </td>
          <td>
            <form:errors path="input001" />
          </td>
          <td>
            ${guruguruForm.input001}
          </td>
        </tr>

        <tr>
          <td>
            <form:label path="input002">2. 整数型</form:label>
          </td>
          <td>
            <form:input path="input002" />
          </td>
          <td>
            <form:errors path="input002" />
          </td>
          <td>
            ${guruguruForm.input002}
          </td>
        </tr>

        <tr>
          <td>
            <form:label path="input003">3. 真偽値型</form:label>
          </td>
          <td>
            <form:input path="input003" />
          </td>
          <td>
            <form:errors path="input003" />
          </td>
          <td>
            ${guruguruForm.input003}
          </td>
        </tr>

      </table>

     <form:button name="confirm">Send</form:button>

    </form:form>

    <spring:message code="e.sl.fa.5001" arguments="aaa" />


    <!------------------------------------------------------------------------->
    <hr>

    <!-- Fromオブジェクトに格納された値を表示 -->
    <table class="table">
      <tr>
        <th>スクリプトレット</th>
        <td><% out.println(request.getAttribute("guruguruForm")); %></td>
      </tr>
      <tr>
        <th>EL式</th>
        <td>${guruguruForm}</td>
      </tr>
    </table>

    <!-- 暗黙オブジェクト(request)に格納されている値の項目一覧 -->
    <details>
      <summary>Request</summary>
      <table>
    <%
    Enumeration enum_request = request.getAttributeNames();
    while(enum_request.hasMoreElements()) {
      String key = (String)enum_request.nextElement();

      out.println("<tr>");
          out.println("<td>");
          out.println(key);
          out.println("</td>");
          out.println("<td>");
          out.println(request.getAttribute(key));
          out.println("</td>");
      out.println("</tr>");
    }
    %>
      </table>
    </details>

    <!-- 暗黙オブジェクト(session)に格納されている値の項目一覧 -->
    <details>
      <summary>Session</summary>
      <table>
        <%
    Enumeration enum_session = session.getAttributeNames();
    while(enum_session.hasMoreElements()) {
      String key = (String)enum_session.nextElement();

      out.println("<tr>");
          out.println("<td>");
          out.println(key);
          out.println("</td>");
          out.println("<td>");
          out.println(session.getAttribute(key));
          out.println("</td>");
      out.println("</tr>");
    }
    %>
      </table>
    </details>

    <!-- ここより上にメインコンテンツを記入 -->
  </div>
</section>
