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