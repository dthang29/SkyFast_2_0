document.addEventListener("DOMContentLoaded", function () {
    console.log("🚀 Script loaded and DOM is ready!");
    attachEventListeners();
});

// 🛠 Gán lại sự kiện khi nội dung trang thay đổi
function attachEventListeners() {
    console.log("🎯 Attaching event listeners...");

    let searchButton = document.querySelector(".filterController .btn-primary");
    let resetButton = document.getElementById("resetSearch");

    if (searchButton) {
        searchButton.addEventListener("click", function (event) {
            event.preventDefault();
            searchMaintenance();
        });
    } else {
        console.error("❌ Search button not found!");
    }

    if (resetButton) {
        resetButton.addEventListener("click", function (event) {
            event.preventDefault();
            resetMaintenanceList();
        });
    } else {
        console.error("❌ Reset button not found!");
    }

    // Dùng event delegation để không cần gán sự kiện lại sau mỗi lần cập nhật bảng
    document.body.addEventListener("click", function (event) {
        if (event.target.classList.contains("btn-update")) {
            let id = event.target.getAttribute("data-id");
            if (id) {
                openUpdateModal(id);
            }
        }
    });
}

function searchMaintenance() {
    console.log("🔍 Searching maintenance records...");

    let status = document.querySelector("select[name='status']").value;
    let fromDate = document.querySelector("input[name='fromDate']").value;
    let toDate = document.querySelector("input[name='toDate']").value;

    // Lấy thêm giá trị từ input airplaneName
    let airplaneName = document.querySelector("input[name='airplaneName']").value;

    let queryParams = new URLSearchParams();
    if (status !== "") queryParams.append("status", status);
    if (fromDate) queryParams.append("fromDate", fromDate);
    if (toDate) queryParams.append("toDate", toDate);

    // Nếu người dùng nhập tên máy bay, ta đưa vào query param
    if (airplaneName) {
        queryParams.append("airplaneName", airplaneName);
    }

    // Gọi endpoint /admin/maintenance/searchAll,
    // ta sẽ sửa backend để nhận thêm param airplaneName
    fetch(`/admin/maintenance/searchAll?${queryParams.toString()}`)
        .then(response => response.json())
        .then(data => {
            updateMaintenanceTable(data);
        })
        .catch(error => {
            console.error("Error searching maintenance:", error);
            showNotification("Failed to search maintenance!", "error");
        });
}



//function updateMaintenanceTable(maintenanceList) {
//    let tableBody = document.querySelector("tbody");
//    tableBody.innerHTML = "";
//
//    maintenanceList.forEach(maintenance => {
//        let actionButton = "";
//        if (maintenance.maintenanceStatus !== "Processed") {
//            actionButton = `<button class="btn btn-update" data-id="${maintenance.id}">Update</button>`;
//        }
//
//        let row = `<tr>
//            <td>${maintenance.id}</td>
//            <td>${maintenance.airplane.airplaneName}</td>  <!-- Hiển thị tên máy bay -->
//            <td>${maintenance.maintenanceDate}</td>
//            <td>${maintenance.duration}</td>
//            <td>${maintenance.completionDate}</td>
//            <td>${maintenance.description}</td>
//            <td>
//                <span class="status-badge ${getStatusClass(maintenance.maintenanceStatus)}">
//                    ${maintenance.maintenanceStatus}
//                </span>
//            </td>
//            <td>${actionButton}</td>
//        </tr>`;
//        tableBody.innerHTML += row;
//    });
//}
function updateMaintenanceTable(maintenanceList) {
    let tableBody = document.querySelector("tbody");
    let pagination = document.querySelector(".pagination"); // Lấy phần phân trang
    let noDataMessage = document.getElementById("noDataMessage");

    tableBody.innerHTML = "";

    if (maintenanceList.length === 0) {
        // Nếu không có dữ liệu, hiển thị thông báo
        if (!noDataMessage) {
            noDataMessage = document.createElement("p");
            noDataMessage.id = "noDataMessage";
            noDataMessage.className = "text-center text-danger fw-bold";
            noDataMessage.innerText = "No Data";
            tableBody.parentElement.appendChild(noDataMessage);
        }
        // Ẩn phân trang nếu có
        if (pagination) pagination.style.display = "none";
        return;
    }

    // Nếu có dữ liệu, xóa thông báo "Không có dữ liệu"
    if (noDataMessage) {
        noDataMessage.remove();
    }

    maintenanceList.forEach(maintenance => {
        let actionButton = "";
        if (maintenance.maintenanceStatus !== "Processed") {
            actionButton = `<button class="btn btn-update" data-id="${maintenance.id}">Update</button>`;
        }

        let row = `<tr>
            <td>${maintenance.id}</td>
            <td>${maintenance.airplane.airplaneName}</td>
            <td>${maintenance.maintenanceDate}</td>
            <td>${maintenance.duration}</td>
            <td>${maintenance.completionDate}</td>
            <td>${maintenance.description}</td>
            <td>
                <span class="status-badge ${getStatusClass(maintenance.maintenanceStatus)}">
                    ${maintenance.maintenanceStatus}
                </span>
            </td>
            <td>${actionButton}</td>
        </tr>`;
        tableBody.innerHTML += row;
    });

    // Hiển thị phân trang nếu có dữ liệu
    if (pagination) pagination.style.display = "flex";
}



