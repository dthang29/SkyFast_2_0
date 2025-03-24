
//    function openModal(id) {
//        $("#myModal" + id).modal('show');
//    }
function openModal(id) {
    let modal = new bootstrap.Modal(document.getElementById("myModal" + id));
    modal.show();
}


function validateForm() {
    const name = document.querySelector('input[name="airplaneName"]').value.trim();
    if (!name) {
        alert("Please enter a valid airplane name.");
        return false;
    }
    return true;
}

function openSeatCategory(id) {
    alert("Seat category setting for airplane ID: " + id);
}
function openUpdateModal(id) {
    if (typeof $ === "undefined") {
        console.error("🚨 jQuery chưa được load!");
        return;
    }

    console.log("Opening update modal for airplane ID:", id); // Debug

    $.ajax({
        url: "/airplanes/get",
        type: "GET",
        data: { id: id },
        success: function (data) {
            $("#updateId").val(data.id);
            $("#updateAirplaneName").val(data.airplaneName);
            $("#updateManufacturer").val(data.manufacturer);
            $("#updateSpeed").val(data.speed);
            $("#updateTotalLength").val(data.totalLength);
            $("#updateWingspan").val(data.wingspan);
            $("#updateHeight").val(data.height);
            $("#updateSeatCapacity").val(data.seatCapacity);
            $("#updateAirplaneStatus").val(data.airplaneStatus);
            $("#updateAirlineId").val(data.airline.id);
            $("#updateCurrentImage").attr("src", data.airplaneImage);

            // Disable "Waiting" option if the airplane is already in "Maintaining" state
            if (data.airplaneStatus === 'Maintaining') {
                $('#updateAirplaneStatus option[value="Waiting"]').prop('disabled', true);
            } else {
                $('#updateAirplaneStatus option[value="Waiting"]').prop('disabled', false);
            }

            let modal = new bootstrap.Modal(document.getElementById("updateModal"));
            modal.show();
        },
        error: function (error) {
            console.error("Error loading airplane data", error);
            alert("Error loading airplane data");
        }
    });
}









//function openUpdateModal(id) {
//$.ajax({
//url: "/airplanes/get",
//type: "GET",
//data: { id: id },
//success: function (data) {
//$("#updateId").val(data.id);
//$("#updateAirplaneName").val(data.airplaneName);
//$("#updateManufacturer").val(data.manufacturer);
//$("#updateDiagram").val(data.diagram);
//$("#updateSpeed").val(data.speed);
//$("#updateTotalLength").val(data.totalLength);
//$("#updateWingspan").val(data.wingspan);
//$("#updateHeight").val(data.height);
//$("#updateSeatCapacity").val(data.seatCapacity);
//$("#updateAirplaneStatus").val(data.airplaneStatus);
//$("#updateAirlineId").val(data.airline.id);
//$("#updateCurrentImage").attr("src", data.airplaneImage);
//
//$("#updateModal").modal("show");
//},
//error: function (error) {
//alert("Error loading airplane data");
//}
//});
//}
function showNotification(message, type) {
    let toast = document.getElementById("notification-toast");
    let messageContainer = document.getElementById("notification-message");

    // Đổi màu nền theo loại thông báo
    if (type === "success") {
        toast.classList.remove("bg-danger");
        toast.classList.add("bg-success");
    } else {
        toast.classList.remove("bg-success");
        toast.classList.add("bg-danger");
    }

    // Cập nhật nội dung thông báo
    messageContainer.innerText = message;

    // Hiển thị toast
    toast.style.display = "block";

    // Tự động ẩn sau 3 giây
    setTimeout(() => {
        toast.style.display = "none";
    }, 3000);
}

document.addEventListener("DOMContentLoaded", function () {
    let successMessage = document.getElementById("successMessage")?.innerText;
    let errorMessage = document.getElementById("errorMessage")?.innerText;

    if (successMessage) {
        showNotification(successMessage, "success");
    }
    if (errorMessage) {
        showNotification(errorMessage, "error");
    }
    let maintenanceForm = document.getElementById("maintenanceForm");
    if (maintenanceForm) {
        maintenanceForm.addEventListener("submit", function (event) {
            event.preventDefault();
            console.log("✅ Maintenance form submitted!");
        });
    } else {
        console.error("🚨 maintenanceForm không tồn tại!");
    }
});

function openMaintenanceModal(airplaneId) {
    // Set airplane ID vào input ẩn
    document.getElementById("airplaneId").value = airplaneId;
    // Hiển thị modal
    let addMaintenanceModal = new bootstrap.Modal(document.getElementById('addMaintenanceModal'));
    addMaintenanceModal.show();
}


