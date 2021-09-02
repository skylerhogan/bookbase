import returnBookObjectFromJson from './modules/bookFunctions.js';

const API_ENDPOINT = 'https://www.googleapis.com'
const API_KEY = 'AIzaSyDk87M-Tr5KQMeR2ZlCIjQ2nEsqiAo-uMg'

let promise = [];

let bookCover = document.getElementById('book-cover');
let bookDetails = document.getElementById('book-details');
let coverAndRating = document.getElementById('cover-and-rating');
let bookDescription = document.getElementById('book-description');

function fillAddBookForm(bookObject) {
    let title = document.getElementById('title');
    let author = document.getElementById('author');
    let isbn = document.getElementById('isbn');
    let pages = document.getElementById('pages');
    let genre = document.getElementById('genre');

    title.value = bookObject.title;
    author.value = bookObject.author;
    isbn.value = bookObject.industryIdentifiers;
    pages.value = bookObject.pageCount;
    genre.value = bookObject.categories;

}

const go = async () => {
    await printBook(bookId);
}

const printBook = async (bookId) => {
    await retrieveBook(bookId);

    let bookObject = returnBookObjectFromJson(promise[0]);

    await renderPage(bookObject);

    fillAddBookForm(bookObject);
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
        <p class="avg-rating">average rating: ${bookObject.averageRating}
            <span class="rating-count">(${bookObject.ratingsCount})</span>
        </p>
    `;
    coverAndRating.appendChild(rating);

    bookDetails.innerHTML = `
        <div class="book-details">
            <p class="book-detail"><span style="font-weight:bold;">Title: </span>${bookObject.title}</p>
            <p class="book-detail"><span style="font-weight:bold;">Author: </span>${bookObject.author}</p>
            <p class="book-detail"><span style="font-weight:bold;">Pages: </span>${bookObject.pageCount}</p>
            <p class="book-detail"><span style="font-weight:bold;">Publication Date: </span>${bookObject.publishedDate}</p>
            <p class="book-detail"><span style="font-weight:bold;">ISBN: </span>${bookObject.industryIdentifiers}</p>
            <p class="book-detail"><span style="font-weight:bold;">Genre: </span>${bookObject.categories}</p>
        </div>
    `;

    bookDescription.innerHTML = `
        <h3>description</h3>
        <div id="description">
            <p>${bookObject.description}</p>
        </div>
    `;
}


window.addEventListener('load', () => {
    go();

    const addButton = document.getElementById('add-button');
    let addBookForm = document.getElementById('add-book-form-container');

    addButton.addEventListener('click', (event) => {
        event.preventDefault();
        addBookForm.classList.add('active')
    })

    addBookForm.addEventListener('click', e => {
      if (e.target !== e.currentTarget) return
      addBookForm.classList.remove('active')
    })

});