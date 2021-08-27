import {
    returnBookObjectFromJson,
    returnObjectFieldsAsHtml,
    renderAddBookButton,
    fillAddBookForm,
    generateTagLinks
} from './modules/bookFunctions.js';

const API_ENDPOINT = 'https://www.googleapis.com'
const API_KEY = 'AIzaSyDk87M-Tr5KQMeR2ZlCIjQ2nEsqiAo-uMg'

let promise = [];

let bookCover = document.getElementById('book-cover');
let bookDetails = document.getElementById('book-details');
let coverAndRating = document.getElementById('cover-and-rating');
let bookDescription = document.getElementById('book-description');

bookIsbns = bookIsbns.replaceAll("[", "").replaceAll("]", "").replaceAll(" ", "");
bookIsbns = bookIsbns.split(",");

bookIds = bookIds.replaceAll("[", "").replaceAll("]", "").replaceAll(" ", "");
bookIds = bookIds.split(",");

const go = async () => {
    await printBook(bookId);
}

const printBook = async (bookId) => {
    await retrieveBook(bookId);

    let bookObject = returnBookObjectFromJson(promise[0]);
    console.log(bookObject.categories);

    let alreadyInBookshelf = false;
    let bookShelfId = null;

    for (let i = 0; i < bookIsbns.length; i++) {
        if (bookIsbns[i] === bookObject.industryIdentifiers) {
            alreadyInBookshelf = true;
            bookShelfId = bookIds[i];
        }
    }

    await renderPage(bookObject);

    fillAddBookForm(bookObject);

    renderAddBookButton(alreadyInBookshelf, bookShelfId);
}

const retrieveBook = async (bookId) => {
    promise = [];
    let response = await fetch(`${API_ENDPOINT}/books/v1/volumes/${bookId}?key=${API_KEY}`);
    promise.push(await response.json());
}

const renderPage = async (bookObject) => {
    bookCover.src = bookObject.thumbnail;

    let rating = document.createElement('div');
    rating.id = 'rating-details';
    rating.innerHTML = `
//        <p class="avg-rating">average rating: ${bookObject.averageRating}
//            <span class="rating-count">(${bookObject.ratingsCount})</span>
//        </p>
        <label class="rating-label"><strong>Average rating: ${bookObject.averageRating} <code>readonly</code></strong>
          <input
            class="rating"
            max="5"
            readonly
            step="0.01"
            style="--fill:#777;--value:${bookObject.averageRating}"
            type="range"
            value="${bookObject.averageRating}">
        </label>

    `;
    coverAndRating.appendChild(rating);

    bookDetails.innerHTML = `
        <div class="book-details">
            <p class="book-detail"><span style="font-weight:bold;">Title: </span>${bookObject.title}</p>
            <p class="book-detail"><span style="font-weight:bold;">Author: </span>${bookObject.author}</p>
            <p class="book-detail"><span style="font-weight:bold;">Pages: </span>${bookObject.pageCount}</p>
            <p class="book-detail"><span style="font-weight:bold;">Publication Date: </span>${bookObject.publishedDate}</p>
            <p class="book-detail"><span style="font-weight:bold;">ISBN: </span>${bookObject.industryIdentifiers}</p>
            <p class="book-detail"><span style="font-weight:bold;">Genre: </span>${bookObject.genre}</p>
            <p class="book-detail"><span style="font-weight:bold;">Tags: </span>${generateTagLinks(bookObject.tags)}</p>
        </div>
    `;

    let description = document.createElement('div');
    description.id = 'description';
    description.innerHTML = `<p>${bookObject.description}</p>`;
    bookDescription.appendChild(description);

}

window.addEventListener('load', go);