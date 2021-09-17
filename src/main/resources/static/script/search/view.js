import {
    returnBookObjectFromJson,
    returnObjectFieldsAsHtml,
    renderAddBookButton,
    renderBuyButton,
    fillAddBookForm,
    generateTagLinks
} from './modules/bookFunctions.js';

const API_ENDPOINT = 'https://www.googleapis.com'
const API_KEY = 'AIzaSyDk87M-Tr5KQMeR2ZlCIjQ2nEsqiAo-uMg'

let promise = [];

let bookDetails = document.getElementById('book-details');
let coverAndRating = document.getElementById('cover-and-rating');
let bookDescription = document.getElementById('book-description');

bookIsbns = bookIsbns.replaceAll("[", "").replaceAll("]", "").replaceAll(" ", "");
bookIsbns = bookIsbns.split(",");

bookIds = bookIds.replaceAll("[", "").replaceAll("]", "").replaceAll(" ", "");
bookIds = bookIds.split(",");
let bookShelfId = null;

let backButton = document.getElementById('back-button');

const go = async () => {
    if (window.history.length < 2) {
        backButton.style.display = 'none';
    }
    await printBook(bookId);
}

const printBook = async (bookId) => {
    await retrieveBook(bookId);

    let bookObject = returnBookObjectFromJson(promise[0]);

    let alreadyInBookshelf = await checkBookshelf(bookObject.industryIdentifiers, bookIsbns);

    await renderPage(bookObject);

    fillAddBookForm(bookObject);

    renderAddBookButton(alreadyInBookshelf, bookShelfId);

    renderBuyButton(bookObject);
}

const retrieveBook = async (bookId) => {
    promise = [];
    let response = await fetch(`${API_ENDPOINT}/books/v1/volumes/${bookId}?key=${API_KEY}`);
    promise.push(await response.json());
}

const renderPage = async (bookObject) => {

    let coverImage = document.createElement('img');
    coverImage.id = 'cover-image';
    coverImage.src = bookObject.thumbnail2;
    coverImage.onerror = `this.onerror = null; this.src="${bookObject.thumbnail}"`

//    <img
//        class="img-fluid book"
//        src="${object.thumbnail2}"
//        onerror='this.onerror = null; this.src="${object.thumbnail}"'
//        onmouseover="this.style.opacity='50%'"
//        onmouseout="this.style.opacity='100%'"
//    >

    coverImage.alt = `cover for ${bookObject.title}`;

    coverAndRating.appendChild(coverImage);

    let rating = document.createElement('div');
    rating.id = 'rating-details';

    if (bookObject.averageRating != undefined) {

    rating.innerHTML = `
        <label class="rating-label"><strong>Average rating: ${bookObject.averageRating} <span class="rating-count">(${bookObject.ratingsCount})</span></strong>
          <input
            class="rating"
            max="5"
            readonly
            step="0.01"
            style="--fill:gray;--value:${bookObject.averageRating}"
            type="range"
            value="${bookObject.averageRating}">
        </label>
    `;
    } else {
        rating.innerHTML = `<label class="rating-label">No ratings</label>`
    }
    coverAndRating.appendChild(rating);

    bookDetails.innerHTML = `
         <h1 class="display-6 fw-bold text-dark">${bookObject.title}</h1>
         <h4>by ${bookObject.author}</h4>
         <div class="row" style="margin-top: 2rem;">
            <div class="col"
                 <p><span style="font-weight:bold;">Pages: </span>${bookObject.pageCount}</p>
                 <p><span style="font-weight:bold;">Genre: </span>${bookObject.genre}</p>
            </div>
            <div class="col">
                <p><span style="font-weight:bold;">Publication Date: </span>${bookObject.publishedDate}</p>
                <p><span style="font-weight:bold;">ISBN: </span>${bookObject.industryIdentifiers}</p>
            </div>
         </div>
         <p class="book-detail" style="margin-top: 1rem;"><span style="font-weight:bold;">Tags: </span>${generateTagLinks(bookObject.tags)}</p>
    `;

    let descriptionHeading = document.createElement('h3');
    descriptionHeading.id = 'description-heading';
    descriptionHeading.innerHTML = 'Description';

    let description = document.createElement('p');
    description.id = 'description-text';
    description.innerHTML = `${bookObject.description}`;

    bookDescription.appendChild(descriptionHeading);
    bookDescription.appendChild(description);

}

const checkBookshelf = async (isbn, shelfIsbns) => {
    if (shelfIsbns.includes(isbn)) {
        bookShelfId = bookIds[shelfIsbns.indexOf(isbn)];
        return true;
    }
    return false;
}

window.addEventListener('load', go);
