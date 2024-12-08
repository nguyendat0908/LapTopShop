<%@page contentType="text/html" pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
        <%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    <meta name="description" content="Laptopshop" />
    <meta name="author" content="Nguyễn Hoàng Đạt" />
    <title>Update User - LapTopShop</title>
    <link href="/css/styles.css" rel="stylesheet" />
    <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

    <script>
        $(document).ready(() => {
            const avatarFile = $("#avatarFile");
            const orgAvatar = "${newUser.avatar}";
            if (orgAvatar) {
                const urlAvatar = "/images/avatar/" + orgAvatar;
                $("#avatarPreview").attr("src", urlAvatar);
                $("#avatarPreview").css({"display":"block"})
            }
            avatarFile.change(function(e){
                const imgURL = URL.createObjectURL(e.target.files[0]);
                $("#avatarPreview").attr("src", imgURL);
                $("#avatarPreview").css({"display":"block"})
            })
        })
    </script>
</head>

<body class="sb-nav-fixed">

    <!-- Tai su dung code header -->
    <jsp:include page="../layout/header.jsp"/>

    <div id="layoutSidenav">
        
        <!-- Tai su dung code sidebar -->
        <jsp:include page="../layout/sidebar.jsp"/>

        <div id="layoutSidenav_content">
            <main>
                <div class="container-fluid px-4">
                    <h1 class="mt-4">Users</h1>
                    <ol class="breadcrumb mb-4">
                        <li class="breadcrumb-item"><a href="/admin">Dashboard</a></li>
                        <li class="breadcrumb-item"><a href="/admin/user">User</a></li>
                        <li class="breadcrumb-item active">Update user</li>
                    </ol>
                    <div class="container mt-5">
                        <div class="row">
                            <div class="col-12 mx-auto">
                                <h4>Update a User</h4>
                                <hr />
                                <form:form method="post" action="/admin/user/update" modelAttribute="newUser" enctype="multipart/form-data">
                
                                    <div class="mb-3" style="display: none;">
                                      <label class="form-label">Id</label>
                                      <form:input type="text" class="form-control" path="id"/>
                                    </div>
                
                                    <div class="mb-3">
                                      <label class="form-label">Email</label>
                                      <form:input type="email" class="form-control" path="email" disabled="true"/>
                                    </div>
                            
                                    <div class="mb-3">
                                        <label class="form-label">Phone Number</label>
                                        <form:input type="tel" class="form-control" path="phone"/>
                                    </div>
                            
                                    <div class="mb-3">
                                        <label class="form-label">Full Name</label>
                                        <form:input type="text" class="form-control" path="fullName"/>
                                    </div>

                                    <!-- <div class="mb-3">
                                        <label class="form-label">Password</label>
                                        <input type="text" class="form-control"/>
                                    </div> -->
                            
                                    <div class="mb-3">
                                        <label class="form-label">Address</label>
                                        <form:input type="text" class="form-control" path="address"/>
                                    </div>

                                    <div class="col-12 col-md-6">
                                        <div class="mb-3">
                                            <label for="avatarFile" class="form-label">Avatar</label>
                                            <input class="form-control" type="file" id="avatarFile" accept=".png, .jpg, .jpeg" name="uploadFile"/>
                                        </div>
                                    </div>
                                    <div class="col-12 mb-3">
                                        <img style="max-height: 250px; display: none;" alt="Avatar preview" id="avatarPreview">
                                    </div>
                            
                                    <button type="submit" class="btn btn-warning">Update</button>
                                </form:form>
                            </div>
                        </div>
                
                    </div>
                </div>
            </main>
            <jsp:include page="../layout/footer.jsp"/>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
        crossorigin="anonymous"></script>
    <script src="js/scripts.js"></script>
</body>

</html>