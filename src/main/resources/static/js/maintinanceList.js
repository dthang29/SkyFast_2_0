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

// 🔍 Xử lý tìm kiếm
function searchMaintenance() {
    console.log("🔍 Searching maintenance records...");
    let airplaneElement = document.getElementById("airplaneInfo");
    let airplaneId = airplaneElement ? airplaneElement.getAttribute("data-airplane-id") : null;

    if (!airplaneId) {
        console.error("🚨 Airplane ID is null! Check Thymeleaf rendering.");
        return;
    }

    let status = document.querySelector("select[name='status']").value;
    let fromDate = document.querySelector("input[name='fromDate']").value;
    let toDate = document.querySelector("input[name='toDate']").value;

    let queryParams = new URLSearchParams({
        airplaneId: airplaneId,
        status: status,
        fromDate: fromDate,
        toDate: toDate
    });

    fetch(`/maintenance/search?${queryParams.toString()}`)
        .then(response => response.json())
        .then(data => {
            updateMaintenanceTable(data);
        })
        .catch(error => {
            console.error("Error searching maintenance:", error);
            showNotification("Failed to search maintenance!", "error");
        });
}

function updateMaintenanceTable(maintenanceList) {
    let tableBody = document.querySelector("tbody");
    tableBody.innerHTML = "";

    maintenanceList.forEach(maintenance => {
        let actionButton = "";
        if (maintenance.maintenanceStatus !== "Processed") {
            actionButton = `<button class="btn btn-update" data-id="${maintenance.id}">Update</button>`;
        }

        let row = `<tr>
            <td>${maintenance.maintenanceDate}</td>
            <td>${maintenance.duration}</td>
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
}

// 🔄 Reset danh sách bảo trì
function resetMaintenanceList() {
    console.log("🔄 Resetting maintenance list...");
    let airplaneId = document.getElementById("airplaneInfo").getAttribute("data-airplane-id");

    if (!airplaneId) {
        console.error("❌ Airplane ID not found!");
        showNotification("Error resetting list!", "error");
        return;
    }

    fetch(`/maintenance/list/json/${airplaneId}`)
        .then(response => response.json())
        .then(data => {
            updateMaintenanceTable(data);
        })
        .catch(error => {
            console.error("Error resetting maintenance list:", error);
            showNotification("Failed to reset list!", "error");
        });
}

function showNotification(message, type) {
    let toast = document.getElementById("notification-toast");
    let messageContainer = document.getElementById("notification-message");

    if (!toast || !messageContainer) {
        console.error("❌ Notification elements not found!");
        return;
    }

    console.log("🔔 Showing notification:", message, "Type:", type);

    // Set màu nền cho toast dựa vào loại thông báo
    toast.classList.remove("bg-success", "bg-danger");
    toast.classList.add(type === "success" ? "bg-success" : "bg-danger");

    // Hiển thị nội dung thông báo
    messageContainer.innerText = message;

    // Hiển thị toast (dùng Bootstrap Toast)
    let toastInstance = new bootstrap.Toast(toast);
    toastInstance.show();

    // Ẩn sau 3 giây
    setTimeout(() => {
        console.log("🔕 Hiding toast...");
        toastInstance.hide();
    }, 3000);
}


// 🎯 Xử lý update modal
function openUpdateModal(id) {
    console.log("🔄 Opening update modal for ID:", id);
    fetch(`/maintenance/get/${id}`)
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

            fetch(`/maintenance/update/${maintenanceId}`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(requestData)
            })
                .then(response => response.json())
                .then(data => {
                    if (data.success) {
                        showNotification("Maintenance updated successfully!", "success");
                        document.querySelector(".btn-close").click(); // Đóng modal
                        resetMaintenanceList(); // Reload lại danh sách
                    } else {
                        showNotification("Update failed!", "error");
                    }
                })
                .catch(error => {
                    console.error("Error updating maintenance:", error);
                    showNotification("An error occurred!", "error");
                });
        });
    } else {
        console.error("❌ Update form not found!");
    }
});
