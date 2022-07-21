// 댓글 가져오기
function getListFromServer(pageNo = 1) {
    axios.get(`/comment/${bno}?cmtPageNo=${pageNo}`, {
        headers: {
            "Content-Type": "application/json",
        },
    }).then(function (response) {
        if (response.data.cmtList.length > 0) {
            showCommentList(response.data.cmtList);
            showPagination(response.data.pgn);
        } else {
            noListFound();
        }
    }).catch(function (error) {
        console.log(error);
    });
};

// 댓글 리스트
function showCommentList(cmtList) {
    const ul = document.getElementById('cmtListArea');
    ul.innerHTML = "";
        
    cmtList.forEach(cmt => {
        let li = `<li data-cno="${cmt.cno}" data-mno="${cmt.mno}" class="list-group-item d-flex justify-content-between align-items-start">`;
        li += `<div class="ms-2 me-auto"><span class="badge badge-pill bg-dark rounded-pill">${cmt.username}</span> <span class="cmtContent">${cmt.content}</span></div>`;
        li += `<div class="ms-2 me-auto">`;
        if(cmt.mine){
            li += `<button type="button" class="btn btn-sm btn-outline-warning py-0 mod" data-bs-toggle="modal" data-bs-target="#cmtModal">e</button>`;
            li += `<button type="button" class="btn btn-sm btn-pastel-red py-0 del">x</button>`;
            }
        li += `${cmt.modifiedAt == null ? displayedAt(cmt.createdAt) : displayedAt(cmt.modifiedAt) + " • 수정됨"}</div></li>`;
        ul.innerHTML += li;
    });
}

function noListFound() {
    const ul = document.getElementById('cmtListArea');
    ul.innerHTML = "";
    let li = `<li class="list-group-item d-flex justify-content-between align-items-start">현재 등록된 댓글이 없습니다.</li>`;
    ul.innerHTML = li;
}

// 댓글 페이지
function showPagination(pgn) {
    let cmtPage = document.getElementById("cmtPaging");
    cmtPage.innerHTML = "";
    let ul = `<ul>`;
    if (pgn.prev) {
        ul += `<li data-page="${pgn.startPage - 1}"><a href="#" class="page"><i class="mi-arrow-left"></i></a></li>`;
    }

    for (let i = pgn.startPage; i <= pgn.endPage; i++) {
        ul += `<li class="${pgn.pgvo.pageNo == i ? "active" : ""}" data-page="${i}" aria-current="page">
                <a href="#" class="page">${i}</a></li>`;
    }

    if (pgn.next) {
        ul += `<li data-page="${pgn.startPage + 1}"><a href="#" class="page"><i class="mi-arrow-right"></i></a></li>`;
    }
    ul += `</ul>`;
    cmtPage.innerHTML = ul;
}

// 댓글 등록
document.getElementById("cmtPostBtn").addEventListener("click", () => {
    let cmtContent = document.getElementById("cmtText").value;
    let cmtCount = document.getElementById("cmtCount").innerText * 1;
    if (cmtContent == "") {
        alert("댓글을 작성해주세요");
    }  else {
        axios.post(`/comment/${bno}`, {
            content: document.getElementById("cmtText").value,
            bno,
        }).then(function (response) {
            if (response.data) {
                document.getElementById("cmtText").value = "";
                document.getElementById("cmtCount").innerHTML = `<a href="#cmtListArea">${cmtCount + 1}</a>`;
                getListFromServer();
            }
        })
    }
})

// 댓글 수정, 삭제
document.addEventListener("click", (e) => {
    let cmtCount = document.getElementById("cmtCount").innerText * 1;
    if (e.target.classList.contains("del")) {
        let div = e.target.closest("div");

        axios.delete(`/comment/${bno}`, {
            data: {
                cno: div.parentNode.dataset.cno,
                mno: div.parentNode.dataset.mno,
            }
        }).then(response => {
            if (response.data) {
                document.getElementById("cmtCount").innerHTML = `<a href="#cmtListArea">${cmtCount - 1}</a>`;
                getListFromServer();
            }
        })
    } else if (e.target.classList.contains("mod")) {
        let div = e.target.closest("div");
        let mod = document.getElementById("cmtModBtn");

        let cno = div.parentNode.dataset.cno;
        let mno = div.parentNode.dataset.mno;
        let content = div.parentNode.firstChild.lastChild.innerText;

        div.parentNode.firstChild.lastChild.innerText += "(수정중)";
        document.getElementById("cmtText").value = content;
        document.getElementById("cmtText").focus();
        document.getElementById("cmtPostBtn").classList.add("hidden");
        mod.classList.remove("hidden");
        mod.setAttribute("data-mno", mno);
        mod.setAttribute("data-cno", cno);
    } else if (e.target.id == "cmtModBtn") {
        console.log(e.target.dataset.mno);
        axios.put(`/comment/${bno}`, {
            cno: document.getElementById("cmtModBtn").dataset.cno,
            content: document.getElementById("cmtText").value,
            mno: document.getElementById("cmtModBtn").dataset.mno,
        }).then(function (response) {
            if (response.data) {
                document.getElementById("cmtText").value = "";
                getListFromServer();
            }
        })

        document.getElementById("cmtPostBtn").classList.remove("hidden");
        document.getElementById("cmtModBtn").classList.add("hidden");
    } else if (e.target.classList.contains("page")) {
        e.preventDefault();
        let pageNo = e.target.closest("li").dataset.page;
        getListFromServer(pageNo);
    }
});

getListFromServer();
