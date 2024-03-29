<%@ page contentType="text/html;charset=UTF-8" %>

<div id="styleSwitcherDropdown"
     class="hs-unfold-content sidebar sidebar-bordered sidebar-box-shadow"
     style="width: 35rem">

    <div class="card card-lg border-0 h-100">
        <div class="card-footer">
            <div class="row gx-2">
                <div class="col">
                    <button type="button" id="js-builder-reset" class="btn btn-block btn-lg btn-white">
                        <i class="tio-restore"></i> Reset
                    </button>
                </div>
                <div class="col">
                    <button type="button" id="js-builder-preview" class="btn btn-block btn-lg btn-primary">
                        <i class="tio-visible"></i> Preview
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>

<div id="headerMain" class="d-none">
    <header id="header"
            class="navbar navbar-expand-lg navbar-fixed navbar-height navbar-flush navbar-container navbar-bordered">
        <div class="navbar-nav-wrap">
            <div class="navbar-nav-wrap-content-left">
                <button type="button" class="js-navbar-vertical-aside-toggle-invoker close mr-3">
                    <i class="tio-first-page navbar-vertical-aside-toggle-short-align" data-toggle="tooltip"
                       data-placement="right" title="Collapse">
                    </i>
                    <i class="tio-last-page navbar-vertical-aside-toggle-full-align"
                       data-template=
                               '<div class="tooltip d-none d-sm-block" role="tooltip">
                                    <div class="arrow"></div><div class="tooltip-inner"></div>
                               </div>'
                       data-toggle="tooltip" data-placement="right" title="Expand"></i>
                </button>

                <div class="d-none d-md-block mt-3">
                    <form class="position-relative">
                        <div class="input-group input-group-merge input-group-borderless input-group-hover-light navbar-input-group">
                            <div class="input-group-prepend">
                                <div class="input-group-text">
                                    <i class="tio-search"></i>
                                </div>
                            </div>
                            <input type="search" class="js-form-search form-control" placeholder="Tìm kiếm..."/>
                        </div>
                    </form>
                </div>
            </div>

            <div class="navbar-nav-wrap-content-right">
                <ul class="navbar-nav align-items-center flex-row">
                    <li class="nav-item">
                        <div class="hs-unfold">
                            <a class="js-hs-unfold-invoker navbar-dropdown-account-wrapper" href="#"
                               data-hs-unfold-options='{
                               "target": "#accountNavbarDropdown",
                               "type": "css-animation"}'>

                                <%-- Có ảnh cá nhân bên ngoài --%>
                                <div class="avatar avatar-sm avatar-circle">
                                    <img class="avatar-img" src="assets\img\160x160\img6.jpg" alt="Image Description"/>
                                    <span class="avatar-status avatar-sm-status avatar-status-success"></span>
                                </div>

                                <%-- Áp dụng cho không có ảnh cá nhân bên ngoài --%>
                                <%--                                <div class="avatar avatar-sm avatar-dark avatar-circle">--%>
                                <%--                                    <span class="avatar-initials">HS</span>--%>
                                <%--                                </div>--%>
                            </a>

                            <div id="accountNavbarDropdown"
                                 class="hs-unfold-content dropdown-unfold dropdown-menu dropdown-menu-right navbar-dropdown-menu navbar-dropdown-account"
                                 style="width: 16rem">
                                <%-- Tài khoản có ảnh cá nhân --%>
                                <div class="dropdown-item-text">
                                    <div class="media align-items-center">
                                        <div class="avatar avatar-sm avatar-circle mr-2">
                                            <img class="avatar-img" src="assets\img\160x160\img6.jpg"
                                                 alt="Image Description"/>
                                        </div>
                                        <div class="media-body">
                                            <span class="card-title h5">Mark Williams</span>
                                            <span class="card-text">mark@example.com</span>
                                        </div>
                                    </div>
                                </div>

                                <div class="dropdown-divider"></div>

                                <a class="dropdown-item" href="#">
                                    <span class="text-truncate pr-2">
                                        Hồ sơ
                                    </span>
                                </a>
                                <a class="dropdown-item" href="#">
                                    <span class="text-truncate pr-2">Đăng xuất</span>
                                </a>

                                <div class="dropdown-divider"></div>

                                <%-- Tài khoản không có ảnh cá nhân --%>
                                <%--                                <a class="dropdown-item" href="#">--%>
                                <%--                                    <div class="media align-items-center">--%>
                                <%--                                        <div class="avatar avatar-sm avatar-dark avatar-circle mr-2">--%>
                                <%--                                            <span class="avatar-initials">HS</span>--%>
                                <%--                                        </div>--%>
                                <%--                                        <div class="media-body">--%>
                                <%--                                            <span class="card-title h5">--%>
                                <%--                                                Htmlstream--%>
                                <%--                                                <span class="badge badge-primary badge-pill text-uppercase ml-1">--%>
                                <%--                                                    Admin--%>
                                <%--                                                </span>--%>
                                <%--                                            </span>--%>
                                <%--                                            <span class="card-text">hs.example.com</span>--%>
                                <%--                                        </div>--%>
                                <%--                                    </div>--%>
                                <%--                                </a>--%>
                            </div>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
    </header>
</div>

<div id="headerFluid" class="d-none"></div>
<div id="headerDouble" class="d-none"></div>

<%-- Thanh menu bên cạnh --%>
<div id="sidebarMain" class="d-none">
    <aside class="js-navbar-vertical-aside navbar navbar-vertical-aside navbar-vertical navbar-vertical-fixed navbar-expand-xl navbar-bordered  ">
        <div class="navbar-vertical-container">
            <div class="navbar-vertical-footer-offset">
                <div class="navbar-brand-wrapper justify-content-between">
                    <!-- Logo  thanh menu -->
                    <a class="navbar-brand" href="index.html" aria-label="Front">
                        <img class="navbar-brand-logo" src="assets\svg\logos\logo.svg" alt="Logo">
                        <img class="navbar-brand-logo-mini" src="assets\svg\logos\logo-short.svg" alt="Logo">
                    </a>

                    <button type="button"
                            class="js-navbar-vertical-aside-toggle-invoker navbar-vertical-aside-toggle btn btn-icon btn-xs btn-ghost-dark">
                        <i class="tio-clear tio-lg"></i>
                    </button>
                </div>

                <div class="navbar-vertical-content">
                    <ul class="navbar-nav navbar-nav-lg nav-tabs">
                        <li class="navbar-vertical-aside-has-menu">
                            <a class="js-navbar-vertical-aside-menu-link nav-link" href="${pageContext.request.contextPath}/backend/">
                                <i class="tio-home-vs-1-outlined nav-icon"></i>
                                <span class="navbar-vertical-aside-mini-mode-hidden-elements text-truncate">Trang chủ</span>
                            </a>
                        </li>
                        <li class="navbar-vertical-aside-has-menu">
                            <a class="js-navbar-vertical-aside-menu-link nav-link" href="list_employees">
                                <i class="tio-users-switch nav-icon"></i>
                                <span class="navbar-vertical-aside-mini-mode-hidden-elements text-truncate">Nhân viên</span>
                            </a>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </aside>
</div>

<div id="sidebarCompact" class="d-none"></div>