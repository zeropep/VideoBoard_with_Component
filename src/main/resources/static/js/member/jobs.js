let tbody = document.getElementById("tbody");
let next = false;
let scrolling = true;

function showJobLists(jobList) {
    jobList.forEach(job => {
        let tr = document.createElement("tr");
        tr.dataset.vno = job.vno;
        tr.innerHTML = `<td>${job.jobSeq}</td>
                    <td>${getComponentName(job.component)}</td>
                    <td>${job.fileName}</td>
                    <td>${getFileType(job.fileType)}</td>
                    <td>${getStatus(job.status)}</td>
                    <td>${job.component == "1001" ? job.progress + "%" : ""}</td>
                    <td>${job.startTime != null ? job.startTime : ""}</td>
                    <td>${job.finishTime != null ? job.finishTime : ""}</td>`;

        tbody.appendChild(tr);
    });
    scrolling = true;
}

function noListFound() {
    tbody.innerHTML = ``;
    let tr = document.createElement("tr");
    tr.innerHTML = `<th scope="row" colspan="8">
                작업 내역이 없습니다.
                </th>`;
    tbody.appendChild(tr);
}

function showPagination(pgn) {
    next = pgn.more;
}

function getListFromServer() {
    axios.get(`/auth/member/mywork`, {
        headers: {
            "Content-Type": "application/json",
        },
    }).then(function (response) {
        // console.log(response.data);
        if (response.data.jobList.length > 0) {
            showJobLists(response.data.jobList);
            showPagination(response.data.pgn);
        } else {
            noListFound(response.data.pgn);
        }
    }).catch(function (error) {
        console.log(error);
    });
};

getListFromServer();

// 새로고침
document.getElementById("refresh").addEventListener("click", () => {
    location.reload(true);
})

// 게시물 로딩
window.addEventListener("scroll", (e) => {
    if (!scrolling) {
        return null;
    }
    let scrollTop = document.documentElement.scrollTop;
    let innerHeight = document.documentElement.clientHeight;
    let scrollHeight = document.documentElement.scrollHeight;

    if (scrollTop + innerHeight + 10 >= scrollHeight && next) {
        scrolling = false;
        let pageNow = Cookies.get("curPage");
        Cookies.set("curPage", pageNow * 1 + 1);
        getListFromServer();
    }
})

document.addEventListener("click", (e) => {
    if(e.target.tagName.toLowerCase() == "td") {
        location.href=`/video/comp/${e.target.closest("tr").dataset.vno}`;
    }
})