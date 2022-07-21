// Overview - workflow
const jobView = document.getElementById("jobView");
const canvas = document.getElementById("canvas");
function showOverview(jobList) {
    jobView.innerHTML = "";
    if (jobList.length > 0) {
        jobList.forEach((job,idx) => {
            // job list
            let tr = `<tr class="bg-pastel-blue">
                        <th scope="row">${idx + 1}</th>
                        <td>${getComponentName(job.component)}</td>
                        <td>${getStatus(job.status)}</td>
                        <td>${getFileType(job.fileType)}</td>
                      </tr>`;
            jobView.innerHTML += tr;

            // canvas 없애기
            if (job.component == "1001" && job.status == "03") {
                canvas.style.display = "none";
            }
        });
    } else {
    let tr = `<tr class="bg-pastel-blue">
                <th scope="row" colspan="4">No work found.</th>
                </tr>`;
    jobView.innerHTML = tr;

    }
}

// Overview - fileInfo
const fileInfo = document.getElementById("fileInfo");
const filter = ["fno", "uuid", "isDelete", "saveDir", "poster"];

function showFileInfo(fvo) {
    fileInfo.innerHTML = "";
    if (fvo != null) {
        let keys = Object.keys(fvo).filter((key) => !filter.includes(key));
        keys.forEach(key => {
            let tr = `<tr>
                        <td scope="col" width="40%">${fileInfoItems(key)}</th>
                        <td scope="col" width="60%">${key == "fileSize" ? (fvo[key] / (1024 * 1024)).toFixed(2) + " MB" : fvo[key]}</td>
                      </tr>`;
            fileInfo.innerHTML += tr;
        });
    } else {
    let tr = `<tr>
                <th scope="row" colspan="2">No info found.</th>
                </tr>`;
    fileInfo.innerHTML = tr;
    }
}

function fileInfoItems(key) {
    let keyName = "";
    switch (key) {
        case "vno":
            keyName = "Content No."
            break;
        case "saveDir":
            keyName = "Upload time";
            break;
        case "fileName":
            keyName = "File name";
            break;
        case "fileExt":
            keyName = "Extension";
            break;
        case "fileType":
            keyName = "File type";
            break;
        case "fileSize":
            keyName = "File size";
            break;
        case "createdAt":
            keyName = "Uploaded at";
            break;
    
        default:
            break;
    }
    return keyName;
}

// Cataloger

const catalogTab = document.getElementById("tab1-3");

function timeConverter(startTime) {
    let hour = startTime.substring(0,2) * 1 * 60 * 60;
    let min = startTime.substring(3,5) * 1 * 60;
    let sec = startTime.substring(6,8) * 1;
    let mic = startTime.substring(9) * 1 / 60;
    return (hour + min + sec + mic).toFixed(5)
}

function showCatalog(catalogList) {
    catalogTab.innerHTML = ``;
    if (catalogList.length > 1) {
        for (let i = 1; i < catalogList.length; i++) {
            let figure = `<figure class="catalog-item mb-20">
                            <a href="#" data-time="${timeConverter(catalogList[i].startTime)}"><img src="/upload${catalogList[i].imagePath}"></a>
                            <figcaption class="text-center">${catalogList[i].startTime}</figcaption>
                          </figure>`;
            catalogTab.innerHTML += figure;
        }
    } else if (catalogList.length == 1) {
        let figure = `<figure class="mb-20 pt-30 w-100 text-center">
                        Video is too short to make catalog..
                      </figure>`;
        catalogTab.innerHTML = figure;
    } else {
        let figure = `<figure class="mb-20 pt-30 w-100 text-center">
                        Preparing to show catalog images...
                      </figure>`;
        catalogTab.innerHTML = figure;

    }
}

document.addEventListener("click", (e) => {
    if (e.target.tagName.toLowerCase() == "img") {
        e.preventDefault();
        let currentTime = e.target.parentNode.dataset.time;
        if (currentTime != null) {
            player.currentTime(currentTime);
        }
    }
})

