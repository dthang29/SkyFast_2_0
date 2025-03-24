document.addEventListener("DOMContentLoaded", function () {
    console.log("🚀 Script loaded and DOM is ready!");
    attachEventListeners();
});

// Gán lại sự kiện khi nội dung trang thay đổi
function attachEventListeners() {
    console.log("🎯 Attaching event listeners...");

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

// Mở modal cập nhật hạng ghế
function openUpdateModal(id) {
    console.log(`🔄 Opening update modal for ID: ${id}`);
    fetch(`/admin/seatCategory/edit/${id}`)
        .then(response => response.json())
        .then(data => {
            if (data) {
                // Gán dữ liệu vào các input trong modal
                document.getElementById("updateId").value = data.id;
                document.getElementById("updateName").value = data.name;  // Tên hạng ghế không được phép sửa
                document.getElementById("updateTotalSeats").value = data.totalSeats;
                document.getElementById("updateSurcharge").value = data.surcharge;
                document.getElementById("updateDescription").value = data.description;

                // Hiển thị ảnh hiện tại nếu có
                document.getElementById("updateCurrentImage").src = data.image || '/default-image.png';

                // Mở modal
                let modalElement = document.getElementById("updateModal");
                let modal = new bootstrap.Modal(modalElement);
                modal.show();
            } else {
                showNotification("Seat Category data not found!", "error");
            }
        })
        .catch(error => {
            console.error("Error fetching seat category data:", error);
            showNotification("Failed to load seat category data!", "error");
        });
}


// Hiển thị thông báo thành công hoặc lỗi
function showNotification(message, type) {
    let toast = document.getElementById("notification-toast");
    let messageContainer = document.getElementById("notification-message");

    if (!toast || !messageContainer) {
        console.error("❌ Notification elements not found!");
        return;
    }

    toast.classList.remove("bg-success", "bg-danger");
    toast.classList.add(type === "success" ? "bg-success" : "bg-danger");
    messageContainer.innerText = message;

    let toastInstance = new bootstrap.Toast(toast);
    toastInstance.show();

    setTimeout(() => {
        toastInstance.hide();
    }, 3000);
}
