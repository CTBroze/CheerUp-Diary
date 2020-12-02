var userId;
var database;
/*
    데이터구조
    Event: {
        UID: {
            1: {
                data: {
                    date : "날짜",
                    descriptin : "des",
                    scheduleType : "st",
                    time : "time",
                    title : "title"
                }
            },
            manyofdata: 2,
        }
    }
 */


function getList(){
    userId = firebase.auth().currentUser.uid;
    database = firebase.database().ref('Event/'+userId);
    database.once('value').then(function (snapshot){
        let data = snapshot.val();
        console.log(data);
        var html;
        clearList();
        for(let i = 1;i<=data.manyofdata;i++){
            let subData = snapshot.child(i).val();
            console.log(subData);
            let key = i;
            let title = subData.data.title;
            let date = subData.data.date;
            let time = subData.data.time;
            html = "<li id='" + key + "' onclick='modif(" + key +")'>일정명 : " + title + " 일자 : " + date + " 시간 : " + time + "</li>";
            $('#eventList').append(html);
        }
    });
}

function clearList(){
    $('#eventList').html("");
}