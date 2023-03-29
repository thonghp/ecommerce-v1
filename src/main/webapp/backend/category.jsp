<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html lang="en">

<head>
    <!-- Title -->
    <title>Admin - Quản lý thể loại</title>
    <jsp:include page="css.jsp"/>
</head>

<body class="footer-offset">

<jsp:include page="header.jsp"/>

<main id="content" role="main" class="main">
    <div class="content container-fluid">
        <div class="page-header">
            <div class="row align-items-end">
                <div class="col-sm mb-2 mb-sm-0">
                    <h1 class="page-header-title">Thể loại</h1>
                </div>

                <div class="col-sm-auto">
                    <a class="btn btn-primary" href="create_category">
                        <i class="tio-user-add mr-1"></i> Thêm thể loại
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
                        <form action="list_categories" method="get">
                            <div class="input-group input-group-merge input-group-flush">
                                <div class="input-group-prepend">
                                    <div class="input-group-text">
                                        <i class="tio-search"></i>
                                    </div>
                                </div>
                                <input type="search" class="form-control" placeholder="Tìm kiếm thể loại"
                                       name="keyword" value="${keyword}">
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
                                             src="../images/svg/illustrations/copy.svg" alt="Image Description">
                                        Sao chép
                                    </a>
                                    <a id="export-print" class="dropdown-item" href="#">
                                        <img class="avatar avatar-xss avatar-4by3 mr-2"
                                             src="../images/svg/illustrations/print.svg" alt="Image Description">
                                        In
                                    </a>
                                    <div class="dropdown-divider"></div>
                                    <span class="dropdown-header">Tuỳ chọn tải xuống</span>
                                    <a id="export-excel" class="dropdown-item" href="#">
                                        <img class="avatar avatar-xss avatar-4by3 mr-2"
                                             src="../images/svg/brands/excel.svg" alt="Image Description">
                                        Excel
                                    </a>
                                    <a id="export-csv" class="dropdown-item" href="#">
                                        <img class="avatar avatar-xss avatar-4by3 mr-2"
                                             src="../images/svg/components/placeholder-csv-format.svg"
                                             alt="Image Description">
                                        CSV
                                    </a>
                                    <a id="export-pdf" class="dropdown-item" href="#">
                                        <img class="avatar avatar-xss avatar-4by3 mr-2"
                                             src="../images/svg/brands/pdf.svg"
                                             alt="Image Description">
                                        PDF
                                    </a>
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
                        "targets": [0, 4],
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
                        <th>Alias</th>
                        <th>Thể loại cha</th>
                        <th>Trạng thái</th>
                        <th></th>
                    </tr>
                    </thead>

                    <tbody>

                    <c:forEach var="category" items="${listCategories}">
                        <tr>
                            <td>
                                <a class="d-flex align-items-center" href="edit_category?id=${category.id}">
                                        ${category.name}
                                </a>
                            </td>
                            <td>${category.alias}</td>
                            <td>
                                <c:if test="${category.parentId == 0}"></c:if>
                                <c:if test="${category.parentId != 0}">
                                    ${category.parentId}
                                </c:if>
                            </td>
                            <td>
                                <c:if test="${category.enabled}">
                                    <a class="fas fa-check-circle fa-2x icon-green"
                                       href="status_category?enabled=false&id=${category.id}" title="Vô hiệu hoá">
                                    </a>
                                </c:if>
                                <c:if test="${!category.enabled}">
                                    <a class="fas fa-check-circle fa-2x icon-gray"
                                       href="status_category?enabled=true&id=${category.id}" title="Kích hoạt">
                                    </a>
                                </c:if>
                            </td>
                            <td>
                                <div>
                                    <a class="btn btn-sm btn-white" href="edit_category?id=${category.id}">
                                        <i class="tio-edit"></i> Chỉnh sửa
                                    </a>
                                    <a class="btn btn-sm btn-white link-delete"
                                       href="delete_category?id=${category.id}" entityId="${category.id}">
                                        <i class="tio-delete"></i> Xóa
                                    </a>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>

            <%-- Pagination --%>
            <c:if test="${totalPages > 0}">
                <div class="card-footer">
                    <nav aria-label="Page navigation example">
                        <ul class="pagination float-right">
                            <li class="${currentPage > 1 ? 'page-item' : 'page-item disabled'}">
                                <a class="page-link"
                                   href="list_categories?pageNumber=${currentPage - 1}&keyword=${keyword}">Trước</a>
                            </li>
                            <c:forEach var="i" begin="1" end="${totalPages}">
                                <li class="${currentPage != i ? 'page-item' : 'page-item active'}">
                                    <a class="page-link"
                                       href="list_categories?pageNumber=${i}&keyword=${keyword}">${i}</a>
                                </li>
                            </c:forEach>
                            <li class="${currentPage < totalPages ? 'page-item' : 'page-item disabled'}">
                                <a class="page-link"
                                   href="list_categories?pageNumber=${currentPage + 1}&keyword=${keyword}">Sau</a>
                            </li>
                        </ul>
                    </nav>
                </div>
            </c:if>
        </div>
    </div>
</main>

<jsp:include page="confirm_modal.jsp"/>

<jsp:include page="js.jsp"/>

<script type="text/javascript">
    const message = document.getElementById('hideMessage');
    if (message !== null) {
        function hideMessage() {
            message.style.display = 'none';
        }

        setTimeout(hideMessage, 5000);
    }

    function showDeleteConfirmModal(link) {
        let entityId = link.attr("entityId");
        $("#yesButton").attr("href", link.attr("href")); // set value of attribute
        $("#confirmText").text("Bạn có muốn xoá thể loại không ?");
        $("#confirmModal").modal();
    }

    $(document).ready(function () {
        $(".link-delete").on("click", function (e) {
            e.preventDefault();
            showDeleteConfirmModal($(this));
        });
    });
</script>

</body>

</html>