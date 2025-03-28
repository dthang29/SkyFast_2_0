function hideBooking(button) {
    let row = button.closest("tr"); // Tìm hàng chứa nút được nhấn
    row.style.display = "none"; // Ẩn hàng đó đi
}