document.addEventListener('DOMContentLoaded', () => {
    console.log("DOM LOADED!")
    let page = 0;
    const size = 4; // 페이지 당 항목 수
    const observer = new IntersectionObserver(entries => {
        if (entries[0].isIntersecting) {
            console.log("intersecting")
            page++;
            fetch(`/stores/slice?page=${page}&size=${size}`)
                .then(response => response.json())
                .then(data => {
                    console.log(data)
                    if (data.content.length > 0) {
                        data.content.forEach(store => {
                            const storeItem = document.createElement('div');
                            storeItem.classList.add('store-item');
                            storeItem.innerHTML = `
                                <h2>${store.name}</h2>
                                <p>Category: ${store.categoryName}</p>
                                <p>Capacity: ${store.capacity}</p>
                                <p>Open Time: ${store.openTime}</p>
                                <p>Close Time: ${store.closeTime}</p>
                                <p>Last Order Time: ${store.lastOrderTime}</p>
                                <p>Phone: ${store.phone}</p>
                                <p>Address: ${store.city}, ${store.street}, ${store.zipcode}</p>
                                <p>${store.description}</p>
                            `;
                            document.getElementById('storesContainer').appendChild(storeItem);
                        });
                    } else {
                        observer.unobserve(document.getElementById('loadMore'));
                        document.getElementById('loadMore').innerText = 'No more stores to load';
                    }
                })
                .catch(error => console.error('Error:', error));
        }
    }, {threshold: 1.0});

    observer.observe(document.getElementById('loadMore'));
});
