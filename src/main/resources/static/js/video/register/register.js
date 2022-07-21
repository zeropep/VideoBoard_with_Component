document.getElementById("regForm").addEventListener("submit", (e) => {
    e.preventDefault();
    const arr = document.getElementById("file").files;
    let formData = new FormData();
    formData.append("title", document.getElementById("title").value);
    formData.append("description", document.getElementById("description").value);
    formData.append("files", arr[0]);

    axios.post("/video", formData, {
        Headers: {
            "Content-Type": "multipart/form-data"
        },
        
        onUploadProgress: progressEvent => {
            let bar = new ldBar("#upload", {
                "width": 100,
                "preset": "rainbow"
                
            });
            let percentComplete = progressEvent.loaded / progressEvent.total;
            percentComplete = parseInt(percentComplete * 100);
            bar.set(percentComplete);
            
            if (percentComplete == 100) {
                document.getElementById("loadingImg").style.display = "block";
            }
        }
    })
    .then(function (response) {
        if (response.data != null) {
            // location.href=`/video/comp/${response.data}`;
            location.href="/video/list";
        }
    });
    
});
