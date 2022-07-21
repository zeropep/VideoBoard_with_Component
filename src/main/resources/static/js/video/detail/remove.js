document.getElementById("del").addEventListener("click", (e) => {
    e.preventDefault();
    if (!window.confirm("정말 삭제하시겠습니까?")) {
        return;
    }
    axios.delete(`/video/${vno}`, {
        data: {
            vno,
            mno: uploader
        }
    }).then(function (response) {
        if (response.data) {
            location.href=`/video/list?sort=suc&msg=v3&key=0`;
        }
    });
})
