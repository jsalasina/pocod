<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
      <link rel="stylesheet" href="img/style.css">
    <title>Minesweeper</title>
  </head>
  <body style="margin: 20px;">
      <input type="button" name="start" value="Начать игру" id="start-game" style="height: 35px; width: 150px;">
      <p style="display: none; font-size: 20pt; color: green">Ты победил</p>
      <br>
      <div>
          <%--playing field is placed here--%>
      </div>
  <script src="js/indexScript.js"></script>

  </body>
</html>
