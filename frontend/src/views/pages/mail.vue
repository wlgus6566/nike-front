<template>
  <div>
    <img src="../../assets/images/@test1.jpg" alt="">
  </div>
</template>
<script>
export default {
  name: "mail",
  created() {
    this.SaveToDisk('../../assets/images/@test1.jpg' , 'test.jpg')
  },
  methods:{
     makeFrame(url,target){
      let ifrm = document.createElement( "IFRAME" );
      ifrm.setAttribute( "style", "display:none;" ) ;
      ifrm.setAttribute( "src", url ) ;
      ifrm.setAttribute( "name", target) ;
      ifrm.style.width = 0+"px";
      ifrm.style.height = 0+"px";
      document.body.appendChild( ifrm ) ;
    },
     removeiframe(){
       let iframes = document.getElementsByTagName('iframe');
      for (var i = 0; i < iframes.length; i++){
        iframes[i].parentNode.removeChild(iframes[i]);
      }
    },
    SaveToDisk(fileURL, fileName){
      // for non-IE
      if (!(window.ActiveXObject || "ActiveXObject" in window)){
        console.log(23)
        let save = document.createElement('a');
        save.href = fileURL;
        save.target = '_blank';
        save.download = fileName || fileURL;
        let evt = document.createEvent('MouseEvents');
        evt.initMouseEvent('click', true, true, window, 1, 0, 0, 0, 0, false, false, false, false, 0, null);
        save.dispatchEvent(evt);
        (window.URL || window.webkitURL).revokeObjectURL(save.href);
        window.close()
      }
      // for IE
      else if ( window.ActiveXObject || "ActiveXObject" in window ){
        console.log(3424)
        this.makeFrame(fileURL,fileName);
        alert('"' + fileName +'" 이미지 파일을 다운로드 합니다.');
        let _window = window.open(fileURL, fileName);
        _window.document.close();
        _window.document.execCommand('SaveAs', true, fileName || fileURL)
        _window.close();
        this.removeiframe();
        window.close()
      }
    }
  }
}
</script>
<style scoped></style>
