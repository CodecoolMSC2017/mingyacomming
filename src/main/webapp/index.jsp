<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <!DOCTYPE HTML>
    <html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" type="text/css" href="css/style.css">
        <script src="js/scripts.js"> </script>
        <script src="js/zombies.js"> </script>
        <title> BrainStorm </title>
    </head>

    <body>
        <div id="menu_bar">
            <h1> BrainStorm </h1>
            <div id="user_tab">
                <div id="user_profile">
                    <div id="user_data"> </div>

                    <div id="user_profile_buttons">
                        <input type="button" id="logout_button" value="LOGOUT">
                    </div>
                </div>

                <jsp:include page="formcontainer.jsp"></jsp:include>
            </div>
        </div>

        <div id="content">
            <label id="home">Home</label>
            <label id="current_schedule"> > Schedules</label>
            <label id="current_day"> > Days </label>
            <label id="current_task"> > Tasks </label>
            <jsp:include page="tasks_page.jsp"></jsp:include>
            <jsp:include page="schedules_page.jsp"></jsp:include>
            <jsp:include page="days_page.jsp"></jsp:include>
        </div>

        <div id="messages"> </div>
        <canvas id="canvas"> Not supported! </canvas>
        <div id="game_button"> </div>
    </body>

    </html>