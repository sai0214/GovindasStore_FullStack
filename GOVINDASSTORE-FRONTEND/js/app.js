const API_URL = "http://localhost:8080/api";

const App = {
    init: () => {
        const params = new URLSearchParams(window.location.search);
        if (params.get('token')) {
            localStorage.setItem('jwtToken', params.get('token'));
            localStorage.setItem('userRole', params.get('role'));
            localStorage.setItem('userEmail', params.get('email') || "");
            window.history.replaceState({}, document.title, window.location.pathname);
        }
        if (!localStorage.getItem('jwtToken') && !window.location.pathname.includes('login.html')) {
            window.location.href = 'login.html';
        }
    },

    getHeaders: () => ({
        'Authorization': `Bearer ${localStorage.getItem('jwtToken')}`,
        'Content-Type': 'application/json'
    }),

    logout: () => {
        localStorage.clear();
        window.location.href = 'login.html';
    }
};

// Global Store Functions
async function addToCart(id) {
    const res = await fetch(`${API_URL}/cart/add/${id}`, { method: 'POST', headers: App.getHeaders() });
    if (res.ok) alert("Added to cart!");
    else {
        const err = await res.json();
        alert(err.message || "Error adding to cart");
    }
}