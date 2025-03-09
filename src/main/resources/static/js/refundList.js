document.addEventListener("DOMContentLoaded", function () {
let statusMessage = document.getElementById("statusMessage");
    if (!statusMessage) {
        console.warn("⚠️ statusMessage không tồn tại khi DOM load, kiểm tra lại HTML!");
    }
    attachEventListeners();

    function attachEventListeners() {
        document.addEventListener("click", function (event) {
            let target = event.target;

            // Xử lý sự kiện tìm kiếm
            if (target.classList.contains("btn-primary")) {
                event.preventDefault();
                searchRefunds();
            }

            // Xử lý sự kiện reset
            if (target.id === "resetSearch") {
                event.preventDefault();
                resetFilters();
                resetRefundList();
            }

            // Xử lý sự kiện thay đổi trạng thái
            if (target.classList.contains("btn-change-status")) {
                showChangeStatusModal(target);
            }

            // Xử lý xác nhận thay đổi trạng thái
            if (target.id === "confirmChangeStatus") {
                updateRefundStatus();
            }
        });
    }

    function searchRefunds() {
        let status = document.querySelector("select[name='status']").value;
        let fromRequestDate = document.querySelector("input[name='fromDate']").value;
        let toRequestDate = document.querySelector("input[name='toDate']").value;
        let fromRefundDate = document.querySelector("input[name='fromRefundDate']").value;
        let toRefundDate = document.querySelector("input[name='toRefundDate']").value;

        let queryParams = new URLSearchParams();
        if (status !== "") queryParams.append("status", status);
        if (fromRequestDate) queryParams.append("fromRequestDate", fromRequestDate);
        if (toRequestDate) queryParams.append("toRequestDate", toRequestDate);
        if (fromRefundDate) queryParams.append("fromRefundDate", fromRefundDate);
        if (toRefundDate) queryParams.append("toRefundDate", toRefundDate);

        fetch(`/refund/search?${queryParams.toString()}`)
            .then(response => response.json())
            .then(data => updateRefundTable(data))
            .catch(error => {
                console.error("Error searching refunds:", error);
                showToast("Lỗi tìm kiếm refund!", "error");
            });
    }

    function resetFilters() {
        document.querySelector("select[name='status']").value = "";
        document.querySelectorAll("input[type='date']").forEach(input => input.value = "");
    }

    function resetRefundList() {
        fetch(`/refund/list`)
            .then(response => response.text())
            .then(html => {
                let parser = new DOMParser();
                let doc = parser.parseFromString(html, "text/html");
                document.querySelector(".content-container").innerHTML = doc.querySelector(".content-container").innerHTML;
                attachEventListeners(); // Gán lại sự kiện sau khi cập nhật DOM
            })
            .catch(error => {
                console.error("Error resetting refund list:", error);
                showToast("Lỗi khi reset danh sách refund!", "error");
            });
    }

    function updateRefundTable(refundList) {
        let tableBody = document.querySelector("tbody");
        tableBody.innerHTML = "";
        refundList.forEach(refund => {
            let row = `<tr>
                <td>${formatDate(refund.requestDate)}</td>
                <td>${formatDate(refund.refundDate)}</td>
                <td>${refund.bank}</td>
                <td>${refund.bankNumber}</td>
                <td>${refund.refundPrice}</td>
                <td><span class="badge bg-info">${refund.status}</span></td>
                <td>
                    ${refund.status !== "Processed" ? `<button class="btn btn-warning btn-change-status" data-id="${refund.id}" data-status="${refund.status}">Change Status</button>` : ''}
                </td>
            </tr>`;
            tableBody.innerHTML += row;
        });
        attachEventListeners(); // Gán lại sự kiện sau khi cập nhật danh sách
    }

    function showChangeStatusModal(target) {
        let refundId = target.getAttribute("data-id");
        let currentStatus = target.getAttribute("data-status");
        let newStatus = "";

        if (currentStatus === "Unprocessed") {
            newStatus = "Is_Processing";
            document.getElementById("statusMessage").innerText = "Bạn có muốn chuyển sang Is_Processing?";
        } else if (currentStatus === "Is_Processing") {
            newStatus = "Processed";
            document.getElementById("statusMessage").innerText = "Bạn có muốn chuyển sang Processed?";
        }

        document.getElementById("selectedRefundId").value = refundId;
        document.getElementById("newStatus").value = newStatus;

        let modal = bootstrap.Modal.getOrCreateInstance(document.getElementById("changeStatusModal"));
        modal.show();
    }

    function updateRefundStatus() {
        let refundId = document.getElementById("selectedRefundId").value;
        let newStatus = document.getElementById("newStatus").value;

        fetch(`/refund/updateStatus/${refundId}`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ status: newStatus })
        })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                closeModalAndShowToast("Trạng thái đã cập nhật thành công!", "success");
                setTimeout(() => location.reload(), 2000);
            } else {
                showToast("Cập nhật thất bại!", "error");
            }
        })
        .catch(error => {
            console.error("Lỗi cập nhật trạng thái:", error);
            showToast("Lỗi hệ thống, thử lại sau!", "error");
        });
    }

    function closeModalAndShowToast(message, type) {
        let modal = bootstrap.Modal.getOrCreateInstance(document.getElementById("changeStatusModal"));
        modal.hide(); // Đóng modal ngay lập tức
        showToast(message, type);
    }

    function showToast(message, type) {
        let toast = document.getElementById("notification-toast");
        let messageContainer = document.getElementById("notification-message");

        if (!toast || !messageContainer) {
            console.error("Không tìm thấy phần tử thông báo!");
            return;
        }

        toast.classList.remove("bg-danger", "bg-success");
        toast.classList.add(type === "success" ? "bg-success" : "bg-danger");

        messageContainer.innerText = message;
        toast.style.display = "block";

        setTimeout(() => {
            toast.style.display = "none";
        }, 2000); // Ẩn toast sau 2 giây
    }

    function formatDate(dateString) {
        if (!dateString) return "";
        let date = new Date(dateString);
        let day = String(date.getDate()).padStart(2, '0');
        let month = String(date.getMonth() + 1).padStart(2, '0');
        let year = date.getFullYear();
        return `${day}/${month}/${year}`;
    }

});