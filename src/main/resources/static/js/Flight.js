// Price slider functionality
const priceSlider = document.querySelector('.price-slider');
const priceRange = document.querySelector('.price-range');

priceSlider.addEventListener('input', (e) => {
    const value = e.target.value;
    priceRange.textContent = `$0 - $${value}`;
});

// Clear all filters
const clearAllButton = document.querySelector('.clear-all');
clearAllButton.addEventListener('click', () => {
    // Reset checkboxes
    document.querySelectorAll('input[type="checkbox"]').forEach(checkbox => {
        checkbox.checked = false;
    });

    // Reset price slider
    priceSlider.value = 5000;
    priceRange.textContent = '$0 - $5000';

    // Reset location inputs
    document.querySelectorAll('.location-input').forEach(input => {
        input.value = '';
    });
});

// Pagination functionality
const paginationButtons = document.querySelectorAll('.pagination button');
paginationButtons.forEach(button => {
    button.addEventListener('click', () => {
        if (!button.disabled) {
            // Remove active class from all buttons
            paginationButtons.forEach(btn => btn.classList.remove('active'));
            // Add active class to clicked button
            button.classList.add('active');

            // Here you would typically fetch new data and update the flight listings
            console.log('Page changed to:', button.textContent);
        }
    });
});

// Book now button functionality
// document.querySelectorAll('.book-now').forEach(button => {
//     button.addEventListener('click', () => {
//         alert('Booking functionality would go here!');
//     });
// });

document.getElementById('round-trip').addEventListener('change', function () {
    var returnDate = document.getElementById('return-date');
    if (this.checked) {
        returnDate.style.display = 'block';
    } else {
        returnDate.style.display = 'none';
    }
});
document.addEventListener("click", function (event) {
    let isInput = event.target.matches(".destination");
    let isDropdown = event.target.closest(".dropdown-content");

    // Đóng tất cả dropdown trước
    document.querySelectorAll(".dropdown-content").forEach(dropdown => {
        dropdown.style.display = "none";
    });

    // Nếu bấm vào input, chỉ mở dropdown của nó
    if (isInput) {
        let dropdown = event.target.nextElementSibling;
        dropdown.style.display = "block";
    }
});

document.querySelectorAll(".destination").forEach(input => {
    input.addEventListener("input", function () {
        let filter = this.value.toLowerCase();
        let dropdown = this.nextElementSibling;
        let options = dropdown.querySelectorAll(".option");

        options.forEach(option => {
            option.style.display = option.textContent.toLowerCase().includes(filter) ? "block" : "none";
        });

        dropdown.style.display = "block";
    });
});

document.querySelectorAll(".dropdown-content .option").forEach(option => {
    option.addEventListener("click", function () {
        let input = this.closest(".form-group").querySelector(".destination");
        input.value = this.textContent;

        // Đóng dropdown sau khi chọn
        this.parentElement.style.display = "none";
    });
});

document.getElementById("class-input").addEventListener("click", function () {
    let dropdown = document.getElementById("class-dropdown");
    dropdown.style.display = "block";
});

// Xử lý chọn hạng ghế
document.querySelectorAll("#class-dropdown .option").forEach(option => {
    option.addEventListener("click", function () {
        document.getElementById("class-input").value = this.textContent;
        document.getElementById("class-dropdown").style.display = "none";
    });
});
// Đóng dropdown khi bấm ra ngoài
document.addEventListener("click", function (event) {
    let input = document.getElementById("class-input");
    let dropdown = document.getElementById("class-dropdown");

    if (!input.contains(event.target) && !dropdown.contains(event.target)) {
        dropdown.style.display = "none";
    }
});

document.addEventListener("DOMContentLoaded", function () {
    let passengerText = document.getElementById("passenger-count").value.trim();

    let matches = passengerText.match(/(\d+) Người lớn, (\d+) Trẻ em, (\d+) Em bé/);

    let adult = matches ? parseInt(matches[1]) : 1;
    let child = matches ? parseInt(matches[2]) : 0;
    let infant = matches ? parseInt(matches[3]) : 0;

    function updatePassengerText() {
        document.getElementById("passenger-count").value =
            `${adult} Người lớn, ${child} Trẻ em, ${infant} Em bé`;
    }

    function updateDropdown() {
        document.getElementById("adult-count").textContent = adult;
        document.getElementById("child-count").textContent = child;
        document.getElementById("infant-count").textContent = infant;
    }

    document.querySelectorAll(".increase, .decrease").forEach(button => {
        button.addEventListener("click", function () {
            let type = this.getAttribute("data-type");

            if (this.classList.contains("increase")) {
                if (adult + child + infant < 9) {
                    if (type === "adult") adult++;
                    if (type === "child") child++;
                    if (type === "infant") infant++;
                }
            } else {
                if (type === "adult" && adult > 1) adult--;
                if (type === "child" && child > 0) child--;
                if (type === "infant" && infant > 0) infant--;
            }

            updatePassengerText();
            updateDropdown();
        });
    });

    document.getElementById("passenger-count").addEventListener("click", function () {
        let dropdown = document.getElementById("passenger-dropdown");
        dropdown.style.display = dropdown.style.display === "block" ? "none" : "block";
    });

    document.addEventListener("click", function (event) {
        let input = document.getElementById("passenger-count");
        let dropdown = document.getElementById("passenger-dropdown");

        if (!input.contains(event.target) && !dropdown.contains(event.target)) {
            dropdown.style.display = "none";
        }
    });

    // ✅ Cập nhật UI ngay sau khi trang load
    updatePassengerText();
    updateDropdown();
});


