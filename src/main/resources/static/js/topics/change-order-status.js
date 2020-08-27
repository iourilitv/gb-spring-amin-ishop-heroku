var stompClient = null;

/* обновляет страницу без ее перезагрузки */
window.onload = connect();

function connect() {
    /* ВНИМАНИЕ! Нужно обязательно добавлять префикс /amin
    из параметра server.servlet.context-path=/amin в application.properties */
    var socket = new SockJS('/amin/changeOrderStatus');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function(frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/changeOrderStatus', function(response){
            showResponse(JSON.parse(response.body).orderStatus
                );
        });
    });
}

function changeOrderStatus() {
    stompClient.send("/app/changeOrderStatus", {},
        JSON.stringify({
            // принимаем значение выбранной option
            'title': $("#o_s_title.select-btn-dropdown-toggle").val()
        }));
}

function showResponse(orderStatus) {
    console.log(orderStatus);
    document.getElementById('o_s_id_input').setAttribute('value', orderStatus.id);
    document.getElementById('o_s_id_td').innerText=orderStatus.id;
    document.getElementById('o_s_description_input').setAttribute('value', orderStatus.description);
    document.getElementById('o_s_description_td').innerText=orderStatus.description;

    document.getElementById('o_s_button').setAttribute('style', "visibility: visible; font-size: 18px; margin-bottom: 20px; padding: 8px 18px 6px;");
}