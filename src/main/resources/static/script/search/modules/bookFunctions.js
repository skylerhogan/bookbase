function getThumbnail(jsonItem) {
    let basePath = /*[[@{}]]*/'';
    let fileName = '/images/not-found.png';
    let filePath = basePath + fileName;

    let thumbnail = "";
    if(jsonItem.volumeInfo.imageLinks === undefined) {
        thumbnail = filePath;
    } else {
        thumbnail = jsonItem.volumeInfo.imageLinks.thumbnail;
    }

    return thumbnail;
}

function getAllCategories(jsonItem) {
    let categories = jsonItem.volumeInfo.categories;
    let categoriesArray = [];

    if (categories === undefined || categories === null || categories === 'undefined') {
        categoriesArray.push('None listed')
    } else {
        if(Array.isArray(categories)) {

            for (let i = 0; i < categories.length; i++) {
                if (categories[i].includes("/")) {
                    let subCategories = categories[i].split("/");
                    for (let j = 0; j < subCategories.length; j++) {
                        categoriesArray.push(subCategories[j].trim());
                    }
                } else {
                    categoriesArray.push(categories[i].trim());
                }
            }
        } else {
            if (categories.includes("/")) {
                let subCategories = categories.split("/");
                for (let i = 0; i < subCategories.length; i++) {
                    categoriesArray.push(subCategories[i].trim())
                }
            } else {
                categoriesArray.push(categories);
            }
        }
    }

    return categoriesArray;

}

// EXPORT FUNCTION
function returnBookObjectFromJson(jsonItem) {

    let isbn = '';
    if (jsonItem.volumeInfo.industryIdentifiers === undefined) {
        isbn = 'not found';
    } else if(Array.isArray(jsonItem.volumeInfo.industryIdentifiers) && jsonItem.volumeInfo.industryIdentifiers.length > 1) {
        isbn = jsonItem.volumeInfo.industryIdentifiers[0].identifier;
    } else {
        isbn = jsonItem.volumeInfo.industryIdentifiers[0].identifier;
    }

    let bookObject = {
        googleId: jsonItem.id,
        title: jsonItem.volumeInfo.title,
        author: jsonItem.volumeInfo.authors,
        description: jsonItem.volumeInfo.description,
        thumbnail: getThumbnail(jsonItem),
        genre: jsonItem.volumeInfo.mainCategory,
        tags: getAllCategories(jsonItem),
        pageCount: jsonItem.volumeInfo.pageCount,
        publishedDate: jsonItem.volumeInfo.publishedDate,
        industryIdentifiers: isbn,
        averageRating: jsonItem.volumeInfo.averageRating,
        ratingsCount: jsonItem.volumeInfo.ratingsCount
    }
    return bookObject;
}


// EXPORT FUNCTION
function returnObjectFieldsAsHtml(object) {
    let baseLink = /*[[@{}]]*/'';
    let linkName = `view/${object.googleId}`;
    let viewLink = baseLink + linkName;
    return `
        <div class="book-cover">
            <a href="${viewLink}" class="viewLink">
                <img class="book" src="${object.thumbnail}">
            </a>
        </div>
        <div class="book-info">
            <p class="title">${object.title}</p>
            <p class="author">${object.author}</p>
        </div>
    `;
}

// EXPORT FUNCTION
function renderAddBookButton(alreadyInBookshelf, bookShelfId) {
    const addButton = document.getElementById('add-button');
    let addBookForm = document.getElementById('add-book-form-container');

    if(alreadyInBookshelf === true) {
        addButton.innerHTML = `
            <a href="/user/view/${bookShelfId}">
                <button>view in bookshelf</button>
            </a>
        `;
    } else {
        addButton.innerHTML = `<button>add book</button>`
        addButton.addEventListener('click', (event) => {
            event.preventDefault();
            addBookForm.classList.add('active')
        })

        addBookForm.addEventListener('click', e => {
            if (e.target !== e.currentTarget) return
            addBookForm.classList.remove('active')
        })
    }
}

// EXPORT FUNCTION
function fillAddBookForm(bookObject) {
    let descriptionText = bookObject.description;
    descriptionText = descriptionText.replaceAll("<p>", "");
    descriptionText = descriptionText.replaceAll("</p>", " ");

    let title = document.getElementById('title');
    let author = document.getElementById('author');
    let isbn = document.getElementById('isbn');
    let pages = document.getElementById('pages');
    let genre = document.getElementById('genre');
    let description = document.getElementById('description');
    let thumbnail = document.getElementById('thumbnail');

    title.value = bookObject.title;
    author.value = bookObject.author;
    isbn.value = bookObject.industryIdentifiers;
    pages.value = bookObject.pageCount;
    genre.value = bookObject.categories;
    description.value = descriptionText;
    thumbnail.value = bookObject.thumbnail;
}

function generateTagLinks(categoriesArray) {
    let tags = "";
    if (categoriesArray.length === 1) {
        if(categoriesArray[0] === "None listed") {
            return "None listed";
        } else {
            let category = categoriesArray[0].toLowerCase().replaceAll(" ", "+");
            return `
                <a class="tag" href="/user/search/results/q=${category}&maxResults=10&page=1">${categoriesArray[0]}</a>
            `;
        }
    } else {
        for (let i = 0; i < categoriesArray.length; i++) {
            let category = categoriesArray[i].toLowerCase().replaceAll(" ", "+");
            category = category.replaceAll("&", "");
            category = category.replaceAll(",", "");
            category = category.replaceAll("++", "+");
            tags += `<a class="tag" href="/user/search/results/q=${category}&maxResults=10&page=1">${categoriesArray[i]}</a>`
        }
        return tags;
    }
}

export {
    returnBookObjectFromJson,
    returnObjectFieldsAsHtml,
    renderAddBookButton,
    fillAddBookForm,
    generateTagLinks
}