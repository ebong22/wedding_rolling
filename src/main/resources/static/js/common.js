const common = {

    downloadImage(id){
        html2canvas(document.getElementById(id)).then(canvas => {
            const img = canvas.toDataURL();
            const fileNm = "파일명";
            common.downloadURI(img, fileNm + ".png")
        });
    },
    downloadURI(uri, name){
          var link = document.createElement("a")
          link.download = name;
          link.href = uri;
          document.body.appendChild(link);
          link.click();
    }

}