// 🔄 Reset danh sách bảo trì
function resetMaintenanceList() {
    console.log("🔄 Resetting maintenance list...");

    fetch(`/admin/maintenance/list/json`)
        .then(response => response.json())
        .then(data => {
            updateMaintenanceTable(data);
        })
        .catch(error => {
            console.error("Error resetting maintenance list:", error);
            showNotification("Failed to reset list!", "error");
        });
}

// 🛠 Hiển thị thông báo
// 🛠 Hiển thị thông báo dạng toast
function showNotification(message, type) {
    let toast = document.getElementById("notification-toast");
    let messageContainer = document.getElementById("notification-message");

    if (!toast || !messageContainer) {
        console.error("❌ Notification elements not found!");
        return;
    }

    // Set màu nền cho toast dựa vào loại thông báo
    toast.classList.remove("bg-success", "bg-danger");
    toast.classList.add(type === "success" ? "bg-success" : "bg-danger");

    // Hiển thị nội dung thông báo
    messageContainer.innerText = message;

    // Hiển thị toast (dùng Bootstrap Toast)
    let toastInstance = new bootstrap.Toast(toast);
    toastInstance.show();

    // Ẩn sau 3 giây
    setTimeout(() => toastInstance.hide(), 3000);
}


// 🎯 Xử lý update modal
function openUpdateModal(id) {
    console.log("🔄 Opening update modal for ID:", id);
    fetch(`/admin/maintenance/get/${id}`)
        .then(response => response.json())
        .then(data => {
            if (data) {
                document.getElementById("maintenanceId").value = data.id;
                document.getElementById("maintenanceStatus").value = data.maintenanceStatus;
                document.getElementById("maintenanceDescription").value = data.description;
                document.getElementById("maintenanceDuration").value = data.duration;

                let modalElement = document.getElementById("updateMaintenanceModal");
                let modal = new bootstrap.Modal(modalElement);
                modal.show();
            } else {
                showNotification("Maintenance data not found!", "error");
            }
        })
        .catch(error => {
            console.error("Error fetching maintenance data:", error);
            showNotification("Failed to load maintenance data!", "error");
        });
}

// 🏷️ Trả về class CSS cho trạng thái
function getStatusClass(status) {
    return {
        "Unprocessed": "status-unprocessed",
        "Is_Processing": "status-processing",
        "Processed": "status-processed"
    }[status] || "";
}

// 🚀 Submit form update
document.addEventListener("DOMContentLoaded", function () {
    let updateForm = document.getElementById("updateMaintenanceForm");

    if (updateForm) {
        updateForm.addEventListener("submit", function (event) {
            event.preventDefault(); // Ngăn chặn load lại trang mặc định

            let maintenanceId = document.getElementById("maintenanceId").value;
            let maintenanceStatus = document.getElementById("maintenanceStatus").value;
            let maintenanceDescription = document.getElementById("maintenanceDescription").value;
            let maintenanceDuration = document.getElementById("maintenanceDuration").value;

            let requestData = {
                id: maintenanceId,
                maintenanceStatus: maintenanceStatus,
                description: maintenanceDescription,
                duration: maintenanceDuration
            };

            fetch(`/admin/maintenance/update/${maintenanceId}`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(requestData)
            })
                .then(response => response.json())
                .then(data => {
                    if (data.success) {
                        showNotification("✅ Maintenance updated successfully!", "success");

                        // 🔴 Đóng modal sau khi update thành công
                        let modalElement = document.getElementById("updateMaintenanceModal");
                        let modal = bootstrap.Modal.getInstance(modalElement);
                        if (modal) {
                            modal.hide();
                        }

                        resetMaintenanceList(); // 🔄 Reload lại danh sách
                    } else {
                        showNotification("⚠ Update failed!", "error");
                    }
                })
                .catch(error => {
                    console.error("Error updating maintenance:", error);
                    showNotification("❌ An error occurred!", "error");
                });
        });
    } else {
        console.error("❌ Update form not found!");
    }
});

