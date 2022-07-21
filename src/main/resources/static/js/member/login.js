const loginForm = document.getElementById("login");

function UNAUTHORIZEDException() {
    location.href = "/member/login?sort=dan&msg=h3";
}

async function login(email, pwd) {
    const url = "/auth/member/login";
    const config = {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({
            "email": email,
            "pwd": pwd
        }),
    }
    const resp = await fetch(url, config);
    return await resp.json();
}


loginForm.addEventListener("submit", (e) => {
    e.preventDefault();
    let email = document.getElementById("email").value;
    let pwd = document.getElementById("pwd").value;
    login(email, pwd).then(result => {
        if (result.code == 401) {
            UNAUTHORIZEDException();
        }
        localStorage.setItem('accessToken', result.result.accessToken)
        localStorage.setItem('refreshToken', result.result.refreshToken)
        location.href="/?sort=suc&msg=h1";
    })
    
})

if (sort != "noMsg" && msg != "noMsg") {
    customAlert(sort, msg);
}