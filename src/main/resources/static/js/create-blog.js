const fileInput = document.getElementById("banner");
const bannerPreview=document.querySelector(".banner-preview");

fileInput.addEventListener("change",onFileChage);

function onFileChage(event){
    const file=event.target.files[0];

    if(file !== ""){
    const url=URL.createObjectURL(file);

    bannerPreview.innerHTML = `<img src="${url}">`;
    }else{
        
    }
}