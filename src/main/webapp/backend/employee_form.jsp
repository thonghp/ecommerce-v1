<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html lang="en">

<head>
    <title>Admin - Thêm nhân viên</title>
    <jsp:include page="css.jsp"/>
</head>

<body class="footer-offset">

<jsp:include page="header.jsp"/>

<main id="content" role="main" class="main">
    <div class="content container-fluid">
        <form class="js-step-form py-md-5" method="post" enctype="multipart/form-data" action="create_employee" id="userForm">
            <div class="row justify-content-lg-center">
                <div class="col-lg-8">
                    <div id="addUserStepFormContent">
                        <div id="addUserStepProfile" class="card card-lg active">
                            <div class="card-body">
                                <div class="row form-group">
                                    <label class="col-sm-3 col-form-label input-label">Ảnh cá nhân</label>

                                    <div class="col-sm-9">
                                        <div class="d-flex align-items-center">
                                            <label class="avatar avatar-xl avatar-circle avatar-uploader mr-5"
                                                   for="avatarUploader">
                                                <img id="avatarImg" class="avatar-img" src="assets\img\160x160\img1.jpg"
                                                     alt="Image Description">

                                                <input type="file" class="js-file-attach avatar-uploader-input"
                                                       id="avatarUploader" name="image"
                                                       data-hs-file-attach-options='{
                                                       "textTarget": "#avatarImg",
                                                       "mode": "image",
                                                       "targetAttr": "src",
                                                       "resetTarget": ".js-file-attach-reset-img",
                                                       "resetImg": "./assets/img/160x160/img1.jpg",
                                                       "allowTypes": [".png", ".jpeg", ".jpg"]}'>

                                                <span class="avatar-uploader-trigger">
                                                    <i class="tio-edit avatar-uploader-icon shadow-soft"></i>
                                                </span>
                                            </label>

                                            <button type="button" class="js-file-attach-reset-img btn btn-white">
                                                Xoá
                                            </button>
                                        </div>
                                    </div>
                                </div>

                                <div class="row form-group">
                                    <label for="lastNameLabel" class="col-sm-3 col-form-label input-label">Họ</label>

                                    <div class="col-sm-9">
                                        <input type="text" class="form-control" name="lastName" id="lastNameLabel"
                                               placeholder="Họ" value="${user.lastName}">
                                    </div>
                                </div>

                                <div class="row form-group">
                                    <label for="firstNameLabel" class="col-sm-3 col-form-label input-label">Tên</label>

                                    <div class="col-sm-9">
                                        <input type="text" class="form-control" name="firstName" id="firstNameLabel"
                                               placeholder="Tên" value="${user.firstName}">
                                    </div>
                                </div>

                                <div class="row form-group">
                                    <label for="emailLabel" class="col-sm-3 col-form-label input-label">Email</label>

                                    <div class="col-sm-9">
                                        <input type="email" class="form-control" name="email" id="emailLabel"
                                               placeholder="example@gmail.com" value="${user.email}">
                                    </div>
                                </div>

                                <div class="row form-group">
                                    <label for="passwordLabel" class="col-sm-3 col-form-label input-label">
                                        Mật khẩu
                                    </label>

                                    <div class="col-sm-9">
                                        <input type="password" class="form-control" name="password" id="passwordLabel">
                                    </div>
                                </div>

                                <div class="row form-group">
                                    <label for="phoneLabel" class="col-sm-3 col-form-label input-label">
                                        Số điện thoại
                                    </label>

                                    <div class="col-sm-9">
                                        <input type="text" class="form-control" name="phone" id="phoneLabel"
                                               value="${user.phoneNumber}">
                                    </div>
                                </div>

                                <div class="row form-group">
                                    <label for="streetLabel" class="col-sm-3 col-form-label input-label">Đường</label>

                                    <div class="col-sm-9">
                                        <input type="text" class="form-control" name="street" id="streetLabel"
                                               value="${user.address.street}">
                                    </div>
                                </div>

                                <div class="row form-group">
                                    <label for="wardLabel" class="col-sm-3 col-form-label input-label">
                                        Phường / Xã
                                    </label>

                                    <div class="col-sm-9">
                                        <input type="text" class="form-control" name="ward" id="wardLabel"
                                               value="${user.address.ward}">
                                    </div>
                                </div>

                                <div class="row form-group">
                                    <label for="districtLabel" class="col-sm-3 col-form-label input-label">
                                        Quận / Huyện
                                    </label>

                                    <div class="col-sm-9">
                                        <input type="text" class="form-control" name="district" id="districtLabel"
                                               value="${user.address.district}">
                                    </div>
                                </div>

                                <div class="row form-group">
                                    <label for="cityLabel" class="col-sm-3 col-form-label input-label">
                                        Tỉnh / Thành phố
                                    </label>

                                    <div class="col-sm-9">
                                        <input type="text" class="form-control" name="city" id="cityLabel"
                                               value="${user.address.city}">
                                    </div>
                                </div>

                                <div class="row form-group">
                                    <label for="roleLabel" class="col-sm-3 col-form-label input-label">
                                        Vai trò
                                    </label>

                                    <div class="col-sm-9 pt-1">
                                        <c:if test="${empty user}">
                                            <c:forEach items="${checkedRoles}" var="role">
                                                <input type="checkbox" id="roleLabel" name="role" class="mt-2"
                                                       value="${role.key.id}" ${role.key.id == 2 ? 'checked' : '' }>
                                                ${role.key.name} - <small>${role.key.description}</small>
                                                <br>
                                            </c:forEach>
                                        </c:if>


                                        <c:if test="${not empty user}">
                                            <c:forEach var="role" items="${checkedRoles}">
                                                <input id="roleLabel" type="checkbox" class="mt-2" name="role"
                                                       value="${role.key.id}"
                                                    ${role.value}> ${role.key.name}
                                                - <small>${role.key.description}</small><br>
                                            </c:forEach>
                                        </c:if>
                                    </div>
                                </div>

                                <div class="row form-group">
                                    <label for="enableLabel" class="col-sm-3 col-form-label input-label">
                                        Trạng thái
                                    </label>

                                    <div class="col-sm-9 pt-1">
                                        <c:if test="${empty user}">
                                            <input type="checkbox" id="enableLabel" name="enable" class="mt-2" checked>
                                        </c:if>
                                        <c:if test="${not empty user}">
                                            <input type="checkbox" id="enableLabel" name="enable"
                                                   class="mt-2" ${user.enabled ? "checked" : ""}>
                                        </c:if>
                                    </div>
                                </div>
                            </div>

                            <div class="card-footer d-flex justify-content-end align-items-center">
                                <button type="submit" class="btn btn-primary">
                                    Xác nhận <i class="tio-chevron-right"></i>
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </div>
</main>