document.getElementById("swap-btn").addEventListener("click", function () {
    let fromInput = document.getElementById("from-input");
    let toInput = document.getElementById("to-input");

    // Hoán đổi giá trị
    let temp = fromInput.value;
    fromInput.value = toInput.value;
    toInput.value = temp;
});

function submitFormAirline() {
    let form = document.getElementById("filterForm");
    let formData = new FormData(form);
    let params = new URLSearchParams(formData).toString();
    let scrollY = window.scrollY; // Lưu vị trí cuộn trang

    fetch("/flight?" + params, { method: "GET" })
        .then(response => response.text())
        .then(html => {
            let parser = new DOMParser();
            let doc = parser.parseFromString(html, "text/html");
            document.getElementById("flight-results").innerHTML =
                doc.getElementById("flight-results").innerHTML;
            window.scrollTo(0, scrollY); // Giữ nguyên vị trí cuộn
        })
        .catch(error => console.error("Error:", error));
}

function submitFormDeTime() {
    let form = document.getElementById("filterFormDe");
    let formData = new FormData(form);
    let params = new URLSearchParams(formData).toString();
    let scrollY = window.scrollY; // Giữ vị trí cuộn trang
    fetch("/flight?" + params, { method: "GET" })
        .then(response => response.text())
        .then(html => {
            let parser = new DOMParser();
            let doc = parser.parseFromString(html, "text/html");
            let newContent = doc.getElementById("flight-results");

            if (newContent) {
                document.getElementById("flight-results").innerHTML = newContent.innerHTML;
            } else {
                console.error("Element #flight-results not found in response.");
            }

            window.scrollTo(0, scrollY); // Giữ nguyên vị trí cuộn
        })
        .catch(error => console.error("Error:", error));
}

function submitFormArTime() {
    let form = document.getElementById("filterFormAr");
    let formData = new FormData(form);
    let params = new URLSearchParams(formData).toString();
    let scrollY = window.scrollY; // Giữ vị trí cuộn trang
    fetch("/flight?" + params, { method: "GET" })
        .then(response => response.text())
        .then(html => {
            let parser = new DOMParser();
            let doc = parser.parseFromString(html, "text/html");
            let newContent = doc.getElementById("flight-results");

            if (newContent) {
                document.getElementById("flight-results").innerHTML = newContent.innerHTML;
            } else {
                console.error("Element #flight-results not found in response.");
            }

            window.scrollTo(0, scrollY); // Giữ nguyên vị trí cuộn
        })
        .catch(error => console.error("Error:", error));
}




document.addEventListener("DOMContentLoaded", function () {
    const flights = Array.from(document.querySelectorAll(".flight-card"));
    const itemsPerPage = 6;
    let currentPage = 1;

    function showPage(page) {
        let start = (page - 1) * itemsPerPage;
        let end = start + itemsPerPage;

        flights.forEach((flight, index) => {
            flight.style.display = index >= start && index < end ? "block" : "none";
        });

        document.getElementById("prev").disabled = page === 1;
        document.getElementById("next").disabled = page === Math.ceil(flights.length / itemsPerPage);
    }

    function createPagination() {
        let pageNumbers = document.getElementById("page-numbers");
        pageNumbers.innerHTML = "";

        let totalPages = Math.ceil(flights.length / itemsPerPage);
        for (let i = 1; i <= totalPages; i++) {
            let btn = document.createElement("button");
            btn.textContent = i;
            btn.classList.add("page-btn");
            if (i === currentPage) btn.classList.add("active");
            btn.addEventListener("click", () => {
                currentPage = i;
                showPage(currentPage);
                updateActiveButton();
            });
            pageNumbers.appendChild(btn);
        }
    }

    function updateActiveButton() {
        document.querySelectorAll(".page-btn").forEach((btn, index) => {
            btn.classList.toggle("active", index + 1 === currentPage);
        });
    }

    document.getElementById("prev").addEventListener("click", () => {
        if (currentPage > 1) {
            currentPage--;
            showPage(currentPage);
            updateActiveButton();
        }
    });

    document.getElementById("next").addEventListener("click", () => {
        if (currentPage < Math.ceil(flights.length / itemsPerPage)) {
            currentPage++;
            showPage(currentPage);
            updateActiveButton();
        }
    });

    showPage(currentPage);
    createPagination();
});


document.addEventListener("DOMContentLoaded", function () {
    const clearAllBtn = document.querySelector(".clear-all");
    const searchBtn = document.querySelector(".search-btn"); // Lấy nút "Tìm chuyến bay"

    if (clearAllBtn) {
        clearAllBtn.addEventListener("click", function () {
            // Xóa tất cả các checkbox được chọn
            document.querySelectorAll(".checkbox-group input[type='checkbox']").forEach(checkbox => {
                checkbox.checked = false;
            });

            // Reset lại danh sách chuyến bay bằng cách mô phỏng nhấn "Tìm chuyến bay"
            if (searchBtn) {
                searchBtn.click(); // Giả lập click vào nút tìm kiếm
            }
        });
    }
});
