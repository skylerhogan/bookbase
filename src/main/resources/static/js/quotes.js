const quotesObject = [
    {
    quote: 'There is some good in this world, and it’s worth fighting for.',
    author: 'J.R.R. Tolkien',
    book: 'The Two Towers',
    },
    {
    quote: 'We accept the love we think we deserve.',
    author: 'Stephen Chbosky',
    book: 'The Perks of Being a Wallflower',
    },
    {
    quote: 'A man, after he has brushed off the dust and chips of his life, will have left only the hard, clean questions: Was it good or was it evil? Have I done well — or ill?',
    author: 'John Steinbeck',
    book: 'East of Eden',
    },
    {
    quote: 'All happy families are alike; each unhappy family is unhappy in its own way.',
    author: 'Leo Tolstoy',
    book: 'Anna Karenina',
    },
    {
    quote: 'Who controls the past controls the future. Who controls the present controls the past.',
    author: 'George Orwell',
    book: '1984',
    },
    {
    quote: 'It does not do to dwell on dreams and forget to live.',
    author: 'J.K. Rowling',
    book: 'Harry Potter and the Sorcerer’s Stone',
    },
    {
    quote: 'So it goes…',
    author: 'Kurt Vonnegut',
    book: 'Slaughterhouse-Five',
    },
    {
    quote: 'It was a pleasure to burn.',
    author: 'Ray Bradbury',
    book: 'Fahrenheit 451',
    },
    {
    quote: 'There are years that ask questions and years that answer.',
    author: 'Zora Neale Hurston',
    book: 'Their Eyes Were Watching God',
    },
    {
    quote: 'It is nothing to die; it is dreadful not to live.',
    author: 'Victor Hugo',
    book: 'Les Misérables',
    },
    {
    quote: 'Memories, even your most precious ones, fade surprisingly quickly. But I don’t go along with that. The memories I value most, I don’t ever see them fading.',
    author: 'Kazuo Ishiguro',
    book: 'Never Let Me Go',
    },
    {
    quote: 'And, when you want something, all the universe conspires in helping you to achieve it.',
    author: 'Paulo Coelho',
    book: 'The Alchemist',
    },
    {
    quote: 'Sometimes I can hear my bones straining under the weight of all the lives I’m not living.',
    author: 'Jonathan Safran Foer',
    book: 'Extremely Loud and Incredibly Close',
    },
    {
    quote: 'It is our choices, Harry, that show what we truly are, far more than our abilities.',
    author: 'J.K. Rowling',
    book: 'Harry Potter and the Chamber of Secrets',
    },
    {
    quote: 'All we can know is that we know nothing. And that’s the height of human wisdom.',
    author: 'Leo Tolstoy',
    book: 'War and Peace',
    },
    {
    quote: 'There is nothing like looking, if you want to find something. You certainly usually find something, if you look, but it is not always quite the something you were after.',
    author: 'J.R.R. Tolkien',
    book: 'The Hobbit',
    }
];

function newQuote() {
    let randomNumber = Math.floor(Math.random() * (quotesObject.length));
    document.getElementById('quoteDisplay').innerHTML = quotesObject[randomNumber].quote;
    document.getElementById('authorDisplay').innerHTML = quotesObject[randomNumber].author;
    document.getElementById('bookDisplay').innerHTML = quotesObject[randomNumber].book;
}

document.addEventListener("DOMContentLoaded", function() {
    newQuote();
});