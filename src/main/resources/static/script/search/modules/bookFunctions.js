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
        categories: jsonItem.volumeInfo.categories,
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
        <a href="${viewLink}" class="viewLink">
            <img class="book" src="${object.thumbnail}">
        </a>
        <div class="book-info">
            <p class="title">${object.title}</p>
            <p class="author">${object.author}</p>
        </div>
    `;
}

export {
    returnBookObjectFromJson,
    returnObjectFieldsAsHtml
}