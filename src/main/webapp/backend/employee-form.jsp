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
        <form class="js-step-form py-md-5" data-hs-step-form-options='{
                "progressSelector": "#addUserStepFormProgress",
                "stepsSelector": "#addUserStepFormContent",
                "endSelector": "#addUserFinishBtn",
                "isValidate": false
              }' method="post" enctype="multipart/form-data" action="create_employee">
            <div class="row justify-content-lg-center">
                <div class="col-lg-8">
                    <ul id="addUserStepFormProgress"
                        class="js-step-progress step step-sm step-icon-sm step step-inline step-item-between mb-3 mb-md-5">
                        <li class="step-item">
                            <a class="step-content-wrapper" href="#"
                               data-hs-step-form-next-options='{"targetSelector": "#addUserStepProfile"}'>
                                <span class="step-icon step-icon-soft-dark">1</span>
                                <div class="step-content">
                                    <span class="step-title">Thông tin cá nhân</span>
                                </div>
                            </a>
                        </li>

                        <li class="step-item">
                            <a class="step-content-wrapper" href="#"
                               data-hs-step-form-next-options='{"targetSelector": "#addUserStepBillingAddress"}'>
                                <span class="step-icon step-icon-soft-dark">2</span>
                                <div class="step-content">
                                    <span class="step-title">Vai trò</span>
                                </div>
                            </a>
                        </li>

                        <li class="step-item">
                            <a class="step-content-wrapper" href="#"
                               data-hs-step-form-next-options='{"targetSelector": "#addUserStepConfirmation"}'>
                                <span class="step-icon step-icon-soft-dark">3</span>
                                <div class="step-content">
                                    <span class="step-title">Xác nhận</span>
                                </div>
                            </a>
                        </li>
                    </ul>

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
                                                       id="avatarUploader"
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
                                    <label for="firstNameLabel" class="col-sm-3 col-form-label input-label">
                                        Họ Tên
                                        <i class="tio-help-outlined text-body ml-1" data-toggle="tooltip"
                                           data-placement="top" title="Điền vào họ tên nhân viên.">
                                        </i>
                                    </label>

                                    <div class="col-sm-9">
                                        <div class="input-group input-group-sm-down-break">
                                            <input type="text" class="form-control" name="firstName" id="firstNameLabel"
                                                   placeholder="Tên" aria-label="Clarice">
                                            <input type="text" class="form-control" name="lastName" id="lastNameLabel"
                                                   placeholder="Họ" aria-label="Boone">
                                        </div>
                                    </div>
                                </div>

                                <div class="row form-group">
                                    <label for="emailLabel" class="col-sm-3 col-form-label input-label">Email</label>

                                    <div class="col-sm-9">
                                        <input type="email" class="form-control" name="email" id="emailLabel"
                                               placeholder="example@gmail.com">
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
                                        <input type="text" class="form-control" name="phone" id="phoneLabel">
                                    </div>
                                </div>

                                <div class="row form-group">
                                    <label for="streetLabel" class="col-sm-3 col-form-label input-label">Đường</label>

                                    <div class="col-sm-9">
                                        <input type="text" class="form-control" name="street" id="streetLabel">
                                    </div>
                                </div>

                                <div class="row form-group">
                                    <label for="wardLabel" class="col-sm-3 col-form-label input-label">
                                        Phường / Xã
                                    </label>

                                    <div class="col-sm-9">
                                        <input type="text" class="form-control" name="ward" id="wardLabel">
                                    </div>
                                </div>

                                <div class="row form-group">
                                    <label for="districtLabel" class="col-sm-3 col-form-label input-label">
                                        Quận / Huyện
                                    </label>

                                    <div class="col-sm-9">
                                        <input type="text" class="form-control" name="district" id="districtLabel">
                                    </div>
                                </div>

                                <div class="row form-group">
                                    <label for="cityLabel" class="col-sm-3 col-form-label input-label">
                                        Tỉnh / Thành phố
                                    </label>

                                    <div class="col-sm-9">
                                        <input type="text" class="form-control" name="city" id="cityLabel">
                                    </div>
                                </div>
                            </div>

                            <div class="card-footer d-flex justify-content-end align-items-center">
                                <button type="button" class="btn btn-primary"
                                        data-hs-step-form-next-options='{"targetSelector": "#addUserStepBillingAddress"}'>
                                    Bước kế tiếp <i class="tio-chevron-right"></i>
                                </button>
                            </div>
                        </div>

                        <div id="addUserStepBillingAddress" class="card card-lg" style="display: none;">
                            <div class="card-body">
                                <div class="row form-group">
                                    <label for="roleLabel" class="col-sm-3 col-form-label input-label">
                                        Vai trò
                                    </label>

                                    <div class="col-sm-9 pt-1">
                                        <c:forEach items="${roles}" var="role">
                                            <input type="checkbox" id="roleLabel" name="role" class="mt-2"
                                                   value="${role.id}"> ${role.name} - <small>${role.description}</small>
                                            <br>
                                        </c:forEach>
                                    </div>
                                </div>

                                <div class="row form-group">
                                    <label for="enableLabel" class="col-sm-3 col-form-label input-label">
                                        Trạng thái
                                    </label>

                                    <div class="col-sm-9 pt-1">
                                        <input type="checkbox" id="enableLabel" name="enable" class="mt-2">
                                    </div>
                                </div>
                            </div>

                            <div class="card-footer d-flex align-items-center">
                                <button type="button" class="btn btn-ghost-secondary"
                                        data-hs-step-form-prev-options='{"targetSelector": "#addUserStepProfile"}'>
                                    <i class="tio-chevron-left"></i> Bước phía trước
                                </button>

                                <div class="ml-auto">
                                    <button type="button" class="btn btn-primary" id="getUserInfo"
                                            data-hs-step-form-next-options='{"targetSelector": "#addUserStepConfirmation"}'>
                                        Bước kế tiếp <i class="tio-chevron-right"></i>
                                    </button>
                                </div>
                            </div>
                        </div>

                        <div id="addUserStepConfirmation" class="card card-lg" style="display: none;">
                            <div class="profile-cover">
                                <div class="profile-cover-img-wrapper">
                                    <img class="profile-cover-img" src="assets\img\1920x400\img1.jpg"
                                         alt="Image Description">
                                </div>
                            </div>

                            <div class="avatar avatar-xxl avatar-circle avatar-border-lg profile-cover-avatar">
                                <img class="avatar-img" id="avatarImg2" src="assets\img\160x160\img1.jpg"
                                     alt="Image Description" width="160px" height="160px">
                            </div>

                            <div class="card-body">
                                <dl class="row">
                                    <dt class="col-sm-6 text-sm-right">Tên đầy đủ:</dt>
                                    <dd class="col-sm-6 fullNameLabel">Hoàng Phạm Thông</dd>

                                    <dt class="col-sm-6 text-sm-right">Email:</dt>
                                    <dd class="col-sm-6 emailLabel">thong@gmail.com</dd>

                                    <dt class="col-sm-6 text-sm-right">Số điện thoại:</dt>
                                    <dd class="col-sm-6 phoneLabel">0766821606</dd>

                                    <dt class="col-sm-6 text-sm-right">Địa chỉ:</dt>
                                    <dd class="col-sm-6 addressLabel">113/4 đường số 12 phường bình hưng hoà</dd>
                                </dl>
                            </div>

                            <div class="card-footer d-sm-flex align-items-sm-center">
                                <button type="button" class="btn btn-ghost-secondary mb-2 mb-sm-0"
                                        data-hs-step-form-prev-options='{"targetSelector": "#addUserStepBillingAddress"}'>
                                    <i class="tio-chevron-left"></i> Bước phía trước
                                </button>

                                <div class="ml-auto">
                                    <button id="addUserFinishBtn" type="button" class="btn btn-primary">
                                        Xác nhận
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div id="successMessageContent" style="display:none;">
                        <div class="text-center">
                            <img class="img-fluid mb-3" src="assets\svg\illustrations\hi-five.svg"
                                 alt="Image Description" style="max-width: 15rem;" id="confirmImg">

                            <div class="mb-4">
                                <h2>Thành công!</h2>
                                <p>Nhân viên <span class="font-weight-bold text-dark">Hoàng Phạm Thông</span> đã được
                                    tạo thành công.</p>
                            </div>

                            <div class="d-flex justify-content-center">
                                <a class="btn btn-white mr-3" href="list_employees">
                                    <i class="tio-chevron-left ml-1"></i> Quay lại
                                </a>
                                <a class="btn btn-primary" href="create_employee">
                                    <i class="tio-user-add mr-1"></i> Thêm nhân viên
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </div>
</main>

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
        $('.js-step-form').each(function () {
            let stepForm = new HSStepForm($(this), {
                finish: function () {
                    $("#addUserStepFormProgress").hide();
                    $("#addUserStepFormContent").hide();
                    $("#successMessageContent").show();
                }
            }).init();
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
</script>

</body>

</html>
