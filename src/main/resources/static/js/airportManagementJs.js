document.querySelectorAll("#searchAirportName, #searchAirportCode").forEach(input => {
    input.addEventListener("input", filterTable);
});

function filterTable() {
    let searchAirportName = document.getElementById("searchAirportName").value.trim().toUpperCase()
    let searchAirportCode = document.getElementById("searchAirportCode").value.toLowerCase();



    document.querySelectorAll("#airportTableBody tr").forEach(row => {
        let AirportName = row.querySelector(".airport-name").textContent.trim().toUpperCase();
        let AirportCode = row.querySelector(".airport-code").textContent.toLowerCase();



        let matchesName = !searchAirportName || AirportName.includes(searchAirportName);
        let matchesCode = !searchAirportCode || AirportCode.includes(searchAirportCode);



        row.style.display = (matchesName && matchesCode) ? "" : "none";
    });
}