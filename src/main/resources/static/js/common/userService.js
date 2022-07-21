// 로그인 비로그인 구분
const refreshToken = localStorage.getItem("refreshToken");
const navList = document.getElementById("navList");
let li = document.createElement("li");
if (refreshToken != null && refreshToken != "undefined") {
  li.className = "nav-item dropdown";
  li.innerHTML = `<a href="#" class="nav-link dropdown-toggle">마이 메뉴</a>
      <ul class="dropdown-menu">
      <li class="nav-item"><a class="dropdown-item" href="/member/detail">내 정보</a></li>
      <li class="nav-item"><a class="dropdown-item" href="/boardone/register">이미지 업로드</a></li>
      <li class="nav-item"><a class="dropdown-item" href="/video/register">동영상 업로드</a></li>
      <li class="nav-item"><a class="dropdown-item" href="/member/mywork">내 작업</a></li>
      <li class="nav-item"><a class="dropdown-item" href="#" id="logout">로그아웃</a></li>
      </ul>`;
      // <li class="nav-item"><a class="dropdown-item" href="/video/trans">동영상 변환</a></li>
} else {
  li.className = "nav-item dropdown";
  li.innerHTML = `<a href="/member/login" class="nav-link">로그인/회원가입</a>
    <ul class="dropdown-menu">
    <li class="nav-item"><a href="/member/login" class="dropdown-item">로그인</a>
    <li class="nav-item"><a href="/member/register" class="dropdown-item">회원가입</a></li>
    </ul>`;
  }
  navList.appendChild(li);

// 로그아웃
const logout = document.getElementById("logout");
if (logout != null) {
  logout.addEventListener("click", (e) => {
    e.preventDefault();
    localStorage.removeItem("accessToken");
    localStorage.removeItem("refreshToken");
    location.href = "/?sort=inf&msg=h2"
   })
}