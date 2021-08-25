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
    await printBooks(query, startIndex, maxResults);
    console.log(queryArray);
    console.log(searchQuery);
    console.log(searchParameter);
}

// PRINTBOOKS
const printBooks = async (query, startIndex, maxResults) => {

    results.innerHTML = '';

    await retrieveBooks(query, startIndex, maxResults);

    let books = promises[0].items;

    books.forEach(book => {
        book = returnBookObjectFromJson(book);
        let result = document.createElement('div');
        result.classList.add('result');
        result.innerHTML = returnObjectFieldsAsHtml(book);
        results.appendChild(result);
    })

    let pages = ((totalResults - (totalResults % maxResults)) / maxResults) + 1;
    await renderPageNumbers(pages, currentPage);

}

const retrieveBooks = async (query, startIndex, maxResults) => {
    promises = [];
    let response = await fetch(`${API_ENDPOINT}/books/v1/volumes?q=${query}&startIndex=${startIndex}&maxResults=${maxResults}&orderBy=relevance&key=${API_KEY}`);
    promises.push(await response.json());

    await Promise.all(promises).then(data => {
        totalResults = data[0].totalItems;
    })
}

const renderPageNumbers = async (pages, currentPage) => {

    pageNumbersContainer.innerHTML = '';

    let previousPage = document.createElement('a');
    previousPage.id = 'previous-page';
    previousPage.innerHTML = '⇦ previous';
    previousPage.classList.add('page-number');
    if(Number(currentPage) === 1) {
        previousPage.classList.add('disabled');
    } else {
        previousPage.href = `q=${query}&maxResults=${maxResults}&page=${Number(currentPage) - 1}`;
    }

    pageNumbersContainer.appendChild(previousPage);

    let pageLinks = 0;
    if (pages > 9) { pageLinks = 9; }
    else { pageLinks = pages; }

    for (let i = 1; i <= pageLinks; i++) {
        let pageNumber = document.createElement('a');
        pageNumber.id = i;
        pageNumber.classList.add('page-number');
        if (pageNumber.id === currentPage) {
            pageNumber.classList.add('active');
        }
        pageNumber.href = `q=${query}&maxResults=${maxResults}&page=${i}`;
        pageNumber.innerHTML = i.toString();
        pageNumbersContainer.appendChild(pageNumber);
    }

    let nextPage = document.createElement('a');
    nextPage.id = 'next-page';
    nextPage.innerHTML = '⇨ next';
    nextPage.classList.add('page-number');
    if(Number(currentPage) === pageLinks) {
        nextPage.classList.add('disabled');
    } else {
        nextPage.href = `q=${query}&maxResults=${maxResults}&page=${Number(currentPage) + 1}`;
    }
    pageNumbersContainer.appendChild(nextPage)

}

window.addEventListener('load', go);