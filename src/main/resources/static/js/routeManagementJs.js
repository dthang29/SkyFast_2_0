document.querySelectorAll("#searchDepartureName, #searchArrivalName").forEach(input => {
    input.addEventListener("input", filterTable);
});

function filterTable() {
    let searchDepartureName = document.getElementById("searchDepartureName").value.toLowerCase();
    let searchArrivalName = document.getElementById("searchArrivalName").value.toLowerCase();



    document.querySelectorAll("#routeTableBody tr").forEach(row => {
        let DepartureName = row.querySelector(".departure-name").textContent.toLowerCase();
        let ArrivalName = row.querySelector(".arrival-name").textContent.toLowerCase();



        let matchesDepartureName = !searchDepartureName || DepartureName.includes(searchDepartureName );
        let matchesArrivalName = !searchArrivalName || ArrivalName.includes(searchArrivalName);



        row.style.display = (matchesDepartureName && matchesArrivalName) ? "" : "none";
    });
}