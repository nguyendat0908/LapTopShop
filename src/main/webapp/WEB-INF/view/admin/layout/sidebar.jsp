<%@page contentType="text/html" pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div id="layoutSidenav_nav">
    <nav class="sb-sidenav accordion sb-sidenav-dark" id="sidenavAccordion">
      <div class="sb-sidenav-menu">
        <div class="nav">
          <div class="sb-sidenav-menu-heading">Features</div>
          <a class="nav-link" href="/admin">
            <div class="sb-nav-link-icon">
              <i class="fas fa-tachometer-alt"></i>
            </div>
            Dashboard
          </a>

          <a class="nav-link" href="/admin/user">
            <div class="sb-nav-link-icon">
              <i class="fas fa-tachometer-alt"></i>
            </div>
            User
          </a>

          <a class="nav-link" href="/admin/product">
            <div class="sb-nav-link-icon">
              <i class="fas fa-tachometer-alt"></i>
            </div>
            Product
          </a>

          <a class="nav-link" href="/admin/order">
            <div class="sb-nav-link-icon">
              <i class="fas fa-tachometer-alt"></i>
            </div>
            Order
          </a>


          <a
            class="nav-link collapsed"
            href="#"
            data-bs-toggle="collapse"
            data-bs-target="#collapsePages"
            aria-expanded="false"
            aria-controls="collapsePages"
          >
            <div class="sb-nav-link-icon">
              <i class="fas fa-book-open"></i>
            </div>
            Pages
            <div class="sb-sidenav-collapse-arrow">
              <i class="fas fa-angle-down"></i>
            </div>
          </a>
          <div
            class="collapse"
            id="collapsePages"
            aria-labelledby="headingTwo"
            data-bs-parent="#sidenavAccordion"
          >
            <nav
              class="sb-sidenav-menu-nested nav accordion"
              id="sidenavAccordionPages"
            >
              <a
                class="nav-link collapsed"
                href="#"
                data-bs-toggle="collapse"
                data-bs-target="#pagesCollapseAuth"
                aria-expanded="false"
                aria-controls="pagesCollapseAuth"
              >
                Authentication
                <div class="sb-sidenav-collapse-arrow">
                  <i class="fas fa-angle-down"></i>
                </div>
              </a>
              <div
                class="collapse"
                id="pagesCollapseAuth"
                aria-labelledby="headingOne"
                data-bs-parent="#sidenavAccordionPages"
              >
                <nav class="sb-sidenav-menu-nested nav">
                  <a class="nav-link" href="/login">Login</a>
                  <a class="nav-link" href="/register">Register</a>
                  <a class="nav-link" href="#"
                    >Forgot Password</a
                  >
                </nav>
              </div>
              <a
                class="nav-link collapsed"
                href="#"
                data-bs-toggle="collapse"
                data-bs-target="#pagesCollapseError"
                aria-expanded="false"
                aria-controls="pagesCollapseError"
              >
                Error
                <div class="sb-sidenav-collapse-arrow">
                  <i class="fas fa-angle-down"></i>
                </div>
              </a>
              <div
                class="collapse"
                id="pagesCollapseError"
                aria-labelledby="headingOne"
                data-bs-parent="#sidenavAccordionPages"
              >
                <nav class="sb-sidenav-menu-nested nav">
                  <a class="nav-link" href="/error/401">401 Page</a>
                  <a class="nav-link" href="/error/404">404 Page</a>
                  <a class="nav-link" href="/error/500">500 Page</a>
                </nav>
              </div>
              <a class="nav-link collapsed" href="/"> HomePage </a>
            </nav>
          </div>
        </div>
      </div>
      <div class="sb-sidenav-footer">
        <div class="small">Logged in as:</div>
        <div class="" style="color: rgba(255, 255, 255, 0.555);">
          <!--In ra thông tin người dùng khi đăng nhập thành công-->
          <c:out value="${pageContext.request.userPrincipal.name}"/>
      </div>
      </div>
    </nav>
  </div>