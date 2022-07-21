let bno = document.getElementById("bno").value;

function showPostDetail(bvo, fvo) {
    const h1 = document.querySelector("div#title h1");
    h1.innerText = bvo.title;

    document.getElementById("mod").setAttribute("href", `/boardone/modify/${bno}?curPage=${curPage}`);

    if (bvo.mine) {
        document.getElementById("mod").hidden = false;
        document.getElementById("del").hidden = false;
    }
    
    const metaDiv = document.querySelector("div#postContainer div.meta");
    metaDiv.innerHTML = `<span class="date">${bvo.modifiedAt == null ? bvo.createdAt : bvo.modifiedAt + "  수정됨"}</span>
                        <span class="author">By <a href="#">${bvo.username}</a></span>
                        <span class="comments" id="cmtCount"><a href="#cmtListArea">${bvo.cmtCount}</a></span>`;

    const img = document.querySelector("div#postContainer img");
    img.src = `/upload/org/video/${fvo.saveDir}/${fvo.uuid}_${fvo.fileName}${fvo.fileExt}`;

    const p = document.querySelector("div#postContainer p");
    p.innerText = bvo.content;

    const back = document.getElementById("back");
    back.setAttribute("href", `/boardone/list?curPage=${curPage}`);
}

axios.get(`/boardone/${bno}`, {
    headers: {
        "Content-Type": "application/json",
    },
}).then(function (response) {
    if (response.data != "") {
        showPostDetail(response.data.bvo, response.data.fvo);
    }

}).catch(function (error) {
    console.log(error);
});