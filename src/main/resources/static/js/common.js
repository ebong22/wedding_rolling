const common = {
    enterChangeBr(){
        let textarea = document.querySelector("textarea");
        let text = textarea.value;
        text = text.replace(/(?:\r\n|\r|\n)/g, '<br>');
        textarea.value = text;
    },

    ////////////////////////////////////////////////
    ////////////////////////////////////////////////
    ////////////////////////////////////////////////
    ////////////////////////////////////////////////
    /////////비동기로 컨트롤러에서 체크 후 true일 떄////////////////
    ////////////a 태그에 type에 맞는 js function 링크 걸어주기//////////////
    ////////////////////////////////////////////////
    passwordPopup(downloadType){
        document.querySelector("#check-password").classList.remove("hidden");
        document.querySelector("#password-pop-btn").dataset.type=downloadType;
    },

    checkPassword(){
        const downloadType = document.querySelector("#password-pop-btn").dataset.type;
        console.log(downloadType);

        const pwInput = document.querySelector("#password-input");
        if(pwInput.value != null && pwInput !=""){
            fetch("http://127.0.0.1:8080/check/"+ pwInput.dataset.boardId, {
                      method: "POST",
                      headers: {
                        "Content-Type": "application/json",
                      },
                      body:pwInput.value,
                }).then((response) => response.json())
                .then((data) => {
                    if(data == true && downloadType == 'img' ){
                        common.downloadImage('img-canvas');
                    }
                    if(data == true && downloadType == 'pdf' ){
                        common.downloadPdfBtn();
                    }
                    if(data == false){
                        alert("비밀번호를 확인해 주세요");
                    }
                });
        }
    },

    download(type){
        const authYn = common.checkPassword();
    },

    downloadImage(id){
        let imgElement =  document.getElementById(id);
        // 패딩 주기(보기 좋게 할라고)
        imgElement.classList.add("py-3");

        // 버튼 숨기기
        let btnWrap = document.querySelector(".btn-wrap");
        btnWrap.classList.add("hidden");


        // 파일명
        let fileNm = document.querySelector(".couple-name").innerText;
        fileNm = fileNm.replace(/ /gi,"");

        html2canvas(imgElement).then(canvas => {
            const img = canvas.toDataURL();

            common.downloadURI(img, fileNm + ".png");
        });
        // 버튼 display
        btnWrap.classList.remove("hidden");
        // 패딩 삭제
        imgElement.classList.remove("py-3");
    },
    downloadURI(uri, name){
          var link = document.createElement("a")
          link.download = name;
          link.href = uri;
          document.body.appendChild(link);
          link.click();
    },

    iconCheckedMotion(){
        // 기존 표시 삭제
        let imageBoxs = document.querySelectorAll(".icon-imageBox");
        imageBoxs.forEach(itm => itm.classList.remove("icon-imageBox"));
        // 선택한 이미지에 표시 추가
        let imageBox = document.querySelector("input:checked");
        if(imageBox != null){
            let p = imageBox.closest("li").querySelector("p");
            p.classList.add("icon-imageBox");
        }
    },

    checkTextMaxLength(input){
        const maxLength = input.maxLength;
        if(input.value.length > maxLength){
            input.value = input.value.slice(0,maxLength);
        }
    },

    downloadPdf(elementId){
        const element = document.getElementById(elementId);
        const opt = {
            pagebreak:  'css',
            // margin:       [2,0,0,5],
            filename:     'myfile2.pdf',
            html2canvas:  { scale:1
                            ,backgroundColor: '#afafff'
                            },
            jsPDF:        { unit: 'px', format: [1500, 1500], orientation: 'portrait', compressPDF: true }
        };
        html2pdf().set(opt).from(element).save();
    },

//    ajaxGet(uri,method,param){
//        let result;
//        var xhr = new XMLHttpRequest();
//        // Making our connection
//        let url = "http://127.0.0.1:8080";
//        url = url + uri + param;
//        xhr.open(method, url, true);
//
//        // Sending our request
//        xhr.send();
//
//        // function execute after request is successful
//        xhr.onreadystatechange = function () {
//            if (this.readyState == 4 && this.status == 200) {
////                console.log(this.responseText);
//                result = this.responseText;
//                return result;
//            }
//        }
//    },

//    async testFetch(){
//        let result;
//        await fetch("http://127.0.0.1:8080/download/1").then(function (response) {
//            return response.text();
//         }).then(function (html) {
//            var parser = new DOMParser();
//            let pdfHtml = parser.parseFromString(html, 'text/html');
//            console.log(pdfHtml.querySelector('body'));
//            var pdfDoc = pdfHtml.getRootNode();
//
//            let div = document.createElement("div");
//            document.querySelector('body').append(div);
//            return pdfDoc.body;
//
//
//         }).catch(function (err) {
//            // There was an error
//            console.warn('Something went wrong.', err);
//         });
//    },

    async getPdfView(id) {
    	const response = await fetch(
    		"http://127.0.0.1:8080/download/"+id ,
    		{
    			method: 'GET'
    		}
    	);
        return await response.text();
    },

     downloadPdfBtn(){
        const boardId = document.querySelector("#pdf-btn").dataset.boardId;
        let getPdfView = common.getPdfView(boardId);

        let div = document.createElement("div");
        div.id = "temp4pdf";
        document.querySelector('body').append(div);

        getPdfView.then((itm) => {
            div.innerHTML = itm;
            document.querySelector("#pdf-wrap").classList.remove("hidden");
            common.downloadPdf("pdf-wrap");
            document.querySelector("#temp4pdf").remove();
        });
    },

}