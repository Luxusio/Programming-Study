const contentList = document.querySelectorAll("#contents #contents #contents")[2].querySelectorAll("#contents #container #thumbnail #thumbnail #overlays ytd-thumbnail-overlay-time-status-renderer span");

let duration = 0;

for(let content of contentList) {
    let s = content.innerText.split(":");
    duration += parseInt(s[0]) * 60 + parseInt(s[1]);
}

console.log("duration:", Math.floor(duration/3600), "h", Math.floor(duration/60) % 60, "m", duration%60,  "s");