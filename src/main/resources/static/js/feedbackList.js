document.addEventListener("DOMContentLoaded", function() {
    let searchBtn = document.getElementById("searchBtn");
    if (searchBtn) {
        searchBtn.addEventListener("click", function(e) {
            e.preventDefault();
            searchFeedback();
        });
    }

    function searchFeedback() {
        let username = document.querySelector("input[name='username']").value;
        let flightCode = document.querySelector("input[name='flightCode']").value;
        let ratingStr = document.querySelector("input[name='rating']").value; // kiểu string
        let rating = ratingStr ? parseInt(ratingStr) : null; // parse ra int

        // Tạo query params
        let queryParams = new URLSearchParams();
        if (username) queryParams.append("username", username);
        if (flightCode) queryParams.append("flightCode", flightCode);
        if (rating) queryParams.append("rating", rating);

        // Gọi API search (trả về JSON)
        fetch("/staff/feedback/search?" + queryParams.toString())
            .then(response => response.json())
            .then(data => updateFeedbackTable(data))
            .catch(error => console.error("Error searching feedback:", error));
    }

    function updateFeedbackTable(feedbackList) {
        let tableBody = document.querySelector("tbody");
        tableBody.innerHTML = ""; // Xoá dữ liệu cũ

        feedbackList.forEach(fb => {
            let row = `
                <tr>
                    <td>${fb.user ? fb.user.userName : ''}</td>
                    <td>${fb.flight ? fb.flight.flightNumber : ''}</td>
                    <td>${formatDate(fb.feedbackDate)}</td>
                    <td>${fb.rating != null ? fb.rating : ''}</td>
                    <td>${fb.comments ?? ''}</td>
                </tr>`;
            tableBody.innerHTML += row;
        });
    }

    function formatDate(dateString) {
        if (!dateString) return "";
        let date = new Date(dateString);
        let day = String(date.getDate()).padStart(2, '0');
        let month = String(date.getMonth() + 1).padStart(2, '0');
        let year = date.getFullYear();
        return `${year}-${month}-${day}`;
    }
});
