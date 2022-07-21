// 댓글 가져오기
function getListFromServer() {
    axios.get(`/vcomment/${vno}`, {
        headers: {
            "Content-Type": "application/json",
        },
    }).then(function (response) {
        if (response.data.cmtList.length > 0) {
            showCommentList(response.data.cmtList, response.data.pgn);
        } else {
            noListFound();
        }
    }).catch(function (error) {
        console.log(error);
    });
};

// 댓글 리스트
function showCommentList(cmtList, pgn) {
    const ul = document.getElementById('cmtListArea');
        
    cmtList.forEach(cmt => {
        let li = `<li class="list-group-item d-flex justify-content-between align-items-start shadow">`;
        li += `<div class="text-left w-95">`;
        li += `<div class="ms-2 me-auto d-inline w-90">`;
        li += `<span class="badge badge-pill bg-dark rounded-pill">asd</span> `;
        li += `<span class="d-inline">${displayedAt(cmt.createdAt)} ${cmt.modifiedAt == null ? " " :  " • (수정됨) "}</span>`;
        li += `<button type="button" class="btn btn-s p-0 m-0 btn-white hidden"><i class="si-arrows_down-arrow arrowBtn"></i></button>`;
        li += `<div class="d-inline cmt">`;
        li += `<div class="cmtContent comment-full" >${cmt.content}</div>`;
        li += `</div><div class="w-100 hidden modcmt">`;
        li += `<input type="text" class="bg-white w-88 p-0 cmt-input" id="modInput" value="${cmt.content}">`;
        li += `<a href="#" class="my-5" id="cmtModCnl">취소</a>`;
        li += `<a href="#" class="my-5" id="cmtModBtn">수정</a>`;
        li += `</div></div></div>`;
        li += `<div data-cno="${cmt.cno}" data-mno="${cmt.mno}" class="dropdown ms-2 me-auto d-inline ${cmt.mine ? "" : "invisible"}">`;
        li += `<a class="nav-link dropdown-toggle color-dark py-0" id="extra" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" href="#">&#8942;</a>`;
        li += `<ul class="dropdown-menu bg-pastel-lavender toleft" aria-labelledby="extra">`;
        li += `<li class="nav-item" ><a class="dropdown-item mod" href="" id="mod">수정</a></li>`;
        li += `<li class="nav-item" ><a class="dropdown-item del" href="" id="del">삭제</a></li>`;
        li += `</ul></div></li>`;
        ul.innerHTML += li;
        
        //
        let firstLi = ul.querySelector("li.list-group-item:last-child");
        let commentHeight = firstLi.scrollHeight;
        if (commentHeight > 86) {
            let commentDiv = firstLi.querySelector("div.comment-full");
            commentDiv.classList.remove("comment-full");
            commentDiv.classList.add("comment-ellip");

            let arrowBtn = firstLi.querySelector("div.w-90 > button");
            arrowBtn.classList.remove("hidden");
        }

    });
    
    // 댓글 더보기
    let more = document.getElementById("more").parentNode;

    more.setAttribute("class", pgn.more ? "visible" : "invisible");
}


// 댓글이 없음
function noListFound() {
    const ul = document.getElementById('cmtListArea');
    ul.innerHTML = "";
    let li = `<li class="list-group-item d-flex justify-content-between align-items-start shadow">현재 등록된 댓글이 없습니다.</li>`;
    ul.innerHTML = li;
}


// 댓글 초기화 후 재로드
function refreshComment() {
    const ul = document.getElementById('cmtListArea');
    ul.innerHTML = ``;

    let pageNo = Cookies.get("cmtCurPage") * 1;
    console.log(pageNo);
    Cookies.set("cmtCurPage", 1);
    getListFromServer();
    for (let i = 0; i < pageNo - 1; i++) {
        document.getElementById("more").click();
    }
}

