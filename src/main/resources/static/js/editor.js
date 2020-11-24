function adder_fn(){
    //입력폼이 보이는 경우
    if($('#eventAdder').attr('class') === 'show'){
        $('#eventAdder').attr('class','notShow');
        $('#del').attr('class','notShow');
        $('#key').val("");
        $('#eventTitle').val("");
        $('#eventDate').val("");
        $('#eventTime').val("");
        $('#eventDes').val("");
        $('#del').attr('class','notShow');
        $('#eventSelect').val("");
        $('#key').attr('value',0);
    } else { //입력폼이 보이지 않는 경우
        $('#eventAdder').attr('class','show');
        $('#del').attr('class','notShow');
        $('#key').attr('value',0);
    }
}

function set_event(){
    //신규 등록인 경우
    let check = $('#key').attr('value');
    if(Number(check) == 0){
        let title = $('#eventTitle').val();
        let date = $('#eventDate').val();
        let time = $('#eventTime').val();
        let des = $('#eventDes').val();
        let type = $('#eventSelect').val();
        //예외처리
        if(title === "" || date === "" || time === "" || type == ""){
            alert("모든 데이터를 입력해주세요");
            return;
        }
        let addData = {
            title: title,
            date: date,
            time: time,
            description: des,
            scheduleType: type
        };
        console.log(addData);
        let key;
        let data;
        database.once('value').then(function (snapshot) {
            data = snapshot.val();
            key = Number(data.manyofdata) + 1;
            database.child(key+"/data").set(addData);
            database.child('manyofdata').set(key);
        }).then(function (){
            $('#key').val("");
            $('#eventTitle').val("");
            $('#eventDate').val("");
            $('#eventTime').val("");
            $('#eventDes').val("");
            $('#eventSelect').val("");
            $('#eventAdder').attr('class',"notShow");
            getList();
        }).catch(function (error){
            console.log(error.message);
        });

    } else { //수정인 경우
        let title = $('#eventTitle').val();
        let date = $('#eventDate').val();
        let time = $('#eventTime').val();
        let des = $('#eventDes').val();
        let type = $('#eventSelect').val();
        //예외처리
        if(title === "" || date === "" || time === "" || type == ""){
            alert("모든 데이터를 입력해주세요");
            return;
        }
        let addData = {
            title: title,
            date: date,
            time: time,
            description: des,
            scheduleType: type
        };
        console.log(addData);
        database.once('value').then(function (snapshot) {
            data = snapshot.val();
            key = $('#key').attr('value');
            database.child(key+"/data").set(addData);
            database.child('manyofdata').set(key);
        }).then(function (){
            $('#del').attr('class','notShow');
            $('#key').val("");
            $('#eventTitle').val("");
            $('#eventDate').val("");
            $('#eventTime').val("");
            $('#eventDes').val("");
            $('#eventSelect').val("");
            $('#eventAdder').attr('class',"notShow");
            getList();
        }).catch(function (error){
            console.log(error.message);
        });
    }
}

function del_event(){
    let key = $('#key').attr('value');
    let data;
    database.once('value').then(function (snapshot){
        database.child(key).set(null);
        data = snapshot.val();
        let subData;
        for(let i=key;i<data.manyofdata;i++){
            subData = snapshot.child(key+1 + '/data').val();
            database.child(key+'/data').set(subData);
        }
        key -= 1;
        database.child('manyofdata').set(key);
        $('#del').attr('class','notShow');
        $('#key').val("");
        $('#eventTitle').val("");
        $('#eventDate').val("");
        $('#eventTime').val("");
        $('#eventDes').val("");
        $('#eventSelect').val("");
        $('#eventAdder').attr('class',"notShow");
        getList();
    });
}

function modif(key){
    database.once('value').then(function (snapshot){
        let data = snapshot.child(key+"/data").val();
        console.log(data);
        $('#del').attr('class','show');
        $('#key').attr('value',key);
        $('#eventTitle').val(data.title);
        $('#eventDate').val(data.date);
        $('#eventTime').val(data.time);
        $('#eventDes').val(data.description);
        $('#eventSelect').val(data.scheduleType);
        $('#eventAdder').attr('class',"show");
    });
}