document.querySelectorAll("#searchAirlineName, #searchPromotionStatus, #searchPromotionCode").forEach(input => {
    input.addEventListener("input", filterTable);
    input.addEventListener("change", filterTable); // Cho thẻ select
});

function filterTable() {
    let searchName = document.getElementById("searchAirlineName").value.trim().toLowerCase();
    let searchStatus = document.getElementById("searchPromotionStatus").value.trim().toUpperCase(); // Chuyển về chữ hoa
    let searchCode = document.getElementById("searchPromotionCode").value.trim().toLowerCase();

    document.querySelectorAll("#promotionTableBody tr").forEach(row => {
        let airlineName = row.querySelector(".airline-name").textContent.trim().toLowerCase();
        let promotionStatus = row.querySelector(".promotion-status").textContent.trim().toUpperCase(); // Chuyển về chữ hoa
        let promotionCode = row.querySelector(".promotion-code").textContent.trim().toLowerCase();

        let matchesName = !searchName || airlineName.includes(searchName);
        let matchesStatus = !searchStatus || promotionStatus === searchStatus; // So sánh trực tiếp
        let matchesCode = !searchCode || promotionCode.includes(searchCode);

        row.style.display = (matchesName && matchesStatus && matchesCode) ? "" : "none";
    });
}

document.addEventListener("DOMContentLoaded", function () {
    const rowsPerPage = 10; // Số booking hiển thị mỗi trang
    let currentPage = 1;

    const tableRows = Array.from(document.querySelectorAll("#ticketTableBody tr"));
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


