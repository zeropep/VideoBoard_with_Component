// document.getElementById("regForm").addEventListener("submit", (e) => {
//     e.preventDefault();
//     let data = {
//         "title": document.getElementById("title").value,
//         "content": document.getElementById("content").value,
//     };
//     axios({
//         method: "post",
//         url: "/boardone",
//         params: data,

//     })
//     .then(function (response) {
//         if (response) {
//             location.href="/boardone/list";
//         }
//     });
    
// });

document.getElementById("regForm").addEventListener("submit", (e) => {
    e.preventDefault();
    let now = new Date();

    const arr = document.getElementById("file").files;
    let formData = new FormData();
    // formData.append("title", document.getElementById("title").value);
    // formData.append("description", document.getElementById("description").value);
    // formData.append("files", arr[0]);
    formData.append("title", now.toLocaleString());
    formData.append("content", `테스트용입니다. 업로드 시간은 ${now.toLocaleString()}입니다.`);
    formData.append("files", arr[0]);

    axios.post("/boardone", formData, {
        Headers: {
            "Content-Type": "multipart/form-data"
        },
        
        // onUploadProgress: progressEvent => {
            // let bar = new ldBar("#upload", {
            //     "width": 100,
            //     "preset": "rainbow"
                
            // });
            // let percentComplete = progressEvent.loaded / progressEvent.total;
            // percentComplete = parseInt(percentComplete * 100);
            // bar.set(percentComplete);
            
            // if (percentComplete == 100) {
                    // document.getElementById("loadingImg").style.display = "block";
            //     }
        // }
    })
    .then(function (response) {
        if (response.data > 0) {
            // location.href=`/video/comp/${response.data}`;
            location.href=`/boardone/list?curPage=1`;
        }
    });
    
});
