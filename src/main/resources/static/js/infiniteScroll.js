document.addEventListener('DOMContentLoaded', () => {
    let page = 0;
    const size = 4;
    let searchQuery = '';

    // 검색 폼 이벤트 리스너
    const searchForm = document.getElementById('search-form');
    const searchInput = document.getElementById('search-input');

    if (searchForm) {
        searchForm.addEventListener('submit', (event) => {
            event.preventDefault();
            searchQuery = searchInput.value;
            page = -1; // 검색을 새로 시작할 때마다 페이지 번호를 초기화
            document.getElementById('storesContainer').innerHTML = ''; // 컨테이너 초기화
            fetchStores(); // 검색 결과로 스토어를 불러오기
        });
    }

    const observer = new IntersectionObserver(entries => {
        if (entries[0].isIntersecting) {
            fetchStores();
        }
    }, {threshold: 1.0});

    observer.observe(document.getElementById('loadMore'));

    function fetchStores() {
        page++;
        // 검색 쿼리를 포함한 URL 생성
        const fetchURL = `/stores/slice?page=${page}&size=${size}&query=${encodeURIComponent(searchQuery)}`;

        console.log(fetchURL)

        fetch(fetchURL)
            .then(response => response.json())
            .then(data => {
                if (data.content.length > 0) {
                    data.content.forEach(store => {
                        const storeItem = document.createElement('div');
                        storeItem.classList.add('store-item');
                        storeItem.innerHTML = `
                            <a href="/stores/${store.id}" class="store-link">
                                <h2>${store.name}</h2>
                                <p>카테고리: ${store.categoryName}</p>
                                <p>수용 인원: ${store.capacity}</p>
                                <p>오픈 시간: ${store.openTime}</p>
                                <p>마감 시간: ${store.closeTime}</p>
                                <p>라스트오더: ${store.lastOrderTime}</p>
                                <p>연락처: ${store.phone}</p>
                                <p>${store.description}</p>
                            </a>
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
});
