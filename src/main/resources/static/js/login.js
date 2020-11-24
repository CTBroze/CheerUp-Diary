$('#btn_google_login').click(function () {
    var provider = new firebase.auth.GoogleAuthProvider();

    if ($(this).html() == "로그인") {
        firebase.auth().signInWithPopup(provider).then(function (result) {
            $('#auth_state').text(result.user.displayName);
            $('#btn_google_login').html('로그아웃');
            getList();
        }).catch(function (error) {
            alert(error.message);
        });
    } else{
        firebase.auth().signOut().then(function (){
            alert("로그아웃되었습니다");
            $('#auth_state').text('[로그인되지 않음]');
            $('#btn_google_login').html('로그인');
            clearList();
        }).catch(function (error){
            alert(error.message);
        });
    }
});