document.addEventListener("DOMContentLoaded", function () {
    let statusMessage = document.getElementById("statusMessage");
    if (!statusMessage) {
        console.warn("⚠️ statusMessage does not exist when the DOM loads, please check the HTML!");
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

        fetch(`/staff/refund/search?${queryParams.toString()}`)
            .then(response => response.json())
            .then(data => updateRefundTable(data))
            .catch(error => {
                console.error("Error searching refunds:", error);
                showToast("Error searching for refund!", "error");
            });
    }

    function resetFilters() {
        document.querySelector("select[name='status']").value = "";
        document.querySelectorAll("input[type='date']").forEach(input => input.value = "");
    }

    function resetRefundList() {
        fetch(`/staff/refund/list`)
            .then(response => response.text())
            .then(html => {
                let parser = new DOMParser();
                let doc = parser.parseFromString(html, "text/html");
                document.querySelector(".content-container").innerHTML = doc.querySelector(".content-container").innerHTML;
                attachEventListeners(); // Gán lại sự kiện sau khi cập nhật DOM
            })
            .catch(error => {
                console.error("Error resetting refund list:", error);
                showToast("Error resetting the refund list!", "error");
            });
    }
    function updateRefundTable(refundList) {
        let tableBody = document.querySelector("tbody");
        tableBody.innerHTML = "";

        refundList.forEach(refund => {
            // Lưu ý: Dữ liệu bookingCode phải được trả về trong JSON => refund.booking.bookingCode
            let row = `<tr>
            <!-- Booking Code -->
            <td>${refund.booking?.bookingCode ?? ""}</td>

            <!-- Bank -->
            <td>${refund.bank ?? ""}</td>

            <!-- Bank Number -->
            <td>${refund.bankNumber ?? ""}</td>

            <!-- Request Date -->
            <td>${formatDate(refund.requestDate)}</td>

            <!-- Reason -->
            <td>${refund.reason ?? ""}</td>

            <!-- Refund Price -->
            <td>${refund.refundPrice ?? ""}</td>

            <!-- Refund Date -->
            <td>${formatDate(refund.refundDate)}</td>

            <!-- Response -->
            <td>${refund.response ?? ""}</td>

            <!-- Status -->
            <td><span class="badge bg-info">${refund.status}</span></td>

            <!-- Action -->
            <td>
                ${refund.status !== "Processed"
                ? `<button class="btn btn-warning btn-change-status"
                               data-id="${refund.id}" data-status="${refund.status}">
                          Change Status
                       </button>`
                : ""
            }
            </td>
        </tr>`;
            tableBody.innerHTML += row;
        });

        // Gán lại sự kiện cho các nút sau khi cập nhật bảng
        attachEventListeners();
    }


    function showChangeStatusModal(target) {
        let refundId = target.getAttribute("data-id");
        document.getElementById("selectedRefundId").value = refundId;

        // Reset form modal (xóa radio checked và input reason)
        document.querySelectorAll("input[name='refundStatus']").forEach(radio => radio.checked = false);
        document.getElementById("refundReason").value = "";

        let modal = bootstrap.Modal.getOrCreateInstance(document.getElementById("changeStatusModal"));
        modal.show();
    }

    function updateRefundStatus() {
        let refundId = document.getElementById("selectedRefundId").value;
        let selectedRadio = document.querySelector("input[name='refundStatus']:checked");
        if (!selectedRadio) {
            showToast("Please select either Approve or Reject status", "error");
            return;
        }
        let newStatus = selectedRadio.value;
        let reason = document.getElementById("refundReason").value;

        fetch(`/staff/refund/updateStatus/${refundId}`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ status: newStatus, reason: reason })
        })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    closeModalAndShowToast("Status updated successfully!", "success");
                    setTimeout(() => location.reload(), 2000);
                } else {
                    showToast("Update failed!", "error");
                }
            })
            .catch(error => {
                console.error("Error updating status:", error);
                showToast("System error, please try again later!", "error");
            });
    }

    function closeModalAndShowToast(message, type) {
        let modal = bootstrap.Modal.getOrCreateInstance(document.getElementById("changeStatusModal"));
        modal.hide();
        showToast(message, type);
    }

    function showToast(message, type) {
        let toast = document.getElementById("notification-toast");
        let messageContainer = document.getElementById("notification-message");

        if (!toast || !messageContainer) {
            console.error("Notification element not found!");
            return;
        }

        toast.classList.remove("bg-danger", "bg-success");
        toast.classList.add(type === "success" ? "bg-success" : "bg-danger");

        messageContainer.innerText = message;
        toast.style.display = "block";

        setTimeout(() => {
            toast.style.display = "none";
        }, 2000);
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