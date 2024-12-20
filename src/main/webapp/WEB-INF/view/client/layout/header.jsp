<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- Navbar start -->
<div class="container-fluid fixed-top">
    <div class="container topbar bg-primary d-none d-lg-block">
        <div class="d-flex justify-content-between">
            <div class="top-info ps-2">
                <small class="me-3"><i class="fas fa-map-marker-alt me-2 text-secondary"></i> <a href="#" class="text-white">Tiền Hải, Thái Bình, Việt Nam</a></small>
                <small class="me-3"><i class="fas fa-envelope me-2 text-secondary"></i><a href="#" class="text-white">DatLeo@gmail.com</a></small>
            </div>
        </div>
    </div>
    <div class="container px-0">
        <nav class="navbar navbar-light bg-white navbar-expand-xl">
            <a href="/" class="navbar-brand"><h1 class="text-primary display-6">Laptop Shop</h1></a>
            <button class="navbar-toggler py-2 px-3" type="button" data-bs-toggle="collapse" data-bs-target="#navbarCollapse">
                <span class="fa fa-bars text-primary"></span>
            </button>
            <div class="collapse navbar-collapse bg-white justify-content-between mx-5" id="navbarCollapse">
                <div class="navbar-nav">
                    <a href="/" class="nav-item nav-link active">Trang Chủ</a>
                    <a href="/products" class="nav-item nav-link">Cửa hàng</a>
                    <a href="/contact" class="nav-item nav-link">Liên Hệ</a>
                </div>
                <div class="d-flex m-3 me-0">
                    <!--Nếu mà có thông tin đăng nhập người dùng thì mới hiển thị khối code bên trong-->
                    <c:if test="${not empty pageContext.request.userPrincipal}">
                        <a href="/cart" class="position-relative me-4 my-auto">
                        <i class="fa fa-shopping-bag fa-2x"></i>
                        <span
                            class="position-absolute bg-secondary rounded-circle d-flex align-items-center justify-content-center text-dark px-1"
                            style="top: -5px; left: 15px; height: 20px; min-width: 20px;" id="sumCart">${sessionScope.sum}</span>
                        </a>
                        <div class="dropdown my-auto">
                        <a href="#" class="dropdown" role="button" id="dropdownMenuLink"
                                data-bs-toggle="dropdown" aria-expanded="false" data-bs-toggle="dropdown" aria-expanded="false">
                                <i class="fas fa-user fa-2x"></i>
                        </a>
                        
                        <ul class="dropdown-menu dropdown-menu-end p-4" aria-labelledby="dropdownMenuLink">
                            <li class="d-flex align-items-center flex-column" style="min-width: 300px;">
                                <img style="width: 150px; height: 150px; border-radius: 50%; overflow: hidden;" src="/images/avatar/${sessionScope.avatar}" />
                                <div class="text-center my-3">
                                    <!--In ra thông tin người dùng khi đăng nhập thành công-->
                                    <c:out value="${sessionScope.fullName}"/>
                                </div>
                            </li>
                            <li><a class="dropdown-item" href="#">Quản lý tài khoản</a></li>
                            <li><a class="dropdown-item" href="/order-history">Lịch sử mua hàng</a></li>
                            <li>
                                <hr class="dropdown-divider">
                            </li>
                            <li>
                                <form method="post" action="/logout">
                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                    <button class="dropdown-item">Đăng xuất</button>
                                </form>
                            </li>
                        </ul>
                        </div>
                    </c:if>
                    <c:if test="${empty pageContext.request.userPrincipal}">
                        <a href="/login" class="position-relative me-4 my-auto a-login">
                            Đăng nhập
                        </a>
                    </c:if>
                </div>
            </div>
        </nav>
    </div>
</div>