// 댓글 등록
document.getElementById("cmtPostBtn").addEventListener("click", () => {
    let cmtContent = document.getElementById("cmtText").value;
    let cmtCount = document.getElementById("cmtCount").innerText * 1;

    if (cmtContent == "") {
        customAlert("warning", "내용을 입력하세요.");
        return;
    }  else {
        axios.post(`/vcomment`, {
            content: document.getElementById("cmtText").value,
            vno,
        }).then(function (response) {
            if (response.data) {
                document.getElementById("cmtText").value = "";
                document.getElementById("cmtCount").innerHTML = `<a href="#cmtListArea">${cmtCount + 1}</a>`;
                const ul = document.getElementById('cmtListArea');
                ul.innerHTML = ``;
                Cookies.set("cmtCurPage", 1);
                customAlert("info", "댓글 등록완료");
                refreshComment();
            }
        })
    }
})

// 댓글 수정, 삭제
document.addEventListener("click", (e) => {
    let cmtCount = document.getElementById("cmtCount").innerText * 1;
    if (e.target.classList.contains("del")) {
        e.preventDefault();
        // del button clicked
        if (!window.confirm("댓글을 삭제하시겠습니까?")) {
            return;
        }
        let div = e.target.closest("div");
        axios.delete(`/vcomment/${vno}`, {
            data: {
                cno: div.dataset.cno,
                mno: div.dataset.mno,
            }
        }).then(response => {
            if (response.data) {
                document.getElementById("cmtCount").innerHTML = `<a href="#cmtListArea">${cmtCount - 1}</a>`;
                customAlert("info", "댓글 삭제완료");
                refreshComment();
            }
        })
    } else if (e.target.classList.contains("mod")) {
        // mod button clicked
        e.preventDefault();
        let div = e.target.closest("div");
        let mod = document.getElementById("cmtModBtn");

        let cno = div.dataset.cno;
        let mno = div.dataset.mno;
        let content = div.parentNode.firstChild.querySelector("div.cmt");
        let input = div.parentNode.firstChild.querySelector("div.modcmt");

        content.classList.add("hidden");
        input.classList.remove("hidden");

        mod.setAttribute("data-mno", mno);
        mod.setAttribute("data-cno", cno);
    } else if (e.target.id == "cmtModCnl") {
        e.preventDefault();
        let div = e.target.closest("div");
        
        let content = div.previousSibling;

        content.classList.remove("hidden");
        div.classList.add("hidden");
    } else if (e.target.id == "cmtModBtn") {
        // comment mod api 
        e.preventDefault();

        if (e.target.closest("div").firstChild.value == "") {
            customAlert("warning", "내용을 입력하세요.");
            return;
        }

        axios.put(`/vcomment/${vno}`, {
            cno: document.getElementById("cmtModBtn").dataset.cno,
            content: e.target.closest("div").firstChild.value,
            mno: document.getElementById("cmtModBtn").dataset.mno,
        }).then(function (response) {
            if (response.data) {
                customAlert("info", "댓글 수정완료");
                refreshComment();
            }
        })

    } else if (e.target.id == "more") {
        // 댓글 더보기
        e.preventDefault();
        let pageNo = Cookies.get("cmtCurPage");
        Cookies.set("cmtCurPage", pageNo * 1 + 1);
        getListFromServer();

    } else if (e.target.classList.contains("arrowBtn")) {
        // 댓글 펼치기
        let switching = e.target.parentNode.nextSibling.firstChild;
        if (e.target.classList.contains("si-arrows_down-arrow")) {
            e.target.classList.remove("si-arrows_down-arrow");
            switching.classList.remove("comment-ellip");
            e.target.classList.add("si-arrows_up-arrow");
            switching.classList.add("comment-full");
        } else {
            e.target.classList.remove("si-arrows_up-arrow");
            switching.classList.remove("comment-full");
            e.target.classList.add("si-arrows_down-arrow");
            switching.classList.add("comment-ellip");

        }
    }
});

getListFromServer();
