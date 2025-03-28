// function submitForm() {
//     // Tạo một form ẩn
//     const form = document.createElement("form");
//     form.method = "GET";  // Hoặc "GET" nếu backend xử lý theo GET
//     form.action = "/test";  // Đường dẫn backend xử lý
//
//     // Danh sách các input cần gửi
//     const data = {
//         title: document.querySelector("select[name='title']").value,
//         lastName: document.querySelector("input[name='LastName']").value,
//         firstName: document.querySelector("input[name='FirstName']").value,
//         nationality: document.querySelector("input[name='nationality']").value,
//         email: document.querySelector("input[name='email']").value,
//         phoneNumber: document.querySelector("input[name='phoneNumber']").value,
//         baggageQuantity: document.querySelector("select[name='baggageQuantity']").value,
//         baggage: document.querySelector("select[name='baggage']").value
//     };
//
//     // Thêm từng input vào form
//     for (const key in data) {
//         if (data.hasOwnProperty(key)) {
//             const input = document.createElement("input");
//             input.type = "hidden";
//             input.name = key;
//             input.value = data[key];
//             form.appendChild(input);
//         }
//     }
//
//     // Thêm form vào body và submit
//     document.body.appendChild(form);
//     form.submit();
// }
//
//


//     function submitAllForms() {
//     document.getElementById("mainForm").submit();
// }

function submitPayment(event, method) {
    event.preventDefault(); // Ngăn chặn hành động mặc định của nút submit
    document.getElementById("method").value = method; // Gán giá trị vào input hidden
    event.target.form.submit(); // Submit form
}

function submitBothForms(event) {
    event.preventDefault(); // Ngăn chặn hành vi mặc định của form dưới

    const form1 = document.getElementById("form1");
    const form2 = document.getElementById("form2");

    // Lấy tất cả input từ form1
    const inputs = form1.querySelectorAll("input, select, textarea");

    // Thêm dữ liệu của form1 vào form2
    inputs.forEach(input => {
        if (input.name && input.value) { // Chỉ lấy input có name và value
            const hiddenInput = document.createElement("input");
            hiddenInput.type = "hidden";
            hiddenInput.name = input.name;
            hiddenInput.value = input.value;
            form2.appendChild(hiddenInput);
        }
    });

    // Sau khi đã thêm dữ liệu vào form2, submit cả hai form
    form1.submit();
    form2.submit();
}


