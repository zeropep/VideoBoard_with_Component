let grid = document.getElementById("videoGrid");
let next = false;
let scrolling = true;

function showVideoList(videoList) {
    // /upload/org/image/${video.saveDir}/${video.uuid}_th_${video.fileName}.jpg
    // /upload/proxy/storyboard/${video.saveDir}/${video.fno}/Poster.jpg
    let div = ``;
    videoList.forEach((video, idx) => {
        div += (idx + 1) % 4 == 1 ? `<div class="items row isotope boxed grid-view text-center">` : ``;
        div += `<div class="item grid-sizer col-md-6 col-lg-3">
                  <div class="box bg-white text-left p-30" shadow>
                    <figure class="main mb-20 rounded">
                      <a href="/video/detail/${video.vno}">`;
        div += (video.poster == "y")
                ?       `<img src="/upload/proxy/storyboard/${video.saveDir}/${video.fno}/Poster.jpg"/>`
                :       `<img src="/style/images/img_prepare.jpg"/>`;
        div +=        `</a>
                    </figure>
                    <h6 class="mb-0 ml-0 offset-2 comment-ellip">${video.title}</h6>
                    <span class="mb-0">${video.username}</span><br>
                    <span class="mb-0">${viewCounter(video.viewCount)} • ${displayedAt(video.createdAt)}</span><br>
                  </div></div>`;
        div += (idx + 1) % 4 == 0 ? `</div>` : ``;
        div += (idx + 1) == videoList.length ? `</div>` : ``;
    });
    grid.innerHTML += div;
    scrolling = true;
}

function noListFound(pgn) {
    let isResultExist = true;
    if (pgn.pgvo.keyword != null) {
        Cookies.set("keyword", pgn.pgvo.keyword);
        document.getElementById("keyword").value = pgn.pgvo.keyword;
        if (pgn.totalCount == 0) {
            isResultExist = false;
        }
    }
    grid.innerHTML = ``;
    let div = `<div>${!isResultExist ? "검색 결과가 없습니다." 
                            : "등록된 게시물이 없습니다."}</div>`;
    grid.innerHTML = div;
}

function showPagination(pgn) {
    next = pgn.more ? true : false;
    if (pgn.pgvo.keyword != null) {
        Cookies.set("keyword", pgn.pgvo.keyword);
        document.getElementById("keyword").value = pgn.pgvo.keyword;
        if (pgn.totalCount == 0) {
            noListFound(true);
        }
    }
}

function getListFromServer() {
    if (key > 0) {
        Cookies.remove("keyword");
    }
    axios.get(`/video/listup`, {
        headers: {
            "Content-Type": "application/json",
        },
    }).then(function (response) {
        if (response.data.videoList.length > 0) {
            showVideoList(response.data.videoList);
            showPagination(response.data.pgn);
        } else {
            noListFound(response.data.pgn);
        }
    }).catch(function (error) {
        console.log(error);
    });
};

getListFromServer();

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

if (sort != "noMsg" && msg != "noMsg") {
    customAlert(sort, msg);
}