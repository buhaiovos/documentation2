<%@page import="java.io.IOException"%>
<%@page import="java.io.FileReader"%>
<%@page import="java.io.BufferedReader"%>
<%@page import="java.io.BufferedReader"%>
<%@page import="java.util.TreeSet"%>
<%@page import="edu.cad.utils.databaseutils.DatabaseSwitcher"%>
<%@page import="org.hibernate.cfg.Configuration"%>
<%@page import="edu.cad.utils.databaseutils.DatabaseYears"%>
<%@page import="edu.cad.utils.Utils"%>
<%@page import="java.util.Set"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Вибір року БД</title>
        <!-- jTable Metro styles. -->
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/metro/blue/jtable.css" rel="stylesheet" type="text/css" />
        <link href="css/jquery-ui-1.10.3.custom.css" rel="stylesheet" type="text/css" />
        <!-- jTable script file. -->
        <script src="js/jquery-1.8.2.js" type="text/javascript"></script>
        <script src="js/jquery-ui-1.10.3.custom.js" type="text/javascript"></script>
        <script src="js/jquery.jtable.min.js" type="text/javascript"></script>
        <script src="js/jquery.jtable.editinline.js" type="text/javascript"></script>
        <link rel="stylesheet" href="styles.css" />
    </head>
    <body>
        <!-- MENU -->
        <header id="header"></header>
        <script type="text/javascript">
        $(function() {
            $( "#header" ).load( "menu.html" );
        });
        </script>
        <!-- MENU END -->
        <div id="dbSwitch">
            <form action="YearChangeController" id="form" method="get">
                <input type="hidden" name="action" value="switch">
                <select name="years" id="years"></select>
                <input type="submit" value="Вибрати">
            </form>

            <script type="text/javascript">
            $(document).ready( function() {                      // When HTML DOM "click" event is invoked on element with ID "somebutton", execute the following function...
                $.get("YearChangeController?action=list", function(responseJson) {                 // Execute Ajax GET request on URL of "someservlet" and execute the following function with Ajax response JSON...
                    var $select = $("#years");                           // Locate HTML DOM element with ID "someselect".
                    $select.find("option").remove();                          // Find all child elements with tag name "option" and remove them (just to prevent duplicate options when button is pressed again).
                    $.each(responseJson, function(index, element){         // Iterate over the JSON object.
                        $("<option>").val(element).text(element).appendTo($select); // Create HTML <option> element, set its value with currently iterated key and its text content with currently iterated item and finally append it to the <select>.
                    });
                });
            });
            </script>

            <%int currentYear = DatabaseSwitcher.getDatabaseYear();%>
            <p>Поточний рік БД: <b><%=currentYear%></b></p>
        </div>
        
    </body>
</html>