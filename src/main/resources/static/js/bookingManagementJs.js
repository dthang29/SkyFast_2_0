document.querySelectorAll("#searchBookingDate, #searchBookingStatus, #searchUserName").forEach(input => {
    input.addEventListener("input", filterTable);
    input.addEventListener("change", filterTable); // Cho thẻ select và input date
});

function filterTable() {
    let searchDate = document.getElementById("searchBookingDate").value; // Format: YYYY-MM-DD
    let searchStatus = document.getElementById("searchBookingStatus").value.toLowerCase();
    let searchUserName = document.getElementById("searchUserName").value.toLowerCase();


    document.querySelectorAll("#bookingTableBody tr").forEach(row => {
        let bookingDate = row.querySelector(".booking-date").textContent.trim(); // Format: DD/MM/YYYY hoặc YYYY-MM-DD
        let bookingStatus = row.querySelector(".booking-status").textContent.toLowerCase();
        let userName = row.querySelector(".user-name").textContent.toLowerCase();


        let matchesDate = !searchDate || compareDates(bookingDate, searchDate);
        let matchesStatus = !searchStatus || bookingStatus.includes(searchStatus);
        let matchesUserName = !searchUserName || userName.includes(searchUserName);


        row.style.display = (matchesDate && matchesStatus && matchesUserName ) ? "" : "none";
    });
}

// Hàm so sánh ngày (Chuyển đổi định dạng từ DD/MM/YYYY -> YYYY-MM-DD nếu cần)
function compareDates(bookingDate, searchDate) {
    let formattedBookingDate = formatDateString(bookingDate);
    return formattedBookingDate === searchDate;
}

// Chuyển đổi ngày từ DD/MM/YYYY thành YYYY-MM-DD (nếu dữ liệu trong bảng là DD/MM/YYYY)
function formatDateString(dateStr) {
    if (dateStr.includes("/")) {
        let parts = dateStr.split("/");
        return `${parts[2]}-${parts[1]}-${parts[0]}`; // Chuyển từ DD/MM/YYYY sang YYYY-MM-DD
    }
    return dateStr; // Nếu đã là YYYY-MM-DD thì giữ nguyên
}

document.addEventListener("DOMContentLoaded", function() {
    let filterBookingId = "";
    if (filterBookingId) {
        document.getElementById("searchBookingId").value = filterBookingId;
        document.getElementById("searchBookingId").dispatchEvent(new Event("input"));
    }
});

document.addEventListener("DOMContentLoaded", function () {
    const rowsPerPage = 10; // Số booking hiển thị mỗi trang
    let currentPage = 1;

    const tableRows = Array.from(document.querySelectorAll("#bookingTableBody tr"));
    const totalPages = Math.ceil(tableRows.length / rowsPerPage);

    function showPage(page) {
        let start = (page - 1) * rowsPerPage;
        let end = start + rowsPerPage;

        tableRows.forEach((row, index) => {
            row.style.display = (index >= start && index < end) ? "" : "none";
        });

        document.getElementById("pageInfo").textContent = `Page ${page} of ${totalPages}`;
        document.getElementById("prevPage").disabled = (page === 1);
        document.getElementById("nextPage").disabled = (page === totalPages);
    }

    document.getElementById("prevPage").addEventListener("click", function () {
        if (currentPage > 1) {
            currentPage--;
            showPage(currentPage);
        }
    });

    document.getElementById("nextPage").addEventListener("click", function () {
        if (currentPage < totalPages) {
            currentPage++;
            showPage(currentPage);
        }
    });

    showPage(currentPage); // Hiển thị trang đầu tiên khi tải trang
});
