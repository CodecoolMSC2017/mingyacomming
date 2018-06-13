
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.13/css/all.css">
    
    <c:url value="/css/style.css" var="styleCSS"/>
    <link rel="stylesheet" type="text/css" href="${styleCSS}">

    <c:url value="/js/searcher.js" var="searcherJS"/>
    <script src="${searcherJS}">  </script>

    <title> BrainStorm </title>
</head>

<body>
    <div id="content" guestData="${guestData}">
        <div id="search_days"> </div>
        <div id="search_slots"> </div>
    </div>

    <canvas id="canvas"> Not supported! </canvas>
    <div id="game_button"> </div>
</body>

</html>