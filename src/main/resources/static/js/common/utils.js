// 댓글 작성시간 계산용
function displayedAt(modAt) {
    let date = new Date(modAt);
    const milliSeconds = new Date() - date
    const seconds = milliSeconds / 1000
    if (seconds < 60) return `방금 전`
    const minutes = seconds / 60
    if (minutes < 60) return `${Math.floor(minutes)}분 전`
    const hours = minutes / 60
    if (hours < 24) return `${Math.floor(hours)}시간 전`
    const days = hours / 24
    if (days < 7) return `${Math.floor(days)}일 전`
    const weeks = days / 7
    if (weeks < 5) return `${Math.floor(weeks)}주 전`
    const months = days / 30
    if (months < 12) return `${Math.floor(months)}개월 전`
    const years = days / 365
    return `${Math.floor(years)}년 전`
  }

  // 조회수 표시용
function viewCounter(view) {
  if (view < 1000) return `${view}회`;
  if (view >= 1000 && view < 10000) return `${(view / 1000).toFixed(1)}천 회`;
  if (view >= 10000 && view < 100000) return `${(view / 10000).toFixed(1)}만 회`;
  if (view >= 100000 && view < 100000000) return `${(view / 10000).toFixed(0)}만 회`;
  if (view >= 100000000) return `${(view / 100000000).toFixed(1)}억 회`;
}

  // 새로고침 마다 페이지 초기화
Cookies.set("curPage", 1);
Cookies.set("cmtCurPage", 1);

// 쿠키 저장
// function setCookie(key, value, expire) {
//   let today = new Date();
//   today.setDate(today.getDate() + expire); // 일자 단위로 저장
//   Cookies.set(key, value, expire);
// }

// alert
function customAlert(sort, message) {
  const alertDiv = document.getElementById("alert");
  let color = "";
  alertDiv.querySelector("p").innerText = message;
  switch (sort) {
    case "success":
      color = "bg-pastel-blue"
      break;
    case "info":
      color = "bg-pastel-green"
      break;
    case "warning":
      color = "bg-pastel-yellow"
      break;
    case "danger":
      color = "bg-pastel-red"
      break;
  
    default:
      color = "bg-pastel-blue";
      break;
  }
  alertDiv.classList.add(color);
  alertDiv.classList.add("notify");
  setTimeout(() => {
    alertDiv.classList.remove("bg-pastel-blue");
      alertDiv.classList.remove("notify");
  }, 3000)
}

// top-button
document.addEventListener("click", (e) => {
  if (e.target.id == "topBtn") {
    e.preventDefault();
    window.scrollTo({
      top: 0,
      left: 0,
      behavior: "smooth"
    })
  }
})

// get component name
function getComponentName(component) {
  let name = "";
  switch (component) {
    case "1001":
      name = "Transcoder";
      break;
      case "1003":
      name = "Cataloger";
      break;
  
    default:
      break;
  }
  
  return name;
}

// get File Type
function getFileType(fileType) {
  let type = "";
  switch (fileType) {
    case "01":
    type = "image";
    break;
    case "02":
    type = "video";
    break;
    case "03":
    type = "audio";
    break;
  
    default:
      break;
  }
  return type;
}

// getStatus
function getStatus(status) {
  let statusName = "";
  switch (status) {
    case "01":
      statusName = "준비";
      break;
    case "02":
      statusName = "진행";
      break;
    case "03":
      statusName = "완료";
      break;
  
    default:
      break;
  }

  return statusName;
}