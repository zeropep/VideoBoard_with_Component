axios.post("/auth/member/list", {
    headers: {
        "Content-Type": "application/json",
    },
}).then(function (response) {
    console.log(response.data);
}).catch(function (error) {
    console.log(error);
});
