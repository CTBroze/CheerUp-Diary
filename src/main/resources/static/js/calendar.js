var today = new Date();//오늘 날짜//내 컴퓨터 로컬을 기준으로 today에 Date 객체를 넣어줌
var date = new Date();//today의 Date를 세어주는 역할

function prevCalendar() {
    today = new Date(today.getFullYear(), today.getMonth() - 1, today.getDate());
    buildCalendar();
}

function nextCalendar() {
    today = new Date(today.getFullYear(), today.getMonth() + 1, today.getDate());
    buildCalendar();
}

function buildCalendar() {
    var doMonth = new Date(today.getFullYear(), today.getMonth(), 1);
    var lastDate = new Date(today.getFullYear(), today.getMonth() + 1, 0);
    var tbCalendar = document.getElementById("calBody");
    var tbCalendarYM = document.getElementById("tbCalendarYM");
    tbCalendarYM.innerHTML = today.getFullYear() + "년 " + (today.getMonth() + 1) + "월";

    while (tbCalendar.rows.length > 0) {
        tbCalendar.deleteRow(tbCalendar.rows.length - 1);
    }
    var row = null;
    row = tbCalendar.insertRow();
    var cnt = 0;// count, 셀의 갯수를 세어주는 역할
    for (i = 0; i < doMonth.getDay(); i++) {
        cell = row.insertCell();
        cnt = cnt + 1;
    }
    for (i = 1; i <= lastDate.getDate(); i++) {
        cell = row.insertCell();
        cell.innerHTML = "<div id=" + "'" + i + "day'>" + i + "</div>";
        cnt = cnt + 1;
        if (cnt % 7 == 1) {
            cell.innerHTML = "<div class='sun'>" + i + "</div>"
        }
        if (cnt % 7 == 0) {
            cell.innerHTML = "<div class='sat'>" + i + "</div>"
            row = calTable.insertRow();
        }
        if (today.getFullYear() == date.getFullYear()
            && today.getMonth() == date.getMonth()
            && i == date.getDate()) {
            cell.bgColor = "yellow";
        }
    }
}