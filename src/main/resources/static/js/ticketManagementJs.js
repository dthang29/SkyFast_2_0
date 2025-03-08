document.querySelectorAll("#searchFlightNumber, #searchSeatNumber, #searchPassenger, #searchTicketStatus").forEach(input => {
    input.addEventListener("input", filterTable);
    input.addEventListener("change", filterTable); // Cho thẻ select
});

function filterTable() {
    let searchFlight = document.getElementById("searchFlightNumber").value.toLowerCase();
    let searchSeat = document.getElementById("searchSeatNumber").value.toLowerCase();
    let searchPassenger = document.getElementById("searchPassenger").value.toLowerCase();
    let searchStatus = document.getElementById("searchTicketStatus").value.toLowerCase();

    document.querySelectorAll("#ticketTableBody tr").forEach(row => {
        let flightNumber = row.querySelector(".flight-number").textContent.toLowerCase();
        let seatNumber = row.querySelector(".seat-number").textContent.toLowerCase();
        let passenger = row.querySelector(".passenger").textContent.toLowerCase();
        let ticketStatus = row.querySelector(".ticket-status").textContent.toLowerCase();

        let matchesFlight = !searchFlight || flightNumber.includes(searchFlight);
        let matchesSeat = !searchSeat || seatNumber.includes(searchSeat);
        let matchesPassenger = !searchPassenger || passenger.includes(searchPassenger);
        let matchesStatus = !searchStatus || ticketStatus.includes(searchStatus);

        row.style.display = (matchesFlight && matchesSeat && matchesPassenger && matchesStatus) ? "" : "none";
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