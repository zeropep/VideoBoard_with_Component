const like = document.getElementById("like");
const dislike = document.getElementById("dislike");

// 좋아요 여부 화면 표시
function displayLike(likeCount, isLike) {
    like.innerText = `  ${likeCount}`;

    if (isLike) {
        like.classList.remove("fa-thumbs-o-up");
        like.classList.add("fa-thumbs-up");
    }
}

// 좋아요 이벤트
like.addEventListener("click", (e) => {
    e.preventDefault();
    if (like.classList.contains("fa-thumbs-o-up")) {
        like.classList.remove("fa-thumbs-o-up");
        like.classList.add("fa-thumbs-up");
        like.closest("a").dataset.content = "좋아요 취소";
        like.innerText = `  ${like.innerText * 1 + 1}`;
        likeToServer();

    } else if (like.classList.contains("fa-thumbs-up")) {
        like.classList.remove("fa-thumbs-up");
        like.classList.add("fa-thumbs-o-up");
        like.closest("a").dataset.content = "좋아요";
        like.innerText = `  ${like.innerText * 1 - 1}`;
        likeCancleToServer();

    }
})

// 좋아요 api
function likeToServer() {
    axios.post(`/like/${vno}`)
    .then((response) => {
        // 이후에 다중사용자 접속환경에서는 
        // 실제 데이터로 refresh필요함 
    });
    
}

// 좋아요 취소 api
function likeCancleToServer() {
    axios.delete(`/like/${vno}`)
    .then((response) => {

    })
}