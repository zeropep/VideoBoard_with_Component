const email = document.getElementById("email");
const username = document.getElementById("username");
const pwd  = document.getElementById("pwd");
const emailInfoMsg = document.getElementById("emailInfo");
const nameInfoMsg = document.getElementById("nameInfo");
const enroll = document.getElementById("enroll");

let isEmailChecked = true;
let isNameChecked = true;

email.addEventListener("focus", () => {
    emailInfoMsg.hidden = true;
})

username.addEventListener("focus", () => {
    nameInfoMsg.hidden = true;
})

function showEmailResult(result) {
    if (result) {
        emailInfoMsg.innerText = "이미 가입된 이메일입니다.";
        emailInfoMsg.hidden = false;
        emailInfoMsg.classList.remove("blue");
        emailInfoMsg.classList.add("red");
        isEmailChecked = false;
    } else {
        emailInfoMsg.innerText = "가입 가능한 이메일입니다.";
        emailInfoMsg.hidden = false;
        emailInfoMsg.classList.remove("red");
        emailInfoMsg.classList.add("blue");
        isEmailChecked = true;
    }
}

function showNameResult(result) {
    if (result) {
        nameInfoMsg.innerText = "이미 가입된 닉네임입니다.";
        nameInfoMsg.hidden = false;
        nameInfoMsg.classList.remove("blue");
        nameInfoMsg.classList.add("red");
        isNameChecked = false;
    } else {
        nameInfoMsg.innerText = "사용 가능한 닉네임입니다.";
        nameInfoMsg.hidden = false;
        nameInfoMsg.classList.remove("red");
        nameInfoMsg.classList.add("blue");
        isNameChecked = true;
    }
}

email.addEventListener("focusout", () => {
    if (email.value == "") {
        return;
    }
    // 중복확인
    axios.get("/auth/member/email", {
        params: {
            email: email.value,
        }
    }).then(response => {
        showEmailResult(response.data);
    }).catch(error => {
        console.log(error);
    })
})

username.addEventListener("focusout", () => {
    if (username.value == "") {
        return;
    }
    // 중복확인
    axios.get("/auth/member/name", {
        params: {
            username: username.value,
        }
    }).then(response => {
        showNameResult(response.data);
    }).catch(error => {
        console.log(error);
    })
})

enroll.addEventListener("click", (e) => {
    e.preventDefault();
    if (pwd.value == "") {
        customAlert("warning", "비밀번호를 입력해주세요.")
        return;
    }
    if (isEmailChecked == false || isNameChecked == false) {
        customAlert("warning", "입력정보를 다시 확인해주세요.")
        return;
    }
    axios.post("/auth/member/register", {
        email: email.value,
        username: username.value,
        pwd: pwd.value,
    }).then(response => {
        if (response.data) {
            location.href = "/?sort=suc&msg=r1";
        }
    }).catch (error => {
        console.log(error);
    })
})