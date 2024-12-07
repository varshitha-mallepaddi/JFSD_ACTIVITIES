<%@page import="com.klef.jfsd.model.Manager"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
Manager m = (Manager)session.getAttribute("manager");
if(m == null) {
	response.sendRedirect("managersessionfail");
	return;
}
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Manager Home</title>
<style>
    body {
            margin: 0;
            font-family: Arial, sans-serif;
            color: #333; /* Dark gray text */
            background: url('https://res.cloudinary.com/def2d6aqn/image/upload/v1733499602/background_apuvhf.webp') no-repeat center center fixed; /* Replace with your image URL */
            background-size: cover;
        } 
    {
            margin: 0;
            font-family: Arial, sans-serif;
            color: #333; /* Dark gray text */
            background: url('https://res.cloudinary.com/def2d6aqn/image/upload/v1733499602/background_apuvhf.webp') no-repeat center center fixed; /* Replace with your image URL */
            background-size: cover;
        } {
        margin: 0;
        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        background: linear-gradient(to bottom, #f8f9fa, #e9ecef);
    }

    .sidebar {
        width: 220px;
        background-color: #343a40;
        position: fixed;
        height: 100%;
        overflow: auto;
        text-align: center;
    }

    .sidebar img {
        width: 80%;
        margin: 20px 0;
    }

    .sidebar a {
        display: block;
        color: #ffffff;
        padding: 16px;
        text-decoration: none;
        font-size: 18px;
        transition: color 0.2s ease, background-color 0.2s ease;
    }

    .sidebar a:hover {
        background-color: #ffc107;
        color: #343a40;
    }

    .content {
        margin-left: 240px;
        padding: 20px;
        display: flex;
        justify-content: center;
        align-items: center;
        min-height: 100vh;
    }

    .profile-card {
        background: linear-gradient(145deg, #ffffff, #f1f3f5);
        padding: 30px;
        border-radius: 15px;
        box-shadow: 0 10px 20px rgba(0, 0, 0, 0.15);
        width: 400px;
        text-align: center;
    }

    .profile-card .profile-image {
        width: 120px;
        height: 120px;
        margin: 0 auto 20px;
        background-color: #6c757d;
        border-radius: 50%;
        display: flex;
        justify-content: center;
        align-items: center;
        color: white;
        font-size: 48px;
        font-weight: bold;
        text-transform: uppercase;
    }

    .profile-card h2 {
        font-size: 26px;
        color: #212529;
        margin-bottom: 10px;
    }

    .profile-card p {
        font-size: 16px;
        color: #495057;
        margin: 8px 0;
    }

    .profile-card .profile-info {
        margin-top: 20px;
        border-top: 1px solid #dee2e6;
        padding-top: 15px;
    }

    .profile-card .profile-info p {
        margin: 10px 0;
        display: flex;
        align-items: center;
        justify-content: center;
    }

    .profile-card .profile-info p i {
        margin-right: 8px;
        color: #007bff;
        font-size: 18px;
    }

    /* Responsive Design */
    @media screen and (max-width: 700px) {
        .sidebar {
            width: 100%;
            height: auto;
            position: relative;
        }
        .sidebar a {
            float: left;
            width: 100%;
        }
        .content {
            margin-left: 0;
        }
    }

    @media screen and (max-width: 400px) {
        .sidebar a {
            text-align: center;
            float: none;
        }
    }
</style>
<!-- Adding Font Awesome Icons -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body>

<div class="sidebar">
    <%@include file="managernavbar.jsp" %>
</div>

<!-- Main Content -->
<div class="content">
    <div class="profile-card">
        <div class="profile-image">
            <%=m.getName().substring(0, 1)%> <!-- Placeholder Initial -->
        </div>
        <h2>Welcome, <%=m.getName()%>!</h2>
        <div class="profile-info">
            <p><i class="fas fa-id-badge"></i><strong>ID:</strong> <%=m.getId()%></p>
            <p><i class="fas fa-venus-mars"></i><strong>Gender:</strong> <%=m.getGender()%></p>
            <p><i class="fas fa-building"></i><strong>Department:</strong> <%=m.getClub()%></p>
            <p><i class="fas fa-envelope"></i><strong>Email:</strong> <%=m.getEmail()%></p>
            <p><i class="fas fa-map-marker-alt"></i><strong>Location:</strong> <%=m.getLocation()%></p>
            <p><i class="fas fa-phone-alt"></i><strong>Contact:</strong> <%=m.getContact()%></p>
        </div>
    </div>
</div>

</body>
</html>