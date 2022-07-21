let bno = document.getElementById("bno").value;
let mno = null;
const title = document.getElementById("title");
const username = document.getElementById("username");
const content = document.getElementById("content");
const back = document.getElementById("back");

function showPostDetail(bvo) {
    title.value = bvo.title;
    username.value = bvo.username;
    content.innerText = bvo.content;
    mno = bvo.mno;
    back.setAttribute("href", `/boardone/detail/${bno}?curPage=${curPage}`);
}

axios.get(`/boardone/${bno}`, {
    headers: {
        "Content-Type": "application/json",
    },
}).then(function (response) {
    if (response.data != "") {
        showPostDetail(response.data);
    }

}).catch(function (error) {
    console.log(error);
});


document.getElementById("modForm").addEventListener("submit", (e) => {
    e.preventDefault();
    axios.put(`/boardone/${bno}`, {
        "bno": bno,
        "title": title.value,
        "content": content.value,
        "mno": mno,
    })
    .then(function (response) {
        if (response.data) {
            location.href=`/boardone/detail/${bno}?curPage=${curPage}`;
        }
    });
    
});