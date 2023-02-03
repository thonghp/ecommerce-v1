<%@ page contentType="text/html;charset=UTF-8" %>

<html lang="en">

<head>
    <!-- Title -->
    <title>Admin - Trang chủ</title>
    <jsp:include page="css.jsp"/>
</head>

<body class="footer-offset">

<jsp:include page="header.jsp"/>

<main id="content" role="main" class="main pointer-event">
    <div class="content container-fluid">
        <div class="page-header">
            <div class="row align-items-center">
                <div class="col-sm mb-2 mb-sm-0">
                    <h1 class="page-header-title">Chào mừng ABC trở lại trang quản lý VTV</h1>
                </div>
            </div>

            <div class="row gx-2 gx-lg-3">
                <div class="col-sm-6 col-lg-3 mb-3 mb-lg-5">
                    <a class="card card-hover-shadow h-100" href="list_employees">
                        <div class="card-body">
                            <h6 class="card-subtitle text-center">Nhân viên</h6>
                            <div class="card-title h2 text-center">100</div>
                        </div>
                    </a>
                </div>

                <div class="col-sm-6 col-lg-3 mb-3 mb-lg-5">
                    <a class="card card-hover-shadow h-100" href="list_customers">
                        <div class="card-body">
                            <h6 class="card-subtitle text-center">Khách hàng</h6>
                            <div class="card-title h2 text-center">100</div>
                        </div>
                    </a>
                </div>

                <div class="col-sm-6 col-lg-3 mb-3 mb-lg-5">
                    <a class="card card-hover-shadow h-100" href="list_products">
                        <div class="card-body">
                            <h6 class="card-subtitle text-center">Sản phẩm</h6>
                            <div class="card-title h2 text-center">100</div>
                        </div>
                    </a>
                </div>

                <div class="col-sm-6 col-lg-3 mb-3 mb-lg-5">
                    <a class="card card-hover-shadow h-100" href="list_orders">
                        <div class="card-body">
                            <h6 class="card-subtitle text-center">Đơn hàng</h6>
                            <div class="card-title h2 text-center">100</div>
                        </div>
                    </a>
                </div>
            </div>
        </div>

        <div class="footer">
            <div class="row justify-content-between align-items-center">
                <div class="col">
                    <p class="font-size-sm mb-0">&copy; Front. <span
                            class="d-none d-sm-inline-block">2020 Htmlstream.</span></p>
                </div>
                <div class="col-auto">
                    <div class="d-flex justify-content-end">
                        <!-- List Dot -->
                        <ul class="list-inline list-separator">
                            <li class="list-inline-item">
                                <a class="list-separator-link" href="#">FAQ</a>
                            </li>

                            <li class="list-inline-item">
                                <a class="list-separator-link" href="#">License</a>
                            </li>

                            <li class="list-inline-item">
                                <!-- Keyboard Shortcuts Toggle -->
                                <div class="hs-unfold">
                                    <a class="js-hs-unfold-invoker btn btn-icon btn-ghost-secondary rounded-circle"
                                       href="javascript:;" data-hs-unfold-options='{
                              "target": "#keyboardShortcutsSidebar",
                              "type": "css-animation",
                              "animationIn": "fadeInRight",
                              "animationOut": "fadeOutRight",
                              "hasOverlay": true,
                              "smartPositionOff": true
                             }'>
                                        <i class="tio-command-key"></i>
                                    </a>
                                </div>
                                <!-- End Keyboard Shortcuts Toggle -->
                            </li>
                        </ul>
                        <!-- End List Dot -->
                    </div>
                </div>
            </div>
        </div>


        <!-- End Footer -->
</main>


<jsp:include page="js.jsp"/>

</body>

</html>

