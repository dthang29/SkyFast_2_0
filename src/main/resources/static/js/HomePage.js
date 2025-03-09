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

document.addEventListener('DOMContentLoaded', () => {
    const tabBtns = document.querySelectorAll('.tab-btn');
    const tabContents = document.querySelectorAll('.tab-content');

    tabBtns.forEach(btn => {
        btn.addEventListener('click', () => {
            // Remove active class from all buttons and contents
            tabBtns.forEach(b => b.classList.remove('active'));
            tabContents.forEach(c => c.classList.remove('active'));

            // Add active class to clicked button and corresponding content
            btn.classList.add('active');
            const tabId = btn.getAttribute('data-tab');
            document.getElementById(tabId).classList.add('active');
        });
    });
});


document.addEventListener('DOMContentLoaded', function() {
    const cards = document.querySelectorAll('.card');

    // Staggered animation for cards
    cards.forEach((card, index) => {
        setTimeout(() => {
            card.style.opacity = '1';
            card.style.transform = 'translateY(0)';
        }, 100 * index);
    });


    // Add click event to cards
    cards.forEach(card => {
        card.addEventListener('click', function() {
            this.classList.add('pulse');
            setTimeout(() => {
                this.classList.remove('pulse');
            }, 500);
        });
    });
});

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