<%--
  Created by IntelliJ IDEA.
  User: ionut
  Date: 21/10/2021
  Time: 10:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page session="false"%>
<%@ page import="model.mo.Utente" %>
<%@ page import="model.mo.TestataOrdine" %>

<%--
    boolean loggedOn = (Boolean) request.getAttribute("loggedOn");
    Utente loggedUser = (Utente) request.getAttribute("loggedUser");
    TestataOrdine testataOrdine = (TestataOrdine) request.getAttribute("testataOrdine");
    String applicationMessage = (String) request.getAttribute("applicationMessage");
    String menuActiveLink = "GestioneOrdini";
    final String stati[] = new String[6];
    stati[0] = "in fase di elaborazione";
    stati[1] = "stato preso in carico";
    stati[2] = "stato consegnato al corriere";
    stati[3] = "in viaggio";
    stati[4] = "in consegna";
    stati[5] = "stato consegnato";
--%>

<html>
    <head>
        <%--@include file="/include/htmlHead.inc"--%>
        <style>
          table {
            border-collapse:collapse
          }
          td, th {
            border:1px solid #ddd;
            padding:8px;
          }
          .left{
            float: left;
          }
          .dropbtn {
            background-color: #04AA6D;
            color: white;
            padding: 16px;
            font-size: 16px;
            border: none;
            cursor: pointer;
          }

          .dropbtn:hover, .dropbtn:focus {
            background-color: #3e8e41;
          }

          .dropdown {
            float: right;
            position: relative;
            display: inline-block;
          }

          .dropdown-content {
            display: none;
            position: absolute;
            background-color: #f1f1f1;
            min-width: 160px;
            overflow: auto;
            box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
            right: 0;
            z-index: 1;
          }

          .dropdown-content a {
            color: black;
            padding: 12px 16px;
            text-decoration: none;
            display: block;
          }

          .dropdown a:hover {background-color: #ddd;}

          .show {display: block;}
        </style>
        <script>
            /* When the user clicks on the button,
            toggle between hiding and showing the dropdown content */
            function myFunction() {
                document.getElementById("myDropdown").classList.toggle("show");
            }

            // Close the dropdown if the user clicks outside of it
            window.onclick = function(event) {
                if (!event.target.matches('.dropbtn')) {
                    var dropdowns = document.getElementsByClassName("dropdown-content");
                    var i;
                    for (i = 0; i < dropdowns.length; i++) {
                        var openDropdown = dropdowns[i];
                        if (openDropdown.classList.contains('show')) {
                            openDropdown.classList.remove('show');
                        }
                    }
                }
            }
        </script>
    </head>
    <body>
        <%--@include file="/include/header.inc"--%>
        <div class="dropdown">
            <button onclick="myFunction()" class="dropbtn">Dropdown</button>
            <div id="myDropdown" class="dropdown-content">
                <a href="#home">Home</a>
                <a href="#about">About</a>
                <a href="#contact">Contact</a>
            </div>
        </div>

    </body>
        <%--@include file="/include/footer.inc"--%>
</html>
