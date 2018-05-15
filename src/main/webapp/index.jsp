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
            <div id="buttons">
                <input type="button" id="log_tab" value="LOGIN">
                <input type="button" id="reg_tab" value="REGISTER">
            </div>
            <div id="form_container">
                <div id="login_form">
                    <h2> Login </h2>
                    <p>
                        <label> Username </label>
                        <input type="text" id="log_username_field">
                    </p>
                    <p>
                        <label> Password </label>
                        <input type="password" id="log_password_field">
                    </p>
                    <p>
                        <input type="button" id="log_button" value="LOGIN">
                    </p>
                </div>

                <div id="register_form">
                    <h2> Register </h2>
                    <p>
                        <label> Username </label>
                        <input type="text" id="reg_username_field">
                    </p>
                    <p>
                        <label> Password </label>
                        <input type="password" id="reg_password_field">
                    </p>
                    <p>
                        <input type="button" id="reg_button" value="REGISTER">
                    </p>
                </div>
            </div>
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
        <div id="message">Hello</div>
    </div>
    <canvas id="canvas"> Not supported! </canvas>
    <div id="game_button"> </div>
</body>

</html>