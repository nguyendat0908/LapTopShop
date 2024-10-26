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
    <title>Update Product - LapTopShop</title>
    <link href="/css/styles.css" rel="stylesheet" />
    <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

    <script>
        $(document).ready(() => {
            const imageFile = $("#imageFile");
            const orgImage = "${newProduct.image}";
            if (orgImage) {
                const urlImage = "/images/product/" + orgImage;
                $("#imagePreview").attr("src", urlImage);
                $("#imagePreview").css({"display":"block"})
            }
            imageFile.change(function(e){
                const imgURL = URL.createObjectURL(e.target.files[0]);
                $("#imagePreview").attr("src", imgURL);
                $("#imagePreview").css({"display":"block"})
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
                    <h1 class="mt-4">Products</h1>
                    <ol class="breadcrumb mb-4">
                        <li class="breadcrumb-item"><a href="/admin">Dashboard</a></li>
                        <li class="breadcrumb-item"><a href="/admin">Products</a></li>
                        <li class="breadcrumb-item active">Update product</li>
                    </ol>
                    <div class="container mt-5">
                        <div class="row">
                            <div class="col-12 mx-auto">
                                <h4>Update a Product</h4>
                                <hr />
                                <form:form method="post" action="/admin/product/update" modelAttribute="newProduct" class="row" enctype="multipart/form-data">
                                    <c:set var="errorName">
                                        <form:errors path="name" cssClass="invalid-feedback"/>
                                    </c:set>
                                    <c:set var="errorPrice">
                                        <form:errors path="price" cssClass="invalid-feedback"/>
                                    </c:set>
                                    <c:set var="errorDetailDesc">
                                        <form:errors path="detailDesc" cssClass="invalid-feedback"/>
                                    </c:set>
                                    <c:set var="errorShortDesc">
                                        <form:errors path="shortDesc" cssClass="invalid-feedback"/>
                                    </c:set>
                                    <c:set var="errorQuantity">
                                        <form:errors path="quantity" cssClass="invalid-feedback"/>
                                    </c:set>

                                    <div class="mb-3" style="display: none;">
                                        <label class="form-label">Id:</label>
                                        <form:input type="text" class="form-control" path="id" />
                                    </div>

                                    <div class="col-12 col-md-6 mx-auto">
                                        <div class="mb-3">
                                            <label class="form-label">Name</label>
                                            <form:input type="text" 
                                            class="form-control ${not empty errorName ? 'is-invalid' : ''}" path="name"/>
                                            ${errorName}
                                        </div>
                                    </div>
                                    <div class="col-12 col-md-6 mx-auto">
                                        <div class="mb-3">
                                            <label class="form-label">Price</label>
                                            <form:input type="number" 
                                            class="form-control ${not empty errorPrice ? 'is-invalid' : ''}" path="price"/>
                                            ${errorPrice}
                                        </div>      
                                    </div>
                                    <div class="col-12 mx-auto">
                                        <div class="mb-3">
                                            <label class="form-label">Detail description</label>
                                            <form:textarea type="text" class="form-control ${not empty errorDetailDesc ? 'is-invalid' : ''}" path="detailDesc"/>
                                            ${errorDetailDesc}
                                        </div>      
                                    </div>
                                    <div class="col-12 col-md-6 mx-auto">
                                        <div class="mb-3">
                                            <label class="form-label">Short description</label>
                                            <form:input type="text" class="form-control ${not empty errorShortDesc ? 'is-invalid' : ''}" path="shortDesc"/>
                                            ${errorShortDesc}
                                        </div>      
                                    </div>
                                    <div class="col-12 col-md-6 mx-auto">
                                        <div class="mb-3">
                                            <label class="form-label">Quantity</label>
                                            <form:input type="number" class="form-control ${not empty errorQuantity ? 'is-invalid' : ''}" path="quantity"/>
                                            ${errorQuantity}
                                        </div>      
                                    </div>
                                    <div class="col-12 col-md-6 mx-auto">
                                        <div class="mb-3">
                                            <label class="form-label">Factory</label>
                                            <form:select class="form-select" path="factory">
                                                <form:option value="APPLE">Apple (Macbook)</form:option>
                                                <form:option value="ASUS">Asus</form:option>
                                                <form:option value="LENOVO">Lenovo</form:option>
                                                <form:option value="DELL">Dell</form:option>
                                                <form:option value="LG">LG</form:option>
                                                <form:option value="ACER">Acer</form:option>
                                            </form:select>
                                        </div>
                                    </div>
                                    <div class="col-12 col-md-6 mx-auto">
                                        <div class="mb-3">
                                            <label class="form-label">Target</label>
                                            <form:select class="form-select" path="target">
                                                <form:option value="GAMING">Gaming</form:option>
                                                <form:option value="SINHVIEN-VANPHONG">Sinh viên - Văn phòng</form:option>
                                                <form:option value="THIET-KE-DO-HOA">Thiết kế đồ họa</form:option>
                                                <form:option value="MONG-NHE">Mỏng nhẹ</form:option>
                                                <form:option value="DOANH-NHAN">Doanh nhân</form:option>
                                            </form:select>
                                        </div>
                                    </div>
                                    <div class="col-12 col-md-6">
                                        <div class="mb-3">
                                            <label for="imageFile" class="form-label">Image</label>
                                            <input class="form-control" type="file" id="imageFile" accept=".png, .jpg, .jpeg" name="uploadFile"/>
                                        </div>
                                    </div>
                                    <div class="col-12 mb-3">
                                        <img style="max-height: 250px; display: none;" alt="Image preview" id="imagePreview">
                                    </div>
                                    <div class="col-12 mb-5">
                                        <button type="submit" class="btn btn-primary">Update</button>
                                    </div>
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