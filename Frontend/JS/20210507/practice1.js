

function print(lis) {
    return Array.from(lis).filter(e=>e.innerText.includes("e"));
}

const lis = document.getElementsByTagName("li");

console.log(print(lis));

