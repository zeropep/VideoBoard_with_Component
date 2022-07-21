let vno = document.getElementById("vno").value;
let uploader = null;

function uploadedDate(upload) {
    return (upload + "").substring(0, 10).replaceAll("-", ". ") + ".";
}

function showVideoDetail(vvo, fvo, mine) {
    uploader = vvo.mno;

    const h1 = document.querySelector("div#title h1");
    h1.innerText = vvo.title;

    document.getElementById("mod").setAttribute("href", `/video/modify/${vno}`);

    if (mine) {
        document.querySelector("div.dropdown").hidden = false;
    }
    
    const metaDiv = document.querySelector("div#postContainer div.meta");
    metaDiv.innerHTML = `<span class="date">${uploadedDate(vvo.createdAt)}${vvo.modifiedAt == null ? "" : "  수정됨"}</span>
                        <span class="author">By <a href="#">${vvo.username}</a></span>
                        <span class="comments" id="cmtCount"><a href="#cmtListArea">${vvo.cmtCount}</a></span>
                        <span class="comments"><i class="fa fa-eye"></i> ${vvo.viewCount.toLocaleString()} 회</span>`;


    const content  = document.querySelector("div#postContainer p");
    content.innerText = vvo.description;
    // vvo.신고횟수가 기준을 넘으면 
    videoPlayer(fvo);
}

axios.get(`/video/${vno}`, {
    headers: {
        "Content-Type": "application/json",
    },
}).then(function (response) {
    if (response.data != "") {
        showVideoDetail(response.data.vvo, response.data.fvo, response.data.mine);
        displayLike(response.data.vvo.likeCount, response.data.like);
    }

}).catch(function (error) {
    console.log(error);
});

if (sort != "noMsg" && msg != "noMsg") {
    customAlert(sort, msg);
}