<jsp:include page="modal_dialog.jsp"/>

<jsp:include page="js.jsp"/>

<script>
    function showImageThumbnail(fileInput) {
        let file = fileInput.files[0];
        let reader = new FileReader();
        reader.onload = function (e) {
            $("#avatarImg2").attr("src", e.target.result);
        };
        reader.readAsDataURL(file);
    }

    function checkFileSize(fileInput) {
        let fileSize = fileInput.files[0].size; // returns the selected file and can get the name or size
        let mb = 1024 * 1024; // 1mb = 1024 x 1024 kb
        if (fileSize > mb) {
            fileInput.setCustomValidity("Bạn phải chọn ảnh nhỏ hơn 1MB !");
            fileInput.reportValidity();
            return false;
        } else {
            fileInput.setCustomValidity("");
            return true;
        }
    }

    $(document).on('ready', function () {
        // validation
        $("#userForm").validate({
            ignore: ".ignore",
            rules: {
                firstName: {
                    required: true
                },
                lastName: {
                    required: true
                },
                email: {
                    required: true,
                    email: true
                },
                password: {
                    required: true
                },
                street: {
                    required: true
                },
                ward: {
                    required: true
                },
                district: {
                    required: true
                },
                city: {
                    required: true
                },
                phone: {
                    required: true,
                    number: true,
                    minlength: 10,
                    maxlength: 11
                },
            },
            messages: {
                firstName: {
                    required: "Vui lòng nhập tên đầy đủ."
                },
                lastName: {
                    required: "Vui lòng nhập họ đầy đủ."
                },
                email: {
                    required: "Vui lòng nhập email đầy đủ.",
                    email: "Email không hợp lệ, vd: abc@gmail.com"
                },
                password: {
                    required: "Vui lòng nhập mật khẩu đầy đủ."
                },
                street: {
                    required: "Vui lòng nhập tên đường đầy đủ."
                },
                ward: {
                    required: "Vui lòng nhập tên phường / xã đầy đủ."
                },
                district: {
                    required: "Vui lòng nhập tên quận / huyện đầy đủ."
                },
                city: {
                    required: "Vui lòng nhập tên tỉnh / thành phố đầy đủ."
                },
                phone: {
                    required: "Vui lòng nhập số điện thoại đầy đủ.",
                    number: "Số điện thoại không hợp lệ.",
                    minlength: "Số điện thoại tối thiểu 10 số.",
                    maxlength: "Số điện thoại tối thiểu 10 số."
                },
            },
        });

        $('.js-file-attach').each(function () {
            let customFile = new HSFileAttach($(this)).init();
        });

        // user info
        $("#avatarUploader").change(function () {
            if (!checkFileSize(this)) {
                return;
            }
            showImageThumbnail(this);
        });


        $('#firstNameLabel, #lastNameLabel').on('keyup', function () {
            let firstName = $('#firstNameLabel').val();
            let lastName = $('#lastNameLabel').val();

            $('.fullNameLabel').text(firstName + ' ' + lastName);
        });

        $('#emailLabel').on('keyup', function () {
            let email = $('#emailLabel').val();

            $('.emailLabel').text(email);
        });

        $('#phoneLabel').on('keyup', function () {
            let phone = $('#phoneLabel').val();

            $('.phoneLabel').text(phone);
        });

        let $addressInfo = $('#streetLabel, #wardLabel, #districtLabel, #cityLabel');

        $addressInfo.keyup(function () {
            let full = '';
            $addressInfo.each(function () {
                let value = $(this).val();
                full += ' ' + value;
            });
            $(".addressLabel").text(full);
        }).keyup();
    });

    // show modal when email exists
    function showModalDialog(title, message) {
        $("#modalTitle").text(title);
        $("#modalBody").text(message);
        $("#modalDialog").modal();
    }

    const message = "${message}";
    if (message !== "") {
        showModalDialog("Cảnh báo", message)
    }
</script>

</body>

</html>
