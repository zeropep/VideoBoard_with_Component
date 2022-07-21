let mno = null;
const title = document.getElementById("title");
const description = document.getElementById("description");
const file = document.getElementById("file");
const back = document.getElementById("back");
const uploaded = document.getElementById("uploaded");

function showVideoDetail(vvo, fvo) {
    title.value = vvo.title;
    description.innerText = vvo.description;
    uploaded.innerHTML = ` ${fvo.fileName + fvo.fileExt} <span class="badge badge-pill bg-default">${(fvo.fileSize / (1000 * 1000)).toFixed(1)} MB</span>`;
    mno = vvo.mno;
    // back.setAttribute("href", `/video/detail/${vno}`);
}

axios.get(`/video/${vno}`, {
    headers: {
        "Content-Type": "application/json",
    },
}).then(function (response) {
    if (response.data != "") {
        showVideoDetail(response.data.vvo, response.data.fvo);
    }

}).catch(function (error) {
    console.log(error);
});


document.getElementById("modForm").addEventListener("submit", (e) => {
    e.preventDefault();

    const arr = file.files;
    let formData = new FormData();
    formData.append("title", title.value);
    formData.append("description", description.value);
    formData.append("files", arr[0]);
    formData.append("mno", mno);
    axios.put(`/video/${vno}`, formData, {
        Headers: {
            "Content-Type": "multipart/form-data"
        },
    })
    .then(function (response) {
        if (response.data) {
            location.href=`/video/detail/${vno}?sort=suc&msg=v2`;
        }
    });
    
});

file.addEventListener("input", (e) => {
    let newFile = e.target.files[0]
    uploaded.innerHTML = ` ${newFile.name} <span class="badge badge-pill bg-default">${(newFile.size / (1000 * 1000)).toFixed(1)} MB</span>`;
})