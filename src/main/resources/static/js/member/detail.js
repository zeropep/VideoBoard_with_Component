let grid = document.getElementById("videoGrid");
let next = false;
let scrolling = true;

function showUserInfo(mvo) {
        document.getElementById("username").innerText = mvo.username;
}

function showUserVIdeos(videoList) {
    console.log(videoList);
    let div = ``;
    videoList.forEach((video, idx) => {
        div += (idx + 1) % 4 == 1 ? `<div class="items row isotope boxed grid-view text-center">` : ``;
        div += `<div class="item grid-sizer col-md-6 col-lg-3">
                  <div class="box bg-white text-left p-30" shadow>
                    <figure class="main mb-20 rounded">
                      <a href="/video/comp/${video.vno}">`;
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

function noListFound() {
    grid.innerHTML = ``;
    let div = `<div>등록된 게시물이 없습니다.</div>`;
    grid.innerHTML = div;
}

function showPagination(pgn) {
    next = pgn.more ? true : false;
}

function getListFromServer() {
    axios.get(`/auth/member/detail`, {
        headers: {
            "Content-Type": "application/json",
        },
    }).then(function (response) {
        showUserInfo(response.data.mvo);
        if (response.data.videoList) {
            showUserVIdeos(response.data.videoList);
            showPagination(response.data.pgn);
        } else {
            noListFound();
        }
    
    }).catch(function (error) {
        console.log(error);
    });
}

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