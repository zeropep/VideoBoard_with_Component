let video = document.getElementById("myVideo");
let player = null;

function videoPlayer(fvo) {
    let option = {
        sources: [
            {src: `/upload/proxy/video/${fvo.saveDir}/${fvo.uuid}_${fvo.fileName}.mp4`, type: "video/mp4"}
        ],
        playbackRates: [.5, .75, 1, 1.25, 1.5],
        autoplay: true,
        poster: false,
        controls: true,
        preload: "auto",
        width: 540,
        height: 320,
        controlBar: {
            playToggle: true,
            pictureInPictureToggle: true,
            remainingTimeDisplay: false,
            progressControl: true,
            qualitySelector: true,
        }
    };

    player = videojs("myVideo", option);

    // player.createModal("my message");

    let playerInfo = Cookies.get("playerInfo");
    let current = Cookies.get(vno);

    let volume, muted, rate, time = null;
    if (playerInfo != null) {
        let playerInfoObj = JSON.parse(playerInfo);
        
        volume = playerInfoObj.volume;
        muted = playerInfoObj.muted;
        rate = playerInfoObj.playbackRate;
    }
    if (current != null) {
        let currentObj = JSON.parse(current);
    
        time = currentObj.current;
    }
    let duration = null;
    
    player.ready(() => {
        player.volume(volume != null ? volume : 1);
        player.muted(muted != null ? muted : false);
        player.playbackRate(rate != null ? rate : 1);
        player.one("loadedmetadata", () => {
            duration = player.duration();
            player.currentTime(time != null && time != duration ? time : 0);
        })
    })
    video.click();
}

document.addEventListener("keydown", (e) => {
    let body = document.getElementById("page-top");
    if (e.key == " " && document.activeElement == body) {
        e.preventDefault();
        player.paused() ? player.play() : player.pause();
    }
});

function viewCountUpToServer(vno) {
    axios.put(`/view/${vno}`)
    .then((response) => {
        return response.data;
        // 혹시 실시간으로 시청자 수가 올라야 한다면 여기
    })
}

video.addEventListener("ended", () => {
    let videoObj = Cookies.get(vno + "view");
    if (videoObj == null) {
        const viewMarker = JSON.stringify({
            "view": 1
        });
        Cookies.set(vno + "view", viewMarker, { expires: 1 });
        viewCountUpToServer(vno);
    } 
})

setInterval(() => {
    const stringObj = JSON.stringify({
        "volume" : player.volume(),
        "muted": player.muted(),
        "playbackRate": player.playbackRate(),
    });

    const current = JSON.stringify({
        "current": player.currentTime(),
        "expire": Date.now() + 1000 * 60 * 60 * 24, // 24시간
    })

    Cookies.set("playerInfo", stringObj, { expires: 3 });
    Cookies.set(vno, current, { expires: 3 });
}, 500)