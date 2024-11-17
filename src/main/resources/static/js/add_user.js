$(document).ready(function(){
    $('#checkId').on("click",function(){
        $('#passwordInput').prop("required",false);
        $('#rePasswordInput').prop("required",false);
        $('#nameInput').prop("required",false);
        $('#kanaInput').prop("required",false);
        $('#birthInput').prop("required",false);
        if (!ckDate($('#birthInput').val())) {
            $('#birthInput').prop("disabled",true);
        }
    });
    $('#register').on("click",function(){
        $('#passwordInput').prop("required",true);
        $('#rePasswordInput').prop("required",true);
        $('#nameInput').prop("required",true);
        $('#kanaInput').prop("required",true);
        $('#birthInput').prop("required",true);
        $('#birthInput').prop("disabled",false);
    });
});

/****************************************************************
* 機能： 入力された値が日付でYYYY/MM/DD形式になっているか調べる
* 引数： strDate = 入力された値
* 戻り値： 正 = true 不正 = false
****************************************************************/
function ckDate(strDate) {
    if(!strDate.match(/^\d{4}\/\d{2}\/\d{2}$/)){
        return false;
    }
    var y = strDate.split("/")[0];
    var m = strDate.split("/")[1] - 1;
    var d = strDate.split("/")[2];
    var date = new Date(y,m,d);
    if(date.getFullYear() != y || date.getMonth() != m || date.getDate() != d){
        return false;
    }
    return true;
}