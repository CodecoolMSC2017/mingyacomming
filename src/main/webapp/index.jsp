<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="google-signin-client_id" content="32266961436-e2umgtfhmqp639r5q2ob87vd7jk9f3mn.apps.googleusercontent.com">
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.13/css/all.css">
    <script src="js/slots.js">     </script>
    <script src="js/days.js">      </script>
    <script src="js/schedules.js"> </script>
    <script src="js/tasks.js">     </script>
    <script src="js/searcher.js">  </script>
    <script src="js/users.js">     </script>
    <script src="js/scripts.js">   </script>
    <script src="js/zombies.js">   </script>
    <script src="js/ee.js">        </script>
    <script src="https://apis.google.com/js/platform.js" async defer></script>
    <title> BrainStorm </title>
</head>

<body>
    <div id="spaceship"> </div>
    <div id="menu_bar">
        <h1>
            <i class="fas fa-skull" id="ee"> </i> BrainStorm </h1>
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
        <tab id="schedule_searcher"> Schedule Searcher </tab>
        <div id="tabs">
            <tab id="user_selector"> Users </tab>
            <tab id="my_tasks"> My Tasks </tab>
            <tab id="my_schedules"> My Schedules </tab>
            <tab id="current_schedule"> Schedule </tab>
            <tab id="current_day"> Day </tab>
        </div>
        <jsp:include page="user_selector_page.jsp"></jsp:include>
        <jsp:include page="schedule_searcher_page.jsp"></jsp:include>
        <jsp:include page="tasks_page.jsp"></jsp:include>
        <jsp:include page="schedules_page.jsp"></jsp:include>
        <jsp:include page="days_page.jsp"></jsp:include>
        <jsp:include page="slots_page.jsp"></jsp:include>
    </div>

    <div id="messages"> </div>
    <canvas id="canvas"> Not supported! </canvas>
    <div id="game_button"> </div>
</body>

</html>