<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html lang="en">

<head>
    <!-- Title -->
    <title>Admin - Quản lý nhân viên</title>
    <jsp:include page="css.jsp"/>
</head>

<body class="footer-offset">

<jsp:include page="header.jsp"/>

<main id="content" role="main" class="main">
    <div class="content container-fluid">
        <div class="page-header">
            <div class="row align-items-end">
                <div class="col-sm mb-2 mb-sm-0">
                    <h1 class="page-header-title">Nhân viên</h1>
                </div>

                <div class="col-sm-auto">
                    <a class="btn btn-primary" href="create_employee">
                        <i class="tio-user-add mr-1"></i> Thêm nhân viên
                    </a>
                </div>
            </div>
        </div>

        <c:if test="${message != null}">
            <div class="alert alert-success text-center" id="hideMessage">${message}</div>
        </c:if>

        <div class="card">
            <div class="card-header">
                <div class="row justify-content-between align-items-center flex-grow-1">
                    <div class="col-sm-6 col-md-4 mb-3 mb-sm-0">
                        <form>
                            <div class="input-group input-group-merge input-group-flush">
                                <div class="input-group-prepend">
                                    <div class="input-group-text">
                                        <i class="tio-search"></i>
                                    </div>
                                </div>
                                <input id="datatableSearch" type="search" class="form-control"
                                       placeholder="Tìm kiếm nhân viên">
                            </div>
                        </form>
                    </div>

                    <div class="col-sm-6">
                        <div class="d-sm-flex justify-content-sm-end align-items-sm-center">
                            <div class="hs-unfold mr-2">
                                <a class="js-hs-unfold-invoker btn btn-sm btn-white dropdown-toggle" href="#"
                                   data-hs-unfold-options='{"target": "#usersExportDropdown","type": "css-animation"}'>
                                    <i class="tio-download-to mr-1"></i> Xuất
                                </a>

                                <div id="usersExportDropdown"
                                     class="hs-unfold-content dropdown-unfold dropdown-menu dropdown-menu-sm-right">
                                    <span class="dropdown-header">Tuỳ chọn</span>
                                    <a id="export-copy" class="dropdown-item" href="#">
                                        <img class="avatar avatar-xss avatar-4by3 mr-2"
                                             src="assets\svg\illustrations\copy.svg" alt="Image Description">
                                        Sao chép
                                    </a>
                                    <a id="export-print" class="dropdown-item" href="#">
                                        <img class="avatar avatar-xss avatar-4by3 mr-2"
                                             src="assets\svg\illustrations\print.svg" alt="Image Description">
                                        In
                                    </a>
                                    <div class="dropdown-divider"></div>
                                    <span class="dropdown-header">Tuỳ chọn tải xuống</span>
                                    <a id="export-excel" class="dropdown-item" href="#">
                                        <img class="avatar avatar-xss avatar-4by3 mr-2"
                                             src="assets\svg\brands\excel.svg" alt="Image Description">
                                        Excel
                                    </a>
                                    <a id="export-csv" class="dropdown-item" href="#">
                                        <img class="avatar avatar-xss avatar-4by3 mr-2"
                                             src="assets\svg\components\placeholder-csv-format.svg"
                                             alt="Image Description">
                                        CSV
                                    </a>
                                    <a id="export-pdf" class="dropdown-item" href="#">
                                        <img class="avatar avatar-xss avatar-4by3 mr-2" src="assets\svg\brands\pdf.svg"
                                             alt="Image Description">
                                        PDF
                                    </a>
                                </div>
                            </div>

                            <div class="hs-unfold">
                                <a class="js-hs-unfold-invoker btn btn-sm btn-white" href="#"
                                   data-hs-unfold-options=
                                           '{"target": "#usersFilterDropdown","type": "css-animation","smartPositionOff": true}'>
                                    <i class="tio-filter-list mr-1"></i> Lọc
                                </a>

                                <div id="usersFilterDropdown"
                                     class="hs-unfold-content dropdown-unfold dropdown-menu dropdown-menu-sm-right dropdown-card card-dropdown-filter-centered"
                                     style="min-width: 22rem;">
                                    <div class="card">
                                        <div class="card-header">
                                            <h5 class="card-header-title">Lọc nhân viên</h5>
                                            <a class="js-hs-unfold-invoker btn btn-icon btn-xs btn-ghost-secondary ml-2"
                                               href="#" data-hs-unfold-options=
                                                       '{"target": "#usersFilterDropdown","type": "css-animation","smartPositionOff": true}'>
                                                <i class="tio-clear tio-lg"></i>
                                            </a>
                                        </div>

                                        <div class="card-body">
                                            <form>
                                                <div class="form-row">
                                                    <div class="col-12 form-group">
                                                        <small class="text-cap mb-2">Vai trò</small>
                                                        <select class="js-select2-custom js-datatable-filter custom-select"
                                                                size="1" style="opacity: 0;"
                                                                data-target-column-index="3"
                                                                data-hs-select2-options='{"searchInputPlaceholder": "Tìm kiếm vai trò"}'>

                                                            <option value="2" selected>Nhân viên bán hàng</option>
                                                            <option value="1">Admin</option>
                                                        </select>
                                                    </div>
                                                </div>

                                                <a class="js-hs-unfold-invoker btn btn-block btn-primary"
                                                   href="#" data-hs-unfold-options=
                                                           '{"target": "#usersFilterDropdown","type": "css-animation","smartPositionOff": true}'>
                                                    Áp dụng
                                                </a>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="table-responsive datatable-custom">
                <table id="datatable"
                       class="table table-lg table-borderless table-thead-bordered table-nowrap table-align-middle card-table"
                       data-hs-datatables-options='{
                     "columnDefs": [{
                        "targets": [0, 5],
                        "orderable": false
                      }],
                     "order": [],
                     "info": {
                       "totalQty": "#datatableWithPaginationInfoTotalQty"
                     },
                     "search": "#datatableSearch",
                     "entries": "#datatableEntries",
                     "pageLength": 5,
                     "isResponsive": false,
                     "isShowPaging": false,
                     "pagination": "datatablePagination"
                   }'>
                    <thead class="thead-light">
                    <tr>
                        <th class="table-column-pl-1">Tên</th>
                        <th>Số điện thoại</th>
                        <th>Địa chỉ</th>
                        <th>Vai trò</th>
                        <th>Trạng thái</th>
                        <th></th>
                    </tr>
                    </thead>

                    <tbody>

                    <c:forEach var="employee" items="${listEmployees}">
                        <tr>
                            <td class="table-column-pl-1">
                                <c:if test="${employee.id == 1}">
                                <a class="d-flex align-items-center">
                                    </c:if>
                                    <c:if test="${employee.id != 1}">
                                    <a class="d-flex align-items-center" href="edit_employee?id=${employee.id}">
                                        </c:if>
                                            <%-- Admin --%>
                                        <c:if test="${employee.id == 1}">
                                            <div class="avatar avatar-soft-primary avatar-circle">
                                                <span class="avatar-initials">A</span>
                                            </div>
                                            <div class="ml-3">
                                            <span class="d-block h5 text-hover-primary mb-0">
                                                Admin
                                                <i class="tio-verified text-primary" data-toggle="tooltip"
                                                   data-placement="top"></i>
                                            </span>
                                                <span class="d-block font-size-sm text-body">${employee.email}</span>
                                            </div>
                                        </c:if>

                                            <%-- Normal --%>
                                        <c:if test="${employee.id != 1}">
                                            <c:if test="${not empty employee.imagePath}">
                                                <div class="avatar avatar-circle">
                                                    <img class="avatar-img"
                                                         src="../images/employee/${employee.id}/${employee.imagePath}"
                                                         alt="Image Description">
                                                </div>
                                                <div class="ml-3">
                                                <span class="d-block h5 text-hover-primary mb-0">
                                                        ${employee.lastName} ${employee.firstName}
                                                </span>
                                                    <span class="d-block font-size-sm text-body">${employee.email}</span>
                                                </div>
                                            </c:if>
                                            <c:if test="${empty employee.imagePath}">
                                                <div class="avatar avatar-soft-primary avatar-circle">
                                                    <span class="avatar-initials">${fn:substring(employee.firstName, 0, 1)}</span>
                                                </div>
                                                <div class="ml-3">
                                                <span class="d-block h5 text-hover-primary mb-0">
                                                        ${employee.lastName} ${employee.firstName}
                                                </span>
                                                    <span class="d-block font-size-sm text-body">${employee.email}</span>
                                                </div>
                                            </c:if>
                                        </c:if>
                                    </a>
                            </td>

                            <td>${employee.phoneNumber}</td>
                            <td>${employee.address}</td>
                            <td>${employee.roles}</td>
                            <td>
                                <c:if test="${employee.enabled}">
                                    <a class="fas fa-check-circle fa-2x icon-green"
                                       href="status_employee?enabled=false&id=${employee.id}"
                                       title="Vô hiệu hoá">
                                    </a>
                                </c:if>
                                <c:if test="${!employee.enabled}">
                                    <a class="fas fa-check-circle fa-2x icon-gray"
                                       href="status_employee?enabled=true&id=${employee.id}" title="Kích hoạt">
                                    </a>
                                </c:if>
                            </td>
                            <td>
                                <div>
                                    <a class="btn btn-sm btn-white" href="edit_employee?id=${employee.id}">
                                        <i class="tio-edit"></i> Chỉnh sửa
                                    </a>
                                    <a class="btn btn-sm btn-white link-delete"
                                       href="delete_employee?id=${employee.id}" entityId="${employee.id}">
                                        <i class="tio-delete"></i> Xóa
                                    </a>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>

                    </tbody>
                </table>
            </div>

            <c:if test="${totalPages > 0}">
                <div class="card-footer">
                    <nav aria-label="Page navigation example">
                        <ul class="pagination float-right">
                            <li class="${currentPage > 1 ? 'page-item' : 'page-item disabled'}">
                                <a class="page-link"
                                   href="list_employees?pageNumber=${currentPage - 1}&keyword=${keyword}">Trước</a>
                            </li>
                            <c:forEach var="i" begin="1" end="${totalPages}">
                                <li class="${currentPage != i ? 'page-item' : 'page-item active'}">
                                    <a class="page-link"
                                       href="list_employees?pageNumber=${i}&keyword=${keyword}">${i}</a>
                                </li>
                            </c:forEach>
                            <li class="${currentPage < totalPages ? 'page-item' : 'page-item disabled'}">
                                <a class="page-link"
                                   href="list_employees?pageNumber=${currentPage + 1}&keyword=${keyword}">Sau</a>
                            </li>
                        </ul>
                    </nav>
                </div>
            </c:if>
        </div>
    </div>
</main>

<jsp:include page="js.jsp"/>

<script type="text/javascript">
    const message = document.getElementById('hideMessage');
    if (message !== null) {
        function hideMessage() {
            message.style.display = 'none';
        }

        setTimeout(hideMessage, 5000);
    }
</script>

</body>

</html>