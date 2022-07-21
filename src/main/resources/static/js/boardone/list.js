let tbody = document.getElementById("tbody");

function showBoardList(boardList) {
    tbody.innerHTML = ``;
    boardList.forEach(board => {
        let tr = document.createElement("tr");
        tr.innerHTML = `<td><a href="/boardone/detail/${board.bno}?curPage=${curPage}">${board.title}</a></td>
                    <td>${board.username}</td>
                    <td>${board.cmtCount}</td>
                    <td>${board.createdAt}</td>`;
        tbody.appendChild(tr);
    });
}

function noListFound() {
    tbody.innerHTML = ``;
    let tr = document.createElement("tr");
    tr.innerHTML = `<th scope="row" colspan="4">
                등록된 게시물이 없습니다.
                </th>`;
    tbody.appendChild(tr);
}

function showPagination(pgn) {
    const ul = document.querySelector("div.pagination ul");
    ul.innerHTML = "";
    let li = "";
    if (pgn.prev) {
        li += `<li data-page="${pgn.startPage - 1}"><a href="javascript:void(0)" class="btn btn-pastel-default shadow page"><i class="mi-arrow-left"></i></a></li>`;
    }
    for (let i = pgn.startPage; i <= pgn.endPage; i++) {
        li += `<li data-page="${i}" class="${pgn.pgvo.pageNo == i ? "active" : ""}"><a href="javascript:void(0)" class="btn btn-pastel-default shadow page"><span>${i}</span></a></li>`;
    }
    if (pgn.next) {
        li += `<li data-page="${pgn.endPage + 1}"><a href="javascript:void(0)" class="btn btn-pastel-default shadow page"><i class="mi-arrow-right"></i></a></li>`;
    }
    ul.innerHTML = li;
}

function getListFromServer(pageNo = 1) {
    axios.get(`/boardone/listup?pageNo=${pageNo}`, {
        headers: {
            "Content-Type": "application/json",
        },
    }).then(function (response) {
        if (response.data.boardList.length > 0) {
            showBoardList(response.data.boardList);
            showPagination(response.data.pgn);
        } else {
            noListFound();
        }
    }).catch(function (error) {
        console.log(error);
    });
};

getListFromServer(curPage);

document.addEventListener("click", (elem) => {
    if (elem.target.classList.contains("page")) {
        let pageNo = elem.target.closest("li").dataset.page;
        curPage = pageNo;
        getListFromServer(pageNo);
    }
})