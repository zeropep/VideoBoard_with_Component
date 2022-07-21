document.getElementById("del").addEventListener("click", () => {
    axios.delete(`/boardone/${bno}`, {
    }).then(function (response) {
        console.log(response.data);
        if (response.data) {
            location.href=`/boardone/list?curPage=${curPage}`;
        }
    });
})
