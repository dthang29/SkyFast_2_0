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
document.querySelectorAll('.book-now').forEach(button => {
    button.addEventListener('click', () => {
        alert('Booking functionality would go here!');
    });
});