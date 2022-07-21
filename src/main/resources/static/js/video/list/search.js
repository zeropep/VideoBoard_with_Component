document.getElementById("keyword").addEventListener("keyup", (e) => {
    if (e.key == "Enter") {
        document.getElementById("searchBtn").click();
    };
})

document.getElementById("searchBtn").addEventListener("click", (e) => {
    e.preventDefault();
    const keyword = document.getElementById("keyword").value;
    if (keyword == "") {
        customAlert("warning", "검색어를 입력하세요.");
        return;
    }
    Cookies.set("keyword", keyword);
    location.href="/video/list?key=0";
})