let books = document.querySelectorAll("img");
let deleteButton = document.querySelector("#deleteButton");
let bookIds = document.getElementsByName("bookIds");


for(let i=0; i < books.length; i++) {
    books[i].addEventListener('click', function (e) {
    if(books[i].style.opacity == "50%") {
        books[i].style.opacity = "100%";
        books[i].style.height = "200px";
        books[i].style.width = "133px"
        books[i].style.marginTop = "0rem";
        } else {
        books[i].style.opacity = "50%";
        books[i].style.height = "180px";
        books[i].style.width = "120px";
        books[i].style.alignItems = "center";
        books[i].style.marginTop = ".3rem";

        }
    })
};


deleteButton.addEventListener('click', function (e) {
    let r = confirm("Are you sure you want to remove these books?");
    if (r) {
        document.getElementById("deleteForm").submit();
    }
});


