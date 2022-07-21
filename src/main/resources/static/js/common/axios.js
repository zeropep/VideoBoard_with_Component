const UNAUTHERISED = 401;
const EXPIRED_ACCESS_TOKEN = 444;
const EXPIRED_REFRESH_TOKEN = 445;


// Request Interceptor
axios.interceptors.request.use(
  (config) => {
  // loading중 표시 띄우기
  if (config.method != "post" && config.url != "/video") {
    document.getElementById("loadingImg").style.display = "block";
  }
  
  // 요청 헤더에 accessToken 세팅
  let accessToken = localStorage.getItem("accessToken");
  config.headers.accessToken = accessToken ? accessToken : "";

  
  return  config;
  },
  (error) => {
    return Promise.reject(error);
  });

// Response Interceptor
axios.interceptors.response.use(
  response => {
      console.log("response arrived");
      document.getElementById("loadingImg").style.display = "none";
    return response;
  },
  async error => {
      console.log("error intercepted");
    const {
      config,
      response: {status},
    } = error;
    console.log(status);

    // accesssToken만료시 재발급
    if (status === EXPIRED_ACCESS_TOKEN) {
      const originalRequest = config;
      const refreshToken = await localStorage.getItem('refreshToken');

      // token refresh 요청
      const {data} = await axios.post(
      `http://localhost:8080/auth/member/refreshToken`, // token refresh api
      {},
      {headers: {refreshToken: refreshToken}},
      );

      // 445(refreshToken만료)시 로그인창으로
      console.log(data.result);
      if (data.result.code == EXPIRED_REFRESH_TOKEN) {
        console.log(data.result.code);
        window.location = "/member/login?sort=war&msg=l2";
      }
      // 새로운 토큰 저장
      localStorage.clear("accessToken");
      localStorage.clear("refreshToken");
      localStorage.setItem("accessToken", data.result.accessToken);
      localStorage.setItem("refreshToken", data.result.refreshToken);
      
      // 실패했던 요청 새로운 토큰으로 재요청
      originalRequest.headers.accessToken = data.result.accessToken;
      return axios(originalRequest);
    } else if (status == UNAUTHERISED) { // (권한없음)을 반환받았을 때
      localStorage.clear("accessToken");
      localStorage.clear("refreshToken");
      document.getElementById("loadingImg").style.display = "none";
      window.location = "/member/login?sort=war&msg=l2";
    } else if (status == 402) {
      console.log("interceptor");
    }
    document.getElementById("loadingImg").style.display = "none";
    return Promise.reject(error);
  },
);