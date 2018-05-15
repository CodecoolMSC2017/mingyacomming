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
            <h1>Tasks</h1>

            <div id="task_form">
                <h2> Create Task </h2>
                <p>
                    <label> Name </label>
                    <input type="text" id="create_task_name_field">
                </p>
                <p>
                    <label> Description </label>
                    <textarea rows="4" cols="20" id="create_task_description_field"></textarea>
                </p>
                <p>
                    <input type="button" id="create_task_button" value="CREATE TASK">
                </p>
            </div>

        </div>

        <div id="messages">
            <div class="message"> as</div>
            <div class="message"> as</div>
            <div class="message"> as</div>
            <div class="message"> as</div>
            <div class="message"> as</div>
        </div>
        <canvas id="canvas"> Not supported! </canvas>
        <div id="game_button"> </div>
    </body>

    </html>