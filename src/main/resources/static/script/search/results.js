import returnBookObjectFromJson from './modules/bookFunctions.js';

function returnObjectFieldsAsHtml(object) {
    let baseLink = /*[[@{}]]*/'';
    let linkName = `view/${object.googleId}`;
    let viewLink = baseLink + linkName;
    return `
            <a href="${viewLink}" class="viewLink">
                <img class="book" src="${object.thumbnail}">
            </a>
            <div class="book-info">
                <p class="title">${object.title}</p>
                <p class="author">${object.author}</p>
            </div>
    `;
}

let promises = [];
let totalResults = 0;

const API_ENDPOINT = `https://www.googleapis.com`;
const API_KEY = `AIzaSyDk87M-Tr5KQMeR2ZlCIjQ2nEsqiAo-uMg`;

let results = document.getElementById('results');
let pageNumbersContainer = document.querySelector('.page-numbers-container');
let pageNumbers = document.querySelectorAll('page-number');

// GO
const go = async () => {
    await printBooks(query, startIndex);
}

// PRINTBOOKS
const printBooks = async (query, startIndex) => {

    results.innerHTML = '';

    await retrieveBooks(query, startIndex);

    let books = promises[0].items;

    books.forEach(book => {
        book = returnBookObjectFromJson(book);
        let result = document.createElement('div');
        result.classList.add('result');
        result.innerHTML = returnObjectFieldsAsHtml(book);
        results.appendChild(result);
    })

    let pages = ((totalResults - (totalResults % 10)) / 10) + 1;
    await renderPageNumbers(pages, currentPage);

}

const retrieveBooks = async (query, startIndex) => {
    promises = [];
    let response = await fetch(`${API_ENDPOINT}/books/v1/volumes?${query}&startIndex=${startIndex}&maxResults=10&orderBy=relevance&key=${API_KEY}`);
    promises.push(await response.json());

    await Promise.all(promises).then(data => {
        totalResults = data[0].totalItems;
    })
}

const renderPageNumbers = async (pages, currentPage) => {
    pageNumbersContainer.innerHTML = '';

    let pageLinks = 0;
    if (pages > 10) { pageLinks = 10; }
    else { pageLinks = pages; }

    for (let i = 1; i <= pageLinks; i++) {
        let pageNumber = document.createElement('a');
        pageNumber.id = i;
        if (pageNumber.id === currentPage) {
            pageNumber.classList.add('active');
        }
        pageNumber.classList.add('page-number');
        pageNumber.href = `${query}&page=${i}`;
        pageNumber.innerHTML = i.toString();
        pageNumbersContainer.appendChild(pageNumber);

    }
}

window.addEventListener('load', go);