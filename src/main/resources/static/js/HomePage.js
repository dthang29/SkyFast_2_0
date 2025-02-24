function formatDate(date) {
    const options = { weekday: 'short', day: '2-digit', month: 'short', year: 'numeric' };
    return date.toLocaleDateString('en-US', options);
}

function updateDateButtons() {
    const today = new Date();
    const dateButtonsContainer = document.getElementById('date-buttons');

    dateButtonsContainer.innerHTML = ""; // Xóa nút cũ

    for (let i = 0; i < 3; i++) {
        let newDate = new Date();
        newDate.setDate(today.getDate() + i); // Hôm nay, mai, ngày kia

        let button = document.createElement("button");
        button.textContent = formatDate(newDate);

        if (i === 0) button.classList.add("active"); // Hôm nay là mặc định active

        button.addEventListener("click", function () {
            document.querySelectorAll(".date-selector button").forEach(btn => btn.classList.remove("active"));
            button.classList.add("active");
        });

        dateButtonsContainer.appendChild(button);
    }
}

updateDateButtons(); // Gọi khi trang tải