//    document.getElementById("maintenanceForm").addEventListener("submit", function (event) {
//        event.preventDefault(); // Ngăn chặn form submit mặc định
//
//        let description = document.getElementById("description").value.trim();
//        let duration = document.getElementById("duration").value;
//        let airplaneId = document.getElementById("airplaneId").value;
//
//        // Validate dữ liệu
//        let isValid = true;
//        if (!description) {
//            document.getElementById("description").classList.add("is-invalid");
//            isValid = false;
//        } else {
//            document.getElementById("description").classList.remove("is-invalid");
//        }
//
//        if (duration <= 0) {
//            document.getElementById("duration").classList.add("is-invalid");
//            isValid = false;
//        } else {
//            document.getElementById("duration").classList.remove("is-invalid");
//        }
//
//        if (!isValid) return;
//
//        // Tạo request object
//        let maintenanceData = {
//            description: description,
//            duration: duration,
//            maintenanceDate: new Date().toISOString().split('T')[0], // Lấy ngày hiện tại
//            maintenanceStatus: "Unprocessed",
//            airplaneId: airplaneId
//        };
//
//        // Gửi dữ liệu lên server qua AJAX
//        fetch("/admin/maintenance/add", {
//            method: "POST",
//            headers: {
//                "Content-Type": "application/json"
//            },
//            body: JSON.stringify(maintenanceData)
//        })
//        .then(response => response.json())
//        .then(data => {
//            if (data.success) {
//                window.location.href = data.redirectUrl; // Chuyển hướng tới trang danh sách bảo trì
//            } else {
//                alert("Failed to add maintenance.");
//            }
//        })
//        .catch(error => {
//            console.error("Error:", error);
//            alert("Error while adding maintenance.");
//        });
//    });
//document.addEventListener("DOMContentLoaded", function () {
//    let maintenanceForm = document.getElementById("maintenanceForm");
//    if (maintenanceForm) {
//        maintenanceForm.addEventListener("submit", function (event) {
//            event.preventDefault(); // Ngăn chặn submit mặc định
//            console.log("✅ Maintenance form submitted!");
//
//            let description = document.getElementById("description").value.trim();
//            let duration = document.getElementById("duration").value;
//            let airplaneId = document.getElementById("airplaneId").value;
//
//            if (!description || duration <= 0) {
//                alert("Vui lòng nhập đúng thông tin bảo trì!");
//                return;
//            }
//
//            let maintenanceData = {
//                description: description,
//                duration: duration,
//                maintenanceDate: new Date().toISOString().split('T')[0], // Ngày hiện tại
//                maintenanceStatus: "Unprocessed",
//                airplaneId: airplaneId
//            };
//
//            fetch("/admin/maintenance/add", {
//                method: "POST",
//                headers: { "Content-Type": "application/json" },
//                body: JSON.stringify(maintenanceData)
//            })
//            .then(response => response.json())
//            .then(data => {
//                if (data.success) {
//                    console.log("✅ Redirecting to:", data.redirectUrl);
//                    window.location.href = data.redirectUrl; // Chuyển hướng tới trang danh sách bảo trì
//                } else {
//                    alert("Thêm bảo trì thất bại.");
//                }
//            })
//            .catch(error => {
//                console.error("Error:", error);
//                alert("Lỗi khi thêm bảo trì.");
//            });
//        });
//    } else {
//        console.error("🚨 maintenanceForm không tìm thấy trong DOM!");
//    }
//});
document.addEventListener("DOMContentLoaded", function () {
    // Lấy các phần tử input
    const startDateInput = document.getElementById("startDate");
    const durationInput = document.getElementById("duration");
    const completionDateInput = document.getElementById("completionDate");
    const maintenanceForm = document.getElementById("maintenanceForm");

    // Thiết lập min cho startDate là ngày hiện tại
    if (startDateInput) {
        startDateInput.min = new Date().toISOString().split("T")[0];
        startDateInput.addEventListener("change", updateCompletionDate);
    }
    if (durationInput) {
        durationInput.addEventListener("change", updateCompletionDate);
    }

    function updateCompletionDate() {
        const startDateValue = startDateInput.value;
        const durationValue = parseInt(durationInput.value) || 0;
        if (startDateValue && durationValue > 0) {
            const startDateObj = new Date(startDateValue);
            // Cộng duration vào ngày bắt đầu (lưu ý: nếu muốn tính ngày bắt đầu là ngày thứ 1 thì giữ nguyên công thức)
            startDateObj.setDate(startDateObj.getDate() + durationValue);
            completionDateInput.value = startDateObj.toISOString().split("T")[0];
        } else {
            completionDateInput.value = "";
        }
    }

    // Xử lý submit form bảo trì
    if (maintenanceForm) {
        maintenanceForm.addEventListener("submit", function (event) {
            event.preventDefault();
            let description = document.getElementById("description").value.trim();
            let duration = durationInput.value;
            let airplaneId = document.getElementById("airplaneId").value;

            if (!description || duration <= 0 || !startDateInput.value) {
                alert("Vui lòng nhập đúng thông tin bảo trì!");
                return;
            }

            let maintenanceData = {
                description: description,
                duration: duration,
                maintenanceDate: startDateInput.value, // Ngày bắt đầu bảo trì được chọn
                completionDate: completionDateInput.value, // Ngày kết thúc bảo trì tự động tính
                maintenanceStatus: "Unprocessed",
                airplaneId: airplaneId
            };

            fetch("/admin/maintenance/add", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(maintenanceData)
            })
                .then(response => response.json())
                .then(data => {
                    if (data.success) {
                        window.location.href = data.redirectUrl;
                    } else {
                        alert("Thêm bảo trì thất bại.");
                    }
                })
                .catch(error => {
                    console.error("Error:", error);
                    alert("Lỗi khi thêm bảo trì.");
                });
        });
    } else {
        console.error("🚨 maintenanceForm không tìm thấy trong DOM!");
    }
});

