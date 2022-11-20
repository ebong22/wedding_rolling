
/**
@todo key 숨기기
*/
Kakao.init('671dc964346c250e54c044f820ce5a53');

const common = {

    enterChangeBr(){
        let textarea = document.querySelector("textarea");
        let text = textarea.value;
        text = text.replace(/(?:\r\n|\r|\n)/g, '<br>');
        textarea.value = text;
    },

    passwordPopup(downloadType){
        let popWrap = document.querySelector("#check-password");
        popWrap.classList.remove("hidden");
        document.querySelector("#password-pop-btn").dataset.type=downloadType;
        let popupBg = document.createElement("div");
        popupBg.id = "popup-bg";
        document.querySelector("body").appendChild(popupBg);

        // 배경 클릭 시 팝업 닫기
        popupBg.addEventListener("click",function(){
            common.popupClose(popupBg, popWrap);
        });
        // 닫기 버튼 클릭 시
        document.querySelector("#pw-x-btn").addEventListener("click",function(){
           common.popupClose(popupBg, popWrap);
       });
    },

    /**
        비밀번호 확인 팝업 닫기
    */
    popupClose(bgElement,popElement){
        document.querySelector("#password-input").value="";
        bgElement.remove();
        popElement.classList.add("hidden");
    },

    /**
        비밀번호 확인 <br>
        다운로드 버튼
    */
    checkPassword(){
        const downloadType = document.querySelector("#password-pop-btn").dataset.type;
        const popupBg = document.querySelector("#popup-bg");
        const popWrap = document.querySelector("#check-password");
        console.log(downloadType);

        const pwInput = document.querySelector("#password-input");
        if(pwInput.value != null && pwInput !=""){
            fetch(window.location.origin + "/check/" + pwInput.dataset.boardId, {
                      method: "POST",
                      headers: {
                        "Content-Type": "application/json",
                      },
                      body:pwInput.value,
                }).then((response) => response.json())
                .then((data) => {
                    if(data == true){
                        if(downloadType == 'img' ){ // 지금 이미지 버튼 숨겨둠(필요가 없을 것 같아서)
                            common.downloadImage('img-canvas');
                            common.popupClose(popupBg, popWrap);
                        }
                        if(downloadType == 'pdf' ){
                            common.downloadPdfBtn();
                            common.popupClose(popupBg, popWrap);
                        }
                        if(downloadType == 'kakao'){
                            document.querySelector("#kakaotalk-sharing-btn").click();
                            common.popupClose(popupBg, popWrap);
                        }
                    }
                    if(data == false){
                        alert("비밀번호를 확인해 주세요");
                    }
                });
        }
    },

//    download(type){
//        const authYn = common.checkPassword();
//    },

    downloadImage(id){
        let imgElement =  document.getElementById(id);
        // 다운로드용 클래스 추가
        imgElement.classList.add("py-3", "w-[120%]", "bg-wd-pink");

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
        // 다운로드용 클래스 제거
        imgElement.classList.remove("py-3", "w-[120%]", "bg-wd-pink");
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
        let fileNm = document.querySelector(".couple-name").innerText;
//        const pageCnt = document.querySelectorAll("#pdf-wrap-inner").length;
//        const height = 1500 * pageCnt;

        const opt = {
//            pagebreak:  "css"
             filename:     fileNm + ".pdf"
            , html2canvas:  {   scale:1
                              ,backgroundColor: "#E5809F"
                            }
            , jsPDF:        {   unit: 'px'
                                , format: [1501, 1501]
                                , backgroundColor: "#E5809F"
//                                , orientation: 'portrait'
//                                , compressPDF: true
                            }
         , pagebreak:       {  after : '.page-wrap'
                               }

        };
        html2pdf().set(opt).from(element).save();
    },

    async getPdfView(id) {
    	const response = await fetch(
    		window.location.origin + "/download/" + id ,
    		{
    			method: 'GET'
    		}
    	);
        return await response.text();
    },

     downloadPdfBtn(){
        const boardId = document.querySelector("#pdf-btn").dataset.boardId;
        let getPdfView = common.getPdfView(boardId);
        console.log(getPdfView);

        let div = document.createElement("div");
        div.id = "temp4pdf";
        document.querySelector('body').append(div);

        getPdfView.then((itm) => {
            div.innerHTML = itm;
            document.querySelectorAll("#pdf-wrap-inner").forEach(itm => {
                itm.classList.remove("hidden");
            })
//            document.querySelector("#pdf-wrap").classList.remove("hidden");
            common.downloadPdf("pdf-wrap");
            document.querySelector("#temp4pdf").remove();
        });
    },

}

Kakao.Share.createDefaultButton({
        container: '#kakaotalk-sharing-btn',
        objectType: 'feed',
        content: {
          title: document.querySelector(".couple-name").innerText + " 결혼합니다.",
          description: "저희의 결혼을 축하해주세요!",
          imageUrl:
            'http://k.kakaocdn.net/dn/wl01N/btrPDrOXuW5/aMmeXzLCWPFy368MOs3kj0/kakaolink40_original.png',
          link: {
            mobileWebUrl: window.location.href,
            webUrl: window.location.href,
          },
        },
        buttons: [
          {
            title: '축하 메세지 보내기',
            link: {
              mobileWebUrl: window.location.href,
              webUrl: window.location.href,
            },
          },
        ],
  });


//  Kakao.Share.createCustomButton({
//      container: '#kakaotalk-sharing-btn',
//      templateId: 84778,
//    });