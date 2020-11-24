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
        for(let i = 1;i<=data.manyofdata;i++){
            let subData = snapshot.child(i).val();
            console.log(subData);
            let key = i+'event';
            let title = subData.data.title;
            let date = subData.data.date;
            let time = subData.data.time;
            html = "<li id='" + key + "' onclick=''>" + title + " " + date + " " + time + "</li>";
            $('#eventList').append(html);
        }
    });
}

function onAddEvent(data){
    let key= data.key;
    let eventData = data.val();
    let title = eventData.title;
    let date = eventData.date; // 메모 내용의 첫 줄을 제목으로 보여줌
    let time = eventData.time;
}

function onModEvent(data){
    let key= data.key;
    let eventData = data.val();
    let title = eventData.title;
    let date = eventData.date; // 메모 내용의 첫 줄을 제목으로 보여줌
    let time = eventData.time;
}